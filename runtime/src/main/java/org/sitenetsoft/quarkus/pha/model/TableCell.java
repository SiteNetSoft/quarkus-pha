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

    public enum Kind { TEXT, LINK, KEBAB, ACTIONS, COMPOUND, EMPTY_STATE, OVERFLOW_MENU, TEXT_STACK }

    private Kind kind;
    private String text;
    private String href;
    private String menuAriaLabel;
    private List<TableAction> actions = List.of();
    private List<String> modifiers = List.of();
    private int colspan = 1;
    private boolean rowHeader;
    // compound expansion (Kind.COMPOUND)
    private String expandKey;
    private String detail;
    private Table detailTable;
    private boolean detailNoPadding;
    private boolean detailNoBackground;
    private boolean expanded;
    // empty state (Kind.EMPTY_STATE)
    private String emptyIcon;
    private String emptyBody;
    // table-text wrapping and stacks (Kind.TEXT_STACK)
    private boolean tableText;
    private List<TableCell> items = List.of();
    // resolved by Table.build()
    private String css;
    private String dataLabel;
    private String domId;
    private String style;
    private String editVar;
    private String editDraft;
    private String editLabel;

    private TableCell() {
    }

    private TableCell copy() {
        TableCell c = new TableCell();
        c.kind = kind;
        c.text = text;
        c.href = href;
        c.menuAriaLabel = menuAriaLabel;
        c.actions = actions;
        c.modifiers = modifiers;
        c.colspan = colspan;
        c.rowHeader = rowHeader;
        c.expandKey = expandKey;
        c.detail = detail;
        c.detailTable = detailTable;
        c.detailNoPadding = detailNoPadding;
        c.detailNoBackground = detailNoBackground;
        c.expanded = expanded;
        c.emptyIcon = emptyIcon;
        c.emptyBody = emptyBody;
        c.tableText = tableText;
        c.items = items;
        c.css = css;
        c.dataLabel = dataLabel;
        c.domId = domId;
        c.style = style;
        c.editVar = editVar;
        c.editDraft = editDraft;
        c.editLabel = editLabel;
        return c;
    }

    /** Plain text cell. */
    public static TableCell text(String text) {
        TableCell c = new TableCell();
        c.kind = Kind.TEXT;
        c.text = Objects.requireNonNull(text, "text");
        return c;
    }

    /** Cell containing a single anchor. */
    public static TableCell link(String text, String href) {
        TableCell c = new TableCell();
        c.kind = Kind.LINK;
        c.text = Objects.requireNonNull(text, "text");
        c.href = Objects.requireNonNull(href, "href");
        return c;
    }

    /**
     * Action cell with a kebab overflow menu ({@code pf-v6-c-table__action});
     * the menu aria-label should name the row, e.g. {@code "John Doe actions"}.
     */
    public static TableCell kebab(String menuAriaLabel, String... itemLabels) {
        TableCell c = new TableCell();
        c.kind = Kind.KEBAB;
        c.menuAriaLabel = Objects.requireNonNull(menuAriaLabel, "menuAriaLabel");
        List<TableAction> list = new ArrayList<>();
        for (String label : itemLabels) {
            list.add(TableAction.link(label));
        }
        c.actions = List.copyOf(list);
        return c;
    }

    /** Action cell with inline buttons ({@code pf-v6-c-table__action}). */
    public static TableCell actions(TableAction... actions) {
        TableCell c = new TableCell();
        c.kind = Kind.ACTIONS;
        c.actions = List.of(actions);
        return c;
    }

    /**
     * Action cell with a responsive overflow menu ({@code pf-v6-c-overflow-menu}):
     * a visible button group on lg and up, collapsing to a kebab menu with the
     * same actions below. The aria-label should name the row, e.g.
     * {@code "nightly-report actions"}.
     */
    public static TableCell overflowMenu(String menuAriaLabel, TableAction... actions) {
        TableCell c = new TableCell();
        c.kind = Kind.OVERFLOW_MENU;
        c.menuAriaLabel = Objects.requireNonNull(menuAriaLabel, "menuAriaLabel");
        c.actions = List.of(actions);
        return c;
    }

    /**
     * Full-width "no results" cell hosting the empty-state component
     * (PatternFly's pattern for a data table with no rows). Spans all data
     * columns unless an explicit colspan is set.
     */
    public static TableCell emptyState(String iconName, String title, String body) {
        TableCell c = new TableCell();
        c.kind = Kind.EMPTY_STATE;
        c.emptyIcon = Objects.requireNonNull(iconName, "iconName");
        c.text = Objects.requireNonNull(title, "title");
        c.emptyBody = Objects.requireNonNull(body, "body");
        return c;
    }

    /**
     * Cell stacking several text/link entries in a gutter stack layout, each
     * wrapped in the {@code pf-v6-c-table__text} element.
     */
    public static TableCell textStack(TableCell... items) {
        TableCell c = new TableCell();
        c.kind = Kind.TEXT_STACK;
        for (TableCell item : items) {
            if (item.kind != Kind.TEXT && item.kind != Kind.LINK) {
                throw new IllegalArgumentException("textStack entries must be text or link cells");
            }
        }
        c.items = List.of(items);
        return c;
    }

    /**
     * Compound-expansion toggle cell ({@code pf-v6-c-table__compound-expansion-toggle}):
     * clicking it expands a detail row scoped to this cell, one open cell per
     * row at a time. {@code expandKey} identifies the cell in the row's Alpine
     * state (e.g. {@code "branches"}); {@code detail} is the detail-row text.
     */
    public static TableCell compound(String text, String expandKey, String detail) {
        TableCell c = new TableCell();
        c.kind = Kind.COMPOUND;
        c.text = Objects.requireNonNull(text, "text");
        c.expandKey = Objects.requireNonNull(expandKey, "expandKey");
        c.detail = Objects.requireNonNull(detail, "detail");
        return c;
    }

    /**
     * Compound-expansion toggle cell whose detail row hosts a nested table
     * (rendered flush via {@code pf-m-no-padding}/{@code pf-m-no-background},
     * per PatternFly's nested-table recipe).
     */
    public static TableCell compoundTable(String text, String expandKey, Table detailTable) {
        TableCell c = new TableCell();
        c.kind = Kind.COMPOUND;
        c.text = Objects.requireNonNull(text, "text");
        c.expandKey = Objects.requireNonNull(expandKey, "expandKey");
        c.detailTable = Objects.requireNonNull(detailTable, "detailTable");
        c.detailNoPadding = true;
        c.detailNoBackground = true;
        return c;
    }

    /** Copy of a compound cell whose detail row starts expanded. */
    public TableCell expanded() {
        requireCompound("expanded()");
        TableCell c = copy();
        c.expanded = true;
        return c;
    }

    /** Copy of a compound cell whose detail cell renders with pf-m-no-padding. */
    public TableCell noPaddingDetail() {
        requireCompound("noPaddingDetail()");
        TableCell c = copy();
        c.detailNoPadding = true;
        return c;
    }

    /** Copy of a compound cell whose detail content renders with pf-m-no-background. */
    public TableCell noBackgroundDetail() {
        requireCompound("noBackgroundDetail()");
        TableCell c = copy();
        c.detailNoBackground = true;
        return c;
    }

    private void requireCompound(String method) {
        if (kind != Kind.COMPOUND) {
            throw new IllegalStateException(method + " only applies to compound cells");
        }
    }

    /** Copy whose content is wrapped in the {@code pf-v6-c-table__text} element. */
    public TableCell asTableText() {
        TableCell c = copy();
        c.tableText = true;
        return c;
    }

    /** Copy with a text-control/width modifier class, e.g. {@code pf-m-truncate}. */
    public TableCell withModifier(String modifierClass) {
        TableCell c = copy();
        List<String> m = new ArrayList<>(modifiers);
        m.add(modifierClass);
        c.modifiers = List.copyOf(m);
        return c;
    }

    /** Copy spanning the given number of columns. */
    public TableCell withColspan(int colspan) {
        TableCell c = copy();
        c.colspan = colspan;
        return c;
    }

    /** Copy rendered as a row-header {@code <th scope="row">}. */
    public TableCell asRowHeader() {
        TableCell c = copy();
        c.rowHeader = true;
        return c;
    }

    /** Copy with inline-edit bindings; called from Table.build(). */
    TableCell withEditBindings(String editVar, String editDraft, String editLabel) {
        TableCell c = copy();
        c.editVar = editVar;
        c.editDraft = editDraft;
        c.editLabel = editLabel;
        return c;
    }

    /** Copy with the attributes resolved against a column; called from Table.build(). */
    TableCell resolved(TableColumn column, boolean emitDataLabel, String domId) {
        List<String> classes = new ArrayList<>();
        if (kind == Kind.KEBAB || kind == Kind.ACTIONS || kind == Kind.OVERFLOW_MENU) {
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
        TableCell c = copy();
        c.css = classes.isEmpty() ? null : String.join(" ", classes);
        c.dataLabel = emitDataLabel && column != null && column.isTextColumn() ? column.label() : null;
        c.domId = domId;
        if (rowHeader || (column != null && column.isRowHeaderColumn())) {
            c.rowHeader = true;
        }
        if (column != null && column.isSticky()) {
            c.style = column.stickyStyle();
        }
        return c;
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

    public boolean isEmptyStateCell() {
        return kind == Kind.EMPTY_STATE;
    }

    public boolean isOverflowMenu() {
        return kind == Kind.OVERFLOW_MENU;
    }

    public boolean isTextStack() {
        return kind == Kind.TEXT_STACK;
    }

    /** True when the cell content renders inside a pf-v6-c-table__text wrapper. */
    public boolean isTableText() {
        return tableText;
    }

    /** Entries of a text-stack cell. */
    public List<TableCell> items() {
        return items;
    }

    /** Icon name of an empty-state cell, e.g. {@code "fa:magnifying-glass"}. */
    public String emptyIcon() {
        return emptyIcon;
    }

    /** Body text of an empty-state cell. */
    public String emptyBody() {
        return emptyBody;
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

    /** Alpine variable holding this cell's committed value in inline-edit tables. */
    public String editVar() {
        return editVar;
    }

    /** Alpine variable holding this cell's draft value in inline-edit tables. */
    public String editDraft() {
        return editDraft;
    }

    /** Column label of this cell in inline-edit tables (input aria-label). */
    public String editLabel() {
        return editLabel;
    }

    public boolean hasModifiers() {
        return !modifiers.isEmpty();
    }

    /** The cell's own modifier classes (text-stack items render these directly). */
    public String modifierClasses() {
        return String.join(" ", modifiers);
    }
}
