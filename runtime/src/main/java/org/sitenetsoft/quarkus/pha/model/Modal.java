package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Modal component ({@code pf-v6-c-modal-box}) — renders
 * the full live pattern: launcher button, backdrop, and dialog box.
 *
 * <pre>
 * Modal.of("mo-basic").size("md").trigger("Open modal")
 *     .title("Are you sure?")
 *     .body("&lt;p&gt;This action removes the project…&lt;/p&gt;")
 *     .footerButton("Delete", "pf-m-danger")
 *     .footerButton("Cancel", "pf-m-link").build()
 * Modal.of("mo-info").size("md").variant("info").alertPrefix().trigger("Open info alert modal")
 *     .title("info alert modal").body(...).footerButton("Confirm", "pf-m-primary")…
 * Modal.of("mo-generic").size("md").ariaLabel("Generic modal container")
 *     .trigger("Open generic modal container").noClose().genericHtml(...)…
 * </pre>
 *
 * <p>Named via the title (aria-labelledby) or {@code ariaLabel}; variants add
 * the colored title icon (status on the box, or the title via
 * {@code statusTitle()}); footer buttons all close the dialog. Compositions
 * with live inner state (forms, dropdowns, help popovers, dynamic sizes,
 * custom headers/focus) stay on the slot shell.
 */
@TemplateData
public final class Modal {

    /** One footer button (closes on click). */
    @TemplateData
    public static final class FooterButton {
        private final String text;
        private final String classes;

        private FooterButton(String text, String classes) {
            this.text = text;
            this.classes = classes;
        }

        public String text() {
            return text;
        }

        public String classes() {
            return classes;
        }
    }

    private final String id;
    private final String triggerText;
    private final String size;
    private final String variant;
    private final boolean statusTitle;
    private final boolean alignTop;
    private final String width;
    private final String title;
    private final String headingLevel;
    private final String titleIcon;
    private final boolean alertPrefix;
    private final String description;
    private final String ariaLabel;
    private final boolean noClose;
    private final String bodyHtml;
    private final boolean bodyScrollable;
    private final String genericHtml;
    private final List<FooterButton> footer;

