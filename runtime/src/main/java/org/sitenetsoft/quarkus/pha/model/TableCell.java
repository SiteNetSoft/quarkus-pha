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

    public enum Kind { TEXT, LINK, KEBAB, ACTIONS }

    private final Kind kind;
    private final String text;
    private final String href;
    private final String menuAriaLabel;
    private final List<TableAction> actions;
    private final List<String> modifiers;
    private final int colspan;
    private final boolean rowHeader;
    // resolved by Table.build()
    private final String css;
    private final String dataLabel;
    private final String domId;
    private String style;

    private TableCell(Kind kind, String text, String href, String menuAriaLabel,
                      List<TableAction> actions, List<String> modifiers, int colspan, boolean rowHeader,
                      String css, String dataLabel, String domId) {
        this.kind = kind;
        this.text = text;
        this.href = href;
        this.menuAriaLabel = menuAriaLabel;
        this.actions = List.copyOf(actions);
        this.modifiers = List.copyOf(modifiers);
        this.colspan = colspan;
        this.rowHeader = rowHeader;
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

    /** Copy with a text-control/width modifier class, e.g. {@code pf-m-truncate}. */
    public TableCell withModifier(String modifierClass) {
        List<String> m = new ArrayList<>(modifiers);
        m.add(modifierClass);
        return new TableCell(kind, text, href, menuAriaLabel, actions, m, colspan, rowHeader,
                css, dataLabel, domId);
    }

    /** Copy spanning the given number of columns. */
    public TableCell withColspan(int colspan) {
        return new TableCell(kind, text, href, menuAriaLabel, actions, modifiers, colspan, rowHeader,
                css, dataLabel, domId);
    }

    /** Copy rendered as a row-header {@code <th scope="row">}. */
    public TableCell asRowHeader() {
        return new TableCell(kind, text, href, menuAriaLabel, actions, modifiers, colspan, true,
                css, dataLabel, domId);
    }

    /** Copy with the attributes resolved against a column; called from Table.build(). */
    TableCell resolved(TableColumn column, boolean emitDataLabel, String domId) {
        List<String> classes = new ArrayList<>();
        if (kind == Kind.KEBAB || kind == Kind.ACTIONS) {
            classes.add("pf-v6-c-table__action");
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
