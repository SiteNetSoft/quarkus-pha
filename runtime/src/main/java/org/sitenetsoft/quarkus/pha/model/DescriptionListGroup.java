package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * One term/description pair of a {@link DescriptionList}. The description is
 * plain text, an anchor, or a PatternFly inline link button with a leading
 * icon; decorate the term with {@link #termIcon}.
 */
@TemplateData
public final class DescriptionListGroup {

    public enum Kind { TEXT, LINK, LINK_BUTTON }

    private Kind kind;
    private String term;
    private String termIcon;
    private String descText;
    private String href;
    private String buttonIcon;

    private DescriptionListGroup() {
    }

    private DescriptionListGroup copy() {
        DescriptionListGroup g = new DescriptionListGroup();
        g.kind = kind;
        g.term = term;
        g.termIcon = termIcon;
        g.descText = descText;
        g.href = href;
        g.buttonIcon = buttonIcon;
        return g;
    }

    /** Plain text description. */
    public static DescriptionListGroup of(String term, String descText) {
        DescriptionListGroup g = new DescriptionListGroup();
        g.kind = Kind.TEXT;
        g.term = Objects.requireNonNull(term, "term");
        g.descText = Objects.requireNonNull(descText, "descText");
        return g;
    }

    /** Anchor description ({@code <a href="#">}). */
    public static DescriptionListGroup link(String term, String descText) {
        return link(term, descText, "#");
    }

    /** Anchor description with an explicit href. */
    public static DescriptionListGroup link(String term, String descText, String href) {
        DescriptionListGroup g = of(term, descText);
        g.kind = Kind.LINK;
        g.href = Objects.requireNonNull(href, "href");
        return g;
    }

    /** Inline link-button description with a leading icon, e.g. {@code "fa:circle-plus"}. */
    public static DescriptionListGroup linkButton(String term, String descText, String buttonIcon) {
        DescriptionListGroup g = of(term, descText);
        g.kind = Kind.LINK_BUTTON;
        g.buttonIcon = Objects.requireNonNull(buttonIcon, "buttonIcon");
        return g;
    }

    /** Copy with a leading term icon, e.g. {@code "fa:cube"}. */
    public DescriptionListGroup termIcon(String termIcon) {
        DescriptionListGroup g = copy();
        g.termIcon = Objects.requireNonNull(termIcon, "termIcon");
        return g;
    }

    public String term() {
        return term;
    }

    public String termIcon() {
        return termIcon;
    }

    public String descText() {
        return descText;
    }

    public boolean isLink() {
        return kind == Kind.LINK;
    }

    public boolean isLinkButton() {
        return kind == Kind.LINK_BUTTON;
    }

    public String href() {
        return href;
    }

    public String buttonIcon() {
        return buttonIcon;
    }
}
