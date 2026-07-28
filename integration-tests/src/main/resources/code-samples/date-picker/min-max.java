import org.sitenetsoft.quarkus.pha.model.*;

import java.time.YearMonth;

CalendarMonth calendar = CalendarMonth.of("dp-min-max-cal", YearMonth.of(2026, 5))
        .hxUrl("/api/htmx/date-picker/min-max")
        .build();

// Template side: hand-rolled pf-v6-c-date-picker markup with the calendar in
// its popover:
// {#include components/forms/calendar-month calendarMonth=calendar /}
//
// The min/max window (2026-01-01..2026-12-31) is validated client-side by the
// example's Alpine state, which flips the form-control and helper text to the
// error treatment when the value falls outside the year.
