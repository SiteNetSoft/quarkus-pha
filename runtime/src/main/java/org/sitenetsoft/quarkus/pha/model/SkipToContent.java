package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Skip to content link ({@code pf-v6-c-skip-to-content}).
 *
 * <pre>
 * SkipToContent.of("#main-content", "Skip to content").build()
 * </pre>
 *
 * <p>The text is the link's accessible name — always provide it.
 */
@TemplateData
public final class SkipToContent {

    private final String href;
    private final String text;
    private final String id;

    private SkipToContent(Builder b) {
        this.href = b.href;
        this.text = b.text;
        this.id = b.id;
    }

    public static Builder of(String href, String text) {
        Builder b = new Builder();
        b.href = Objects.requireNonNull(href, "href");
        b.text = Objects.requireNonNull(text, "text");
        return b;
    }

    public String href() {
        return href;
    }

    public String text() {
        return text;
    }

    public String id() {
        return id;
    }

    public static final class Builder {
        private String href;
        private String text;
        private String id;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public SkipToContent build() {
            return new SkipToContent(this);
        }
    }
}
