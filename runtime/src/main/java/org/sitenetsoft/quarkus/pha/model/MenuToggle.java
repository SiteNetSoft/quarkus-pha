package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Menu toggle component ({@code pf-v6-c-menu-toggle}).
 *
 * <pre>
 * MenuToggle.of("Actions").id("mt-basic")
 * MenuToggle.of("Choose").asPrimary()
 * MenuToggle.of("").asPlain().ariaLabel("More actions")
 * MenuToggle.of("Success").status("success")
 * MenuToggle.split("mt-split").actionButton("Action")
 * MenuToggle.split("mt-check").checkbox("Select all")
 * MenuToggle.typeahead("mt-typeahead", "Type to filter")
 * </pre>
 *
 * <p>Three shapes: the default {@code <button>} toggle (variants, status
 * icons, leading icon or avatar, badge), the split-button {@code <div>}
 * (action button or checkbox plus the caret toggle, built via
 * {@link #split(String)}), and the typeahead input shell
 * ({@link #typeahead(String, String)}). Split/typeahead child ids derive
 * from the root id ({@code {id}-toggle-button}, {@code {id}-check},
 * {@code {id}-input}, {@code {id}-button}).
 */
@TemplateData
public final class MenuToggle {

    private final String shape; // "button" | "split" | "typeahead"
    private final String text;
    private final String id;
    private final String ariaLabel;
    private final boolean primary;
    private final boolean secondary;
    private final boolean danger;
    private final boolean plain;
    private final boolean textModifier;
    private final boolean circle;
    private final boolean settings;
    private final boolean placeholder;
    private final boolean small;
    private final boolean fullHeight;
    private final boolean fullWidth;
    private final String status;
    private final boolean expanded;
    private final boolean disabled;
    private final String icon;
    private final boolean avatar;
    private final String badge;
    private final String actionButton;
    private final boolean checkbox;
    private final String checkAriaLabel;
    private final String checkLabel;
    private final String splitText;
    private final String toggleIcon;
    private final String toggleText;
    private final String typeaheadPlaceholder;

    private MenuToggle(Builder b) {
        this.shape = b.shape;
        this.text = b.text;
        this.id = b.id;
        this.ariaLabel = b.ariaLabel;
        this.primary = b.primary;
        this.secondary = b.secondary;
        this.danger = b.danger;
        this.plain = b.plain;
        this.textModifier = b.textModifier;
        this.circle = b.circle;
        this.settings = b.settings;
        this.placeholder = b.placeholder;
        this.small = b.small;
        this.fullHeight = b.fullHeight;
        this.fullWidth = b.fullWidth;
        this.status = b.status;
        this.expanded = b.expanded;
        this.disabled = b.disabled;
        this.icon = b.icon;
        this.avatar = b.avatar;
        this.badge = b.badge;
        this.actionButton = b.actionButton;
        this.checkbox = b.checkbox;
        this.checkAriaLabel = b.checkAriaLabel;
        this.checkLabel = b.checkLabel;
        this.splitText = b.splitText;
        this.toggleIcon = b.toggleIcon;
        this.toggleText = b.toggleText;
        this.typeaheadPlaceholder = b.typeaheadPlaceholder;
    }

    /** Standard button toggle; pass {@code ""} for icon-only/plain toggles. */
    public static Builder of(String text) {
        Builder b = new Builder("button");
        b.text = Objects.requireNonNull(text, "text");
        return b;
    }

    /** Split-button toggle ({@code pf-m-split-button}); child ids derive from the root id. */
    public static Builder split(String id) {
        Builder b = new Builder("split");
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    /** Typeahead toggle — a plain text input plus the caret button. */
    public static Builder typeahead(String id, String placeholder) {
        Builder b = new Builder("typeahead");
        b.id = Objects.requireNonNull(id, "id");
        b.typeaheadPlaceholder = Objects.requireNonNull(placeholder, "placeholder");
        b.fullWidth = true;
        return b;
    }

    public boolean isSplit() {
        return "split".equals(shape);
    }

    public boolean isTypeahead() {
        return "typeahead".equals(shape);
    }

    public String text() {
        return text;
    }

    public String id() {
        return id;
    }

    public String ariaLabel() {
        return ariaLabel;
    }

    public boolean isPrimary() {
        return primary;
    }

    public boolean isSecondary() {
        return secondary;
    }

    public boolean isDanger() {
        return danger;
    }

    public boolean isPlain() {
        return plain;
    }

    public boolean isTextModifier() {
        return textModifier;
    }

    public boolean isCircle() {
        return circle;
    }

    public boolean isSettings() {
        return settings;
    }

    public boolean isPlaceholder() {
        return placeholder;
    }

    public boolean isSmall() {
        return small;
    }

    public boolean isFullHeight() {
        return fullHeight;
    }

    public boolean isFullWidth() {
        return fullWidth;
    }

    public String status() {
        return status;
    }

    /** Stock status icon for the controls area. */
    public String statusIcon() {
        if (status == null) {
            return null;
        }
        return switch (status) {
            case "success" -> "fa:circle-check";
            case "warning" -> "fa:exclamation-triangle";
            case "danger" -> "fa:exclamation-circle";
            default -> null;
        };
    }

    public boolean isExpanded() {
        return expanded;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public String icon() {
        return icon;
    }

    public boolean isAvatar() {
        return avatar;
    }

    public String badge() {
        return badge;
    }

    /** True when the caret controls are rendered: everything except icon-only plain toggles. */
    public boolean isShowControls() {
        return !plain || textModifier;
    }

    /** True when the (possibly empty) text span is rendered — suppressed on icon-only and badge-only toggles. */
    public boolean isShowText() {
        return !(text != null && text.isEmpty() && (icon != null || avatar || badge != null));
    }

    public String actionButton() {
        return actionButton;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public String checkAriaLabel() {
        return checkAriaLabel;
    }

    public String checkLabel() {
        return checkLabel;
    }

    public String splitText() {
        return splitText;
    }

    public String toggleIcon() {
        return toggleIcon;
    }

    public String toggleText() {
        return toggleText;
    }

    public String typeaheadPlaceholder() {
        return typeaheadPlaceholder;
    }

    public String toggleButtonId() {
        return id != null ? id + "-toggle-button" : null;
    }

    public String checkId() {
        return id != null ? id + "-check" : null;
    }

    public String inputId() {
        return id != null ? id + "-input" : null;
    }

    public String buttonId() {
        return id != null ? id + "-button" : null;
    }

    public static final class Builder {

        private final String shape;
        private String text;
        private String id;
        private String ariaLabel;
        private boolean primary;
        private boolean secondary;
        private boolean danger;
        private boolean plain;
        private boolean textModifier;
        private boolean circle;
        private boolean settings;
        private boolean placeholder;
        private boolean small;
        private boolean fullHeight;
        private boolean fullWidth;
        private String status;
        private boolean expanded;
        private boolean disabled;
        private String icon;
        private boolean avatar;
        private String badge;
        private String actionButton;
        private boolean checkbox;
        private String checkAriaLabel;
        private String checkLabel;
        private String splitText;
        private String toggleIcon;
        private String toggleText;
        private String typeaheadPlaceholder;

        private Builder(String shape) {
            this.shape = shape;
        }

        /** DOM id on the toggle root. */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        public Builder asPrimary() {
            this.primary = true;
            return this;
        }

        public Builder asSecondary() {
            this.secondary = true;
            return this;
        }

        public Builder asDanger() {
            this.danger = true;
            return this;
        }

        public Builder asPlain() {
            this.plain = true;
            return this;
        }

        /** {@code pf-m-text} — plain toggle that keeps its label and caret. */
        public Builder asText() {
            this.textModifier = true;
            return this;
        }

        public Builder asCircle() {
            this.circle = true;
            return this;
        }

        public Builder asSettings() {
            this.settings = true;
            return this;
        }

        public Builder asPlaceholder() {
            this.placeholder = true;
            return this;
        }

        public Builder asSmall() {
            this.small = true;
            return this;
        }

        public Builder asFullHeight() {
            this.fullHeight = true;
            return this;
        }

        public Builder asFullWidth() {
            this.fullWidth = true;
            return this;
        }

        /** success | warning | danger — status color plus its stock icon in the controls. */
        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Builder asExpanded() {
            this.expanded = true;
            return this;
        }

        public Builder asDisabled() {
            this.disabled = true;
            return this;
        }

        /** Leading icon, e.g. {@code "fa:gear"} — or the kebab for plain toggles. */
        public Builder icon(String icon) {
            this.icon = icon;
            return this;
        }

        /** Leading stock avatar image. */
        public Builder asAvatar() {
            this.avatar = true;
            return this;
        }

        /** Unread badge text after the label, e.g. {@code "4 selected"}. */
        public Builder badge(String badge) {
            this.badge = badge;
            return this;
        }

        /** Split only: leading action button with the given label. */
        public Builder actionButton(String actionButton) {
            this.actionButton = actionButton;
            return this;
        }

        /** Split only: leading select-all checkbox with its aria-label. */
        public Builder checkbox(String checkAriaLabel) {
            this.checkbox = true;
            this.checkAriaLabel = Objects.requireNonNull(checkAriaLabel, "checkAriaLabel");
            return this;
        }

        /** Split only: visible label inside the checkbox, e.g. {@code "10 selected"}. */
        public Builder checkLabel(String checkLabel) {
            this.checkLabel = checkLabel;
            return this;
        }

        /** Split only: loose text between the checkbox and the caret button. */
        public Builder splitText(String splitText) {
            this.splitText = splitText;
            return this;
        }

        /** Split only: icon inside the caret button ({@code pf-m-text} toggle button). */
        public Builder toggleIcon(String toggleIcon) {
            this.toggleIcon = toggleIcon;
            return this;
        }

        /** Split only: text inside the caret button ({@code pf-m-text} toggle button). */
        public Builder toggleText(String toggleText) {
            this.toggleText = toggleText;
            return this;
        }

        public MenuToggle build() {
            if ("split".equals(shape) && actionButton == null && !checkbox && splitText == null) {
                throw new IllegalStateException("A split toggle needs an action button, a checkbox, or split text");
            }
            return new MenuToggle(this);
        }
    }
}
