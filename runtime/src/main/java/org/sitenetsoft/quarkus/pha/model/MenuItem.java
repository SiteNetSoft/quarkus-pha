package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * One entry of a {@link Menu} — an action button, link, checkbox/select
 * option, or divider.
 *
 * <pre>
 * MenuItem.of("Edit").description("Change the name or labels.")
 * MenuItem.of("Link 1").href("#").asExternal()
 * MenuItem.divider()
 * </pre>
 *
 * <p>In a select menu ({@link Menu.Builder#selectSingle()} /
 * {@link Menu.Builder#selectMulti()}) mark the initially selected option(s)
 * with {@link #asSelected()}; in a checkbox menu the same flag pre-checks the
 * box. {@link #action(String, String)} adds a trailing icon button;
 * {@link #asFavoriteAction()} makes it a live favorite star.
 */
@TemplateData
public final class MenuItem {

    private final String text;
    private final boolean divider;
    private final String icon;
    private final String href;
    private final boolean external;
    private final boolean disabled;
    private final boolean danger;
    private final String srPrefix;
    private final String description;
    private final String itemId;
    private final String key;
    private final boolean selected;
    private final String actionIcon;
    private final String actionAriaLabel;
    private final boolean favoriteAction;

    /* Assigned by Menu.build() for checkbox menus. */
    String checkboxId;
    String stateVar;

    private MenuItem(String text, boolean divider, String icon, String href, boolean external,
                     boolean disabled, boolean danger, String srPrefix, String description,
                     String itemId, String key, boolean selected, String actionIcon,
                     String actionAriaLabel, boolean favoriteAction) {
        this.text = text;
        this.divider = divider;
        this.icon = icon;
        this.href = href;
        this.external = external;
        this.disabled = disabled;
        this.danger = danger;
        this.srPrefix = srPrefix;
        this.description = description;
        this.itemId = itemId;
        this.key = key;
        this.selected = selected;
        this.actionIcon = actionIcon;
        this.actionAriaLabel = actionAriaLabel;
        this.favoriteAction = favoriteAction;
    }

    public static MenuItem of(String text) {
        return new MenuItem(Objects.requireNonNull(text, "text"), false, null, null, false,
                false, false, null, null, null, null, false, null, null, false);
    }

    /** A separator between items ({@code li.pf-v6-c-divider}). */
    public static MenuItem divider() {
        return new MenuItem(null, true, null, null, false, false, false, null, null,
                null, null, false, null, null, false);
    }

    private MenuItem copy(String icon, String href, boolean external, boolean disabled,
                          boolean danger, String srPrefix, String description, String itemId,
                          String key, boolean selected, String actionIcon, String actionAriaLabel,
                          boolean favoriteAction) {
        return new MenuItem(text, divider, icon, href, external, disabled, danger, srPrefix,
                description, itemId, key, selected, actionIcon, actionAriaLabel, favoriteAction);
    }

    /** Leading icon, e.g. {@code "fa:code"}. */
    public MenuItem icon(String icon) {
        return copy(icon, href, external, disabled, danger, srPrefix, description, itemId,
                key, selected, actionIcon, actionAriaLabel, favoriteAction);
    }

    /** Renders the item as a link. Combine with {@link #asDisabled()} for a disabled link. */
    public MenuItem href(String href) {
        return copy(icon, href, external, disabled, danger, srPrefix, description, itemId,
                key, selected, actionIcon, actionAriaLabel, favoriteAction);
    }

    /** External link — adds the external icon and target=_blank. */
    public MenuItem asExternal() {
        return copy(icon, href, true, disabled, danger, srPrefix, description, itemId,
                key, selected, actionIcon, actionAriaLabel, favoriteAction);
    }

    public MenuItem asDisabled() {
        return copy(icon, href, external, true, danger, srPrefix, description, itemId,
                key, selected, actionIcon, actionAriaLabel, favoriteAction);
    }

    /** Destructive-action treatment ({@code pf-m-danger} on the list item). */
    public MenuItem asDanger() {
        return copy(icon, href, external, disabled, true, srPrefix, description, itemId,
                key, selected, actionIcon, actionAriaLabel, favoriteAction);
    }

    /** Screen-reader-only prefix before the text, e.g. {@code "Danger item:"}. */
    public MenuItem srPrefix(String srPrefix) {
        return copy(icon, href, external, disabled, danger, srPrefix, description, itemId,
                key, selected, actionIcon, actionAriaLabel, favoriteAction);
    }

    /** Secondary line under the text. */
    public MenuItem description(String description) {
        return copy(icon, href, external, disabled, danger, srPrefix, description, itemId,
                key, selected, actionIcon, actionAriaLabel, favoriteAction);
    }

    /** id on the {@code __item} element. */
    public MenuItem itemId(String itemId) {
        return copy(icon, href, external, disabled, danger, srPrefix, description, itemId,
                key, selected, actionIcon, actionAriaLabel, favoriteAction);
    }

    /** Selection key in select menus (defaults to {@code optN} in item order). */
    public MenuItem key(String key) {
        return copy(icon, href, external, disabled, danger, srPrefix, description, itemId,
                key, selected, actionIcon, actionAriaLabel, favoriteAction);
    }

    /** Initially selected (select menus) or checked (checkbox menus). */
    public MenuItem asSelected() {
        return copy(icon, href, external, disabled, danger, srPrefix, description, itemId,
                key, true, actionIcon, actionAriaLabel, favoriteAction);
    }

    /** Trailing icon action button, e.g. {@code action("fa:clipboard", "Copy")}. */
    public MenuItem action(String actionIcon, String actionAriaLabel) {
        return copy(icon, href, external, disabled, danger, srPrefix, description, itemId,
                key, selected, Objects.requireNonNull(actionIcon, "actionIcon"),
                Objects.requireNonNull(actionAriaLabel, "actionAriaLabel"), favoriteAction);
    }

    /** Trailing favorite star action with live toggled state. */
    public MenuItem asFavoriteAction() {
        return copy(icon, href, external, disabled, danger, srPrefix, description, itemId,
                key, selected, "fa:star", "Favorite", true);
    }

    public String text() {
        return text;
    }

    public boolean isDivider() {
        return divider;
    }

    public String icon() {
        return icon;
    }

    public String href() {
        return href;
    }

    public boolean isExternal() {
        return external;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public boolean isDanger() {
        return danger;
    }

    public String srPrefix() {
        return srPrefix;
    }

    public String description() {
        return description;
    }

    public String itemId() {
        return itemId;
    }

    public String key() {
        return key;
    }

    public boolean isSelected() {
        return selected;
    }

    public String actionIcon() {
        return actionIcon;
    }

    public String actionAriaLabel() {
        return actionAriaLabel;
    }

    public boolean isFavoriteAction() {
        return favoriteAction;
    }

    public String checkboxId() {
        return checkboxId;
    }

    /** Wrapper-state variable name (checkbox menus), e.g. {@code c2}. */
    public String stateVar() {
        return stateVar;
    }
}
