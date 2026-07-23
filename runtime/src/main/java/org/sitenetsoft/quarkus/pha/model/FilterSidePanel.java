package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Filter side panel ({@code filter-panel-pf}, PatternFly
 * catalog-view extension) — grouped checkbox filters with optional counts.
 *
 * <pre>
 * FilterSidePanel.of("fsp-demo")
 *     .category(FilterSidePanel.Category.of("Categories")
 *         .item(FilterSidePanel.Item.of("Databases").id("fsp-cat-databases").count(42))
 *         .item(FilterSidePanel.Item.of("Runtimes").id("fsp-cat-runtimes").count(15).checked()))
 *     .build();
 * </pre>
 */
@TemplateData
public final class FilterSidePanel {

    private final String id;
    private final List<Category> categories;

    private FilterSidePanel(Builder b) {
        this.id = b.id;
        List<Category> built = new ArrayList<>(b.categories.size());
        for (Category.Builder cb : b.categories) {
            built.add(cb.buildCategory());
        }
        this.categories = List.copyOf(built);
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = id;
        return b;
    }

    public String id() {
        return id;
    }

    public List<Category> categories() {
        return categories;
    }

    /** One filter category: a heading plus its checkbox items. */
    @TemplateData
    public record Category(String title, String headingTag, List<Item> items) {

        public static Builder of(String title) {
            Builder b = new Builder();
            b.title = Objects.requireNonNull(title, "title");
            return b;
        }

        public static final class Builder {
            private String title;
            private String headingLevel;
            private final List<Item> items = new ArrayList<>();

            private Builder() {
            }

            /** h3 (default) | h4 | ... on the category title. */
            public Builder headingLevel(String headingLevel) {
                this.headingLevel = headingLevel;
                return this;
            }

            public Builder item(Item.Builder item) {
                this.items.add(item.buildItem());
                return this;
            }

            Category buildCategory() {
                return new Category(title, headingLevel != null ? headingLevel : "h3", List.copyOf(items));
            }
        }
    }

    /** One checkbox filter row with an optional count badge. */
    @TemplateData
    public record Item(String id, String label, String count, boolean checked, boolean disabled) {

        public static Builder of(String label) {
            Builder b = new Builder();
            b.label = Objects.requireNonNull(label, "label");
            return b;
        }

        public static final class Builder {
            private String id;
            private String label;
            private String count;
            private boolean checked;
            private boolean disabled;

            private Builder() {
            }

            public Builder id(String id) {
                this.id = id;
                return this;
            }

            public Builder count(int count) {
                this.count = Integer.toString(count);
                return this;
            }

            public Builder checked() {
                this.checked = true;
                return this;
            }

            public Builder disabled() {
                this.disabled = true;
                return this;
            }

            Item buildItem() {
                return new Item(id, label, count, checked, disabled);
            }
        }
    }

    public static final class Builder {
        private String id;
        private final List<Category.Builder> categories = new ArrayList<>();

        private Builder() {
        }

        public Builder category(Category.Builder category) {
            this.categories.add(category);
            return this;
        }

        public FilterSidePanel build() {
            return new FilterSidePanel(this);
        }
    }
}
