import org.sitenetsoft.quarkus.pha.model.*;

ItemList itemList = ItemList.ul().id("list-icons-lg").plain().iconLg()
        .item("fa:server", """
                <div>
                  <strong>Servers</strong>
                  <div>142 running across 3 regions.</div>
                </div>""")
        .item("fa:database", """
                <div>
                  <strong>Databases</strong>
                  <div>28 instances, all healthy.</div>
                </div>""")
        .item("fa:cloud", """
                <div>
                  <strong>Storage</strong>
                  <div>4.8 TB used of 10 TB allocated.</div>
                </div>""").build();

// Template side, with the data in scope:
// {#include components/data-display/list itemList=itemList /}
