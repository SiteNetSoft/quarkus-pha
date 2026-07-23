package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.Engine;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Routing for the /extensions top-level bucket.
 *
 *   GET /extensions/log-viewer                    → demo page with all examples
 *   GET /extensions/log-viewer/{example}          → standalone fragment wrapped in layouts/base
 *   GET /extensions/log-viewer/source/{example}   → raw Qute source as text/plain
 *
 * The bucket will grow to cover the other PatternFly extensions
 * (User feedback, Data view variants, Catalog view variants) as they ship.
 */
@Path("/extensions")
public class ExtensionsRoutes {

    private static final Set<String> LOG_VIEWER_EXAMPLES = Set.of(
        "basic",
        "line-numbers",
        "with-toolbar",
        "wrap-nowrap",
        "ansi-colors",
        "streaming"
    );

    private static final Set<String> USER_FEEDBACK_EXAMPLES = Set.of(
        "full",
        "basic",
        "with-prefilled-email"
    );

    /** The 5 catalog-view sub-items. Each has one "basic" example. */
    private static final Map<String, String> CATALOG_VIEW_TITLES = Map.ofEntries(
        Map.entry("catalog-item-header",   "Catalog item header"),
        Map.entry("catalog-tile",          "Catalog tile"),
        Map.entry("filter-side-panel",     "Filter side panel"),
        Map.entry("properties-side-panel", "Properties side panel"),
        Map.entry("vertical-tabs",         "Vertical tabs")
    );

    private static final Map<String, String> CATALOG_VIEW_INTROS = Map.ofEntries(
        Map.entry("catalog-item-header",   "Header strip with optional icon, title, and vendor subtitle. The piece you'd put at the top of a catalog item's detail page."),
        Map.entry("catalog-tile",          "Browsable tile in a catalog grid. Icon, title, vendor, description, optional badges; a <code class=\"ws-code\">featured</code> modifier highlights the most prominent entry."),
        Map.entry("filter-side-panel",     "Left rail of grouped checkbox filters with per-item counts. Pair with a tile grid; clicking a checkbox should drive an HTMX swap of the grid below."),
        Map.entry("properties-side-panel", "Right rail of label/value rows. Use next to the main detail content to surface metadata without breaking the body's reading flow."),
        Map.entry("vertical-tabs",         "Vertical navigation list with nested children. Active state highlights the side bar; child lists indent.")
    );

    /** Example fragments served per catalog-view item. Each maps to a template at
        templates/extensions/catalog-view/{name}/{example}.html and a card on the demo page.
        Items not listed here fall back to a single "basic" example. */
    private static final Map<String, Set<String>> CATALOG_VIEW_EXAMPLES = Map.of(
        "catalog-item-header", Set.of("basic", "vendor-description"),
        "catalog-tile",        Set.of("basic", "footer", "link", "icon-badges", "text-badge", "children")
    );

    /** Examples for a catalog-view item, defaulting to just "basic" when none are declared. */
    private static Set<String> catalogViewExamples(String name) {
        return CATALOG_VIEW_EXAMPLES.getOrDefault(name, Set.of("basic"));
    }

    /** The 3 data-view sub-items. */
    private static final Map<String, String> DATA_VIEW_TITLES = Map.ofEntries(
        Map.entry("overview", "Data view overview"),
        Map.entry("toolbar",  "Data view toolbar"),
        Map.entry("table",    "Data view table")
    );

    private static final Map<String, String> DATA_VIEW_INTROS = Map.ofEntries(
        Map.entry("overview", "Composition pattern — toolbar + table + pagination wired with shared client-side state. The Alpine factory <code class=\"ws-code\">phaDataView()</code> owns search, sort, and page; the page passes rows in via <code class=\"ws-code\">data-rows</code>."),
        Map.entry("toolbar",  "Toolbar with search input, filter trigger, removable filter chips, and a result count. Pair with a table or card grid below."),
        Map.entry("table",    "Sortable table with search, pagination, and selectable rows (per-row checkboxes + select-all with a selection count). The heaviest of the three demos; everything happens in Alpine state.")
    );

