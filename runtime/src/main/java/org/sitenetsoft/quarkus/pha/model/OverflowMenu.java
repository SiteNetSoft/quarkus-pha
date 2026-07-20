package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Overflow menu component ({@code pf-v6-c-overflow-menu}).
 *
 * <pre>
 * OverflowMenu.of("om-basic")
 *     .buttonGroup(Button.of("Refresh").variant("secondary").build(),
 *                  Button.of("Save").variant("primary").build()).build()
 * OverflowMenu.of("om-persistent")
 *     .buttonGroup(Button.of("Primary action").variant("primary").build())
 *     .persistentMenu("Edit", "Duplicate", "Archive").build()
 * OverflowMenu.of("om-bp").collapsible()
 *     .buttonGroup(...)  // content x-shows on !narrow; pair with chrome owning `narrow`
 *     .build()
 * </pre>
 *
 * <p>Groups hold composed {@link Button} models (text or plain-icon). The kebab
 * control is static by default; {@code persistentMenu(items…)} makes it a
 * working Alpine menu; {@code collapsible()} binds content/control visibility
 * to an outer {@code narrow} state (container-breakpoint pattern — the
 * ResizeObserver chrome stays in the page).
 */
@TemplateData
public final class OverflowMenu {

    /** One __group of composed buttons. */
    @TemplateData
    public static final class Group {
        private final boolean iconGroup;
        private final List<Button> buttons;

        private Group(boolean iconGroup, List<Button> buttons) {
            this.iconGroup = iconGroup;
            this.buttons = buttons;
        }

        public boolean isIconGroup() {
            return iconGroup;
        }

        public List<Button> buttons() {
            return buttons;
        }
    }

    private final String id;
    private final boolean vertical;
    private final boolean collapsible;
    private final String controlAriaLabel;
    private final List<Group> groups;
    private final List<String> menuItems;

    private OverflowMenu(Builder b) {
        this.id = b.id;
        this.vertical = b.vertical;
        this.collapsible = b.collapsible;
        this.controlAriaLabel = b.controlAriaLabel;
        this.groups = List.copyOf(b.groups);
        this.menuItems = b.menuItems;
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    public String id() {
        return id;
    }

    public boolean isVertical() {
        return vertical;
    }

    public boolean isCollapsible() {
        return collapsible;
    }

    public String controlAriaLabel() {
        return controlAriaLabel != null ? controlAriaLabel : "More actions";
    }

    public List<Group> groups() {
        return groups;
    }

    public boolean isHasMenu() {
        return menuItems != null;
    }

    public List<String> menuItems() {
        return menuItems;
    }

    public static final class Builder {
        private String id;
        private boolean vertical;
        private boolean collapsible;
        private String controlAriaLabel;
        private final List<Group> groups = new ArrayList<>();
        private List<String> menuItems;

        private Builder() {
        }

        /** pf-m-vertical — stacked buttons. */
        public Builder vertical() {
            this.vertical = true;
            return this;
        }

        /** Bind content/control visibility to an outer `narrow` Alpine state. */
        public Builder collapsible() {
            this.collapsible = true;
            return this;
        }

        public Builder controlAriaLabel(String controlAriaLabel) {
            this.controlAriaLabel = controlAriaLabel;
            return this;
        }

        /** Text-button group (pf-m-button-group). */
        public Builder buttonGroup(Button... buttons) {
            groups.add(new Group(false, List.of(buttons)));
            return this;
        }

        /** Plain icon-button group (pf-m-icon-button-group). */
        public Builder iconButtonGroup(Button... buttons) {
            groups.add(new Group(true, List.of(buttons)));
            return this;
        }

        /** Working kebab dropdown listing these action items. */
        public Builder persistentMenu(String... items) {
            this.menuItems = List.of(items);
            return this;
        }

        public OverflowMenu build() {
            if (groups.isEmpty()) {
                throw new IllegalStateException("An overflow menu needs at least one group");
            }
            return new OverflowMenu(this);
        }
    }
}
