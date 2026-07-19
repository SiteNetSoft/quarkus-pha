package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * One body row of a {@link Table}. Build with {@link Table#row(Object...)}
 * (varargs of Strings and/or {@link TableCell}s) and refine with the
 * {@code withX} copies for striping, selection, click-selection keys, and
 * expandable detail content.
 */
@TemplateData
public final class TableRow {

    private List<TableCell> cells = List.of();
    private boolean striped;
    private boolean checked;
    private String clickKey;
    private String detail;
    private Table detailTable;
    private String detailAriaLabel;
    private boolean detailExpanded;
    private boolean detailNoPadding;
    private boolean detailNoBackground;
    private boolean detailParagraph;
    private boolean favorited;
    // resolved by Table.build()
    private int number;
    private String rowXData;
    private String favExpr;
    private String selExpr;
    private String editStartExpr;
    private String editSaveExpr;

    private TableRow() {
    }

    private TableRow copy() {
        TableRow r = new TableRow();
        r.cells = cells;
        r.striped = striped;
        r.checked = checked;
        r.clickKey = clickKey;
        r.detail = detail;
        r.detailTable = detailTable;
        r.detailAriaLabel = detailAriaLabel;
        r.detailExpanded = detailExpanded;
        r.detailNoPadding = detailNoPadding;
        r.detailNoBackground = detailNoBackground;
        r.detailParagraph = detailParagraph;
        r.favorited = favorited;
        r.number = number;
        r.rowXData = rowXData;
        r.favExpr = favExpr;
        r.selExpr = selExpr;
        r.editStartExpr = editStartExpr;
        r.editSaveExpr = editSaveExpr;
        return r;
    }

    static TableRow of(Object... cells) {
        List<TableCell> list = new ArrayList<>();
        for (Object c : cells) {
            if (c instanceof TableCell cell) {
                list.add(cell);
            } else if (c instanceof String s) {
                list.add(TableCell.text(s));
            } else {
                throw new IllegalArgumentException(
                        "Row cells must be String or TableCell, got: " + (c == null ? "null" : c.getClass()));
            }
        }
        TableRow r = new TableRow();
        r.cells = List.copyOf(list);
        return r;
    }

    /** Copy with {@code pf-m-striped} on the row (for manually striped tables). */
    public TableRow stripedRow() {
        TableRow r = copy();
        r.striped = true;
        return r;
    }

    /** Copy whose selection checkbox/radio starts checked. */
    public TableRow checkedRow() {
        TableRow r = copy();
        r.checked = true;
        return r;
    }

    /** Copy whose favorite star starts on (favoritable tables). */
    public TableRow favorited() {
        TableRow r = copy();
        r.favorited = true;
        return r;
    }

    /**
     * Copy identified by a key used for click-to-select rows, tri-state
     * selection maps and favorites sorting (see {@link Table.Builder}).
     */
    public TableRow key(String clickKey) {
        TableRow r = copy();
        r.clickKey = Objects.requireNonNull(clickKey, "clickKey");
        return r;
    }

    /**
     * Copy with expandable detail content (rendered in the expandable-row
     * body). The toggle's aria-label becomes
     * "Toggle details for {first cell text}" unless overridden.
     */
    public TableRow expandsTo(String detail) {
        TableRow r = copy();
        r.detail = Objects.requireNonNull(detail, "detail");
        return r;
    }

    /**
     * Copy whose expandable detail row hosts a nested table, rendered flush
     * via {@code pf-m-no-padding}/{@code pf-m-no-background} per PatternFly's
     * nested-table recipe.
     */
    public TableRow expandsToTable(Table detailTable) {
        TableRow r = copy();
        r.detailTable = Objects.requireNonNull(detailTable, "detailTable");
        r.detailNoPadding = true;
        r.detailNoBackground = true;
        return r;
    }

    /** Copy with an explicit toggle aria-label (default derives from the first cell). */
    public TableRow detailAriaLabel(String ariaLabel) {
        TableRow r = copy();
        r.detailAriaLabel = Objects.requireNonNull(ariaLabel, "ariaLabel");
        return r;
    }

    /** Copy whose detail row starts expanded. */
    public TableRow expanded() {
        TableRow r = copy();
        r.detailExpanded = true;
        return r;
    }

    /** Copy whose detail content renders with pf-m-no-padding. */
    public TableRow noPaddingDetail() {
        TableRow r = copy();
        r.detailNoPadding = true;
        return r;
    }

    /** Copy whose detail text renders wrapped in a paragraph element. */
    public TableRow paragraphDetail() {
        TableRow r = copy();
        r.detailParagraph = true;
        return r;
    }

    /** Copy with resolved cells and its 1-based row number; called from Table.build(). */
    TableRow resolved(int number, List<TableCell> resolvedCells) {
        TableRow r = copy();
        r.number = number;
        r.cells = List.copyOf(resolvedCells);
        return r;
    }

    /** Copy with the row-level Alpine bindings; called from Table.build(). */
    TableRow withAlpine(String rowXData, String favExpr, String selExpr) {
        TableRow r = copy();
        r.rowXData = rowXData;
        r.favExpr = favExpr;
        r.selExpr = selExpr;
        return r;
    }

    /** 1-based row number within the table, assigned at build(). */
    public int number() {
        return number;
    }

    public List<TableCell> cells() {
        return cells;
    }

    public boolean isStriped() {
        return striped;
    }

    public boolean isChecked() {
        return checked;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public String clickKey() {
        return clickKey;
    }

    public boolean isExpandable() {
        return detail != null || detailTable != null;
    }

    public String detail() {
        return detail;
    }

    /** Nested table rendered in the expandable detail row, or null. */
    public Table detailTable() {
        return detailTable;
    }

    public boolean isDetailExpanded() {
        return detailExpanded;
    }

    public boolean isDetailNoPadding() {
        return detailNoPadding;
    }

    public boolean isDetailNoBackground() {
        return detailNoBackground;
    }

    public boolean isDetailParagraph() {
        return detailParagraph;
    }

    /** True when any cell is a compound-expansion toggle (renders as a control row). */
    public boolean isCompound() {
        return cells.stream().anyMatch(TableCell::isCompound);
    }

    /** expandKey of the compound cell that starts expanded, or null for none. */
    public String compoundExpandedKey() {
        return cells.stream()
                .filter(c -> c.isCompound() && c.isExpanded())
                .map(TableCell::expandKey)
                .findFirst().orElse(null);
    }

    /** Row-level Alpine x-data (favoritable / inline-edit rows), or null. */
    public String rowXData() {
        return rowXData;
    }

    /** Alpine expression for this row's favorite state, e.g. {@code fav} or {@code fav.john}. */
    public String favExpr() {
        return favExpr;
    }

    /** Alpine x-model expression for this row's checkbox in tri-state selection, e.g. {@code sel.john}. */
    public String selExpr() {
        return selExpr;
    }

    /** Copy with the inline-edit expressions; called from Table.build(). */
    TableRow withEditExprs(String startExpr, String saveExpr) {
        TableRow r = copy();
        r.editStartExpr = startExpr;
        r.editSaveExpr = saveExpr;
        return r;
    }

    /** Alpine expression starting an inline edit (copies values into drafts). */
    public String editStartExpr() {
        return editStartExpr;
    }

    /** Alpine expression committing an inline edit (copies drafts into values). */
    public String editSaveExpr() {
        return editSaveExpr;
    }

    /** aria-label for the expand toggle button. */
    public String toggleAriaLabel() {
        if (detailAriaLabel != null) {
            return detailAriaLabel;
        }
        String subject = cells.isEmpty() || cells.get(0).text() == null ? "row" : cells.get(0).text();
        return "Toggle details for " + subject;
    }

    /** First-cell text, used to derive per-row input names/ids in selectable tables. */
    public String firstCellText() {
        return cells.isEmpty() ? "" : String.valueOf(cells.get(0).text());
    }
}
