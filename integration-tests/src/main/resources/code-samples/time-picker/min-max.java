import org.sitenetsoft.quarkus.pha.model.*;

// Min/max variant — the slot list is clamped to business hours
// (placeholder "HH:MM (09:00 - 17:00)"), ending exactly on the max.
Menu.Builder times = Menu.builder().id("tp-minmax-menu").scrollable();
for (int h = 9; h <= 17; h++) {
    times.item(MenuItem.of(String.format("%02d:00", h)));
    if (h < 17) {
        times.item(MenuItem.of(String.format("%02d:30", h)));
    }
}
Menu menu = times.build();

// Template side — identical wrapper to the basic time picker:
// {#include components/navigation/menu menu=menu /}
