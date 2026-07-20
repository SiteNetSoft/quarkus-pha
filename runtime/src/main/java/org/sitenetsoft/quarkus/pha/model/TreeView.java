package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Tree view component ({@code pf-v6-c-tree-view}).
 *
 * <p>Describe the tree as data and the runtime template renders the full
 * PatternFly markup plus the {@code phaTreeView()} Alpine wiring — whole-node
 * selection by default, or the separate selection/expansion and checkbox
 * patterns, with optional search input, expand-all button, icons, badges and
 * row actions:
 *
 * <pre>
 * TreeView tree = TreeView.builder()
 *     .ariaLabel("Files")
 *     .item(TreeViewItem.of("Documents").asExpanded()
 *         .child(TreeViewItem.of("Reports"))
 *         .child(TreeViewItem.of("Invoices")))
 *     .item(TreeViewItem.of("Downloads"))
 *     .build();
 * </pre>
 *
 * Template side: <code>{#include components/navigation/tree-view tree=tree /}</code>
 *
 * <p>{@code build()} assigns the generated ids the interaction patterns need:
 * separate-selection node ids ({@code node-{prefix}-N}, pre-order over
 * expandable nodes) and checkbox toggle/check/text ids
 * ({@code toggle|check|text-{prefix}-N}, one pre-order counter over all
 * nodes). Selection is runtime state — no item starts selected.
 */
@TemplateData
public final class TreeView {

    private final String id;
    private final String ariaLabel;
    private final boolean multiselectable;
    private final boolean compact;
    private final boolean noBackground;
    private final boolean guides;
    private final String searchPlaceholder;
    private final String searchAriaLabel;
    private final boolean toggleAllButton;
    private final boolean selectableNodes;
    private final boolean checkboxes;
    private final List<TreeViewItem> items;

    private TreeView(Builder b) {
        this.id = b.id;
        this.ariaLabel = b.ariaLabel;
        this.multiselectable = b.multiselectable;
        this.compact = b.compact;
        this.noBackground = b.noBackground;
        this.guides = b.guides;
        this.searchPlaceholder = b.searchPlaceholder;
        this.searchAriaLabel = b.searchAriaLabel;
        this.toggleAllButton = b.toggleAllButton;
        this.selectableNodes = b.selectableNodes;
        this.checkboxes = b.checkboxes;
        this.items = List.copyOf(b.items);
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

    public boolean isMultiselectable() {
        return multiselectable;
    }

    public boolean isCompact() {
        return compact;
    }

    public boolean isNoBackground() {
        return noBackground;
    }

    public boolean isGuides() {
        return guides;
    }

    public boolean isHasSearch() {
        return searchPlaceholder != null;
    }

    public String searchPlaceholder() {
        return searchPlaceholder;
    }

    public String searchAriaLabel() {
        return searchAriaLabel;
    }

    public boolean isToggleAllButton() {
        return toggleAllButton;
    }

    public boolean isSelectableNodes() {
        return selectableNodes;
    }

    public boolean isCheckboxes() {
        return checkboxes;
    }

    public List<TreeViewItem> items() {
        return items;
    }

    public static final class Builder {

        private String id;
        private String ariaLabel;
        private boolean multiselectable;
        private boolean compact;
        private boolean noBackground;
        private boolean guides;
        private String searchPlaceholder;
        private String searchAriaLabel;
        private boolean toggleAllButton;
        private boolean selectableNodes;
        private boolean checkboxes;
        private String idPrefix = "pha-tree";
        private String defaultIcon;
        private String defaultExpandedIcon;
        private final List<TreeViewItem> items = new ArrayList<>();

        private Builder() {
        }

        /** DOM id on the component root. */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** Accessible name of the {@code ul[role=tree]}. */
        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        /** Allow multiple selected nodes ({@code aria-multiselectable="true"}). */
        public Builder multiselectable() {
            this.multiselectable = true;
            return this;
        }

        public Builder compact() {
            this.compact = true;
            return this;
        }

        /** Compact variant without the item background. */
        public Builder noBackground() {
            this.noBackground = true;
            return this;
        }

        /** Visual guide lines connecting nested items. */
        public Builder guides() {
            this.guides = true;
            return this;
        }

        /** Search input (with divider) above the tree, filtering items as you type. */
        public Builder search(String placeholder, String ariaLabel) {
            this.searchPlaceholder = Objects.requireNonNull(placeholder, "placeholder");
            this.searchAriaLabel = Objects.requireNonNull(ariaLabel, "ariaLabel");
            return this;
        }

        /** "Expand all / Collapse all" link button above the tree. */
        public Builder toggleAllButton() {
            this.toggleAllButton = true;
            return this;
        }

        /**
         * Separate selection and expansion (PF's hasSelectableNodes): expandable
         * nodes render distinct toggle and text buttons, so selecting a parent
         * doesn't change its expansion.
         */
        public Builder selectableNodes() {
            this.selectableNodes = true;
            return this;
        }

        /** Checkbox tree — every node carries a cascading checkbox. */
        public Builder checkboxes() {
            this.checkboxes = true;
            return this;
        }

        /** Prefix for generated node/toggle/check/text ids (default {@code pha-tree}). */
        public Builder idPrefix(String idPrefix) {
            this.idPrefix = Objects.requireNonNull(idPrefix, "idPrefix");
            return this;
        }

        /**
         * Icons applied to every item that doesn't set its own: the base icon
         * for collapsed and leaf nodes, the expanded icon for expanded ones.
         * PatternFly's stock pair is {@code fa:folder} / {@code fa:folder-open}.
         */
        public Builder defaultIcons(String icon, String expandedIcon) {
            this.defaultIcon = Objects.requireNonNull(icon, "icon");
            this.defaultExpandedIcon = Objects.requireNonNull(expandedIcon, "expandedIcon");
            return this;
        }

        public Builder item(TreeViewItem item) {
            items.add(Objects.requireNonNull(item, "item"));
            return this;
        }

        public Builder items(TreeViewItem... items) {
            for (TreeViewItem item : items) {
                item(item);
            }
            return this;
        }

        public TreeView build() {
            if (items.isEmpty()) {
                throw new IllegalStateException("A tree view needs at least one item");
            }
            if (selectableNodes && checkboxes) {
                throw new IllegalStateException("selectableNodes() and checkboxes() are mutually exclusive");
            }
            if (defaultIcon != null) {
                List<TreeViewItem> withIcons = new ArrayList<>();
                for (TreeViewItem item : items) {
                    withIcons.add(applyDefaultIcons(item));
                }
                items.clear();
                items.addAll(withIcons);
            }
            if (checkboxes) {
                int[] counter = {0};
                items.forEach(item -> assignCheckboxIds(item, counter));
            } else if (selectableNodes) {
                int[] counter = {0};
                items.forEach(item -> assignNodeIds(item, counter));
            }
            return new TreeView(this);
        }

        private TreeViewItem applyDefaultIcons(TreeViewItem item) {
            List<TreeViewItem> kids = item.children().stream()
                    .map(this::applyDefaultIcons)
                    .toList();
            return item.withDefaultIcons(defaultIcon, defaultExpandedIcon, kids);
        }

        private void assignCheckboxIds(TreeViewItem item, int[] counter) {
            int n = ++counter[0];
            if (item.isExpandable()) {
                item.toggleId = "toggle-" + idPrefix + "-" + n;
            }
            item.checkId = "check-" + idPrefix + "-" + n;
            item.textId = "text-" + idPrefix + "-" + n;
            item.children().forEach(child -> assignCheckboxIds(child, counter));
        }

        private void assignNodeIds(TreeViewItem item, int[] counter) {
            if (item.isExpandable()) {
                item.nodeId = "node-" + idPrefix + "-" + (++counter[0]);
            }
            item.children().forEach(child -> assignNodeIds(child, counter));
        }
    }
}
