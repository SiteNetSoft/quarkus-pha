package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Label group component ({@code pf-v6-c-label-group}).
 *
 * <pre>
 * LabelGroup group = LabelGroup.builder()
 *     .ariaLabel("Basic label group")
 *     .label(Label.of("Label one").color("blue"))
 *     .label(Label.of("Label two").color("green"))
 *     .build();
 * </pre>
 *
 * Template side: <code>{#include components/data-display/label-group group=group /}</code>
 *
 * <p>The group wraps each {@link Label} in its list chrome and always carries
 * the {@code phaLabelGroup} overflow toggle: labels past
 * {@link Builder#numLabels(int)} (default 3) collapse behind a
 * "N more" toggle, optionally starting expanded.
 */
@TemplateData
public final class LabelGroup {

    private final String id;
    private final String ariaLabel;
    private final String categoryName;
    private final boolean vertical;
    private final boolean closable;
    private final String closeBtnAria;
    private final Integer numLabels;
    private final boolean startExpanded;
    private final List<Label> labels;

    private LabelGroup(Builder b) {
        this.id = b.id;
        this.ariaLabel = b.ariaLabel;
        this.categoryName = b.categoryName;
        this.vertical = b.vertical;
        this.closable = b.closable;
        this.closeBtnAria = b.closeBtnAria;
        this.numLabels = b.numLabels;
        this.startExpanded = b.startExpanded;
        this.labels = List.copyOf(b.labels);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String id() {
        return id;
    }

    public String ariaLabel() {
        return ariaLabel;
    }

    public String categoryName() {
        return categoryName;
    }

    public boolean isVertical() {
        return vertical;
    }

    public boolean isClosable() {
        return closable;
    }

    public String closeBtnAria() {
        return closeBtnAria;
    }

    public Integer numLabels() {
        return numLabels;
    }

    public boolean isStartExpanded() {
        return startExpanded;
    }

    public List<Label> labels() {
        return labels;
    }

    public static final class Builder {

        private String id;
        private String ariaLabel;
        private String categoryName;
        private boolean vertical;
        private boolean closable;
        private String closeBtnAria;
        private Integer numLabels;
        private boolean startExpanded;
        private final List<Label> labels = new ArrayList<>();

        private Builder() {
        }

        /** DOM id on the group container. */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** aria-label on the group when no category name is set. */
        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        /** Category text before the labels; implies the category treatment and names the group. */
        public Builder categoryName(String categoryName) {
            this.categoryName = categoryName;
            return this;
        }

        /** Stack the labels vertically. */
        public Builder vertical() {
            this.vertical = true;
            return this;
        }

        /** Close button on the group itself, with its aria-label. */
        public Builder closable(String closeBtnAria) {
            this.closable = true;
            this.closeBtnAria = Objects.requireNonNull(closeBtnAria, "closeBtnAria");
            return this;
        }

        /** Overflow threshold — labels past this collapse behind the "N more" toggle (default 3). */
        public Builder numLabels(int numLabels) {
            this.numLabels = numLabels;
            return this;
        }

        /** Render the overflow group already expanded (the toggle shows "Show Less"). */
        public Builder startExpanded() {
            this.startExpanded = true;
            return this;
        }

        public Builder label(Label label) {
            labels.add(Objects.requireNonNull(label, "label"));
            return this;
        }

        public Builder labels(Label... labels) {
            for (Label label : labels) {
                label(label);
            }
            return this;
        }

        public LabelGroup build() {
            if (labels.isEmpty()) {
                throw new IllegalStateException("A label group needs at least one label");
            }
            return new LabelGroup(this);
        }
    }
}
