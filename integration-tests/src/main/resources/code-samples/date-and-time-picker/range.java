import org.sitenetsoft.quarkus.pha.model.*;

import java.time.YearMonth;

// Date-and-time range: two date-pickers (Java-built calendars) each paired with
// a time-picker composition.
CalendarMonth fromCal = CalendarMonth.of("dtrp-from-cal", YearMonth.of(2026, 5))
        .hxUrl("/api/htmx/date-picker/range-from")
        .build();

CalendarMonth toCal = CalendarMonth.of("dtrp-to-cal", YearMonth.of(2026, 5))
        .hxUrl("/api/htmx/date-picker/range-to")
        .build();

// Template side, with the data in scope:
// {#include components/forms/date-picker id="dtrp-from" placeholder="YYYY-MM-DD"
//     ariaLabel="Start date" calendar=fromCal /}
// {#include components/forms/date-picker id="dtrp-to" placeholder="YYYY-MM-DD"
//     ariaLabel="End date" calendar=toCal /}
// ...each beside a time-picker composition (see the time-picker Java tab).