    /** Example fragments served per data-view item. Each maps to a template at
        templates/extensions/data-view/{name}/{example}.html and a card on the demo page.
        Items not listed here fall back to a single "basic" example. */
    private static final Map<String, Set<String>> DATA_VIEW_EXAMPLES = Map.ofEntries(
        Map.entry("overview", Set.of("basic")),
        Map.entry("toolbar",  Set.of("basic")),
        Map.entry("table",    Set.of("basic", "expandable", "sticky", "tree-table", "resizable", "loading", "error"))
    );

    /** Examples for a data-view item, defaulting to just "basic" when none are declared. */
    private static Set<String> dataViewExamples(String name) {
        return DATA_VIEW_EXAMPLES.getOrDefault(name, Set.of("basic"));
    }

    /** Sample rows used by the overview + table data-view demos. Real data would come from a service / repository. */
    static final String DATA_VIEW_ROWS_JSON =
        "[" +
        "{\"name\":\"Quarkus\",\"vendor\":\"Red Hat\",\"version\":\"3.35.4\",\"license\":\"Apache 2.0\"}," +
        "{\"name\":\"HTMX\",\"vendor\":\"Big Sky Software\",\"version\":\"2.0.10\",\"license\":\"BSD 2-Clause\"}," +
        "{\"name\":\"Alpine.js\",\"vendor\":\"Caleb Porzio\",\"version\":\"3.15.12\",\"license\":\"MIT\"}," +
        "{\"name\":\"PatternFly\",\"vendor\":\"Red Hat\",\"version\":\"6.4.0\",\"license\":\"MIT\"}," +
        "{\"name\":\"ECharts\",\"vendor\":\"Apache\",\"version\":\"6.1.0\",\"license\":\"Apache 2.0\"}," +
        "{\"name\":\"D3.js\",\"vendor\":\"Mike Bostock\",\"version\":\"7.9.0\",\"license\":\"ISC\"}," +
        "{\"name\":\"MapLibre GL\",\"vendor\":\"MapLibre\",\"version\":\"5.24.0\",\"license\":\"BSD 3-Clause\"}," +
        "{\"name\":\"Monaco Editor\",\"vendor\":\"Microsoft\",\"version\":\"0.55.1\",\"license\":\"MIT\"}," +
        "{\"name\":\"Cytoscape\",\"vendor\":\"Cytoscape Consortium\",\"version\":\"3.33.4\",\"license\":\"MIT\"}," +
        "{\"name\":\"video.js\",\"vendor\":\"Video.js\",\"version\":\"8.23.7\",\"license\":\"Apache 2.0\"}," +
        "{\"name\":\"Quill\",\"vendor\":\"Slab\",\"version\":\"2.0.3\",\"license\":\"BSD 3-Clause\"}," +
        "{\"name\":\"Font Awesome Free\",\"vendor\":\"Fonticons\",\"version\":\"7.2.0\",\"license\":\"CC BY 4.0\"}" +
        "]";

    static final String DATA_VIEW_COLUMNS_JSON =
        "[{\"key\":\"name\",\"label\":\"Name\"}," +
        "{\"key\":\"vendor\",\"label\":\"Vendor\"}," +
        "{\"key\":\"version\",\"label\":\"Version\"}," +
        "{\"key\":\"license\",\"label\":\"License\"}]";

