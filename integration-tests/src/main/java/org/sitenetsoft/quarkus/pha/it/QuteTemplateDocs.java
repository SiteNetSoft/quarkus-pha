package org.sitenetsoft.quarkus.pha.it;

import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Stream;

/**
 * Discovers the runtime component templates and extracts their header doc comments — the data
 * behind the per-component "Qute template" docs tab. Every runtime template documents its
 * parameters and slots in leading Qute comments (house convention), so the tab renders that
 * reference straight from the sources and can never drift from the code.
 *
 * <p>The runtime root is located through a known anchor resource so the showcase app's own demo
 * fragments (which also live under templates/components/) are never scanned. Scanning failures
 * throw — a packaging change must fail the smoke tests loudly, not quietly empty every Qute tab.
 */
final class QuteTemplateDocs {

    /** One runtime template: file base name, include path, ready-made include line, header comment. */
    @io.quarkus.qute.TemplateData
    record TemplateDoc(String name, String path, String include, String doc) {
    }

    /** Demo slugs whose runtime template file uses a different base name. */
    private static final Map<String, String> BASE_ALIASES = Map.of("navigation", "nav");

    /**
     * The runtime module's category directories (plus the flat icon.html). In dev mode the
     * classpath merges the showcase app's own templates/components/ (demo pages, per-slug example
     * fragment dirs) into the same root — this allow-list keeps them out of the reference.
     */
    private static final Set<String> RUNTIME_CATEGORIES = Set.of(
        "actions", "charts", "data-display", "editors", "error-communication", "extensions",
        "feedback", "forms", "htmx", "maps", "media", "navigation");

    private static final String TEMPLATES_ROOT = "templates/components/";
    private static final String ANCHOR_RESOURCE = TEMPLATES_ROOT + "feedback/backdrop.html";

    private static volatile Map<String, String> sources;

    private QuteTemplateDocs() {
    }

    /**
     * The runtime templates belonging to the slug: the base file plus its dash-prefixed brick
     * fragments (base first, bricks alphabetical). Longest-prefix ownership keeps e.g.
     * menu-toggle.html out of menu's docs; -model- partials are internal and excluded. Empty for
     * composition components with no runtime template.
     */
    static List<TemplateDoc> docsFor(String slug, Collection<String> allSlugs) {
        String base = BASE_ALIASES.getOrDefault(slug, slug);
        Set<String> longerBases = new HashSet<>();
        for (String s : allSlugs) {
            String b = BASE_ALIASES.getOrDefault(s, s);
            if (!b.equals(base) && b.length() > base.length()) {
                longerBases.add(b);
            }
        }
        List<TemplateDoc> out = new ArrayList<>();
        for (Map.Entry<String, String> e : sources().entrySet()) {
            String rel = e.getKey();
            int slash = rel.indexOf('/');
            boolean runtimeOwned = slash < 0
                ? rel.equals("icon")
                : RUNTIME_CATEGORIES.contains(rel.substring(0, slash));
            String name = rel.substring(rel.lastIndexOf('/') + 1);
            // -model partials and nav-items are model-mode internals, not includable param API.
            if (!runtimeOwned || name.contains("-model") || name.endsWith("-demo") || name.equals("nav-items")) {
                continue;
            }
            if (!name.equals(base) && !name.startsWith(base + "-")) {
                continue;
            }
            if (longerBases.stream().anyMatch(b -> name.equals(b) || name.startsWith(b + "-"))) {
                continue;
            }
            String path = "components/" + rel;
            out.add(new TemplateDoc(name, path, "{#include " + path + " /}", headerComment(e.getValue())));
        }
        out.sort(Comparator.comparing((TemplateDoc d) -> !d.name().equals(base))
                .thenComparing(TemplateDoc::name));
        return out;
    }

    /** The leading run of {@code {! ... !}} blocks, joined and stripped. */
    private static String headerComment(String src) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < src.length()) {
            while (i < src.length() && Character.isWhitespace(src.charAt(i))) {
                i++;
            }
            if (!src.startsWith("{!", i)) {
                break;
            }
            int end = src.indexOf("!}", i + 2);
            if (end < 0) {
                break;
            }
            if (sb.length() > 0) {
                sb.append('\n');
            }
            sb.append(src, i + 2, end);
            i = end + 2;
        }
        return sb.toString().strip();
    }

    private static Map<String, String> sources() {
        Map<String, String> s = sources;
        if (s == null) {
            synchronized (QuteTemplateDocs.class) {
                if (sources == null) {
                    sources = scan();
                }
                s = sources;
            }
        }
        return s;
    }

    /** Keys are runtime template paths relative to templates/components/, without .html. */
    private static Map<String, String> scan() {
        try {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            // Preferred: the build-generated index (see generateTemplateIndex in build.gradle).
            // Resources can be READ under every packaging mode — including native's resource:
            // protocol — but only file/jar classpaths can be WALKED, so enumeration is baked in.
            try (InputStream index = cl.getResourceAsStream("pha-template-index.txt")) {
                if (index != null) {
                    Map<String, String> out = new TreeMap<>();
                    for (String rel : new String(index.readAllBytes(), StandardCharsets.UTF_8).split("\n")) {
                        rel = rel.strip();
                        if (rel.isEmpty()) {
                            continue;
                        }
                        try (InputStream in = cl.getResourceAsStream(TEMPLATES_ROOT + rel + ".html")) {
                            if (in != null) {
                                out.put(rel, new String(in.readAllBytes(), StandardCharsets.UTF_8));
                            }
                        }
                    }
                    if (!out.isEmpty()) {
                        return Map.copyOf(out);
                    }
                }
            }
            URL anchor = cl.getResource(ANCHOR_RESOURCE);
            if (anchor == null) {
                throw new IllegalStateException("Anchor resource not found: " + ANCHOR_RESOURCE);
            }
            Map<String, String> out = new TreeMap<>();
            if ("file".equals(anchor.getProtocol())) {
                Path anchorFile = Paths.get(anchor.toURI());
                Path root = anchorFile.getParent().getParent();
                try (Stream<Path> walk = Files.walk(root)) {
                    for (Path p : (Iterable<Path>) walk::iterator) {
                        if (Files.isRegularFile(p) && p.getFileName().toString().endsWith(".html")) {
                            String rel = root.relativize(p).toString().replace('\\', '/');
                            out.put(rel.substring(0, rel.length() - ".html".length()), Files.readString(p));
                        }
                    }
                }
            } else if ("jar".equals(anchor.getProtocol())) {
                JarURLConnection conn = (JarURLConnection) anchor.openConnection();
                conn.setUseCaches(false);
                try (JarFile jar = conn.getJarFile()) {
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String n = entry.getName();
                        if (n.startsWith(TEMPLATES_ROOT) && n.endsWith(".html")) {
                            try (InputStream in = jar.getInputStream(entry)) {
                                String rel = n.substring(TEMPLATES_ROOT.length(), n.length() - ".html".length());
                                out.put(rel, new String(in.readAllBytes(), StandardCharsets.UTF_8));
                            }
                        }
                    }
                }
            } else {
                throw new IllegalStateException("Unsupported resource protocol for " + anchor);
            }
            return Map.copyOf(out);
        } catch (Exception e) {
            throw new IllegalStateException("Failed scanning runtime component templates", e);
        }
    }
}
