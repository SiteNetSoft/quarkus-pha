import org.sitenetsoft.quarkus.pha.model.*;

Panel panel = Panel.of("panel-header-footer")
        .header("<strong>Header</strong>")
        .body("Body sandwiched between header and footer.")
        .footer("Footer")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/panel panel=panel /}
