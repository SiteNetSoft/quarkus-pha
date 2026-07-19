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
    private final String detailAriaLabel;
    private final boolean detailExpanded;
    private final boolean detailNoPadding;
    private final boolean detailParagraph;
    private final int number;

    TableRow(List<TableCell> cells, boolean striped, boolean checked, String clickKey,
             String detail, String detailAriaLabel, boolean detailExpanded, boolean detailNoPadding) {
        this(cells, striped, checked, clickKey, detail, detailAriaLabel, detailExpanded, detailNoPadding, false, 0);
    }

    private TableRow(List<TableCell> cells, boolean striped, boolean checked, String clickKey,
             String detail, String detailAriaLabel, boolean detailExpanded, boolean detailNoPadding,
             boolean detailParagraph, int number) {
        this.number = number;
        this.detailParagraph = detailParagraph;
        this.cells = List.copyOf(cells);
        this.striped = striped;
        this.checked = checked;
        this.clickKey = clickKey;
        this.detail = detail;
        this.detailAriaLabel = detailAriaLabel;
        this.detailExpanded = detailExpanded;
        this.detailNoPadding = detailNoPadding;
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
        return new TableRow(list, false, false, null, null, null, false, false);
    }

    /** Copy with {@code pf-m-striped} on the row (for manually striped tables). */
    public TableRow stripedRow() {
        return new TableRow(cells, true, checked, clickKey, detail, detailAriaLabel, detailExpanded, detailNoPadding);
    }

    /** Copy whose selection checkbox/radio starts checked. */
    public TableRow checkedRow() {
        return new TableRow(cells, striped, true, clickKey, detail, detailAriaLabel, detailExpanded, detailNoPadding);
    }

    /** Copy identified by a key for click-to-select tables (see {@link Table.Builder#clickable}). */
    public TableRow key(String clickKey) {
        return new TableRow(cells, striped, checked, Objects.requireNonNull(clickKey, "clickKey"),
                detail, detailAriaLabel, detailExpanded, detailNoPadding);
    }

    /**
     * Copy with expandable detail content (rendered in the expandable-row
     * body). The toggle's aria-label becomes
     * "Toggle details for {first cell text}" unless overridden.
     */
    public TableRow expandsTo(String detail) {
        return new TableRow(cells, striped, checked, clickKey,
                Objects.requireNonNull(detail, "detail"), detailAriaLabel, detailExpanded, detailNoPadding);
    }

    /** Copy with an explicit toggle aria-label (default derives from the first cell). */
    public TableRow detailAriaLabel(String ariaLabel) {
        return new TableRow(cells, striped, checked, clickKey, detail,
                Objects.requireNonNull(ariaLabel, "ariaLabel"), detailExpanded, detailNoPadding);
    }

    /** Copy whose detail row starts expanded. */
    public TableRow expanded() {
        return new TableRow(cells, striped, checked, clickKey, detail, detailAriaLabel, true, detailNoPadding);
    }

    /** Copy whose detail content renders with pf-m-no-padding. */
    public TableRow noPaddingDetail() {
        return new TableRow(cells, striped, checked, clickKey, detail, detailAriaLabel, detailExpanded, true);
    }

    /** Copy whose detail text renders wrapped in a paragraph element. */
    public TableRow paragraphDetail() {
        return new TableRow(cells, striped, checked, clickKey, detail, detailAriaLabel,
                detailExpanded, detailNoPadding, true, number);
    }

    /** Copy with resolved cells and its 1-based row number; called from Table.build(). */
    TableRow resolved(int number, List<TableCell> resolvedCells) {
        return new TableRow(resolvedCells, striped, checked, clickKey, detail, detailAriaLabel,
                detailExpanded, detailNoPadding, detailParagraph, number);
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
        return detail != null;
    }

    public String detail() {
        return detail;
    }

    public boolean isDetailExpanded() {
        return detailExpanded;
    }

    public boolean isDetailNoPadding() {
        return detailNoPadding;
    }

    public boolean isDetailParagraph() {
        return detailParagraph;
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
