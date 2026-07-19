package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * One entry of a {@link SimpleList}. Anchors come from {@link #link}, buttons
 * from {@link #button}. On a selectable list every item needs a {@link #key}
 * (verbatim JS literal); on a static list mark the active entry with
 * {@link #current}.
 */
@TemplateData
public final class SimpleListItem {

    private String text;
    private String href;
    private String key;
    private boolean current;

    private SimpleListItem() {
    }

    private SimpleListItem copy() {
        SimpleListItem i = new SimpleListItem();
        i.text = text;
        i.href = href;
        i.key = key;
        i.current = current;
        return i;
    }

    /** Anchor item ({@code <a href=...>}). */
    public static SimpleListItem link(String text, String href) {
        SimpleListItem i = new SimpleListItem();
        i.text = Objects.requireNonNull(text, "text");
        i.href = Objects.requireNonNull(href, "href");
        return i;
    }

    /** Button item ({@code <button type="button">}). */
    public static SimpleListItem button(String text) {
        SimpleListItem i = new SimpleListItem();
        i.text = Objects.requireNonNull(text, "text");
        return i;
    }

    /** Copy carrying the selection key — a verbatim JS literal, e.g. {@code "'inbox'"} or {@code "1"}. */
    public SimpleListItem key(String verbatimKey) {
        SimpleListItem i = copy();
        i.key = Objects.requireNonNull(verbatimKey, "verbatimKey");
        return i;
    }

    /**
     * Copy statically marked current ({@code pf-m-current}); for non-selectable
     * lists. Named {@code asCurrent} — a no-arg {@code current()} would shadow
     * the {@code isCurrent()} property in Qute resolution.
     */
    public SimpleListItem asCurrent() {
        SimpleListItem i = copy();
        i.current = true;
        return i;
    }

    public String text() {
        return text;
    }

    public String href() {
        return href;
    }

    public boolean isAnchor() {
        return href != null;
    }

    public String key() {
        return key;
    }

    public boolean isCurrent() {
        return current;
    }
}
