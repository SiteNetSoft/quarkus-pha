import org.sitenetsoft.quarkus.pha.model.*;

// 24-hour + seconds variant (placeholder "HH:MM:SS").
Menu.Builder times = Menu.builder().id("tp-24s-menu").scrollable();
for (int h = 0; h < 24; h++) {
    times.item(MenuItem.of(String.format("%02d:00:00", h)));
    times.item(MenuItem.of(String.format("%02d:30:00", h)));
}
Menu menu = times.build();

// Template side — identical wrapper to the basic time picker:
// {#include components/navigation/menu menu=menu /}
