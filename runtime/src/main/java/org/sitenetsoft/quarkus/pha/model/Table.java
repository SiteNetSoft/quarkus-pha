package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.IdentityHashMap;
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
 * <p>Coverage: density/striping/border variants, captions, footers, column
 * width/text/visibility modifiers, multiple striped bodies, sortable headers
 * (server-driven via HTMX), checkbox/radio selection (plus live tri-state
 * select-all), click-to-select rows (and click-to-select tbodies combined
 * with expansion), single-level expandable rows (plain text or nested-table
 * details), compound per-cell expansion, sticky layouts, nested column
 * headers, tree tables, favorites (plain and sortable), draggable rows,
 * inline-edit rows, responsive overflow menus, table-text wrappers and
 * empty-state bodies. Bespoke compositions (popovers in headers, toolbars,
 * drawers, custom cell markup) stay hand-written in the content slot.
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
    private final List<TableTreeNode> treeRows;
    private final boolean treeCheckboxes;
    private final boolean treeGridLg;
    private final boolean treeNoInset;
    private final String treeRootXData;
    private final String treeBodyXData;
    private final boolean favoritesSortable;
    private final boolean indeterminateSelect;
    private final String rootXData;

    private Table(Builder b, List<TableBody> bodies) {
        this.favoritesSortable = b.favoritesSortable;
        this.indeterminateSelect = b.indeterminateSelect;
        this.rootXData = b.rootXData;
        this.treeRows = List.copyOf(b.resolvedTreeRows);
        this.treeCheckboxes = b.treeCheckboxes;
        this.treeGridLg = b.treeGridLg;
        this.treeNoInset = b.treeNoInset;
        this.treeRootXData = b.treeRootXData;
        this.treeBodyXData = b.treeBodyXData;
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

    /** Tree-node factory: the name fills the title cell, the rest the other columns. */
    public static TableTreeNode node(String name, String... cells) {
        return TableTreeNode.of(name, cells);
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

    /** True when any row expands — row-level detail rows or compound cells ({@code pf-m-expandable}). */
    public boolean isExpandable() {
        return bodies.stream().flatMap(b -> b.rows().stream())
                .anyMatch(r -> r.isExpandable() || r.isCompound());
    }

    /** True when any row uses compound per-cell expansion toggles. */
    public boolean isCompound() {
        return bodies.stream().flatMap(b -> b.rows().stream()).anyMatch(TableRow::isCompound);
    }

    /** Clickable selection carried per row (non-expandable tables). */
    public boolean isClickableRow() {
        return clickable && !isExpandable();
    }

    /** Clickable selection carried per tbody (expandable tables select the whole row group). */
    public boolean isClickableBody() {
        return clickable && isExpandable();
    }

    /** True when {@code pf-m-animate-expand} is among the modifiers (detail rows stay rendered). */
    public boolean isAnimateExpand() {
        return modifiers.contains("pf-m-animate-expand");
    }

    /** Columns that correspond to row cells (control columns — check, toggle,
     * favorite, drag, inline-edit — are auto-rendered; column groups flatten
     * to their sub-columns). */
    public List<TableColumn> dataColumns() {
        List<TableColumn> flat = new ArrayList<>();
        for (TableColumn c : columns) {
            if (c.isCheckColumn() || c.isToggleColumn()
                    || c.isFavoriteColumn() || c.isDragColumn() || c.isInlineEditColumn()) {
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

    /** True when this table renders as a tree ({@code pf-m-tree-view}). */
    public boolean isTreeView() {
        return !treeRows.isEmpty();
    }

    /** True when the tree has real hierarchy — PF only uses role=treegrid then. */
    public boolean isTreeGrid() {
        return treeRows.stream().anyMatch(TableTreeNode::isParent);
    }

    /** Value of the table's role attribute. */
    public String role() {
        return isTreeGrid() ? "treegrid" : "grid";
    }

    /** Tree modifier classes for the table root, leading space included. */
    public String treeModifiers() {
        if (!isTreeView()) {
            return "";
        }
        StringBuilder sb = new StringBuilder(" pf-m-tree-view");
        if (treeGridLg) {
            sb.append(" pf-m-tree-view-grid-lg");
        }
        if (treeNoInset) {
            sb.append(" pf-m-no-inset");
        }
        return sb.toString();
    }

    /** Tree rows flattened in pre-order, with aria/Alpine bindings resolved. */
    public List<TableTreeNode> treeRows() {
        return treeRows;
    }

    public boolean isTreeCheckboxes() {
        return treeCheckboxes;
    }

    /** Alpine x-data for the table root (checkbox trees), or null. */
    public String treeRootXData() {
        return treeRootXData;
    }

    /** Alpine x-data for the tbody (plain trees with toggles), or null. */
    public String treeBodyXData() {
        return treeBodyXData;
    }

    /** True when a leading favorite-star column is declared. */
    public boolean isFavorites() {
        return columns.stream().anyMatch(TableColumn::isFavoriteColumn);
    }

    /** True when the favorite header is a live sort control over favorite state. */
    public boolean isFavoritesSortable() {
        return favoritesSortable;
    }

    /** True when rows reorder by dragging (leading grip column). */
    public boolean isDraggableRows() {
        return columns.stream().anyMatch(TableColumn::isDragColumn);
    }

    /** True when rows toggle between a read view and inline edit inputs. */
    public boolean isInlineEdit() {
        return columns.stream().anyMatch(TableColumn::isInlineEditColumn);
    }

    /** True when the select-all checkbox is a live tri-state control. */
    public boolean isIndeterminateSelect() {
        return indeterminateSelect;
    }

    /** Generated Alpine x-data for the table root (favorites sorting / tri-state selection), or null. */
    public String rootXData() {
        return rootXData;
    }

    /** Number of columns a detail cell spans (the flattened data columns). */
    public int detailColspan() {
        return Math.max(1, dataColumns().size());
    }

    /** Number of columns a compound detail cell spans (data columns plus a selection column). */
    public int compoundDetailColspan() {
        return Math.max(1, dataColumns().size() + (hasSelection() ? 1 : 0));
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
        private final List<TableTreeNode> treeNodes = new ArrayList<>();
        private boolean treeCheckboxes;
        private boolean treeGridLg;
        private boolean treeNoInset;
        private boolean favoritesSortable;
        private boolean indeterminateSelect;
        // resolved by build()
        private List<TableTreeNode> resolvedTreeRows = List.of();
        private String treeRootXData;
        private String treeBodyXData;
        private String rootXData;

        private Builder() {
        }

        /** Add a top-level tree node (see {@link Table#node}); switches the table to tree mode. */
        public Builder treeNode(TableTreeNode node) {
            treeNodes.add(Objects.requireNonNull(node, "node"));
            return this;
        }

        /** Render a checkbox in every tree row; parents cascade to their leaves. */
        public Builder treeCheckboxes() {
            this.treeCheckboxes = true;
            return this;
        }

        /** Switch the tree to the grid layout below lg ({@code pf-m-tree-view-grid-lg}). */
        public Builder treeGridLg() {
            this.treeGridLg = true;
            return this;
        }

        /** Remove the per-level indent ({@code pf-m-no-inset}). */
        public Builder treeNoInset() {
            this.treeNoInset = true;
            return this;
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

        /** Leading favorite-star column; refine rows with {@link TableRow#favorited()}. */
        public Builder favoriteColumn() {
            columns.add(TableColumn.favorite());
            return this;
        }

        /** Make the favorite header a live sort control over favorite state (rows need keys). */
        public Builder favoritesSortable() {
            this.favoritesSortable = true;
            return this;
        }

        /** Leading grip column; rows reorder by dragging (native HTML5 drag). */
        public Builder dragColumn() {
            columns.add(TableColumn.drag());
            return this;
        }

        /** Trailing inline-edit action column; text cells toggle to inputs with Save/Cancel. */
        public Builder inlineEditColumn(String label) {
            columns.add(TableColumn.inlineEdit(label));
            return this;
        }

        /** Make the select-all checkbox a live tri-state control (rows need keys). */
        public Builder indeterminateSelection() {
            this.indeterminateSelect = true;
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
            for (TableBody body : doneBodies) {
                for (TableRow row : body.rows()) {
                    if (!row.isCompound()) {
                        continue;
                    }
                    if (row.isExpandable()) {
                        throw new IllegalStateException(
                                "A row cannot combine compound cells with row-level detail content");
                    }
                    long open = row.cells().stream()
                            .filter(c -> c.isCompound() && c.isExpanded()).count();
                    if (open > 1) {
                        throw new IllegalStateException(
                                "At most one compound cell per row may start expanded");
                    }
                }
            }
            Objects.requireNonNull(ariaLabel, "ariaLabel");
            boolean hasFavorites = columns.stream().anyMatch(TableColumn::isFavoriteColumn);
            boolean hasInlineEdit = columns.stream().anyMatch(TableColumn::isInlineEditColumn);
            if (favoritesSortable && !hasFavorites) {
                throw new IllegalStateException("favoritesSortable() requires a favoriteColumn()");
            }
            if (indeterminateSelect && columns.stream().noneMatch(TableColumn::isCheckColumn)) {
                throw new IllegalStateException("indeterminateSelection() requires a checkColumn()");
            }

            List<TableColumn> dataCols = new ArrayList<>();
            for (TableColumn c : columns) {
                if (c.isCheckColumn() || c.isToggleColumn()
                        || c.isFavoriteColumn() || c.isDragColumn() || c.isInlineEditColumn()) {
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
            if (!treeNodes.isEmpty()) {
                if (!doneBodies.isEmpty()) {
                    throw new IllegalStateException("A table cannot mix tree nodes with regular rows");
                }
                resolveTree(dataCols.size());
            }
            boolean select = selection != Selection.NONE;
            List<TableBody> resolvedBodies = new ArrayList<>();
            List<String> stateEntries = new ArrayList<>();
            int rowNum = 0;
            for (TableBody body : doneBodies) {
                List<TableRow> resolvedRows = new ArrayList<>();
                for (TableRow row : body.rows()) {
                    rowNum++;
                    if (row.cells().size() > dataCols.size()) {
                        throw new IllegalStateException("Row " + rowNum + " has " + row.cells().size()
                                + " cells but the table declares only " + dataCols.size() + " data columns");
                    }
                    String rowKey = row.clickKey() != null ? row.clickKey() : "row" + rowNum;
                    List<TableCell> resolvedCells = new ArrayList<>();
                    for (int i = 0; i < row.cells().size(); i++) {
                        TableCell cell = row.cells().get(i);
                        if (cell.isEmptyStateCell() && cell.colspan() == 1) {
                            cell = cell.withColspan(Math.max(1, dataCols.size()));
                        }
                        if (hasInlineEdit && cell.isText()) {
                            String var = camelize(dataCols.get(i).label());
                            cell = cell.withEditBindings(var,
                                    "d" + Character.toUpperCase(var.charAt(0)) + var.substring(1),
                                    dataCols.get(i).label());
                        }
                        String domId = select && !indeterminateSelect && i == 0 && id != null
                                ? id + "-" + rowNum : null;
                        resolvedCells.add(cell.resolved(dataCols.get(i), dataLabels, domId));
                    }
                    String rowXData = null;
                    String favExpr = null;
                    String selExpr = null;
                    if (hasFavorites && !favoritesSortable) {
                        rowXData = "{ fav: " + row.isFavorited() + " }";
                        favExpr = "fav";
                    } else if (favoritesSortable) {
                        favExpr = "fav." + rowKey;
                        stateEntries.add(rowKey + ": " + row.isFavorited());
                    }
                    if (indeterminateSelect) {
                        selExpr = "sel." + rowKey;
                        stateEntries.add(rowKey + ": " + row.isChecked());
                    }
                    String editStart = null;
                    String editSave = null;
                    if (hasInlineEdit) {
                        StringBuilder xd = new StringBuilder("{ editing: false");
                        StringBuilder start = new StringBuilder();
                        StringBuilder save = new StringBuilder();
                        List<String> drafts = new ArrayList<>();
                        for (TableCell cell : resolvedCells) {
                            if (cell.editVar() != null) {
                                xd.append(", ").append(cell.editVar()).append(": '")
                                        .append(escapeJs(cell.text())).append("'");
                                drafts.add(cell.editDraft());
                                start.append(cell.editDraft()).append(" = ").append(cell.editVar()).append("; ");
                                save.append(cell.editVar()).append(" = ").append(cell.editDraft()).append("; ");
                            }
                        }
                        for (String draft : drafts) {
                            xd.append(", ").append(draft).append(": ''");
                        }
                        rowXData = xd.append(" }").toString();
                        editStart = start.append("editing = true").toString();
                        editSave = save.append("editing = false").toString();
                    }
                    TableRow resolved = row.resolved(rowNum, resolvedCells);
                    if (rowXData != null || favExpr != null || selExpr != null) {
                        resolved = resolved.withAlpine(rowXData, favExpr, selExpr);
                    }
                    if (editStart != null) {
                        resolved = resolved.withEditExprs(editStart, editSave);
                    }
                    resolvedRows.add(resolved.key(rowKey));
                }
                resolvedBodies.add(new TableBody(bodyStripe(body), resolvedRows));
            }
            if (favoritesSortable) {
                rootXData = "{ fav: { " + String.join(", ", stateEntries) + " }, dir: null, "
                        + "sortFavs() { this.dir = this.dir === 'desc' ? 'asc' : 'desc'; "
                        + "const rows = Array.from(this.$refs.body.children); "
                        + "rows.sort((a, b) => { const d = (this.fav[b.dataset.key] ? 1 : 0) - (this.fav[a.dataset.key] ? 1 : 0); "
                        + "return this.dir === 'desc' ? d : -d; }); "
                        + "rows.forEach((r) => this.$refs.body.appendChild(r)); } }";
            } else if (indeterminateSelect) {
                rootXData = "{ sel: { " + String.join(", ", stateEntries) + " }, "
                        + "get allOn() { return Object.values(this.sel).every(Boolean) }, "
                        + "get someOn() { return Object.values(this.sel).some(Boolean) }, "
                        + "toggleAll() { const on = !this.allOn; Object.keys(this.sel).forEach((k) => { this.sel[k] = on }) } }";
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

        /** Flatten the tree pre-order, assigning aria positions and Alpine bindings. */
        private void resolveTree(int dataColCount) {
            IdentityHashMap<TableTreeNode, String> leafKeys = new IdentityHashMap<>();
            assignLeafKeys(treeNodes, leafKeys, new int[] {0});

            List<TableTreeNode> flat = new ArrayList<>();
            List<String> toggles = new ArrayList<>();
            List<String> selEntries = new ArrayList<>();
            List<String> helpers = new ArrayList<>();
            walkTree(treeNodes, 1, List.of(), dataColCount, leafKeys,
                    flat, toggles, selEntries, helpers, new int[] {0});

            this.resolvedTreeRows = flat;
            if (treeCheckboxes) {
                List<String> parts = new ArrayList<>(toggles);
                parts.add("sel: { " + String.join(", ", selEntries) + " }");
                parts.addAll(helpers);
                this.treeRootXData = "{ " + String.join(", ", parts) + " }";
            } else if (!toggles.isEmpty()) {
                this.treeBodyXData = "{ " + String.join(", ", toggles) + " }";
            }
        }

        private static void assignLeafKeys(List<TableTreeNode> nodes,
                IdentityHashMap<TableTreeNode, String> keys, int[] counter) {
            for (TableTreeNode n : nodes) {
                if (n.isParent()) {
                    assignLeafKeys(n.children(), keys, counter);
                } else {
                    counter[0]++;
                    keys.put(n, n.selKey() != null ? n.selKey() : "n" + counter[0]);
                }
            }
        }

        private static void collectLeafKeys(List<TableTreeNode> nodes,
                IdentityHashMap<TableTreeNode, String> keys, List<String> out) {
            for (TableTreeNode n : nodes) {
                if (n.isParent()) {
                    collectLeafKeys(n.children(), keys, out);
                } else {
                    out.add(keys.get(n));
                }
            }
        }

        private void walkTree(List<TableTreeNode> siblings, int level, List<String> ancestorVars,
                int dataColCount, IdentityHashMap<TableTreeNode, String> leafKeys,
                List<TableTreeNode> flat, List<String> toggles, List<String> selEntries,
                List<String> helpers, int[] parentCounter) {
            int pos = 0;
            for (TableTreeNode n : siblings) {
                pos++;
                if (n.cells().size() > dataColCount - 1) {
                    throw new IllegalStateException("Tree node '" + n.name() + "' has " + n.cells().size()
                            + " extra cells but the table declares only " + (dataColCount - 1)
                            + " columns after the title column");
                }
                String toggleVar = null;
                if (n.isParent()) {
                    parentCounter[0]++;
                    toggleVar = "g" + parentCounter[0];
                    toggles.add(toggleVar + ": " + n.isExpanded());
                }
                String showExpr = ancestorVars.isEmpty() ? null : String.join(" && ", ancestorVars);
                String checkboxModel = null;
                String checkedExpr = null;
                String indetExpr = null;
                String changeExpr = null;
                if (treeCheckboxes) {
                    if (n.isParent()) {
                        List<String> leaves = new ArrayList<>();
                        collectLeafKeys(n.children(), leafKeys, leaves);
                        if (leaves.size() == 1) {
                            String key = "sel." + leaves.get(0);
                            checkedExpr = key;
                            changeExpr = key + " = !" + key;
                        } else {
                            int k = parentCounter[0];
                            String all = "g" + k + "All";
                            String some = "g" + k + "Some";
                            String setter = "setGroup" + k;
                            StringBuilder set = new StringBuilder(setter + "(on) {");
                            for (String leaf : leaves) {
                                set.append(" this.sel.").append(leaf).append(" = on;");
                            }
                            set.append(" }");
                            helpers.add(set.toString());
                            helpers.add("get " + all + "() { return "
                                    + leaves.stream().map(l -> "this.sel." + l)
                                            .reduce((a, b) -> a + " && " + b).orElse("false") + " }");
                            helpers.add("get " + some + "() { return "
                                    + leaves.stream().map(l -> "this.sel." + l)
                                            .reduce((a, b) -> a + " || " + b).orElse("false") + " }");
                            checkedExpr = all;
                            indetExpr = some + " && !" + all;
                            changeExpr = setter + "(!" + all + ")";
                        }
                    } else {
                        String key = leafKeys.get(n);
                        selEntries.add(key + ": " + n.isChecked());
                        checkboxModel = "sel." + key;
                    }
                }
                flat.add(n.resolved(level, siblings.size(), pos, toggleVar, showExpr,
                        checkboxModel, checkedExpr, indetExpr, changeExpr));
                if (n.isParent()) {
                    List<String> anc = new ArrayList<>(ancestorVars);
                    anc.add(toggleVar);
                    walkTree(n.children(), level + 1, anc, dataColCount, leafKeys,
                            flat, toggles, selEntries, helpers, parentCounter);
                }
            }
        }

        /** Camel-case identifier from a column label: "Last seen" → "lastSeen". */
        private static String camelize(String label) {
            StringBuilder sb = new StringBuilder();
            boolean upperNext = false;
            for (char ch : label.toCharArray()) {
                if (Character.isLetterOrDigit(ch)) {
                    sb.append(upperNext && sb.length() > 0 ? Character.toUpperCase(ch) : Character.toLowerCase(ch));
                    upperNext = false;
                } else {
                    upperNext = true;
                }
            }
            if (sb.length() == 0) {
                throw new IllegalStateException("Cannot derive an edit variable from column label: " + label);
            }
            return sb.toString();
        }

        /** Escape a value for a single-quoted JS string literal. */
        private static String escapeJs(String value) {
            return value.replace("\\", "\\\\").replace("'", "\\'");
        }

        private static TableBody.Stripe bodyStripe(TableBody body) {
            if (body.isStripedOdd()) {
                return TableBody.Stripe.ODD;
            }
            return body.isStripedEven() ? TableBody.Stripe.EVEN : TableBody.Stripe.NONE;
        }
    }
}
