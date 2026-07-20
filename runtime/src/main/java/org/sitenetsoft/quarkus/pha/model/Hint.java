package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Hint component ({@code pf-v6-c-hint}).
 *
 * <pre>
 * Hint.of("ht-basic").title("Pro tip").body("You can drag and drop files…").build()
 * Hint.of("ht-plain").body("A hint without a title.").build()
 * Hint.of("ht-action").title("Hint with action").body("Need to migrate?")
 *     .footerHtml("&lt;a class=\\"pf-v6-c-button pf-m-link pf-m-inline\\" href=\\"#\\"&gt;…&lt;/a&gt;")
 *     .build()
 * </pre>
 *
 * <p>The footer is raw HTML — typically an inline link button.
 */
@TemplateData
public final class Hint {

    private final String id;
    private final String title;
    private final String bodyText;
    private final String footerHtml;

    private Hint(Builder b) {
        this.id = b.id;
        this.title = b.title;
        this.bodyText = b.bodyText;
        this.footerHtml = b.footerHtml;
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    public String id() {
        return id;
    }

    public String title() {
        return title;
    }

    public String bodyText() {
        return bodyText;
    }

    public String footerHtml() {
        return footerHtml;
    }

    public static final class Builder {
        private String id;
        private String title;
        private String bodyText;
        private String footerHtml;

        private Builder() {
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder body(String bodyText) {
            this.bodyText = bodyText;
            return this;
        }

        /** Footer content (raw HTML) — typically an inline link button. */
        public Builder footerHtml(String footerHtml) {
            this.footerHtml = footerHtml;
            return this;
        }

        public Hint build() {
            if (title == null && bodyText == null) {
                throw new IllegalStateException("A hint needs a title or a body");
            }
            return new Hint(this);
        }
    }
}
