import org.sitenetsoft.quarkus.pha.model.*;

Label label = Label.of("Edit me").id("lbl-editable").color("blue").asEditable();

// Template side, with the data in scope:
// {#include components/data-display/label label=label /}
