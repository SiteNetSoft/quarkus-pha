import org.sitenetsoft.quarkus.pha.model.*;

SimpleList list = SimpleList.builder()
        .id("sl-grouped").selectable("'a1'")
        .section(SimpleList.section("Account",
                SimpleListItem.link("Profile", "#").key("'a1'"),
                SimpleListItem.link("Security", "#").key("'a2'")))
        .section(SimpleList.section("Notifications",
                SimpleListItem.link("Email", "#").key("'n1'"),
                SimpleListItem.link("Push", "#").key("'n2'")))
        .build();

// Template side, with `list` in the template data:
// {#include components/data-display/simple-list list=list /}
