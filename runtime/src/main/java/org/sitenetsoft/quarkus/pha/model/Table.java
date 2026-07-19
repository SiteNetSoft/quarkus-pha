package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Table component ({@code pf-v6-c-table}).
 *
 * <p>Describe the table as data and the runtime template renders the full
 * PatternFly markup — header cells with sort buttons, selection inputs,
 * expandable-row toggles with their Alpine wiring, action kebabs, captions and
 * footers:
 *
 * <pre>
 * Table table = Table.builder()
 *     .id("users").ariaLabel("Users")
 *     .column("Name")
 *     .column(TableColumn.of("Status").sortable("status"))
 *     .actionColumn()
 *     .sortEndpoint("/api/htmx/table-sort")
 *     .row("John Doe", "Active", TableCell.kebab("John Doe actions", "Edit", "Delete"))
 *     .row(Table.row("Jane Smith", "Inactive",
 *             TableCell.actions(TableAction.secondary("Edit"), TableAction.danger("Delete"))))
 *     .build();
 * </pre>
 *
 * Template side: <code>{#include components/data-display/table table=table /}</code>
 *
 * <p>Batch-1 coverage: density/striping/border variants, captions, footers,
 * column width/text/visibility modifiers, multiple striped bodies, sortable
 * headers (server-driven via HTMX), checkbox/radio selection, click-to-select
 * rows, and single-level expandable rows. Tree tables, compound expansion,
 * sticky layouts, nested headers, and editable/draggable rows are not yet
 * modeled — compose those by hand in the content slot.
 */
@TemplateData
public final class Table {

    public enum Selection { NONE, CHECKBOX, RADIO }

    private final String id;
    private final String ariaLabel;
    private final String caption;
    private final boolean compact;
    private final boolean borderless;
    private final boolean striped;
    private final boolean gridMd;
    private final boolean dataLabels;
    private final List<String> modifiers;
    private final List<TableColumn> columns;
    private final List<TableBody> bodies;
    private final TableRow footerRow;
    private final Selection selection;
    private final boolean clickable;
    private final String initialClickKey;
    private final String sortEndpoint;
    private final boolean stickyHeader;
    private final boolean stickyFooter;
    private final boolean scrollOuter;
    private final String scrollWrapperId;
    private final String scrollWrapperStyle;

    private Table(Builder b, List<TableBody> bodies) {
        this.stickyHeader = b.stickyHeader;
        this.stickyFooter = b.stickyFooter;
        this.scrollOuter = b.scrollOuter;
        this.scrollWrapperId = b.scrollWrapperId;
        this.scrollWrapperStyle = b.scrollWrapperStyle;
        this.id = b.id;
        this.ariaLabel = b.ariaLabel;
        this.caption = b.caption;
        this.compact = b.compact;
        this.borderless = b.borderless;
        this.striped = b.striped;
        this.gridMd = b.gridMd;
        this.dataLabels = b.dataLabels;
        this.modifiers = List.copyOf(b.modifiers);
        this.columns = List.copyOf(b.columns);
        this.bodies = List.copyOf(bodies);
        this.footerRow = b.footerRow;
        this.selection = b.selection;
        this.clickable = b.clickable;
        this.initialClickKey = b.initialClickKey;
        this.sortEndpoint = b.sortEndpoint;
    }

    /** Row factory for refined rows: {@code Table.row("a", "b").stripedRow()}. */
    public static TableRow row(Object... cells) {
        return TableRow.of(cells);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String id() {
        return id;
    }

    public String ariaLabel() {
        return ariaLabel;
    }

    public String caption() {
        return caption;
    }

    public boolean isCompact() {
        return compact;
    }

    public boolean isBorderless() {
        return borderless;
    }

    public boolean isStriped() {
        return striped;
    }

    public boolean isGridMd() {
        return gridMd;
    }

    public boolean isDataLabels() {
        return dataLabels;
    }

    public String extraModifiers() {
        return modifiers.isEmpty() ? "" : " " + String.join(" ", modifiers);
    }

    public List<TableColumn> columns() {
        return columns;
    }

    public List<TableBody> bodies() {
        return bodies;
    }

    public TableRow footerRow() {
        return footerRow;
    }

    public boolean isCheckboxSelect() {
        return selection == Selection.CHECKBOX;
    }

    public boolean isRadioSelect() {
        return selection == Selection.RADIO;
    }

    public boolean isClickable() {
        return clickable;
    }

    public String initialClickKey() {
        return initialClickKey == null ? "" : initialClickKey;
    }

    public String sortEndpoint() {
        return sortEndpoint;
    }

    /** True when any row carries expandable detail content. */
    public boolean isExpandable() {
        return bodies.stream().flatMap(b -> b.rows().stream()).anyMatch(TableRow::isExpandable);
    }

    /** True when {@code pf-m-animate-expand} is among the modifiers (detail rows stay rendered). */
    public boolean isAnimateExpand() {
        return modifiers.contains("pf-m-animate-expand");
    }

    /** Columns that correspond to row cells (check and toggle columns are auto-rendered;
     * column groups flatten to their sub-columns). */
    public List<TableColumn> dataColumns() {
        List<TableColumn> flat = new ArrayList<>();
        for (TableColumn c : columns) {
            if (c.isCheckColumn() || c.isToggleColumn()) {
                continue;
            }
            if (c.isGroup()) {
                for (String sub : c.subColumns()) {
                    flat.add(TableColumn.of(sub));
                }
            } else {
                flat.add(c);
            }
        }
        return List.copyOf(flat);
    }

    /** True when any column is a group — renders the two-row nested column header. */
    public boolean isNestedHeader() {
        return columns.stream().anyMatch(TableColumn::isGroup);
    }

    public boolean isStickyHeader() {
        return stickyHeader;
    }

    public boolean isStickyFooter() {
        return stickyFooter;
    }

    /** True when the table renders inside a scroll wrapper. */
    public boolean isScrollWrapped() {
        return scrollOuter || scrollWrapperId != null || scrollWrapperStyle != null;
    }

    public boolean isScrollOuter() {
        return scrollOuter;
    }

    public String scrollWrapperId() {
        return scrollWrapperId;
    }

    public String scrollWrapperStyle() {
        return scrollWrapperStyle;
    }

    public boolean hasSelection() {
        return selection != Selection.NONE;
    }

    /** Number of columns a detail cell spans (all columns minus the toggle column). */
    public int detailColspan() {
        return Math.max(1, columns.size() - 1);
    }

    public static final class Builder {

        private String id;
        private String ariaLabel = "Data table";
        private String caption;
        private boolean compact;
        private boolean borderless;
        private boolean striped;
        private boolean gridMd;
        private boolean dataLabels;
        private final List<String> modifiers = new ArrayList<>();
        private final List<TableColumn> columns = new ArrayList<>();
        private final List<TableBody> doneBodies = new ArrayList<>();
        private TableBody.Stripe currentStripe = TableBody.Stripe.NONE;
        private List<TableRow> currentRows = new ArrayList<>();
        private TableRow footerRow;
        private Selection selection = Selection.NONE;
        private boolean clickable;
        private String initialClickKey;
        private String sortEndpoint;
        private boolean stickyHeader;
        private boolean stickyFooter;
        private boolean scrollOuter;
        private String scrollWrapperId;
        private String scrollWrapperStyle;

        private Builder() {
        }

        /** Pin the header while scrolling ({@code pf-m-sticky-header}). */
        public Builder stickyHeader() {
            this.stickyHeader = true;
            return this;
        }

        /** Pin the footer while scrolling ({@code pf-m-sticky-footer}). */
        public Builder stickyFooter() {
            this.stickyFooter = true;
            return this;
        }

        /**
         * Wrap in outer + inner scroll wrappers with the given inner style,
         * e.g. {@code "max-block-size: 13rem; overflow: auto"}.
         */
        public Builder scrollable(String innerStyle) {
            this.scrollOuter = true;
            this.scrollWrapperStyle = innerStyle;
            return this;
        }

        /** Wrap in an inner scroll wrapper only, carrying the given DOM id. */
        public Builder scrollableInner(String wrapperId) {
            this.scrollWrapperId = wrapperId;
            return this;
        }

        /** Wrap in an inner scroll wrapper only, carrying the given inline style. */
        public Builder scrollableInnerStyled(String style) {
            this.scrollWrapperStyle = style;
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        public Builder caption(String caption) {
            this.caption = caption;
            return this;
        }

        public Builder compact() {
            this.compact = true;
            return this;
        }

        /** No row borders ({@code pf-m-no-border-rows}). */
        public Builder borderless() {
            this.borderless = true;
            return this;
        }

        public Builder striped() {
            this.striped = true;
            return this;
        }

        /** Responsive grid fallback below md; also enables per-cell data-labels. */
        public Builder gridMd() {
            this.gridMd = true;
            this.dataLabels = true;
            return this;
        }

        /** Emit data-label attributes without the gridMd class (e.g. with pf-m-grid-lg). */
        public Builder dataLabels() {
            this.dataLabels = true;
            return this;
        }

        /** Extra root modifier class, e.g. {@code pf-m-plain} or {@code pf-m-grid-lg}. */
        public Builder modifier(String modifierClass) {
            modifiers.add(modifierClass);
            return this;
        }

        public Builder column(String label) {
            columns.add(TableColumn.of(label));
            return this;
        }

        public Builder column(TableColumn column) {
            columns.add(column);
            return this;
        }

        /** Leading select-all checkbox column; implies checkbox selection. */
        public Builder checkColumn() {
            columns.add(TableColumn.check());
            if (selection == Selection.NONE) {
                selection = Selection.CHECKBOX;
            }
            return this;
        }

        /** Leading empty header cell above expandable-row toggles. */
        public Builder toggleColumn() {
            columns.add(TableColumn.toggle());
            return this;
        }

        public Builder actionColumn() {
            columns.add(TableColumn.action());
            return this;
        }

        public Builder actionColumn(String label) {
            columns.add(TableColumn.action(label));
            return this;
        }

        /** Radio-button row selection (single-select); use with a leading check column. */
        public Builder radioSelection() {
            this.selection = Selection.RADIO;
            return this;
        }

        /** Rows select on click; pass the initially selected row key (or null). */
        public Builder clickable(String initialKey) {
            this.clickable = true;
            this.initialClickKey = initialKey;
            return this;
        }

        /** HTMX endpoint sortable columns request, e.g. {@code /api/htmx/table-sort}. */
        public Builder sortEndpoint(String endpoint) {
            this.sortEndpoint = endpoint;
            return this;
        }

        /** Start a new tbody group with the given striping. */
        public Builder body(TableBody.Stripe stripe) {
            flushBody();
            currentStripe = stripe;
            return this;
        }

        public Builder row(Object... cells) {
            currentRows.add(TableRow.of(cells));
            return this;
        }

        public Builder row(TableRow row) {
            currentRows.add(row);
            return this;
        }

        /** Footer row rendered in {@code pf-v6-c-table__tfoot}. */
        public Builder footer(TableCell... cells) {
            this.footerRow = TableRow.of((Object[]) cells);
            return this;
        }

        private void flushBody() {
            if (!currentRows.isEmpty()) {
                doneBodies.add(new TableBody(currentStripe, currentRows));
                currentRows = new ArrayList<>();
                currentStripe = TableBody.Stripe.NONE;
            }
        }

        public Table build() {
            flushBody();
            if (columns.isEmpty()) {
                throw new IllegalStateException("A table needs at least one column");
            }
            boolean anyDetail = doneBodies.stream()
                    .flatMap(b -> b.rows().stream()).anyMatch(TableRow::isExpandable);
            boolean hasToggle = columns.stream().anyMatch(TableColumn::isToggleColumn);
            if (anyDetail && !hasToggle) {
                throw new IllegalStateException(
                        "Expandable rows need a leading toggleColumn() in the header");
            }
            Objects.requireNonNull(ariaLabel, "ariaLabel");

            List<TableColumn> dataCols = new ArrayList<>();
            for (TableColumn c : columns) {
                if (c.isCheckColumn() || c.isToggleColumn()) {
                    continue;
                }
                if (c.isGroup()) {
                    for (String sub : c.subColumns()) {
                        dataCols.add(TableColumn.of(sub));
                    }
                } else {
                    dataCols.add(c);
                }
            }
            boolean select = selection != Selection.NONE;
            List<TableBody> resolvedBodies = new ArrayList<>();
            int rowNum = 0;
            for (TableBody body : doneBodies) {
                List<TableRow> resolvedRows = new ArrayList<>();
                for (TableRow row : body.rows()) {
                    rowNum++;
                    if (row.cells().size() > dataCols.size()) {
                        throw new IllegalStateException("Row " + rowNum + " has " + row.cells().size()
                                + " cells but the table declares only " + dataCols.size() + " data columns");
                    }
                    List<TableCell> resolvedCells = new ArrayList<>();
                    for (int i = 0; i < row.cells().size(); i++) {
                        String domId = select && i == 0 && id != null ? id + "-" + rowNum : null;
                        resolvedCells.add(row.cells().get(i).resolved(dataCols.get(i), dataLabels, domId));
                    }
                    resolvedRows.add(row.resolved(rowNum, resolvedCells));
                }
                resolvedBodies.add(new TableBody(bodyStripe(body), resolvedRows));
            }
            if (footerRow != null) {
                List<TableCell> cells = new ArrayList<>();
                for (TableCell cell : footerRow.cells()) {
                    cells.add(cell.resolved(null, false, null));
                }
                footerRow = footerRow.resolved(0, cells);
            }
            return new Table(this, resolvedBodies);
        }

        private static TableBody.Stripe bodyStripe(TableBody body) {
            if (body.isStripedOdd()) {
                return TableBody.Stripe.ODD;
            }
            return body.isStripedEven() ? TableBody.Stripe.EVEN : TableBody.Stripe.NONE;
        }
    }
}
