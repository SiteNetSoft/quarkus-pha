package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.CalendarMonth;

import java.time.LocalDate;
import java.time.YearMonth;

/**
 * Demo data + factories for the calendar-month examples. The examples on
 * /components/calendar-month render from these models; the HTMX endpoints in
 * {@link HtmxRoutes} rebuild them for navigation (prev/next month, month menu,
 * year input, date selection) and pass them to the same fragments, shadowing
 * the globals.
 */
public class CalendarMonthDemoData {

    static final String BASIC_URL = "/api/htmx/calendar-month/basic";
    static final String RANGE_URL = "/api/htmx/calendar-month/date-range";

    static final LocalDate BASIC_DEFAULT_SELECTED = LocalDate.of(2026, 5, 20);
    static final LocalDate RANGE_START = LocalDate.of(2026, 5, 12);
    static final LocalDate RANGE_DEFAULT_END = LocalDate.of(2026, 5, 20);

    static CalendarMonth basic(YearMonth month, LocalDate selected) {
        return CalendarMonth.of("cm-basic", month)
                .selected(selected)
                .hxUrl(BASIC_URL)
                .build();
    }

    static CalendarMonth range(YearMonth month, LocalDate end) {
        return CalendarMonth.of("cm-range", month)
                .range(RANGE_START, end)
                .hxUrl(RANGE_URL)
                .build();
    }

    @TemplateGlobal
    public static CalendarMonth demoCalBasic = basic(YearMonth.of(2026, 5), BASIC_DEFAULT_SELECTED);

    @TemplateGlobal
    public static CalendarMonth demoCalRange = range(YearMonth.of(2026, 5), RANGE_DEFAULT_END);
}
