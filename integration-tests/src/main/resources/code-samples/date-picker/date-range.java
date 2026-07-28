import org.sitenetsoft.quarkus.pha.model.*;

import java.time.YearMonth;

CalendarMonth fromCal = CalendarMonth.of("dp-range-from-cal", YearMonth.of(2026, 5))
        .hxUrl("/api/htmx/date-picker/date-range-from")
        .build();

CalendarMonth toCal = CalendarMonth.of("dp-range-to-cal", YearMonth.of(2026, 5))
        .hxUrl("/api/htmx/date-picker/date-range-to")
        .build();

// Template side: two picker includes joined by "to", with the data in scope:
// {#include components/forms/date-picker id="dp-range-from" placeholder="YYYY-MM-DD"
//     ariaLabel="Start date" calendar=fromCal /}
// {#include components/forms/date-picker id="dp-range-to" placeholder="YYYY-MM-DD"
//     ariaLabel="End date" calendar=toCal /}
//
// The example's Alpine state enables the end picker once a start is picked,
// pre-fills it with the next day, and shows an error helper when end < start.
