package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Tabs component ({@code pf-v6-c-tabs}).
 *
 * <pre>
 * Tabs tabs = Tabs.of("tabs-basic")
 *     .item(TabItem.of("tab-1", "Overview").panel("Overview panel — high-level summary."))
 *     .item(TabItem.of("tab-2", "Details").panel("Details panel — per-field values."))
 *     .panelPadding()
 *     .build();
 * </pre>
 *
 * Template side: <code>{#include components/navigation/tabs tabs=tabs /}</code>
 *
 * <p>Renders the strip and its panels with the Alpine selection state. By
 * default the include owns the state in a wrapper div ({@code active}
 * defaulting to the first key); {@link Builder#externalState()} binds to a
 * variable owned by surrounding chrome instead (paired main/subtab rows),
 * optionally renamed via {@link Builder#stateVar(String)}. Variants: box,
 * vertical (with the flex layout chrome), filled, secondary, subtab, page
 * insets, nav-link tabs, verbatim modifier classes, and the animated
 * current-tab accent. {@link Builder#panelsOnly()} renders just the panels.
 * The heavier overflow / dynamic / expandable / help / close examples stay
 * hand-written — see the tabs demo page.
 */
@TemplateData
public final class Tabs {

    private final String id;
    private final String ariaLabel;
    private final String active;
    private final String stateVar;
    private final boolean ownState;
    private final boolean box;
    private final boolean vertical;
    private final boolean filled;
    private final boolean secondary;
    private final boolean subtab;
    private final boolean pageInsets;
    private final boolean noBorderBottom;
    private final boolean nav;
    private final String inset;
    private final String modifiers;
    private final boolean animated;
    private final boolean panelPadding;
    private final boolean panelSecondary;
    private final boolean panelsOnly;
    private final List<TabItem> items;

    private Tabs(Builder b) {
        this.id = b.id;
        this.ariaLabel = b.ariaLabel;
        this.active = b.active;
        this.stateVar = b.stateVar;
        this.ownState = b.ownState;
        this.box = b.box;
        this.vertical = b.vertical;
        this.filled = b.filled;
        this.secondary = b.secondary;
        this.subtab = b.subtab;
        this.pageInsets = b.pageInsets;
        this.noBorderBottom = b.noBorderBottom;
        this.nav = b.nav;
        this.inset = b.inset;
        this.modifiers = b.modifiers;
        this.animated = b.animated;
        this.panelPadding = b.panelPadding;
        this.panelSecondary = b.panelSecondary;
        this.panelsOnly = b.panelsOnly;
        this.items = List.copyOf(b.items);
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    public String id() {
        return id;
    }

    public String ariaLabel() {
        return ariaLabel;
    }

    /** Initially selected key (defaults to the first item's key). */
    public String activeKey() {
        return active != null ? active : items.get(0).key();
    }

    public String stateVar() {
        return stateVar;
    }

    public boolean isOwnState() {
        return ownState;
    }

    public boolean isBox() {
        return box;
    }

    public boolean isVertical() {
        return vertical;
    }

    public boolean isFilled() {
        return filled;
    }

    public boolean isSecondary() {
        return secondary;
    }

    public boolean isSubtab() {
        return subtab;
    }

    public boolean isPageInsets() {
        return pageInsets;
    }

    public boolean isNoBorderBottom() {
        return noBorderBottom;
    }

    public boolean isNav() {
        return nav;
    }

    public String inset() {
        return inset;
    }

    public String modifiers() {
        return modifiers;
    }

    public boolean isAnimated() {
        return animated;
    }

    public boolean isPanelPadding() {
        return panelPadding;
    }

    public boolean isPanelSecondary() {
        return panelSecondary;
    }

    public boolean isPanelsOnly() {
        return panelsOnly;
    }

    public boolean isHasPanels() {
        return items.stream().anyMatch(i -> i.panelHtml() != null);
    }

    public List<TabItem> items() {
        return items;
    }

    public static final class Builder {

        private String id;
        private String ariaLabel;
        private String active;
        private String stateVar = "active";
        private boolean ownState = true;
        private boolean box;
        private boolean vertical;
        private boolean filled;
        private boolean secondary;
        private boolean subtab;
        private boolean pageInsets;
        private boolean noBorderBottom;
        private boolean nav;
        private String inset;
        private String modifiers;
        private boolean animated;
        private boolean panelPadding;
        private boolean panelSecondary;
        private boolean panelsOnly;
        private final List<TabItem> items = new ArrayList<>();

        private Builder() {
        }

        /** Accessible name of the tab list. */
        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        /** Initially selected key (defaults to the first item's key). */
        public Builder active(String active) {
            this.active = active;
            return this;
        }

        /** Rename the Alpine selection variable (e.g. {@code "sub"} for a secondary row). */
        public Builder stateVar(String stateVar) {
            this.stateVar = Objects.requireNonNull(stateVar, "stateVar");
            return this;
        }

        /** Bind to a selection variable owned by surrounding chrome instead of rendering a wrapper. */
        public Builder externalState() {
            this.ownState = false;
            return this;
        }

        public Builder box() {
            this.box = true;
            return this;
        }

        /** Vertical strip with the flex layout chrome around strip and panels. */
        public Builder vertical() {
            this.vertical = true;
            return this;
        }

        /** pf-m-fill — tabs stretch across the full width. */
        public Builder filled() {
            this.filled = true;
            return this;
        }

        /** Secondary treatment (pair with {@link #box()}). */
        public Builder secondary() {
            this.secondary = true;
            return this;
        }

        /** Subtab row treatment. */
        public Builder subtab() {
            this.subtab = true;
            return this;
        }

        public Builder pageInsets() {
            this.pageInsets = true;
            return this;
        }

        public Builder noBorderBottom() {
            this.noBorderBottom = true;
            return this;
        }

        /** Navigation-link tabs: anchors with aria-current, no ARIA tablist. */
        public Builder nav() {
            this.nav = true;
            return this;
        }

        /** Single-value inset: sm | md | lg | xl | 2xl | none. */
        public Builder inset(String inset) {
            this.inset = inset;
            return this;
        }

        /** Extra pf-m-* classes verbatim (responsive insets etc.). */
        public Builder modifiers(String modifiers) {
            this.modifiers = modifiers;
            return this;
        }

        /** Animated current-tab accent — emits the live accent-measuring wrapper. */
        public Builder animated() {
            this.animated = true;
            return this;
        }

        /** pf-m-padding on every panel body. */
        public Builder panelPadding() {
            this.panelPadding = true;
            return this;
        }

        /** pf-m-secondary on every panel. */
        public Builder panelSecondary() {
            this.panelSecondary = true;
            return this;
        }

        /** Render only the panels (no strip). */
        public Builder panelsOnly() {
            this.panelsOnly = true;
            return this;
        }

        public Builder item(TabItem item) {
            items.add(Objects.requireNonNull(item, "item"));
            return this;
        }

        public Builder items(TabItem... items) {
            for (TabItem item : items) {
                item(item);
            }
            return this;
        }

        public Tabs build() {
            if (items.isEmpty()) {
                throw new IllegalStateException("Tabs need at least one item");
            }
            if (animated && !ownState) {
                throw new IllegalStateException("animated() needs the include-owned state wrapper");
            }
            return new Tabs(this);
        }
    }
}