    /** Sample app log used by the basic / line-numbers / with-toolbar / wrap-nowrap examples. */
    static final List<String> SAMPLE_LINES = List.of(
        "2026-05-25T08:00:01.142Z INFO  Starting Quarkus 3.35.3",
        "2026-05-25T08:00:01.218Z INFO  Listening on http://0.0.0.0:9090",
        "2026-05-25T08:00:02.301Z INFO  Initialised PostgreSQL pool (size=20)",
        "2026-05-25T08:00:03.011Z DEBUG  Routes registered: 142",
        "2026-05-25T08:00:03.082Z INFO  Cache warmup complete (1283 entries)",
        "2026-05-25T08:00:03.514Z WARN  Deprecated config key 'quarkus.legacy.foo' — will be removed in 4.0",
        "2026-05-25T08:00:04.622Z INFO  Quarkus application ready (3.480s)",
        "2026-05-25T08:00:05.018Z INFO  Health check OK",
        "2026-05-25T08:00:06.319Z DEBUG  GET /api/users 200 12ms",
        "2026-05-25T08:00:06.518Z DEBUG  GET /api/orders 200 18ms",
        "2026-05-25T08:00:07.117Z ERROR Connection refused: redis://cache.internal:6379 — falling back to in-memory cache",
        "2026-05-25T08:00:08.211Z INFO  Background job 'cleanup-temp' started",
        "2026-05-25T08:00:09.054Z DEBUG  Reaped 412 stale sessions",
        "2026-05-25T08:00:10.700Z INFO  Background job 'cleanup-temp' completed in 2489ms",
        "2026-05-25T08:00:12.011Z DEBUG  GET /api/projects 200 9ms",
        "2026-05-25T08:00:13.518Z DEBUG  POST /api/projects 201 31ms",
        "2026-05-25T08:00:14.001Z WARN  Slow query: SELECT * FROM audit_log WHERE user_id = ? (842ms)",
        "2026-05-25T08:00:15.118Z DEBUG  GET /api/dashboard 200 11ms",
        "2026-05-25T08:00:16.882Z INFO  User 12 signed in from 10.0.4.21",
        "2026-05-25T08:00:18.224Z DEBUG  GET /api/users/12 200 6ms",
        "2026-05-25T08:00:20.099Z INFO  Scheduler tick at 2026-05-25T08:00:20Z"
    );

    /** Long-line variant for the wrap/nowrap demo — wide single-line entries reveal the difference. */
    static final List<String> SAMPLE_LINES_LONG = List.of(
        "2026-05-25T08:00:01.142Z INFO  Starting Quarkus 3.35.3 with profile=prod, classpath=/deployments/lib/main/io.quarkus.quarkus-core-3.35.3.jar:/deployments/lib/main/io.quarkus.quarkus-runtime-3.35.3.jar:/deployments/lib/main/io.quarkus.quarkus-arc-3.35.3.jar:/deployments/lib/main/io.quarkus.quarkus-qute-3.35.3.jar",
        "2026-05-25T08:00:02.301Z INFO  Initialised PostgreSQL pool (size=20, jdbc-url=jdbc:postgresql://db.internal:5432/app?currentSchema=public&ApplicationName=quarkus-pha&sslmode=require&sslfactory=org.postgresql.ssl.NonValidatingFactory)",
        "2026-05-25T08:00:03.514Z WARN  Deprecated config key 'quarkus.legacy.foo' set to 'bar' — will be removed in 4.0. Migrate to 'quarkus.foo.value' (see https://quarkus.io/guides/config-reference#deprecations for details on the v3→v4 config rename plan).",
        "2026-05-25T08:00:07.117Z ERROR Connection refused: redis://cache.internal:6379 — falling back to in-memory cache. Underlying cause: io.lettuce.core.RedisConnectionException: Unable to connect to cache.internal:6379: connect timed out after 2000 ms",
        "2026-05-25T08:00:14.001Z WARN  Slow query (842ms): SELECT a.id, a.user_id, a.action, a.target_type, a.target_id, a.metadata, a.created_at FROM audit_log a WHERE a.user_id = ? AND a.created_at > NOW() - INTERVAL '30 days' ORDER BY a.created_at DESC LIMIT 100"
    );

