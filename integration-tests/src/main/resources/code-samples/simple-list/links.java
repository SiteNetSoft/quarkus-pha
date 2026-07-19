import org.sitenetsoft.quarkus.pha.model.*;

SimpleList list = SimpleList.builder()
        .id("slist-links")
        .item(SimpleListItem.link("Linked item 1", "#").asCurrent())
        .item(SimpleListItem.link("Linked item 2", "#"))
        .item(SimpleListItem.link("Linked item 3", "#"))
        .build();

// Template side, with `list` in the template data:
// {#include components/data-display/simple-list list=list /}
