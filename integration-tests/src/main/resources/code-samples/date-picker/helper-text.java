import org.sitenetsoft.quarkus.pha.model.*;

import java.time.YearMonth;

CalendarMonth calendar = CalendarMonth.of("dp-helper-text-cal", YearMonth.of(2026, 5))
        .hxUrl("/api/htmx/date-picker/helper-text")
        .build();

// Template side: hand-rolled pf-v6-c-date-picker markup with a
// pf-v6-c-date-picker__helper-text block under the input and the calendar in
// its popover:
// {#include components/forms/calendar-month calendarMonth=calendar /}
