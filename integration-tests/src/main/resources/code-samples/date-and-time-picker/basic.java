import org.sitenetsoft.quarkus.pha.model.*;

import java.time.LocalDate;
import java.time.YearMonth;

// Date + time pair: a date-picker (with a Java-built calendar) beside a
// time-picker composition. The calendar is the model-driven piece.
CalendarMonth calendar = CalendarMonth.of("dtp-date-cal", YearMonth.of(2026, 5))
        .selected(LocalDate.of(2026, 5, 20))
        .hxUrl("/api/htmx/date-picker/date-and-time")
        .build();

// Template side, with the data in scope:
// {#include components/forms/date-picker id="dtp-date" value="2026-05-20" calendar=calendar /}
// ...beside it, the time-picker composition (input-group + menu of slots) —
// see the time-picker component's Java tab for building the slot menu.
