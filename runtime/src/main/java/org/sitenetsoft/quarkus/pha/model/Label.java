package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Label component ({@code pf-v6-c-label}).
 *
 * <pre>
 * Label.of("Success").status("success")
 * Label.of("Blue").color("blue").asRemovable()
 * Label.of("Docs").color("purple").href("https://example.com")
 * </pre>
 *
 * <p>Renders as {@code <span>} by default, {@code <a>} when {@link #href(String)}
 * is set, or {@code <button>} for the {@code overflow}/{@code add} variants.
 * Statuses auto-pick their stock icon (info, success, warning, danger,
 * custom), overridable via {@link #icon(String)}. {@link #asEditable()} emits
 * the full {@code phaLabelEditable} Alpine wiring. Standalone or inside a
 * {@link LabelGroup}.
 *
 * Template side: <code>{#include components/data-display/label label=label /}</code>
 */
@TemplateData
public final class Label {

    private final String text;
    private final String id;
    private final String color;
    private final String status;
    private final String variant;
    private final boolean compact;
    private final boolean disabled;
    private final boolean clickable;
    private final boolean editable;
    private final String href;
    private final String icon;
    private final boolean removable;
    private final String removeClick;
    private final String removeAria;
    private final String maxWidth;

    private Label(String text, String id, String color, String status, String variant,
                  boolean compact, boolean disabled, boolean clickable, boolean editable,
                  String href, String icon, boolean removable, String removeClick,
                  String removeAria, String maxWidth) {
        this.text = text;
        this.id = id;
        this.color = color;
        this.status = status;
        this.variant = variant;
        this.compact = compact;
        this.disabled = disabled;
        this.clickable = clickable;
        this.editable = editable;
        this.href = href;
        this.icon = icon;
        this.removable = removable;
        this.removeClick = removeClick;
        this.removeAria = removeAria;
        this.maxWidth = maxWidth;
    }

    public static Label of(String text) {
        return new Label(Objects.requireNonNull(text, "text"), null, null, null, null,
                false, false, false, false, null, null, false, null, null, null);
    }

    private Label copy(String id, String color, String status, String variant,
                       boolean compact, boolean disabled, boolean clickable, boolean editable,
                       String href, String icon, boolean removable, String removeClick,
                       String removeAria, String maxWidth) {
        return new Label(text, id, color, status, variant, compact, disabled, clickable,
                editable, href, icon, removable, removeClick, removeAria, maxWidth);
    }

    /** DOM id on the label root. */
    public Label id(String id) {
        return copy(id, color, status, variant, compact, disabled, clickable, editable,
                href, icon, removable, removeClick, removeAria, maxWidth);
    }

    /** blue | teal | green | orange | purple | red | orangered | yellow (default grey). */
    public Label color(String color) {
        return copy(id, color, status, variant, compact, disabled, clickable, editable,
                href, icon, removable, removeClick, removeAria, maxWidth);
    }

    /** info | success | warning | danger | custom — picks the status color and stock icon. */
    public Label status(String status) {
        return copy(id, color, status, variant, compact, disabled, clickable, editable,
                href, icon, removable, removeClick, removeAria, maxWidth);
    }

    /** Outline variant (default is filled). */
    public Label asOutline() {
        return copy(id, color, status, "outline", compact, disabled, clickable, editable,
                href, icon, removable, removeClick, removeAria, maxWidth);
    }

    /** Overflow variant — a button-flavoured label revealing hidden group labels. */
    public Label asOverflow() {
        return copy(id, color, status, "overflow", compact, disabled, clickable, editable,
                href, icon, removable, removeClick, removeAria, maxWidth);
    }

    /** Add variant — a button-flavoured label for adding new labels. */
    public Label asAdd() {
        return copy(id, color, status, "add", compact, disabled, clickable, editable,
                href, icon, removable, removeClick, removeAria, maxWidth);
    }

    public Label asCompact() {
        return copy(id, color, status, variant, true, disabled, clickable, editable,
                href, icon, removable, removeClick, removeAria, maxWidth);
    }

    public Label asDisabled() {
        return copy(id, color, status, variant, compact, true, clickable, editable,
                href, icon, removable, removeClick, removeAria, maxWidth);
    }

    /** Clickable treatment (cursor + hover); usually paired with {@link #href(String)}. */
    public Label asClickable() {
        return copy(id, color, status, variant, compact, disabled, true, editable,
                href, icon, removable, removeClick, removeAria, maxWidth);
    }

    /** Edit-in-place label — emits the phaLabelEditable Alpine wiring. */
    public Label asEditable() {
        return copy(id, color, status, variant, compact, disabled, clickable, true,
                href, icon, removable, removeClick, removeAria, maxWidth);
    }

    /** Renders the label as an anchor. */
    public Label href(String href) {
        return copy(id, color, status, variant, compact, disabled, clickable, editable,
                href, icon, removable, removeClick, removeAria, maxWidth);
    }

    /** Icon before the text, e.g. {@code "fa:circle-info"} — overrides the status icon. */
    public Label icon(String icon) {
        return copy(id, color, status, variant, compact, disabled, clickable, editable,
                href, icon, removable, removeClick, removeAria, maxWidth);
    }

    /** Close button in the label's actions area. */
    public Label asRemovable() {
        return copy(id, color, status, variant, compact, disabled, clickable, editable,
                href, icon, true, removeClick, removeAria, maxWidth);
    }

    /** Close button whose {@code @click} runs the given Alpine expression (e.g. remove the group row). */
    public Label removable(String removeClick) {
        return copy(id, color, status, variant, compact, disabled, clickable, editable,
                href, icon, true, Objects.requireNonNull(removeClick, "removeClick"), removeAria, maxWidth);
    }

    /** aria-label for the close button (default "Remove"). */
    public Label removeAria(String removeAria) {
        return copy(id, color, status, variant, compact, disabled, clickable, editable,
                href, icon, removable, removeClick, removeAria, maxWidth);
    }

    /** Truncation cap for the text, e.g. {@code "38ch"} — sets {@code --pf-v6-c-label__text--MaxWidth}. */
    public Label maxWidth(String maxWidth) {
        return copy(id, color, status, variant, compact, disabled, clickable, editable,
                href, icon, removable, removeClick, removeAria, maxWidth);
    }

    public String text() {
        return text;
    }

    public String id() {
        return id;
    }

    public String color() {
        return color;
    }

    public String status() {
        return status;
    }

    public String variant() {
        return variant;
    }

    /** True for the overflow/add variants, which render as buttons. */
    public boolean isButtonVariant() {
        return "overflow".equals(variant) || "add".equals(variant);
    }

    public boolean isCompact() {
        return compact;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public boolean isClickable() {
        return clickable;
    }

    public boolean isEditable() {
        return editable;
    }

    public String href() {
        return href;
    }

    public String icon() {
        return icon;
    }

    /** Icon resolved for rendering: the explicit icon, else the status stock icon. */
    public String displayIcon() {
        if (icon != null) {
            return icon;
        }
        if (status == null) {
            return null;
        }
        return switch (status) {
            case "info" -> "fa:circle-info";
            case "success" -> "fa:circle-check";
            case "warning" -> "fa:triangle-exclamation";
            case "danger" -> "fa:circle-exclamation";
            case "custom" -> "fa:bell";
            default -> null;
        };
    }

    public boolean isRemovable() {
        return removable;
    }

    public String removeClick() {
        return removeClick;
    }

    public String removeAria() {
        return removeAria;
    }

    public String maxWidth() {
        return maxWidth;
    }
}
