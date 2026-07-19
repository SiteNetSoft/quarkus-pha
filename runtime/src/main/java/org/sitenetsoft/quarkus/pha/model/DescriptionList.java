package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Description list component ({@code pf-v6-c-description-list}).
 *
 * <p>Describe the term/description pairs as data and the runtime template
 * renders the full PatternFly markup. Layout modifiers append in call order,
 * so the class list reads exactly like the builder chain:
 *
 * <pre>
 * DescriptionList list = DescriptionList.builder()
 *     .id("app-details").ariaLabel("Application details")
 *     .horizontal().columns("2-col-on-lg")
 *     .group(DescriptionListGroup.of("Name", "Example"))
 *     .group(DescriptionListGroup.link("Namespace", "mary-test"))
 *     .group(DescriptionListGroup.linkButton("Pod selector", "app=MyApp", "fa:circle-plus"))
 *     .build();
 * </pre>
 *
 * Template side: <code>{#include components/data-display/description-list list=list /}</code>
 *
 * <p>Compositions beyond term/description pairs — e.g. help-text terms that
 * open a Popover — stay hand-written through the include's content slot.
 */
@TemplateData
public final class DescriptionList {

    private final String id;
    private final String ariaLabel;
    private final List<String> modifiers;
    private final String styleVars;
    private final boolean cardGroups;
    private final List<DescriptionListGroup> groups;

    private DescriptionList(Builder b) {
        this.id = b.id;
        this.ariaLabel = b.ariaLabel;
        this.modifiers = List.copyOf(b.modifiers);
        this.styleVars = b.styleVars;
        this.cardGroups = b.cardGroups;
        this.groups = List.copyOf(b.groups);
    }

    public static Builder builder() {
        return new Builder();
    }

    public String id() {
        return id;
    }

    public String ariaLabel() {
        return ariaLabel;
    }

    /** Modifier classes in call order, leading-space-joined for the class attribute. */
    public String css() {
        return modifiers.isEmpty() ? "" : " " + String.join(" ", modifiers);
    }

    /** CSS custom properties emitted verbatim into the root style attribute. */
    public String styleVars() {
        return styleVars;
    }

    /** Groups render as {@code pf-v6-c-card} (PF card display) instead of {@code __group}. */
    public boolean isCardGroups() {
        return cardGroups;
    }

    public List<DescriptionListGroup> groups() {
        return groups;
    }

    public static final class Builder {

        private String id;
        private String ariaLabel;
        private final List<String> modifiers = new ArrayList<>();
        private String styleVars;
        private boolean cardGroups;
        private final List<DescriptionListGroup> groups = new ArrayList<>();

        private Builder() {
        }

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        public Builder horizontal() {
            return modifier("pf-m-horizontal");
        }

        public Builder compact() {
            return modifier("pf-m-compact");
        }

        public Builder fluid() {
            return modifier("pf-m-fluid");
        }

        public Builder autoFit() {
            return modifier("pf-m-auto-fit");
        }

        public Builder inlineGrid() {
            return modifier("pf-m-inline-grid");
        }

        public Builder autoColumnWidths() {
            return modifier("pf-m-auto-column-widths");
        }

        public Builder fillColumns() {
            return modifier("pf-m-fill-columns");
        }

        public Builder displayLg() {
            return modifier("pf-m-display-lg");
        }

        /**
         * Column-count tokens, e.g. {@code columns("2-col")} or
         * {@code columns("2-col-on-lg", "3-col-on-xl")} — each renders as
         * {@code pf-m-<token>}.
         */
        public Builder columns(String... tokens) {
            for (String token : tokens) {
                modifier("pf-m-" + Objects.requireNonNull(token, "token"));
            }
            return this;
        }

        /**
         * Per-breakpoint orientation tokens, e.g.
         * {@code orientations("vertical-on-md", "horizontal-on-lg")} — each
         * renders as {@code pf-m-<token>}.
         */
        public Builder orientations(String... tokens) {
            return columns(tokens);
        }

        /** Extra root modifier class, verbatim. */
        public Builder modifier(String modifierClass) {
            modifiers.add(Objects.requireNonNull(modifierClass, "modifierClass"));
            return this;
        }

        /** CSS custom properties for the root style attribute, verbatim. */
        public Builder styleVars(String styleVars) {
            this.styleVars = Objects.requireNonNull(styleVars, "styleVars");
            return this;
        }

        /** Render each group as a {@code pf-v6-c-card} (PF card display). */
        public Builder cardGroups() {
            this.cardGroups = true;
            return this;
        }

        public Builder group(DescriptionListGroup group) {
            groups.add(Objects.requireNonNull(group, "group"));
            return this;
        }

        /** Plain-text shorthand for {@link DescriptionListGroup#of}. */
        public Builder group(String term, String descText) {
            return group(DescriptionListGroup.of(term, descText));
        }

        public Builder groups(List<DescriptionListGroup> groups) {
            groups.forEach(this::group);
            return this;
        }

        public DescriptionList build() {
            if (groups.isEmpty()) {
                throw new IllegalStateException("A description list needs at least one group");
            }
            return new DescriptionList(this);
        }
    }
}
