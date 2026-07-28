import org.sitenetsoft.quarkus.pha.model.*;

import java.time.LocalDate;
import java.time.YearMonth;

CalendarMonth calendar = CalendarMonth.of("cm-basic", YearMonth.of(2026, 5))
        .selected(LocalDate.of(2026, 5, 20))
        .hxUrl("/api/htmx/calendar-month/basic")
        .build();

// Template side, with the data in scope:
// {#include components/forms/calendar-month calendarMonth=calendar /}
//
// hxUrl is where navigation (prev/next month, month menu, year input) and date
// selection re-fetch this fragment from — the endpoint rebuilds the model for
// the requested month/date and renders the same template (see HtmxRoutes).
