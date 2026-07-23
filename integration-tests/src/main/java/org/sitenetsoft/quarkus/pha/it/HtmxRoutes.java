package org.sitenetsoft.quarkus.pha.it;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.sse.OutboundSseEvent;
import jakarta.ws.rs.sse.Sse;

import io.quarkus.qute.Engine;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.mutiny.Multi;

import org.sitenetsoft.quarkus.pha.model.CalendarMonth;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Path("/api/htmx")
public class HtmxRoutes {

    private static final List<String[]> ITEMS = List.of(
        new String[]{"Server Alpha", "Primary application server"},
        new String[]{"Server Beta", "Secondary application server"},
        new String[]{"Database Gamma", "PostgreSQL cluster"},
        new String[]{"Cache Delta", "Redis cache layer"},
        new String[]{"Queue Epsilon", "Message queue broker"},
        new String[]{"Gateway Zeta", "API gateway"},
        new String[]{"Monitor Eta", "Monitoring service"},
        new String[]{"Storage Theta", "Object storage"},
        new String[]{"Auth Iota", "Authentication service"},
        new String[]{"Logger Kappa", "Centralized logging"},
        new String[]{"Proxy Lambda", "Reverse proxy"},
        new String[]{"Search Mu", "Search engine"},
        new String[]{"Scheduler Nu", "Task scheduler"},
        new String[]{"Config Xi", "Configuration service"},
        new String[]{"Registry Omicron", "Service registry"},
        new String[]{"Vault Pi", "Secrets management"},
        new String[]{"Mesh Rho", "Service mesh"},
        new String[]{"CDN Sigma", "Content delivery"},
        new String[]{"DNS Tau", "DNS resolver"},
        new String[]{"Firewall Upsilon", "Network firewall"},
        new String[]{"Balancer Phi", "Load balancer"},
        new String[]{"Backup Chi", "Backup service"},
        new String[]{"Deployer Psi", "Deployment pipeline"},
        new String[]{"Orchestrator Omega", "Container orchestration"}
    );

    private static final int PAGE_SIZE = 6;

    @Inject
    @Location("components/calendar-month/basic")
    Template calendarMonthBasicFragment;

    @Inject
    @Location("components/calendar-month/date-range")
    Template calendarMonthRangeFragment;

    /** PF clamps the year input to 1900-2100; out-of-range/garbage falls back. */
    private static YearMonth calendarGrid(Integer year, Integer month, LocalDate fallback) {
        int y = (year != null && year >= 1900 && year <= 2100) ? year : fallback.getYear();
        int m = (month != null && month >= 1 && month <= 12) ? month : fallback.getMonthValue();
        return YearMonth.of(y, m);
    }

