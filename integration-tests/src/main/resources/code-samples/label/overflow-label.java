import org.sitenetsoft.quarkus.pha.model.*;

Label label = Label.of("Overflow").id("lbl-overflow").asOverflow();

// Template side, with the data in scope:
// {#include components/data-display/label label=label /}
