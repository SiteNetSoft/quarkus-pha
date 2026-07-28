import org.sitenetsoft.quarkus.pha.model.*;

import java.time.LocalDate;
import java.time.YearMonth;

CalendarMonth calendar = CalendarMonth.of("dp-value-cal", YearMonth.of(2026, 5))
        .selected(LocalDate.of(2026, 5, 20))
        .hxUrl("/api/htmx/date-picker/with-value")
        .build();

// Template side, with the data in scope — value pre-fills the input and
// selected(...) highlights the same date on the calendar:
// {#include components/forms/date-picker id="dp-value" value="2026-05-20" calendar=calendar /}
