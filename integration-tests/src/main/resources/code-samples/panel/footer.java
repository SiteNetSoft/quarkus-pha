import org.sitenetsoft.quarkus.pha.model.*;

Panel panel = Panel.of("panel-footer")
        .body("Main body sits above the footer. The footer gets PF's automatic top border.")
        .footer("Footer content — typically actions or summary text.")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/panel panel=panel /}
