package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Toolbar component ({@code pf-v6-c-toolbar}).
 *
 * <pre>
 * Toolbar.of("tb-basic")
 *     .section(Toolbar.Section.of(
 *         Toolbar.Entry.group(Toolbar.Group.of(
 *             Toolbar.Item.search("Search"),
 *             Toolbar.Item.button(Button.of("Filter").variant("secondary").build()))),
 *         Toolbar.Entry.group(Toolbar.Group.of(
 *             Toolbar.Item.button(Button.of("Create").variant("primary").build()))
 *             .alignEnd()))).build()
 * </pre>
 *
 * <p>Items hold composed {@link Button}/{@link MenuToggle} models, a generated
 * search input, or plain text; sections mix items, groups and vertical
 * dividers. Live filter/label-group state compositions stay on the slot shell.
 */
@TemplateData
public final class Toolbar {

    /** One toolbar item. */
    @TemplateData
    public static final class Item {
        private final Button button;
        private final MenuToggle menuToggle;
        private final String searchText;
        private final String text;
        private final String gap;
        private final String modifiers;
        private final String style;

        private Item(Button button, MenuToggle menuToggle, String searchText, String text,
                     String gap, String modifiers, String style) {
            this.button = button;
            this.menuToggle = menuToggle;
            this.searchText = searchText;
            this.text = text;
            this.gap = gap;
            this.modifiers = modifiers;
            this.style = style;
        }

        public static Item button(Button button) {
            return new Item(Objects.requireNonNull(button, "button"), null, null, null, null, null, null);
        }

        public static Item toggle(MenuToggle menuToggle) {
            return new Item(null, Objects.requireNonNull(menuToggle, "menuToggle"), null, null, null, null, null);
        }

        /** Search input — the text doubles as placeholder and aria-label. */
        public static Item search(String searchText) {
            return new Item(null, null, Objects.requireNonNull(searchText, "searchText"), null, null, null, null);
        }

        /** Plain text content. */
        public static Item text(String text) {
            return new Item(null, null, null, Objects.requireNonNull(text, "text"), null, null, null);
        }

        /** Gap token (none | xl | 4xl | …) → pf-m-gap-*. */
        public Item gap(String gap) {
            return new Item(button, menuToggle, searchText, text, gap, modifiers, style);
        }

        /** Verbatim extra classes (e.g. "pf-m-bulk-select"). */
        public Item modifiers(String modifiers) {
            return new Item(button, menuToggle, searchText, text, gap, modifiers, style);
        }

        public Item style(String style) {
            return new Item(button, menuToggle, searchText, text, gap, modifiers, style);
        }

        public boolean isButtonItem() {
            return button != null;
        }

        public Button buttonModel() {
            return button;
        }

        public boolean isToggleItem() {
            return menuToggle != null;
        }

        public MenuToggle toggleModel() {
            return menuToggle;
        }

        public boolean isSearchItem() {
            return searchText != null;
        }

        public String searchText() {
            return searchText;
        }

        public String textContent() {
            return text;
        }

        /** Item class suffix, leading-space separated. */
        public String classSuffix() {
            StringBuilder sb = new StringBuilder();
            if (gap != null) {
                sb.append(" pf-m-gap-").append(gap);
            }
            if (modifiers != null) {
                sb.append(' ').append(modifiers);
            }
            return sb.toString();
        }

        public String styleValue() {
            return style;
        }
    }

    /** One toolbar group of items. */
    @TemplateData
    public static final class Group {
        private final List<Item> items;
        private final String variant;
        private final boolean alignEnd;
        private final String columnGap;
        private final String modifiers;
        private final String style;

        private Group(List<Item> items, String variant, boolean alignEnd, String columnGap,
                      String modifiers, String style) {
            this.items = items;
            this.variant = variant;
            this.alignEnd = alignEnd;
            this.columnGap = columnGap;
            this.modifiers = modifiers;
            this.style = style;
        }

        public static Group of(Item... items) {
            return new Group(List.of(items), null, false, null, null, null);
        }

        /** filter | toggle → pf-m-*-group. */
        public Group variant(String variant) {
            return new Group(items, variant, alignEnd, columnGap, modifiers, style);
        }

        public Group alignEnd() {
            return new Group(items, variant, true, columnGap, modifiers, style);
        }

        /** Column-gap token → pf-m-column-gap-*. */
        public Group columnGap(String columnGap) {
            return new Group(items, variant, alignEnd, columnGap, modifiers, style);
        }

        /** Verbatim extra classes (e.g. "pf-m-action-group-inline"). */
        public Group modifiers(String modifiers) {
            return new Group(items, variant, alignEnd, columnGap, modifiers, style);
        }

