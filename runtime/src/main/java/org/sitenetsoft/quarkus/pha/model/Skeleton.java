package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

/**
 * Typed model for the Skeleton component ({@code pf-v6-c-skeleton}).
 *
 * <pre>
 * Skeleton.builder().screenReaderText("Loading").build()
 * Skeleton.builder().width("25").build()                        // pf-m-width-25
 * Skeleton.builder().shape("circle").width("sm").height("sm").build()
 * Skeleton.builder().shape("circle").widthValue("80px").heightValue("80px").build()
 * Skeleton.builder().fontSize("2xl").width("50").build()        // pf-m-text-2xl
 * </pre>
 *
 * <p>Size with modifier tokens ({@code width}/{@code height}: sm | md | lg |
 * 25 | 33 | 50 | 66 | 75 | 100) or pixel-exact inline lengths
 * ({@code widthValue}/{@code heightValue}) — each axis one or the other.
 */
@TemplateData
public final class Skeleton {

    private final String id;
    private final String shape;
    private final String width;
    private final String height;
    private final String fontSize;
    private final String widthValue;
    private final String heightValue;
    private final String screenReaderText;

    private Skeleton(Builder b) {
        this.id = b.id;
        this.shape = b.shape;
        this.width = b.width;
        this.height = b.height;
        this.fontSize = b.fontSize;
        this.widthValue = b.widthValue;
        this.heightValue = b.heightValue;
        this.screenReaderText = b.screenReaderText;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String id() {
        return id;
    }

    public String shape() {
        return shape;
    }

    public String width() {
        return width;
    }

    public String height() {
        return height;
    }

    public String fontSize() {
        return fontSize;
    }

    public String widthValue() {
        return widthValue;
    }

    public String heightValue() {
        return heightValue;
    }

    public String screenReaderText() {
        return screenReaderText;
    }

    public static final class Builder {
        private String id;
        private String shape;
        private String width;
        private String height;
        private String fontSize;
        private String widthValue;
        private String heightValue;
        private String screenReaderText;

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** circle | square. */
        public Builder shape(String shape) {
            this.shape = shape;
            return this;
        }

        /** Width token: sm | md | lg | 25 | 33 | 50 | 66 | 75. */
        public Builder width(String width) {
            this.width = width;
            return this;
        }

        /** Height token: sm | md | lg | 25 | 33 | 50 | 66 | 75 | 100 (needs a sized parent). */
        public Builder height(String height) {
            this.height = height;
            return this;
        }

        /** Text-line height: sm | md | lg | xl | 2xl | 3xl | 4xl → pf-m-text-{v}. */
        public Builder fontSize(String fontSize) {
            this.fontSize = fontSize;
            return this;
        }

        /** Arbitrary CSS width (mutually exclusive with {@link #width}). */
        public Builder widthValue(String widthValue) {
            this.widthValue = widthValue;
            return this;
        }

        /** Arbitrary CSS height (mutually exclusive with {@link #height}). */
        public Builder heightValue(String heightValue) {
            this.heightValue = heightValue;
            return this;
        }

        public Builder screenReaderText(String screenReaderText) {
            this.screenReaderText = screenReaderText;
            return this;
        }

        public Skeleton build() {
            if (width != null && widthValue != null) {
                throw new IllegalStateException("width and widthValue are mutually exclusive");
            }
            if (height != null && heightValue != null) {
                throw new IllegalStateException("height and heightValue are mutually exclusive");
            }
            return new Skeleton(this);
        }
    }
}
