import org.sitenetsoft.quarkus.pha.model.*;

FilterSidePanel filterSidePanel = FilterSidePanel.of("fsp-demo")
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

// Template side, with the data in scope:
// {#include components/extensions/filter-side-panel filterSidePanel=filterSidePanel /}
