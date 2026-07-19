package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Simple list component ({@code pf-v6-c-simple-list}).
 *
 * <p>Describe the list as data and the runtime template renders the full
 * PatternFly markup — flat or titled-section lists of anchor/button entries,
 * with click-to-select Alpine wiring on selectable lists:
 *
 * <pre>
 * SimpleList list = SimpleList.builder()
 *     .id("folders").selectable("'inbox'")
 *     .item(SimpleListItem.link("Inbox", "#").key("'inbox'"))
 *     .item(SimpleListItem.link("Sent", "#").key("'sent'"))
 *     .build();
 * </pre>
 *
 * Template side: <code>{#include components/data-display/simple-list list=list /}</code>
 *
 * <p>A list is grouped (sections only) or flat (items only) — mixing throws
 * at {@code build()}.
 */
@TemplateData
public final class SimpleList {

    private final String id;
    private final boolean selectable;
    private final String initialKey;
    private final List<SimpleListItem> items;
    private final List<SimpleListSection> sections;

    private SimpleList(Builder b) {
        this.id = b.id;
        this.selectable = b.selectable;
        this.initialKey = b.initialKey;
        this.items = List.copyOf(b.items);
        this.sections = List.copyOf(b.sections);
    }

    /** Section factory for grouped lists: {@code SimpleList.section("Account", item, item)}. */
    public static SimpleListSection section(String title, SimpleListItem... items) {
        return new SimpleListSection(title, List.of(items));
    }

    public static Builder builder() {
        return new Builder();
    }

    public String id() {
        return id;
    }

    public boolean isSelectable() {
        return selectable;
    }

    /** Initial selection key, emitted verbatim into the root's x-data. */
    public String initialKey() {
        return initialKey;
    }

    public boolean isGrouped() {
        return !sections.isEmpty();
    }

    public List<SimpleListItem> items() {
        return items;
    }

    public List<SimpleListSection> sections() {
        return sections;
    }

    public static final class Builder {

        private String id;
        private boolean selectable;
        private String initialKey;
        private final List<SimpleListItem> items = new ArrayList<>();
        private final List<SimpleListSection> sections = new ArrayList<>();

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        /**
         * Items select on click; pass the initially selected key
         * (verbatim JS literal, matching the item keys).
         */
        public Builder selectable(String initialKey) {
            this.selectable = true;
            this.initialKey = Objects.requireNonNull(initialKey, "initialKey");
            return this;
        }

        public Builder item(SimpleListItem item) {
            items.add(Objects.requireNonNull(item, "item"));
            return this;
        }

        public Builder section(SimpleListSection section) {
            sections.add(Objects.requireNonNull(section, "section"));
            return this;
        }

        public SimpleList build() {
            if (items.isEmpty() && sections.isEmpty()) {
                throw new IllegalStateException("A simple list needs at least one item or section");
            }
            if (!items.isEmpty() && !sections.isEmpty()) {
                throw new IllegalStateException("A simple list is grouped (sections only) or flat (items only), not both");
            }
            if (selectable) {
                List<SimpleListItem> all = new ArrayList<>(items);
                sections.forEach(s -> all.addAll(s.items()));
                int n = 0;
                for (SimpleListItem item : all) {
                    n++;
                    if (item.key() == null) {
                        throw new IllegalStateException("Selectable lists need a key() on every item (item " + n + ")");
                    }
                }
            }
            return new SimpleList(this);
        }
    }
}
