package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Icon component ({@code pf-v6-c-icon}).
 *
 * <pre>
 * Icon.of("fa:star").build()
 * Icon.of("fa:circle-info").size("xl").status("info").label("Info").build()
 * Icon.of("fa:circle-info").size("3xl").iconSize("sm").label("small glyph").build()
 * Icon.of("fa:save").size("xl").inProgress().label("Saving").build()
 * Icon.of("fa:download").size("xl").inProgress().progressIcon("fa:hourglass-half").build()
 * </pre>
 *
 * <p>Without a label the SVG is aria-hidden (decorative); with one it gets
 * role="img" + aria-label. {@code inProgress()} adds the {@code __progress}
 * overlay (default PF spinner, or {@code progressIcon}).
 */
@TemplateData
public final class Icon {

    private final String name;
    private final String size;
    private final String iconSize;
    private final String status;
    private final boolean inline;
    private final boolean inProgress;
    private final String progressIcon;
    private final String label;

    private Icon(Builder b) {
        this.name = b.name;
        this.size = b.size;
        this.iconSize = b.iconSize;
        this.status = b.status;
        this.inline = b.inline;
        this.inProgress = b.inProgress;
        this.progressIcon = b.progressIcon;
        this.label = b.label;
    }

    public static Builder of(String name) {
        Builder b = new Builder();
        b.name = Objects.requireNonNull(name, "name");
        return b;
    }

    public String name() {
        return name;
    }

    public String size() {
        return size;
    }

    public String iconSize() {
        return iconSize;
    }

    public String status() {
        return status;
    }

    public boolean isInline() {
        return inline;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public String progressIcon() {
        return progressIcon;
    }

    public String label() {
        return label;
    }

    public static final class Builder {
        private String name;
        private String size;
        private String iconSize;
        private String status;
        private boolean inline;
        private boolean inProgress;
        private String progressIcon;
        private String label;

        private Builder() {
        }

        /** Container size: sm..3xl | body-sm/default/lg | heading-sm..heading-3xl. */
        public Builder size(String size) {
            this.size = size;
            return this;
        }

        /** Glyph size, independent from the container (same scale as size). */
        public Builder iconSize(String iconSize) {
            this.iconSize = iconSize;
            return this;
        }

        /** info | success | warning | danger | custom. */
        public Builder status(String status) {
            this.status = status;
            return this;
        }

        /** Flow with surrounding text (pf-m-inline). */
        public Builder inline() {
            this.inline = true;
            return this;
        }

        /** Show the __progress overlay (PF spinner unless progressIcon is set). */
        public Builder inProgress() {
            this.inProgress = true;
            return this;
        }

        public Builder progressIcon(String progressIcon) {
            this.progressIcon = progressIcon;
            return this;
        }

        /** Accessible label; omit for decorative icons (aria-hidden). */
        public Builder label(String label) {
            this.label = label;
            return this;
        }

        public Icon build() {
            return new Icon(this);
        }
    }
}
