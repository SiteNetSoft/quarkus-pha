package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Toggle group component ({@code pf-v6-c-toggle-group}).
 *
 * <pre>
 * ToggleGroup.of("tg-compact", "Toggle group").compact()
 *     .item(Item.of("Hourly")).item(Item.of("Daily")).item(Item.of("Weekly")).build()
 * ToggleGroup.of("tg-single", "View mode").stateVar("selected").selectedKey("list")
 *     .item(Item.of("List").key("list")).item(Item.of("Grid").key("grid")).build()
 * ToggleGroup.of("tg-multi", "Filters").multiVar("filters")
 *     .item(Item.of("Bold").key("bold"))
 *     .item(Item.of("Italic").key("italic").pressed()).build()
 * </pre>
 *
 * <p>Selection Alpine is generated at build(): single-select keys default to
 * one/two/three… with the first item selected; {@code multiVar} switches to
 * independent boolean toggles in a state map.
 */
@TemplateData
public final class ToggleGroup {

    /** One toggle button — text and/or icon; key/pressed drive the generated state. */
    @TemplateData
    public static final class Item {
        private final String text;
        private final String icon;
        private final String ariaLabel;
        private final String key;
        private final boolean pressed;
        private final String pressedExpr;
        private final String classExpr;
        private final String clickExpr;

        private Item(String text, String icon, String ariaLabel, String key, boolean pressed,
                     String pressedExpr, String classExpr, String clickExpr) {
            this.text = text;
            this.icon = icon;
            this.ariaLabel = ariaLabel;
            this.key = key;
            this.pressed = pressed;
            this.pressedExpr = pressedExpr;
            this.classExpr = classExpr;
            this.clickExpr = clickExpr;
        }

        public static Item of(String text) {
            return new Item(Objects.requireNonNull(text, "text"), null, null, null, false, null, null, null);
        }

        /** Icon-only item with its accessible label. */
        public static Item icon(String icon, String ariaLabel) {
            return new Item(null, Objects.requireNonNull(icon, "icon"),
                    Objects.requireNonNull(ariaLabel, "ariaLabel"), null, false, null, null, null);
        }

        /** Icon before the text. */
        public Item withIcon(String icon) {
            return new Item(text, icon, ariaLabel, key, pressed, pressedExpr, classExpr, clickExpr);
        }

        /** Selection key (defaults to one/two/three… by position). */
        public Item key(String key) {
            return new Item(text, icon, ariaLabel, key, pressed, pressedExpr, classExpr, clickExpr);
        }

        /** Initially pressed (multi-select mode). */
        public Item pressed() {
            return new Item(text, icon, ariaLabel, key, true, pressedExpr, classExpr, clickExpr);
        }

        Item resolved(String key, String pressedExpr, String classExpr, String clickExpr) {
            return new Item(text, icon, ariaLabel, key, pressed, pressedExpr, classExpr, clickExpr);
        }

        public String text() {
            return text;
        }

        public String iconName() {
            return icon;
        }

        public String ariaLabel() {
            return ariaLabel;
        }

        public String pressedExpr() {
            return pressedExpr;
        }

        public String classExpr() {
            return classExpr;
        }

        public String clickExpr() {
            return clickExpr;
        }
    }

    private static final String[] ORDINALS = {"one", "two", "three", "four", "five", "six", "seven", "eight"};

    private final String id;
    private final String ariaLabel;
    private final boolean compact;
    private final boolean fill;
    private final String xData;
    private final List<Item> items;

    private ToggleGroup(Builder b) {
        this.id = b.id;
        this.ariaLabel = b.ariaLabel;
        this.compact = b.compact;
        this.fill = b.fill;

        List<Item> resolved = new ArrayList<>();
        StringBuilder multiState = new StringBuilder();
        for (int i = 0; i < b.items.size(); i++) {
            Item raw = b.items.get(i);
            String key = raw.key != null ? raw.key : ORDINALS[i];
            String v = b.stateVar;
            if (b.multi) {
                if (i > 0) {
                    multiState.append(", ");
                }
                multiState.append(key).append(": ").append(raw.pressed);
                resolved.add(raw.resolved(key,
                        v + "." + key,
                        v + "." + key + " ? 'pf-m-selected' : ''",
                        v + "." + key + " = !" + v + "." + key));
            } else {
                resolved.add(raw.resolved(key,
                        v + " === '" + key + "'",
                        v + " === '" + key + "' ? 'pf-m-selected' : ''",
                        v + " = '" + key + "'"));
            }
        }
        this.items = List.copyOf(resolved);
        if (b.multi) {
            this.xData = "{ " + b.stateVar + ": { " + multiState + " } }";
        } else {
            String selected = b.selectedKey != null ? b.selectedKey : this.items.get(0).key;
            this.xData = "{ " + b.stateVar + ": '" + selected + "' }";
        }
    }

    public static Builder of(String id, String ariaLabel) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        b.ariaLabel = Objects.requireNonNull(ariaLabel, "ariaLabel");
        return b;
    }

    public String id() {
        return id;
    }

    public String ariaLabel() {
        return ariaLabel;
    }

    /** Modifier-class suffix, leading-space separated. */
    public String modifiers() {
        StringBuilder sb = new StringBuilder();
        if (compact) {
            sb.append(" pf-m-compact");
        }
        if (fill) {
            sb.append(" pf-m-fill");
        }
        return sb.toString();
    }

    public String xData() {
        return xData;
    }

    public List<Item> items() {
        return items;
    }

    public static final class Builder {
        private String id;
        private String ariaLabel;
        private boolean compact;
        private boolean fill;
        private String stateVar = "sel";
        private String selectedKey;
        private boolean multi;
        private final List<Item> items = new ArrayList<>();

        private Builder() {
        }

        public Builder compact() {
            this.compact = true;
            return this;
        }

        /** Full-width items (pf-m-fill). */
        public Builder fill() {
            this.fill = true;
            return this;
        }

        /** Alpine state variable name (default "sel"). */
        public Builder stateVar(String stateVar) {
            this.stateVar = stateVar;
            return this;
        }

        /** Initially selected key (single mode; defaults to the first item). */
        public Builder selectedKey(String selectedKey) {
            this.selectedKey = selectedKey;
            return this;
        }

        /** Independent toggles in a boolean state map with this variable name. */
        public Builder multiVar(String stateVar) {
            this.multi = true;
            this.stateVar = stateVar;
            return this;
        }

        public Builder item(Item item) {
            items.add(Objects.requireNonNull(item, "item"));
            return this;
        }

        public ToggleGroup build() {
            if (items.isEmpty()) {
                throw new IllegalStateException("A toggle group needs at least one item");
            }
            return new ToggleGroup(this);
        }
    }
}
