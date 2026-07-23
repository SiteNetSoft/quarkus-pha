import org.sitenetsoft.quarkus.pha.model.*;

VerticalTabs verticalTabs = VerticalTabs.of("vt-demo")
            .tab(VerticalTabs.Tab.of("Overview").id("vt-overview").href("#"))
            .tab(VerticalTabs.Tab.of("Databases").id("vt-databases").href("#").active()
                    .child(VerticalTabs.Tab.of("PostgreSQL").id("vt-postgres").href("#"))
                    .child(VerticalTabs.Tab.of("MySQL").id("vt-mysql").href("#"))
                    .child(VerticalTabs.Tab.of("MongoDB").id("vt-mongo").href("#")))
            .tab(VerticalTabs.Tab.of("Messaging").id("vt-messaging").href("#"))
            .tab(VerticalTabs.Tab.of("Observability").id("vt-observability").href("#"))
            .build();

// Template side, with the data in scope:
// {#include components/extensions/vertical-tabs verticalTabs=verticalTabs /}
