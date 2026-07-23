import org.sitenetsoft.quarkus.pha.model.*;

PropertiesSidePanel propertiesSidePanel = PropertiesSidePanel.of("psp-demo")
            .property("Version", "1.0.0-SNAPSHOT")
            .property("Last updated", "2026-05-25")
            .property("License", "Apache 2.0")
            .property("Language", "Java 25")
            .build();

// Template side, with the data in scope:
// {#include components/extensions/properties-side-panel propertiesSidePanel=propertiesSidePanel /}
