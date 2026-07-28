package org.sitenetsoft.quarkus.pha.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Badge component ({@code pf-v6-c-badge}).
 *
 * <pre>
 * Badge.of("7").build()                       // unread by default
 * Badge.of("24").variant("read").build()
 * Badge.of("3").screenReaderText("unread notifications").build()
 * </pre>
 *
 * <p>JSON-constructible (the pha view-model contract): properties bind to the builder, e.g.
 * {@code {"value": "7", "variant": "read"}} — see {@code docs/json-models} for the shape rules.
 */
@TemplateData
@JsonDeserialize(builder = Badge.Builder.class)
public final class Badge {

    private final String id;
    private final String value;
    private final String variant;
    private final String screenReaderText;

    private Badge(Builder b) {
        this.id = b.id;
        this.value = b.value;
        this.variant = b.variant;
        this.screenReaderText = b.screenReaderText;
    }

    public static Builder of(String value) {
        Builder b = new Builder();
        b.value = Objects.requireNonNull(value, "value");
        return b;
    }

    public String id() {
        return id;
    }

    public String value() {
        return value;
    }

    /** read | unread (default) | disabled. */
    public String variant() {
        return variant != null ? variant : "unread";
    }

    public boolean isDisabledVariant() {
        return "disabled".equals(variant);
    }

    public String screenReaderText() {
        return screenReaderText;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private String id;
        private String value;
        private String variant;
        private String screenReaderText;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** The badge count/text — required; JSON binds it here, {@link #of} sets it in Java. */
        public Builder value(String value) {
            this.value = value;
            return this;
        }

        /** read | unread | disabled. */
        public Builder variant(String variant) {
            this.variant = variant;
            return this;
        }

        /** Visually-hidden text appended for AT consumers. */
        public Builder screenReaderText(String screenReaderText) {
            this.screenReaderText = screenReaderText;
            return this;
        }

        public Badge build() {
            Objects.requireNonNull(value, "value");
            return new Badge(this);
        }
    }
}
