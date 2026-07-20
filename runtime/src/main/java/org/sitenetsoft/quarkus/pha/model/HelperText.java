package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Helper text component ({@code pf-v6-c-helper-text}).
 *
 * <pre>
 * HelperText.of("ht-1", "This is default helper text").build()
 * HelperText.of("ht-2", "This is error helper text").variant("error").build()
 * HelperText.of("ht-3", "Pro tip.").variant("success").icon("fa:lightbulb").build()
 * HelperText.list("ht-pwd")
 *     .item(Item.of("At least 8 characters").variant("success"))
 *     .item(Item.of("Contains a number").variant("warning")).build()
 * </pre>
 *
 * <p>Two shapes: a single-item div (of), or a ul list (list) of items. Default
 * per-variant icons resolve in Java (success→circle-check, warning→triangle-
 * exclamation, error→circle-exclamation, indeterminate→minus).
 */
@TemplateData
public final class HelperText {

    /** One helper-text row. */
    @TemplateData
    public static final class Item {
        private final String text;
        private final String variant;
        private final String icon;
        private final boolean dynamic;
        private final String screenReaderText;

        private Item(String text, String variant, String icon, boolean dynamic, String screenReaderText) {
            this.text = text;
            this.variant = variant;
            this.icon = icon;
            this.dynamic = dynamic;
            this.screenReaderText = screenReaderText;
        }

        public static Item of(String text) {
            return new Item(Objects.requireNonNull(text, "text"), null, null, false, null);
        }

        /** indeterminate | warning | success | error. */
        public Item variant(String variant) {
            return new Item(text, variant, icon, dynamic, screenReaderText);
        }

        /** Override the default per-variant icon. */
        public Item icon(String icon) {
            return new Item(text, variant, icon, dynamic, screenReaderText);
        }

        /** Transition animations (pf-m-dynamic). */
        public Item dynamic() {
            return new Item(text, variant, icon, true, screenReaderText);
        }

        /** Screen-reader-only suffix announcing the variant. */
        public Item screenReaderText(String screenReaderText) {
            return new Item(text, variant, icon, dynamic, screenReaderText);
        }

        public String text() {
            return text;
        }

        public String variant() {
            return variant;
        }

        public boolean isDynamic() {
            return dynamic;
        }

        public String screenReaderText() {
            return screenReaderText;
        }

        /** Explicit icon, or the variant default; null when neither applies. */
        public String displayIcon() {
            if (icon != null) {
                return icon;
            }
            if (variant == null) {
                return null;
            }
            return switch (variant) {
                case "success" -> "fa:circle-check";
                case "warning" -> "fa:triangle-exclamation";
                case "error" -> "fa:circle-exclamation";
                case "indeterminate" -> "fa:minus";
                default -> null;
            };
        }
    }

    private final String id;
    private final boolean list;
    private final String ariaLabel;
    private final List<Item> items;

    private HelperText(Builder b) {
        this.id = b.id;
        this.list = b.list;
        this.ariaLabel = b.ariaLabel;
        this.items = List.copyOf(b.items);
    }

    /** Single-item shape (a div container with one auto-wrapped item). */
    public static Builder of(String id, String text) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        b.single = Item.of(text);
        return b;
    }

    /** Multi-item list shape (ul + li rows). */
    public static Builder list(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        b.list = true;
        return b;
    }

    public String id() {
        return id;
    }

    public boolean isListShape() {
        return list;
    }

    public String ariaLabel() {
        return ariaLabel;
    }

    public List<Item> items() {
        return items;
    }

    public static final class Builder {
        private String id;
        private boolean list;
        private String ariaLabel;
        private Item single;
        private final List<Item> items = new ArrayList<>();

        private Builder() {
        }

        /** Single shape: indeterminate | warning | success | error. */
        public Builder variant(String variant) {
            this.single = single.variant(variant);
            return this;
        }

        /** Single shape: override the default per-variant icon. */
        public Builder icon(String icon) {
            this.single = single.icon(icon);
            return this;
        }

        /** Single shape: transition animations (pf-m-dynamic). */
        public Builder dynamic() {
            this.single = single.dynamic();
            return this;
        }

        /** List shape: accessible label on the ul. */
        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        /** List shape: add a row. */
        public Builder item(Item item) {
            items.add(Objects.requireNonNull(item, "item"));
            return this;
        }

        public HelperText build() {
            if (list) {
                if (items.isEmpty()) {
                    throw new IllegalStateException("A helper-text list needs at least one item");
                }
            } else {
                items.clear();
                items.add(single);
            }
            return new HelperText(this);
        }
    }
}
