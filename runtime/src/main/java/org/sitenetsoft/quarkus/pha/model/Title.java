package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Title component ({@code pf-v6-c-title}).
 *
 * <pre>
 * Title.of("h1 heading").build()                       // h1, size follows level
 * Title.of("4xl title").headingLevel("h2").size("4xl").build()
 * </pre>
 *
 * <p>Without a size the modifier follows the heading level (h2 → pf-m-h2),
 * matching the param shell.
 */
@TemplateData
public final class Title {

    private final String text;
    private final String headingLevel;
    private final String size;
    private final String id;

    private Title(Builder b) {
        this.text = b.text;
        this.headingLevel = b.headingLevel;
        this.size = b.size;
        this.id = b.id;
    }

    public static Builder of(String text) {
        Builder b = new Builder();
        b.text = Objects.requireNonNull(text, "text");
        return b;
    }

    public String text() {
        return text;
    }

    public String headingTag() {
        return headingLevel != null ? headingLevel : "h1";
    }

    /** The pf-m-* size token — explicit size or the heading level. */
    public String sizeToken() {
        return size != null ? size : headingTag();
    }

    public String id() {
        return id;
    }

    public static final class Builder {
        private String text;
        private String headingLevel;
        private String size;
        private String id;

        private Builder() {
        }

        /** h1 (default) | h2 | h3 | h4 | h5 | h6. */
        public Builder headingLevel(String headingLevel) {
            this.headingLevel = headingLevel;
            return this;
        }

        /** md | lg | xl | 2xl | 3xl | 4xl. */
        public Builder size(String size) {
            this.size = size;
            return this;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Title build() {
            return new Title(this);
        }
    }
}
