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

    private final List<TableCell> cells;
    private final boolean striped;
    private final boolean checked;
    private final String clickKey;
    private final String detail;
    private final Table detailTable;
    private final String detailAriaLabel;
    private final boolean detailExpanded;
    private final boolean detailNoPadding;
    private final boolean detailNoBackground;
    private final boolean detailParagraph;
    private final int number;

    private TableRow(List<TableCell> cells, boolean striped, boolean checked, String clickKey,
             String detail, Table detailTable, String detailAriaLabel, boolean detailExpanded,
             boolean detailNoPadding, boolean detailNoBackground, boolean detailParagraph, int number) {
        this.cells = List.copyOf(cells);
        this.striped = striped;
        this.checked = checked;
        this.clickKey = clickKey;
        this.detail = detail;
        this.detailTable = detailTable;
        this.detailAriaLabel = detailAriaLabel;
        this.detailExpanded = detailExpanded;
        this.detailNoPadding = detailNoPadding;
        this.detailNoBackground = detailNoBackground;
        this.detailParagraph = detailParagraph;
        this.number = number;
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
        return new TableRow(list, false, false, null, null, null, null, false, false, false, false, 0);
    }

    /** Copy with {@code pf-m-striped} on the row (for manually striped tables). */
    public TableRow stripedRow() {
        return new TableRow(cells, true, checked, clickKey, detail, detailTable, detailAriaLabel,
                detailExpanded, detailNoPadding, detailNoBackground, detailParagraph, number);
    }

    /** Copy whose selection checkbox/radio starts checked. */
    public TableRow checkedRow() {
        return new TableRow(cells, striped, true, clickKey, detail, detailTable, detailAriaLabel,
                detailExpanded, detailNoPadding, detailNoBackground, detailParagraph, number);
    }

    /** Copy identified by a key for click-to-select tables (see {@link Table.Builder#clickable}). */
    public TableRow key(String clickKey) {
        return new TableRow(cells, striped, checked, Objects.requireNonNull(clickKey, "clickKey"),
                detail, detailTable, detailAriaLabel, detailExpanded, detailNoPadding, detailNoBackground,
                detailParagraph, number);
    }

    /**
     * Copy with expandable detail content (rendered in the expandable-row
     * body). The toggle's aria-label becomes
     * "Toggle details for {first cell text}" unless overridden.
     */
    public TableRow expandsTo(String detail) {
        return new TableRow(cells, striped, checked, clickKey,
                Objects.requireNonNull(detail, "detail"), detailTable, detailAriaLabel, detailExpanded,
                detailNoPadding, detailNoBackground, detailParagraph, number);
    }

    /**
     * Copy whose expandable detail row hosts a nested table, rendered flush
     * via {@code pf-m-no-padding}/{@code pf-m-no-background} per PatternFly's
     * nested-table recipe.
     */
    public TableRow expandsToTable(Table detailTable) {
        return new TableRow(cells, striped, checked, clickKey, detail,
                Objects.requireNonNull(detailTable, "detailTable"), detailAriaLabel, detailExpanded,
                true, true, detailParagraph, number);
    }

    /** Copy with an explicit toggle aria-label (default derives from the first cell). */
    public TableRow detailAriaLabel(String ariaLabel) {
        return new TableRow(cells, striped, checked, clickKey, detail, detailTable,
                Objects.requireNonNull(ariaLabel, "ariaLabel"), detailExpanded, detailNoPadding,
                detailNoBackground, detailParagraph, number);
    }

    /** Copy whose detail row starts expanded. */
    public TableRow expanded() {
        return new TableRow(cells, striped, checked, clickKey, detail, detailTable, detailAriaLabel,
                true, detailNoPadding, detailNoBackground, detailParagraph, number);
    }

    /** Copy whose detail content renders with pf-m-no-padding. */
    public TableRow noPaddingDetail() {
        return new TableRow(cells, striped, checked, clickKey, detail, detailTable, detailAriaLabel,
                detailExpanded, true, detailNoBackground, detailParagraph, number);
    }

    /** Copy whose detail text renders wrapped in a paragraph element. */
    public TableRow paragraphDetail() {
        return new TableRow(cells, striped, checked, clickKey, detail, detailTable, detailAriaLabel,
                detailExpanded, detailNoPadding, detailNoBackground, true, number);
    }

    /** Copy with resolved cells and its 1-based row number; called from Table.build(). */
    TableRow resolved(int number, List<TableCell> resolvedCells) {
        return new TableRow(resolvedCells, striped, checked, clickKey, detail, detailTable,
                detailAriaLabel, detailExpanded, detailNoPadding, detailNoBackground, detailParagraph, number);
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
