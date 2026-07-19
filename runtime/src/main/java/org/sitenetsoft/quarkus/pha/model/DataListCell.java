package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * One cell of a {@link DataList} row. Plain strings become text cells; the
 * static factories cover titles (bold, with an optional muted description
 * line), headings and icon cells. Refine with the {@code withX} copies for
 * width/text modifiers ({@code pf-m-flex-2}, {@code pf-m-truncate},
 * {@code pf-m-no-fill}, …) and a DOM id.
 */
@TemplateData
public final class DataListCell {

    public enum Kind { TEXT, TITLE, HEADING, ICON }

    private Kind kind;
    private String text;
    private String description;
    private String iconName;
    private List<String> modifiers = List.of();
    private String domId;

    private DataListCell() {
    }

    private DataListCell copy() {
        DataListCell c = new DataListCell();
        c.kind = kind;
        c.text = text;
        c.description = description;
        c.iconName = iconName;
        c.modifiers = modifiers;
        c.domId = domId;
        return c;
    }

    /** Plain text cell. */
    public static DataListCell text(String text) {
        DataListCell c = new DataListCell();
        c.kind = Kind.TEXT;
        c.text = Objects.requireNonNull(text, "text");
        return c;
    }

    /** Bold title cell; add a muted second line with {@link #description}. */
    public static DataListCell title(String text) {
        DataListCell c = new DataListCell();
        c.kind = Kind.TITLE;
        c.text = Objects.requireNonNull(text, "text");
        return c;
    }

    /** Heading cell ({@code <h4 class="pf-v6-c-content--h4">}). */
    public static DataListCell heading(String text) {
        DataListCell c = new DataListCell();
        c.kind = Kind.HEADING;
        c.text = Objects.requireNonNull(text, "text");
        return c;
    }

    /** Icon cell ({@code pf-m-icon}) rendering the named inline SVG, e.g. {@code "fa:code-branch"}. */
    public static DataListCell icon(String iconName) {
        DataListCell c = new DataListCell();
        c.kind = Kind.ICON;
        c.iconName = Objects.requireNonNull(iconName, "iconName");
        c.modifiers = List.of("pf-m-icon");
        return c;
    }

    /** Copy of a title cell with a muted description line under the title. */
    public DataListCell description(String description) {
        if (kind != Kind.TITLE) {
            throw new IllegalStateException("description() only applies to title cells");
        }
        DataListCell c = copy();
        c.description = Objects.requireNonNull(description, "description");
        return c;
    }

    /** Copy with an extra {@code pf-m-*} cell modifier. */
    public DataListCell withModifier(String modifierClass) {
        DataListCell c = copy();
        List<String> m = new ArrayList<>(modifiers);
        m.add(modifierClass);
        c.modifiers = List.copyOf(m);
        return c;
    }

    /** Copy carrying a DOM id. */
    public DataListCell id(String domId) {
        DataListCell c = copy();
        c.domId = Objects.requireNonNull(domId, "domId");
        return c;
    }

    public boolean isTitle() {
        return kind == Kind.TITLE;
    }

    public boolean isHeading() {
        return kind == Kind.HEADING;
    }

    public boolean isIcon() {
        return kind == Kind.ICON;
    }

    public String text() {
        return text;
    }

    public String description() {
        return description;
    }

    public String iconName() {
        return iconName;
    }

    public String domId() {
        return domId;
    }

    /** Modifier classes for the cell, or null for none. */
    public String css() {
        return modifiers.isEmpty() ? null : String.join(" ", modifiers);
    }
}
