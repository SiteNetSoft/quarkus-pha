package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.SimpleList;
import org.sitenetsoft.quarkus.pha.model.SimpleListItem;

/**
 * Demo data for the simple-list examples — every model-driven example on
 * /components/simple-list is populated from one of these SimpleList models.
 * Globals so the standalone example route (which renders templates without
 * data) can see them. Each is mirrored by a snippet in
 * resources/code-samples/simple-list/ served on the example card's Java tab —
 * keep them in sync when editing.
 */
@TemplateGlobal
public class SimpleListDemoData {

    public static SimpleList demoSimpleBasic = SimpleList.builder()
            .id("sl-basic").selectable("'inbox'")
            .item(SimpleListItem.link("Inbox", "#").key("'inbox'"))
            .item(SimpleListItem.link("Sent", "#").key("'sent'"))
            .item(SimpleListItem.link("Drafts", "#").key("'drafts'"))
            .item(SimpleListItem.link("Spam", "#").key("'spam'"))
            .build();

    public static SimpleList demoSimpleGrouped = SimpleList.builder()
            .id("sl-grouped").selectable("'a1'")
            .section(SimpleList.section("Account",
                    SimpleListItem.link("Profile", "#").key("'a1'"),
                    SimpleListItem.link("Security", "#").key("'a2'")))
            .section(SimpleList.section("Notifications",
                    SimpleListItem.link("Email", "#").key("'n1'"),
                    SimpleListItem.link("Push", "#").key("'n2'")))
            .build();

    public static SimpleList demoSimpleLinks = SimpleList.builder()
            .id("slist-links")
            .item(SimpleListItem.link("Linked item 1", "#").asCurrent())
            .item(SimpleListItem.link("Linked item 2", "#"))
            .item(SimpleListItem.link("Linked item 3", "#"))
            .build();

    public static SimpleList demoSimpleSelectable = SimpleList.builder()
            .id("slist-selectable").selectable("1")
            .item(SimpleListItem.button("Item 1").key("1"))
            .item(SimpleListItem.button("Item 2").key("2"))
            .item(SimpleListItem.button("Item 3").key("3"))
            .build();
}
