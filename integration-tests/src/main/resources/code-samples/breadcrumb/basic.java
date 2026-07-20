import org.sitenetsoft.quarkus.pha.model.*;

Breadcrumb breadcrumb = Breadcrumb.of("breadcrumb-basic")
        .item(BreadcrumbItem.of("Section home").href("#"))
        .item(BreadcrumbItem.of("Section title").href("#"))
        .item(BreadcrumbItem.of("Section title").href("#"))
        .item(BreadcrumbItem.of("Section landing").href("#").asCurrent())
        .build();

// Template side, with the data in scope:
// {#include components/navigation/breadcrumb breadcrumb=breadcrumb /}
