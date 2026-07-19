package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * One body/footer cell of a {@link Table}. Plain strings become text cells;
 * the static factories cover the other kinds. Immutable; the {@code withX}
 * copies attach per-cell presentation (modifiers, colspan, row-header).
 *
 * <p>{@link Table.Builder#build()} resolves each cell's final css class
 * string, data-label and DOM id from its column, so the template reads
 * finished attributes ({@link #css()}, {@link #dataLabel()}, {@link #domId()})
 * instead of re-deriving them.
 */
@TemplateData
public final class TableCell {

    public enum Kind { TEXT, LINK, KEBAB, ACTIONS, COMPOUND }

    private final Kind kind;
    private final String text;
    private final String href;
    private final String menuAriaLabel;
    private final List<TableAction> actions;
    private final List<String> modifiers;
    private final int colspan;
    private final boolean rowHeader;
    // compound expansion (Kind.COMPOUND)
    private final String expandKey;
    private final String detail;
    private final Table detailTable;
    private final boolean detailNoPadding;
    private final boolean detailNoBackground;
    private final boolean expanded;
    // resolved by Table.build()
    private final String css;
    private final String dataLabel;
    private final String domId;
    private String style;

    private TableCell(Kind kind, String text, String href, String menuAriaLabel,
                      List<TableAction> actions, List<String> modifiers, int colspan, boolean rowHeader,
                      String css, String dataLabel, String domId) {
        this(kind, text, href, menuAriaLabel, actions, modifiers, colspan, rowHeader,
                null, null, null, false, false, false, css, dataLabel, domId);
    }

    private TableCell(Kind kind, String text, String href, String menuAriaLabel,
                      List<TableAction> actions, List<String> modifiers, int colspan, boolean rowHeader,
                      String expandKey, String detail, Table detailTable,
                      boolean detailNoPadding, boolean detailNoBackground, boolean expanded,
                      String css, String dataLabel, String domId) {
        this.kind = kind;
        this.text = text;
        this.href = href;
        this.menuAriaLabel = menuAriaLabel;
        this.actions = List.copyOf(actions);
        this.modifiers = List.copyOf(modifiers);
        this.colspan = colspan;
        this.rowHeader = rowHeader;
        this.expandKey = expandKey;
        this.detail = detail;
        this.detailTable = detailTable;
        this.detailNoPadding = detailNoPadding;
        this.detailNoBackground = detailNoBackground;
        this.expanded = expanded;
        this.css = css;
        this.dataLabel = dataLabel;
        this.domId = domId;
    }

    /** Plain text cell. */
    public static TableCell text(String text) {
        return new TableCell(Kind.TEXT, Objects.requireNonNull(text, "text"), null, null,
                List.of(), List.of(), 1, false, null, null, null);
    }

    /** Cell containing a single anchor. */
    public static TableCell link(String text, String href) {
        return new TableCell(Kind.LINK, Objects.requireNonNull(text, "text"),
                Objects.requireNonNull(href, "href"), null, List.of(), List.of(), 1, false, null, null, null);
    }

    /**
     * Action cell with a kebab overflow menu ({@code pf-v6-c-table__action});
     * the menu aria-label should name the row, e.g. {@code "John Doe actions"}.
     */
    public static TableCell kebab(String menuAriaLabel, String... itemLabels) {
        List<TableAction> items = new ArrayList<>();
        for (String label : itemLabels) {
            items.add(TableAction.link(label));
        }
        return new TableCell(Kind.KEBAB, null, null,
                Objects.requireNonNull(menuAriaLabel, "menuAriaLabel"), items, List.of(), 1, false,
                null, null, null);
    }

    /** Action cell with inline buttons ({@code pf-v6-c-table__action}). */
    public static TableCell actions(TableAction... actions) {
        return new TableCell(Kind.ACTIONS, null, null, null, List.of(actions), List.of(), 1, false,
                null, null, null);
    }

    /**
     * Compound-expansion toggle cell ({@code pf-v6-c-table__compound-expansion-toggle}):
     * clicking it expands a detail row scoped to this cell, one open cell per
     * row at a time. {@code expandKey} identifies the cell in the row's Alpine
     * state (e.g. {@code "branches"}); {@code detail} is the detail-row text.
     */
    public static TableCell compound(String text, String expandKey, String detail) {
        return new TableCell(Kind.COMPOUND, Objects.requireNonNull(text, "text"), null, null,
                List.of(), List.of(), 1, false,
                Objects.requireNonNull(expandKey, "expandKey"), Objects.requireNonNull(detail, "detail"),
                null, false, false, false, null, null, null);
    }

    /**
     * Compound-expansion toggle cell whose detail row hosts a nested table
     * (rendered flush via {@code pf-m-no-padding}/{@code pf-m-no-background},
     * per PatternFly's nested-table recipe).
     */
    public static TableCell compoundTable(String text, String expandKey, Table detailTable) {
        return new TableCell(Kind.COMPOUND, Objects.requireNonNull(text, "text"), null, null,
                List.of(), List.of(), 1, false,
                Objects.requireNonNull(expandKey, "expandKey"), null,
                Objects.requireNonNull(detailTable, "detailTable"), true, true, false, null, null, null);
    }

    /** Copy of a compound cell whose detail row starts expanded. */
    public TableCell expanded() {
        requireCompound("expanded()");
        return new TableCell(kind, text, href, menuAriaLabel, actions, modifiers, colspan, rowHeader,
                expandKey, detail, detailTable, detailNoPadding, detailNoBackground, true,
                css, dataLabel, domId);
    }

    /** Copy of a compound cell whose detail cell renders with pf-m-no-padding. */
    public TableCell noPaddingDetail() {
        requireCompound("noPaddingDetail()");
        return new TableCell(kind, text, href, menuAriaLabel, actions, modifiers, colspan, rowHeader,
                expandKey, detail, detailTable, true, detailNoBackground, expanded,
                css, dataLabel, domId);
    }

    /** Copy of a compound cell whose detail content renders with pf-m-no-background. */
    public TableCell noBackgroundDetail() {
        requireCompound("noBackgroundDetail()");
        return new TableCell(kind, text, href, menuAriaLabel, actions, modifiers, colspan, rowHeader,
                expandKey, detail, detailTable, detailNoPadding, true, expanded,
                css, dataLabel, domId);
    }

    private void requireCompound(String method) {
        if (kind != Kind.COMPOUND) {
            throw new IllegalStateException(method + " only applies to compound cells");
        }
    }

    /** Copy with a text-control/width modifier class, e.g. {@code pf-m-truncate}. */
    public TableCell withModifier(String modifierClass) {
        List<String> m = new ArrayList<>(modifiers);
        m.add(modifierClass);
        return new TableCell(kind, text, href, menuAriaLabel, actions, m, colspan, rowHeader,
                expandKey, detail, detailTable, detailNoPadding, detailNoBackground, expanded,
                css, dataLabel, domId);
    }

    /** Copy spanning the given number of columns. */
    public TableCell withColspan(int colspan) {
        return new TableCell(kind, text, href, menuAriaLabel, actions, modifiers, colspan, rowHeader,
                expandKey, detail, detailTable, detailNoPadding, detailNoBackground, expanded,
                css, dataLabel, domId);
    }

    /** Copy rendered as a row-header {@code <th scope="row">}. */
    public TableCell asRowHeader() {
        return new TableCell(kind, text, href, menuAriaLabel, actions, modifiers, colspan, true,
                expandKey, detail, detailTable, detailNoPadding, detailNoBackground, expanded,
                css, dataLabel, domId);
    }

    /** Copy with the attributes resolved against a column; called from Table.build(). */
    TableCell resolved(TableColumn column, boolean emitDataLabel, String domId) {
        List<String> classes = new ArrayList<>();
        if (kind == Kind.KEBAB || kind == Kind.ACTIONS) {
            classes.add("pf-v6-c-table__action");
        }
        if (kind == Kind.COMPOUND) {
            classes.add("pf-v6-c-table__compound-expansion-toggle");
        }
        if (column != null && column.isSticky()) {
            classes.add(column.stickyClasses());
        }
        classes.addAll(modifiers);
        if (column != null && column.hasCellModifiers()) {
            classes.add(column.cellModifierClasses());
        }
        String css = classes.isEmpty() ? null : String.join(" ", classes);
        String label = emitDataLabel && column != null && column.isTextColumn() ? column.label() : null;
        boolean header = rowHeader || (column != null && column.isRowHeaderColumn());
        TableCell r = new TableCell(kind, text, href, menuAriaLabel, actions, modifiers, colspan, header,
                expandKey, detail, detailTable, detailNoPadding, detailNoBackground, expanded,
                css, label, domId);
        if (column != null && column.isSticky()) {
            r.style = column.stickyStyle();
        }
        return r;
    }

    public boolean isText() {
        return kind == Kind.TEXT;
    }

    public boolean isLink() {
        return kind == Kind.LINK;
    }

    public boolean isKebab() {
        return kind == Kind.KEBAB;
    }

    public boolean isActionsCell() {
        return kind == Kind.ACTIONS;
    }

    public boolean isCompound() {
        return kind == Kind.COMPOUND;
    }

    /** Alpine state key identifying this compound cell within its row. */
    public String expandKey() {
        return expandKey;
    }

    /** Detail-row text of a compound cell, or null when the detail is a nested table. */
    public String detail() {
        return detail;
    }

    /** Nested table rendered in this compound cell's detail row, or null. */
    public Table detailTable() {
        return detailTable;
    }

    public boolean isDetailNoPadding() {
        return detailNoPadding;
    }

    public boolean isDetailNoBackground() {
        return detailNoBackground;
    }

    /** True when this compound cell's detail row starts expanded. */
    public boolean isExpanded() {
        return expanded;
    }

    public String text() {
        return text;
    }

    public String href() {
        return href;
    }

    public String menuAriaLabel() {
        return menuAriaLabel;
    }

    public List<TableAction> actions() {
        return actions;
    }

    public int colspan() {
        return colspan;
    }

    public boolean isSpanning() {
        return colspan > 1;
    }

    public boolean isRowHeader() {
        return rowHeader;
    }

    /** Final class attribute value, or null for none. */
    public String css() {
        return css;
    }

    /** Final data-label attribute value, or null for none. */
    public String dataLabel() {
        return dataLabel;
    }

    /** Final id attribute value (selectable tables label rows by first cell), or null. */
    public String domId() {
        return domId;
    }

    /** Final inline style value (sticky-cell custom properties), or null. */
    public String style() {
        return style;
    }
}