    /** ANSI-colored sample, raw escape form (used for server-side rendering). \u001B is the ESC byte. */
    static final List<String> ANSI_LINES_RAW = List.of(
        "\u001B[32m[OK]\u001B[0m   Pulling base image                  registry.access.redhat.com/ubi9/openjdk-25-runtime:1.24",
        "\u001B[32m[OK]\u001B[0m   Resolving dependencies              45 artifacts",
        "\u001B[33m[WARN]\u001B[0m Snapshot dependency in build         org.acme:experimental:1.0-SNAPSHOT",
        "\u001B[32m[OK]\u001B[0m   Compiling sources                   42 files",
        "\u001B[31m[FAIL]\u001B[0m Test failed                          OrderServiceTest.placeOrder_validatesStock",
        "\u001B[36m[INFO]\u001B[0m Coverage report                      \u001B[1m87.4%\u001B[0m lines, \u001B[1m74.1%\u001B[0m branches",
        "\u001B[34m[BUILD]\u001B[0m Packaging quarkus-app               123ms",
        "\u001B[32m[OK]\u001B[0m   Build succeeded in 18.42s"
    );


    private static final Map<Integer, String> ANSI_COLORS = Map.ofEntries(
        Map.entry(30, "#000"), Map.entry(31, "#c00"), Map.entry(32, "#0a0"), Map.entry(33, "#c80"),
        Map.entry(34, "#04c"), Map.entry(35, "#a0a"), Map.entry(36, "#0aa"), Map.entry(37, "#ccc"),
        Map.entry(90, "#666"), Map.entry(91, "#f44"), Map.entry(92, "#4f4"), Map.entry(93, "#ff0"),
        Map.entry(94, "#48f"), Map.entry(95, "#f4f"), Map.entry(96, "#4ff"), Map.entry(97, "#fff")
    );

    private static final Pattern ANSI_SGR = Pattern.compile("\u001B\\[([0-9;]*)m");

    /**
     * ANSI lines pre-rendered to HTML with inline-styled spans. We do the
     * conversion server-side because raw ESC bytes (U+001B) are forbidden in
     * HTML — vnu rejects them outright. The Alpine ANSI parser still exists
     * for HTMX-streamed runtime logs (where the bytes arrive after page load),
     * but the seeded demo lines come pre-formatted.
     */
    static final List<String> ANSI_LINES_HTML = renderAnsiLines(ANSI_LINES_RAW);


    /** Subset of ECMA-48 SGR: 30-37 / 90-97 foreground, bold (1), reset (0/empty). */
    private static List<String> renderAnsiLines(List<String> raw) {
        List<String> out = new ArrayList<>(raw.size());
        for (String line : raw) {
            out.add(renderAnsiLine(line));
        }
        return List.copyOf(out);
    }

    private static String renderAnsiLine(String input) {
        StringBuilder out = new StringBuilder();
        int pos = 0;
        int stack = 0;
        Matcher m = ANSI_SGR.matcher(input);
        while (m.find()) {
            out.append(htmlEscape(input.substring(pos, m.start())));
            pos = m.end();
            String body = m.group(1);
            int[] codes = body.isEmpty() ? new int[] { 0 } : parseCodes(body);
            for (int code : codes) {
                if (code == 0) {
                    while (stack > 0) { out.append("</span>"); stack--; }
                } else if (code == 1) {
                    out.append("<span style=\"font-weight:bold\">");
                    stack++;
                } else {
                    String color = ANSI_COLORS.get(code);
                    if (color != null) {
                        out.append("<span style=\"color:").append(color).append("\">");
                        stack++;
                    }
                }
            }
        }
        out.append(htmlEscape(input.substring(pos)));
        while (stack > 0) { out.append("</span>"); stack--; }
        return out.toString();
    }

    private static int[] parseCodes(String body) {
        String[] parts = body.split(";");
        int[] result = new int[parts.length];
        for (int i = 0; i < parts.length; i++) {
            try { result[i] = Integer.parseInt(parts[i]); }
            catch (NumberFormatException e) { result[i] = 0; }
        }
        return result;
    }

