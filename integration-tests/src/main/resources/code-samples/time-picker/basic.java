import org.sitenetsoft.quarkus.pha.model.*;

// The time picker is a composition: an input-group (text input + clock toggle)
// over a scrollable menu of time slots. The slot list is the Java-built piece —
// generate the same 30-minute increments the demo builds client-side.
Menu.Builder times = Menu.builder().id("tp-basic-menu").scrollable();
for (int h = 0; h < 24; h++) {
    times.item(MenuItem.of(String.format("%02d:00", h)));
    times.item(MenuItem.of(String.format("%02d:30", h)));
}
Menu menu = times.build();

// Template side — the wrapper owns the Alpine open state and writes the picked
// slot into the input (placeholder "HH:MM"):
// <div x-data="{ open: false, value: '' }" style="position: relative">
//   ...pf-v6-c-input-group with the text input and the fa:clock control button...
//   <div x-show="open" x-cloak style="position: absolute; top: 100%">
//     {#include components/navigation/menu menu=menu /}
//   </div>
// </div>
