import org.sitenetsoft.quarkus.pha.model.*;

import java.time.YearMonth;

CalendarMonth fixedCal = CalendarMonth.of("dp-width-fixed-cal", YearMonth.of(2026, 5))
        .hxUrl("/api/htmx/date-picker/width-fixed")
        .build();

CalendarMonth charsCal = CalendarMonth.of("dp-width-chars-cal", YearMonth.of(2026, 5))
        .hxUrl("/api/htmx/date-picker/width-chars")
        .build();

// Template side: two hand-rolled pf-v6-c-date-picker blocks whose input width
// comes from the PF width var on the root —
//   style="--pf-v6-c-date-picker__input--c-form-control--Width: 220px"  (fixed)
//   style="--pf-v6-c-date-picker__input--c-form-control--Width: 12ch"   (ch-based)
// — each embedding its calendar in the popover:
// {#include components/forms/calendar-month calendarMonth=fixedCal /}
// {#include components/forms/calendar-month calendarMonth=charsCal /}
