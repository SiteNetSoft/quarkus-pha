package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Breadcrumb component ({@code pf-v6-c-breadcrumb}).
 *
 * <pre>
 * Breadcrumb.of("breadcrumb-basic")
 *     .item(BreadcrumbItem.of("Section home").href("#"))
 *     .item(BreadcrumbItem.of("Section landing").href("#").asCurrent())
 *     .build()
 * </pre>
 *
 * <p>Complements the template's existing {@code crumbs=} auto mode (used for
 * server-generated trails) with fully typed items including the heading and
 * dropdown crumb shapes.
 */
@TemplateData
public final class Breadcrumb {

    private final String id;
    private final String ariaLabel;
    private final List<BreadcrumbItem> items;

    private Breadcrumb(Builder b) {
        this.id = b.id;
        this.ariaLabel = b.ariaLabel;
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

    public String ariaLabel() {
        return ariaLabel != null ? ariaLabel : "Breadcrumb";
    }

    public List<BreadcrumbItem> items() {
        return items;
    }

    public static final class Builder {
        private String id;
        private String ariaLabel;
        private final List<BreadcrumbItem> items = new ArrayList<>();

        private Builder() {
        }

        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        public Builder item(BreadcrumbItem item) {
            items.add(Objects.requireNonNull(item, "item"));
            return this;
        }

        public Breadcrumb build() {
            if (items.isEmpty()) {
                throw new IllegalStateException("A breadcrumb needs at least one item");
            }
            return new Breadcrumb(this);
        }
    }
}
