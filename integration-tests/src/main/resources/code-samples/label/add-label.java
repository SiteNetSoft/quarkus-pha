import org.sitenetsoft.quarkus.pha.model.*;

Label label = Label.of("Add Label").id("lbl-add").asAdd();

// Template side, with the data in scope:
// {#include components/data-display/label label=label /}
