import org.sitenetsoft.quarkus.pha.model.*;

MenuToggle toggle = MenuToggle.of("Select a state").asPlaceholder().build();

// viewMore(text, count, loadedPrefix) renders a "View more" load row after the
// first `count` items.
Menu menu = Menu.builder()
        .id("sl-view-more-menu")
        .selectSingle()
        .viewMore("View more", 3, "State ")
        .items(MenuItem.of("Alabama"), MenuItem.of("Florida"), MenuItem.of("New Jersey"))
        .build();

// Template side — same select composition wrapper; this demo reveals the extra
// options (New Mexico, New York, North Carolina) client-side when "View more"
// is clicked instead of loading generated items:
// {#include components/navigation/menu-toggle toggle=toggle /}
// ... {#include components/navigation/menu menu=menu /}
