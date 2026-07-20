import org.sitenetsoft.quarkus.pha.model.*;

Button button = Button.of("click here to learn more")
        .id("btn-inline-span").variant("link").asInline().asSpan().build();

// Template side, with the data in scope:
// {#include components/actions/button btn=button /}
