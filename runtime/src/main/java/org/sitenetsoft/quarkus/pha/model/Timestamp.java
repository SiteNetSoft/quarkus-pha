package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Timestamp component ({@code pf-v6-c-timestamp}).
 *
 * <pre>
 * Timestamp.of("May 20, 2026 at 2:30 PM").datetime("2026-05-20T14:30:00Z").build()
 * Timestamp.of("2 hours ago").datetime("2026-05-20T14:30:00Z").build()
 * Timestamp.of("2 hours ago").tooltip("2026-05-20 14:30:00 UTC").build()
 * </pre>
 *
 * <p>The display text is pre-formatted server-side (formatting is a route
 * concern); {@code datetime} carries the machine-readable ISO 8601 value.
 */
@TemplateData
public final class Timestamp {

    private final String text;
    private final String id;
    private final String datetime;
    private final String tooltip;

    private Timestamp(Builder b) {
        this.text = b.text;
        this.id = b.id;
        this.datetime = b.datetime;
        this.tooltip = b.tooltip;
    }

    public static Builder of(String text) {
        Builder b = new Builder();
        b.text = Objects.requireNonNull(text, "text");
        return b;
    }

    public String text() {
        return text;
    }

    public String id() {
        return id;
    }

    public String datetime() {
        return datetime;
    }


    public String tooltip() {
        return tooltip;
    }

    public static final class Builder {
        private String text;
        private String id;
        private String datetime;
        private String tooltip;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** Machine-readable ISO 8601 value for the {@code <time datetime>} attribute. */
        public Builder datetime(String datetime) {
            this.datetime = datetime;
            return this;
        }


        /** Native browser tooltip (title attribute), e.g. the full ISO timestamp. */
        public Builder tooltip(String tooltip) {
            this.tooltip = tooltip;
            return this;
        }

        public Timestamp build() {
            return new Timestamp(this);
        }
    }
}
