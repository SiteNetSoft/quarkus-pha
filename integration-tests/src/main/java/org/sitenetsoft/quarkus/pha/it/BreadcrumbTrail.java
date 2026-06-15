package org.sitenetsoft.quarkus.pha.it;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Server-side breadcrumb auto-generation: turn a request path into the ordered
 * crumb list the runtime {@code components/navigation/breadcrumb} template
 * consumes in AUTO mode ({@code crumbs=...}).
 *
 * <p>Each crumb is a small map with {@code text}, an optional {@code href}, and
 * {@code current=true} only on the final crumb (the active page renders as plain
 * text, every ancestor as a link). A leading "Home" crumb pointing at {@code /}
 * is always prepended.
 *
 * <p>Segment labels come from an optional lookup keyed by the URL segment; any
 * segment without an entry is humanized ({@code "data-view" → "Data view"}).
 * This keeps the trail readable without forcing the caller to register every
 * route.
 */
public final class BreadcrumbTrail {

    private BreadcrumbTrail() {
    }

    /** Build a trail for {@code path} with no custom labels (all humanized). */
    public static List<Map<String, Object>> fromPath(String path) {
        return fromPath(path, Map.of());
    }

    /**
     * Build a trail for {@code path}. {@code labels} maps a URL segment to a
     * display label; unmapped segments are humanized.
     */
    public static List<Map<String, Object>> fromPath(String path, Map<String, String> labels) {
        List<Map<String, Object>> crumbs = new ArrayList<>();
        crumbs.add(crumb("Home", "/", false));

        if (path == null || path.isBlank()) {
            markLast(crumbs);
            return crumbs;
        }

        String[] segments = path.split("/");
        StringBuilder href = new StringBuilder();
        for (String segment : segments) {
            if (segment.isBlank()) {
                continue;
            }
            href.append('/').append(segment);
            String label = labels.getOrDefault(segment, humanize(segment));
            crumbs.add(crumb(label, href.toString(), false));
        }

        markLast(crumbs);
        return crumbs;
    }

    /** The active (last) crumb drops its href and is flagged current. */
    private static void markLast(List<Map<String, Object>> crumbs) {
        Map<String, Object> last = crumbs.get(crumbs.size() - 1);
        last.put("href", null);
        last.put("current", Boolean.TRUE);
    }

    /**
     * Every crumb carries all three keys — Qute is strict on map access and
     * throws on a missing key, so a non-applicable value is stored as {@code
     * null} (which {@code {#if ...??}} treats as absent) rather than omitted.
     */
    private static Map<String, Object> crumb(String text, String href, boolean current) {
        Map<String, Object> c = new LinkedHashMap<>();
        c.put("text", text);
        c.put("href", href);
        c.put("current", current ? Boolean.TRUE : null);
        return c;
    }

    /** "data-view" → "Data view"; "components" → "Components". */
    static String humanize(String segment) {
        String spaced = segment.replace('-', ' ').replace('_', ' ').trim();
        if (spaced.isEmpty()) {
            return spaced;
        }
        return Character.toUpperCase(spaced.charAt(0)) + spaced.substring(1);
    }
}
