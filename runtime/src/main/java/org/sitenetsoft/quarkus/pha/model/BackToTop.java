package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

/**
 * Typed model for the Back to top button ({@code pf-v6-c-back-to-top}).
 *
 * <pre>
 * BackToTop.of().id("btt").build()
 * BackToTop.of().alwaysVisible().build()
 * BackToTop.of().scrollableSelector("#scroll-pane").build()
 * </pre>
 *
 * <p>Requires /web/js/alpine/components/back-to-top.js (phaBackToTop) on the
 * host page; the x-data arguments are generated at build time.
 */
@TemplateData
public final class BackToTop {

    private final String id;
    private final String title;
    private final boolean alwaysVisible;
    private final String scrollableSelector;

    private BackToTop(Builder b) {
        this.id = b.id;
        this.title = b.title;
        this.alwaysVisible = b.alwaysVisible;
        this.scrollableSelector = b.scrollableSelector;
    }

    public static Builder of() {
        return new Builder();
    }

    public String id() {
        return id;
    }

    public String title() {
        return title != null ? title : "Back to top";
    }

    public String xData() {
        String sel = scrollableSelector != null ? "'" + scrollableSelector + "'" : "null";
        return "phaBackToTop(" + sel + ", " + alwaysVisible + ")";
    }

    public static final class Builder {
        private String id;
        private String title;
        private boolean alwaysVisible;
        private String scrollableSelector;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** Button label (default "Back to top"). */
        public Builder title(String title) {
            this.title = title;
            return this;
        }

        /** Skip the 400px scroll threshold. */
        public Builder alwaysVisible() {
            this.alwaysVisible = true;
            return this;
        }

        /** Spy on (and scroll) this element instead of the window. */
        public Builder scrollableSelector(String scrollableSelector) {
            this.scrollableSelector = scrollableSelector;
            return this;
        }

        public BackToTop build() {
            return new BackToTop(this);
        }
    }
}
