package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Menu component ({@code pf-v6-c-menu}).
 *
 * <pre>
 * Menu menu = Menu.builder()
 *     .id("mn-basic").style("max-width: 220px")
 *     .item(MenuItem.of("Action 1"))
 *     .item(MenuItem.of("Action 2"))
 *     .item(MenuItem.of("Disabled action").asDisabled())
 *     .item(MenuItem.divider())
 *     .item(MenuItem.of("Delete").asDanger())
 *     .build();
 * </pre>
 *
 * Template side: <code>{#include components/navigation/menu menu=menu /}</code>
 *
 * <p>Flat items (with dividers) or titled {@link Builder#group}s. Interaction
 * modes generate their Alpine wiring at {@code build()}: single/multi select
 * ({@code menuitemradio}/{@code menuitemcheckbox} options with a shared
 * selection state), checkbox items, a live {@link Builder#searchFilter} that
 * hides non-matching items, {@link Builder#viewMore} lazy items, per-item
 * trailing {@link MenuItem#action} buttons, plus header/footer regions and a
 * trailing loading row. The heavier PF compositions (drilldown, flyout,
 * favorites) stay hand-written — see the menu demo page.
 */
@TemplateData
public final class Menu {

    /** A titled section of menu items. */
    @TemplateData
    public static final class Group {
        private final String title;
        private final List<MenuItem> items;

        private Group(String title, List<MenuItem> items) {
            this.title = title;
            this.items = items;
        }

        public String title() {
            return title;
        }

        public List<MenuItem> items() {
            return items;
        }
    }

    private final String id;
    private final String style;
    private final boolean plain;
    private final boolean scrollable;
    private final String modifiers;
    private final String contentStyle;
    private final String header;
    private final String footerButton;
    private final boolean footerDivider;
    private final String searchPlaceholder;
    private final String searchAriaLabel;
    private final String selectMode;
    private final boolean checkboxes;
    private final String viewMoreText;
    private final int viewMoreCount;
    private final String viewMoreLoadedPrefix;
    private final String loadingLabel;
    private final List<MenuItem> items;
    private final List<Group> groups;
    private final String wrapperXData;
    private final boolean listNoRole;

    private Menu(Builder b, String wrapperXData, boolean listNoRole) {
        this.id = b.id;
        this.style = b.style;
        this.plain = b.plain;
        this.scrollable = b.scrollable;
        this.modifiers = b.modifiers;
        this.contentStyle = b.contentStyle;
        this.header = b.header;
        this.footerButton = b.footerButton;
        this.footerDivider = b.footerDivider;
        this.searchPlaceholder = b.searchPlaceholder;
        this.searchAriaLabel = b.searchAriaLabel;
        this.selectMode = b.selectMode;
        this.checkboxes = b.checkboxes;
        this.viewMoreText = b.viewMoreText;
        this.viewMoreCount = b.viewMoreCount;
        this.viewMoreLoadedPrefix = b.viewMoreLoadedPrefix;
        this.loadingLabel = b.loadingLabel;
        this.items = List.copyOf(b.items);
        this.groups = List.copyOf(b.groups);
        this.wrapperXData = wrapperXData;
        this.listNoRole = listNoRole;
    }

    public static Builder builder() {
        return new Builder();
    }

    /** Group factory: {@code Menu.group("Group 1", item, item)} — null title for an untitled group. */
    public static Group group(String title, MenuItem... items) {
        return new Group(title, List.of(items));
    }

    public String id() {
        return id;
    }

    public String style() {
        return style;
    }

    public boolean isPlain() {
        return plain;
    }

    public boolean isScrollable() {
        return scrollable;
    }

    public String modifiers() {
        return modifiers;
    }

    public String contentStyle() {
        return contentStyle;
    }

    public String header() {
        return header;
    }

    public String footerButton() {
        return footerButton;
    }

    /** True when a divider separates the content from the footer region. */
    public boolean isFooterDivider() {
        return footerDivider;
    }

    public boolean isHasSearch() {
        return searchPlaceholder != null;
    }

    public String searchPlaceholder() {
        return searchPlaceholder;
    }

    public String searchAriaLabel() {
        return searchAriaLabel;
    }

    public boolean isSelectSingle() {
        return "single".equals(selectMode);
    }

    public boolean isSelectMulti() {
        return "multi".equals(selectMode);
    }

    public boolean isCheckboxes() {
        return checkboxes;
    }

    public boolean isHasViewMore() {
        return viewMoreText != null;
    }

    public String viewMoreText() {
        return viewMoreText;
    }

    public int viewMoreCount() {
        return viewMoreCount;
    }

    public String viewMoreLoadedPrefix() {
        return viewMoreLoadedPrefix;
    }

    public String loadingLabel() {
        return loadingLabel;
    }

    public boolean isGrouped() {
        return !groups.isEmpty();
    }

    public List<MenuItem> items() {
        return items;
    }

    public List<Group> groups() {
        return groups;
    }

    /** Generated Alpine state for the wrapper div, or null when none is needed. */
    public String wrapperXData() {
        return wrapperXData;
    }

    /** True when the list omits role=menu (checkbox / action lists). */
    public boolean isListNoRole() {
        return listNoRole;
    }

    public static final class Builder {

        private String id;
        private String style;
        private boolean plain;
        private boolean scrollable;
        private String modifiers;
        private String contentStyle;
        private String header;
        private String footerButton;
        private boolean footerDivider;
        private String searchPlaceholder;
        private String searchAriaLabel;
        private String selectMode;
        private boolean checkboxes;
        private String checkboxIdPrefix = "pha-menu-check";
        private String viewMoreText;
        private int viewMoreCount;
        private String viewMoreLoadedPrefix;
        private String loadingLabel;
        private final List<MenuItem> items = new ArrayList<>();
        private final List<Group> groups = new ArrayList<>();

        private Builder() {
        }

        /** DOM id on the menu root. */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** Inline style on the menu root, e.g. {@code "max-width: 220px"}. */
        public Builder style(String style) {
            this.style = style;
            return this;
        }

        public Builder plain() {
            this.plain = true;
            return this;
        }

        public Builder scrollable() {
            this.scrollable = true;
            return this;
        }

        /** Extra pf-m-* classes on the root, verbatim. */
        public Builder modifiers(String modifiers) {
            this.modifiers = modifiers;
            return this;
        }

        /** Inline style on the content wrapper, e.g. a custom {@code --pf-v6-c-menu__content--MaxHeight}. */
        public Builder contentStyle(String contentStyle) {
            this.contentStyle = contentStyle;
            return this;
        }

        /** Header region text above the content (with a divider). */
        public Builder header(String header) {
            this.header = header;
            return this;
        }

        /** Inline link button in the footer region. */
        public Builder footerButton(String footerButton) {
            this.footerButton = footerButton;
            return this;
        }

        /** Divider between the content and the footer region. */
        public Builder footerDivider() {
            this.footerDivider = true;
            return this;
        }

        /** Search region above the content that live-filters the items as you type. */
        public Builder searchFilter(String placeholder, String ariaLabel) {
            this.searchPlaceholder = Objects.requireNonNull(placeholder, "placeholder");
            this.searchAriaLabel = Objects.requireNonNull(ariaLabel, "ariaLabel");
            return this;
        }

        /** Single-select option menu ({@code menuitemradio} items with a check icon). */
        public Builder selectSingle() {
            this.selectMode = "single";
            return this;
        }

        /** Multi-select option menu ({@code menuitemcheckbox} items with check icons). */
        public Builder selectMulti() {
            this.selectMode = "multi";
            return this;
        }

        /** Checkbox items; generated input ids use the given prefix ({@code {prefix}-N-input}). */
        public Builder checkboxes(String idPrefix) {
            this.checkboxes = true;
            this.checkboxIdPrefix = Objects.requireNonNull(idPrefix, "idPrefix");
            return this;
        }

        /** Lazy "view more" row that loads {@code count} extra items on click. */
        public Builder viewMore(String text, int count, String loadedPrefix) {
            this.viewMoreText = Objects.requireNonNull(text, "text");
            this.viewMoreCount = count;
            this.viewMoreLoadedPrefix = Objects.requireNonNull(loadedPrefix, "loadedPrefix");
            return this;
        }

        /** Trailing visual-only loading row with a large spinner. */
        public Builder loading(String label) {
            this.loadingLabel = Objects.requireNonNull(label, "label");
            return this;
        }

        public Builder item(MenuItem item) {
            items.add(Objects.requireNonNull(item, "item"));
            return this;
        }

        public Builder items(MenuItem... items) {
            for (MenuItem item : items) {
                item(item);
            }
            return this;
        }

        public Builder group(Group group) {
            groups.add(Objects.requireNonNull(group, "group"));
            return this;
        }

        public Menu build() {
            if (items.isEmpty() && groups.isEmpty()) {
                throw new IllegalStateException("A menu needs at least one item or group");
            }
            if (!items.isEmpty() && !groups.isEmpty()) {
                throw new IllegalStateException("A menu is grouped (groups only) or flat (items only), not both");
            }
            if (selectMode != null && checkboxes) {
                throw new IllegalStateException("Select and checkbox modes are mutually exclusive");
            }
            if (selectMode != null) {
                // Auto-assign optN keys in item order where missing.
                int n = 0;
                for (int i = 0; i < items.size(); i++) {
                    MenuItem item = items.get(i);
                    if (item.isDivider()) {
                        continue;
                    }
                    n++;
                    if (item.key() == null) {
                        items.set(i, item.key("opt" + n));
                    }
                }
            }
            if (checkboxes) {
                int n = 0;
                for (MenuItem item : items) {
                    if (item.isDivider()) {
                        continue;
                    }
                    n++;
                    item.checkboxId = checkboxIdPrefix + "-" + n + "-input";
                    item.stateVar = "c" + n;
                }
            }
            String xData = computeWrapperXData();
            boolean noRole = checkboxes || items.stream().anyMatch(i -> i.actionIcon() != null);
            return new Menu(this, xData, noRole);
        }

        private String computeWrapperXData() {
            if (searchPlaceholder != null) {
                return "{ q: '' }";
            }
            if ("single".equals(selectMode)) {
                String initial = items.stream()
                        .filter(i -> !i.isDivider() && i.isSelected())
                        .map(MenuItem::key)
                        .findFirst()
                        .orElse(null);
                return "{ selected: " + (initial != null ? "'" + initial + "'" : "''") + " }";
            }
            if ("multi".equals(selectMode)) {
                List<String> initial = items.stream()
                        .filter(i -> !i.isDivider() && i.isSelected())
                        .map(i -> "'" + i.key() + "'")
                        .toList();
                return "{ sel: [" + String.join(", ", initial) + "] }";
            }
            if (checkboxes) {
                List<String> vars = new ArrayList<>();
                for (MenuItem item : items) {
                    if (!item.isDivider()) {
                        vars.add(item.stateVar() + ": " + item.isSelected());
                    }
                }
                return "{ " + String.join(", ", vars) + " }";
            }
            if (viewMoreText != null) {
                return "{ extra: 0 }";
            }
            return null;
        }
    }
}
