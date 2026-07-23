package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.CatalogItemHeader;
import org.sitenetsoft.quarkus.pha.model.CatalogTile;
import org.sitenetsoft.quarkus.pha.model.FilterSidePanel;
import org.sitenetsoft.quarkus.pha.model.PropertiesSidePanel;
import org.sitenetsoft.quarkus.pha.model.VerticalTabs;

/**
 * Demo data for the catalog-view extension examples — the examples on
 * /extensions/catalog-view/* are populated from these models. Globals so the
 * standalone example routes (which render templates without data) can see
 * them. Each is mirrored by a snippet in resources/code-samples/catalog-view/
 * served on the example card's Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class CatalogViewDemoData {

    // ---- catalog-item-header ----

    public static CatalogItemHeader demoCihBasic = CatalogItemHeader
            .of("Quarkus pha")
            .vendorHtml("SiteNetSoft")
            .iconClass("pf-v6-c-icon fas fa-box")
            .build();

    public static CatalogItemHeader demoCihVendor = CatalogItemHeader
            .of("Quarkus pha").id("cih-vendor")
            .iconClass("pf-v6-c-icon fas fa-box")
            .vendorHtml("provided by <a href=\"https://github.com/SiteNetSoft\" style=\"text-decoration: underline\">SiteNetSoft</a>")
            .build();

    // ---- catalog-tile ----

    public static CatalogTile demoCtQuarkus = CatalogTile
            .of("Quarkus").id("ct-quarkus")
            .vendor("Red Hat")
            .description("Supersonic, subatomic Java framework — Kubernetes-native, GraalVM-friendly, built for the cloud era.")
            .iconClass("pf-v6-c-icon fas fa-bolt")
            .build();

    public static CatalogTile demoCtHtmx = CatalogTile
            .of("HTMX").id("ct-htmx").featured()
            .iconClass("pf-v6-c-icon fas fa-arrow-right-arrow-left")
            .badge(CatalogTile.Badge.icon("pf-v6-c-icon fas fa-certificate", "Certified"))
            .vendor("Big Sky Software")
            .description("High-power tools for HTML — modern UX with minimal JavaScript. Adds AJAX, CSS Transitions, WebSockets and SSE directly to HTML, using attributes.")
            .build();

    public static CatalogTile demoCtAlpine = CatalogTile
            .of("Alpine.js").id("ct-alpine")
            .vendor("Caleb Porzio")
            .description("Your new, lightweight JavaScript framework — Tailwind for JavaScript. 15kB and reactive.")
            .iconClass("pf-v6-c-icon fas fa-mountain")
            .build();

    public static CatalogTile demoCtKubernetes = CatalogTile
            .of("Kubernetes").id("ct-kubernetes")
            .vendor("CNCF")
            .description("Production-grade container orchestration — automated deployment, scaling, and management.")
            .iconClass("pf-v6-c-icon fas fa-network-wired")
            .footer("Available in the Operator catalog")
            .build();

    public static CatalogTile demoCtOpenshift = CatalogTile
            .of("OpenShift").id("ct-openshift").href("#")
            .vendor("Red Hat")
            .description("Enterprise Kubernetes platform — click this tile to open the catalog entry.")
            .iconClass("pf-v6-c-icon fas fa-dharmachakra")
            .build();

    public static CatalogTile demoCtIstio = CatalogTile
            .of("Istio").id("ct-istio")
            .iconClass("pf-v6-c-icon fas fa-diagram-project")
            .badge(CatalogTile.Badge.icon("pf-v6-c-icon fas fa-certificate", "Certified"))
            .badge(CatalogTile.Badge.icon("pf-v6-c-icon fas fa-star", "Featured partner"))
            .vendor("CNCF")
            .description("Service mesh for microservices — traffic management, security, and observability.")
            .build();

    public static CatalogTile demoCtCamel = CatalogTile
            .of("Camel").id("ct-community")
            .iconClass("pf-v6-c-icon fas fa-cubes")
            .badge(CatalogTile.Badge.text("Community"))
            .vendor("Apache")
            .description("Integration framework with hundreds of connectors — route, transform, and mediate between systems.")
            .build();

    public static CatalogTile demoCtQute = CatalogTile
            .of("Qute").id("ct-children").featured()
            .iconClass("pf-v6-c-icon fas fa-feather")
            .badge(CatalogTile.Badge.icon("pf-v6-c-icon fas fa-certificate", "Certified"))
            .vendor("Quarkus")
            .bodyHtml("""
                    This is a long stretch of arbitrary body content that is not truncated, illustrating how a tile
                    can carry richer content than a plain description. Descriptions normally clamp at three lines
                    (one line with a footer); body content placed directly in the card body has no clamp at all and
                    can run as long as it needs to.""")
            .build();

    // ---- filter-side-panel ----

    public static FilterSidePanel demoFspBasic = FilterSidePanel.of("fsp-demo")
            .category(FilterSidePanel.Category.of("Categories")
                    .item(FilterSidePanel.Item.of("Databases").id("fsp-cat-databases").count(42))
                    .item(FilterSidePanel.Item.of("Messaging").id("fsp-cat-messaging").count(18))
                    .item(FilterSidePanel.Item.of("Runtimes").id("fsp-cat-runtimes").count(15).checked())
                    .item(FilterSidePanel.Item.of("Observability").id("fsp-cat-observability").count(9)))
            .category(FilterSidePanel.Category.of("Vendor")
                    .item(FilterSidePanel.Item.of("Red Hat").id("fsp-vendor-rh").count(28))
                    .item(FilterSidePanel.Item.of("Apache").id("fsp-vendor-apache").count(12))
                    .item(FilterSidePanel.Item.of("Community").id("fsp-vendor-community").count(44)))
            .build();

    // ---- properties-side-panel ----

    public static PropertiesSidePanel demoPspBasic = PropertiesSidePanel.of("psp-demo")
            .property("Version", "1.0.0-SNAPSHOT")
            .property("Last updated", "2026-05-25")
            .property("License", "Apache 2.0")
            .property("Language", "Java 25")
            .build();

    // ---- vertical-tabs ----

    public static VerticalTabs demoVtBasic = VerticalTabs.of("vt-demo")
            .tab(VerticalTabs.Tab.of("Overview").id("vt-overview").href("#"))
            .tab(VerticalTabs.Tab.of("Databases").id("vt-databases").href("#").active()
                    .child(VerticalTabs.Tab.of("PostgreSQL").id("vt-postgres").href("#"))
                    .child(VerticalTabs.Tab.of("MySQL").id("vt-mysql").href("#"))
                    .child(VerticalTabs.Tab.of("MongoDB").id("vt-mongo").href("#")))
            .tab(VerticalTabs.Tab.of("Messaging").id("vt-messaging").href("#"))
            .tab(VerticalTabs.Tab.of("Observability").id("vt-observability").href("#"))
            .build();
}
