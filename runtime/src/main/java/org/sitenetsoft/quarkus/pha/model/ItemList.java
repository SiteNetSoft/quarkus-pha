package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the List component ({@code pf-v6-c-list}).
 *
 * <p>Named {@code ItemList} (not {@code List}) so it can share a star import
 * with {@code java.util.List} in consumer code.
 *
 * <pre>
 * ItemList.ul().item("First item").item("Second item").build()
 * ItemList.ol().type("A").item("First step").build()
 * ItemList.ul().plain().item("fa:circle-check", "Step one complete").build()
 * </pre>
 *
 * <p>Item content is raw HTML; the two-arg {@code item(icon, html)} renders a
 * {@code __item-icon} span before it.
 */
@TemplateData
public final class ItemList {

    /** One list row — optional icon plus raw-HTML content. */
    @TemplateData
    public static final class Item {
        private final String iconName;
        private final String html;

        private Item(String iconName, String html) {
            this.iconName = iconName;
            this.html = html;
        }

        public String iconName() {
            return iconName;
        }

        public String html() {
            return html;
        }
    }

    private final String element;
    private final String id;
    private final String type;
    private final boolean plain;
    private final boolean inline;
    private final boolean bordered;
    private final boolean iconLg;
    private final String ariaLabel;
    private final List<Item> items;

    private ItemList(Builder b) {
        this.element = b.element;
        this.id = b.id;
        this.type = b.type;
        this.plain = b.plain;
        this.inline = b.inline;
        this.bordered = b.bordered;
        this.iconLg = b.iconLg;
        this.ariaLabel = b.ariaLabel;
        this.items = List.copyOf(b.items);
    }

    public static Builder ul() {
        return new Builder("ul");
    }

    public static Builder ol() {
        return new Builder("ol");
    }

    public String element() {
        return element;
    }

    public String id() {
        return id;
    }

    /** ol numbering type attr (1 | A | a | I | i); null on ul. */
    public String typeAttr() {
        return "ol".equals(element) ? type : null;
    }

    /** Modifier-class suffix, leading-space separated. */
    public String modifiers() {
        StringBuilder sb = new StringBuilder();
        if (plain) {
            sb.append(" pf-m-plain");
        }
        if (inline) {
            sb.append(" pf-m-inline");
        }
        if (bordered) {
            sb.append(" pf-m-bordered");
        }
        if (iconLg) {
            sb.append(" pf-m-icon-lg");
        }
        return sb.toString();
    }

    public String ariaLabel() {
        return ariaLabel;
    }

    public List<Item> items() {
        return items;
    }

    public static final class Builder {
        private final String element;
        private String id;
        private String type;
        private boolean plain;
        private boolean inline;
        private boolean bordered;
        private boolean iconLg;
        private String ariaLabel;
        private final List<Item> items = new ArrayList<>();

        private Builder(String element) {
            this.element = element;
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** ol numbering: "1" (default) | "A" | "a" | "I" | "i". */
        public Builder type(String type) {
            this.type = type;
            return this;
        }

        /** No bullets, no indent (pf-m-plain). */
        public Builder plain() {
            this.plain = true;
            return this;
        }

        /** Horizontal row (pf-m-inline). */
        public Builder inline() {
            this.inline = true;
            return this;
        }

        /** Horizontal rules between rows (pf-m-bordered; pair with plain()). */
        public Builder bordered() {
            this.bordered = true;
            return this;
        }

        /** Larger __item-icon glyphs (pf-m-icon-lg). */
        public Builder iconLg() {
            this.iconLg = true;
            return this;
        }

        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        /** Row with raw-HTML content. */
        public Builder item(String html) {
            items.add(new Item(null, Objects.requireNonNull(html, "html")));
            return this;
        }

        /** Row with a leading __item-icon and raw-HTML content. */
        public Builder item(String iconName, String html) {
            items.add(new Item(Objects.requireNonNull(iconName, "iconName"),
                    Objects.requireNonNull(html, "html")));
            return this;
        }

        public ItemList build() {
            if (items.isEmpty()) {
                throw new IllegalStateException("A list needs at least one item");
            }
            return new ItemList(this);
        }
    }
}
