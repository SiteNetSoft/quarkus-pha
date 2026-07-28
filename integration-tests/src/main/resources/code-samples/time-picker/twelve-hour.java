import org.sitenetsoft.quarkus.pha.model.*;

// 12-hour variant — same composition as the basic time picker, AM/PM labels
// (placeholder "HH:MM AM/PM").
Menu.Builder times = Menu.builder().id("tp-12h-menu").scrollable();
for (int h = 0; h < 24; h++) {
    int display = h % 12 == 0 ? 12 : h % 12;
    String meridiem = h < 12 ? "AM" : "PM";
    times.item(MenuItem.of(String.format("%d:00 %s", display, meridiem)));
    times.item(MenuItem.of(String.format("%d:30 %s", display, meridiem)));
}
Menu menu = times.build();

// Template side — identical wrapper to the basic time picker:
// {#include components/navigation/menu menu=menu /}
