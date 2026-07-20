package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Page shell ({@code pf-v6-c-page}) — masthead, optional
 * collapsible sidebar, and ordered main-area entries.
 *
 * <pre>
 * Page.of("pg-basic").style("min-height: 360px")
 *     .brand("App name")
 *     .section(Page.Section.of(welcomeHtml).bodyStyle("padding: 1rem")).build()
 * Page.of("pg-nav").brand("Logo").toggle("Vertical nav demo").toolbarText("header-tools")
 *     .sidebarNav("Vertical nav demo secondary",
 *         Page.NavItem.of("Nav item 1").current(), Page.NavItem.of("Nav item 2"))
 *     .section(...).build()
 * </pre>
 *
 * <p>{@code toggle(...)} generates the sidebarOpen Alpine state and burger;
 * groups can lead with a composed {@link Breadcrumb}; sections take raw HTML
 * with verbatim pf-m-* modifiers; {@code scrollableMain()} makes the main area
 * its own scroll region (sticky-section pattern).
 */
@TemplateData
public final class Page {

    /** One nav link. */
    @TemplateData
    public static final class NavItem {
        private final String text;
        private final boolean current;

        private NavItem(String text, boolean current) {
            this.text = text;
            this.current = current;
        }

        public static NavItem of(String text) {
            return new NavItem(Objects.requireNonNull(text, "text"), false);
        }

        public NavItem current() {
            return new NavItem(text, true);
        }

        public String text() {
            return text;
        }

        public boolean isCurrent() {
            return current;
        }
    }

    /** One main-area section ({@code __main-section}). */
    @TemplateData
    public static final class Section {
        private final String html;
        private final String modifiers;
        private final String ariaLabel;
        private final String ariaLabelledBy;
        private final String bodyStyle;

        private Section(String html, String modifiers, String ariaLabel, String ariaLabelledBy, String bodyStyle) {
            this.html = html;
            this.modifiers = modifiers;
            this.ariaLabel = ariaLabel;
            this.ariaLabelledBy = ariaLabelledBy;
            this.bodyStyle = bodyStyle;
        }

        public static Section of(String html) {
            return new Section(Objects.requireNonNull(html, "html"), null, null, null, null);
        }

        /** Verbatim pf-m-* tokens with leading space handled here. */
        public Section modifiers(String modifiers) {
            return new Section(html, modifiers, ariaLabel, ariaLabelledBy, bodyStyle);
        }

        public Section ariaLabel(String ariaLabel) {
            return new Section(html, modifiers, ariaLabel, ariaLabelledBy, bodyStyle);
        }

        public Section ariaLabelledBy(String ariaLabelledBy) {
            return new Section(html, modifiers, ariaLabel, ariaLabelledBy, bodyStyle);
        }

        public Section bodyStyle(String bodyStyle) {
            return new Section(html, modifiers, ariaLabel, ariaLabelledBy, bodyStyle);
        }

        public String html() {
            return html;
        }

        public String modifierSuffix() {
            return modifiers != null ? " " + modifiers : "";
        }

        public String sectionAriaLabel() {
            return ariaLabel;
        }

        public String sectionAriaLabelledBy() {
            return ariaLabelledBy;
        }

        public String bodyStyleValue() {
            return bodyStyle;
        }
    }

    /** One ordered main-area entry — section, group, or a typed region. */
    @TemplateData
    public static final class Entry {
        private final Section section;
        private final boolean groupPlain;
        private final Breadcrumb groupBreadcrumb;
        private final List<Section> groupSections;
        private final String regionKind;
        private final String regionAriaLabel;
        private final String regionHtml;

        private Entry(Section section, boolean groupPlain, Breadcrumb groupBreadcrumb,
                      List<Section> groupSections, String regionKind, String regionAriaLabel, String regionHtml) {
            this.section = section;
            this.groupPlain = groupPlain;
            this.groupBreadcrumb = groupBreadcrumb;
            this.groupSections = groupSections;
            this.regionKind = regionKind;
            this.regionAriaLabel = regionAriaLabel;
            this.regionHtml = regionHtml;
        }

        public boolean isSection() {
            return section != null;
        }

        public Section section() {
            return section;
        }

        public boolean isGroup() {
            return groupSections != null;
        }

        public boolean isGroupPlain() {
            return groupPlain;
        }

        public Breadcrumb groupBreadcrumb() {
            return groupBreadcrumb;
        }

        public List<Section> groupSections() {
            return groupSections;
        }

        public boolean isRegion() {
            return regionKind != null;
        }

        public String regionKind() {
            return regionKind;
        }

        public String regionAriaLabel() {
            return regionAriaLabel;
        }

        public String regionHtml() {
            return regionHtml;
        }
    }

    private final String id;
    private final String style;
    private final String brand;
    private final boolean mastheadInline;
    private final String toggleAriaLabel;
    private final String toolbarText;
    private final String horizontalNavAriaLabel;
    private final List<NavItem> horizontalNav;
    private final String sidebarNavAriaLabel;
    private final List<NavItem> sidebarNav;
    private final boolean sidebarNavFill;
    private final String sidebarContextSelector;
    private final boolean scrollableMain;
    private final List<Entry> entries;

