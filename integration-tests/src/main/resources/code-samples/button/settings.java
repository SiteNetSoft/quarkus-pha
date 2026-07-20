import org.sitenetsoft.quarkus.pha.model.*;

Button button = Button.settings("Settings").id("btn-settings").build();

// Template side, with the data in scope:
// {#include components/actions/button btn=button /}
