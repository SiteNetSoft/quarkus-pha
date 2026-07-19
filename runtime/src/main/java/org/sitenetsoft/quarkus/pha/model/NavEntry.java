package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.List;
import java.util.Objects;

/**
 * One entry of a {@link Nav}: a plain link, an expandable item with a subnav,
 * or a titled group (section). Instances are immutable; create them through
 * the static factories on {@link Nav} ({@code Nav.item(...)}) or the
 * {@link Nav.Builder} methods ({@code expandable(...)}, {@code group(...)}).
 *
 * <p>The template renders an entry by kind, so the accessors
 * {@link #isLink()}, {@link #isExpandable()} and {@link #isGroup()} are the
 * dispatch points ({@code entry.link} / {@code entry.expandable} /
 * {@code entry.group} in Qute).
 */
@TemplateData
public final class NavEntry {

    public enum Kind { LINK, EXPANDABLE, GROUP }

    private final Kind kind;
    private final String label;
    private final String href;
    private final String icon;
    private final boolean current;
    private final boolean expanded;
    private final List<NavEntry> children;
    private final String toggleId;

    NavEntry(Kind kind, String label, String href, String icon, boolean current,
             boolean expanded, List<NavEntry> children, String toggleId) {
        this.kind = Objects.requireNonNull(kind, "kind");
        this.label = Objects.requireNonNull(label, "label");
        this.href = href;
        this.icon = icon;
        this.current = current;
        this.expanded = expanded;
        this.children = List.copyOf(children);
        this.toggleId = toggleId;
    }

    static NavEntry link(String label, String href, boolean current, String icon) {
        return new NavEntry(Kind.LINK, label, Objects.requireNonNull(href, "href"),
                icon, current, false, List.of(), null);
    }

    static NavEntry expandable(String label, boolean expanded, List<NavEntry> children) {
        requireNoGroups(children, "an expandable item");
        return new NavEntry(Kind.EXPANDABLE, label, null, null, false, expanded, children, null);
    }

    static NavEntry group(String title, List<NavEntry> children) {
        requireNoGroups(children, "a group");
        return new NavEntry(Kind.GROUP, title, null, null, false, false, children, null);
    }

    private static void requireNoGroups(List<NavEntry> children, String parent) {
        for (NavEntry child : children) {
            if (child.kind == Kind.GROUP) {
                throw new IllegalArgumentException(
                        "Nav groups cannot be nested inside " + parent + ": " + child.label);
            }
        }
    }

    /**
     * Copy of this entry (and its subtree) with toggle ids assigned to expandables,
     * numbered in document order (parent before children).
     */
    NavEntry withToggleIds(String idPrefix, int[] counter) {
        String assigned = kind == Kind.EXPANDABLE ? idPrefix + "-toggle-" + (++counter[0]) : null;
        List<NavEntry> mapped = children.stream()
                .map(c -> c.withToggleIds(idPrefix, counter))
                .toList();
        return new NavEntry(kind, label, href, icon, current, expanded, mapped, assigned);
    }

    public boolean isLink() {
        return kind == Kind.LINK;
    }

    public boolean isExpandable() {
        return kind == Kind.EXPANDABLE;
    }

    public boolean isGroup() {
        return kind == Kind.GROUP;
    }

    public String label() {
        return label;
    }

    public String href() {
        return href;
    }

    /** Optional icon name for the {@code icons:svg} resolver, e.g. {@code fa:gauge}; null for none. */
    public String icon() {
        return icon;
    }

    public boolean isCurrent() {
        return current;
    }

    /** Initial expanded state of an expandable item. */
    public boolean isExpanded() {
        return expanded;
    }

    public List<NavEntry> children() {
        return children;
    }

    /** DOM id of the expandable toggle button; assigned by {@link Nav.Builder#build()}. */
    public String toggleId() {
        return toggleId;
    }
}