    private static String htmlEscape(String s) {
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }

    @Inject
    Engine engine;

    @Location("extensions/log-viewer")
    @Inject
    Template logViewerPage;

    @Location("extensions/log-viewer/_standalone")
    @Inject
    Template logViewerStandalonePage;

    @Location("extensions/user-feedback")
    @Inject
    Template userFeedbackPage;

    @Location("extensions/user-feedback/_standalone")
    @Inject
    Template userFeedbackStandalonePage;

    @Location("extensions/catalog-view/_demo-page")
    @Inject
    Template catalogViewDemoPage;

    @Location("extensions/catalog-view/_standalone")
    @Inject
    Template catalogViewStandalonePage;

    @Location("extensions/data-view/_demo-page")
    @Inject
    Template dataViewDemoPage;

    @Location("extensions/data-view/_standalone")
    @Inject
    Template dataViewStandalonePage;

    @GET
    @Path("/log-viewer")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance logViewer() {
        return logViewerPage.instance()
            .data("sampleLines", SAMPLE_LINES)
            .data("sampleLinesLong", SAMPLE_LINES_LONG)
            .data("ansiLines", ANSI_LINES_HTML);
    }

    @GET
    @Path("/log-viewer/source/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String logViewerSource(@PathParam("example") String example) {
        if (!LOG_VIEWER_EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown log-viewer example: " + example);
        }
        String resourcePath = "/templates/extensions/log-viewer/" + example + ".html";
        try (InputStream in = getClass().getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new NotFoundException("Missing source for: " + example);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed reading " + resourcePath, e);
        }
    }

    @GET
    @Path("/log-viewer/{example}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance logViewerStandalone(@PathParam("example") String example) {
        if (!LOG_VIEWER_EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown log-viewer example: " + example);
        }
        Template inner = engine.getTemplate("extensions/log-viewer/" + example);
        if (inner == null) {
            throw new NotFoundException("Template not found for: " + example);
        }
        String rendered = inner.instance()
            .data("sampleLines", SAMPLE_LINES)
            .data("sampleLinesLong", SAMPLE_LINES_LONG)
            .data("ansiLines", ANSI_LINES_HTML)
            .render();
        return logViewerStandalonePage.instance()
            .data("example", example)
            .data("content", rendered);
    }

    @GET
    @Path("/user-feedback")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance userFeedback() {
        return userFeedbackPage.instance();
    }

    @GET
    @Path("/user-feedback/source/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String userFeedbackSource(@PathParam("example") String example) {
        if (!USER_FEEDBACK_EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown user-feedback example: " + example);
        }
        String resourcePath = "/templates/extensions/user-feedback/" + example + ".html";
        try (InputStream in = getClass().getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new NotFoundException("Missing source for: " + example);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed reading " + resourcePath, e);
        }
    }

    @GET
    @Path("/user-feedback/{example}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance userFeedbackStandalone(@PathParam("example") String example) {
        if (!USER_FEEDBACK_EXAMPLES.contains(example)) {
            throw new NotFoundException("Unknown user-feedback example: " + example);
        }
        Template inner = engine.getTemplate("extensions/user-feedback/" + example);
        if (inner == null) {
            throw new NotFoundException("Template not found for: " + example);
        }
        String rendered = inner.instance().render();
        return userFeedbackStandalonePage.instance()
            .data("example", example)
            .data("content", rendered);
    }

    @GET
    @Path("/catalog-view/{name}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance catalogView(@PathParam("name") String name) {
        String title = CATALOG_VIEW_TITLES.get(name);
        if (title == null) {
            throw new NotFoundException("Unknown catalog-view item: " + name);
        }
        return catalogViewDemoPage.instance()
            .data("type", name)
            .data("title", title)
            .data("intro", CATALOG_VIEW_INTROS.get(name));
    }

