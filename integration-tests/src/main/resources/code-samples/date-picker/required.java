import org.sitenetsoft.quarkus.pha.model.*;

import java.time.YearMonth;

CalendarMonth calendar = CalendarMonth.of("dp-required-cal", YearMonth.of(2026, 5))
        .hxUrl("/api/htmx/date-picker/required")
        .build();

// Template side: this example hand-rolls the pf-v6-c-date-picker markup (to add
// the required marker and validation) and embeds the calendar in its popover:
// {#include components/forms/calendar-month calendarMonth=calendar /}
