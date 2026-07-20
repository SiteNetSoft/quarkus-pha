import org.sitenetsoft.quarkus.pha.model.*;

ItemList itemList = ItemList.ul().id("list-bordered").plain().bordered()
        .item("Bordered row one").item("Bordered row two")
        .item("Bordered row three").item("Bordered row four").build();

// Template side, with the data in scope:
// {#include components/data-display/list itemList=itemList /}
