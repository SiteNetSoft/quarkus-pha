package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * One jump link — text, target, current state and optional subsections.
 *
 * <pre>
 * JumpLinkItem.of("Top", "#top").asCurrent()
 * JumpLinkItem.of("Purgatorio", "#")
 *     .subsAriaLabel("Purgatorio subsections")
 *     .sub(JumpLinkItem.of("Ante-Purgatory", "#").asCurrentLocation())
 *     .sub(JumpLinkItem.of("The Terraces", "#"))
 * </pre>
 */
@TemplateData
public final class JumpLinkItem {

    private final String text;
    private final String href;
    private final boolean current;
    private final boolean ariaCurrent;
    private final String subsAriaLabel;
    private final List<JumpLinkItem> subs;

    private JumpLinkItem(String text, String href, boolean current, boolean ariaCurrent,
                         String subsAriaLabel, List<JumpLinkItem> subs) {
        this.text = text;
        this.href = href;
        this.current = current;
        this.ariaCurrent = ariaCurrent;
        this.subsAriaLabel = subsAriaLabel;
        this.subs = subs;
    }

    public static JumpLinkItem of(String text, String href) {
        return new JumpLinkItem(Objects.requireNonNull(text, "text"),
                Objects.requireNonNull(href, "href"), false, false, null, List.of());
    }

    /** Mark as the current section (class only). */
    public JumpLinkItem asCurrent() {
        return new JumpLinkItem(text, href, true, ariaCurrent, subsAriaLabel, subs);
    }

    /** Mark as current with {@code aria-current="location"}. */
    public JumpLinkItem asCurrentLocation() {
        return new JumpLinkItem(text, href, true, true, subsAriaLabel, subs);
    }

    /** aria-label for the nested subsection list. */
    public JumpLinkItem subsAriaLabel(String subsAriaLabel) {
        return new JumpLinkItem(text, href, current, ariaCurrent, subsAriaLabel, subs);
    }

    public JumpLinkItem sub(JumpLinkItem item) {
        List<JumpLinkItem> next = new ArrayList<>(subs);
        next.add(Objects.requireNonNull(item, "item"));
        return new JumpLinkItem(text, href, current, ariaCurrent, subsAriaLabel, List.copyOf(next));
    }

    public String text() {
        return text;
    }

    public String href() {
        return href;
    }

    public boolean isCurrent() {
        return current;
    }

    public boolean isAriaCurrent() {
        return ariaCurrent;
    }

    public String subsAriaLabel() {
        return subsAriaLabel;
    }

    public boolean isHasSubs() {
        return !subs.isEmpty();
    }

    public List<JumpLinkItem> subs() {
        return subs;
    }
}
