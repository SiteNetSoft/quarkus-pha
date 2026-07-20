package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Empty state component ({@code pf-v6-c-empty-state}).
 *
 * <pre>
 * EmptyState.of("Empty state").icon("fa:cubes")
 *     .body("This represents the empty state pattern in PatternFly.")
 *     .actionGroup(Button.of("Primary action").build())
 *     .actionGroup(Button.of("Multiple").variant("link").build(),
 *                  Button.of("Action buttons").variant("link").build())
 *     .build()
 * EmptyState.of("Loading").spinner("xl", "Loading").build()
 * </pre>
 *
 * <p>Footer actions are composed {@link Button} models in ordered groups
 * (PF's EmptyStateActions — commonly a primary group then a link row).
 * {@code spinner(size, label)} puts a PF spinner in the icon area.
 */
@TemplateData
public final class EmptyState {

    private final String titleText;
    private final String id;
    private final String iconName;
    private final String spinnerSize;
    private final String spinnerLabel;
    private final String bodyText;
    private final String size;
    private final String status;
    private final List<List<Button>> actionGroups;

    private EmptyState(Builder b) {
        this.titleText = b.titleText;
        this.id = b.id;
        this.iconName = b.iconName;
        this.spinnerSize = b.spinnerSize;
        this.spinnerLabel = b.spinnerLabel;
        this.bodyText = b.bodyText;
        this.size = b.size;
        this.status = b.status;
        this.actionGroups = List.copyOf(b.actionGroups);
    }

    public static Builder of(String titleText) {
        Builder b = new Builder();
        b.titleText = Objects.requireNonNull(titleText, "titleText");
        return b;
    }

    public String titleText() {
        return titleText;
    }

    public String id() {
        return id;
    }

    public String iconName() {
        return iconName;
    }

    public boolean isSpinnerShape() {
        return spinnerSize != null;
    }

    public String spinnerSize() {
        return spinnerSize;
    }

    public String spinnerLabel() {
        return spinnerLabel;
    }

    public String bodyText() {
        return bodyText;
    }

    /** Modifier-class suffix, leading-space separated (size, status). */
    public String modifiers() {
        StringBuilder sb = new StringBuilder();
        if (size != null) {
            sb.append(" pf-m-").append(size);
        }
        if (status != null) {
            sb.append(" pf-m-").append(status);
        }
        return sb.toString();
    }

    public boolean isHasActions() {
        return !actionGroups.isEmpty();
    }

    public List<List<Button>> actionGroups() {
        return actionGroups;
    }

    public static final class Builder {
        private String titleText;
        private String id;
        private String iconName;
        private String spinnerSize;
        private String spinnerLabel;
        private String bodyText;
        private String size;
        private String status;
        private final List<List<Button>> actionGroups = new ArrayList<>();

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** Illustrative icon as "set:slug"; omit for an icon-less state. */
        public Builder icon(String iconName) {
            this.iconName = iconName;
            return this;
        }

        /** PF spinner in the icon area (loading states); overrides icon. */
        public Builder spinner(String size, String label) {
            this.spinnerSize = Objects.requireNonNull(size, "size");
            this.spinnerLabel = Objects.requireNonNull(label, "label");
            return this;
        }

        public Builder body(String bodyText) {
            this.bodyText = bodyText;
            return this;
        }

        /** xs | sm | lg | xl. */
        public Builder size(String size) {
            this.size = size;
            return this;
        }

        /** danger | warning | success | info — colors the icon. */
        public Builder status(String status) {
            this.status = status;
            return this;
        }

        /** One footer __actions group; call repeatedly for multiple groups in order. */
        public Builder actionGroup(Button... buttons) {
            actionGroups.add(List.of(buttons));
            return this;
        }

        public EmptyState build() {
            return new EmptyState(this);
        }
    }
}
