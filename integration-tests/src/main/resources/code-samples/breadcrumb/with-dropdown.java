import org.sitenetsoft.quarkus.pha.model.*;

Breadcrumb breadcrumb = Breadcrumb.of("breadcrumb-dropdown")
        .item(BreadcrumbItem.of("Section home").href("#"))
        .item(BreadcrumbItem.of("Section title").dropdown("bc-dropdown-section")
                .option("Sibling page A", "#")
                .option("Sibling page B", "#")
                .option("Sibling page C", "#"))
        .item(BreadcrumbItem.of("Section landing").href("#").asCurrent())
        .build();

// Template side, with the data in scope:
// {#include components/navigation/breadcrumb breadcrumb=breadcrumb /}
