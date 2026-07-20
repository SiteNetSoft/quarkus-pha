package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Action list component ({@code pf-v6-c-action-list}).
 *
 * <pre>
 * ActionList.builder()
 *     .group(ActionList.group(
 *         ActionList.item(Button.of("Next").build()),
 *         ActionList.item(Button.of("Back").variant("secondary").build())))
 *     .build()
 * </pre>
 *
 * <p>Groups hold items that are {@link Button}s or {@link MenuToggle}s
 * (kebab menus). {@code pf-m-icons} applies to the whole list via
 * {@link Builder#iconList()} or per group via
 * {@link #iconGroup(Item...)}.
 */
@TemplateData
public final class ActionList {

    /** One action — a button or a menu toggle. */
    @TemplateData
    public static final class Item {
        private final Button button;
        private final MenuToggle toggle;

        private Item(Button button, MenuToggle toggle) {
            this.button = button;
            this.toggle = toggle;
        }

        public Button button() {
            return button;
        }

        public MenuToggle toggleModel() {
            return toggle;
        }
    }

    /** A group of actions, optionally with the tight icon spacing. */
    @TemplateData
    public static final class Group {
        private final boolean icons;
        private final List<Item> groupItems;

        private Group(boolean icons, List<Item> groupItems) {
            this.icons = icons;
            this.groupItems = groupItems;
        }

        public boolean isIcons() {
            return icons;
        }

        public List<Item> groupItems() {
            return groupItems;
        }
    }

    public static Item item(Button button) {
        return new Item(Objects.requireNonNull(button, "button"), null);
    }

    public static Item item(MenuToggle toggle) {
        return new Item(null, Objects.requireNonNull(toggle, "toggle"));
    }

    public static Group group(Item... items) {
        return new Group(false, List.of(items));
    }

    /** Group with {@code pf-m-icons} (tight icon-only spacing). */
    public static Group iconGroup(Item... items) {
        return new Group(true, List.of(items));
    }

    private final String id;
    private final boolean iconList;
    private final boolean vertical;
    private final List<Group> groups;

    private ActionList(Builder b) {
        this.id = b.id;
        this.iconList = b.iconList;
        this.vertical = b.vertical;
        this.groups = List.copyOf(b.groups);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String id() {
        return id;
    }

    public boolean isIconList() {
        return iconList;
    }

    public boolean isVertical() {
        return vertical;
    }

    public List<Group> groups() {
        return groups;
    }

    public static final class Builder {

        private String id;
        private boolean iconList;
        private boolean vertical;
        private final List<Group> groups = new ArrayList<>();

        private Builder() {
        }

        /** DOM id on the root. */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** pf-m-icons on the whole list. */
        public Builder iconList() {
            this.iconList = true;
            return this;
        }

        /** Stack actions vertically (PHA divergence). */
        public Builder vertical() {
            this.vertical = true;
            return this;
        }

        public Builder group(Group group) {
            groups.add(Objects.requireNonNull(group, "group"));
            return this;
        }

        public ActionList build() {
            if (groups.isEmpty()) {
                throw new IllegalStateException("An action list needs at least one group");
            }
            return new ActionList(this);
        }
    }
}
