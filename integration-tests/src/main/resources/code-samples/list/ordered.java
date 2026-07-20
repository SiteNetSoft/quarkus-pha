import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<ItemList> lists = List.of(
        ItemList.ol().id("list-ordered-1")
                .item("First step").item("Second step").item("Third step").build(),
        ItemList.ol().id("list-ordered-A").type("A")
                .item("First step").item("Second step").item("Third step").build(),
        ItemList.ol().id("list-ordered-i").type("i")
                .item("First step").item("Second step").item("Third step").build());

// Template side, with the data in scope:
// {#include components/data-display/list itemList=lists.0 /} (one include per list)
