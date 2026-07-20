package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Clipboard copy component ({@code pf-v6-c-clipboard-copy}).
 *
 * <pre>
 * ClipboardCopy.of("cc-basic", "Copy this string").build()
 * ClipboardCopy.of("cc-exp", "One-line summary").expandable().expandedText("Full content…").build()
 * ClipboardCopy.of("cc-keys", "ssh-rsa AAAA... (3 keys)").expanded()
 *     .expandedHtml("key-one&lt;br /&gt;key-two").build()          // copies the body's innerText
 * ClipboardCopy.of("cc-cmd", "oc new-app httpd-example").inline().code()
 *     .extraAction("fa:play", "Run in terminal").build()
 * </pre>
 *
 * <p>Three anatomies: input row (default), expandable (toggle + panel), and
 * inline (text span/code + actions). {@code expandedHtml} switches the copy
 * source to the expandable body's innerText (multi-line/array pattern) and
 * renders the panel as raw HTML.
 */
@TemplateData
public final class ClipboardCopy {

    private final String id;
    private final String text;
    private final boolean readonly;
    private final boolean expandable;
    private final boolean expanded;
    private final String expandedText;
    private final String expandedHtml;
    private final boolean inline;
    private final boolean code;
    private final boolean block;
    private final String textStyle;
    private final String extraActionIcon;
    private final String extraActionLabel;

    private ClipboardCopy(Builder b) {
        this.id = b.id;
        this.text = b.text;
        this.readonly = b.readonly;
        this.expandable = b.expandable;
        this.expanded = b.expanded;
        this.expandedText = b.expandedText;
        this.expandedHtml = b.expandedHtml;
        this.inline = b.inline;
        this.code = b.code;
        this.block = b.block;
        this.textStyle = b.textStyle;
        this.extraActionIcon = b.extraActionIcon;
        this.extraActionLabel = b.extraActionLabel;
    }

    public static Builder of(String id, String text) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        b.text = Objects.requireNonNull(text, "text");
        return b;
    }

    public String id() {
        return id;
    }

    public String text() {
        return text;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public boolean isExpandableShape() {
        return expandable || expandedHtml != null;
    }

    public boolean isStartExpanded() {
        return expanded;
    }

    /** Panel text (escaped); falls back to the copy text. */
    public String panelText() {
        return expandedText != null ? expandedText : text;
    }

    public boolean isBodyCopy() {
        return expandedHtml != null;
    }

    public String expandedHtml() {
        return expandedHtml;
    }

    public boolean isInline() {
        return inline;
    }

    public boolean isCode() {
        return code;
    }

    public boolean isBlock() {
        return block;
    }

    public String textStyle() {
        return textStyle;
    }

    public String extraActionIcon() {
        return extraActionIcon;
    }

    public String extraActionLabel() {
        return extraActionLabel;
    }

    public static final class Builder {
        private String id;
        private String text;
        private boolean readonly;
        private boolean expandable;
        private boolean expanded;
        private String expandedText;
        private String expandedHtml;
        private boolean inline;
        private boolean code;
        private boolean block;
        private String textStyle;
        private String extraActionIcon;
        private String extraActionLabel;

        private Builder() {
        }

        /** Read-only input (pf-m-readonly + readonly attr). */
        public Builder readonly() {
            this.readonly = true;
            return this;
        }

        public Builder expandable() {
            this.expandable = true;
            return this;
        }

        /** Start with the panel open. */
        public Builder expanded() {
            this.expanded = true;
            return this;
        }

        /** Panel text (escaped) for the expandable shape. */
        public Builder expandedText(String expandedText) {
            this.expandedText = expandedText;
            return this;
        }

        /** Raw-HTML panel whose innerText is what gets copied (array/JSON pattern). */
        public Builder expandedHtml(String expandedHtml) {
            this.expandedHtml = expandedHtml;
            return this;
        }

        /** Inline text-span anatomy. */
        public Builder inline() {
            this.inline = true;
            return this;
        }

        /** Inline code styling (pf-m-code on the text). */
        public Builder code() {
            this.code = true;
            return this;
        }

        /** pf-m-block on the inline shape. */
        public Builder block() {
            this.block = true;
            return this;
        }

        /** Inline style on the text span (truncation etc.). */
        public Builder textStyle(String textStyle) {
            this.textStyle = textStyle;
            return this;
        }

        /** Additional action button after the copy action (inline shape). */
        public Builder extraAction(String icon, String ariaLabel) {
            this.extraActionIcon = Objects.requireNonNull(icon, "icon");
            this.extraActionLabel = Objects.requireNonNull(ariaLabel, "ariaLabel");
            return this;
        }

        public ClipboardCopy build() {
            return new ClipboardCopy(this);
        }
    }
}
