package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.CalendarMonth;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Demo data for the date-picker examples. Each example's popover hosts an
 * interactive {@link CalendarMonth} whose HTMX endpoint is
 * /api/htmx/date-picker/{example}; picking a date answers with an out-of-band
 * span that dispatches pha-date-picked (see partials/date-picker-calendar.html),
 * formatted per example (ISO, US, or French).
 */
public class DatePickerDemoData {

    /** Per-example config: which picker element to notify and how to format the picked date. */
    record Config(String pickerId, DateTimeFormatter format) {
    }

    static final DateTimeFormatter ISO = DateTimeFormatter.ISO_LOCAL_DATE;
    static final DateTimeFormatter US = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    static final DateTimeFormatter FR = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    static final Map<String, Config> CONFIGS = Map.ofEntries(
            Map.entry("basic", new Config("dp-basic", ISO)),
            Map.entry("with-value", new Config("dp-value", ISO)),
            Map.entry("american-format", new Config("dp-american", US)),
            Map.entry("french", new Config("dp-french", FR)),
            Map.entry("helper-text", new Config("dp-helper-text", ISO)),
            Map.entry("min-max", new Config("dp-min-max", ISO)),
            Map.entry("required", new Config("dp-required", ISO)),
            Map.entry("date-and-time", new Config("dtp-date", ISO)),
            Map.entry("invalid", new Config("dp-invalid", ISO)),
            Map.entry("width-fixed", new Config("dp-width-fixed", ISO)),
            Map.entry("width-chars", new Config("dp-width-chars", ISO)));

    static CalendarMonth calendar(String example, YearMonth month, LocalDate selected) {
        return CalendarMonth.of(CONFIGS.get(example).pickerId() + "-cal", month)
                .selected(selected)
                .hxUrl("/api/htmx/date-picker/" + example)
                .build();
    }

    private static final YearMonth DEMO_MONTH = YearMonth.of(2026, 5);

    @TemplateGlobal
    public static CalendarMonth dpCalBasic = calendar("basic", DEMO_MONTH, null);

    @TemplateGlobal
    public static CalendarMonth dpCalValue = calendar("with-value", DEMO_MONTH, LocalDate.of(2026, 5, 20));

    @TemplateGlobal
    public static CalendarMonth dpCalAmerican = calendar("american-format", YearMonth.of(2026, 3), LocalDate.of(2026, 3, 5));

    @TemplateGlobal
    public static CalendarMonth dpCalFrench = calendar("french", YearMonth.of(2026, 3), LocalDate.of(2026, 3, 5));

    @TemplateGlobal
    public static CalendarMonth dpCalHelper = calendar("helper-text", DEMO_MONTH, null);

    @TemplateGlobal
    public static CalendarMonth dpCalMinMax = calendar("min-max", DEMO_MONTH, null);

    @TemplateGlobal
    public static CalendarMonth dpCalRequired = calendar("required", DEMO_MONTH, null);

    @TemplateGlobal
    public static CalendarMonth dtpCal = calendar("date-and-time", DEMO_MONTH, LocalDate.of(2026, 5, 20));

    @TemplateGlobal
    public static CalendarMonth dpCalInvalid = calendar("invalid", DEMO_MONTH, null);

    @TemplateGlobal
    public static CalendarMonth dpCalWidthFixed = calendar("width-fixed", DEMO_MONTH, null);

    @TemplateGlobal
    public static CalendarMonth dpCalWidthChars = calendar("width-chars", DEMO_MONTH, null);
}