    private Page(Builder b) {
        this.id = b.id;
        this.style = b.style;
        this.brand = b.brand;
        this.mastheadInline = b.mastheadInline;
        this.toggleAriaLabel = b.toggleAriaLabel;
        this.toolbarText = b.toolbarText;
        this.horizontalNavAriaLabel = b.horizontalNavAriaLabel;
        this.horizontalNav = b.horizontalNav;
        this.sidebarNavAriaLabel = b.sidebarNavAriaLabel;
        this.sidebarNav = b.sidebarNav;
        this.sidebarNavFill = b.sidebarNavFill;
        this.sidebarContextSelector = b.sidebarContextSelector;
        this.scrollableMain = b.scrollableMain;
        this.entries = List.copyOf(b.entries);
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    public String id() {
        return id;
    }

    public String style() {
        return style;
    }

    public String brand() {
        return brand;
    }

    public boolean isMastheadInline() {
        return mastheadInline;
    }

    public boolean isHasToggle() {
        return toggleAriaLabel != null;
    }

    public String toggleAriaLabel() {
        return toggleAriaLabel;
    }

    public String toolbarText() {
        return toolbarText;
    }

    public boolean isHasHorizontalNav() {
        return horizontalNav != null;
    }

    public String horizontalNavAriaLabel() {
        return horizontalNavAriaLabel;
    }

    public List<NavItem> horizontalNav() {
        return horizontalNav;
    }

    public boolean isHasSidebar() {
        return sidebarNav != null;
    }

    public String sidebarNavAriaLabel() {
        return sidebarNavAriaLabel;
    }

    public List<NavItem> sidebarNav() {
        return sidebarNav;
    }

    public boolean isSidebarNavFill() {
        return sidebarNavFill;
    }

    public String sidebarContextSelector() {
        return sidebarContextSelector;
    }

    public boolean isScrollableMain() {
        return scrollableMain;
    }

    public List<Entry> entries() {
        return entries;
    }

    public static final class Builder {
        private String id;
        private String style;
        private String brand;
        private boolean mastheadInline;
        private String toggleAriaLabel;
        private String toolbarText;
        private String horizontalNavAriaLabel;
        private List<NavItem> horizontalNav;
        private String sidebarNavAriaLabel;
        private List<NavItem> sidebarNav;
        private boolean sidebarNavFill;
        private String sidebarContextSelector;
        private boolean scrollableMain;
        private final List<Entry> entries = new ArrayList<>();

        private Builder() {
        }

        /** Inline style on the page root (demo sizing). */
        public Builder style(String style) {
            this.style = style;
            return this;
        }

        /** Brand text rendered as a strong in the masthead. */
        public Builder brand(String brand) {
            this.brand = brand;
            return this;
        }

        /** pf-m-display-inline on the masthead (horizontal-nav layout). */
        public Builder mastheadInline() {
            this.mastheadInline = true;
            return this;
        }

        /** Burger toggle wired to generated sidebarOpen Alpine state. */
        public Builder toggle(String ariaLabel) {
            this.toggleAriaLabel = ariaLabel;
            return this;
        }

        /** Masthead content: a single-item toolbar with this text. */
        public Builder toolbarText(String toolbarText) {
            this.toolbarText = toolbarText;
            return this;
        }

        /** Masthead content: horizontal nav of items. */
        public Builder horizontalNav(String ariaLabel, NavItem... items) {
            this.horizontalNavAriaLabel = ariaLabel;
            this.horizontalNav = List.of(items);
            return this;
        }

        /** Collapsible page sidebar holding one nav body. */
        public Builder sidebarNav(String ariaLabel, NavItem... items) {
            this.sidebarNavAriaLabel = ariaLabel;
            this.sidebarNav = List.of(items);
            return this;
        }

        /** pf-m-fill on the nav sidebar body (multiple-body pattern). */
        public Builder sidebarNavFill() {
            this.sidebarNavFill = true;
            return this;
        }

        /** Leading pf-m-context-selector sidebar body with this text. */
        public Builder sidebarContextSelector(String text) {
            this.sidebarContextSelector = text;
            return this;
        }

        /** Main area scrolls itself (tabindex 0 + overflow-y) — sticky-section pattern. */
        public Builder scrollableMain() {
            this.scrollableMain = true;
            return this;
        }

        public Builder section(Section section) {
            entries.add(new Entry(Objects.requireNonNull(section, "section"), false, null, null, null, null, null));
            return this;
        }

        /** __main-group of sections, optionally led by a composed Breadcrumb. */
        public Builder group(boolean plain, Breadcrumb breadcrumb, Section... sections) {
            entries.add(new Entry(null, plain, breadcrumb, List.of(sections), null, null, null));
            return this;
        }

        /** Typed main region: subnav | tabs | breadcrumb | wizard (raw HTML content). */
        public Builder region(String kind, String ariaLabel, String html) {
            entries.add(new Entry(null, false, null, null,
                    Objects.requireNonNull(kind, "kind"), ariaLabel, Objects.requireNonNull(html, "html")));
            return this;
        }

        public Page build() {
            if (entries.isEmpty()) {
                throw new IllegalStateException("A page needs at least one main entry");
            }
            return new Page(this);
        }
    }
}
