package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Background image component ({@code pf-v6-c-background-image}).
 *
 * <pre>
 * BackgroundImage.of("/web/images/pf-background.svg").id("bg").build()
 * </pre>
 */
@TemplateData
public final class BackgroundImage {

    private final String src;
    private final String id;

    private BackgroundImage(Builder b) {
        this.src = b.src;
        this.id = b.id;
    }

    public static Builder of(String src) {
        Builder b = new Builder();
        b.src = Objects.requireNonNull(src, "src");
        return b;
    }

    public String src() {
        return src;
    }

    public String id() {
        return id;
    }

    public static final class Builder {
        private String src;
        private String id;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public BackgroundImage build() {
            return new BackgroundImage(this);
        }
    }
}
