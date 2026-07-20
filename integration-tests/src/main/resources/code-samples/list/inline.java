import org.sitenetsoft.quarkus.pha.model.*;

ItemList itemList = ItemList.ul().id("list-inline").inline()
        .item("Home").item("Products").item("Pricing").item("About").item("Contact").build();

// Template side, with the data in scope:
// {#include components/data-display/list itemList=itemList /}
