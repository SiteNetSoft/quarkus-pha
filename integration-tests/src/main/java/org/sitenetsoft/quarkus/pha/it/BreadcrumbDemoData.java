package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Breadcrumb;
import org.sitenetsoft.quarkus.pha.model.BreadcrumbItem;

/**
 * Demo data for the breadcrumb examples — the composed examples on
 * /components/breadcrumb are populated from these Breadcrumb models
 * (auto-generated keeps the template's crumbs= server-data mode). Globals so
 * the standalone example route can see them; each is mirrored by a snippet in
 * resources/code-samples/breadcrumb/ served on the example card's Java tab.
 */
@TemplateGlobal
public class BreadcrumbDemoData {

    public static Breadcrumb demoBcBasic = Breadcrumb.of("breadcrumb-basic")
            .item(BreadcrumbItem.of("Section home").href("#"))
            .item(BreadcrumbItem.of("Section title").href("#"))
            .item(BreadcrumbItem.of("Section title").href("#"))
            .item(BreadcrumbItem.of("Section landing").href("#").asCurrent())
            .build();

    public static Breadcrumb demoBcWithoutHome = Breadcrumb.of("breadcrumb-no-home")
            .item(BreadcrumbItem.of("Section home"))
            .item(BreadcrumbItem.of("Section title").href("#"))
            .item(BreadcrumbItem.of("Section title").href("#"))
            .item(BreadcrumbItem.of("Section landing").href("#").asCurrent())
            .build();

    public static Breadcrumb demoBcWithHeading = Breadcrumb.of("breadcrumb-heading")
            .item(BreadcrumbItem.of("Section home").href("#"))
            .item(BreadcrumbItem.of("Section title").href("#"))
            .item(BreadcrumbItem.of("Section title").href("#"))
            .item(BreadcrumbItem.of("Section landing").href("#").heading("h2"))
            .build();

    public static Breadcrumb demoBcWithDropdown = Breadcrumb.of("breadcrumb-dropdown")
            .item(BreadcrumbItem.of("Section home").href("#"))
            .item(BreadcrumbItem.of("Section title").dropdown("bc-dropdown-section")
                    .option("Sibling page A", "#")
                    .option("Sibling page B", "#")
                    .option("Sibling page C", "#"))
            .item(BreadcrumbItem.of("Section landing").href("#").asCurrent())
            .build();
}
