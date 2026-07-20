package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Alert component ({@code pf-v6-c-alert}).
 *
 * <pre>
 * Alert.of("Success alert").variant("success").build()
 * Alert.of("Deployment complete").variant("success")
 *     .description("Your application is live.")
 *     .actionLink("View deployment", "#").actionLink("Roll back", "#")
 *     .build()
 * </pre>
 *
 * <p>Variants drive the auto icon, screen-reader prefix and close-button
 * label exactly like the parameter shell ({@code custom} when omitted).
 * Covers inline/plain, expandable (description and action links collapse),
 * closable, truncated titles, custom icons and the action-link footer.
 * The live alert-group demos (toasts, timeouts, dynamic groups) stay
 * hand-written — see the alert demo page.
 */
@TemplateData
public final class Alert {

    /** One action link in the alert's footer — a link-variant button or anchor. */
    @TemplateData
    public static final class ActionLink {
        private final String text;
        private final String href;

        private ActionLink(String text, String href) {
            this.text = text;
            this.href = href;
        }

        public String text() {
            return text;
        }

        public String href() {
            return href;
        }
    }

    private final String id;
    private final String title;
    private final String variant;
    private final String description;
    private final boolean inline;
    private final boolean plain;
    private final boolean expandable;
    private final boolean closable;
    private final boolean truncate;
    private final String customIcon;
    private final List<ActionLink> actionLinks;

    private Alert(Builder b) {
        this.id = b.id;
        this.title = b.title;
        this.variant = b.variant;
        this.description = b.description;
        this.inline = b.inline;
        this.plain = b.plain;
        this.expandable = b.expandable;
        this.closable = b.closable;
        this.truncate = b.truncate;
        this.customIcon = b.customIcon;
        this.actionLinks = List.copyOf(b.actionLinks);
    }

    public static Builder of(String title) {
        Builder b = new Builder();
        b.title = Objects.requireNonNull(title, "title");
        return b;
    }

    public String id() {
        return id;
    }

    public String title() {
        return title;
    }

    /** Resolved variant — {@code custom} when none was set (matches PF React). */
    public String variant() {
        return variant != null ? variant : "custom";
    }

    public String description() {
        return description;
    }

    public boolean isInline() {
        return inline;
    }

    public boolean isPlain() {
        return plain;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public boolean isClosable() {
        return closable;
    }

    public boolean isTruncate() {
        return truncate;
    }

    public String customIcon() {
        return customIcon;
    }

    public boolean isHasActionLinks() {
        return !actionLinks.isEmpty();
    }

    public List<ActionLink> actionLinks() {
        return actionLinks;
    }

    public static final class Builder {

        private String id;
        private String title;
        private String variant;
        private String description;
        private boolean inline;
        private boolean plain;
        private boolean expandable;
        private boolean closable;
        private boolean truncate;
        private String customIcon;
        private final List<ActionLink> actionLinks = new ArrayList<>();

        private Builder() {
        }

        /** DOM id on the alert root. */
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /** custom (default) | info | success | warning | danger. */
        public Builder variant(String variant) {
            this.variant = variant;
            return this;
        }

        /** Explanatory paragraph below the title. */
        public Builder description(String description) {
            this.description = description;
            return this;
        }

        /** Inline treatment — sits inside content blocks without the boxed look. */
        public Builder asInline() {
            this.inline = true;
            return this;
        }

        /** Plain inline treatment (pair with {@link #asInline()}). */
        public Builder asPlain() {
            this.plain = true;
            return this;
        }

        /** Description and action links stay hidden until the alert is expanded. */
        public Builder asExpandable() {
            this.expandable = true;
            return this;
        }

        /** Close button that dismisses the alert. */
        public Builder asClosable() {
            this.closable = true;
            return this;
        }

        /** Truncate a long title to a single line. */
        public Builder asTruncated() {
            this.truncate = true;
            return this;
        }

        /** Replace the variant icon, e.g. {@code "fa:rocket"}. */
        public Builder customIcon(String customIcon) {
            this.customIcon = customIcon;
            return this;
        }

        /** Inline link button in the action footer. */
        public Builder actionLink(String text) {
            actionLinks.add(new ActionLink(Objects.requireNonNull(text, "text"), null));
            return this;
        }

        /** Inline link anchor in the action footer. */
        public Builder actionLink(String text, String href) {
            actionLinks.add(new ActionLink(Objects.requireNonNull(text, "text"),
                    Objects.requireNonNull(href, "href")));
            return this;
        }

        public Alert build() {
            return new Alert(this);
        }
    }
}
