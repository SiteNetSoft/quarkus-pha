package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

/**
 * Typed model for the Spinner component ({@code pf-v6-c-spinner}).
 *
 * <pre>
 * Spinner.of("Loading").build()
 * Spinner.of("Loading (xl)").size("xl").build()
 * Spinner.of("Loading (80px)").diameter("80px").build()
 * Spinner.of("Loading").inline().build()
 * </pre>
 *
 * <p>{@code diameter} (a CSS length) overrides {@code size} tokens; inline
 * spinners inherit the surrounding font size and color.
 */
@TemplateData
public final class Spinner {

    private final String label;
    private final String size;
    private final String diameter;
    private final boolean inline;
    private final String ariaValuetext;

    private Spinner(Builder b) {
        this.label = b.label;
        this.size = b.size;
        this.diameter = b.diameter;
        this.inline = b.inline;
        this.ariaValuetext = b.ariaValuetext;
    }

    public static Builder of(String label) {
        Builder b = new Builder();
        b.label = label != null ? label : "Loading";
        return b;
    }

    public String label() {
        return label;
    }

    public String size() {
        return size;
    }

    public String diameter() {
        return diameter;
    }

    public boolean isInline() {
        return inline;
    }

    public String ariaValuetext() {
        return ariaValuetext != null ? ariaValuetext : "Loading...";
    }

    public static final class Builder {
        private String label;
        private String size;
        private String diameter;
        private boolean inline;
        private String ariaValuetext;

        private Builder() {
        }

        /** xs | sm | md | lg | xl. */
        public Builder size(String size) {
            this.size = size;
            return this;
        }

        /** CSS length (e.g. "5rem", "80px"); overrides size. */
        public Builder diameter(String diameter) {
            this.diameter = diameter;
            return this;
        }

        /** Inherit surrounding font size and color (pf-m-inline). */
        public Builder inline() {
            this.inline = true;
            return this;
        }

        /** Progress text (aria-valuetext); defaults to "Loading...". */
        public Builder ariaValuetext(String ariaValuetext) {
            this.ariaValuetext = ariaValuetext;
            return this;
        }

        public Spinner build() {
            return new Spinner(this);
        }
    }
}
