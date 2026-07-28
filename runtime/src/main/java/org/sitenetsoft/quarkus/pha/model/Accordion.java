package org.sitenetsoft.quarkus.pha.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Accordion component ({@code pf-v6-c-accordion}).
 *
 * <pre>
 * Accordion.of("acc-bordered").bordered()
 *     .item(AccordionItem.of("acc-bordered-item-1", "Item one").body("Content one."))
 *     .item(AccordionItem.of("acc-bordered-item-2", "Item two").body("Content two."))
 *     .build()
 * </pre>
 *
 * <p>Items expand independently by default (each owns its Alpine state);
 * {@link Builder#singleExpand()} generates a shared {@code open} state so
 * only one item is open at a time (give each item a key).
 * {@link Builder#definitionList()} renders the dl/dt/dd shape.
 */
@TemplateData
@JsonDeserialize(builder = Accordion.Builder.class)
public final class Accordion {

    private final String id;
    private final String ariaLabel;
    private final boolean bordered;
    private final boolean displayLg;
    private final boolean toggleStart;
    private final boolean definitionList;
    private final boolean singleExpand;
    private final List<AccordionItem> items;

    private Accordion(Builder b) {
        this.id = b.id;
        this.ariaLabel = b.ariaLabel;
        this.bordered = b.bordered;
        this.displayLg = b.displayLg;
        this.toggleStart = b.toggleStart;
        this.definitionList = b.definitionList;
        this.singleExpand = b.singleExpand;
        this.items = List.copyOf(b.items);
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    public String id() {
        return id;
    }

    public String ariaLabel() {
        return ariaLabel;
    }

    public boolean isBordered() {
        return bordered;
    }

    public boolean isDisplayLg() {
        return displayLg;
    }

    public boolean isToggleStart() {
        return toggleStart;
    }

    public boolean isDefinitionList() {
        return definitionList;
    }

    public boolean isSingleExpand() {
        return singleExpand;
    }

    public List<AccordionItem> items() {
        return items;
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static final class Builder {

        private String id;
        private String ariaLabel;
        private boolean bordered;
        private boolean displayLg;
        private boolean toggleStart;
        private boolean definitionList;
        private boolean singleExpand;
        private final List<AccordionItem> items = new ArrayList<>();

        private Builder() {
        }

        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        /** Items separated by borders; no rounded root corners. */
        public Builder bordered() {
            this.bordered = true;
            return this;
        }

        public Builder bordered(boolean bordered) {
            this.bordered = bordered;
            return this;
        }

        /** Large display size. */
        public Builder displayLg() {
            this.displayLg = true;
            return this;
        }

        public Builder displayLg(boolean displayLg) {
            this.displayLg = displayLg;
            return this;
        }

        /** Toggle icon before the text. */
        public Builder toggleStart() {
            this.toggleStart = true;
            return this;
        }

        public Builder toggleStart(boolean toggleStart) {
            this.toggleStart = toggleStart;
            return this;
        }

        /** Render as dl/dt/dd. */
        public Builder definitionList() {
            this.definitionList = true;
            return this;
        }

        public Builder definitionList(boolean definitionList) {
            this.definitionList = definitionList;
            return this;
        }

        /** Only one item open at a time (shared generated state; items need keys). */
        public Builder singleExpand() {
            this.singleExpand = true;
            return this;
        }

        public Builder singleExpand(boolean singleExpand) {
            this.singleExpand = singleExpand;
            return this;
        }

        public Builder item(AccordionItem item) {
            items.add(Objects.requireNonNull(item, "item"));
            return this;
        }

        public Builder items(AccordionItem... items) {
            for (AccordionItem item : items) {
                item(item);
            }
            return this;
        }


        /* JSON contract: field-named setters (generated for the view-model contract). */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Accordion build() {
            if (items.isEmpty()) {
                throw new IllegalStateException("An accordion needs at least one item");
            }
            if (singleExpand) {
                for (AccordionItem item : items) {
                    if (item.key() == null) {
                        throw new IllegalStateException("singleExpand() needs a key() on every item");
                    }
                }
            }
            return new Accordion(this);
        }
    }
}
