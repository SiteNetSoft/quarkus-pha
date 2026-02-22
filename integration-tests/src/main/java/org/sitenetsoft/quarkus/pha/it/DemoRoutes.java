package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.List;
import java.util.Map;

@Path("/demos")
public class DemoRoutes {

    @Location("demos/dashboard")
    @Inject
    Template dashboardPage;

    @Location("demos/data-management")
    @Inject
    Template dataManagementPage;

    @Location("demos/settings")
    @Inject
    Template settingsPage;

    @Location("demos/landing")
    @Inject
    Template landingPage;

    @GET
    @Path("/dashboard")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance dashboard() {
        List<Map<String, Object>> stats = List.of(
            Map.of("title", "Total servers", "value", "142", "icon", "fas fa-server"),
            Map.of("title", "Active services", "value", "87", "icon", "fas fa-cogs"),
            Map.of("title", "Uptime", "value", "99.97%", "icon", "fas fa-check-circle"),
            Map.of("title", "Open alerts", "value", "3", "icon", "fas fa-bell")
        );

        List<Map<String, String>> recentActivity = List.of(
            Map.of("time", "2 min ago", "event", "Server Alpha restarted", "status", "Success"),
            Map.of("time", "15 min ago", "event", "Deployment to production", "status", "Success"),
            Map.of("time", "1 hour ago", "event", "Database backup completed", "status", "Success"),
            Map.of("time", "2 hours ago", "event", "SSL certificate renewed", "status", "Info"),
            Map.of("time", "5 hours ago", "event", "High CPU alert triggered", "status", "Warning")
        );

        return dashboardPage.data("applicationName", "quarkus-pha")
            .data("stats", stats)
            .data("recentActivity", recentActivity);
    }

    @GET
    @Path("/data-management")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance dataManagement() {
        return dataManagementPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/settings")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance settings() {
        return settingsPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/landing")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance landing() {
        List<Map<String, String>> features = List.of(
            Map.of("title", "Server-Rendered Components", "description", "Pure HTML fragments rendered via Qute — no virtual DOM, no JS framework.", "icon", "fas fa-code"),
            Map.of("title", "PatternFly v6 Design System", "description", "Enterprise-grade design with consistent tokens, spacing, and accessibility.", "icon", "fas fa-palette"),
            Map.of("title", "HTMX Server-Driven UI", "description", "Ajax, partial page updates, and server interactions with no client-side routing.", "icon", "fas fa-exchange-alt"),
            Map.of("title", "Alpine.js Reactivity", "description", "Lightweight reactivity via HTML attributes — no build step required.", "icon", "fas fa-bolt"),
            Map.of("title", "Charts & Maps", "description", "Data visualization with ECharts and map components with MapLibre GL JS.", "icon", "fas fa-chart-bar"),
            Map.of("title", "Quarkus Native", "description", "Built as a Quarkus extension — any Quarkus app can consume these components.", "icon", "fas fa-rocket")
        );

        return landingPage.data("applicationName", "quarkus-pha")
            .data("features", features);
    }
}
