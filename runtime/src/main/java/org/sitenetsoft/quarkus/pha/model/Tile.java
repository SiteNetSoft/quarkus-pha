package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Tile component ({@code pf-v6-c-tile}).
 *
 * <p>Tile is deprecated upstream (prefer Card's selectable tiles) but kept for
 * parity. Selection groups (single/multiple) stay hand-written compositions —
 * selection is live page state.
 *
 * <pre>
 * Tile.of("Default").id("tile-basic").build()
 * Tile.of("With icon").icon("fa:plus").body("Icon beside the title.").build()
 * Tile.of("Large icon").icon("fa:cloud").stacked().displayLg().body("...").build()
 * </pre>
 */
@TemplateData
public final class Tile {

    private final String title;
    private final String id;
    private final String iconName;
    private final String bodyText;
    private final boolean selected;
    private final boolean disabled;
    private final boolean stacked;
    private final boolean displayLg;

    private Tile(Builder b) {
        this.title = b.title;
        this.id = b.id;
        this.iconName = b.iconName;
        this.bodyText = b.bodyText;
        this.selected = b.selected;
        this.disabled = b.disabled;
        this.stacked = b.stacked;
        this.displayLg = b.displayLg;
    }

    public static Builder of(String title) {
        Builder b = new Builder();
        b.title = Objects.requireNonNull(title, "title");
        return b;
    }

    public String title() {
        return title;
    }

    public String id() {
        return id;
    }

    public String iconName() {
        return iconName;
    }

    public String bodyText() {
        return bodyText;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public boolean isStacked() {
        return stacked;
    }

    public boolean isDisplayLg() {
        return displayLg;
    }

    public static final class Builder {
        private String title;
        private String id;
        private String iconName;
        private String bodyText;
        private boolean selected;
        private boolean disabled;
        private boolean stacked;
        private boolean displayLg;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** Icon as "set:slug", e.g. "fa:plus". */
        public Builder icon(String iconName) {
            this.iconName = iconName;
            return this;
        }

        /** Supporting text under the title. */
        public Builder body(String bodyText) {
            this.bodyText = bodyText;
            return this;
        }

        public Builder selected() {
            this.selected = true;
            return this;
        }

        public Builder disabled() {
            this.disabled = true;
            return this;
        }

        /** Icon above the title (pf-m-stacked on the header). */
        public Builder stacked() {
            this.stacked = true;
            return this;
        }

        /** Larger stacked icon (pf-m-display-lg). */
        public Builder displayLg() {
            this.displayLg = true;
            return this;
        }

        public Tile build() {
            return new Tile(this);
        }
    }
}
