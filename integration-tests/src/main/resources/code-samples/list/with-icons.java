import org.sitenetsoft.quarkus.pha.model.*;

ItemList itemList = ItemList.ul().id("list-icons").plain()
        .item("fa:circle-check", "Step one complete")
        .item("fa:circle-check", "Step two complete")
        .item("fa:clock", "Step three in progress")
        .item("fa:circle-exclamation", "Step four needs attention").build();

// Template side, with the data in scope:
// {#include components/data-display/list itemList=itemList /}
