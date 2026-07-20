package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Expandable section component
 * ({@code pf-v6-c-expandable-section}).
 *
 * <pre>
 * ExpandableSection.of("es-collapsed", "Show advanced settings")
 *     .content("&lt;p&gt;Advanced settings…&lt;/p&gt;").build()
 * ExpandableSection.of("es-more", null)
 *     .dynamicToggle("Show more", "Show less").content("…").build()
 * </pre>
 *
 * <p>Covers the collapsed/expanded default shape plus every variation:
 * detached ({@code pf-m-expand-top}, content above the toggle), disclosure
 * (large + width limit), indented, truncate expansion (content always
 * rendered, no toggle icon), dynamic toggle text, custom toggle markup and
 * heading-wrapped toggles. Content renders unescaped.
 */
@TemplateData
public final class ExpandableSection {

    private final String id;
    private final String toggleText;
    private final String contentHtml;
    private final boolean expanded;
    private final boolean detached;
    private final boolean disclosure;
    private final boolean indented;
    private final boolean truncate;
    private final String showMoreText;
    private final String showLessText;
    private final String toggleHtml;
    private final String headingLevel;
    private final String style;

    private ExpandableSection(Builder b) {
        this.id = b.id;
        this.toggleText = b.toggleText;
        this.contentHtml = b.contentHtml;
        this.expanded = b.expanded;
        this.detached = b.detached;
        this.disclosure = b.disclosure;
        this.indented = b.indented;
        this.truncate = b.truncate;
        this.showMoreText = b.showMoreText;
        this.showLessText = b.showLessText;
        this.toggleHtml = b.toggleHtml;
        this.headingLevel = b.headingLevel;
        this.style = b.style;
    }

    /** id plus the static toggle text (null when using dynamicToggle or toggleHtml). */
    public static Builder of(String id, String toggleText) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        b.toggleText = toggleText;
        return b;
    }

    public String id() {
        return id;
    }

    public String toggleText() {
        return toggleText;
    }

    public String contentHtml() {
        return contentHtml;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public boolean isDetached() {
        return detached;
    }

    public boolean isDisclosure() {
        return disclosure;
    }

    public boolean isIndented() {
        return indented;
    }

    public boolean isTruncate() {
        return truncate;
    }

    public boolean isDynamicToggle() {
        return showMoreText != null;
    }

    public String showMoreText() {
        return showMoreText;
    }

    public String showLessText() {
        return showLessText;
    }

    public String toggleHtml() {
        return toggleHtml;
    }

    public String headingLevel() {
        return headingLevel;
    }

    public String style() {
        return style;
    }

    public static final class Builder {

        private String id;
        private String toggleText;
        private String contentHtml;
        private boolean expanded;
        private boolean detached;
        private boolean disclosure;
        private boolean indented;
        private boolean truncate;
        private String showMoreText;
        private String showLessText;
        private String toggleHtml;
        private String headingLevel;
        private String style;

        private Builder() {
        }

        /** Section content (rendered unescaped). */
        public Builder content(String contentHtml) {
            this.contentHtml = contentHtml;
            return this;
        }

        /** Start open. */
        public Builder asExpanded() {
            this.expanded = true;
            return this;
        }

        /** Content sits above the toggle ({@code pf-m-expand-top}). */
        public Builder asDetached() {
            this.detached = true;
            return this;
        }

        /** Disclosure variation — large display size with a width limit. */
        public Builder asDisclosure() {
            this.disclosure = true;
            return this;
        }

        /** Content aligns under the toggle text ({@code pf-m-indented}). */
        public Builder asIndented() {
            this.indented = true;
            return this;
        }

        /** Truncate expansion — content always rendered but clamped; no toggle icon. */
        public Builder asTruncate() {
            this.truncate = true;
            return this;
        }

        /** Toggle label flips with the state. */
        public Builder dynamicToggle(String showMoreText, String showLessText) {
            this.showMoreText = Objects.requireNonNull(showMoreText, "showMoreText");
            this.showLessText = Objects.requireNonNull(showLessText, "showLessText");
            return this;
        }

        /** Custom toggle markup after the icon (rendered unescaped). */
        public Builder toggleHtml(String toggleHtml) {
            this.toggleHtml = toggleHtml;
            return this;
        }

        /** Wrap the toggle in a real heading element for the page outline. */
        public Builder headingLevel(String headingLevel) {
            this.headingLevel = headingLevel;
            return this;
        }

        /** Inline style on the root, e.g. {@code "max-width: 480px"}. */
        public Builder style(String style) {
            this.style = style;
            return this;
        }

        public ExpandableSection build() {
            return new ExpandableSection(this);
        }
    }
}
