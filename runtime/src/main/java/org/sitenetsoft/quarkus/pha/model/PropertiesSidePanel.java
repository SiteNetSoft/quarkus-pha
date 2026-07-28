package org.sitenetsoft.quarkus.pha.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Properties side panel ({@code properties-side-panel-pf},
 * PatternFly catalog-view extension) — label/value rows for a detail page rail.
 *
 * <pre>
 * PropertiesSidePanel.of("psp-demo")
 *     .property("Version", "1.0.0-SNAPSHOT")
 *     .property("License", "Apache 2.0")
 *     .build();
 * </pre>
 */
@TemplateData
@JsonDeserialize(builder = PropertiesSidePanel.Builder.class)
public final class PropertiesSidePanel {

    private final String id;
    private final List<Property> properties;

    private PropertiesSidePanel(Builder b) {
        this.id = b.id;
        this.properties = List.copyOf(b.properties);
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = id;
        return b;
    }

    public String id() {
        return id;
    }

    public List<Property> properties() {
        return properties;
    }

    /** One label/value row. */
    @TemplateData
    public record Property(String label, String value) {
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {
        private String id;
        private final List<Property> properties = new ArrayList<>();

        private Builder() {
        }

        public Builder property(String label, String value) {
            this.properties.add(new Property(Objects.requireNonNull(label, "label"),
                    Objects.requireNonNull(value, "value")));
            return this;
        }


        /* JSON contract: field-named setters (generated for the view-model contract). */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder properties(java.util.List<Property> properties) {
            this.properties.addAll(properties);
            return this;
        }

        public PropertiesSidePanel build() {
            return new PropertiesSidePanel(this);
        }
    }
}
