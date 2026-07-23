package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Banner component ({@code pf-v6-c-banner}).
 *
 * <pre>
 * Banner.of("Default banner").build()
 * Banner.of("Warning banner message").variant("warning")
 *     .screenReaderText("Warning:").build()
 * </pre>
 *
 * <p>Status variants (success/warning/danger/info/custom) auto-render their
 * icon; color variants render plain. The message renders unescaped so inline
 * links are allowed.
 */
@TemplateData
public final class Banner {

    private final String id;
    private final String messageHtml;
    private final String variant;
    private final boolean sticky;
    private final boolean pill;
    private final String screenReaderText;

    private Banner(Builder b) {
        this.id = b.id;
        this.messageHtml = b.messageHtml;
        this.variant = b.variant;
        this.sticky = b.sticky;
        this.pill = b.pill;
        this.screenReaderText = b.screenReaderText;
    }

    public static Builder of(String messageHtml) {
        Builder b = new Builder();
        b.messageHtml = Objects.requireNonNull(messageHtml, "messageHtml");
        return b;
    }

    public String id() {
        return id;
    }

    public String messageHtml() {
        return messageHtml;
    }

    public String variant() {
        return variant;
    }

    /** Stock status icon, or null for color/default variants. */
    public String statusIcon() {
        if (variant == null) {
            return null;
        }
        return switch (variant) {
            case "success" -> "fa:circle-check";
            case "warning" -> "fa:triangle-exclamation";
            case "danger" -> "fa:circle-exclamation";
            case "info" -> "fa:circle-info";
            default -> null;
        };
    }

    public boolean isPill() {
        return pill;
    }

    public boolean isSticky() {
        return sticky;
    }

    public String screenReaderText() {
        return screenReaderText;
    }

    public static final class Builder {
        private String id;
        private String messageHtml;
        private String variant;
        private boolean sticky;
        private boolean pill;
        private String screenReaderText;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** Status (success|warning|danger|info|custom) or color (red|orangered|orange|yellow|green|teal|blue|purple). */
        public Builder variant(String variant) {
            this.variant = variant;
            return this;
        }

        /** Pins to the top of its scroll container. */
        /** Pill-shaped banner (PF 6.6 pf-m-pill). */
        public Builder pill() {
            this.pill = true;
            return this;
        }

        public Builder asSticky() {
            this.sticky = true;
            return this;
        }

        /** Visually-hidden prefix announced by AT, e.g. {@code "Warning:"}. */
        public Builder screenReaderText(String screenReaderText) {
            this.screenReaderText = screenReaderText;
            return this;
        }

        public Banner build() {
            return new Banner(this);
        }
    }
}
