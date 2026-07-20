import org.sitenetsoft.quarkus.pha.model.*;

Icon iconLabeled = Icon.of("fa:trash").size("lg").label("Delete item").build();

Icon iconDecorative = Icon.of("fa:trash").size("lg").build();

// Template side, with the data in scope:
// {#include components/icon iconModel=icon... /} (one include per icon)
