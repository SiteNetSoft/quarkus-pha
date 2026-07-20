import org.sitenetsoft.quarkus.pha.model.*;

Icon iconInlineCheck = Icon.of("fa:circle-check").inline().label("check").build();

Icon iconInlineWarn = Icon.of("fa:triangle-exclamation").inline().label("warning").build();

// Template side, with the data in scope:
// {#include components/icon iconModel=icon... /} (one include per icon)
