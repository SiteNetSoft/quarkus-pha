package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * One breadcrumb crumb — text, target and current/heading/dropdown shape.
 *
 * <pre>
 * BreadcrumbItem.of("Section home").href("#")
 * BreadcrumbItem.of("Section landing").href("#").asCurrent()
 * BreadcrumbItem.of("Section title").dropdown("bc-dropdown-section")
 *     .option("Sibling page A", "#")
 * </pre>
 */
@TemplateData
public final class BreadcrumbItem {

    private final String text;
    private final String href;
    private final boolean current;
    private final String headingLevel;
    private final String dropdownId;
    private final List<MenuItem> options;

    private BreadcrumbItem(String text, String href, boolean current, String headingLevel,
                           String dropdownId, List<MenuItem> options) {
        this.text = text;
        this.href = href;
        this.current = current;
        this.headingLevel = headingLevel;
        this.dropdownId = dropdownId;
        this.options = options;
    }

    public static BreadcrumbItem of(String text) {
        return new BreadcrumbItem(Objects.requireNonNull(text, "text"), null, false, null, null, List.of());
    }

    public BreadcrumbItem href(String href) {
        return new BreadcrumbItem(text, href, current, headingLevel, dropdownId, options);
    }

    /** The active crumb — pf-m-current + aria-current="page". */
    public BreadcrumbItem asCurrent() {
        return new BreadcrumbItem(text, href, true, headingLevel, dropdownId, options);
    }

    /** Wrap the (current) crumb in a heading for screen readers. */
    public BreadcrumbItem heading(String headingLevel) {
        return new BreadcrumbItem(text, href, current, Objects.requireNonNull(headingLevel, "headingLevel"),
                dropdownId, options);
    }

    /** Dropdown crumb; add menu entries via {@link #option(String, String)}. */
    public BreadcrumbItem dropdown(String dropdownId) {
        return new BreadcrumbItem(text, href, current, headingLevel,
                Objects.requireNonNull(dropdownId, "dropdownId"), options);
    }

    /** Dropdown entry composed from the real MenuItem model. */
    public BreadcrumbItem option(MenuItem item) {
        List<MenuItem> next = new ArrayList<>(options);
        next.add(Objects.requireNonNull(item, "item"));
        return new BreadcrumbItem(this.text, this.href, current, headingLevel, dropdownId, List.copyOf(next));
    }

    /** Dropdown entry shorthand: text + link target. */
    public BreadcrumbItem option(String text, String href) {
        return option(MenuItem.of(Objects.requireNonNull(text, "text"))
                .href(Objects.requireNonNull(href, "href")));
    }

    public String text() {
        return text;
    }

    public String href() {
        return href;
    }

    public boolean isCurrent() {
        return current;
    }

    public boolean isHeadingShape() {
        return headingLevel != null;
    }

    public String headingTag() {
        return headingLevel;
    }

    public boolean isDropdownShape() {
        return dropdownId != null;
    }

    public String dropdownId() {
        return dropdownId;
    }

    public List<MenuItem> options() {
        return options;
    }
}
