import org.sitenetsoft.quarkus.pha.model.*;

SimpleList list = SimpleList.builder()
        .id("sl-basic").selectable("'inbox'")
        .item(SimpleListItem.link("Inbox", "#").key("'inbox'"))
        .item(SimpleListItem.link("Sent", "#").key("'sent'"))
        .item(SimpleListItem.link("Drafts", "#").key("'drafts'"))
        .item(SimpleListItem.link("Spam", "#").key("'spam'"))
        .build();

// Template side, with `list` in the template data:
// {#include components/data-display/simple-list list=list /}
