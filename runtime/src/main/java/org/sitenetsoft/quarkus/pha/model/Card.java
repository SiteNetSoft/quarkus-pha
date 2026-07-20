package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Card component ({@code pf-v6-c-card}).
 *
 * <pre>
 * Card.of("cd-basic")
 *     .title("Project Apollo")
 *     .body("Ship the new dashboard by end of quarter.")
 *     .footer("Last updated 2 hours ago")
 *     .build()
 * </pre>
 *
 * <p>Sections render in insertion order (title, subtitle-in-header, bodies
 * with optional no-fill, footer, dividers, expandable content), with body,
 * footer and title content rendered unescaped so trusted markup like
 * {@code <code>} or Alpine spans is allowed. {@link Header} models the
 * header region: expand toggle, kebab/checkbox actions, selectable-actions
 * inputs (checkbox, radio, clickable button/anchor) and the header-main
 * brand/icon/title/subtitle stack. Selection state binds to chrome-owned
 * Alpine variables via {@link Builder#selectedExpr(String)}. The live
 * modifier playground example stays hand-written — see the card demo page.
 */
@TemplateData
public final class Card {

    /** One ordered card section. */
    @TemplateData
    public static final class Section {
        private final String kind; // title | body | footer | divider | expandable
        private final String html;
        private final String id;
        private final String headingLevel;
        private final boolean noFill;
        private final String footerHtml;

        private Section(String kind, String html, String id, String headingLevel,
                        boolean noFill, String footerHtml) {
            this.kind = kind;
            this.html = html;
            this.id = id;
            this.headingLevel = headingLevel;
            this.noFill = noFill;
            this.footerHtml = footerHtml;
        }

        public String kind() {
            return kind;
        }

        public String html() {
            return html;
        }

        public String id() {
            return id;
        }

        public String headingLevel() {
            return headingLevel != null ? headingLevel : "h2";
        }

        public boolean isNoFill() {
            return noFill;
        }

        public String footerHtml() {
            return footerHtml;
        }
    }

    /** The card header region — toggle, actions, selectable actions and header-main. */
    @TemplateData
    public static final class Header {
        private boolean wrap;
        private boolean expandableToggle;
        private String kebabAria;
        private String checkboxId;
        private String checkboxLabelledBy;
        private String checkboxAriaLabel;
        private String selKind; // null | check | radio | clickButton | clickAnchor
        private String selId;
        private String selLabelledBy;
        private String selAriaLabel;
        private String selXModel;
        private String selName;
        private String selValue;
        private String selClickExpr;
        private String selHref;
        private boolean selDisabled;
        private String brandSrc;
        private String brandAlt;
        private String brandHeight;
        private boolean brandMainFlag;
        private String mainIcon;
        private String mainTitleHtml;
        private String mainTitleId;
        private String mainSubtitle;
        private String titleHtml;
        private String titleId;

        private Header() {
        }

        public static Header create() {
            return new Header();
        }

        public Header asWrapped() {
            this.wrap = true;
            return this;
        }

        /** The expand toggle (pair with the card's expandable() flag). */
        public Header withExpandToggle() {
            this.expandableToggle = true;
            return this;
        }

        /** Kebab button in the actions area. */
        public Header kebab(String ariaLabel) {
            this.kebabAria = Objects.requireNonNull(ariaLabel, "ariaLabel");
            return this;
        }

        /** Standalone checkbox in the actions area, labelled by the given element id. */
        public Header checkbox(String id, String labelledBy) {
            this.checkboxId = Objects.requireNonNull(id, "id");
            this.checkboxLabelledBy = Objects.requireNonNull(labelledBy, "labelledBy");
            return this;
        }

        /** Extra aria-label on the actions-area checkbox. */
        public Header checkboxAriaLabel(String checkboxAriaLabel) {
            this.checkboxAriaLabel = checkboxAriaLabel;
            return this;
        }

        /** Selectable-actions checkbox bound to a chrome-owned Alpine variable. */
        public Header selectCheckbox(String id, String labelledBy, String xModel) {
            this.selKind = "check";
            this.selId = id;
            this.selLabelledBy = labelledBy;
            this.selXModel = xModel;
            return this;
        }

        /** Selectable-actions radio bound to a chrome-owned Alpine variable. */
        public Header selectRadio(String name, String id, String value, String labelledBy, String xModel) {
            this.selKind = "radio";
            this.selName = name;
            this.selId = id;
            this.selValue = value;
            this.selLabelledBy = labelledBy;
            this.selXModel = xModel;
            return this;
        }

        /** Whole-card clickable action button; the Alpine click expression may be null. */
        public Header clickButton(String labelledBy, String clickExpr) {
            this.selKind = "clickButton";
            this.selLabelledBy = labelledBy;
            this.selClickExpr = clickExpr;
            return this;
        }

        /** Whole-card clickable action anchor. */
        public Header clickAnchor(String href, String labelledBy) {
            this.selKind = "clickAnchor";
            this.selHref = href;
            this.selLabelledBy = labelledBy;
            return this;
        }

        /** Disable the selectable-actions input. */
        public Header asSelectDisabled() {
            this.selDisabled = true;
            return this;
        }

        /** Brand image rendered bare in the header, after the regions. */
        public Header brand(String src, String alt, String heightPx) {
            this.brandSrc = src;
            this.brandAlt = alt;
            this.brandHeight = heightPx;
            return this;
        }

        /** Brand image rendered inside the header-main area. */
        public Header brandMain(String src, String alt, String heightPx) {
            this.brandSrc = src;
            this.brandAlt = alt;
            this.brandHeight = heightPx;
            this.brandMainFlag = true;
            return this;
        }

        /** Icon in the header-main area (tiles). */
        public Header mainIcon(String mainIcon) {
            this.mainIcon = mainIcon;
            return this;
        }

        /** Title inside header-main (below the brand when both are set). */
        public Header mainTitle(String html, String id) {
            this.mainTitleHtml = html;
            this.mainTitleId = id;
            return this;
        }

        /** Subtitle inside header-main, under the title. */
        public Header mainSubtitle(String mainSubtitle) {
            this.mainSubtitle = mainSubtitle;
            return this;
        }

        /** Title rendered bare in the header, after the regions. */
        public Header title(String html, String id) {
            this.titleHtml = html;
            this.titleId = id;
            return this;
        }

        public boolean isWrap() {
            return wrap;
        }

        public boolean isExpandableToggle() {
            return expandableToggle;
        }

        public String kebabAria() {
            return kebabAria;
        }

        public boolean isHasActions() {
            return kebabAria != null || checkboxId != null;
        }

        public String checkboxId() {
            return checkboxId;
        }

        public String checkboxLabelledBy() {
            return checkboxLabelledBy;
        }

        public String checkboxAriaLabel() {
            return checkboxAriaLabel;
        }

        public String selKind() {
            return selKind;
        }

        public boolean isHasSelectableActions() {
            return selKind != null;
        }

        public String selId() {
            return selId;
        }

        public String selLabelledBy() {
            return selLabelledBy;
        }

        public String selAriaLabel() {
            return selAriaLabel;
        }

        public String selXModel() {
            return selXModel;
        }

        public String selName() {
            return selName;
        }

        public String selValue() {
            return selValue;
        }

        public String selClickExpr() {
            return selClickExpr;
        }

        public String selHref() {
            return selHref;
        }

        public boolean isSelDisabled() {
            return selDisabled;
        }

        public String brandSrc() {
            return brandSrc;
        }

        public String brandAlt() {
            return brandAlt;
        }

        public String brandHeight() {
            return brandHeight;
        }

        public String mainIcon() {
            return mainIcon;
        }

        public String mainTitleHtml() {
            return mainTitleHtml;
        }

        public String mainTitleId() {
            return mainTitleId;
        }

        public String mainSubtitle() {
            return mainSubtitle;
        }

        public String titleHtml() {
            return titleHtml;
        }

        public String titleId() {
            return titleId;
        }

        public boolean isHasHeaderMain() {
            return brandMainFlag || mainIcon != null || mainTitleHtml != null || mainSubtitle != null;
        }

        public boolean isBrandInMain() {
            return brandMainFlag;
        }
    }

    private final String id;
    private final boolean compact;
    private final boolean secondary;
    private final boolean fullHeight;
    private final boolean displayLg;
    private final boolean plain;
    private final boolean selectable;
    private final boolean clickable;
    private final boolean disabled;
    private final boolean expandable;
    private final String selectedExpr;
    private final Header header;
    private final List<Section> sections;

    private Card(Builder b) {
        this.id = b.id;
        this.compact = b.compact;
        this.secondary = b.secondary;
        this.fullHeight = b.fullHeight;
        this.displayLg = b.displayLg;
        this.plain = b.plain;
        this.selectable = b.selectable;
        this.clickable = b.clickable;
        this.disabled = b.disabled;
        this.expandable = b.expandable;
        this.selectedExpr = b.selectedExpr;
        this.header = b.header;
        this.sections = List.copyOf(b.sections);
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    public String id() {
        return id;
    }

    public boolean isCompact() {
        return compact;
    }

    public boolean isSecondary() {
        return secondary;
    }

    public boolean isFullHeight() {
        return fullHeight;
    }

    public boolean isDisplayLg() {
        return displayLg;
    }

    public boolean isPlain() {
        return plain;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public boolean isClickable() {
        return clickable;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public String selectedExpr() {
        return selectedExpr;
    }

    public Header header() {
        return header;
    }

    public List<Section> sections() {
        return sections;
    }

    public static final class Builder {

        private String id;
        private boolean compact;
        private boolean secondary;
        private boolean fullHeight;
        private boolean displayLg;
        private boolean plain;
        private boolean selectable;
        private boolean clickable;
        private boolean disabled;
        private boolean expandable;
        private String selectedExpr;
        private Header header;
        private final List<Section> sections = new ArrayList<>();

        private Builder() {
        }

        public Builder compact() {
            this.compact = true;
            return this;
        }

        public Builder secondary() {
            this.secondary = true;
            return this;
        }

        public Builder fullHeight() {
            this.fullHeight = true;
            return this;
        }

        public Builder displayLg() {
            this.displayLg = true;
            return this;
        }

        public Builder plain() {
            this.plain = true;
            return this;
        }

        public Builder selectable() {
            this.selectable = true;
            return this;
        }

        public Builder clickable() {
            this.clickable = true;
            return this;
        }

        public Builder asDisabled() {
            this.disabled = true;
            return this;
        }

        /** Wires the expand contract; pair with a header carrying the toggle. */
        public Builder expandable() {
            this.expandable = true;
            return this;
        }

        /** Alpine expression driving pf-m-selected (chrome owns the variable). */
        public Builder selectedExpr(String selectedExpr) {
            this.selectedExpr = selectedExpr;
            return this;
        }

        public Builder header(Header header) {
            this.header = Objects.requireNonNull(header, "header");
            return this;
        }

        /** Plain title section (rendered unescaped). */
        public Builder title(String html) {
            sections.add(new Section("title", html, null, null, false, null));
            return this;
        }

        /** Title with an id (referenced by selectable inputs) and/or heading level. */
        public Builder title(String html, String id, String headingLevel) {
            sections.add(new Section("title", html, id, headingLevel, false, null));
            return this;
        }

        /** Body section (rendered unescaped). */
        public Builder body(String html) {
            sections.add(new Section("body", html, null, null, false, null));
            return this;
        }

        /** Body section with pf-m-no-fill. */
        public Builder bodyNoFill(String html) {
            sections.add(new Section("body", html, null, null, true, null));
            return this;
        }

        public Builder footer(String html) {
            sections.add(new Section("footer", html, null, null, false, null));
            return this;
        }

        public Builder divider() {
            sections.add(new Section("divider", null, null, null, false, null));
            return this;
        }

        /** Expandable content region holding a body and optional footer. */
        public Builder expandableContent(String bodyHtml, String footerHtml) {
            sections.add(new Section("expandable", bodyHtml, null, null, false, footerHtml));
            return this;
        }

        public Card build() {
            return new Card(this);
        }
    }
}
