package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Input group component ({@code pf-v6-c-input-group}).
 *
 * <pre>
 * InputGroup.of("ig-basic")
 *     .boxText("@")
 *     .fill("&lt;span class=\\"pf-v6-c-form-control\\"&gt;&lt;input ... /&gt;&lt;/span&gt;")
 *     .boxText(".example.com").build()
 * InputGroup.of("ig-send")
 *     .fill(textareaHtml)
 *     .button(Button.of("Send").variant("control").build()).build()
 * </pre>
 *
 * <p>Item kinds: {@code boxText} (bordered __text addon), {@code fill} (raw
 * HTML in a stretching item — typically a form-control span), {@code button}
 * (a composed {@link Button}). Live compositions (dropdowns, popovers) stay
 * hand-written.
 */
@TemplateData
public final class InputGroup {

    /** One segment of the group. */
    @TemplateData
    public static final class Item {
        private final String boxText;
        private final String html;
        private final Button button;
        private final boolean disabled;

        private Item(String boxText, String html, Button button, boolean disabled) {
            this.boxText = boxText;
            this.html = html;
            this.button = button;
            this.disabled = disabled;
        }

        public boolean isBox() {
            return boxText != null;
        }

        public String boxText() {
            return boxText;
        }

        public boolean isFill() {
            return html != null;
        }

        public String html() {
            return html;
        }

        public Button button() {
            return button;
        }

        public boolean isDisabled() {
            return disabled;
        }
    }

    private final String id;
    private final List<Item> items;

    private InputGroup(Builder b) {
        this.id = b.id;
        this.items = List.copyOf(b.items);
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    public String id() {
        return id;
    }

    public List<Item> items() {
        return items;
    }

    public static final class Builder {
        private String id;
        private final List<Item> items = new ArrayList<>();

        private Builder() {
        }

        /** Bordered text addon (pf-m-box + __text span). */
        public Builder boxText(String text) {
            items.add(new Item(Objects.requireNonNull(text, "text"), null, null, false));
            return this;
        }

        /** Disabled bordered text addon. */
        public Builder boxTextDisabled(String text) {
            items.add(new Item(Objects.requireNonNull(text, "text"), null, null, true));
            return this;
        }

        /** Stretching item with raw HTML content (typically a form-control span). */
        public Builder fill(String html) {
            items.add(new Item(null, Objects.requireNonNull(html, "html"), null, false));
            return this;
        }

        /** Plain item holding a composed Button. */
        public Builder button(Button button) {
            items.add(new Item(null, null, Objects.requireNonNull(button, "button"), false));
            return this;
        }

        public InputGroup build() {
            if (items.isEmpty()) {
                throw new IllegalStateException("An input group needs at least one item");
            }
            return new InputGroup(this);
        }
    }
}
