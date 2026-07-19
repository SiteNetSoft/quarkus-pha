package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.List;
import java.util.Objects;

/**
 * One titled section of a grouped {@link SimpleList}. Built via
 * {@code SimpleList.section(title, items...)}.
 */
@TemplateData
public final class SimpleListSection {

    private final String title;
    private final List<SimpleListItem> items;

    SimpleListSection(String title, List<SimpleListItem> items) {
        this.title = Objects.requireNonNull(title, "title");
        if (items.isEmpty()) {
            throw new IllegalArgumentException("Section \"" + title + "\" needs at least one item");
        }
        this.items = List.copyOf(items);
    }

    public String title() {
        return title;
    }

    public List<SimpleListItem> items() {
        return items;
    }
}