        public Group style(String style) {
            return new Group(items, variant, alignEnd, columnGap, modifiers, style);
        }

        public List<Item> items() {
            return items;
        }

        /** Group class suffix, leading-space separated. */
        public String classSuffix() {
            StringBuilder sb = new StringBuilder();
            if (variant != null) {
                sb.append(" pf-m-").append(variant).append("-group");
            }
            if (alignEnd) {
                sb.append(" pf-m-align-end");
            }
            if (columnGap != null) {
                sb.append(" pf-m-column-gap-").append(columnGap);
            }
            if (modifiers != null) {
                sb.append(' ').append(modifiers);
            }
            return sb.toString();
        }

        public String styleValue() {
            return style;
        }
    }

    /** One section entry — item, group, or a vertical divider. */
    @TemplateData
    public static final class Entry {
        private final Item item;
        private final Group group;
        private final String divider;

        private Entry(Item item, Group group, String divider) {
            this.item = item;
            this.group = group;
            this.divider = divider;
        }

        public static Entry item(Item item) {
            return new Entry(Objects.requireNonNull(item, "item"), null, null);
        }

        public static Entry group(Group group) {
            return new Entry(null, Objects.requireNonNull(group, "group"), null);
        }

        /** Vertical divider as a div (role separator). */
        public static Entry dividerDiv() {
            return new Entry(null, null, "div");
        }

        /** Vertical divider as an hr. */
        public static Entry dividerHr() {
            return new Entry(null, null, "hr");
        }

        public boolean isItemEntry() {
            return item != null;
        }

        public Item itemValue() {
            return item;
        }

        public boolean isGroupEntry() {
            return group != null;
        }

        public Group groupValue() {
            return group;
        }

        public boolean isDividerDivEntry() {
            return "div".equals(divider);
        }

        public boolean isDividerHrEntry() {
            return "hr".equals(divider);
        }
    }

    /** One content section (row content). */
    @TemplateData
    public static final class Section {
        private final List<Entry> entries;
        private final String modifiers;

        private Section(List<Entry> entries, String modifiers) {
            this.entries = entries;
            this.modifiers = modifiers;
        }

        public static Section of(Entry... entries) {
            return new Section(List.of(entries), null);
        }

        /** Verbatim classes (e.g. "pf-m-wrap"). */
        public Section modifiers(String modifiers) {
            return new Section(entries, modifiers);
        }

        public List<Entry> entries() {
            return entries;
        }

        public String modifierSuffix() {
            return modifiers != null ? " " + modifiers : "";
        }
    }

    private final String id;
    private final String variant;
    private final boolean noBackground;
    private final boolean sticky;
    private final boolean vertical;
    private final String modifiers;
    private final List<Section> sections;

    private Toolbar(Builder b) {
        this.id = b.id;
        this.variant = b.variant;
        this.noBackground = b.noBackground;
        this.sticky = b.sticky;
        this.vertical = b.vertical;
        this.modifiers = b.modifiers;
        this.sections = List.copyOf(b.sections);
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    public String id() {
        return id;
    }

    /** Root class suffix, leading-space separated (shell order). */
    public String rootModifiers() {
        StringBuilder sb = new StringBuilder();
        if (variant != null) {
            sb.append(" pf-m-").append(variant);
        }
        if (noBackground) {
            sb.append(" pf-m-no-background");
        }
        if (sticky) {
            sb.append(" pf-m-sticky");
        }
        if (vertical) {
            sb.append(" pf-m-vertical");
        }
        if (modifiers != null) {
            sb.append(' ').append(modifiers);
        }
        return sb.toString();
    }

    public List<Section> sections() {
        return sections;
    }

    public static final class Builder {
        private String id;
        private String variant;
        private boolean noBackground;
        private boolean sticky;
        private boolean vertical;
        private String modifiers;
        private final List<Section> sections = new ArrayList<>();

        private Builder() {
        }

        /** primary | secondary. */
        public Builder variant(String variant) {
            this.variant = variant;
            return this;
        }

        public Builder noBackground() {
            this.noBackground = true;
            return this;
        }

        public Builder sticky() {
            this.sticky = true;
            return this;
        }

        public Builder vertical() {
            this.vertical = true;
            return this;
        }

        /** Verbatim root classes (insets, no-padding). */
        public Builder modifiers(String modifiers) {
            this.modifiers = modifiers;
            return this;
        }

        /** One content row's section. */
        public Builder section(Section section) {
            sections.add(Objects.requireNonNull(section, "section"));
            return this;
        }

        public Toolbar build() {
            if (sections.isEmpty()) {
                throw new IllegalStateException("A toolbar needs at least one section");
            }
            return new Toolbar(this);
        }
    }
}