    private static LocalDate parseDate(String value, LocalDate fallback) {
        if (value == null) {
            return fallback;
        }
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException e) {
            return fallback;
        }
    }

    @Inject
    @Location("partials/date-picker-calendar")
    Template datePickerCalendarPartial;

    @GET
    @Path("/date-picker/{example}")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance datePickerCalendar(@PathParam("example") String example,
            @QueryParam("year") Integer year, @QueryParam("month") Integer month,
            @QueryParam("selected") String selected, @QueryParam("pick") String pick) {
        DatePickerDemoData.Config config = DatePickerDemoData.CONFIGS.get(example);
        if (config == null) {
            throw new NotFoundException("Unknown date-picker example: " + example);
        }
        LocalDate sel = parseDate(selected, null);
        YearMonth grid = calendarGrid(year, month, sel != null ? sel : LocalDate.of(2026, 5, 1));
        TemplateInstance ti = datePickerCalendarPartial
                .data("cal", DatePickerDemoData.calendar(example, grid, sel))
                .data("pickerId", config.pickerId());
        if (pick != null && sel != null) {
            ti = ti.data("picked", config.format().format(sel));
        }
        return ti;
    }

    @GET
    @Path("/calendar-month/basic")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance calendarMonthBasic(@QueryParam("year") Integer year,
            @QueryParam("month") Integer month, @QueryParam("selected") String selected) {
        LocalDate sel = parseDate(selected, CalendarMonthDemoData.BASIC_DEFAULT_SELECTED);
        YearMonth grid = calendarGrid(year, month, sel);
        return calendarMonthBasicFragment.data("demoCalBasic", CalendarMonthDemoData.basic(grid, sel));
    }

    @GET
    @Path("/calendar-month/date-range")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance calendarMonthRange(@QueryParam("year") Integer year,
            @QueryParam("month") Integer month, @QueryParam("end") String end) {
        LocalDate endDate = parseDate(end, CalendarMonthDemoData.RANGE_DEFAULT_END);
        if (endDate.isBefore(CalendarMonthDemoData.RANGE_START)) {
            endDate = CalendarMonthDemoData.RANGE_DEFAULT_END;
        }
        YearMonth grid = calendarGrid(year, month, endDate);
        return calendarMonthRangeFragment.data("demoCalRange", CalendarMonthDemoData.range(grid, endDate));
    }

    @GET
    @Path("/search")
    @Produces(MediaType.TEXT_HTML)
    public String search(@QueryParam("q") String query) {
        String q = query == null ? "" : query.trim().toLowerCase();

        StringBuilder html = new StringBuilder();
        int count = 0;

        for (String[] item : ITEMS) {
            if (q.isEmpty() || item[0].toLowerCase().contains(q) || item[1].toLowerCase().contains(q)) {
                html.append("<li class=\"pf-v6-c-menu__list-item\" id=\"search-result-").append(count).append("\">");
                html.append("<button class=\"pf-v6-c-menu__item\" type=\"button\">");
                html.append("<span class=\"pf-v6-c-menu__item-main\">");
                html.append("<span class=\"pf-v6-c-menu__item-text\">").append(escapeHtml(item[0])).append("</span>");
                html.append("</span>");
                html.append("<span class=\"pf-v6-c-menu__item-description\">").append(escapeHtml(item[1])).append("</span>");
                html.append("</button></li>\n");
                count++;
            }
        }

        if (count == 0) {
            html.append("<li class=\"pf-v6-c-menu__list-item\"><div class=\"pf-v6-c-menu__item\">");
            html.append("<span class=\"pf-v6-c-menu__item-main\"><span class=\"pf-v6-c-menu__item-text\">No results found</span></span>");
            html.append("</div></li>\n");
        }

        return html.toString();
    }

    @POST
    @Path("/form-validate")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public String formValidate(@FormParam("username") String username, @FormParam("email") String email) {
        String u = username == null ? "" : username.trim();
        String e = email == null ? "" : email.trim();
        String errUsername = null;
        String errEmail = null;

        if (u.isEmpty()) {
            errUsername = "Username is required.";
        } else if (u.length() < 3 || u.length() > 20) {
            errUsername = "Username must be 3-20 characters.";
        } else if (!u.matches("[a-zA-Z0-9_]+")) {
            errUsername = "Use letters, numbers, and underscores only.";
        } else if (u.equalsIgnoreCase("admin") || u.equalsIgnoreCase("root")) {
            errUsername = "That username is already taken.";
        }

        if (e.isEmpty()) {
            errEmail = "Email is required.";
        } else if (!e.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) {
            errEmail = "Enter a valid email address.";
        }

        boolean ok = errUsername == null && errEmail == null;
        var tpl = engine.getTemplate("partials/htmx/signup-form").instance();
        if (ok) {
            tpl.data("username", u).data("email", "").data("success", true);
        } else {
            tpl.data("username", u).data("email", e)
               .data("errUsername", errUsername).data("errEmail", errEmail);
        }
        return tpl.render();
    }

    @GET
    @Path("/items")
    @Produces(MediaType.TEXT_HTML)
    public String items(@QueryParam("page") int page) {
        int start = page * PAGE_SIZE;
        if (start >= ITEMS.size()) {
            return ""; // No more items — sentinel removed
        }

        int end = Math.min(start + PAGE_SIZE, ITEMS.size());
        StringBuilder html = new StringBuilder();

        for (int i = start; i < end; i++) {
            String[] item = ITEMS.get(i);
            html.append("<div class=\"pf-v6-c-data-list__item\" id=\"scroll-item-").append(i).append("\">");
            html.append("<div class=\"pf-v6-c-data-list__item-row\">");
            html.append("<div class=\"pf-v6-c-data-list__item-content\">");
            html.append("<div class=\"pf-v6-c-data-list__cell pf-m-align-left\">");
            html.append("<span class=\"pf-v6-c-data-list__cell-text\">").append(escapeHtml(item[0])).append("</span></div>");
            html.append("<div class=\"pf-v6-c-data-list__cell\">");
            html.append("<span class=\"pf-v6-c-data-list__cell-text\">").append(escapeHtml(item[1])).append("</span></div>");
            html.append("</div></div></div>\n");
        }

        // Append next sentinel if more items exist. Trigger is scoped to the demo's scroll
        // container (#scroll-list) via IntersectionObserver — "revealed" keys off window
        // scroll and never fires for a sentinel inside a bounded overflow:auto container.
        // This endpoint exclusively backs that container, so the selector is hardcoded here;
        // it must match root="#scroll-list" on the initial sentinel (components/infinite-scroll/basic).
        int nextPage = page + 1;
        if (nextPage * PAGE_SIZE < ITEMS.size()) {
            html.append("<div class=\"pha-c-htmx-sentinel\" id=\"scroll-sentinel-").append(nextPage).append("\"");
            html.append(" hx-get=\"/api/htmx/items?page=").append(nextPage).append("\"");
            html.append(" hx-trigger=\"intersect once root:#scroll-list\" hx-swap=\"afterend\"></div>\n");
        }

        return html.toString();
    }

    @GET
    @Path("/load-more")
    @Produces(MediaType.TEXT_HTML)
    public String loadMore(@QueryParam("page") int page) {
        int start = page * PAGE_SIZE;
        if (start >= ITEMS.size()) {
            return "";
        }

        int end = Math.min(start + PAGE_SIZE, ITEMS.size());
        StringBuilder html = new StringBuilder();

        for (int i = start; i < end; i++) {
            String[] item = ITEMS.get(i);
            html.append("<div class=\"pf-v6-c-data-list__item\" id=\"load-item-").append(i).append("\">");
            html.append("<div class=\"pf-v6-c-data-list__item-row\">");
            html.append("<div class=\"pf-v6-c-data-list__item-content\">");
            html.append("<div class=\"pf-v6-c-data-list__cell pf-m-align-left\">");
            html.append("<span class=\"pf-v6-c-data-list__cell-text\">").append(escapeHtml(item[0])).append("</span></div>");
            html.append("<div class=\"pf-v6-c-data-list__cell\">");
            html.append("<span class=\"pf-v6-c-data-list__cell-text\">").append(escapeHtml(item[1])).append("</span></div>");
            html.append("</div></div></div>\n");
        }

        // Append next "load more" button if more items exist
        int nextPage = page + 1;
        if (nextPage * PAGE_SIZE < ITEMS.size()) {
            html.append("<div class=\"pha-c-htmx-load-more\" id=\"load-more-btn-").append(nextPage).append("\">");
            html.append("<button class=\"pf-v6-c-button pf-m-primary\" type=\"button\"");
            html.append(" hx-get=\"/api/htmx/load-more?page=").append(nextPage).append("\"");
            html.append(" hx-swap=\"outerHTML\" hx-target=\"#load-more-btn-").append(nextPage).append("\">");
            html.append("Load more</button></div>\n");
        }

        return html.toString();
    }

    @GET
    @Path("/modal-content/{id}")
    @Produces(MediaType.TEXT_HTML)
    public String modalContent(@PathParam("id") String id) {
        return "<div id=\"modal-loaded-content-" + escapeHtml(id) + "\">"
             + "<p>This content was loaded on demand via HTMX for modal <strong>" + escapeHtml(id) + "</strong>.</p>"
             + "<dl class=\"pf-v6-c-description-list\">"
             + "<div class=\"pf-v6-c-description-list__group\">"
             + "<dt class=\"pf-v6-c-description-list__term\"><span class=\"pf-v6-c-description-list__text\">Status</span></dt>"
             + "<dd class=\"pf-v6-c-description-list__description\"><div class=\"pf-v6-c-description-list__text\">Active</div></dd>"
             + "</div>"
             + "<div class=\"pf-v6-c-description-list__group\">"
             + "<dt class=\"pf-v6-c-description-list__term\"><span class=\"pf-v6-c-description-list__text\">Loaded at</span></dt>"
             + "<dd class=\"pf-v6-c-description-list__description\"><div class=\"pf-v6-c-description-list__text\">" + java.time.Instant.now().toString() + "</div></dd>"
             + "</div>"
             + "<div class=\"pf-v6-c-description-list__group\">"
             + "<dt class=\"pf-v6-c-description-list__term\"><span class=\"pf-v6-c-description-list__text\">Source</span></dt>"
             + "<dd class=\"pf-v6-c-description-list__description\"><div class=\"pf-v6-c-description-list__text\">/api/htmx/modal-content/" + escapeHtml(id) + "</div></dd>"
             + "</div>"
             + "</dl>"
             + "</div>";
    }

    // ---------- Skeleton loading (HTMX swap placeholder) ----------

    @GET
    @Path("/skeleton-content")
    @Produces(MediaType.TEXT_HTML)
    public String skeletonContent() {
        try {
            Thread.sleep(700); // simulate a slow upstream so the skeleton is visible
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "<div id=\"skeleton-loaded\">"
             + "<h3 class=\"pf-v6-c-content--h3\" style=\"margin: 0\">Ada Lovelace</h3>"
             + "<p class=\"pf-v6-c-content--small\">Staff engineer &middot; Platform team</p>"
             + "<dl class=\"pf-v6-c-description-list pf-m-horizontal\" style=\"margin-top: 1rem\">"
             + dlRow("Location", "Cambridge, UK")
             + dlRow("Joined", "2019")
             + dlRow("Status", "Active")
             + "</dl></div>";
    }

    private static final List<String[]> TABLE_ROWS = List.of(
        new String[]{"Alice Chen",   "Admin",  "us-east-1"},
        new String[]{"Bob Johnson",  "Viewer", "eu-west-1"},
        new String[]{"Diego Ramos",  "Viewer", "ap-southeast-1"},
        new String[]{"Jane Smith",   "Editor", "eu-west-1"},
        new String[]{"John Doe",     "Admin",  "us-west-2"}
    );

    private static final List<String[]> TABLE_COLUMNS = List.of(
        new String[]{"name",   "Name"},
        new String[]{"role",   "Role"},
        new String[]{"region", "Region"}
    );

    @GET
    @Path("/table-sort")
    @Produces(MediaType.TEXT_HTML)
    public String tableSort(@QueryParam("col") String col, @QueryParam("dir") String dir) {
        int colIdx = 0;
        for (int i = 0; i < TABLE_COLUMNS.size(); i++) {
            if (TABLE_COLUMNS.get(i)[0].equals(col)) {
                colIdx = i;
                break;
            }
        }
        boolean ascending = !"desc".equalsIgnoreCase(dir);

        List<String[]> sorted = new ArrayList<>(TABLE_ROWS);
        final int sortIdx = colIdx;
        Comparator<String[]> cmp = Comparator.comparing(r -> r[sortIdx], String.CASE_INSENSITIVE_ORDER);
        if (!ascending) {
            cmp = cmp.reversed();
        }
        sorted.sort(cmp);

        StringBuilder html = new StringBuilder();
        html.append("<table class=\"pf-v6-c-table\" role=\"grid\" id=\"tbl-sortable\" aria-label=\"Sortable table example\">");
        html.append("<thead><tr>");
        for (int i = 0; i < TABLE_COLUMNS.size(); i++) {
            String key = TABLE_COLUMNS.get(i)[0];
            String label = TABLE_COLUMNS.get(i)[1];
            boolean active = i == colIdx;
            String ariaSort = active ? (ascending ? "ascending" : "descending") : "none";
            String nextDir = active && ascending ? "desc" : "asc";
            String iconClass;
            if (active) {
                iconClass = ascending ? "fas fa-long-arrow-alt-up" : "fas fa-long-arrow-alt-down";
            } else {
                iconClass = "fas fa-arrows-alt-v";
            }
            html.append("<th scope=\"col\" class=\"pf-v6-c-table__sort").append(active ? " pf-m-selected" : "").append("\" aria-sort=\"").append(ariaSort).append("\">");
            html.append("<button type=\"button\" class=\"pf-v6-c-table__button\"");
            html.append(" hx-get=\"/api/htmx/table-sort?col=").append(key).append("&amp;dir=").append(nextDir).append("\"");
            html.append(" hx-target=\"#tbl-sortable\" hx-swap=\"outerHTML\">");
            html.append("<div class=\"pf-v6-c-table__button-content\">");
            html.append("<span class=\"pf-v6-c-table__text\">").append(escapeHtml(label)).append("</span>");
            html.append("<span class=\"pf-v6-c-table__sort-indicator\"><i class=\"").append(iconClass).append("\" aria-hidden=\"true\"></i></span>");
            html.append("</div></button></th>");
        }
        html.append("</tr></thead><tbody>");
        for (String[] row : sorted) {
            html.append("<tr>");
            for (String cell : row) {
                html.append("<td>").append(escapeHtml(cell)).append("</td>");
            }
            html.append("</tr>");
        }
        html.append("</tbody></table>");
        return html.toString();
    }

    // ---------- Toasts (HX-Trigger) + confirm ----------

    @POST
    @Path("/toast/save")
    @Produces(MediaType.TEXT_HTML)
    public Response toastSave() {
        return Response.ok("")
            .header("HX-Trigger", "{\"pha:toast\": {\"variant\": \"success\", \"title\": \"Changes saved successfully.\"}}")
            .build();
    }

    @POST
    @Path("/toast/delete")
    @Produces(MediaType.TEXT_HTML)
    public Response toastDelete() {
        return Response.ok("")
            .header("HX-Trigger", "{\"pha:toast\": {\"variant\": \"danger\", \"title\": \"Item deleted.\"}}")
            .build();
    }

    // ---------- Inline edit (click-to-edit, server-driven) ----------

    private static String ceName = "Jane Smith";
    private static String ceTitle = "Senior Engineer";

    private String clickToEditView() {
        return "<div id=\"htmx-click-to-edit\">"
             + "<dl class=\"pf-v6-c-description-list pf-m-horizontal\">"
             + "<div class=\"pf-v6-c-description-list__group\">"
             + "<dt class=\"pf-v6-c-description-list__term\"><span class=\"pf-v6-c-description-list__text\">Name</span></dt>"
             + "<dd class=\"pf-v6-c-description-list__description\"><div class=\"pf-v6-c-description-list__text\">" + escapeHtml(ceName) + "</div></dd>"
             + "</div>"
             + "<div class=\"pf-v6-c-description-list__group\">"
             + "<dt class=\"pf-v6-c-description-list__term\"><span class=\"pf-v6-c-description-list__text\">Title</span></dt>"
             + "<dd class=\"pf-v6-c-description-list__description\"><div class=\"pf-v6-c-description-list__text\">" + escapeHtml(ceTitle) + "</div></dd>"
             + "</div>"
             + "</dl>"
             + "<button class=\"pf-v6-c-button pf-m-secondary\" type=\"button\" hx-get=\"/api/htmx/click-to-edit/form\" hx-target=\"#htmx-click-to-edit\" hx-swap=\"outerHTML\">"
             + "<span class=\"pf-v6-c-button__text\">Edit</span></button>"
             + "</div>";
    }

    private String clickToEditForm() {
        return "<form id=\"htmx-click-to-edit\" class=\"pf-v6-c-form\" hx-post=\"/api/htmx/click-to-edit\" hx-target=\"#htmx-click-to-edit\" hx-swap=\"outerHTML\">"
             + "<div class=\"pf-v6-c-form__group\">"
             + "<div class=\"pf-v6-c-form__group-label\"><label class=\"pf-v6-c-form__label\" for=\"ce-name\"><span class=\"pf-v6-c-form__label-text\">Name</span></label></div>"
             + "<div class=\"pf-v6-c-form__group-control\"><span class=\"pf-v6-c-form-control\"><input type=\"text\" id=\"ce-name\" name=\"name\" value=\"" + escapeHtml(ceName) + "\" /></span></div>"
             + "</div>"
             + "<div class=\"pf-v6-c-form__group\">"
             + "<div class=\"pf-v6-c-form__group-label\"><label class=\"pf-v6-c-form__label\" for=\"ce-title\"><span class=\"pf-v6-c-form__label-text\">Title</span></label></div>"
             + "<div class=\"pf-v6-c-form__group-control\"><span class=\"pf-v6-c-form-control\"><input type=\"text\" id=\"ce-title\" name=\"title\" value=\"" + escapeHtml(ceTitle) + "\" /></span></div>"
             + "</div>"
             + "<div class=\"pf-v6-c-form__group pf-m-action\"><div class=\"pf-v6-c-form__actions\">"
             + "<button type=\"submit\" class=\"pf-v6-c-button pf-m-primary\"><span class=\"pf-v6-c-button__text\">Save</span></button>"
             + "<button type=\"button\" class=\"pf-v6-c-button pf-m-link\" hx-get=\"/api/htmx/click-to-edit\" hx-target=\"#htmx-click-to-edit\" hx-swap=\"outerHTML\"><span class=\"pf-v6-c-button__text\">Cancel</span></button>"
             + "</div></div>"
             + "</form>";
    }

    @GET
    @Path("/click-to-edit")
    @Produces(MediaType.TEXT_HTML)
    public String clickToEdit() {
        return clickToEditView();
    }

    @GET
    @Path("/click-to-edit/form")
    @Produces(MediaType.TEXT_HTML)
    public String clickToEditEdit() {
        return clickToEditForm();
    }

    @POST
    @Path("/click-to-edit")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public String clickToEditSave(@FormParam("name") String name, @FormParam("title") String title) {
        if (name != null && !name.trim().isEmpty()) ceName = name.trim();
        if (title != null && !title.trim().isEmpty()) ceTitle = title.trim();
        return clickToEditView();
    }

    // ---------- Sortable / filterable / paginated data table ----------

    private static final List<String[]> DT_ROWS = List.of(
        new String[]{"Alice Chen",      "Admin",  "us-east-1",      "Active"},
        new String[]{"Bob Johnson",     "Viewer", "eu-west-1",      "Active"},
        new String[]{"Carla Mendez",    "Editor", "us-west-2",      "Suspended"},
        new String[]{"Diego Ramos",     "Viewer", "ap-southeast-1", "Active"},
        new String[]{"Ewa Kowalski",    "Admin",  "eu-central-1",   "Active"},
        new String[]{"Fatima Noor",     "Editor", "me-south-1",     "Pending"},
        new String[]{"Grace Park",      "Viewer", "ap-northeast-1", "Active"},
        new String[]{"Hassan Ali",      "Admin",  "af-south-1",     "Suspended"},
        new String[]{"Ingrid Olsen",    "Editor", "eu-north-1",     "Active"},
        new String[]{"Jamal Wright",    "Viewer", "us-east-2",      "Pending"},
        new String[]{"Keiko Tanaka",    "Admin",  "ap-northeast-1", "Active"},
        new String[]{"Liam Murphy",     "Editor", "eu-west-2",      "Active"},
        new String[]{"Maria Silva",     "Viewer", "sa-east-1",      "Suspended"}
    );

    private static final List<String[]> DT_COLUMNS = List.of(
        new String[]{"name",   "Name"},
        new String[]{"role",   "Role"},
        new String[]{"region", "Region"},
        new String[]{"status", "Status"}
    );

    private static final int DT_PAGE_SIZE = 5;

    @GET
    @Path("/data-table")
    @Produces(MediaType.TEXT_HTML)
    public String dataTable(@QueryParam("q") String q, @QueryParam("col") String col,
                            @QueryParam("dir") String dir, @QueryParam("page") Integer page) {
        String query = q == null ? "" : q.trim().toLowerCase();
        int colIdx = 0;
        for (int i = 0; i < DT_COLUMNS.size(); i++) {
            if (DT_COLUMNS.get(i)[0].equals(col)) { colIdx = i; break; }
        }
        boolean ascending = !"desc".equalsIgnoreCase(dir);

        List<String[]> rows = new ArrayList<>();
        for (String[] r : DT_ROWS) {
            boolean match = query.isEmpty();
            for (String cell : r) {
                if (cell.toLowerCase().contains(query)) { match = true; break; }
            }
            if (match) rows.add(r);
        }
        final int sortIdx = colIdx;
        Comparator<String[]> cmp = Comparator.comparing(r -> r[sortIdx], String.CASE_INSENSITIVE_ORDER);
        if (!ascending) cmp = cmp.reversed();
        rows.sort(cmp);

        int total = rows.size();
        int totalPages = Math.max(1, (int) Math.ceil(total / (double) DT_PAGE_SIZE));
        int p = page == null ? 1 : Math.min(Math.max(1, page), totalPages);
        int from = (p - 1) * DT_PAGE_SIZE;
        int to = Math.min(from + DT_PAGE_SIZE, total);
        List<String[]> pageRows = rows.subList(from, to);

        String curCol = DT_COLUMNS.get(colIdx)[0];
        String curDir = ascending ? "asc" : "desc";
        String qEnc = escapeHtml(query);

        StringBuilder html = new StringBuilder();
        html.append("<div id=\"htmx-dt-results\">");
        html.append("<p class=\"pf-v6-c-content--small\" style=\"margin-bottom: 0.5rem\" aria-live=\"polite\">");
        if (total == 0) {
            html.append("No matching users.");
        } else {
            html.append("Showing ").append(from + 1).append("–").append(to).append(" of ").append(total).append(" users");
        }
        html.append("</p>");

        html.append("<table class=\"pf-v6-c-table\" role=\"grid\" aria-label=\"Users\">");
        html.append("<thead><tr>");
        for (int i = 0; i < DT_COLUMNS.size(); i++) {
            String key = DT_COLUMNS.get(i)[0];
            String label = DT_COLUMNS.get(i)[1];
            boolean active = i == colIdx;
            String ariaSort = active ? (ascending ? "ascending" : "descending") : "none";
            String nextDir = active && ascending ? "desc" : "asc";
            String icon = active ? (ascending ? "fas fa-long-arrow-alt-up" : "fas fa-long-arrow-alt-down") : "fas fa-arrows-alt-v";
            html.append("<th scope=\"col\" class=\"pf-v6-c-table__sort").append(active ? " pf-m-selected" : "").append("\" aria-sort=\"").append(ariaSort).append("\">");
            html.append("<button type=\"button\" class=\"pf-v6-c-table__button\"");
            html.append(" hx-get=\"/api/htmx/data-table?col=").append(key).append("&amp;dir=").append(nextDir)
                .append("&amp;q=").append(qEnc).append("&amp;page=").append(p).append("\"");
            html.append(" hx-target=\"#htmx-dt-results\" hx-swap=\"outerHTML\">");
            html.append("<div class=\"pf-v6-c-table__button-content\">");
            html.append("<span class=\"pf-v6-c-table__text\">").append(escapeHtml(label)).append("</span>");
            html.append("<span class=\"pf-v6-c-table__sort-indicator\"><i class=\"").append(icon).append("\" aria-hidden=\"true\"></i></span>");
            html.append("</div></button></th>");
        }
        html.append("</tr></thead><tbody>");
        if (pageRows.isEmpty()) {
            html.append("<tr><td colspan=\"").append(DT_COLUMNS.size()).append("\" style=\"text-align: center; padding: 1.5rem\">No matching users.</td></tr>");
        }
        for (String[] row : pageRows) {
            html.append("<tr>");
            for (String cell : row) html.append("<td>").append(escapeHtml(cell)).append("</td>");
            html.append("</tr>");
        }
        html.append("</tbody></table>");

        if (totalPages > 1) {
            html.append("<nav class=\"pf-v6-c-pagination pf-m-bottom\" aria-label=\"Table pagination\" style=\"margin-top: 0.5rem\">");
            html.append("<div class=\"pf-v6-c-pagination__total-items\"><b>").append(from + 1).append("–").append(to).append("</b> of <b>").append(total).append("</b></div>");
            html.append("<div class=\"pf-v6-c-pagination__nav\" aria-label=\"Pagination\">");
            html.append("<div class=\"pf-v6-c-pagination__nav-control pf-m-prev\">");
            html.append("<button class=\"pf-v6-c-button pf-m-plain\" type=\"button\" aria-label=\"Go to previous page\"").append(p <= 1 ? " disabled" : "");
            if (p > 1) html.append(" hx-get=\"/api/htmx/data-table?col=").append(curCol).append("&amp;dir=").append(curDir).append("&amp;q=").append(qEnc).append("&amp;page=").append(p - 1).append("\" hx-target=\"#htmx-dt-results\" hx-swap=\"outerHTML\"");
            html.append("><span class=\"pf-v6-c-button__icon\"><i class=\"fas fa-angle-left\" aria-hidden=\"true\"></i></span></button></div>");
            html.append("<div class=\"pf-v6-c-pagination__nav-page-select\">Page <b>").append(p).append("</b> of <b>").append(totalPages).append("</b></div>");
            html.append("<div class=\"pf-v6-c-pagination__nav-control pf-m-next\">");
            html.append("<button class=\"pf-v6-c-button pf-m-plain\" type=\"button\" aria-label=\"Go to next page\"").append(p >= totalPages ? " disabled" : "");
            if (p < totalPages) html.append(" hx-get=\"/api/htmx/data-table?col=").append(curCol).append("&amp;dir=").append(curDir).append("&amp;q=").append(qEnc).append("&amp;page=").append(p + 1).append("\" hx-target=\"#htmx-dt-results\" hx-swap=\"outerHTML\"");
            html.append("><span class=\"pf-v6-c-button__icon\"><i class=\"fas fa-angle-right\" aria-hidden=\"true\"></i></span></button></div>");
            html.append("</div></nav>");
        }

        html.append("</div>");
        return html.toString();
    }

    // ---------- Wizard step rendering ----------

    private static final Map<String, Integer> WIZARD_TOTAL_STEPS = Map.of(
        "basic", 4,
        "substeps", 4,
        "review", 3
    );

    @GET
    @Path("/wizard/{flavor}/step/{n}")
    @Produces(MediaType.TEXT_HTML)
    public String wizardStep(@PathParam("flavor") String flavor, @PathParam("n") int step) {
        Integer total = WIZARD_TOTAL_STEPS.get(flavor);
        if (total == null) {
            throw new NotFoundException("Unknown wizard flavor: " + flavor);
        }
        if (step < 1 || step > total) {
            throw new NotFoundException("Step out of range: " + step);
        }
        return switch (flavor) {
            case "basic" -> renderBasicWizard(step);
            case "substeps" -> renderSubstepsWizard(step);
            case "review" -> renderReviewWizard(step);
            default -> throw new NotFoundException("Unknown wizard flavor: " + flavor);
        };
    }

    private static String wizardShell(String id, String navLabel, String navHtml, String bodyHtml, String footerHtml) {
        return "<div class=\"pf-v6-c-wizard\" id=\"" + id + "\">"
             + "<div class=\"pf-v6-c-wizard__outer-wrap\">"
             + "<div class=\"pf-v6-c-wizard__inner-wrap\">"
             + "<nav class=\"pf-v6-c-wizard__nav\" aria-label=\"" + escapeHtml(navLabel) + "\">"
             + "<ol class=\"pf-v6-c-wizard__nav-list\">" + navHtml + "</ol>"
             + "</nav>"
             + "<main class=\"pf-v6-c-wizard__main\">"
             + "<div class=\"pf-v6-c-wizard__main-body\">" + bodyHtml + "</div>"
             + "</main>"
             + "</div>"
             + "<footer class=\"pf-v6-c-wizard__footer\">" + footerHtml + "</footer>"
             + "</div></div>";
    }

    /** Build one flat nav item. state: 0=current, 1=visited (clickable), 2=future (disabled). */
    private static String navItem(String id, String label, int state, String url) {
        StringBuilder sb = new StringBuilder("<li class=\"pf-v6-c-wizard__nav-item\">");
        sb.append("<button type=\"button\" class=\"pf-v6-c-wizard__nav-link");
        if (state == 0) sb.append(" pf-m-current");
        sb.append("\"");
        if (state == 0) sb.append(" aria-current=\"step\"");
        if (state == 2) {
            sb.append(" disabled");
        } else {
            sb.append(" hx-get=\"").append(url).append("\" hx-target=\"#").append(id)
              .append("\" hx-swap=\"outerHTML\"");
        }
        sb.append("><span class=\"pf-v6-c-wizard__nav-link-main\"><span class=\"pf-v6-c-wizard__nav-link-text\">")
          .append(escapeHtml(label))
          .append("</span></span></button></li>");
        return sb.toString();
    }

    private static String footerButtons(String id, String flavor, int step, int total, boolean finishLabel) {
        StringBuilder sb = new StringBuilder();
        String primaryLabel = finishLabel && step == total ? "Finish" : "Next";
        // PF v6 wizard footer: action-list with Back/Next in one group, Cancel in its own
        sb.append("<div class=\"pf-v6-c-action-list\">")
          .append("<div class=\"pf-v6-c-action-list__group\">")
          .append("<div class=\"pf-v6-c-action-list__item\">");
        if (step > 1) {
            sb.append("<button type=\"button\" class=\"pf-v6-c-button pf-m-secondary\"")
              .append(" hx-get=\"/api/htmx/wizard/").append(flavor).append("/step/").append(step - 1).append("\"")
              .append(" hx-target=\"#").append(id).append("\" hx-swap=\"outerHTML\">")
              .append("<span class=\"pf-v6-c-button__text\">Back</span></button>");
        } else {
            sb.append("<button type=\"button\" class=\"pf-v6-c-button pf-m-secondary\" disabled>")
              .append("<span class=\"pf-v6-c-button__text\">Back</span></button>");
        }
        sb.append("</div><div class=\"pf-v6-c-action-list__item\">");
        if (step < total) {
            sb.append("<button type=\"button\" class=\"pf-v6-c-button pf-m-primary\"")
              .append(" hx-get=\"/api/htmx/wizard/").append(flavor).append("/step/").append(step + 1).append("\"")
              .append(" hx-target=\"#").append(id).append("\" hx-swap=\"outerHTML\">")
              .append("<span class=\"pf-v6-c-button__text\">").append(primaryLabel).append("</span></button>");
        } else {
            sb.append("<button type=\"button\" class=\"pf-v6-c-button pf-m-primary\">")
              .append("<span class=\"pf-v6-c-button__text\">Finish</span></button>");
        }
        sb.append("</div></div>")
          .append("<div class=\"pf-v6-c-action-list__group\">")
          .append("<div class=\"pf-v6-c-action-list__item\">")
          .append("<button type=\"button\" class=\"pf-v6-c-button pf-m-link\">")
          .append("<span class=\"pf-v6-c-button__text\">Cancel</span></button>")
          .append("</div></div></div>");
        return sb.toString();
    }

    // -- basic flavor --
    private static final List<String> BASIC_LABELS =
        List.of("General info", "Connection details", "Permissions", "Review");

    private static String renderBasicWizard(int step) {
        String id = "wiz-basic";
        StringBuilder nav = new StringBuilder();
        for (int i = 1; i <= BASIC_LABELS.size(); i++) {
            int state = i == step ? 0 : (i < step ? 1 : 2);
            nav.append(navItem(id, BASIC_LABELS.get(i - 1), state, "/api/htmx/wizard/basic/step/" + i));
        }
        String body = switch (step) {
            case 1 -> basicStep1Body();
            case 2 -> basicStep2Body();
            case 3 -> basicStep3Body();
            case 4 -> basicStep4Body();
            default -> "<p>Unknown step.</p>";
        };
        return wizardShell(id, "Cluster setup steps", nav.toString(), body,
            footerButtons(id, "basic", step, BASIC_LABELS.size(), true));
    }

    private static String basicStep1Body() {
        return "<h2 class=\"pf-v6-c-content--h2\">General info</h2>"
             + "<p>Tell us about the cluster you're creating.</p>"
             + "<form class=\"pf-v6-c-form\">"
             + formGroup("wiz-basic-name", "Name", "production-east")
             + formGroup("wiz-basic-region", "Region", "us-east-1")
             + "</form>";
    }

    private static String basicStep2Body() {
        return "<h2 class=\"pf-v6-c-content--h2\">Connection details</h2>"
             + "<p>Where should the cluster live on the network?</p>"
             + "<form class=\"pf-v6-c-form\">"
             + formGroup("wiz-basic-vpc", "VPC", "vpc-prod-shared")
             + formGroup("wiz-basic-subnet", "Subnet CIDR", "10.42.0.0/16")
             + "</form>";
    }

    private static String basicStep3Body() {
        return "<h2 class=\"pf-v6-c-content--h2\">Permissions</h2>"
             + "<p>Pick the IAM role the cluster should assume.</p>"
             + "<form class=\"pf-v6-c-form\">"
             + formGroup("wiz-basic-role", "IAM role", "ClusterAdminRole")
             + "</form>";
    }

    private static String basicStep4Body() {
        return "<h2 class=\"pf-v6-c-content--h2\">Review</h2>"
             + "<p>Confirm your settings, then click Finish to create the cluster.</p>"
             + "<dl class=\"pf-v6-c-description-list\">"
             + dlRow("Name", "production-east")
             + dlRow("Region", "us-east-1")
             + dlRow("VPC", "vpc-prod-shared")
             + dlRow("Subnet CIDR", "10.42.0.0/16")
             + dlRow("IAM role", "ClusterAdminRole")
             + "</dl>";
    }

    // -- substeps flavor --
    private static String renderSubstepsWizard(int step) {
        String id = "wiz-substeps";
        // Nav: 3 top-level items; "Build" has 2 children. Step 1 = Source,
        // step 2 = Compile, step 3 = Test, step 4 = Deploy.
        StringBuilder nav = new StringBuilder();
        // Step 1: Source
        nav.append(navItem(id, "Source", stateFor(step, 1, 1), "/api/htmx/wizard/substeps/step/1"));
        // Step 2 parent: Build (current when on substeps; visited after)
        int buildState;
        boolean buildExpanded = step >= 2;
        if (step >= 2 && step <= 3) buildState = 0; // parent "current" while in substeps
        else if (step > 3) buildState = 1;
        else buildState = 2;
        nav.append("<li class=\"pf-v6-c-wizard__nav-item\">");
        nav.append("<button type=\"button\" class=\"pf-v6-c-wizard__nav-link");
        if (buildState == 0) nav.append(" pf-m-current");
        nav.append("\"");
        if (buildState == 0) nav.append(" aria-current=\"step\"");
        nav.append(" aria-expanded=\"").append(buildExpanded ? "true" : "false").append("\"");
        if (buildState == 2) nav.append(" disabled");
        nav.append("><span class=\"pf-v6-c-wizard__nav-link-main\"><span class=\"pf-v6-c-wizard__nav-link-text\">Build</span></span></button>");
        nav.append("<ol class=\"pf-v6-c-wizard__nav-list\">");
        nav.append(navItem(id, "Compile", stateFor(step, 2, 1), "/api/htmx/wizard/substeps/step/2"));
        nav.append(navItem(id, "Test", stateFor(step, 3, 2), "/api/htmx/wizard/substeps/step/3"));
        nav.append("</ol></li>");
        // Step 4: Deploy
        nav.append(navItem(id, "Deploy", stateFor(step, 4, 3), "/api/htmx/wizard/substeps/step/4"));

        String body = switch (step) {
            case 1 -> "<h2 class=\"pf-v6-c-content--h2\">Source</h2>"
                + "<p>Choose where the pipeline pulls code from.</p>"
                + "<form class=\"pf-v6-c-form\">"
                + formGroup("wiz-substeps-repo", "Repository URL", "github.com/example/web")
                + formGroup("wiz-substeps-branch", "Branch", "main")
                + "</form>";
            case 2 -> "<h2 class=\"pf-v6-c-content--h2\">Compile</h2>"
                + "<p>Build command and Docker base image for the compile stage.</p>"
                + "<form class=\"pf-v6-c-form\">"
                + formGroup("wiz-substeps-cmd", "Build command", "npm ci &amp;&amp; npm run build")
                + formGroup("wiz-substeps-image", "Base image", "node:22-alpine")
                + "</form>";
            case 3 -> "<h2 class=\"pf-v6-c-content--h2\">Test</h2>"
                + "<p>How to run the suite and what counts as success.</p>"
                + "<form class=\"pf-v6-c-form\">"
                + formGroup("wiz-substeps-test", "Test command", "npm test")
                + formGroup("wiz-substeps-cov", "Min coverage", "80%")
                + "</form>";
            case 4 -> "<h2 class=\"pf-v6-c-content--h2\">Deploy</h2>"
                + "<p>Target environment for the deploy step.</p>"
                + "<form class=\"pf-v6-c-form\">"
                + formGroup("wiz-substeps-env", "Environment", "production")
                + formGroup("wiz-substeps-strategy", "Strategy", "blue-green")
                + "</form>";
            default -> "<p>Unknown step.</p>";
        };
        return wizardShell(id, "Pipeline setup steps", nav.toString(), body,
            footerButtons(id, "substeps", step, 4, true));
    }

    /** Step-by-step state helper used by the substeps flavor. */
    private static int stateFor(int currentStep, int itemStep, int orderRelativeToBuild) {
        // Unused param kept for readability of caller; actual logic is straightforward.
        if (itemStep == currentStep) return 0;
        if (itemStep < currentStep) return 1;
        return 2;
    }

    // -- review flavor --
    private static final List<String> REVIEW_LABELS = List.of("Project details", "Team", "Review");

    private static String renderReviewWizard(int step) {
        String id = "wiz-review";
        StringBuilder nav = new StringBuilder();
        for (int i = 1; i <= REVIEW_LABELS.size(); i++) {
            int state = i == step ? 0 : (i < step ? 1 : 2);
            nav.append(navItem(id, REVIEW_LABELS.get(i - 1), state, "/api/htmx/wizard/review/step/" + i));
        }
        String body = switch (step) {
            case 1 -> "<h2 class=\"pf-v6-c-content--h2\">Project details</h2>"
                + "<p>Name the project and pick a visibility.</p>"
                + "<form class=\"pf-v6-c-form\">"
                + formGroup("wiz-review-name", "Project name", "Atlas migration")
                + selectGroup("wiz-review-visibility", "Visibility",
                    List.of(new String[]{"private", "Private"}, new String[]{"internal", "Internal"}, new String[]{"public", "Public"}),
                    "private")
                + "</form>";
            case 2 -> "<h2 class=\"pf-v6-c-content--h2\">Team</h2>"
                + "<p>Who should have access to this project?</p>"
                + "<form class=\"pf-v6-c-form\">"
                + formGroup("wiz-review-owner", "Owner", "alice@example.com")
                + formGroup("wiz-review-reviewers", "Reviewers (comma-separated)", "bob, carla, diego")
                + "</form>";
            case 3 -> "<h2 class=\"pf-v6-c-content--h2\">Review</h2>"
                + "<p>Confirm the project before creating it.</p>"
                + "<dl class=\"pf-v6-c-description-list\">"
                + dlRow("Project name", "Atlas migration")
                + dlRow("Visibility", "Private")
                + dlRow("Owner", "alice@example.com")
                + dlRow("Reviewers", "bob, carla, diego")
                + "</dl>";
            default -> "<p>Unknown step.</p>";
        };
        return wizardShell(id, "New project steps", nav.toString(), body,
            footerButtons(id, "review", step, REVIEW_LABELS.size(), true));
    }

    private static String formGroup(String id, String label, String value) {
        return "<div class=\"pf-v6-c-form__group\">"
             + "<div class=\"pf-v6-c-form__group-label\">"
             + "<label class=\"pf-v6-c-form__label\" for=\"" + id + "\">"
             + "<span class=\"pf-v6-c-form__label-text\">" + escapeHtml(label) + "</span></label></div>"
             + "<div class=\"pf-v6-c-form__group-control\">"
             + "<input class=\"pf-v6-c-form-control\" type=\"text\" id=\"" + id + "\" value=\"" + value + "\" />"
             + "</div></div>";
    }

    private static String selectGroup(String id, String label, List<String[]> options, String selected) {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"pf-v6-c-form__group\">")
          .append("<div class=\"pf-v6-c-form__group-label\">")
          .append("<label class=\"pf-v6-c-form__label\" for=\"").append(id).append("\">")
          .append("<span class=\"pf-v6-c-form__label-text\">").append(escapeHtml(label)).append("</span></label></div>")
          .append("<div class=\"pf-v6-c-form__group-control\">")
          .append("<select class=\"pf-v6-c-form-control\" id=\"").append(id).append("\">");
        for (String[] o : options) {
            sb.append("<option value=\"").append(o[0]).append("\"");
            if (o[0].equals(selected)) sb.append(" selected");
            sb.append(">").append(escapeHtml(o[1])).append("</option>");
        }
        sb.append("</select></div></div>");
        return sb.toString();
    }

    private static String dlRow(String term, String desc) {
        return "<div class=\"pf-v6-c-description-list__group\">"
             + "<dt class=\"pf-v6-c-description-list__term\">"
             + "<span class=\"pf-v6-c-description-list__text\">" + escapeHtml(term) + "</span></dt>"
             + "<dd class=\"pf-v6-c-description-list__description\">"
             + "<div class=\"pf-v6-c-description-list__text\">" + escapeHtml(desc) + "</div></dd></div>";
    }

    private static String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;");
    }

    // ─────────────────────────────────────────────────────────────────────
    // Drag-and-drop persist (components/drag-and-drop/data-list). The Alpine
    // phaDragDrop factory POSTs the new order (zone + comma-joined ids) on
    // every reorder. The server is the source of truth for the sequence and
    // echoes the resolved labels back for the #dd-persist-status swap.
    // In-memory only — fine for a demo.
    // ─────────────────────────────────────────────────────────────────────

    private static final Map<String, String> DD_LABELS = Map.of(
        "primary", "Primary nav",
        "masthead", "Masthead",
        "sidebar", "Sidebar",
        "footer", "Footer"
    );

    private volatile String dragDropOrder = "Primary nav, Masthead, Sidebar, Footer";

    @POST
    @Path("/drag-drop-reorder")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_HTML)
    public String dragDropReorder(@FormParam("zone") String zone, @FormParam("order") String order) {
        String ord = order == null ? "" : order.trim();
        StringBuilder labels = new StringBuilder();
        for (String id : ord.split(",")) {
            String key = id.trim();
            if (key.isEmpty()) continue;
            if (labels.length() > 0) labels.append(", ");
            labels.append(DD_LABELS.getOrDefault(key, key));
        }
        dragDropOrder = labels.length() == 0 ? "(empty)" : labels.toString();
        return escapeHtml(dragDropOrder);
    }

    // ─────────────────────────────────────────────────────────────────────
    // SSE: live log stream for the log-viewer/streaming demo.
    //
    // Each tick becomes a named `message` SSE event whose data is the
    // fully-rendered HTML chunk that HTMX's sse-swap="message" appends to
    // the target.
    //
    // Bounded duration (15 ticks at 1.2s = ~18s) so an idle browser tab
    // doesn't leak a server-side timer indefinitely. When the bounded
    // stream ends we emit one final `close` event: the client's
    // sse-close="close" tears the EventSource down cleanly. Without it, the
    // browser treats the server-side close as an error — logging
    // `console.error([object Event])` and reconnecting forever, restarting
    // the whole 18s cycle on a loop.
    // ─────────────────────────────────────────────────────────────────────

    private static final String[] LOG_LEVELS = { "INFO", "DEBUG", "DEBUG", "INFO", "WARN", "DEBUG", "INFO", "DEBUG" };
    private static final String[] LOG_MESSAGES = {
        "Health check OK",
        "GET /api/users 200 8ms",
        "Cache hit ratio 0.94 (last 60s)",
        "Scheduler tick",
        "Slow query: SELECT * FROM audit_log (612ms)",
        "GET /api/orders 200 14ms",
        "User signed in from 10.0.4.21",
        "POST /api/projects 201 22ms"
    };

    @GET
    @Path("/log-stream")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<OutboundSseEvent> logStream(@Context Sse sse) {
        return Multi.createFrom().ticks().every(Duration.ofMillis(1200))
            .select().first(15)
            .map(tick -> {
                int i = (int) (tick % LOG_MESSAGES.length);
                String ts = LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
                String level = LOG_LEVELS[i];
                String body = LOG_MESSAGES[i];
                String line = ts + " " + level + "  " + body;
                // Pre-rendered span; HTMX sse-swap appends it to the __list.
                String html = "<span class=\"pf-v6-c-log-viewer__list-item\" role=\"listitem\">"
                            + "<span class=\"pf-v6-c-log-viewer__text\">" + escapeHtml(line) + "</span>"
                            + "</span>";
                return sse.newEventBuilder().name("message").mediaType(MediaType.TEXT_HTML_TYPE).data(html).build();
            })
            // Terminal event: sse-close="close" on the client closes the
            // EventSource cleanly instead of erroring + reconnecting.
            .onCompletion().continueWith(sse.newEventBuilder().name("close").data("").build());
    }

    // ─────────────────────────────────────────────────────────────────────
    // Progress-button login demo (components/button/progress-login). STUB:
    // no credentials are checked, no session is created. The short delay just
    // makes the button's in-progress state visible before the success swap.
    // ─────────────────────────────────────────────────────────────────────

    @Inject
    Engine engine;

    @POST
    @Path("/button/login")
    @Produces(MediaType.TEXT_HTML)
    public String login() {
        try {
            Thread.sleep(1200); // simulate a round-trip so progress is visible
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return "<div id=\"login-action\" class=\"pf-v6-l-stack pf-m-gutter\">"
             + "<div class=\"pf-v6-l-stack__item\">"
             + "<div class=\"pf-v6-c-helper-text\">"
             + "<div class=\"pf-v6-c-helper-text__item pf-m-success\">"
             + "<span class=\"pf-v6-c-helper-text__item-icon\">"
             + "<svg class=\"pf-v6-svg\" viewBox=\"0 0 448 512\" fill=\"currentColor\" width=\"1em\" height=\"1em\" aria-hidden=\"true\" role=\"img\">"
             + "<path d=\"M438.6 105.4c12.5 12.5 12.5 32.8 0 45.3l-256 256c-12.5 12.5-32.8 12.5-45.3 0l-128-128c-12.5-12.5-12.5-32.8 0-45.3s32.8-12.5 45.3 0L160 338.7 393.4 105.4c12.5-12.5 32.8-12.5 45.3 0z\"/>"
             + "</svg></span>"
             + "<span class=\"pf-v6-c-helper-text__item-text\">Logged in as <b>redhat</b>. Stub demo &mdash; no real authentication happened.</span>"
             + "</div></div></div>"
             + "<div class=\"pf-v6-l-stack__item\">"
             + "<button class=\"pf-v6-c-button pf-m-secondary\" type=\"button\""
             + " hx-get=\"/api/htmx/button/login-reset\" hx-target=\"#login-action\" hx-swap=\"outerHTML\">"
             + "<span class=\"pf-v6-c-button__text\">Reset demo</span></button>"
             + "</div></div>";
    }

    @GET
    @Path("/button/login-reset")
    @Produces(MediaType.TEXT_HTML)
    public String loginReset() {
        return engine.getTemplate("components/button/_login-action").instance().render();
    }
}
