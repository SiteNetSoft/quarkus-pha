package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Hero component ({@code pf-v6-c-hero}).
 *
 * <pre>
 * Hero.of("hero-basic")
 *     .html(titleAndCopyHtml)
 *     .cta(Button.of("Get started").variant("primary").build()).build()
 * Hero.of("hero-glass").glass().html(bodyHtml).build()
 * </pre>
 *
 * <p>The body is raw HTML; an optional CTA {@link Button} renders after it.
 */
@TemplateData
public final class Hero {

    private final String id;
    private final boolean glass;
    private final String html;
    private final Button cta;

    private Hero(Builder b) {
        this.id = b.id;
        this.glass = b.glass;
        this.html = b.html;
        this.cta = b.cta;
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    public String id() {
        return id;
    }

    public boolean isGlass() {
        return glass;
    }

    public String html() {
        return html;
    }

    public Button cta() {
        return cta;
    }

    public static final class Builder {
        private String id;
        private boolean glass;
        private String html;
        private Button cta;

        private Builder() {
        }

        /** Translucent blurred backdrop (pf-m-glass) — pair with an image/pattern background. */
        public Builder glass() {
            this.glass = true;
            return this;
        }

        /** Body content (raw HTML) — typically a title and supporting copy. */
        public Builder html(String html) {
            this.html = html;
            return this;
        }

        /** Call-to-action button rendered after the body content. */
        public Builder cta(Button cta) {
            this.cta = cta;
            return this;
        }

        public Hero build() {
            if (html == null) {
                throw new IllegalStateException("A hero needs html(...) body content");
            }
            return new Hero(this);
        }
    }
}
