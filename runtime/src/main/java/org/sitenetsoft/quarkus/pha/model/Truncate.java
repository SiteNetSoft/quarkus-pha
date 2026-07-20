package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Truncate component ({@code pf-v6-c-truncate}).
 *
 * <pre>
 * Truncate.of(longText).build()                              // end (default)
 * Truncate.of(path).position("start").build()
 * Truncate.of(name).position("middle").trailingNumChars(8).build()
 * Truncate.of(name).maxChars(20).build()                     // fixed char budget (Alpine)
 * Truncate.of(name).link("#").build()                        // truncating anchor
 * </pre>
 *
 * <p>Middle truncation splits the text in Java ({@code startText}/{@code endText});
 * {@code maxChars} renders the Alpine char-budget flavour; {@code link} renders
 * a block anchor that truncates via CSS instead of a pf-v6-c-truncate span.
 */
@TemplateData
public final class Truncate {

    private final String content;
    private final String id;
    private final String position;
    private final int trailingNumChars;
    private final Integer maxChars;
    private final String linkHref;

    private Truncate(Builder b) {
        this.content = b.content;
        this.id = b.id;
        this.position = b.position;
        this.trailingNumChars = b.trailingNumChars;
        this.maxChars = b.maxChars;
        this.linkHref = b.linkHref;
    }

    public static Builder of(String content) {
        Builder b = new Builder();
        b.content = Objects.requireNonNull(content, "content");
        return b;
    }

    public String content() {
        return content;
    }

    public String id() {
        return id;
    }

    public boolean isLink() {
        return linkHref != null;
    }

    public String linkHref() {
        return linkHref;
    }

    public boolean isMaxChars() {
        return maxChars != null;
    }

    /** The Alpine state for the char-budget flavour. */
    public String maxCharsXData() {
        return "{ full: '" + content.replace("'", "\\'") + "' }";
    }

    /** The Alpine x-text expression for the char-budget flavour. */
    public String maxCharsExpr() {
        return "full.length > " + maxChars + " ? full.slice(0, " + maxChars + ") + '\\u2026' : full";
    }

    public boolean isMiddle() {
        return "middle".equals(position);
    }

    public boolean isStart() {
        return "start".equals(position);
    }

    /** Middle mode: everything before the trailing chars. */
    public String startText() {
        return content.substring(0, content.length() - trailingNumChars);
    }

    /** Middle mode: the always-visible tail. */
    public String endText() {
        return content.substring(content.length() - trailingNumChars);
    }

    public static final class Builder {
        private String content;
        private String id;
        private String position;
        private int trailingNumChars = 7;
        private Integer maxChars;
        private String linkHref;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** end (default) | start | middle. */
        public Builder position(String position) {
            this.position = position;
            return this;
        }

        /** Middle mode: how many trailing characters stay visible (default 7). */
        public Builder trailingNumChars(int trailingNumChars) {
            this.trailingNumChars = trailingNumChars;
            return this;
        }

        /** Fixed character budget instead of container width (Alpine flavour). */
        public Builder maxChars(int maxChars) {
            this.maxChars = maxChars;
            return this;
        }

        /** Render as a truncating block anchor with this target. */
        public Builder link(String href) {
            this.linkHref = href;
            return this;
        }

        public Truncate build() {
            return new Truncate(this);
        }
    }
}
