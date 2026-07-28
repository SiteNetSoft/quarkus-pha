import org.sitenetsoft.quarkus.pha.model.*;

import java.time.LocalDate;
import java.time.YearMonth;

CalendarMonth calendar = CalendarMonth.of("dp-american-cal", YearMonth.of(2026, 3))
        .selected(LocalDate.of(2026, 3, 5))
        .hxUrl("/api/htmx/date-picker/american-format")
        .build();

// Template side: hand-rolled pf-v6-c-date-picker markup with the calendar in
// its popover:
// {#include components/forms/calendar-month calendarMonth=calendar /}
//
// The MM/DD/YYYY formatting of the picked date happens server-side — the HTMX
// endpoint formats with DateTimeFormatter.ofPattern("MM/dd/yyyy") before
// dispatching pha-date-picked (see DatePickerDemoData + HtmxRoutes).
