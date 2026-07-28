import org.sitenetsoft.quarkus.pha.model.*;

import java.time.LocalDate;
import java.time.YearMonth;

// Date select: a menu toggle opening a raised panel that hosts the
// calendar-month component — the calendar is the Java-built piece.
CalendarMonth calendar = CalendarMonth.of("cm-basic", YearMonth.of(2026, 5))
        .selected(LocalDate.of(2026, 5, 20))
        .hxUrl("/api/htmx/calendar-month/basic")
        .build();

// Template side — the wrapper owns the Alpine open state; picking a day updates
// the toggle's timestamp text and closes the panel (delegated click handler):
// {#include components/navigation/menu-toggle expandedExpr='open' /}
// <div x-show="open" x-cloak class="pf-v6-c-panel pf-m-raised">
//   {#include components/forms/calendar-month calendarMonth=calendar /}
// </div>
