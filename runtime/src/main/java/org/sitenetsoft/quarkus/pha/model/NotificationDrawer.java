package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Notification drawer ({@code pf-v6-c-notification-drawer}).
 *
 * <pre>
 * NotificationDrawer.of("nd-basic").header("3 unread").style("max-width: 480px")
 *     .item(Item.of("info", "Build completed").description("…").timestamp("2 minutes ago"))
 *     .item(Item.of("success", "Deploy succeeded").read().timestamp("1 hour ago")).build()
 * NotificationDrawer.of("nd-groups").header("3 unread").ariaLabel("Grouped notifications")
 *     .group("Production alerts", true, "2", item1, item2)
 *     .group("Staging alerts", false, "1", item3).build()
 * </pre>
 *
 * <p>Flat items or expandable groups (group open state generates g1/g2… Alpine
 * vars on the root). Omit {@code header} for the lightweight variant.
 */
@TemplateData
public final class NotificationDrawer {

    /** One notification. */
    @TemplateData
    public static final class Item {
        private final String variant;
        private final String title;
        private final String description;
        private final String timestamp;
        private final boolean read;

        private Item(String variant, String title, String description, String timestamp, boolean read) {
            this.variant = variant;
            this.title = title;
            this.description = description;
            this.timestamp = timestamp;
            this.read = read;
        }

        public static Item of(String variant, String title) {
            return new Item(Objects.requireNonNull(variant, "variant"),
                    Objects.requireNonNull(title, "title"), null, null, false);
        }

        public Item description(String description) {
            return new Item(variant, title, description, timestamp, read);
        }

        public Item timestamp(String timestamp) {
            return new Item(variant, title, description, timestamp, read);
        }

        public Item read() {
            return new Item(variant, title, description, timestamp, true);
        }

        public String variant() {
            return variant;
        }

        public String title() {
            return title;
        }

        public String description() {
            return description;
        }

        public String timestamp() {
            return timestamp;
        }

        public boolean isRead() {
            return read;
        }

        /** Variant status icon. */
        public String displayIcon() {
            return switch (variant) {
                case "warning" -> "fa:exclamation-triangle";
                case "success" -> "fa:circle-check";
                case "danger" -> "fa:circle-exclamation";
                default -> "fa:circle-info";
            };
        }
    }

    /** One expandable group. */
    @TemplateData
    public static final class Group {
        private final String title;
        private final String count;
        private final String openVar;
        private final List<Item> items;

        private Group(String title, String count, String openVar, List<Item> items) {
            this.title = title;
            this.count = count;
            this.openVar = openVar;
            this.items = items;
        }

        public String title() {
            return title;
        }

        public String count() {
            return count;
        }

        public String openVar() {
            return openVar;
        }

        public List<Item> items() {
            return items;
        }
    }

    private final String id;
    private final String headerStatus;
    private final String titleText;
    private final String ariaLabel;
    private final String style;
    private final List<Item> items;
    private final List<Group> groups;
    private final String xData;

    private NotificationDrawer(Builder b) {
        this.id = b.id;
        this.headerStatus = b.headerStatus;
        this.titleText = b.titleText;
        this.ariaLabel = b.ariaLabel;
        this.style = b.style;
        this.items = List.copyOf(b.items);

        List<Group> resolved = new ArrayList<>();
        StringBuilder xd = new StringBuilder();
        for (int i = 0; i < b.groupTitles.size(); i++) {
            String var = "g" + (i + 1);
            if (i > 0) {
                xd.append(", ");
            }
            xd.append(var).append(": ").append(b.groupOpens.get(i));
            resolved.add(new Group(b.groupTitles.get(i), b.groupCounts.get(i), var, b.groupItems.get(i)));
        }
        this.groups = List.copyOf(resolved);
        this.xData = groups.isEmpty() ? null : "{ " + xd + " }";
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    public String id() {
        return id;
    }

    public boolean isHasHeader() {
        return headerStatus != null || titleText != null;
    }

    public String titleText() {
        return titleText != null ? titleText : "Notifications";
    }

    public String headerStatus() {
        return headerStatus;
    }

    public String ariaLabelResolved() {
        if (ariaLabel != null) {
            return ariaLabel;
        }
        if (titleText != null) {
            return titleText;
        }
        return "Notifications";
    }

    public String style() {
        return style;
    }

    public List<Item> items() {
        return items;
    }

    public boolean isGrouped() {
        return !groups.isEmpty();
    }

    public List<Group> groups() {
        return groups;
    }

    public String xData() {
        return xData;
    }

    public static final class Builder {
        private String id;
        private String headerStatus;
        private String titleText;
        private String ariaLabel;
        private String style;
        private final List<Item> items = new ArrayList<>();
        private final List<String> groupTitles = new ArrayList<>();
        private final List<Boolean> groupOpens = new ArrayList<>();
        private final List<String> groupCounts = new ArrayList<>();
        private final List<List<Item>> groupItems = new ArrayList<>();

        private Builder() {
        }

        /** Render the header with this status line (e.g. "3 unread"). */
        public Builder header(String headerStatus) {
            this.headerStatus = headerStatus;
            return this;
        }

        public Builder titleText(String titleText) {
            this.titleText = titleText;
            return this;
        }

        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        /** Inline style on the drawer root (demo chrome). */
        public Builder style(String style) {
            this.style = style;
            return this;
        }

        public Builder item(Item item) {
            items.add(Objects.requireNonNull(item, "item"));
            return this;
        }

        /** Expandable group (open state generates g1/g2… Alpine vars). */
        public Builder group(String title, boolean open, String count, Item... groupItemsArr) {
            groupTitles.add(Objects.requireNonNull(title, "title"));
            groupOpens.add(open);
            groupCounts.add(count);
            groupItems.add(List.of(groupItemsArr));
            return this;
        }

        public NotificationDrawer build() {
            if (items.isEmpty() && groupTitles.isEmpty()) {
                throw new IllegalStateException("A notification drawer needs items or groups");
            }
            return new NotificationDrawer(this);
        }
    }
}
