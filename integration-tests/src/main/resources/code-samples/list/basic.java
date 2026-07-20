import org.sitenetsoft.quarkus.pha.model.*;

ItemList itemList = ItemList.ul().id("list-basic")
        .item("First item").item("Second item").item("Third item").build();

// Template side, with the data in scope:
// {#include components/data-display/list itemList=itemList /}
