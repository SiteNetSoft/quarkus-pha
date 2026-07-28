import org.sitenetsoft.quarkus.pha.model.*;

import java.time.YearMonth;

CalendarMonth calendar = CalendarMonth.of("dp-basic-cal", YearMonth.of(2026, 5))
        .hxUrl("/api/htmx/date-picker/basic")
        .build();

// Template side, with the data in scope:
// {#include components/forms/date-picker id="dp-basic" placeholder="YYYY-MM-DD" calendar=calendar /}
//
// Picking a date answers over HTMX with an out-of-band span that dispatches
// pha-date-picked to the picker element, which writes the ISO date into the
// input and closes the popover (see HtmxRoutes + partials/date-picker-calendar).
