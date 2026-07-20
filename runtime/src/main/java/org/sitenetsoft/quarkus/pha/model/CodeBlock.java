package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Code block component ({@code pf-v6-c-code-block}).
 *
 * <pre>
 * CodeBlock.of("cb-basic", "apiVersion: v1\\nkind: Pod").build()
 * CodeBlock.of("cb-copy", curlCommand).withCopy().build()
 * </pre>
 *
 * <p>{@code withCopy()} renders the header copy-to-clipboard button with its
 * Alpine wiring (copied flag, aria-label swap, 2s reset). Code is escaped text
 * — whitespace and line breaks are preserved by the pre element.
 */
@TemplateData
public final class CodeBlock {

    private final String id;
    private final String code;
    private final boolean copy;

    private CodeBlock(Builder b) {
        this.id = b.id;
        this.code = b.code;
        this.copy = b.copy;
    }

    public static Builder of(String id, String code) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        b.code = Objects.requireNonNull(code, "code");
        return b;
    }

    public String id() {
        return id;
    }

    public String code() {
        return code;
    }

    public boolean isWithCopy() {
        return copy;
    }

    public static final class Builder {
        private String id;
        private String code;
        private boolean copy;

        private Builder() {
        }

        /** Header copy-to-clipboard button (Alpine). */
        public Builder withCopy() {
            this.copy = true;
            return this;
        }

        public CodeBlock build() {
            return new CodeBlock(this);
        }
    }
}
