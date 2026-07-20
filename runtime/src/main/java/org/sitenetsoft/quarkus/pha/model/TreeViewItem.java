package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * One node of a {@link TreeView} — a label plus optional children, icons,
 * badge, action button and initial expansion / checkbox state.
 *
 * <pre>
 * TreeViewItem.of("Application launcher").asExpanded()
 *     .child(TreeViewItem.of("Settings"))
 *     .child(TreeViewItem.of("Current"))
 * </pre>
 *
 * <p>An item renders a toggle when it has children or was forced expandable
 * via {@link #asExpandable()} (PatternFly's docs show toggles on some
 * childless nodes). Generated ids (checkbox / separate-selection patterns)
 * are assigned by {@link TreeView.Builder#build()}.
 */
@TemplateData
public final class TreeViewItem {

    private final String text;
    private final String description;
    private final String id;
    private final boolean expanded;
    private final boolean forcedExpandable;
    private final boolean checked;
    private final String icon;
    private final String expandedIcon;
    private final String badge;
    private final String actionIcon;
    private final String actionAriaLabel;
    private final List<TreeViewItem> children;

    /* Assigned by TreeView.build() (pattern-specific generated ids). */
    String nodeId;
    String toggleId;
    String checkId;
    String textId;

    private TreeViewItem(String text, String description, String id, boolean expanded,
                         boolean forcedExpandable, boolean checked, String icon, String expandedIcon,
                         String badge, String actionIcon, String actionAriaLabel,
                         List<TreeViewItem> children) {
        this.text = text;
        this.description = description;
        this.id = id;
        this.expanded = expanded;
        this.forcedExpandable = forcedExpandable;
        this.checked = checked;
        this.icon = icon;
        this.expandedIcon = expandedIcon;
        this.badge = badge;
        this.actionIcon = actionIcon;
        this.actionAriaLabel = actionAriaLabel;
        this.children = children;
    }

    public static TreeViewItem of(String text) {
        return new TreeViewItem(Objects.requireNonNull(text, "text"), null, null, false,
                false, false, null, null, null, null, null, List.of());
    }

    private TreeViewItem copy(String description, String id, boolean expanded,
                              boolean forcedExpandable, boolean checked, String icon, String expandedIcon,
                              String badge, String actionIcon, String actionAriaLabel,
                              List<TreeViewItem> children) {
        return new TreeViewItem(text, description, id, expanded, forcedExpandable, checked,
                icon, expandedIcon, badge, actionIcon, actionAriaLabel, children);
    }

    /** Explicit DOM id on the {@code <li>}. */
    public TreeViewItem id(String id) {
        return copy(description, id, expanded, forcedExpandable, checked, icon, expandedIcon,
                badge, actionIcon, actionAriaLabel, children);
    }

    /**
     * Secondary description — the node renders the compact title + text
     * anatomy ({@code __node-title} above {@code __node-text}).
     */
    public TreeViewItem description(String description) {
        return copy(description, id, expanded, forcedExpandable, checked, icon, expandedIcon,
                badge, actionIcon, actionAriaLabel, children);
    }

    /** Initially expanded (adds {@code pf-m-expanded} + {@code aria-expanded="true"}). */
    public TreeViewItem asExpanded() {
        return copy(description, id, true, forcedExpandable, checked, icon, expandedIcon,
                badge, actionIcon, actionAriaLabel, children);
    }

    /** Renders a toggle even without children (collapsed branch whose children are not in the markup). */
    public TreeViewItem asExpandable() {
        return copy(description, id, expanded, true, checked, icon, expandedIcon,
                badge, actionIcon, actionAriaLabel, children);
    }

    /** Initially checked (checkbox trees). */
    public TreeViewItem asChecked() {
        return copy(description, id, expanded, forcedExpandable, true, icon, expandedIcon,
                badge, actionIcon, actionAriaLabel, children);
    }

    /** Node icon, e.g. {@code "fa:folder"} or {@code "fa-brands:github"}. */
    public TreeViewItem icon(String icon) {
        return copy(description, id, expanded, forcedExpandable, checked, icon, expandedIcon,
                badge, actionIcon, actionAriaLabel, children);
    }

    /** Icon shown instead of {@link #icon(String)} while the node is expanded. */
    public TreeViewItem expandedIcon(String expandedIcon) {
        return copy(description, id, expanded, forcedExpandable, checked, icon, expandedIcon,
                badge, actionIcon, actionAriaLabel, children);
    }

    /** Badge text rendered in the node count area (e.g. a child count). */
    public TreeViewItem badge(String badge) {
        return copy(description, id, expanded, forcedExpandable, checked, icon, expandedIcon,
                badge, actionIcon, actionAriaLabel, children);
    }

    /** Plain icon button in the row's action area. */
    public TreeViewItem action(String actionIcon, String actionAriaLabel) {
        return copy(description, id, expanded, forcedExpandable, checked, icon, expandedIcon,
                badge, Objects.requireNonNull(actionIcon, "actionIcon"),
                Objects.requireNonNull(actionAriaLabel, "actionAriaLabel"), children);
    }

    public TreeViewItem child(TreeViewItem child) {
        List<TreeViewItem> next = new ArrayList<>(children);
        next.add(Objects.requireNonNull(child, "child"));
        return copy(description, id, expanded, forcedExpandable, checked, icon, expandedIcon,
                badge, actionIcon, actionAriaLabel, List.copyOf(next));
    }

    public TreeViewItem children(TreeViewItem... items) {
        List<TreeViewItem> next = new ArrayList<>(children);
        for (TreeViewItem child : items) {
            next.add(Objects.requireNonNull(child, "child"));
        }
        return copy(description, id, expanded, forcedExpandable, checked, icon, expandedIcon,
                badge, actionIcon, actionAriaLabel, List.copyOf(next));
    }

    public String text() {
        return text;
    }

    public String description() {
        return description;
    }

    public String id() {
        return id;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public boolean isChecked() {
        return checked;
    }

    /** True when the node renders a toggle: it has children or was forced expandable. */
    public boolean isExpandable() {
        return forcedExpandable || !children.isEmpty();
    }

    /** True when a nested child list is rendered. */
    public boolean isParent() {
        return !children.isEmpty();
    }

    public String icon() {
        return icon;
    }

    public String expandedIcon() {
        return expandedIcon;
    }

    /** Icon resolved for the initial state: the expanded icon while expanded, else the base icon. */
    public String displayIcon() {
        return expanded && expandedIcon != null ? expandedIcon : icon;
    }

    public String badge() {
        return badge;
    }

    public String actionIcon() {
        return actionIcon;
    }

    public String actionAriaLabel() {
        return actionAriaLabel;
    }

    public List<TreeViewItem> children() {
        return children;
    }

    /**
     * Build-time copy applying tree-level default icons (items with their own
     * icon keep it) and replacing the child list with already-processed copies.
     */
    TreeViewItem withDefaultIcons(String defaultIcon, String defaultExpandedIcon, List<TreeViewItem> processedChildren) {
        boolean ownIcon = icon != null;
        return new TreeViewItem(text, description, id, expanded, forcedExpandable, checked,
                ownIcon ? icon : defaultIcon,
                ownIcon ? expandedIcon : defaultExpandedIcon,
                badge, actionIcon, actionAriaLabel, List.copyOf(processedChildren));
    }

    public String nodeId() {
        return nodeId;
    }

    public String toggleId() {
        return toggleId;
    }

    public String checkId() {
        return checkId;
    }

    public String textId() {
        return textId;
    }
}
