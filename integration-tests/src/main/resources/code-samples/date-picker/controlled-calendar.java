import org.sitenetsoft.quarkus.pha.model.*;

import java.time.YearMonth;

CalendarMonth calendar = CalendarMonth.of("dp-controlled-calendar-cal", YearMonth.of(2026, 5))
        .hxUrl("/api/htmx/date-picker/controlled-calendar")
        .build();

// Template side, with the data in scope:
// {#include components/forms/date-picker id="dp-controlled-calendar" placeholder="YYYY-MM-DD"
//     ariaLabel="Date" calendar=calendar /}
//
// The external "Toggle calendar" button drives the popover by clicking the
// picker's own toggle control — no extra state to wire up.
