package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

/**
 * Typed model for the Divider component ({@code pf-v6-c-divider}).
 *
 * <pre>
 * Divider.builder().build()                       // semantic <hr>
 * Divider.builder().vertical().inset("sm").build()
 * Divider.builder().insetOn("md", "sm").insetOn("lg", "md").build()
 * </pre>
 *
 * <p>Renders an {@code <hr>} by default, or a {@code <div>}/{@code <li>}
 * separator; supports base and per-breakpoint insets plus orientation
 * switches at breakpoints.
 */
@TemplateData
public final class Divider {

    private final String component;
    private final boolean vertical;
    private final String inset;
    private final String insetOnSm;
    private final String insetOnMd;
    private final String insetOnLg;
    private final String insetOnXl;
    private final String insetOn2xl;
    private final String verticalOn;
    private final String horizontalOn;

    private Divider(Builder b) {
        this.component = b.component;
        this.vertical = b.vertical;
        this.inset = b.inset;
        this.insetOnSm = b.insetOnSm;
        this.insetOnMd = b.insetOnMd;
        this.insetOnLg = b.insetOnLg;
        this.insetOnXl = b.insetOnXl;
        this.insetOn2xl = b.insetOn2xl;
        this.verticalOn = b.verticalOn;
        this.horizontalOn = b.horizontalOn;
    }

    public static Builder builder() {
        return new Builder();
    }

    /** True for div/li shapes (vertical implies div when unset). */
    public boolean isElementShape() {
        return vertical || component != null;
    }

    public String element() {
        return component != null ? component : "div";
    }

    public boolean isVertical() {
        return vertical;
    }

    public String inset() {
        return inset;
    }

    public String insetOnSm() {
        return insetOnSm;
    }

    public String insetOnMd() {
        return insetOnMd;
    }

    public String insetOnLg() {
        return insetOnLg;
    }

    public String insetOnXl() {
        return insetOnXl;
    }

    public String insetOn2xl() {
        return insetOn2xl;
    }

    public String verticalOn() {
        return verticalOn;
    }

    public String horizontalOn() {
        return horizontalOn;
    }

    public static final class Builder {
        private String component;
        private boolean vertical;
        private String inset;
        private String insetOnSm;
        private String insetOnMd;
        private String insetOnLg;
        private String insetOnXl;
        private String insetOn2xl;
        private String verticalOn;
        private String horizontalOn;

        private Builder() {
        }

        /** hr (default) | div | li. */
        public Builder component(String component) {
            this.component = component;
            return this;
        }

        public Builder vertical() {
            this.vertical = true;
            return this;
        }

        /** Base inset token: none | xs | sm | md | lg | xl | 2xl | 3xl. */
        public Builder inset(String inset) {
            this.inset = inset;
            return this;
        }

        /** Per-breakpoint inset, e.g. {@code insetOn("md", "sm")} → pf-m-inset-sm-on-md. */
        public Builder insetOn(String breakpoint, String size) {
            switch (breakpoint) {
                case "sm" -> this.insetOnSm = size;
                case "md" -> this.insetOnMd = size;
                case "lg" -> this.insetOnLg = size;
                case "xl" -> this.insetOnXl = size;
                case "2xl" -> this.insetOn2xl = size;
                default -> throw new IllegalArgumentException("Unknown breakpoint: " + breakpoint);
            }
            return this;
        }

        /** Switch to vertical at the named breakpoint (md | lg | xl | 2xl). */
        public Builder verticalOn(String breakpoint) {
            this.verticalOn = breakpoint;
            return this;
        }

        /** Switch back to horizontal at the named breakpoint. */
        public Builder horizontalOn(String breakpoint) {
            this.horizontalOn = breakpoint;
            return this;
        }

        public Divider build() {
            return new Divider(this);
        }
    }
}
