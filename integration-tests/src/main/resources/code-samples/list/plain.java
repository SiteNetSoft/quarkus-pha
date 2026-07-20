import org.sitenetsoft.quarkus.pha.model.*;

ItemList itemList = ItemList.ul().id("list-plain").plain()
        .item("No bullets here").item("Just plain text").item("Clean and simple").build();

// Template side, with the data in scope:
// {#include components/data-display/list itemList=itemList /}
