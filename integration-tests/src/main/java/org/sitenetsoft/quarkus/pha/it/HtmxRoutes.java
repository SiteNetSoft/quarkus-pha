package org.sitenetsoft.quarkus.pha.it;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

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

        // Append next sentinel if more items exist
        int nextPage = page + 1;
        if (nextPage * PAGE_SIZE < ITEMS.size()) {
            html.append("<div class=\"pha-c-htmx-sentinel\" id=\"scroll-sentinel-").append(nextPage).append("\"");
            html.append(" hx-get=\"/api/htmx/items?page=").append(nextPage).append("\"");
            html.append(" hx-trigger=\"revealed\" hx-swap=\"afterend\"></div>\n");
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

    private static String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;");
    }
}
