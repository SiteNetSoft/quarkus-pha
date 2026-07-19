package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * One row of a tree {@link Table} ({@code pf-m-tree-view}). Build with
 * {@link Table#node(String, String...)} — the name renders in the tree-view
 * title cell, the remaining strings fill the other columns — and refine with
 * the {@code withX} copies:
 *
 * <pre>
 * Table.node("Application servers", "Group", "2 nodes").expanded()
 *     .child(Table.node("web-1", "Server", "us-east-1").key("web1").icon("fa-leaf"))
 * </pre>
 *
 * <p>{@link Table.Builder#build()} resolves each node's aria-level /
 * aria-setsize / aria-posinset, its Alpine toggle variable ({@code g1},
 * {@code g2}, … pre-order over parents) and, for checkbox trees, the
 * selection bindings (leaves get an {@code x-model} key; parents cascade to
 * their descendant leaves and show indeterminate for partial selection).
 */
@TemplateData
public final class TableTreeNode {

    private final String name;
    private final List<String> cells;
    private final List<TableTreeNode> children;
    private final boolean expanded;
    private final boolean checked;
    private final String selKey;
    private final String iconCollapsed;
    private final String iconExpanded;
    // resolved by Table.build()
    private final int level;
    private final int setSize;
    private final int posInSet;
    private final String toggleVar;
    private final String showExpr;
    private final String checkboxModel;
    private final String checkedExpr;
    private final String indeterminateExpr;
    private final String changeExpr;

    private TableTreeNode(String name, List<String> cells, List<TableTreeNode> children,
                          boolean expanded, boolean checked, String selKey,
                          String iconCollapsed, String iconExpanded,
                          int level, int setSize, int posInSet, String toggleVar, String showExpr,
                          String checkboxModel, String checkedExpr, String indeterminateExpr, String changeExpr) {
        this.name = name;
        this.cells = List.copyOf(cells);
        this.children = List.copyOf(children);
        this.expanded = expanded;
        this.checked = checked;
        this.selKey = selKey;
        this.iconCollapsed = iconCollapsed;
        this.iconExpanded = iconExpanded;
        this.level = level;
        this.setSize = setSize;
        this.posInSet = posInSet;
        this.toggleVar = toggleVar;
        this.showExpr = showExpr;
        this.checkboxModel = checkboxModel;
        this.checkedExpr = checkedExpr;
        this.indeterminateExpr = indeterminateExpr;
        this.changeExpr = changeExpr;
    }

    static TableTreeNode of(String name, String... cells) {
        return new TableTreeNode(Objects.requireNonNull(name, "name"), List.of(cells), List.of(),
                false, false, null, null, null, 0, 0, 0, null, null, null, null, null, null);
    }

    /** Copy with an added child node (makes this node a toggleable parent). */
    public TableTreeNode child(TableTreeNode child) {
        List<TableTreeNode> c = new ArrayList<>(children);
        c.add(Objects.requireNonNull(child, "child"));
        return new TableTreeNode(name, cells, c, expanded, checked, selKey, iconCollapsed, iconExpanded,
                level, setSize, posInSet, toggleVar, showExpr,
                checkboxModel, checkedExpr, indeterminateExpr, changeExpr);
    }

    /** Copy that starts expanded. */
    public TableTreeNode expanded() {
        return new TableTreeNode(name, cells, children, true, checked, selKey, iconCollapsed, iconExpanded,
                level, setSize, posInSet, toggleVar, showExpr,
                checkboxModel, checkedExpr, indeterminateExpr, changeExpr);
    }

    /** Copy whose checkbox starts checked (checkbox trees, leaves only). */
    public TableTreeNode checked() {
        return new TableTreeNode(name, cells, children, expanded, true, selKey, iconCollapsed, iconExpanded,
                level, setSize, posInSet, toggleVar, showExpr,
                checkboxModel, checkedExpr, indeterminateExpr, changeExpr);
    }

    /** Copy with the Alpine selection key for this leaf, e.g. {@code "web1"}. */
    public TableTreeNode key(String selKey) {
        return new TableTreeNode(name, cells, children, expanded, checked,
                Objects.requireNonNull(selKey, "selKey"), iconCollapsed, iconExpanded,
                level, setSize, posInSet, toggleVar, showExpr,
                checkboxModel, checkedExpr, indeterminateExpr, changeExpr);
    }

    /** Copy with a static row icon, e.g. {@code "fa-leaf"}. */
    public TableTreeNode icon(String iconClass) {
        return new TableTreeNode(name, cells, children, expanded, checked, selKey,
                Objects.requireNonNull(iconClass, "iconClass"), null,
                level, setSize, posInSet, toggleVar, showExpr,
                checkboxModel, checkedExpr, indeterminateExpr, changeExpr);
    }

    /** Copy with a toggle-bound icon pair, e.g. {@code icon("fa-folder", "fa-folder-open")}. */
    public TableTreeNode icon(String collapsed, String expanded) {
        return new TableTreeNode(name, cells, children, this.expanded, checked, selKey,
                Objects.requireNonNull(collapsed, "collapsed"), Objects.requireNonNull(expanded, "expanded"),
                level, setSize, posInSet, toggleVar, showExpr,
                checkboxModel, checkedExpr, indeterminateExpr, changeExpr);
    }

    /** Copy with the tree position and Alpine bindings resolved; called from Table.build(). */
    TableTreeNode resolved(int level, int setSize, int posInSet, String toggleVar, String showExpr,
                           String checkboxModel, String checkedExpr, String indeterminateExpr, String changeExpr) {
        return new TableTreeNode(name, cells, children, expanded, checked, selKey, iconCollapsed, iconExpanded,
                level, setSize, posInSet, toggleVar, showExpr,
                checkboxModel, checkedExpr, indeterminateExpr, changeExpr);
    }

    public String name() {
        return name;
    }

    public List<String> cells() {
        return cells;
    }

    public List<TableTreeNode> children() {
        return children;
    }

    public boolean isParent() {
        return !children.isEmpty();
    }

    public boolean isExpanded() {
        return expanded;
    }

    public boolean isChecked() {
        return checked;
    }

    String selKey() {
        return selKey;
    }

    public int level() {
        return level;
    }

    public int setSize() {
        return setSize;
    }

    public int posInSet() {
        return posInSet;
    }

    /** Alpine toggle variable of this parent, e.g. {@code g1}. */
    public String toggleVar() {
        return toggleVar;
    }

    /** Alpine expression gating this row's visibility (its ancestors' toggles), or null at level 1. */
    public String showExpr() {
        return showExpr;
    }

    /** x-model expression for a leaf checkbox, or null for parent checkboxes. */
    public String checkboxModel() {
        return checkboxModel;
    }

    public String checkedExpr() {
        return checkedExpr;
    }

    public String indeterminateExpr() {
        return indeterminateExpr;
    }

    public String changeExpr() {
        return changeExpr;
    }

    public boolean hasIcon() {
        return iconCollapsed != null;
    }

    /** True when the icon swaps with the toggle state (folder / folder-open parents). */
    public boolean isIconSwap() {
        return iconExpanded != null;
    }

    public String iconCollapsed() {
        return iconCollapsed;
    }

    public String iconExpanded() {
        return iconExpanded;
    }

    /** aria-label for the expand toggle button. */
    public String toggleAriaLabel() {
        return "Toggle " + name;
    }
}
