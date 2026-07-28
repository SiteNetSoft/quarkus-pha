import org.sitenetsoft.quarkus.pha.model.*;

import java.time.YearMonth;

CalendarMonth calendar = CalendarMonth.of("dp-invalid-cal", YearMonth.of(2026, 5))
        .hxUrl("/api/htmx/date-picker/invalid")
        .build();

// Template side: hand-rolled pf-v6-c-date-picker markup with the calendar in
// its popover:
// {#include components/forms/calendar-month calendarMonth=calendar /}
//
// The invalid treatment (pf-m-error form-control, error helper text) is driven
// by the example's Alpine format check on the typed value.
