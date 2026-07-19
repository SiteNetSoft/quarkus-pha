package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Data list component ({@code pf-v6-c-data-list}).
 *
 * <p>Describe the list as data and the runtime template renders the full
 * PatternFly markup — rows with title/description cells, leading checkboxes,
 * expand toggles with their Alpine wiring, trailing button or kebab actions,
 * and click-to-select rows:
 *
 * <pre>
 * DataList list = DataList.builder()
 *     .id("projects").ariaLabel("Projects")
 *     .item(DataList.item(DataListCell.title("Apollo").description("Backend service"),
 *             "Active", "2 hours ago"))
 *     .item(DataList.item(DataListCell.title("Mercury"), "Paused", "Yesterday")
 *             .expandsTo("Deployment paused by the release captain."))
 *     .build();
 * </pre>
 *
 * Template side: <code>{#include components/data-display/data-list list=list /}</code>
 *
 * <p>Bespoke row content (custom markup, drag handles, form controls) stays
 * hand-written through the include family's content slots.
 */
@TemplateData
public final class DataList {

    private final String id;
    private final String ariaLabel;
    private final boolean compact;
    private final boolean plain;
    private final String gridBreakpoint;
    private final List<String> modifiers;
    private final boolean clickable;
    private final String initialKey;
    private final List<DataListItem> items;

    private DataList(Builder b, List<DataListItem> items) {
        this.id = b.id;
        this.ariaLabel = b.ariaLabel;
        this.compact = b.compact;
        this.plain = b.plain;
        this.gridBreakpoint = b.gridBreakpoint;
        this.modifiers = List.copyOf(b.modifiers);
        this.clickable = b.clickable;
        this.initialKey = b.initialKey;
        this.items = List.copyOf(items);
    }

    /** Item factory for refined rows: {@code DataList.item("a", "b").expandsTo(...)}. */
    public static DataListItem item(Object... cells) {
        return DataListItem.of(cells);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String id() {
        return id;
    }

    public String ariaLabel() {
        return ariaLabel;
    }

    public boolean isCompact() {
        return compact;
    }

    public boolean isPlain() {
        return plain;
    }

    public String gridBreakpoint() {
        return gridBreakpoint;
    }

    public String extraModifiers() {
        return modifiers.isEmpty() ? "" : " " + String.join(" ", modifiers);
    }

    public boolean isClickable() {
        return clickable;
    }

    /** Initial selection key, emitted verbatim into the wrapper's x-data. */
    public String initialKey() {
        return initialKey;
    }

    public List<DataListItem> items() {
        return items;
    }

    public static final class Builder {

        private String id;
        private String ariaLabel = "Data list";
        private boolean compact;
        private boolean plain;
        private String gridBreakpoint;
        private final List<String> modifiers = new ArrayList<>();
        private boolean clickable;
        private String initialKey;
        private final List<DataListItem> items = new ArrayList<>();

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        public Builder compact() {
            this.compact = true;
            return this;
        }

        public Builder plain() {
            this.plain = true;
            return this;
        }

        /** Grid modifier suffix, e.g. {@code "sm"} renders {@code pf-m-grid-sm}. */
        public Builder gridBreakpoint(String breakpoint) {
            this.gridBreakpoint = Objects.requireNonNull(breakpoint, "breakpoint");
            return this;
        }

        /** Extra root modifier class, e.g. {@code pf-m-grid} or {@code pf-m-grid-none}. */
        public Builder modifier(String modifierClass) {
            modifiers.add(modifierClass);
            return this;
        }

        /**
         * Rows select on click/Enter; pass the initially selected key
         * (verbatim JS literal, matching the item keys).
         */
        public Builder clickable(String initialKey) {
            this.clickable = true;
            this.initialKey = Objects.requireNonNull(initialKey, "initialKey");
            return this;
        }

        public Builder item(DataListItem item) {
            items.add(Objects.requireNonNull(item, "item"));
            return this;
        }

        public DataList build() {
            if (items.isEmpty()) {
                throw new IllegalStateException("A data list needs at least one item");
            }
            Objects.requireNonNull(ariaLabel, "ariaLabel");
            List<DataListItem> resolved = new ArrayList<>();
            int n = 0;
            for (DataListItem item : items) {
                n++;
                if (clickable && item.key() == null) {
                    throw new IllegalStateException("Clickable lists need a key() on every item (item " + n + ")");
                }
                resolved.add(item.resolved(n));
            }
            return new DataList(this, resolved);
        }
    }
}
