package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * One accordion item — toggle title and expandable body.
 *
 * <pre>
 * AccordionItem.of("acc-item-1", "Item one").body("Expandable content.")
 * </pre>
 *
 * <p>The item id derives the toggle/content ids ({@code {id}-toggle} /
 * {@code {id}-content}). Body content renders unescaped. In a single-expand
 * accordion give each item a {@link #key(String)} for the shared state.
 */
@TemplateData
public final class AccordionItem {

    private final String itemId;
    private final String title;
    private final String bodyHtml;
    private final boolean expanded;
    private final boolean fixed;
    private final String headingLevel;
    private final String key;

    private AccordionItem(String itemId, String title, String bodyHtml, boolean expanded,
                          boolean fixed, String headingLevel, String key) {
        this.itemId = itemId;
        this.title = title;
        this.bodyHtml = bodyHtml;
        this.expanded = expanded;
        this.fixed = fixed;
        this.headingLevel = headingLevel;
        this.key = key;
    }

    public static AccordionItem of(String itemId, String title) {
        return new AccordionItem(Objects.requireNonNull(itemId, "itemId"),
                Objects.requireNonNull(title, "title"), null, false, false, null, null);
    }

    /** Body content (rendered unescaped). */
    public AccordionItem body(String bodyHtml) {
        return new AccordionItem(itemId, title, bodyHtml, expanded, fixed, headingLevel, key);
    }

    /** Start open. */
    public AccordionItem asExpanded() {
        return new AccordionItem(itemId, title, bodyHtml, true, fixed, headingLevel, key);
    }

    /** Cap the expanded body height with overflow scroll. */
    public AccordionItem asFixed() {
        return new AccordionItem(itemId, title, bodyHtml, expanded, true, headingLevel, key);
    }

    /** Heading element wrapping the toggle (default h3). */
    public AccordionItem headingLevel(String headingLevel) {
        return new AccordionItem(itemId, title, bodyHtml, expanded, fixed, headingLevel, key);
    }

    /** Selection key for single-expand accordions. */
    public AccordionItem key(String key) {
        return new AccordionItem(itemId, title, bodyHtml, expanded, fixed, headingLevel, key);
    }

    public String itemId() {
        return itemId;
    }

    public String title() {
        return title;
    }

    public String bodyHtml() {
        return bodyHtml;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public boolean isFixed() {
        return fixed;
    }

    public String heading() {
        return headingLevel != null ? headingLevel : "h3";
    }

    public String key() {
        return key;
    }
}
