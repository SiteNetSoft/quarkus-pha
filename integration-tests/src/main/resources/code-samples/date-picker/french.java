import org.sitenetsoft.quarkus.pha.model.*;

import java.time.LocalDate;
import java.time.YearMonth;

CalendarMonth calendar = CalendarMonth.of("dp-french-cal", YearMonth.of(2026, 3))
        .selected(LocalDate.of(2026, 3, 5))
        .hxUrl("/api/htmx/date-picker/french")
        .build();

// Template side: hand-rolled pf-v6-c-date-picker markup (lang="fr" input,
// JJ/MM/AAAA placeholder) with the calendar in its popover:
// {#include components/forms/calendar-month calendarMonth=calendar /}
//
// The dd/MM/yyyy formatting of the picked date happens server-side — the HTMX
// endpoint formats with DateTimeFormatter.ofPattern("dd/MM/yyyy") before
// dispatching pha-date-picked (see DatePickerDemoData + HtmxRoutes).
