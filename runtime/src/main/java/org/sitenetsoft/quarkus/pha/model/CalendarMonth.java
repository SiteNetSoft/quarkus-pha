package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Typed model for the Calendar month component ({@code pf-v6-c-calendar-month}).
 *
 * <p>Mirrors PF React's {@code CalendarMonth} (react-core 6.6.0): the grid is built
 * from the week containing the 1st of the displayed month, up to 6 weeks; the
 * current day carries {@code pf-m-current}; range mode disables dates before the
 * range start, marks the committed band inclusively, and exposes the data the
 * template needs for the hover-preview bindings (hovering past the committed end
 * moves the effective range end, across month boundaries).
 *
 * <pre>
 * CalendarMonth.of("cm-basic", YearMonth.of(2026, 5))
 *     .selected(LocalDate.of(2026, 5, 20))
 *     .hxUrl("/api/htmx/calendar-month/basic")
 *     .build();
 * </pre>
 *
 * <p>{@code hxUrl} enables server-driven navigation: prev/next month, the month
 * select menu, the year input, and date selection all re-fetch the fragment with
 * updated query params ({@code year}, {@code month}, and {@code selected} — or
 * {@code end} in range mode). Omit it for a static, non-navigable calendar.
 */
@TemplateData
public final class CalendarMonth {

    private final String id;
    private final String monthName;
    private final int year;
    private final boolean range;
    private final long endOrd;
    private final String prevUrl;
    private final String nextUrl;
    private final String yearUrl;
    private final List<MonthOption> monthOptions;
    private final List<List<Day>> weeks;

    private CalendarMonth(Builder b, List<List<Day>> weeks, String prevUrl, String nextUrl,
            String yearUrl, List<MonthOption> monthOptions) {
        this.id = b.id;
        this.monthName = b.month.getMonth().getDisplayName(TextStyle.FULL, b.locale);
        this.year = b.month.getYear();
        this.range = b.rangeStart != null;
        this.endOrd = b.rangeEnd != null ? b.rangeEnd.toEpochDay() : -1;
        this.prevUrl = prevUrl;
        this.nextUrl = nextUrl;
        this.yearUrl = yearUrl;
        this.monthOptions = monthOptions;
        this.weeks = weeks;
    }

