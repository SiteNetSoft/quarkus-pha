package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the About modal component ({@code pf-v6-c-about-modal-box}).
 *
 * <pre>
 * AboutModal.of("basic", "/web/images/logo-dark.svg", "logo")
 *     .productName("quarkus-pha").headingLevel("h2")
 *     .backgroundImage("/web/images/pf-background.svg")
 *     .trigger("About")
 *     .content(descriptionListHtml)
 *     .trademarkHtml("&lt;p class=\\"pf-v6-c-about-modal-box__strapline\\"&gt;…&lt;/p&gt;")
 *     .strapline("Copyright &amp;copy; 2026 SiteNetSoft").build()
 * </pre>
 *
 * <p>Provide {@code productName} (aria-labelledby heading) or {@code ariaLabel}
 * (label-only dialog). {@code trigger(text)} renders the primary button that
 * dispatches the open event; {@code noContentContainer()} skips the default
 * __content wrapper for custom layouts. Content/trademark/strapline are raw HTML.
 */
@TemplateData
public final class AboutModal {

    private final String id;
    private final String brandImageSrc;
    private final String brandImageAlt;
    private final String productName;
    private final String ariaLabel;
    private final String backgroundImage;
    private final String headingLevel;
    private final String closeButtonAriaLabel;
    private final boolean noContentContainer;
    private final String triggerText;
    private final String contentHtml;
    private final String trademarkHtml;
    private final String straplineHtml;

    private AboutModal(Builder b) {
        this.id = b.id;
        this.brandImageSrc = b.brandImageSrc;
        this.brandImageAlt = b.brandImageAlt;
        this.productName = b.productName;
        this.ariaLabel = b.ariaLabel;
        this.backgroundImage = b.backgroundImage;
        this.headingLevel = b.headingLevel;
        this.closeButtonAriaLabel = b.closeButtonAriaLabel;
        this.noContentContainer = b.noContentContainer;
        this.triggerText = b.triggerText;
        this.contentHtml = b.contentHtml;
        this.trademarkHtml = b.trademarkHtml;
        this.straplineHtml = b.straplineHtml;
    }

    public static Builder of(String id, String brandImageSrc, String brandImageAlt) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        b.brandImageSrc = Objects.requireNonNull(brandImageSrc, "brandImageSrc");
        b.brandImageAlt = Objects.requireNonNull(brandImageAlt, "brandImageAlt");
        return b;
    }

    public String id() {
        return id;
    }

    public String brandImageSrc() {
        return brandImageSrc;
    }

    public String brandImageAlt() {
        return brandImageAlt;
    }

    public String productName() {
        return productName;
    }

    public String ariaLabel() {
        return ariaLabel;
    }

    public String backgroundImage() {
        return backgroundImage;
    }

    public String headingTag() {
        return headingLevel != null ? headingLevel : "h1";
    }

    public String closeButtonAriaLabel() {
        return closeButtonAriaLabel != null ? closeButtonAriaLabel : "Close dialog";
    }

    public boolean isNoContentContainer() {
        return noContentContainer;
    }

    public String triggerText() {
        return triggerText;
    }

    public String contentHtml() {
        return contentHtml;
    }

    public String trademarkHtml() {
        return trademarkHtml;
    }

    public String straplineHtml() {
        return straplineHtml;
    }

    public static final class Builder {
        private String id;
        private String brandImageSrc;
        private String brandImageAlt;
        private String productName;
        private String ariaLabel;
        private String backgroundImage;
        private String headingLevel;
        private String closeButtonAriaLabel;
        private boolean noContentContainer;
        private String triggerText;
        private String contentHtml;
        private String trademarkHtml;
        private String straplineHtml;

        private Builder() {
        }

        /** Product name heading (dialog labelled via aria-labelledby). */
        public Builder productName(String productName) {
            this.productName = productName;
            return this;
        }

        /** Accessible dialog name when there is no product-name heading. */
        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        public Builder backgroundImage(String backgroundImage) {
            this.backgroundImage = backgroundImage;
            return this;
        }

        /** h1 (default) | h2 | ... on the product-name title. */
        public Builder headingLevel(String headingLevel) {
            this.headingLevel = headingLevel;
            return this;
        }

        public Builder closeButtonAriaLabel(String closeButtonAriaLabel) {
            this.closeButtonAriaLabel = closeButtonAriaLabel;
            return this;
        }

        /** Skip the default __content wrapper — the content brings its own layout. */
        public Builder noContentContainer() {
            this.noContentContainer = true;
            return this;
        }

        /** Primary trigger button dispatching open-about-modal-{id}. */
        public Builder trigger(String triggerText) {
            this.triggerText = triggerText;
            return this;
        }

        /** Body content (raw HTML). */
        public Builder content(String contentHtml) {
            this.contentHtml = contentHtml;
            return this;
        }

        /** Trademark block (raw HTML) rendered between body and strapline. */
        public Builder trademarkHtml(String trademarkHtml) {
            this.trademarkHtml = trademarkHtml;
            return this;
        }

        /** Strapline content (raw HTML) inside the __strapline paragraph. */
        public Builder strapline(String straplineHtml) {
            this.straplineHtml = straplineHtml;
            return this;
        }

        public AboutModal build() {
            if (productName == null && ariaLabel == null) {
                throw new IllegalStateException("An about modal needs productName or ariaLabel");
            }
            if (contentHtml == null) {
                throw new IllegalStateException("An about modal needs content(...)");
            }
            return new AboutModal(this);
        }
    }
}