    private Modal(Builder b) {
        this.id = b.id;
        this.triggerText = b.triggerText;
        this.size = b.size;
        this.variant = b.variant;
        this.statusTitle = b.statusTitle;
        this.alignTop = b.alignTop;
        this.width = b.width;
        this.title = b.title;
        this.headingLevel = b.headingLevel;
        this.titleIcon = b.titleIcon;
        this.alertPrefix = b.alertPrefix;
        this.description = b.description;
        this.ariaLabel = b.ariaLabel;
        this.noClose = b.noClose;
        this.bodyHtml = b.bodyHtml;
        this.bodyScrollable = b.bodyScrollable;
        this.genericHtml = b.genericHtml;
        this.footer = List.copyOf(b.footer);
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    public String id() {
        return id;
    }

    public String triggerText() {
        return triggerText;
    }

    /** Box modifier-class suffix, leading-space separated (shell order). */
    public String boxModifiers() {
        StringBuilder sb = new StringBuilder();
        if (size != null) {
            sb.append(" pf-m-").append(size);
        }
        if (variant != null && !statusTitle) {
            sb.append(" pf-m-").append(variant);
        }
        if (alignTop) {
            sb.append(" pf-m-align-top");
        }
        return sb.toString();
    }

    public String variant() {
        return variant;
    }

    public boolean isStatusTitle() {
        return statusTitle;
    }

    public String width() {
        return width;
    }

    public String title() {
        return title;
    }

    public String headingTag() {
        return headingLevel != null ? headingLevel : "h1";
    }

    /** Explicit or variant title icon; null when the title is plain. */
    public String displayTitleIcon() {
        if (titleIcon != null) {
            return titleIcon;
        }
        if (variant == null) {
            return null;
        }
        return switch (variant) {
            case "danger" -> "fa:circle-exclamation";
            case "warning" -> "fa:exclamation-triangle";
            case "success" -> "fa:circle-check";
            case "custom" -> "fa:bell";
            default -> "fa:circle-info";
        };
    }

    public boolean isAlertPrefix() {
        return alertPrefix && variant != null;
    }

    /** The screen-reader alert-prefix wording for the variant. */
    public String alertPrefixText() {
        return switch (variant) {
            case "custom" -> "Default";
            case "success" -> "Success";
            case "warning" -> "Warning";
            case "danger" -> "Danger";
            default -> "Info";
        };
    }

    public String description() {
        return description;
    }

    public String ariaLabel() {
        return ariaLabel;
    }

    public boolean isNoClose() {
        return noClose;
    }

    public String bodyHtml() {
        return bodyHtml;
    }

    public boolean isBodyScrollable() {
        return bodyScrollable;
    }

    public String genericHtml() {
        return genericHtml;
    }

    public List<FooterButton> footer() {
        return footer;
    }

    public static final class Builder {
        private String id;
        private String triggerText;
        private String size;
        private String variant;
        private boolean statusTitle;
        private boolean alignTop;
        private String width;
        private String title;
        private String headingLevel;
        private String titleIcon;
        private boolean alertPrefix;
        private String description;
        private String ariaLabel;
        private boolean noClose;
        private String bodyHtml;
        private boolean bodyScrollable;
        private String genericHtml;
        private final List<FooterButton> footer = new ArrayList<>();

        private Builder() {
        }

        /** Primary launcher button text. */
        public Builder trigger(String triggerText) {
            this.triggerText = triggerText;
            return this;
        }

        /** sm | md | lg. */
        public Builder size(String size) {
            this.size = size;
            return this;
        }

        /** danger | warning | success | info | custom. */
        public Builder variant(String variant) {
            this.variant = variant;
            return this;
        }

        /** Status class on the title element instead of the box. */
        public Builder statusTitle() {
            this.statusTitle = true;
            return this;
        }

        public Builder alignTop() {
            this.alignTop = true;
            return this;
        }

        /** Custom width (e.g. "50%") via the PF width variable. */
        public Builder width(String width) {
            this.width = width;
            return this;
        }

        /** Header title (dialog named via aria-labelledby). */
        public Builder title(String title) {
            this.title = title;
            return this;
        }

        /** h1 (default) | h2 | … */
        public Builder headingLevel(String headingLevel) {
            this.headingLevel = headingLevel;
            return this;
        }

        /** Custom title icon, overrides the variant icon. */
        public Builder titleIcon(String titleIcon) {
            this.titleIcon = titleIcon;
            return this;
        }

        /** Screen-reader "&lt;Variant&gt; alert:" prefix before the title. */
        public Builder alertPrefix() {
            this.alertPrefix = true;
            return this;
        }

        /** Static description line under the title. */
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        /** Accessible dialog name when there is no title. */
        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        /** No floating close button (generic-container shape). */
        public Builder noClose() {
            this.noClose = true;
            return this;
        }

        /** Body content (raw HTML). */
        public Builder body(String bodyHtml) {
            this.bodyHtml = bodyHtml;
            return this;
        }

        /** Keyboard-scrollable body (tabindex 0). */
        public Builder bodyScrollable() {
            this.bodyScrollable = true;
            return this;
        }

        /** Bare-box content — no header/body/footer wrappers (raw HTML). */
        public Builder genericHtml(String genericHtml) {
            this.genericHtml = genericHtml;
            return this;
        }

        /** Footer button; all close the dialog on click. */
        public Builder footerButton(String text, String classes) {
            footer.add(new FooterButton(Objects.requireNonNull(text, "text"),
                    Objects.requireNonNull(classes, "classes")));
            return this;
        }

        public Modal build() {
            Objects.requireNonNull(triggerText, "trigger");
            if (title == null && ariaLabel == null) {
                throw new IllegalStateException("A modal needs a title or an ariaLabel");
            }
            return new Modal(this);
        }
    }
}
