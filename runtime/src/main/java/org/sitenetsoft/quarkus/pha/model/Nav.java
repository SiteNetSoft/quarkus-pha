package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;

/**
 * Typed model for the Navigation component ({@code pf-v6-c-nav}).
 *
 * <p>Build the data in Java, hand it to the runtime template, and the correct
 * PatternFly markup — including Alpine expand/collapse wiring and ARIA
 * attributes — is rendered server-side:
 *
 * <pre>
 * Nav nav = Nav.builder()
 *     .id("side-nav")
 *     .item("Dashboard", "/dashboard", true)
 *     .item("Projects", "/projects")
 *     .expandable("Reports",
 *         Nav.item("Usage", "/reports/usage"),
 *         Nav.item("Billing", "/reports/billing"))
 *     .build();
 * </pre>
 *
 * Template side: <code>{#include components/navigation/nav nav=nav /}</code>
 *
 * <p>A nav is either a flat list (items and expandables, freely mixed) or a
 * grouped nav (titled sections only) — PatternFly has no mixed form, and
 * {@link Builder#build()} rejects the mix. Set an {@link Builder#id(String)}
 * when the nav contains expandable items and more than one nav renders on the
 * page, so generated toggle ids cannot collide.
 */
@TemplateData
public final class Nav {

    public enum Orientation { VERTICAL, HORIZONTAL, SUBNAV }

    private final String id;
    private final String ariaLabel;
    private final Orientation orientation;
    private final List<NavEntry> entries;

    private Nav(String id, String ariaLabel, Orientation orientation, List<NavEntry> entries) {
        this.id = id;
        this.ariaLabel = ariaLabel;
        this.orientation = orientation;
        this.entries = List.copyOf(entries);
    }

    /** Plain link entry. */
    public static NavEntry item(String label, String href) {
        return NavEntry.link(label, href, false, null);
    }

    /** Plain link entry, optionally marked as the current page. */
    public static NavEntry item(String label, String href, boolean current) {
        return NavEntry.link(label, href, current, null);
    }

    /** Link entry with a leading icon, e.g. {@code Nav.icon("Dashboard", "/dash", "fa:gauge")}. */
    public static NavEntry icon(String label, String href, String iconName) {
        return NavEntry.link(label, href, false, iconName);
    }

    /** Link entry with a leading icon, optionally marked current. */
    public static NavEntry icon(String label, String href, String iconName, boolean current) {
        return NavEntry.link(label, href, current, iconName);
    }

    /** Expandable entry (collapsed by default), for nesting inside another expandable. */
    public static NavEntry expandable(String label, NavEntry... children) {
        return NavEntry.expandable(label, false, List.of(children));
    }

    /** Expandable entry with an explicit initial state, for nesting. */
    public static NavEntry expandable(String label, boolean expanded, NavEntry... children) {
        return NavEntry.expandable(label, expanded, List.of(children));
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

    public boolean isHorizontal() {
        return orientation != Orientation.VERTICAL;
    }

    public boolean isSubnav() {
        return orientation == Orientation.SUBNAV;
    }

    /** True when the nav is made of titled sections rather than a flat list. */
    public boolean isGrouped() {
        return !entries.isEmpty() && entries.get(0).isGroup();
    }

    public List<NavEntry> entries() {
        return entries;
    }

    public static final class Builder {

        private String id;
        private String ariaLabel = "Navigation";
        private Orientation orientation = Orientation.VERTICAL;
        private final List<NavEntry> entries = new ArrayList<>();

        private Builder() {
        }

        /** DOM id of the root element; also prefixes generated expandable-toggle ids. */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        /** Horizontal nav for the masthead content area ({@code pf-m-horizontal}). */
        public Builder horizontal() {
            this.orientation = Orientation.HORIZONTAL;
            return this;
        }

        /** Local tertiary nav under a page header ({@code pf-m-horizontal pf-m-subnav}). */
        public Builder subnav() {
            this.orientation = Orientation.SUBNAV;
            return this;
        }

        public Builder item(String label, String href) {
            entries.add(Nav.item(label, href));
            return this;
        }

        public Builder item(String label, String href, boolean current) {
            entries.add(Nav.item(label, href, current));
            return this;
        }

        public Builder icon(String label, String href, String iconName) {
            entries.add(Nav.icon(label, href, iconName));
            return this;
        }

        public Builder icon(String label, String href, String iconName, boolean current) {
            entries.add(Nav.icon(label, href, iconName, current));
            return this;
        }

        public Builder expandable(String label, NavEntry... children) {
            entries.add(NavEntry.expandable(label, false, List.of(children)));
            return this;
        }

        public Builder expandable(String label, boolean expanded, NavEntry... children) {
            entries.add(NavEntry.expandable(label, expanded, List.of(children)));
            return this;
        }

        /** Titled section; a grouped nav must consist of groups only. */
        public Builder group(String title, NavEntry... children) {
            entries.add(NavEntry.group(title, List.of(children)));
            return this;
        }

        public Nav build() {
            boolean anyGroup = entries.stream().anyMatch(NavEntry::isGroup);
            boolean allGroups = entries.stream().allMatch(NavEntry::isGroup);
            if (anyGroup && !allGroups) {
                throw new IllegalStateException(
                        "A nav is either grouped (sections only) or a flat list — "
                                + "PatternFly has no mixed form. Move loose items into a group.");
            }
            String idPrefix = id != null ? id : "pha-nav";
            int[] counter = { 0 };
            List<NavEntry> withIds = entries.stream()
                    .map(e -> e.withToggleIds(idPrefix, counter))
                    .toList();
            return new Nav(id, ariaLabel, orientation, withIds);
        }
    }
}
