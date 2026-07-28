import org.sitenetsoft.quarkus.pha.model.*;

import java.time.LocalDate;
import java.time.YearMonth;

CalendarMonth calendar = CalendarMonth.of("cm-range", YearMonth.of(2026, 5))
        .range(LocalDate.of(2026, 5, 12), LocalDate.of(2026, 5, 20))
        .hxUrl("/api/htmx/calendar-month/date-range")
        .build();

// Template side, with the data in scope:
// {#include components/forms/calendar-month calendarMonth=calendar /}
//
// range(start, end) fixes the range start (earlier dates render disabled) and
// marks the committed end. Clicking a date commits a new end; the endpoint
// rebuilds the model with the picked end and re-renders the fragment.
