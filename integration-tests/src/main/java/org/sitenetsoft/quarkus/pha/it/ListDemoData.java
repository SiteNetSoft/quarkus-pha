package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.ItemList;

import java.util.List;

/**
 * Demo data for the list examples — the examples on /components/list are
 * populated from these models (the model class is ItemList so it coexists
 * with java.util.List under star imports). Globals so the standalone example
 * route (which renders templates without data) can see them. Each is mirrored
 * by a snippet in resources/code-samples/list/ served on the example card's
 * Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class ListDemoData {

    public static ItemList demoListBasic = ItemList.ul().id("list-basic")
            .item("First item").item("Second item").item("Third item").build();

    public static ItemList demoListInline = ItemList.ul().id("list-inline").inline()
            .item("Home").item("Products").item("Pricing").item("About").item("Contact").build();

    public static List<ItemList> demoListsOrdered = List.of(
            ItemList.ol().id("list-ordered-1")
                    .item("First step").item("Second step").item("Third step").build(),
            ItemList.ol().id("list-ordered-A").type("A")
                    .item("First step").item("Second step").item("Third step").build(),
            ItemList.ol().id("list-ordered-i").type("i")
                    .item("First step").item("Second step").item("Third step").build());

    public static ItemList demoListPlain = ItemList.ul().id("list-plain").plain()
            .item("No bullets here").item("Just plain text").item("Clean and simple").build();

    public static ItemList demoListBordered = ItemList.ul().id("list-bordered").plain().bordered()
            .item("Bordered row one").item("Bordered row two")
            .item("Bordered row three").item("Bordered row four").build();

    public static ItemList demoListIcons = ItemList.ul().id("list-icons").plain()
            .item("fa:circle-check", "Step one complete")
            .item("fa:circle-check", "Step two complete")
            .item("fa:clock", "Step three in progress")
            .item("fa:circle-exclamation", "Step four needs attention").build();

    public static ItemList demoListIconsLg = ItemList.ul().id("list-icons-lg").plain().iconLg()
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
}
