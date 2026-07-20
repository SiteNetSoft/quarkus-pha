package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * Typed model for the Notification badge ({@code pf-v6-c-button pf-m-stateful}).
 *
 * <pre>
 * NotificationBadge.of("nb-unread", "3 unread notifications").variant("unread").count("3").build()
 * NotificationBadge.of("nb-read", "Notifications, all read").build()
 * NotificationBadge.of("nb-attn", "Needs attention").variant("attention").count("!").build()
 * </pre>
 *
 * <p>PF v6 renders this as a stateful icon button; the count renders as a
 * badge inside {@code __count} (unread styling unless the variant is read).
 */
@TemplateData
public final class NotificationBadge {

    private final String id;
    private final String ariaLabel;
    private final String variant;
    private final String count;

    private NotificationBadge(Builder b) {
        this.id = b.id;
        this.ariaLabel = b.ariaLabel;
        this.variant = b.variant;
        this.count = b.count;
    }

    public static Builder of(String id, String ariaLabel) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        b.ariaLabel = Objects.requireNonNull(ariaLabel, "ariaLabel");
        return b;
    }

    public String id() {
        return id;
    }

    public String ariaLabel() {
        return ariaLabel;
    }

    public String variant() {
        return variant != null ? variant : "read";
    }

    public boolean isRead() {
        return "read".equals(variant());
    }

    public String count() {
        return count;
    }

    public static final class Builder {
        private String id;
        private String ariaLabel;
        private String variant;
        private String count;

        private Builder() {
        }

        /** read (default) | unread | attention. */
        public Builder variant(String variant) {
            this.variant = variant;
            return this;
        }

        /** Visible count text (e.g. "3", "!"). */
        public Builder count(String count) {
            this.count = count;
            return this;
        }

        public NotificationBadge build() {
            return new NotificationBadge(this);
        }
    }
}
