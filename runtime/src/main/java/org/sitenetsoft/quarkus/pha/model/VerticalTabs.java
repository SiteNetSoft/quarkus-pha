package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for Vertical tabs ({@code vertical-tabs-pf}, PatternFly
 * catalog-view extension). Despite the name this is a navigation list, not an
 * ARIA tablist — items link to pages or HTMX-swap a sibling region.
 *
 * <pre>
 * VerticalTabs.of("vt-demo")
 *     .tab(VerticalTabs.Tab.of("Overview").id("vt-overview").href("#"))
 *     .tab(VerticalTabs.Tab.of("Databases").id("vt-databases").href("#").active()
 *         .child(VerticalTabs.Tab.of("PostgreSQL").id("vt-postgres").href("#")))
 *     .build();
 * </pre>
 *
 * <p>Nesting goes through {@code child(...)} — the template recurses over the
 * data (which sidesteps Qute's slot-recursion limitation that forced the old
 * slot-driven example to hand-render its nested branch).
 */
@TemplateData
public final class VerticalTabs {

    private final String id;
    private final String ariaLabel;
    private final boolean restrictTabs;
    private final boolean activeTab;
    private final List<Tab> tabs;

    private VerticalTabs(Builder b) {
        this.id = b.id;
        this.ariaLabel = b.ariaLabel;
        this.restrictTabs = b.restrictTabs;
        this.activeTab = b.activeTab;
        List<Tab> built = new ArrayList<>(b.tabs.size());
        for (Tab.Builder tb : b.tabs) {
            built.add(tb.buildTab());
        }
        this.tabs = List.copyOf(built);
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = id;
        return b;
    }

    public String id() {
        return id;
    }

    public String ariaLabel() {
        return ariaLabel != null ? ariaLabel : "Vertical navigation";
    }

    public boolean restrictTabs() {
        return restrictTabs;
    }

    public boolean activeTab() {
        return activeTab;
    }

    public List<Tab> tabs() {
        return tabs;
    }

    /** One navigation item; children render as a nested vertical-tabs-pf list. */
    @TemplateData
    public record Tab(String id, String title, String href, boolean active, List<Tab> children) {

        public static Builder of(String title) {
            Builder b = new Builder();
            b.title = Objects.requireNonNull(title, "title");
            return b;
        }

        public static final class Builder {
            private String id;
            private String title;
            private String href;
            private boolean active;
            private final List<Tab> children = new ArrayList<>();

            private Builder() {
            }

            public Builder id(String id) {
                this.id = id;
                return this;
            }

            /** Renders the title as an anchor; omit for a plain span. */
            public Builder href(String href) {
                this.href = href;
                return this;
            }

            /** Current item: active class + aria-current on the link. */
            public Builder active() {
                this.active = true;
                return this;
            }

            public Builder child(Builder child) {
                this.children.add(child.buildTab());
                return this;
            }

            Tab buildTab() {
                return new Tab(id, title, href, active, List.copyOf(children));
            }
        }
    }

    public static final class Builder {
        private String id;
        private String ariaLabel;
        private boolean restrictTabs;
        private boolean activeTab;
        private final List<Tab.Builder> tabs = new ArrayList<>();

        private Builder() {
        }

        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        /** Restrict display to the active path (extension's restrict-tabs class). */
        public Builder restrictTabs() {
            this.restrictTabs = true;
            return this;
        }

        /** Direct-child active marker (extension's active-tab class, with restrictTabs). */
        public Builder activeTab() {
            this.activeTab = true;
            return this;
        }

        public Builder tab(Tab.Builder tab) {
            this.tabs.add(tab);
            return this;
        }

        public VerticalTabs build() {
            return new VerticalTabs(this);
        }
    }
}
