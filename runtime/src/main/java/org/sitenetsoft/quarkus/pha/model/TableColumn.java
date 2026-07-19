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

    public enum Kind { TEXT, CHECK, TOGGLE, ACTION, FAVORITE, DRAG, INLINE_EDIT }

    public enum Sort { NONE, ASCENDING, DESCENDING }

    private final Kind kind;
    private final String label;
    private final List<String> modifiers;
    private final String sortKey;
    private final Sort sort;
    private final List<String> subColumns;
    private final String stickyModifiers;
    private final String stickyStyle;
    private final boolean rowHeader;

    private TableColumn(Kind kind, String label, List<String> modifiers, String sortKey, Sort sort) {
        this(kind, label, modifiers, sortKey, sort, List.of(), null, null, false);
    }

    private TableColumn(Kind kind, String label, List<String> modifiers, String sortKey, Sort sort,
                        List<String> subColumns, String stickyModifiers, String stickyStyle, boolean rowHeader) {
        this.kind = kind;
        this.label = label;
        this.modifiers = List.copyOf(modifiers);
        this.sortKey = sortKey;
        this.sort = sort;
        this.subColumns = List.copyOf(subColumns);
        this.stickyModifiers = stickyModifiers;
        this.stickyStyle = stickyStyle;
        this.rowHeader = rowHeader;
    }

    /** Regular text column. */
    public static TableColumn of(String label) {
        return new TableColumn(Kind.TEXT, Objects.requireNonNull(label, "label"), List.of(), null, Sort.NONE);
    }

    /**
     * Parent header spanning sub-columns (nested column headers): the parent
     * renders with a colspan in the first header row, the sub-labels as
     * {@code __subhead} cells in the second.
     */
    public static TableColumn group(String label, String... subColumns) {
        if (subColumns.length == 0) {
            throw new IllegalArgumentException("A column group needs at least one sub-column: " + label);
        }
        return new TableColumn(Kind.TEXT, Objects.requireNonNull(label, "label"), List.of(), null, Sort.NONE,
                List.of(subColumns), null, null, false);
    }

    /** Leading select-all checkbox column ({@code pf-v6-c-table__check} header cell). */
    public static TableColumn check() {
        return new TableColumn(Kind.CHECK, null, List.of(), null, Sort.NONE);
    }

    /** Leading empty column above expandable-row toggles. */
    public static TableColumn toggle() {
        return new TableColumn(Kind.TOGGLE, null, List.of(), null, Sort.NONE);
    }

    /** Leading favorite-star column ({@code pf-v6-c-table__favorite}). */
    public static TableColumn favorite() {
        return new TableColumn(Kind.FAVORITE, null, List.of(), null, Sort.NONE);
    }

    /** Leading drag-grip column ({@code pf-v6-c-table__draggable}). */
    public static TableColumn drag() {
        return new TableColumn(Kind.DRAG, null, List.of(), null, Sort.NONE);
    }

    /** Trailing inline-edit action column ({@code pf-v6-c-table__inline-edit-action}). */
    public static TableColumn inlineEdit(String label) {
        return new TableColumn(Kind.INLINE_EDIT, Objects.requireNonNull(label, "label"), List.of(), null, Sort.NONE);
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
        return new TableColumn(kind, label, modifiers, Objects.requireNonNull(sortKey, "sortKey"), Sort.NONE,
                subColumns, stickyModifiers, stickyStyle, rowHeader);
    }

    /** Copy marked as the actively sorted column. */
    public TableColumn sorted(boolean ascending) {
        if (sortKey == null) {
            throw new IllegalStateException("sorted() requires sortable(key) first: " + label);
        }
        return new TableColumn(kind, label, modifiers, sortKey, ascending ? Sort.ASCENDING : Sort.DESCENDING,
                subColumns, stickyModifiers, stickyStyle, rowHeader);
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
        return new TableColumn(kind, label, m, sortKey, sort, subColumns, stickyModifiers, stickyStyle, rowHeader);
    }

    /**
     * Copy pinned while scrolling: {@code modifiers} e.g. {@code "pf-m-left"} or
     * {@code "pf-m-right pf-m-border-left"}, {@code style} the sticky-cell custom
     * properties verbatim (MinWidth / InsetInlineStart per PF's scrollable-table
     * recipe). Applies to the header cell and to this column's body cells.
     */
    public TableColumn stickyCell(String modifiers, String style) {
        return new TableColumn(kind, label, this.modifiers, sortKey, sort, subColumns,
                Objects.requireNonNull(modifiers, "modifiers"), Objects.requireNonNull(style, "style"), rowHeader);
    }

    /** Copy whose body cells render as row headers ({@code <th scope="row">}). */
    public TableColumn rowHeader() {
        return new TableColumn(kind, label, modifiers, sortKey, sort, subColumns,
                stickyModifiers, stickyStyle, true);
    }

    public boolean isGroup() {
        return !subColumns.isEmpty();
    }

    public List<String> subColumns() {
        return subColumns;
    }

    public int groupColspan() {
        return subColumns.size();
    }

    public boolean isSticky() {
        return stickyModifiers != null;
    }

    /** Full class list for a sticky header/body cell, e.g. "pf-v6-c-table__sticky-cell pf-m-left". */
    public String stickyClasses() {
        return "pf-v6-c-table__sticky-cell " + stickyModifiers;
    }

    public String stickyStyle() {
        return stickyStyle;
    }

    public boolean isRowHeaderColumn() {
        return rowHeader;
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

    public boolean isFavoriteColumn() {
        return kind == Kind.FAVORITE;
    }

    public boolean isDragColumn() {
        return kind == Kind.DRAG;
    }

    public boolean isInlineEditColumn() {
        return kind == Kind.INLINE_EDIT;
    }

    public String label() {
        return label;
    }

    public boolean hasModifiers() {
        return !modifiers.isEmpty();
    }

    /** Final header-cell class value (sticky classes + modifiers), or null for none. */
    public String headerCss() {
        List<String> parts = new ArrayList<>();
        if (isSticky()) {
            parts.add(stickyClasses());
        }
        if (!modifiers.isEmpty()) {
            parts.add(String.join(" ", modifiers));
        }
        return parts.isEmpty() ? null : String.join(" ", parts);
    }

    public String modifierClasses() {
        return String.join(" ", modifiers);
    }

    /** Modifier classes that body cells in this column repeat (visibility + nowrap). */
    public boolean hasCellModifiers() {
        return modifiers.stream().anyMatch(TableColumn::isCellInherited);
    }

    public String cellModifierClasses() {
        return modifiers.stream()
                .filter(TableColumn::isCellInherited)
                .reduce((a, b) -> a + " " + b).orElse("");
    }

    private static boolean isCellInherited(String m) {
        return m.startsWith("pf-m-hidden") || m.startsWith("pf-m-visible") || m.equals("pf-m-nowrap");
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