    @GET
    @Path("/catalog-view/{name}/source/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String catalogViewSource(@PathParam("name") String name, @PathParam("example") String example) {
        if (!CATALOG_VIEW_TITLES.containsKey(name) || !catalogViewExamples(name).contains(example)) {
            throw new NotFoundException("Unknown catalog-view example: " + name + "/" + example);
        }
        String resourcePath = "/templates/extensions/catalog-view/" + name + "/" + example + ".html";
        try (InputStream in = getClass().getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new NotFoundException("Missing source for: " + name + "/" + example);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed reading " + resourcePath, e);
        }
    }

    @GET
    @Path("/catalog-view/{name}/source-java/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String catalogViewSourceJava(@PathParam("name") String name, @PathParam("example") String example) {
        if (!CATALOG_VIEW_TITLES.containsKey(name) || !catalogViewExamples(name).contains(example)) {
            throw new NotFoundException("Unknown catalog-view example: " + name + "/" + example);
        }
        String resourcePath = "/code-samples/catalog-view/" + name + "/" + example + ".java";
        try (InputStream in = getClass().getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new NotFoundException("Missing Java snippet for: " + name + "/" + example);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed reading " + resourcePath, e);
        }
    }

    @GET
    @Path("/catalog-view/{name}/{example}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance catalogViewStandalone(@PathParam("name") String name, @PathParam("example") String example) {
        if (!CATALOG_VIEW_TITLES.containsKey(name) || !catalogViewExamples(name).contains(example)) {
            throw new NotFoundException("Unknown catalog-view example: " + name + "/" + example);
        }
        Template inner = engine.getTemplate("extensions/catalog-view/" + name + "/" + example);
        if (inner == null) {
            throw new NotFoundException("Template not found for: " + name + "/" + example);
        }
        String rendered = inner.instance().render();
        return catalogViewStandalonePage.instance()
            .data("example", example)
            .data("content", rendered);
    }

    @GET
    @Path("/data-view/{name}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance dataView(@PathParam("name") String name) {
        String title = DATA_VIEW_TITLES.get(name);
        if (title == null) {
            throw new NotFoundException("Unknown data-view item: " + name);
        }
        return dataViewDemoPage.instance()
            .data("type", name)
            .data("title", title)
            .data("intro", DATA_VIEW_INTROS.get(name))
            .data("columnsJson", DATA_VIEW_COLUMNS_JSON)
            .data("rowsJson", DATA_VIEW_ROWS_JSON);
    }

    @GET
    @Path("/data-view/{name}/source/{example}")
    @Produces(MediaType.TEXT_PLAIN)
    public String dataViewSource(@PathParam("name") String name, @PathParam("example") String example) {
        if (!DATA_VIEW_TITLES.containsKey(name) || !dataViewExamples(name).contains(example)) {
            throw new NotFoundException("Unknown data-view example: " + name + "/" + example);
        }
        String resourcePath = "/templates/extensions/data-view/" + name + "/" + example + ".html";
        try (InputStream in = getClass().getResourceAsStream(resourcePath)) {
            if (in == null) {
                throw new NotFoundException("Missing source for: " + name + "/" + example);
            }
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Failed reading " + resourcePath, e);
        }
    }

    @GET
    @Path("/data-view/{name}/{example}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance dataViewStandalone(@PathParam("name") String name, @PathParam("example") String example) {
        if (!DATA_VIEW_TITLES.containsKey(name) || !dataViewExamples(name).contains(example)) {
            throw new NotFoundException("Unknown data-view example: " + name + "/" + example);
        }
        Template inner = engine.getTemplate("extensions/data-view/" + name + "/" + example);
        if (inner == null) {
            throw new NotFoundException("Template not found for: " + name + "/" + example);
        }
        String rendered = inner.instance()
            .data("columnsJson", DATA_VIEW_COLUMNS_JSON)
            .data("rowsJson", DATA_VIEW_ROWS_JSON)
            .render();
        return dataViewStandalonePage.instance()
            .data("example", example)
            .data("content", rendered);
    }
}
