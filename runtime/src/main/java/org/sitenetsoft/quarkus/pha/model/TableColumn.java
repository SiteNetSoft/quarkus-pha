package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * One header column of a {@link Table}. Create via {@link #of(String)} (or the
 * {@link Table.Builder} conveniences) and refine with the {@code withX} copies:
 *
 * <pre>
 * TableColumn.of("Description").width(30).truncate()
 * TableColumn.of("Name").sortable("name")
 * </pre>
 */
@TemplateData
public final class TableColumn {

    public enum Kind { TEXT, CHECK, TOGGLE, ACTION }

    public enum Sort { NONE, ASCENDING, DESCENDING }

    private final Kind kind;
    private final String label;
    private final List<String> modifiers;
    private final String sortKey;
    private final Sort sort;

    private TableColumn(Kind kind, String label, List<String> modifiers, String sortKey, Sort sort) {
        this.kind = kind;
        this.label = label;
        this.modifiers = List.copyOf(modifiers);
        this.sortKey = sortKey;
        this.sort = sort;
    }

    /** Regular text column. */
    public static TableColumn of(String label) {
        return new TableColumn(Kind.TEXT, Objects.requireNonNull(label, "label"), List.of(), null, Sort.NONE);
    }

    /** Leading select-all checkbox column ({@code pf-v6-c-table__check} header cell). */
    public static TableColumn check() {
        return new TableColumn(Kind.CHECK, null, List.of(), null, Sort.NONE);
    }

    /** Leading empty column above expandable-row toggles. */
    public static TableColumn toggle() {
        return new TableColumn(Kind.TOGGLE, null, List.of(), null, Sort.NONE);
    }

    /** Trailing action column ({@code pf-v6-c-table__action}); label optional. */
    public static TableColumn action(String label) {
        return new TableColumn(Kind.ACTION, label, List.of(), null, Sort.NONE);
    }

    public static TableColumn action() {
        return new TableColumn(Kind.ACTION, null, List.of(), null, Sort.NONE);
    }

    /** Copy that sorts server-side under this key (see {@link Table.Builder#sortEndpoint}). */
    public TableColumn sortable(String sortKey) {
        return new TableColumn(kind, label, modifiers, Objects.requireNonNull(sortKey, "sortKey"), Sort.NONE);
    }

    /** Copy marked as the actively sorted column. */
    public TableColumn sorted(boolean ascending) {
        if (sortKey == null) {
            throw new IllegalStateException("sorted() requires sortable(key) first: " + label);
        }
        return new TableColumn(kind, label, modifiers, sortKey, ascending ? Sort.ASCENDING : Sort.DESCENDING);
    }

    /** Copy with {@code pf-m-width-<pct>}; pct is one of PatternFly's width steps. */
    public TableColumn width(int pct) {
        return withModifier("pf-m-width-" + pct);
    }

    public TableColumn truncate() {
        return withModifier("pf-m-truncate");
    }

    public TableColumn fitContent() {
        return withModifier("pf-m-fit-content");
    }

    public TableColumn nowrap() {
        return withModifier("pf-m-nowrap");
    }

    public TableColumn wrap() {
        return withModifier("pf-m-wrap");
    }

    /** Copy with any extra {@code pf-m-*} class (e.g. visibility breakpoints). */
    public TableColumn withModifier(String modifierClass) {
        List<String> m = new ArrayList<>(modifiers);
        m.add(modifierClass);
        return new TableColumn(kind, label, m, sortKey, sort);
    }

    public boolean isTextColumn() {
        return kind == Kind.TEXT;
    }

    public boolean isCheckColumn() {
        return kind == Kind.CHECK;
    }

    public boolean isToggleColumn() {
        return kind == Kind.TOGGLE;
    }

    public boolean isActionColumn() {
        return kind == Kind.ACTION;
    }

    public String label() {
        return label;
    }

    public boolean hasModifiers() {
        return !modifiers.isEmpty();
    }

    public String modifierClasses() {
        return String.join(" ", modifiers);
    }

    /** Visibility modifier classes that body cells in this column must repeat. */
    public boolean hasCellModifiers() {
        return modifiers.stream().anyMatch(m -> m.startsWith("pf-m-hidden") || m.startsWith("pf-m-visible"));
    }

    public String cellModifierClasses() {
        return modifiers.stream()
                .filter(m -> m.startsWith("pf-m-hidden") || m.startsWith("pf-m-visible"))
                .reduce((a, b) -> a + " " + b).orElse("");
    }

    public boolean isSortable() {
        return sortKey != null;
    }

    public String sortKey() {
        return sortKey;
    }

    public boolean isSorted() {
        return sort != Sort.NONE;
    }

    public boolean isAscending() {
        return sort == Sort.ASCENDING;
    }

    /** Value for the th's aria-sort attribute. */
    public String ariaSort() {
        switch (sort) {
            case ASCENDING: return "ascending";
            case DESCENDING: return "descending";
            default: return "none";
        }
    }

    /** Direction the sort button should request next. */
    public String nextDir() {
        return sort == Sort.ASCENDING ? "desc" : "asc";
    }

    /** Font Awesome classes for the sort indicator icon. */
    public String sortIconClasses() {
        switch (sort) {
            case ASCENDING: return "fas fa-long-arrow-alt-up";
            case DESCENDING: return "fas fa-long-arrow-alt-down";
            default: return "fas fa-arrows-alt-v";
        }
    }
}
