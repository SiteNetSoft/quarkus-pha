package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Jump links component ({@code pf-v6-c-jump-links}).
 *
 * <pre>
 * JumpLinks.of("jl-horizontal")
 *     .item(JumpLinkItem.of("Top", "#top").asCurrent())
 *     .item(JumpLinkItem.of("Middle", "#middle"))
 *     .build()
 * </pre>
 *
 * <p>Plain lists render bare anchors; {@link Builder#buttonLinks()} switches
 * to the button-link anatomy used by the labeled, subsection and expandable
 * variants. {@link Builder#label(String)} adds the header label,
 * {@link Builder#expandable(String, boolean)} the direct-toggle expandable
 * shape, and {@link Builder#expandableResponsive(String, String)} the
 * breakpoint-driven toggle-plus-label shape.
 */
@TemplateData
public final class JumpLinks {

    private final String id;
    private final String ariaLabel;
    private final boolean vertical;
    private final boolean center;
    private final boolean buttonLinks;
    private final String label;
    private final String style;
    private final String expandableMode; // null | "simple" | "responsive"
    private final String expandableModifiers;
    private final String toggleText;
    private final boolean startOpen;
    private final List<JumpLinkItem> items;

    private JumpLinks(Builder b) {
        this.id = b.id;
        this.ariaLabel = b.ariaLabel;
        this.vertical = b.vertical;
        this.center = b.center;
        this.buttonLinks = b.buttonLinks;
        this.label = b.label;
        this.style = b.style;
        this.expandableMode = b.expandableMode;
        this.expandableModifiers = b.expandableModifiers;
        this.toggleText = b.toggleText;
        this.startOpen = b.startOpen;
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
        return ariaLabel != null ? ariaLabel : "Jump to section";
    }

    public boolean isVertical() {
        return vertical;
    }

    public boolean isCenter() {
        return center;
    }

    /** True when links use the button-link anatomy. */
    public boolean isButtonLinks() {
        return buttonLinks || label != null || expandableMode != null;
    }

    public String label() {
        return label;
    }

    public String style() {
        return style;
    }

    public boolean isExpandable() {
        return expandableMode != null;
    }

    public boolean isExpandableSimple() {
        return "simple".equals(expandableMode);
    }

    public boolean isExpandableResponsive() {
        return "responsive".equals(expandableMode);
    }

    public String expandableModifiers() {
        return expandableModifiers;
    }

    public String toggleText() {
        return toggleText;
    }

    public boolean isStartOpen() {
        return startOpen;
    }

    /** True when the list sits inside the __main wrapper (labeled or expandable shapes). */
    public boolean isHasMain() {
        return label != null || expandableMode != null;
    }

    public List<JumpLinkItem> items() {
        return items;
    }

    public static final class Builder {

        private String id;
        private String ariaLabel;
        private boolean vertical;
        private boolean center;
        private boolean buttonLinks;
        private String label;
        private String style;
        private String expandableMode;
        private String expandableModifiers;
        private String toggleText;
        private boolean startOpen;
        private final List<JumpLinkItem> items = new ArrayList<>();

        private Builder() {
        }

        /** Accessible name of the nav (default "Jump to section"). */
        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        public Builder vertical() {
            this.vertical = true;
            return this;
        }

        public Builder center() {
            this.center = true;
            return this;
        }

        /** Button-link anatomy without a label (subsection variants). */
        public Builder buttonLinks() {
            this.buttonLinks = true;
            return this;
        }

        /** Header label above the list (implies the __main wrapper and button links). */
        public Builder label(String label) {
            this.label = label;
            return this;
        }

        /** Inline style on the nav root, e.g. {@code "max-width: 240px"}. */
        public Builder style(String style) {
            this.style = style;
            return this;
        }

        /** Expandable with the direct button toggle; {@code startOpen} sets the initial state. */
        public Builder expandable(String toggleText, boolean startOpen) {
            this.expandableMode = "simple";
            this.toggleText = Objects.requireNonNull(toggleText, "toggleText");
            this.startOpen = startOpen;
            return this;
        }

        /**
         * Breakpoint-driven expandable (toggle div + optional label); the modifiers are
         * the pf-m-(non-)expandable-on-* classes, verbatim.
         */
        public Builder expandableResponsive(String modifiers, String toggleText) {
            this.expandableMode = "responsive";
            this.expandableModifiers = Objects.requireNonNull(modifiers, "modifiers");
            this.toggleText = Objects.requireNonNull(toggleText, "toggleText");
            return this;
        }

        public Builder item(JumpLinkItem item) {
            items.add(Objects.requireNonNull(item, "item"));
            return this;
        }

        public Builder items(JumpLinkItem... items) {
            for (JumpLinkItem item : items) {
                item(item);
            }
            return this;
        }

        public JumpLinks build() {
            if (items.isEmpty()) {
                throw new IllegalStateException("Jump links need at least one item");
            }
            return new JumpLinks(this);
        }
    }
}