    public static Builder of(String id, YearMonth month) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        b.month = Objects.requireNonNull(month, "month");
        return b;
    }

    public String id() {
        return id;
    }

    public String monthName() {
        return monthName;
    }

    public int year() {
        return year;
    }

    /** Range mode: enables the Alpine hover-preview bindings in the template. */
    public boolean range() {
        return range;
    }

    /** Epoch day of the committed range end (the hover preview pivots on it). */
    public long endOrd() {
        return endOrd;
    }

    public String prevUrl() {
        return prevUrl;
    }

    public String nextUrl() {
        return nextUrl;
    }

    /** hx-get target for the year input; the input's own name=year value rides along. */
    public String yearUrl() {
        return yearUrl;
    }

    public List<MonthOption> monthOptions() {
        return monthOptions;
    }

    public List<List<Day>> weeks() {
        return weeks;
    }

    /** One month entry of the header month-select menu. */
    @TemplateData
    public record MonthOption(String name, String url, boolean selected) {
    }

    /** One day cell with every modifier the template renders. */
    @TemplateData
    public record Day(int date, long ord, String ariaLabel, String url, boolean adjacent,
            boolean current, boolean selected, boolean disabled, boolean inRange,
            boolean startRange, boolean endRange, boolean afterEnd, boolean tabZero) {
    }

    public static final class Builder {
        private String id;
        private YearMonth month;
        private LocalDate selected;
        private LocalDate rangeStart;
        private LocalDate rangeEnd;
        private String hxUrl;
        private Locale locale = Locale.ENGLISH;

        private Builder() {
        }

        /** Selected date (basic mode). */
        public Builder selected(LocalDate selected) {
            this.selected = selected;
            return this;
        }

        /**
         * Range mode: dates before {@code start} are disabled (PF demo validator),
         * the committed band runs inclusively from {@code start} to {@code end}.
         */
        public Builder range(LocalDate start, LocalDate end) {
            this.rangeStart = Objects.requireNonNull(start, "start");
            this.rangeEnd = Objects.requireNonNull(end, "end");
            return this;
        }

        /** Endpoint that re-renders this calendar; enables all navigation controls. */
        public Builder hxUrl(String hxUrl) {
            this.hxUrl = hxUrl;
            return this;
        }

        public Builder locale(Locale locale) {
            this.locale = locale;
            return this;
        }

        private String url(YearMonth ym, LocalDate carryDate) {
            if (hxUrl == null) {
                return null;
            }
            StringBuilder sb = new StringBuilder(hxUrl)
                    .append("?year=").append(ym.getYear())
                    .append("&month=").append(ym.getMonthValue());
            if (carryDate != null) {
                sb.append(rangeStart != null ? "&end=" : "&selected=").append(carryDate);
            }
            return sb.toString();
        }

        public CalendarMonth build() {
            LocalDate carry = rangeStart != null ? rangeEnd : selected;
            String prevUrl = url(month.minusMonths(1), carry);
            String nextUrl = url(month.plusMonths(1), carry);
            // the year input contributes its own `year` param; only month + carry ride the URL
            String yearUrl = hxUrl == null ? null
                    : hxUrl + "?month=" + month.getMonthValue()
                            + (carry != null ? (rangeStart != null ? "&end=" : "&selected=") + carry : "");
            List<MonthOption> monthOptions = null;
            if (hxUrl != null) {
                monthOptions = new ArrayList<>(12);
                for (int m = 1; m <= 12; m++) {
                    YearMonth ym = YearMonth.of(month.getYear(), m);
                    monthOptions.add(new MonthOption(
                            ym.getMonth().getDisplayName(TextStyle.FULL, locale),
                            url(ym, carry), m == month.getMonthValue()));
                }
            }

            // PF buildCalendar: start from the week containing the 1st, up to 6 weeks,
            // stop once a completed week has run past the displayed month.
            LocalDate today = LocalDate.now();
            LocalDate cursor = month.atDay(1);
            int back = cursor.getDayOfWeek() == DayOfWeek.SUNDAY ? 0 : cursor.getDayOfWeek().getValue();
            cursor = cursor.minusDays(back);
            List<List<Day>> weeks = new ArrayList<>(6);
            boolean tabZeroAssigned = false;
            List<Day> tabZeroCandidates = new ArrayList<>();
            for (int w = 0; w < 6; w++) {
                List<Day> week = new ArrayList<>(7);
                for (int d = 0; d < 7; d++) {
                    week.add(day(cursor, today));
                    cursor = cursor.plusDays(1);
                }
                weeks.add(week);
                if (cursor.getMonth() != month.getMonth() || cursor.getYear() != month.getYear()) {
                    break;
                }
            }
            // roving tabindex: the selected day if visible, else the 1st of the month
            outer: for (List<Day> week : weeks) {
                for (int i = 0; i < week.size(); i++) {
                    Day day = week.get(i);
                    if (day.selected() && !day.adjacent()) {
                        week.set(i, withTabZero(day));
                        tabZeroAssigned = true;
                        break outer;
                    }
                }
            }
            if (!tabZeroAssigned) {
                outer: for (List<Day> week : weeks) {
                    for (int i = 0; i < week.size(); i++) {
                        Day day = week.get(i);
                        if (!day.adjacent() && !day.disabled()) {
                            week.set(i, withTabZero(day));
                            break outer;
                        }
                    }
                }
            }
            return new CalendarMonth(this, weeks, prevUrl, nextUrl, yearUrl, monthOptions);
        }

        private static Day withTabZero(Day d) {
            return new Day(d.date(), d.ord(), d.ariaLabel(), d.url(), d.adjacent(), d.current(),
                    d.selected(), d.disabled(), d.inRange(), d.startRange(), d.endRange(),
                    d.afterEnd(), true);
        }

        private Day day(LocalDate date, LocalDate today) {
            boolean valid = rangeStart == null || !date.isBefore(rangeStart);
            boolean adjacent = date.getMonth() != month.getMonth() || date.getYear() != month.getYear();
            boolean isStart = rangeStart != null && date.equals(rangeStart);
            boolean isEnd = rangeEnd != null && date.equals(rangeEnd);
            boolean isSelected = rangeStart != null ? (isStart || isEnd)
                    : selected != null && date.equals(selected);
            boolean inRange = rangeStart != null && rangeEnd != null
                    && !date.isBefore(rangeStart) && !date.isAfter(rangeEnd);
            boolean disabled = !valid && !(inRange || isSelected);
            boolean afterEnd = rangeStart != null && valid && rangeEnd != null && date.isAfter(rangeEnd);
            // selecting a date re-renders the grid on that date's month (PF jumps there);
            // pick=1 marks selection clicks so consumers (e.g. date-picker) can react to
            // a chosen date without confusing it with month/year navigation
            String dayUrl = (hxUrl != null && !disabled) ? url(YearMonth.from(date), date) + "&pick=1" : null;
            String ariaLabel = date.getDayOfMonth() + " "
                    + date.getMonth().getDisplayName(TextStyle.FULL, locale) + " " + date.getYear();
            return new Day(date.getDayOfMonth(), date.toEpochDay(), ariaLabel, dayUrl, adjacent,
                    date.equals(today), isSelected, disabled, inRange, isStart, isEnd, afterEnd, false);
        }
    }
}
