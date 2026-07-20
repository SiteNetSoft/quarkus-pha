package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Pagination component ({@code pf-v6-c-pagination}).
 *
 * <pre>
 * Pagination.of("pg-top", "Pagination nav - top example").build()
 * Pagination.of("pg-bottom", "…").asBottom().build()
 * Pagination.of("pg-offset", "…").startPage(3).build()
 * Pagination.of("pg-no-items", "…").total(0).build()
 * </pre>
 *
 * <p>Renders the canonical live pagination: total-items text, the per-page
 * menu toggle (10/20/50), and the nav with first/prev/page-input/next/last
 * controls, all wired through generated Alpine state. Variants: bottom
 * (menu opens upward, no total-items), compact (prev/next only), disabled,
 * indeterminate ("of many"), plus verbatim modifier classes for insets,
 * display presets and sticky positioning. {@link Builder#stickyToggle()} /
 * {@link Builder#stickyScroll()} bind the sticky classes to an outer-scope
 * Alpine variable owned by the surrounding demo chrome.
 */
@TemplateData
public final class Pagination {

    private final String id;
    private final String navLabel;
    private final int total;
    private final int perPage;
    private final int startPage;
    private final boolean bottom;
    private final boolean compact;
    private final boolean disabled;
    private final boolean indeterminate;
    private final String modifiers;
    private final String stickyMode;

    private Pagination(Builder b) {
        this.id = b.id;
        this.navLabel = b.navLabel;
        this.total = b.total;
        this.perPage = b.perPage;
        this.startPage = b.startPage;
        this.bottom = b.bottom;
        this.compact = b.compact;
        this.disabled = b.disabled;
        this.indeterminate = b.indeterminate;
        this.modifiers = b.modifiers;
        this.stickyMode = b.stickyMode;
    }

    /** id (inner ids derive from it) and a unique aria-label for the nav landmark. */
    public static Builder of(String id, String navLabel) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        b.navLabel = Objects.requireNonNull(navLabel, "navLabel");
        return b;
    }

    public String id() {
        return id;
    }

    public String navLabel() {
        return navLabel;
    }

    public int total() {
        return total;
    }

    public int perPage() {
        return perPage;
    }

    public int startPage() {
        return startPage;
    }

    public boolean isBottom() {
        return bottom;
    }

    public boolean isCompact() {
        return compact;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public boolean isIndeterminate() {
        return indeterminate;
    }

    public String modifiers() {
        return modifiers;
    }

    /** "toggle" | "scroll" | null — which outer-scope sticky binding to emit. */
    public String stickyMode() {
        return stickyMode;
    }

    public boolean isStickyToggle() {
        return "toggle".equals(stickyMode);
    }

    public boolean isStickyScroll() {
        return "scroll".equals(stickyMode);
    }

    public String toggleId() {
        return id + "-toggle";
    }

    public static final class Builder {

        private String id;
        private String navLabel;
        private int total = 523;
        private int perPage = 10;
        private int startPage = 1;
        private boolean bottom;
        private boolean compact;
        private boolean disabled;
        private boolean indeterminate;
        private String modifiers;
        private String stickyMode;

        private Builder() {
        }

        /** Item count (default 523); pass 0 for the no-items case. */
        public Builder total(int total) {
            this.total = total;
            return this;
        }

        /** Initial page size (default 10). */
        public Builder perPage(int perPage) {
            this.perPage = perPage;
            return this;
        }

        /** Initial page (default 1). */
        public Builder startPage(int startPage) {
            this.startPage = startPage;
            return this;
        }

        /** Bottom pagination — no total-items block; the per-page menu opens upward. */
        public Builder asBottom() {
            this.bottom = true;
            return this;
        }

        /** Compact — prev/next controls only. */
        public Builder asCompact() {
            this.compact = true;
            return this;
        }

        /** Every control rendered disabled. */
        public Builder asDisabled() {
            this.disabled = true;
            return this;
        }

        /** Unknown item count — "of many", no input max, last control disabled. */
        public Builder asIndeterminate() {
            this.indeterminate = true;
            return this;
        }

        /** Extra classes on the root, verbatim (insets, display-*, sticky, plain, static). */
        public Builder modifiers(String modifiers) {
            this.modifiers = modifiers;
            return this;
        }

        /** Bind pf-m-sticky to an outer-scope `sticky` Alpine variable (checkbox demo). */
        public Builder stickyToggle() {
            this.stickyMode = "toggle";
            return this;
        }

        /** Bind pf-m-sticky-stuck to an outer-scope `stuck` Alpine variable (scroll demo). */
        public Builder stickyScroll() {
            this.stickyMode = "scroll";
            return this;
        }

        public Pagination build() {
            return new Pagination(this);
        }
    }
}
