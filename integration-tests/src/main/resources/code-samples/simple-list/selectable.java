import org.sitenetsoft.quarkus.pha.model.*;

SimpleList list = SimpleList.builder()
        .id("slist-selectable").selectable("1")
        .item(SimpleListItem.button("Item 1").key("1"))
        .item(SimpleListItem.button("Item 2").key("2"))
        .item(SimpleListItem.button("Item 3").key("3"))
        .build();

// Template side, with `list` in the template data:
// {#include components/data-display/simple-list list=list /}
