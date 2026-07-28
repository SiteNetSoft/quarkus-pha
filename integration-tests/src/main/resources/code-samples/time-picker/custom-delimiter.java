import org.sitenetsoft.quarkus.pha.model.*;

// Custom-delimiter variant — same composition as the basic time picker with a
// dot delimiter (placeholder "HH.MM").
Menu.Builder times = Menu.builder().id("tp-delim-menu").scrollable();
for (int h = 0; h < 24; h++) {
    times.item(MenuItem.of(String.format("%02d.00", h)));
    times.item(MenuItem.of(String.format("%02d.30", h)));
}
Menu menu = times.build();

// Template side — identical wrapper to the basic time picker:
// {#include components/navigation/menu menu=menu /}
