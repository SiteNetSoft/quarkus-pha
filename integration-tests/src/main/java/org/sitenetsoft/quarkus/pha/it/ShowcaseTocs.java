package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.JumpLinkItem;
import org.sitenetsoft.quarkus.pha.model.JumpLinks;

/**
 * Per-page table-of-contents models for the showcase shell. Generated from the
 * pages' previous hand-rolled ws-toc markup during the shell mass conversion;
 * edit freely — this is source, not build output.
 */
@TemplateGlobal
public class ShowcaseTocs {

    /** ToC for the about-modal demo page. */
    public static JumpLinks tocAboutModal = ShowcaseToc.of("about-modal",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Without product name", "#without-product-name"))
                    .sub(JumpLinkItem.of("Complex content", "#complex-content")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("About modal props", "#props-about-modal"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the accordion demo page. */
    public static JumpLinks tocAccordion = ShowcaseToc.of("accordion",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Definition list", "#definition-list"))
                    .sub(JumpLinkItem.of("Single expand", "#single-expand"))
                    .sub(JumpLinkItem.of("Fixed (multiple expand)", "#fixed-multiple"))
                    .sub(JumpLinkItem.of("Bordered", "#bordered"))
                    .sub(JumpLinkItem.of("Large bordered", "#large-bordered"))
                    .sub(JumpLinkItem.of("Toggle at start", "#toggle-start")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Accordion props", "#props-accordion"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the action-list demo page. */
    public static JumpLinks tocActionList = ShowcaseToc.of("action-list",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Single group", "#single-group"))
                    .sub(JumpLinkItem.of("With kebab", "#with-kebab"))
                    .sub(JumpLinkItem.of("Icons on list", "#icons-list"))
                    .sub(JumpLinkItem.of("Icons on group", "#icons-group"))
                    .sub(JumpLinkItem.of("Multiple groups", "#multiple-groups"))
                    .sub(JumpLinkItem.of("Cancel — form", "#cancel-form"))
                    .sub(JumpLinkItem.of("Cancel — wizard", "#cancel-wizard"))
                    .sub(JumpLinkItem.of("Vertical", "#vertical")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Action list props", "#props-action-list"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the alert demo page. */
    public static JumpLinks tocAlert = ShowcaseToc.of("alert",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Alert variants", "#variants"))
                    .sub(JumpLinkItem.of("Alert variations", "#variations"))
                    .sub(JumpLinkItem.of("With description", "#with-description"))
                    .sub(JumpLinkItem.of("Closable", "#closable"))
                    .sub(JumpLinkItem.of("Inline", "#inline"))
                    .sub(JumpLinkItem.of("With action links", "#with-actions"))
                    .sub(JumpLinkItem.of("Alert timeout", "#timeout"))
                    .sub(JumpLinkItem.of("Expandable alerts", "#expandable"))
                    .sub(JumpLinkItem.of("Truncated alerts", "#truncated"))
                    .sub(JumpLinkItem.of("Custom icons", "#custom-icons"))
                    .sub(JumpLinkItem.of("Plain inline alert variants", "#plain-inline"))
                    .sub(JumpLinkItem.of("Static live region alerts", "#static-live-region"))
                    .sub(JumpLinkItem.of("Static inline alert group", "#alert-group-static"))
                    .sub(JumpLinkItem.of("Toast alert group", "#alert-group-toast"))
                    .sub(JumpLinkItem.of("Toast alert group with overflow capture", "#alert-group-toast-overflow"))
                    .sub(JumpLinkItem.of("Dynamic alert groups", "#dynamic-groups"))
                    .sub(JumpLinkItem.of("Dynamic live region alerts", "#dynamic-live-region"))
                    .sub(JumpLinkItem.of("Async live region alerts", "#async-live-region"))
                    .sub(JumpLinkItem.of("Dynamic alert group with overflow", "#dynamic-group-overflow"))
                    .sub(JumpLinkItem.of("Multiple dynamic alert groups", "#multiple-dynamic-groups"))
                    .sub(JumpLinkItem.of("Async alert groups", "#async-groups"))
                    .sub(JumpLinkItem.of("Toast alerts with notification drawer", "#toast-with-drawer")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Alert props", "#props-alert"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the application-launcher demo page. */
    public static JumpLinks tocApplicationLauncher = ShowcaseToc.of("application-launcher",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Application launcher menu", "#application-launcher-menu")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Application launcher pattern", "#props-application-launcher"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the avatar demo page. */
    public static JumpLinks tocAvatar = ShowcaseToc.of("avatar",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Bordered", "#bordered"))
                    .sub(JumpLinkItem.of("Sizes", "#sizes"))
                    .sub(JumpLinkItem.of("Bordered sizes", "#bordered-sizes"))
                    .sub(JumpLinkItem.of("Initials", "#initials"))
                    .sub(JumpLinkItem.of("Colors", "#colors")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Avatar props", "#props-avatar"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the back-to-top demo page. */
    public static JumpLinks tocBackToTop = ShowcaseToc.of("back-to-top",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Back to top props", "#props-back-to-top"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the background-image demo page. */
    public static JumpLinks tocBackgroundImage = ShowcaseToc.of("background-image",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Background image props", "#props-background-image"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the badge demo page. */
    public static JumpLinks tocBadge = ShowcaseToc.of("badge",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Read", "#read"))
                    .sub(JumpLinkItem.of("Unread", "#unread"))
                    .sub(JumpLinkItem.of("Disabled", "#disabled"))
                    .sub(JumpLinkItem.of("Screen reader text", "#screen-reader")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Badge props", "#props-badge"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the banner demo page. */
    public static JumpLinks tocBanner = ShowcaseToc.of("banner",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic colors", "#basic-colors"))
                    .sub(JumpLinkItem.of("Status", "#status"))
                    .sub(JumpLinkItem.of("With links", "#with-links"))
                    .sub(JumpLinkItem.of("Screen reader text", "#screen-reader"))
                    .sub(JumpLinkItem.of("Sticky", "#sticky"))
                    .sub(JumpLinkItem.of("Pill", "#pill")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Banner props", "#props-banner"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the brand demo page. */
    public static JumpLinks tocBrand = ShowcaseToc.of("brand",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("With width", "#with-width"))
                    .sub(JumpLinkItem.of("With height", "#with-height"))
                    .sub(JumpLinkItem.of("Responsive", "#responsive")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Brand props", "#props-brand"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the breadcrumb demo page. */
    public static JumpLinks tocBreadcrumb = ShowcaseToc.of("breadcrumb",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Without home link", "#without-home-link"))
                    .sub(JumpLinkItem.of("With heading", "#with-heading"))
                    .sub(JumpLinkItem.of("With dropdown", "#with-dropdown"))
                    .sub(JumpLinkItem.of("Auto-generated", "#auto-generated"))
                    .sub(JumpLinkItem.of("With buttons", "#with-buttons")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Breadcrumb props", "#props-breadcrumb"))
                    .sub(JumpLinkItem.of("BreadcrumbItem props", "#props-item"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the button demo page. */
    public static JumpLinks tocButton = ShowcaseToc.of("button",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Variant examples", "#variant-examples"))
                    .sub(JumpLinkItem.of("Disabled buttons", "#disabled-buttons"))
                    .sub(JumpLinkItem.of("Small buttons", "#small-buttons"))
                    .sub(JumpLinkItem.of("Call to action", "#call-to-action"))
                    .sub(JumpLinkItem.of("Block level", "#block-level"))
                    .sub(JumpLinkItem.of("Progress indicators", "#progress-indicators"))
                    .sub(JumpLinkItem.of("Links as buttons", "#links-as-buttons"))
                    .sub(JumpLinkItem.of("Inline link as span", "#inline-link-as-span"))
                    .sub(JumpLinkItem.of("Custom component", "#custom-component"))
                    .sub(JumpLinkItem.of("Aria-disabled", "#aria-disabled-examples"))
                    .sub(JumpLinkItem.of("Button with count", "#button-with-count"))
                    .sub(JumpLinkItem.of("Plain with no padding", "#plain-with-no-padding"))
                    .sub(JumpLinkItem.of("Stateful", "#stateful"))
                    .sub(JumpLinkItem.of("Circle buttons", "#circle-buttons"))
                    .sub(JumpLinkItem.of("Button types", "#button-types"))
                    .sub(JumpLinkItem.of("Progress button (login)", "#progress-login"))
                    .sub(JumpLinkItem.of("Favorite", "#favorite"))
                    .sub(JumpLinkItem.of("Settings", "#settings"))
                    .sub(JumpLinkItem.of("Hamburger", "#hamburger"))
                    .sub(JumpLinkItem.of("Clicked buttons", "#clicked")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Button props", "#props-button"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the calendar-month demo page. */
    public static JumpLinks tocCalendarMonth = ShowcaseToc.of("calendar-month",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Date range", "#date-range")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Calendar month props", "#props-calendar-month"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the card demo page. */
    public static JumpLinks tocCard = ShowcaseToc.of("card",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Card with subtitle", "#subtitle"))
                    .sub(JumpLinkItem.of("Card with subtitle and actions", "#subtitle-actions"))
                    .sub(JumpLinkItem.of("Secondary cards", "#secondary"))
                    .sub(JumpLinkItem.of("Compact", "#compact"))
                    .sub(JumpLinkItem.of("Flat", "#flat"))
                    .sub(JumpLinkItem.of("Modifiers", "#modifiers"))
                    .sub(JumpLinkItem.of("Header images and actions", "#header-images-actions"))
                    .sub(JumpLinkItem.of("Title inline with images and actions", "#title-inline-images-actions"))
                    .sub(JumpLinkItem.of("Card header without title", "#header-without-title"))
                    .sub(JumpLinkItem.of("Card with header that wraps", "#header-wraps"))
                    .sub(JumpLinkItem.of("With HTML heading element", "#heading-element"))
                    .sub(JumpLinkItem.of("With multiple body sections", "#multiple-body-sections"))
                    .sub(JumpLinkItem.of("With a primary body section that fills", "#body-section-fills"))
                    .sub(JumpLinkItem.of("Selectable", "#selectable"))
                    .sub(JumpLinkItem.of("Single selectable", "#single-selectable"))
                    .sub(JumpLinkItem.of("Actionable", "#actionable"))
                    .sub(JumpLinkItem.of("Actionable and selectable", "#actionable-selectable"))
                    .sub(JumpLinkItem.of("Expandable cards", "#expandable"))
                    .sub(JumpLinkItem.of("Expandable with icon", "#expandable-with-icon"))
                    .sub(JumpLinkItem.of("Card with dividers", "#with-dividers"))
                    .sub(JumpLinkItem.of("Single selectable tiles", "#single-selectable-tiles"))
                    .sub(JumpLinkItem.of("Multi selectable tiles", "#multi-selectable-tiles")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Card props", "#props-card"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the checkbox demo page. */
    public static JumpLinks tocCheckbox = ShowcaseToc.of("checkbox",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Checked", "#checked"))
                    .sub(JumpLinkItem.of("Disabled", "#disabled"))
                    .sub(JumpLinkItem.of("Reversed", "#reversed"))
                    .sub(JumpLinkItem.of("With description", "#with-description"))
                    .sub(JumpLinkItem.of("With body", "#with-body"))
                    .sub(JumpLinkItem.of("Required", "#required"))
                    .sub(JumpLinkItem.of("Standalone", "#standalone"))
                    .sub(JumpLinkItem.of("Controlled", "#controlled"))
                    .sub(JumpLinkItem.of("Label wraps", "#label-wraps"))
                    .sub(JumpLinkItem.of("With description and body", "#description-and-body")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Checkbox props", "#props-checkbox"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the clipboard-copy demo page. */
    public static JumpLinks tocClipboardCopy = ShowcaseToc.of("clipboard-copy",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Read only", "#readonly"))
                    .sub(JumpLinkItem.of("Expanded", "#expandable"))
                    .sub(JumpLinkItem.of("Read only expanded", "#read-only-expanded"))
                    .sub(JumpLinkItem.of("Read only expanded by default", "#read-only-expanded-by-default"))
                    .sub(JumpLinkItem.of("Expanded with array", "#expanded-with-array"))
                    .sub(JumpLinkItem.of("JSON object (wrap code with pre)", "#json-pre"))
                    .sub(JumpLinkItem.of("Inline compact", "#inline-compact"))
                    .sub(JumpLinkItem.of("Inline compact code in sentence", "#inline"))
                    .sub(JumpLinkItem.of("Inline compact with additional action", "#inline-compact-with-additional-action"))
                    .sub(JumpLinkItem.of("Inline compact with truncation", "#inline-compact-truncation")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Clipboard copy props", "#props-clipboard-copy"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the code-block demo page. */
    public static JumpLinks tocCodeBlock = ShowcaseToc.of("code-block",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("With copy", "#with-copy"))
                    .sub(JumpLinkItem.of("Expandable", "#expandable")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Code block props", "#props-code-block"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the code-editor demo page. */
    public static JumpLinks tocCodeEditor = ShowcaseToc.of("code-editor",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Read-only", "#read-only"))
                    .sub(JumpLinkItem.of("Without actions", "#without-actions"))
                    .sub(JumpLinkItem.of("Without language tab", "#minimal"))
                    .sub(JumpLinkItem.of("Upload", "#upload"))
                    .sub(JumpLinkItem.of("Header content and shortcuts", "#header-content"))
                    .sub(JumpLinkItem.of("Container", "#container"))
                    .sub(JumpLinkItem.of("Full height", "#full-height"))
                    .sub(JumpLinkItem.of("Custom control", "#custom-control"))
                    .sub(JumpLinkItem.of("Size to fit", "#size-to-fit"))
                    .sub(JumpLinkItem.of("Configuration modal", "#configuration-modal")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Code editor props", "#props-code-editor"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the compass demo page. */
    public static JumpLinks tocCompass = ShowcaseToc.of("compass",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("With alternate footer", "#alternate-footer"))
                    .sub(JumpLinkItem.of("With docked nav", "#docked-nav"))
                    .sub(JumpLinkItem.of("Main header structure", "#main-header-structure")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Compass props", "#props-compass"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the content demo page. */
    public static JumpLinks tocContent = ShowcaseToc.of("content",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Content as a wrapper", "#content-as-a-wrapper"))
                    .sub(JumpLinkItem.of("Headings", "#headings"))
                    .sub(JumpLinkItem.of("Body", "#body"))
                    .sub(JumpLinkItem.of("Unordered list", "#unordered-list"))
                    .sub(JumpLinkItem.of("Ordered list", "#ordered-list"))
                    .sub(JumpLinkItem.of("Plain list", "#plain-list"))
                    .sub(JumpLinkItem.of("Description list", "#description-list"))
                    .sub(JumpLinkItem.of("Link and visited link", "#link-and-visited-link"))
                    .sub(JumpLinkItem.of("Editorial content", "#editorial-content")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Content props", "#props-content"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the context-selector demo page. */
    public static JumpLinks tocContextSelector = ShowcaseToc.of("context-selector",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Context selector props", "#props-context-selector"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the custom-menus demo page. */
    public static JumpLinks tocCustomMenus = ShowcaseToc.of("custom-menus",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("With actions", "#with-actions"))
                    .sub(JumpLinkItem.of("With favorites", "#with-favorites"))
                    .sub(JumpLinkItem.of("With drilldown", "#with-drilldown"))
                    .sub(JumpLinkItem.of("With inline search filter", "#with-inline-search-filter"))
                    .sub(JumpLinkItem.of("Tree view menu", "#tree-view-menu"))
                    .sub(JumpLinkItem.of("Flyout menu", "#flyout"))
                    .sub(JumpLinkItem.of("Date select menu", "#date-select")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Custom menus pattern", "#props-custom-menus"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the data-list demo page. */
    public static JumpLinks tocDataList = ShowcaseToc.of("data-list",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("With headings", "#with-headings"))
                    .sub(JumpLinkItem.of("Compact", "#compact"))
                    .sub(JumpLinkItem.of("Plain", "#plain"))
                    .sub(JumpLinkItem.of("Checkboxes, actions and additional cells", "#checkboxes-actions"))
                    .sub(JumpLinkItem.of("Actions: single and multiple", "#actions"))
                    .sub(JumpLinkItem.of("Expandable", "#expandable"))
                    .sub(JumpLinkItem.of("Compact expandable", "#compact-expandable"))
                    .sub(JumpLinkItem.of("Nested expandable", "#nested-expandable"))
                    .sub(JumpLinkItem.of("Mixed expandable", "#mixed-expandable"))
                    .sub(JumpLinkItem.of("Width modifiers", "#width-modifiers"))
                    .sub(JumpLinkItem.of("Clickable rows", "#clickable-rows"))
                    .sub(JumpLinkItem.of("Clickable expandable rows", "#clickable-expandable-rows"))
                    .sub(JumpLinkItem.of("Controlling text", "#controlling-text"))
                    .sub(JumpLinkItem.of("As grid", "#as-grid"))
                    .sub(JumpLinkItem.of("Small grid breakpoint", "#sm-grid-breakpoint"))
                    .sub(JumpLinkItem.of("With no grid", "#no-grid"))
                    .sub(JumpLinkItem.of("Draggable", "#draggable")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Data list props", "#props-data-list"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the date-and-time-picker demo page. */
    public static JumpLinks tocDateAndTimePicker = ShowcaseToc.of("date-and-time-picker",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Date and time range picker", "#range")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Date and time picker pattern", "#props-date-and-time-picker"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the date-picker demo page. */
    public static JumpLinks tocDatePicker = ShowcaseToc.of("date-picker",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Required", "#required"))
                    .sub(JumpLinkItem.of("American format", "#american-format"))
                    .sub(JumpLinkItem.of("Helper text", "#helper-text"))
                    .sub(JumpLinkItem.of("Min and max date", "#min-max"))
                    .sub(JumpLinkItem.of("French", "#french"))
                    .sub(JumpLinkItem.of("With value", "#with-value"))
                    .sub(JumpLinkItem.of("Disabled", "#disabled"))
                    .sub(JumpLinkItem.of("Invalid", "#invalid"))
                    .sub(JumpLinkItem.of("Custom widths", "#custom-width"))
                    .sub(JumpLinkItem.of("Controlling the calendar state", "#controlled-calendar"))
                    .sub(JumpLinkItem.of("Date range picker", "#date-range")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Date picker props", "#props-date-picker"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the description-list demo page. */
    public static JumpLinks tocDescriptionList = ShowcaseToc.of("description-list",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Term help text", "#term-help-text"))
                    .sub(JumpLinkItem.of("Default 2 col", "#default-2-col"))
                    .sub(JumpLinkItem.of("Default 3 col on lg", "#default-3-col-on-lg"))
                    .sub(JumpLinkItem.of("Horizontal", "#horizontal"))
                    .sub(JumpLinkItem.of("Horizontal using custom term width modifier", "#horizontal-custom-term-width"))
                    .sub(JumpLinkItem.of("Horizontal 2 col", "#horizontal-2-col"))
                    .sub(JumpLinkItem.of("Horizontal 3 col on lg", "#horizontal-3-col-on-lg"))
                    .sub(JumpLinkItem.of("Compact", "#compact"))
                    .sub(JumpLinkItem.of("Compact horizontal", "#compact-horizontal"))
                    .sub(JumpLinkItem.of("Fluid horizontal", "#fluid-horizontal"))
                    .sub(JumpLinkItem.of("Column fill", "#fill-columns"))
                    .sub(JumpLinkItem.of("Large display size", "#large-display-size"))
                    .sub(JumpLinkItem.of("Default responsive columns", "#default-responsive-columns"))
                    .sub(JumpLinkItem.of("Horizontal responsive columns", "#horizontal-responsive-columns"))
                    .sub(JumpLinkItem.of("Responsive horizontal, vertical group layout", "#responsive-hori-vert-group"))
                    .sub(JumpLinkItem.of("Default auto column width", "#default-auto-column-width"))
                    .sub(JumpLinkItem.of("Horizontal auto column width", "#horizontal-auto-column-width"))
                    .sub(JumpLinkItem.of("Default inline grid", "#default-inline-grid"))
                    .sub(JumpLinkItem.of("Description list with card", "#with-card"))
                    .sub(JumpLinkItem.of("Large display size and card", "#large-display-size-and-card"))
                    .sub(JumpLinkItem.of("Display size with card, three column on large breakpoint", "#display-size-card-3-col-lg"))
                    .sub(JumpLinkItem.of("Display size with card, horizontal, modified term width", "#display-size-card-horizontal-term-width"))
                    .sub(JumpLinkItem.of("Auto-fit basic", "#auto-fit"))
                    .sub(JumpLinkItem.of("Auto-fit, min width modified grid template columns", "#auto-fit-min-width"))
                    .sub(JumpLinkItem.of("Auto-fit, min width modified, responsive grid template columns", "#auto-fit-min-width-responsive"))
                    .sub(JumpLinkItem.of("Icons on terms", "#icons-on-terms"))
                    .sub(JumpLinkItem.of("Display 2xl", "#display-2xl"))
                    .sub(JumpLinkItem.of("In drawer", "#in-drawer")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Description list props", "#props-description-list"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the divider demo page. */
    public static JumpLinks tocDivider = ShowcaseToc.of("divider",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Using hr", "#using-hr"))
                    .sub(JumpLinkItem.of("Using li", "#using-li"))
                    .sub(JumpLinkItem.of("Using div", "#using-div"))
                    .sub(JumpLinkItem.of("Inset medium", "#inset-medium"))
                    .sub(JumpLinkItem.of("Inset at various breakpoints", "#inset-at-various-breakpoints"))
                    .sub(JumpLinkItem.of("Vertical in flex layout", "#vertical-in-flex-layout"))
                    .sub(JumpLinkItem.of("Vertical, inset small", "#vertical-in-flex-layout-inset-small"))
                    .sub(JumpLinkItem.of("Vertical, inset at breakpoints", "#vertical-in-flex-layout-inset-at-various-breakpoints"))
                    .sub(JumpLinkItem.of("Switch orientation at breakpoints", "#switch-orientation-at-various-breakpoints")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Divider props", "#props-divider"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the drag-and-drop demo page. */
    public static JumpLinks tocDragAndDrop = ShowcaseToc.of("drag-and-drop",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Multiple drop zones", "#multiple-zones"))
                    .sub(JumpLinkItem.of("Sortable data list", "#data-list"))
                    .sub(JumpLinkItem.of("Dual list selector", "#dual-list")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Drag and drop pattern", "#props-drag-and-drop"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the drawer demo page. */
    public static JumpLinks tocDrawer = ShowcaseToc.of("drawer",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Panel at end", "#panel-end"))
                    .sub(JumpLinkItem.of("Panel at start", "#panel-start"))
                    .sub(JumpLinkItem.of("Panel on bottom", "#panel-bottom"))
                    .sub(JumpLinkItem.of("Basic inline", "#basic-inline"))
                    .sub(JumpLinkItem.of("Inline panel at end", "#inline-panel-end"))
                    .sub(JumpLinkItem.of("Inline panel at start", "#inline-panel-start"))
                    .sub(JumpLinkItem.of("Stacked content body elements", "#stacked-content-body"))
                    .sub(JumpLinkItem.of("Modified content padding", "#modified-content-padding"))
                    .sub(JumpLinkItem.of("Modified panel padding", "#modified-panel-padding"))
                    .sub(JumpLinkItem.of("Additional section", "#additional-section"))
                    .sub(JumpLinkItem.of("Static", "#static"))
                    .sub(JumpLinkItem.of("Breakpoint", "#breakpoint"))
                    .sub(JumpLinkItem.of("Resizable at end", "#resizable-end"))
                    .sub(JumpLinkItem.of("Resizable at start", "#resizable-start"))
                    .sub(JumpLinkItem.of("Resizable on bottom", "#resizable-bottom"))
                    .sub(JumpLinkItem.of("Resizable on inline", "#resizable-inline"))
                    .sub(JumpLinkItem.of("Secondary background", "#secondary-background"))
                    .sub(JumpLinkItem.of("With focus trap", "#focus-trap"))
                    .sub(JumpLinkItem.of("Pill", "#pill"))
                    .sub(JumpLinkItem.of("Pill inline", "#pill-inline")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Drawer props", "#props-drawer"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the dropdown demo page. */
    public static JumpLinks tocDropdown = ShowcaseToc.of("dropdown",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic dropdowns", "#basic"))
                    .sub(JumpLinkItem.of("With kebab toggle", "#plain-kebab"))
                    .sub(JumpLinkItem.of("With groups of items", "#with-groups"))
                    .sub(JumpLinkItem.of("With item descriptions", "#with-descriptions"))
                    .sub(JumpLinkItem.of("Split toggle with checkbox", "#split-checkbox")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Dropdown pattern", "#props-dropdown"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the dual-list-selector demo page. */
    public static JumpLinks tocDualListSelector = ShowcaseToc.of("dual-list-selector",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Basic with tooltips", "#with-tooltips"))
                    .sub(JumpLinkItem.of("Basic with search", "#with-search"))
                    .sub(JumpLinkItem.of("Using more complex options with actions", "#complex-actions"))
                    .sub(JumpLinkItem.of("Tree view", "#tree-view"))
                    .sub(JumpLinkItem.of("Tree view with chosen and disabled options", "#tree-view-disabled"))
                    .sub(JumpLinkItem.of("Draggable", "#draggable")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Dual list selector props", "#props-dual-list-selector"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the empty-state demo page. */
    public static JumpLinks tocEmptyState = ShowcaseToc.of("empty-state",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Extra small", "#extra-small"))
                    .sub(JumpLinkItem.of("Small", "#small"))
                    .sub(JumpLinkItem.of("Large", "#large"))
                    .sub(JumpLinkItem.of("Extra large", "#extra-large"))
                    .sub(JumpLinkItem.of("Success", "#success"))
                    .sub(JumpLinkItem.of("With status", "#with-status"))
                    .sub(JumpLinkItem.of("Spinner", "#spinner"))
                    .sub(JumpLinkItem.of("No match found", "#no-match"))
                    .sub(JumpLinkItem.of("No icon", "#no-icon"))
                    .sub(JumpLinkItem.of("With actions", "#with-actions")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Empty state props", "#props-empty-state"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the expandable-section demo page. */
    public static JumpLinks tocExpandableSection = ShowcaseToc.of("expandable-section",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic (collapsed)", "#collapsed"))
                    .sub(JumpLinkItem.of("Expanded by default", "#expanded"))
                    .sub(JumpLinkItem.of("Dynamic toggle text", "#dynamic-toggle-text"))
                    .sub(JumpLinkItem.of("Detached", "#detached"))
                    .sub(JumpLinkItem.of("Disclosure variation", "#disclosure"))
                    .sub(JumpLinkItem.of("Indented expandable content", "#indented"))
                    .sub(JumpLinkItem.of("With custom toggle content", "#custom-toggle"))
                    .sub(JumpLinkItem.of("With heading semantics", "#heading-semantics"))
                    .sub(JumpLinkItem.of("Truncate expansion", "#truncate-expansion"))
                    .sub(JumpLinkItem.of("Truncated text", "#truncated-text")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Expandable section props", "#props-expandable-section"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the form-control demo page. */
    public static JumpLinks tocFormControl = ShowcaseToc.of("form-control",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Validated", "#validated"))
                    .sub(JumpLinkItem.of("Disabled / read only", "#disabled-readonly")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Modifiers", "#props-form-control"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the form demo page. */
    public static JumpLinks tocForm = ShowcaseToc.of("form",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Horizontal", "#horizontal"))
                    .sub(JumpLinkItem.of("Limit width", "#limit-width"))
                    .sub(JumpLinkItem.of("Invalid", "#invalid"))
                    .sub(JumpLinkItem.of("Invalid with form alert", "#invalid-form-alert"))
                    .sub(JumpLinkItem.of("Validated", "#validated"))
                    .sub(JumpLinkItem.of("Horizontal stacked no padding top", "#horizontal-stacked"))
                    .sub(JumpLinkItem.of("Horizontal stacked helper text on top", "#horizontal-helper-on-top"))
                    .sub(JumpLinkItem.of("Form group with additional label info", "#group-label-info"))
                    .sub(JumpLinkItem.of("Form sections", "#sections"))
                    .sub(JumpLinkItem.of("Grid form", "#grid"))
                    .sub(JumpLinkItem.of("Field groups", "#field-groups"))
                    .sub(JumpLinkItem.of("With helper text", "#with-helper"))
                    .sub(JumpLinkItem.of("Action group", "#action-group"))
                    .sub(JumpLinkItem.of("Form state", "#form-state")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Form props", "#props-form"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the form-select demo page. */
    public static JumpLinks tocFormSelect = ShowcaseToc.of("form-select",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Grouped", "#grouped"))
                    .sub(JumpLinkItem.of("Validated", "#validated"))
                    .sub(JumpLinkItem.of("Disabled", "#disabled")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Form select props", "#props-form-select"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the helper-text demo page. */
    public static JumpLinks tocHelperText = ShowcaseToc.of("helper-text",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("With custom icons", "#with-custom-icons"))
                    .sub(JumpLinkItem.of("Multiple items", "#multiple-items"))
                    .sub(JumpLinkItem.of("Dynamic", "#dynamic"))
                    .sub(JumpLinkItem.of("Dynamic list", "#dynamic-list")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Container props", "#props-container"))
                    .sub(JumpLinkItem.of("Item props", "#props-item"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the hero demo page. */
    public static JumpLinks tocHero = ShowcaseToc.of("hero",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Glass", "#glass")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Hero props", "#props-hero"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the hint demo page. */
    public static JumpLinks tocHint = ShowcaseToc.of("hint",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("With content", "#with-content"))
                    .sub(JumpLinkItem.of("Basic without title", "#without-title"))
                    .sub(JumpLinkItem.of("With actions", "#actions")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Hint props", "#props-hint"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the icon demo page. */
    public static JumpLinks tocIcon = ShowcaseToc.of("icon",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Icon sets", "#sets"))
                    .sub(JumpLinkItem.of("Sizes", "#sizes"))
                    .sub(JumpLinkItem.of("Body sizes", "#body-sizes"))
                    .sub(JumpLinkItem.of("Heading sizes", "#heading-sizes"))
                    .sub(JumpLinkItem.of("Sizing within container", "#sizing-within-container"))
                    .sub(JumpLinkItem.of("Inline", "#inline"))
                    .sub(JumpLinkItem.of("Status colors", "#status-colors"))
                    .sub(JumpLinkItem.of("In progress", "#in-progress"))
                    .sub(JumpLinkItem.of("Custom in-progress icon", "#custom-in-progress-icon"))
                    .sub(JumpLinkItem.of("With accessible label", "#with-label")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Icon component props", "#props-icon-component"))
                    .sub(JumpLinkItem.of("icons:svg() arguments", "#props-icons-svg"))
                    .sub(JumpLinkItem.of("Usage", "#usage"))
                    .sub(JumpLinkItem.of("Design — why two APIs?", "#design")));

    /** ToC for the infinite-scroll demo page. */
    public static JumpLinks tocInfiniteScroll = ShowcaseToc.of("infinite-scroll",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Sentinel props", "#props-infinite-scroll"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the inline-edit demo page. */
    public static JumpLinks tocInlineEdit = ShowcaseToc.of("inline-edit",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("With label", "#with-label"))
                    .sub(JumpLinkItem.of("Multiple fields", "#multiple"))
                    .sub(JumpLinkItem.of("Validated", "#validated"))
                    .sub(JumpLinkItem.of("Free form edit", "#free-form"))
                    .sub(JumpLinkItem.of("Inline edit table row", "#table-row")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Inline edit props", "#props-inline-edit"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the input-group demo page. */
    public static JumpLinks tocInputGroup = ShowcaseToc.of("input-group",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("With textarea", "#with-textarea"))
                    .sub(JumpLinkItem.of("With dropdown", "#with-dropdown"))
                    .sub(JumpLinkItem.of("With popover", "#with-popover"))
                    .sub(JumpLinkItem.of("With multiple group siblings", "#multiple-siblings")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Input group props", "#props-input-group"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the jump-links demo page. */
    public static JumpLinks tocJumpLinks = ShowcaseToc.of("jump-links",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#horizontal"))
                    .sub(JumpLinkItem.of("With centered list", "#centered"))
                    .sub(JumpLinkItem.of("With label", "#with-label"))
                    .sub(JumpLinkItem.of("Vertical", "#vertical"))
                    .sub(JumpLinkItem.of("Vertical with label", "#vertical-with-label"))
                    .sub(JumpLinkItem.of("Vertical with inactive subsections", "#subsections-inactive"))
                    .sub(JumpLinkItem.of("Vertical with active subsections", "#subsections-active"))
                    .sub(JumpLinkItem.of("Expandable vertical with subsection", "#expandable-vertical-subsection"))
                    .sub(JumpLinkItem.of("Expandable (responsive)", "#expandable-responsive"))
                    .sub(JumpLinkItem.of("Expandable (responsive) with no label", "#expandable-responsive-no-label")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Jump links props", "#props-jump-links"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the label demo page. */
    public static JumpLinks tocLabel = ShowcaseToc.of("label",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Filled labels", "#filled-labels"))
                    .sub(JumpLinkItem.of("Outlined labels", "#outlined-labels"))
                    .sub(JumpLinkItem.of("Compact labels", "#compact-labels"))
                    .sub(JumpLinkItem.of("Custom render", "#label-with-custom-render"))
                    .sub(JumpLinkItem.of("Editable labels", "#editable-labels"))
                    .sub(JumpLinkItem.of("Basic label group", "#basic-label-group"))
                    .sub(JumpLinkItem.of("Group with overflow", "#label-group-with-overflow"))
                    .sub(JumpLinkItem.of("Group with categories", "#label-group-with-categories"))
                    .sub(JumpLinkItem.of("Removable categories", "#label-group-with-removable-categories"))
                    .sub(JumpLinkItem.of("Vertical group", "#vertical-label-group"))
                    .sub(JumpLinkItem.of("Editable group", "#editable-label-group"))
                    .sub(JumpLinkItem.of("Editable with add", "#editable-label-group-with-add-button"))
                    .sub(JumpLinkItem.of("Overflow label", "#overflow-label"))
                    .sub(JumpLinkItem.of("Add label", "#add-label"))
                    .sub(JumpLinkItem.of("Label group with overflow expanded", "#label-group-with-overflow-expanded"))
                    .sub(JumpLinkItem.of("Vertical label group with overflow", "#vertical-label-group-with-overflow"))
                    .sub(JumpLinkItem.of("Vertical label group with overflow expanded", "#vertical-label-group-with-overflow-expanded"))
                    .sub(JumpLinkItem.of("Vertical label group with category", "#vertical-label-group-with-category"))
                    .sub(JumpLinkItem.of("Vertical label group with removable category", "#vertical-label-group-with-removable-category"))
                    .sub(JumpLinkItem.of("Static labels, dynamic label group", "#static-labels-dynamic-group"))
                    .sub(JumpLinkItem.of("Mixed labels, dynamic label group", "#mixed-labels-dynamic-group"))
                    .sub(JumpLinkItem.of("Label group with compact labels", "#label-group-with-compact-labels"))
                    .sub(JumpLinkItem.of("Label group with compact labels and overflow", "#label-group-with-compact-labels-and-overflow"))
                    .sub(JumpLinkItem.of("Vertical label group with compact labels", "#vertical-label-group-with-compact-labels"))
                    .sub(JumpLinkItem.of("Labels with truncation", "#labels-with-truncation"))
                    .sub(JumpLinkItem.of("Editable labels with add dropdown", "#editable-add-dropdown"))
                    .sub(JumpLinkItem.of("Editable labels with add modal", "#editable-add-modal")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Label props", "#props-label"))
                    .sub(JumpLinkItem.of("LabelGroup props", "#props-group"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the list demo page. */
    public static JumpLinks tocList = ShowcaseToc.of("list",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Inline", "#inline"))
                    .sub(JumpLinkItem.of("Ordered", "#ordered"))
                    .sub(JumpLinkItem.of("Plain", "#plain"))
                    .sub(JumpLinkItem.of("With horizontal rules", "#with-horizontal-rules"))
                    .sub(JumpLinkItem.of("With icons", "#with-icons"))
                    .sub(JumpLinkItem.of("With large icons", "#with-large-icons")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("List props", "#props-list"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the login-page demo page. */
    public static JumpLinks tocLoginPage = ShowcaseToc.of("login-page",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Show/hide password", "#show-hide-password"))
                    .sub(JumpLinkItem.of("With header utilities", "#header-utilities")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Login page props", "#props-login-page"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the masthead demo page. */
    public static JumpLinks tocMasthead = ShowcaseToc.of("masthead",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Mixed content", "#mixed-content"))
                    .sub(JumpLinkItem.of("Display inline", "#display-inline"))
                    .sub(JumpLinkItem.of("Display stack", "#display-stack"))
                    .sub(JumpLinkItem.of("Display stack, inline responsive", "#display-stack-inline-responsive"))
                    .sub(JumpLinkItem.of("Insets", "#insets"))
                    .sub(JumpLinkItem.of("Custom logo component", "#custom-logo")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Masthead props", "#props-masthead"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the menu demo page. */
    public static JumpLinks tocMenu = ShowcaseToc.of("menu",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic menus", "#basic"))
                    .sub(JumpLinkItem.of("With icons", "#with-icons"))
                    .sub(JumpLinkItem.of("With actions", "#with-actions"))
                    .sub(JumpLinkItem.of("With links", "#with-links"))
                    .sub(JumpLinkItem.of("With descriptions", "#with-descriptions"))
                    .sub(JumpLinkItem.of("Item checkbox", "#item-checkbox"))
                    .sub(JumpLinkItem.of("Menu footer", "#footer"))
                    .sub(JumpLinkItem.of("Separated items", "#separated-items"))
                    .sub(JumpLinkItem.of("Titled groups of items", "#titled-groups"))
                    .sub(JumpLinkItem.of("With favorites", "#favorites"))
                    .sub(JumpLinkItem.of("Filtering with search input", "#filtering-search"))
                    .sub(JumpLinkItem.of("Option single select", "#option-single-select"))
                    .sub(JumpLinkItem.of("Option multi select", "#option-multi-select"))
                    .sub(JumpLinkItem.of("Scrollable menus", "#scrollable"))
                    .sub(JumpLinkItem.of("Scrollable menu with custom height", "#scrollable-custom-height"))
                    .sub(JumpLinkItem.of("With view more", "#view-more"))
                    .sub(JumpLinkItem.of("With drilldown", "#with-drilldown"))
                    .sub(JumpLinkItem.of("Danger menu item", "#danger-item"))
                    .sub(JumpLinkItem.of("With flyout", "#flyout"))
                    .sub(JumpLinkItem.of("Flyout menu positions", "#flyout-positions"))
                    .sub(JumpLinkItem.of("Scrollable drilldown", "#scrollable-drilldown"))
                    .sub(JumpLinkItem.of("Width modified drilldown", "#width-modified-drilldown"))
                    .sub(JumpLinkItem.of("Drilldown with breadcrumbs", "#drilldown-breadcrumbs"))
                    .sub(JumpLinkItem.of("Initially drilled-in menu", "#drilldown-initial-state"))
                    .sub(JumpLinkItem.of("Drilldown with inline filter", "#drilldown-inline-filter"))
                    .sub(JumpLinkItem.of("Scrollable menu with header and footer", "#header-footer"))
                    .sub(JumpLinkItem.of("Scrollable menu with search and footer", "#search-footer"))
                    .sub(JumpLinkItem.of("Loading", "#loading"))
                    .sub(JumpLinkItem.of("Plain", "#plain"))
                    .sub(JumpLinkItem.of("Plain with search and footer", "#plain-search-footer"))
                    .sub(JumpLinkItem.of("Plain scrollable with search and footer", "#plain-scrollable-search-footer")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Menu props", "#props-menu"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the menu-toggle demo page. */
    public static JumpLinks tocMenuToggle = ShowcaseToc.of("menu-toggle",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Collapsed toggle", "#basic"))
                    .sub(JumpLinkItem.of("Expanded toggle", "#expanded"))
                    .sub(JumpLinkItem.of("Small toggle", "#small"))
                    .sub(JumpLinkItem.of("Disabled toggle", "#disabled"))
                    .sub(JumpLinkItem.of("With a badge", "#badge"))
                    .sub(JumpLinkItem.of("Settings toggle", "#settings"))
                    .sub(JumpLinkItem.of("Custom icons", "#custom-icon"))
                    .sub(JumpLinkItem.of("With avatar and text", "#avatar-text"))
                    .sub(JumpLinkItem.of("Variant styles", "#variant-styles"))
                    .sub(JumpLinkItem.of("Plain toggle", "#plain"))
                    .sub(JumpLinkItem.of("Primary toggle", "#primary"))
                    .sub(JumpLinkItem.of("Plain circle toggle", "#plain-circle"))
                    .sub(JumpLinkItem.of("Plain toggle with text label", "#plain-text-label"))
                    .sub(JumpLinkItem.of("Split toggle", "#split"))
                    .sub(JumpLinkItem.of("Split toggle with checkbox", "#split-checkbox"))
                    .sub(JumpLinkItem.of("Split toggle with labeled checkbox", "#split-checkbox-text"))
                    .sub(JumpLinkItem.of("Split toggle with checkbox and toggle text", "#split-checkbox-toggle-text"))
                    .sub(JumpLinkItem.of("Split toggle with checkbox, icon, and toggle text", "#split-checkbox-icon-text"))
                    .sub(JumpLinkItem.of("Split toggle with action", "#split-action"))
                    .sub(JumpLinkItem.of("Full height toggle", "#full-height"))
                    .sub(JumpLinkItem.of("Full width toggle", "#full-width"))
                    .sub(JumpLinkItem.of("Toggle in a form", "#in-form"))
                    .sub(JumpLinkItem.of("Typeahead toggle", "#typeahead"))
                    .sub(JumpLinkItem.of("Status toggle", "#status"))
                    .sub(JumpLinkItem.of("Placeholder text in toggle", "#placeholder")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Menu toggle props", "#props-menu-toggle"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the modal demo page. */
    public static JumpLinks tocModal = ShowcaseToc.of("modal",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic modals", "#basic"))
                    .sub(JumpLinkItem.of("Scrollable modals", "#scrollable"))
                    .sub(JumpLinkItem.of("With a static description", "#with-description"))
                    .sub(JumpLinkItem.of("Top aligned", "#top-aligned"))
                    .sub(JumpLinkItem.of("Modal sizes", "#sizes"))
                    .sub(JumpLinkItem.of("Custom width", "#custom-width"))
                    .sub(JumpLinkItem.of("Custom header", "#custom-header"))
                    .sub(JumpLinkItem.of("No header or footer", "#no-header-footer"))
                    .sub(JumpLinkItem.of("Title icon", "#title-icon"))
                    .sub(JumpLinkItem.of("Custom title icon", "#custom-title-icon"))
                    .sub(JumpLinkItem.of("With dropdown", "#with-dropdown"))
                    .sub(JumpLinkItem.of("With help", "#with-help"))
                    .sub(JumpLinkItem.of("With form", "#with-form"))
                    .sub(JumpLinkItem.of("Custom focus", "#custom-focus"))
                    .sub(JumpLinkItem.of("Without title", "#without-title"))
                    .sub(JumpLinkItem.of("Generic container", "#generic-container"))
                    .sub(JumpLinkItem.of("Custom alert", "#custom-alert"))
                    .sub(JumpLinkItem.of("Info alert", "#info-alert"))
                    .sub(JumpLinkItem.of("Success alert", "#success-alert"))
                    .sub(JumpLinkItem.of("Warning alert", "#warning-alert"))
                    .sub(JumpLinkItem.of("Danger alert", "#danger-alert"))
                    .sub(JumpLinkItem.of("Danger alert with title modifier", "#danger-alert-title")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Modal props", "#props-modal"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the multiple-file-upload demo page. */
    public static JumpLinks tocMultipleFileUpload = ShowcaseToc.of("multiple-file-upload",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Horizontal", "#horizontal"))
                    .sub(JumpLinkItem.of("File upload status", "#status"))
                    .sub(JumpLinkItem.of("File upload status expanded", "#status-expanded"))
                    .sub(JumpLinkItem.of("Horizontal file upload status expanded", "#horizontal-status-expanded"))
                    .sub(JumpLinkItem.of("Rejected file handling", "#rejected-files")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Multiple file upload props", "#props-multiple-file-upload"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the navigation demo page. */
    public static JumpLinks tocNavigation = ShowcaseToc.of("navigation",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Vertical", "#vertical"))
                    .sub(JumpLinkItem.of("Grouped", "#grouped"))
                    .sub(JumpLinkItem.of("Expandable", "#expandable"))
                    .sub(JumpLinkItem.of("Expandable third level", "#expandable-third-level"))
                    .sub(JumpLinkItem.of("Mixed", "#mixed"))
                    .sub(JumpLinkItem.of("Horizontal", "#horizontal"))
                    .sub(JumpLinkItem.of("Horizontal subnav", "#horizontal-subnav"))
                    .sub(JumpLinkItem.of("With item icons", "#icons"))
                    .sub(JumpLinkItem.of("Horizontal overflow", "#horizontal-overflow"))
                    .sub(JumpLinkItem.of("Composable (pure Qute)", "#composable")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Navigation pattern", "#props-navigation"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the notification-badge demo page. */
    public static JumpLinks tocNotificationBadge = ShowcaseToc.of("notification-badge",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Read", "#read"))
                    .sub(JumpLinkItem.of("Unread", "#unread"))
                    .sub(JumpLinkItem.of("Attention", "#attention"))
                    .sub(JumpLinkItem.of("With animation", "#with-animation")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Notification badge props", "#props-notification-badge"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the notification-drawer demo page. */
    public static JumpLinks tocNotificationDrawer = ShowcaseToc.of("notification-drawer",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Groups", "#groups"))
                    .sub(JumpLinkItem.of("Lightweight", "#lightweight")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Notification drawer props", "#props-notification-drawer"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the number-input demo page. */
    public static JumpLinks tocNumberInput = ShowcaseToc.of("number-input",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Default", "#basic"))
                    .sub(JumpLinkItem.of("With unit", "#with-unit"))
                    .sub(JumpLinkItem.of("With unit and thresholds", "#bounded"))
                    .sub(JumpLinkItem.of("Disabled", "#disabled"))
                    .sub(JumpLinkItem.of("With status", "#with-status"))
                    .sub(JumpLinkItem.of("Varying sizes", "#varying-sizes"))
                    .sub(JumpLinkItem.of("Custom increment/decrement", "#custom-step"))
                    .sub(JumpLinkItem.of("Custom increment/decrement and thresholds", "#custom-step-threshold")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Number input props", "#props-number-input"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the options-menu demo page. */
    public static JumpLinks tocOptionsMenu = ShowcaseToc.of("options-menu",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Options menu pattern", "#props-options-menu"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the overflow-menu demo page. */
    public static JumpLinks tocOverflowMenu = ShowcaseToc.of("overflow-menu",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Simple", "#basic"))
                    .sub(JumpLinkItem.of("Vertical", "#vertical"))
                    .sub(JumpLinkItem.of("Group types", "#group-types"))
                    .sub(JumpLinkItem.of("Multiple groups", "#multiple-groups"))
                    .sub(JumpLinkItem.of("Persistent", "#persistent"))
                    .sub(JumpLinkItem.of("Breakpoint on container width", "#breakpoint-container-width"))
                    .sub(JumpLinkItem.of("Breakpoint on container height", "#breakpoint-container-height")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Overflow menu props", "#props-overflow-menu"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the page demo page. */
    public static JumpLinks tocPage = ShowcaseToc.of("page",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Vertical navigation", "#vertical-nav"))
                    .sub(JumpLinkItem.of("Multiple sidebar body", "#multiple-sidebar-body"))
                    .sub(JumpLinkItem.of("Horizontal navigation", "#horizontal-nav"))
                    .sub(JumpLinkItem.of("Uncontrolled navigation", "#uncontrolled-nav"))
                    .sub(JumpLinkItem.of("Filled page sections", "#filled-sections"))
                    .sub(JumpLinkItem.of("Main section padding", "#main-section-padding"))
                    .sub(JumpLinkItem.of("Main section variations", "#main-section-variations"))
                    .sub(JumpLinkItem.of("Group section", "#group-section"))
                    .sub(JumpLinkItem.of("Centered section", "#centered-section"))
                    .sub(JumpLinkItem.of("Plain sections and groups", "#plain-sections"))
                    .sub(JumpLinkItem.of("Dynamic sticky section", "#dynamic-sticky-section"))
                    .sub(JumpLinkItem.of("Composable (pure Qute)", "#composable")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Page props", "#props-page"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the JSON view-model contract page. */
    public static JumpLinks tocJsonModels = ShowcaseToc.of("json-models",
            JumpLinkItem.of("Quick start", "#quick-start"),
            JumpLinkItem.of("Shape rules", "#shape-rules"),
            JumpLinkItem.of("The pha: template URI", "#template-uris"),
            JumpLinkItem.of("Try it", "#try-it"));

    /** ToC for the pagination demo page. */
    public static JumpLinks tocPagination = ShowcaseToc.of("pagination",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Top", "#top"))
                    .sub(JumpLinkItem.of("Top sticky", "#sticky"))
                    .sub(JumpLinkItem.of("Indeterminate", "#indeterminate"))
                    .sub(JumpLinkItem.of("Bottom", "#bottom"))
                    .sub(JumpLinkItem.of("Bottom plain", "#bottom-plain"))
                    .sub(JumpLinkItem.of("Bottom sticky", "#bottom-sticky"))
                    .sub(JumpLinkItem.of("Top sticky with base and stuck", "#sticky-base-stuck"))
                    .sub(JumpLinkItem.of("Bottom sticky with base and stuck", "#bottom-sticky-base-stuck"))
                    .sub(JumpLinkItem.of("Bottom static", "#bottom-static"))
                    .sub(JumpLinkItem.of("Disabled", "#disabled"))
                    .sub(JumpLinkItem.of("Compact", "#compact"))
                    .sub(JumpLinkItem.of("Top with display summary modifier", "#display-summary"))
                    .sub(JumpLinkItem.of("Top with display full modifier", "#display-full"))
                    .sub(JumpLinkItem.of("Responsive display summary and full", "#display-responsive"))
                    .sub(JumpLinkItem.of("Compact display full modifier", "#compact-display-full"))
                    .sub(JumpLinkItem.of("Inset", "#inset"))
                    .sub(JumpLinkItem.of("No items", "#no-items"))
                    .sub(JumpLinkItem.of("One page", "#one-page"))
                    .sub(JumpLinkItem.of("Offset", "#offset"))
                    .sub(JumpLinkItem.of("Dynamic sticky", "#dynamic-sticky")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Pagination props", "#props-pagination"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the panel demo page. */
    public static JumpLinks tocPanel = ShowcaseToc.of("panel",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Header", "#header"))
                    .sub(JumpLinkItem.of("Footer", "#footer"))
                    .sub(JumpLinkItem.of("Header and footer", "#header-and-footer"))
                    .sub(JumpLinkItem.of("No body", "#no-body"))
                    .sub(JumpLinkItem.of("Raised", "#raised"))
                    .sub(JumpLinkItem.of("Bordered", "#bordered"))
                    .sub(JumpLinkItem.of("Secondary variant", "#secondary-variant"))
                    .sub(JumpLinkItem.of("Scrollable", "#scrollable"))
                    .sub(JumpLinkItem.of("Scrollable with header / footer", "#scrollable-with-header-and-footer"))
                    .sub(JumpLinkItem.of("Pill", "#pill"))
                    .sub(JumpLinkItem.of("Scrollable with auto height", "#scrollable-auto-height")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Panel props", "#props-panel"))
                    .sub(JumpLinkItem.of("Part templates", "#props-parts"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the password-generator demo page. */
    public static JumpLinks tocPasswordGenerator = ShowcaseToc.of("password-generator",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Password generator pattern", "#props-password-generator"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the password-strength demo page. */
    public static JumpLinks tocPasswordStrength = ShowcaseToc.of("password-strength",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Password strength pattern", "#props-password-strength"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the popover demo page. */
    public static JumpLinks tocPopover = ShowcaseToc.of("popover",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Position variants", "#positions"))
                    .sub(JumpLinkItem.of("Hoverable", "#hoverable"))
                    .sub(JumpLinkItem.of("Close popover from content", "#close-from-content"))
                    .sub(JumpLinkItem.of("Custom focus", "#custom-focus"))
                    .sub(JumpLinkItem.of("Without header/footer/close and no padding", "#no-header-footer"))
                    .sub(JumpLinkItem.of("No padding", "#no-padding"))
                    .sub(JumpLinkItem.of("Width auto", "#width-auto"))
                    .sub(JumpLinkItem.of("Advanced", "#advanced"))
                    .sub(JumpLinkItem.of("Popover with icon in the title", "#icon-in-title"))
                    .sub(JumpLinkItem.of("Alert popover", "#alert-variants"))
                    .sub(JumpLinkItem.of("Danger confirmation", "#danger")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Popover props", "#props-popover"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the progress demo page. */
    public static JumpLinks tocProgress = ShowcaseToc.of("progress",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Small", "#small"))
                    .sub(JumpLinkItem.of("Large", "#large"))
                    .sub(JumpLinkItem.of("Outside", "#outside"))
                    .sub(JumpLinkItem.of("Inside", "#inside"))
                    .sub(JumpLinkItem.of("Single line", "#single-line"))
                    .sub(JumpLinkItem.of("Without measure", "#without-measure"))
                    .sub(JumpLinkItem.of("Finite step", "#finite-step"))
                    .sub(JumpLinkItem.of("Step instructions", "#step-instructions"))
                    .sub(JumpLinkItem.of("Truncate title", "#truncate-title"))
                    .sub(JumpLinkItem.of("Title outside", "#title-outside"))
                    .sub(JumpLinkItem.of("Helper text", "#helper-text"))
                    .sub(JumpLinkItem.of("Success", "#success"))
                    .sub(JumpLinkItem.of("Failure", "#failure"))
                    .sub(JumpLinkItem.of("Warning", "#warning"))
                    .sub(JumpLinkItem.of("Inside success", "#inside-success"))
                    .sub(JumpLinkItem.of("Outside failure", "#outside-failure"))
                    .sub(JumpLinkItem.of("Failure without measure", "#failure-without-measure"))
                    .sub(JumpLinkItem.of("Outside static width measure", "#static-width")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Progress props", "#props-progress"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the progress-stepper demo page. */
    public static JumpLinks tocProgressStepper = ShowcaseToc.of("progress-stepper",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic (horizontal)", "#horizontal"))
                    .sub(JumpLinkItem.of("Vertical", "#vertical"))
                    .sub(JumpLinkItem.of("Vertical, horizontal responsive", "#vertical-responsive"))
                    .sub(JumpLinkItem.of("Compact", "#compact"))
                    .sub(JumpLinkItem.of("Compact, vertical", "#compact-vertical"))
                    .sub(JumpLinkItem.of("Compact, vertical responsive", "#compact-vertical-responsive"))
                    .sub(JumpLinkItem.of("Compact, vertical, centered", "#compact-vertical-center"))
                    .sub(JumpLinkItem.of("Compact, centered", "#compact-center"))
                    .sub(JumpLinkItem.of("With alignment", "#with-alignment"))
                    .sub(JumpLinkItem.of("Center aligned, vertical", "#center-vertical"))
                    .sub(JumpLinkItem.of("With an issue", "#with-issue"))
                    .sub(JumpLinkItem.of("With a failure", "#with-failure"))
                    .sub(JumpLinkItem.of("Basic in process", "#in-process"))
                    .sub(JumpLinkItem.of("With custom icons", "#custom-icons"))
                    .sub(JumpLinkItem.of("With help popover", "#help-popover")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Progress stepper props", "#props-progress-stepper"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the radio demo page. */
    public static JumpLinks tocRadio = ShowcaseToc.of("radio",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Controlled", "#controlled"))
                    .sub(JumpLinkItem.of("Reversed", "#reversed"))
                    .sub(JumpLinkItem.of("Label wraps", "#label-wraps"))
                    .sub(JumpLinkItem.of("Disabled", "#disabled"))
                    .sub(JumpLinkItem.of("With description", "#with-description"))
                    .sub(JumpLinkItem.of("With body", "#with-body"))
                    .sub(JumpLinkItem.of("With description and body", "#description-and-body"))
                    .sub(JumpLinkItem.of("Standalone input", "#standalone")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Radio props", "#props-radio"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the ripple demo page. */
    public static JumpLinks tocRipple = ShowcaseToc.of("ripple",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Custom color", "#custom-color"))
                    .sub(JumpLinkItem.of("On dark surface", "#on-dark")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Ripple props", "#props-ripple"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the search-input demo page. */
    public static JumpLinks tocSearchInput = ShowcaseToc.of("search-input",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("With clear button", "#with-clear"))
                    .sub(JumpLinkItem.of("No match", "#no-match"))
                    .sub(JumpLinkItem.of("Match with result count", "#result-count"))
                    .sub(JumpLinkItem.of("Match with navigable options", "#navigable-options"))
                    .sub(JumpLinkItem.of("Autocomplete", "#autocomplete"))
                    .sub(JumpLinkItem.of("Autocomplete hint", "#autocomplete-hint"))
                    .sub(JumpLinkItem.of("With submit button", "#with-submit"))
                    .sub(JumpLinkItem.of("With expandable button", "#expandable"))
                    .sub(JumpLinkItem.of("Advanced", "#advanced"))
                    .sub(JumpLinkItem.of("Advanced expanded", "#advanced-expanded"))
                    .sub(JumpLinkItem.of("Composable advanced search", "#advanced-composable")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Search input structure", "#props-search-input"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the select demo page. */
    public static JumpLinks tocSelect = ShowcaseToc.of("select",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Single select", "#single"))
                    .sub(JumpLinkItem.of("Select option variants", "#option-variants"))
                    .sub(JumpLinkItem.of("With grouped items", "#grouped"))
                    .sub(JumpLinkItem.of("With validation", "#validation"))
                    .sub(JumpLinkItem.of("Checkbox select", "#checkboxes"))
                    .sub(JumpLinkItem.of("Typeahead", "#typeahead"))
                    .sub(JumpLinkItem.of("Typeahead with create option", "#typeahead-creatable"))
                    .sub(JumpLinkItem.of("Multiple typeahead with labels", "#multi-typeahead"))
                    .sub(JumpLinkItem.of("Multiple typeahead with create option", "#multi-typeahead-creatable"))
                    .sub(JumpLinkItem.of("Multiple typeahead with checkboxes", "#multi-typeahead-checkbox"))
                    .sub(JumpLinkItem.of("With view more", "#view-more"))
                    .sub(JumpLinkItem.of("With a footer", "#footer")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Select pattern", "#props-select"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the sidebar demo page. */
    public static JumpLinks tocSidebar = ShowcaseToc.of("sidebar",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Stack", "#stack"))
                    .sub(JumpLinkItem.of("Panel right", "#panel-right"))
                    .sub(JumpLinkItem.of("Panel right with gutter", "#panel-right-gutter"))
                    .sub(JumpLinkItem.of("Sticky panel", "#sticky-panel"))
                    .sub(JumpLinkItem.of("Static panel", "#static-panel"))
                    .sub(JumpLinkItem.of("Responsive panel width", "#responsive-panel"))
                    .sub(JumpLinkItem.of("Border", "#border"))
                    .sub(JumpLinkItem.of("Padding on panel", "#padding-panel"))
                    .sub(JumpLinkItem.of("Padding on content", "#padding-content")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Sidebar props", "#props-sidebar"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the simple-file-upload demo page. */
    public static JumpLinks tocSimpleFileUpload = ShowcaseToc.of("simple-file-upload",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Simple text file", "#simple-text-file"))
                    .sub(JumpLinkItem.of("With helper text", "#with-helper-text"))
                    .sub(JumpLinkItem.of("Text file with edits allowed", "#text-with-edits"))
                    .sub(JumpLinkItem.of("Text file with restrictions", "#text-with-restrictions"))
                    .sub(JumpLinkItem.of("Custom file preview", "#custom-file-preview"))
                    .sub(JumpLinkItem.of("Custom file upload", "#custom-upload"))
                    .sub(JumpLinkItem.of("Disabled", "#disabled"))
                    .sub(JumpLinkItem.of("File upload loading", "#loading")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Simple file upload props", "#props-simple-file-upload"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the simple-list demo page. */
    public static JumpLinks tocSimpleList = ShowcaseToc.of("simple-list",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Grouped", "#grouped"))
                    .sub(JumpLinkItem.of("With links", "#links"))
                    .sub(JumpLinkItem.of("Selectable", "#selectable")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Simple list props", "#props-simple-list"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the skeleton demo page. */
    public static JumpLinks tocSkeleton = ShowcaseToc.of("skeleton",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Default", "#default"))
                    .sub(JumpLinkItem.of("Percentage widths", "#percentage-widths"))
                    .sub(JumpLinkItem.of("Percentage heights", "#percentage-heights"))
                    .sub(JumpLinkItem.of("Text modifiers", "#text-modifiers"))
                    .sub(JumpLinkItem.of("Shapes", "#shapes"))
                    .sub(JumpLinkItem.of("Skeleton card", "#card")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Skeleton props", "#props-skeleton"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the skip-to-content demo page. */
    public static JumpLinks tocSkipToContent = ShowcaseToc.of("skip-to-content",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Custom text", "#custom-text")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Skip to content props", "#props-skip-to-content"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the slider demo page. */
    public static JumpLinks tocSlider = ShowcaseToc.of("slider",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Discrete", "#basic"))
                    .sub(JumpLinkItem.of("Continuous", "#continuous"))
                    .sub(JumpLinkItem.of("Value input", "#value-input"))
                    .sub(JumpLinkItem.of("Thumb value input", "#thumb-value-input"))
                    .sub(JumpLinkItem.of("Actions", "#actions"))
                    .sub(JumpLinkItem.of("Custom range", "#custom-range"))
                    .sub(JumpLinkItem.of("Disabled", "#disabled")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Slider props", "#props-slider"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the spinner demo page. */
    public static JumpLinks tocSpinner = ShowcaseToc.of("spinner",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Size variations", "#size-variations"))
                    .sub(JumpLinkItem.of("Custom size", "#custom-size"))
                    .sub(JumpLinkItem.of("Inline", "#inline")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Spinner props", "#props-spinner"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the switch demo page. */
    public static JumpLinks tocSwitch = ShowcaseToc.of("switch",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Checked", "#checked"))
                    .sub(JumpLinkItem.of("Disabled", "#disabled"))
                    .sub(JumpLinkItem.of("Reversed", "#reversed"))
                    .sub(JumpLinkItem.of("Without label", "#without-label"))
                    .sub(JumpLinkItem.of("Label and check icon", "#check-icon")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Switch props", "#props-switch"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the table demo page. */
    public static JumpLinks tocTable = ShowcaseToc.of("table",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Plain", "#plain"))
                    .sub(JumpLinkItem.of("Header tooltips and popovers", "#header-help"))
                    .sub(JumpLinkItem.of("Compact", "#compact"))
                    .sub(JumpLinkItem.of("Compact expandable", "#compact-expandable"))
                    .sub(JumpLinkItem.of("Striped", "#striped"))
                    .sub(JumpLinkItem.of("Striped expandable", "#striped-expandable"))
                    .sub(JumpLinkItem.of("Striped multiple tbody", "#striped-multiple-tbody"))
                    .sub(JumpLinkItem.of("Striped tr", "#striped-tr"))
                    .sub(JumpLinkItem.of("Borderless", "#borderless"))
                    .sub(JumpLinkItem.of("Borderless compact", "#borderless-compact"))
                    .sub(JumpLinkItem.of("Borderless expandable", "#borderless-expandable"))
                    .sub(JumpLinkItem.of("Borderless compound expandable", "#borderless-compound-expandable"))
                    .sub(JumpLinkItem.of("Sortable (HTMX)", "#sortable"))
                    .sub(JumpLinkItem.of("Sortable — custom control", "#sortable-custom-control"))
                    .sub(JumpLinkItem.of("Expandable (Alpine)", "#expandable"))
                    .sub(JumpLinkItem.of("Animated expandable", "#animated-expandable"))
                    .sub(JumpLinkItem.of("Expandable with set width columns", "#expandable-set-width"))
                    .sub(JumpLinkItem.of("Expandable with nested table", "#expandable-nested-table"))
                    .sub(JumpLinkItem.of("Selectable with checkboxes", "#selectable-checkbox"))
                    .sub(JumpLinkItem.of("Selectable with indeterminate state", "#selectable-indeterminate"))
                    .sub(JumpLinkItem.of("Selectable with radio", "#selectable-radio"))
                    .sub(JumpLinkItem.of("Actions", "#actions"))
                    .sub(JumpLinkItem.of("Overflow menu", "#overflow-menu"))
                    .sub(JumpLinkItem.of("Empty state", "#empty-state"))
                    .sub(JumpLinkItem.of("Width and text modifiers", "#width"))
                    .sub(JumpLinkItem.of("Text control", "#text-control"))
                    .sub(JumpLinkItem.of("Table text element", "#table-text"))
                    .sub(JumpLinkItem.of("Long strings", "#long-strings"))
                    .sub(JumpLinkItem.of("Width constrained", "#width-constrained"))
                    .sub(JumpLinkItem.of("Hidden/visible breakpoints", "#breakpoint-modifiers"))
                    .sub(JumpLinkItem.of("Favoritable", "#favoritable"))
                    .sub(JumpLinkItem.of("Favorites sortable", "#favorites-sortable"))
                    .sub(JumpLinkItem.of("Clickable rows", "#clickable-rows"))
                    .sub(JumpLinkItem.of("Clickable and expandable", "#clickable-expandable"))
                    .sub(JumpLinkItem.of("Nested column headers", "#nested-column-headers"))
                    .sub(JumpLinkItem.of("Nested headers and expandable rows", "#nested-expandable"))
                    .sub(JumpLinkItem.of("Nested headers, sticky header", "#nested-sticky-header"))
                    .sub(JumpLinkItem.of("Editable rows", "#editable-rows"))
                    .sub(JumpLinkItem.of("Compound expandable", "#compound-expandable"))
                    .sub(JumpLinkItem.of("Compound expandable with nested table", "#compound-expandable-nested-table"))
                    .sub(JumpLinkItem.of("Animated compound expandable", "#animated-compound-expandable"))
                    .sub(JumpLinkItem.of("Sticky header and column", "#sticky"))
                    .sub(JumpLinkItem.of("Sticky footer", "#sticky-footer"))
                    .sub(JumpLinkItem.of("Multiple sticky columns", "#multiple-sticky-columns"))
                    .sub(JumpLinkItem.of("Sticky right column", "#sticky-right-column"))
                    .sub(JumpLinkItem.of("Tree table", "#tree-table"))
                    .sub(JumpLinkItem.of("Tree table with checkboxes", "#tree-table-checkboxes"))
                    .sub(JumpLinkItem.of("Tree table with checkboxes and icons", "#tree-table-icons"))
                    .sub(JumpLinkItem.of("Tree with no children or indentation", "#tree-table-flat"))
                    .sub(JumpLinkItem.of("Draggable rows", "#draggable-rows"))
                    .sub(JumpLinkItem.of("Footer", "#footer"))
                    .sub(JumpLinkItem.of("Cell with image alignment", "#cell-with-image-alignment"))
                    .sub(JumpLinkItem.of("Container query with drawer", "#container-query-with-drawer"))
                    .sub(JumpLinkItem.of("Sticky header with base and stuck", "#sticky-base-and-stuck"))
                    .sub(JumpLinkItem.of("Composable (pure Qute)", "#composable"))
                    .sub(JumpLinkItem.of("Composable expandable (pure Qute)", "#composable-expandable"))
                    .sub(JumpLinkItem.of("Composable tree (pure Qute)", "#composable-tree")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Table props", "#props-table"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the tabs demo page. */
    public static JumpLinks tocTabs = ShowcaseToc.of("tabs",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Default tabs", "#basic"))
                    .sub(JumpLinkItem.of("Box tabs", "#box"))
                    .sub(JumpLinkItem.of("Boxed secondary tabs", "#box-secondary"))
                    .sub(JumpLinkItem.of("Box vertical tabs", "#box-vertical"))
                    .sub(JumpLinkItem.of("Vertical tabs", "#vertical"))
                    .sub(JumpLinkItem.of("Vertical expandable tabs", "#vertical-expandable"))
                    .sub(JumpLinkItem.of("Vertical expandable responsive", "#vertical-expandable-responsive"))
                    .sub(JumpLinkItem.of("Overflow tabs", "#overflow"))
                    .sub(JumpLinkItem.of("Horizontal overflow tabs", "#horizontal-overflow"))
                    .sub(JumpLinkItem.of("Box overflow tabs", "#box-overflow"))
                    .sub(JumpLinkItem.of("With adjusted inset", "#inset"))
                    .sub(JumpLinkItem.of("Box tabs with adjusted insets", "#box-inset"))
                    .sub(JumpLinkItem.of("With page insets", "#page-insets"))
                    .sub(JumpLinkItem.of("With icons and text", "#icons-text"))
                    .sub(JumpLinkItem.of("Subtabs", "#subtabs"))
                    .sub(JumpLinkItem.of("Box tabs with subtabs", "#box-subtabs"))
                    .sub(JumpLinkItem.of("Filled tabs", "#filled"))
                    .sub(JumpLinkItem.of("Filled tabs with icons", "#filled-with-icons"))
                    .sub(JumpLinkItem.of("Filled box tabs", "#filled-box"))
                    .sub(JumpLinkItem.of("Filled box tabs with icons", "#filled-box-with-icons"))
                    .sub(JumpLinkItem.of("Tabs linked to nav elements", "#nav-tabs"))
                    .sub(JumpLinkItem.of("Subtabs linked to nav elements", "#nav-subtabs"))
                    .sub(JumpLinkItem.of("Tabs used for site navigation", "#site-nav"))
                    .sub(JumpLinkItem.of("With tab content with body and padding", "#content-body-padding"))
                    .sub(JumpLinkItem.of("Tab content", "#tab-content"))
                    .sub(JumpLinkItem.of("Secondary tab content", "#tab-content-secondary"))
                    .sub(JumpLinkItem.of("Dynamic tabs", "#dynamic"))
                    .sub(JumpLinkItem.of("With help action popover", "#help"))
                    .sub(JumpLinkItem.of("With close actions", "#close"))
                    .sub(JumpLinkItem.of("With help and close actions", "#help-and-close"))
                    .sub(JumpLinkItem.of("Animated tabs accent", "#animate-default"))
                    .sub(JumpLinkItem.of("Animated subtabs accent", "#animate-subtabs"))
                    .sub(JumpLinkItem.of("Animated filled tabs accent", "#animate-filled"))
                    .sub(JumpLinkItem.of("Animated vertical tabs accent", "#animate-vertical")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Tabs props", "#props-tabs"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the text-area demo page. */
    public static JumpLinks tocTextArea = ShowcaseToc.of("text-area",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Invalid", "#invalid"))
                    .sub(JumpLinkItem.of("Validated", "#validated"))
                    .sub(JumpLinkItem.of("Vertically resizable", "#resize-vertical"))
                    .sub(JumpLinkItem.of("Horizontally resizable", "#horizontal-resizable"))
                    .sub(JumpLinkItem.of("Not resizable", "#not-resizable"))
                    .sub(JumpLinkItem.of("Disabled", "#disabled"))
                    .sub(JumpLinkItem.of("Read only", "#read-only"))
                    .sub(JumpLinkItem.of("Disabled and read-only together", "#disabled-readonly"))
                    .sub(JumpLinkItem.of("Auto resizing", "#auto-resizing")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Text area props", "#props-text-area"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the text-input demo page. */
    public static JumpLinks tocTextInput = ShowcaseToc.of("text-input",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Disabled", "#disabled"))
                    .sub(JumpLinkItem.of("Read only", "#readonly"))
                    .sub(JumpLinkItem.of("Invalid", "#invalid"))
                    .sub(JumpLinkItem.of("With icon", "#with-icon"))
                    .sub(JumpLinkItem.of("Input types", "#types"))
                    .sub(JumpLinkItem.of("Start truncated", "#start-truncated"))
                    .sub(JumpLinkItem.of("Custom icon with error", "#icon-invalid")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Text input props", "#props-text-input"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the text-input-group demo page. */
    public static JumpLinks tocTextInputGroup = ShowcaseToc.of("text-input-group",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("With icon", "#with-icon"))
                    .sub(JumpLinkItem.of("Plain", "#plain"))
                    .sub(JumpLinkItem.of("Autocomplete hint", "#autocomplete-hint"))
                    .sub(JumpLinkItem.of("Disabled", "#disabled"))
                    .sub(JumpLinkItem.of("Filters", "#filters"))
                    .sub(JumpLinkItem.of("Filters expanded", "#filters-expanded"))
                    .sub(JumpLinkItem.of("With status", "#with-status"))
                    .sub(JumpLinkItem.of("Attribute-value filtering", "#attribute-value-filtering"))
                    .sub(JumpLinkItem.of("Auto-complete search with typeahead", "#autocomplete-search")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Text input group props", "#props-text-input-group"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the tile demo page. */
    public static JumpLinks tocTile = ShowcaseToc.of("tile",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic tile", "#basic"))
                    .sub(JumpLinkItem.of("With subtext", "#with-subtext"))
                    .sub(JumpLinkItem.of("With icon", "#with-icon"))
                    .sub(JumpLinkItem.of("With stacked icon", "#stacked-icon"))
                    .sub(JumpLinkItem.of("With large icons", "#large-icons"))
                    .sub(JumpLinkItem.of("With long subtext", "#long-subtext"))
                    .sub(JumpLinkItem.of("Tiles with single selection", "#single-selection"))
                    .sub(JumpLinkItem.of("Tiles with multiple selection", "#multiple-selection")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Tile props", "#props-tile"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the time-picker demo page. */
    public static JumpLinks tocTimePicker = ShowcaseToc.of("time-picker",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic 24 hour", "#basic"))
                    .sub(JumpLinkItem.of("Basic 12 hour", "#twelve-hour"))
                    .sub(JumpLinkItem.of("Custom delimiter", "#custom-delimiter"))
                    .sub(JumpLinkItem.of("Minimum/maximum times", "#min-max"))
                    .sub(JumpLinkItem.of("With seconds", "#with-seconds"))
                    .sub(JumpLinkItem.of("Basic 24 hours with seconds", "#twenty-four-with-seconds")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Time picker pattern", "#props-time-picker"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the timestamp demo page. */
    public static JumpLinks tocTimestamp = ShowcaseToc.of("timestamp",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Default", "#basic"))
                    .sub(JumpLinkItem.of("Basic formats", "#basic-formats"))
                    .sub(JumpLinkItem.of("Custom format", "#custom-format"))
                    .sub(JumpLinkItem.of("Custom content", "#custom-content"))
                    .sub(JumpLinkItem.of("Inline", "#inline"))
                    .sub(JumpLinkItem.of("Default tooltip", "#with-tooltip"))
                    .sub(JumpLinkItem.of("Custom tooltip", "#custom-tooltip")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Timestamp props", "#props-timestamp"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the title demo page. */
    public static JumpLinks tocTitle = ShowcaseToc.of("title",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Default sizes", "#default-sizes"))
                    .sub(JumpLinkItem.of("Custom sizes", "#custom-sizes"))
                    .sub(JumpLinkItem.of("Heading level modifiers", "#heading-levels"))
                    .sub(JumpLinkItem.of("Page title", "#page-title")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Title props", "#props-title"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the toggle-group demo page. */
    public static JumpLinks tocToggleGroup = ShowcaseToc.of("toggle-group",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Single select", "#single-select"))
                    .sub(JumpLinkItem.of("Multi select", "#multi-select"))
                    .sub(JumpLinkItem.of("With icons", "#with-icons"))
                    .sub(JumpLinkItem.of("With text and icons", "#text-and-icons"))
                    .sub(JumpLinkItem.of("Compact", "#compact"))
                    .sub(JumpLinkItem.of("Full-width", "#full-width")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Toggle group props", "#props-toggle-group"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the toolbar demo page. */
    public static JumpLinks tocToolbar = ShowcaseToc.of("toolbar",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Toolbar items", "#items"))
                    .sub(JumpLinkItem.of("With adjusted inset", "#insets"))
                    .sub(JumpLinkItem.of("No padding", "#no-padding"))
                    .sub(JumpLinkItem.of("Width control", "#width-control"))
                    .sub(JumpLinkItem.of("Vertical toolbar", "#vertical"))
                    .sub(JumpLinkItem.of("Sticky toolbar", "#sticky"))
                    .sub(JumpLinkItem.of("Dynamic sticky toolbar", "#dynamic-sticky"))
                    .sub(JumpLinkItem.of("With groups of items", "#groups"))
                    .sub(JumpLinkItem.of("Filter group", "#filter-group"))
                    .sub(JumpLinkItem.of("Action group", "#action-group"))
                    .sub(JumpLinkItem.of("Action group plain", "#action-group-plain"))
                    .sub(JumpLinkItem.of("Action group inline", "#action-group-inline"))
                    .sub(JumpLinkItem.of("Background color variants", "#color-variants"))
                    .sub(JumpLinkItem.of("Toggle groups", "#toggle-groups"))
                    .sub(JumpLinkItem.of("With filters", "#with-filters"))
                    .sub(JumpLinkItem.of("Label group", "#label-group"))
                    .sub(JumpLinkItem.of("Custom label group content", "#custom-label-group-content"))
                    .sub(JumpLinkItem.of("Stacked example", "#stacked"))
                    .sub(JumpLinkItem.of("Toolbar content wrapping", "#content-wrap"))
                    .sub(JumpLinkItem.of("Toolbar group spacers", "#group-spacers"))
                    .sub(JumpLinkItem.of("Toolbar item spacers", "#item-spacers"))
                    .sub(JumpLinkItem.of("Adjusted group column gap", "#column-gap"))
                    .sub(JumpLinkItem.of("Vertical with height visibility", "#vertical-height-visibility")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Toolbar props", "#props-toolbar"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the tooltip demo page. */
    public static JumpLinks tocTooltip = ShowcaseToc.of("tooltip",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Positions", "#positions"))
                    .sub(JumpLinkItem.of("Diagonal positions", "#diagonal-positions"))
                    .sub(JumpLinkItem.of("On icon", "#on-icon"))
                    .sub(JumpLinkItem.of("Long content", "#long-content"))
                    .sub(JumpLinkItem.of("Left aligned text", "#left-aligned"))
                    .sub(JumpLinkItem.of("Dynamic content", "#dynamic-content")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Tooltip props", "#props-tooltip"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the truncate demo page. */
    public static JumpLinks tocTruncate = ShowcaseToc.of("truncate",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Default", "#end"))
                    .sub(JumpLinkItem.of("Middle", "#middle"))
                    .sub(JumpLinkItem.of("Start", "#start"))
                    .sub(JumpLinkItem.of("With custom tooltip position", "#custom-tooltip-position"))
                    .sub(JumpLinkItem.of("Based on max characters", "#max-chars"))
                    .sub(JumpLinkItem.of("With links", "#with-links")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Truncate props", "#props-truncate"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the wizard demo page. */
    public static JumpLinks tocWizard = ShowcaseToc.of("wizard",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("With substeps", "#with-substeps"))
                    .sub(JumpLinkItem.of("With review", "#with-review"))
                    .sub(JumpLinkItem.of("Plain", "#plain"))
                    .sub(JumpLinkItem.of("Header", "#header"))
                    .sub(JumpLinkItem.of("Basic with disabled steps", "#disabled-steps"))
                    .sub(JumpLinkItem.of("Anchors for nav items", "#nav-anchors"))
                    .sub(JumpLinkItem.of("Incrementally enabled steps", "#incrementally-enabled"))
                    .sub(JumpLinkItem.of("Expandable steps", "#expandable-steps"))
                    .sub(JumpLinkItem.of("Step status", "#step-status"))
                    .sub(JumpLinkItem.of("Enabled on form validation", "#form-validation"))
                    .sub(JumpLinkItem.of("Validate on button press", "#validate-button-press"))
                    .sub(JumpLinkItem.of("Progressive steps", "#progressive-steps"))
                    .sub(JumpLinkItem.of("Toggle step visibility", "#toggle-step-visibility"))
                    .sub(JumpLinkItem.of("Progress after submission", "#submit-progress"))
                    .sub(JumpLinkItem.of("Custom footer", "#custom-footer"))
                    .sub(JumpLinkItem.of("Custom navigation", "#custom-nav"))
                    .sub(JumpLinkItem.of("Custom navigation item", "#custom-nav-item"))
                    .sub(JumpLinkItem.of("Focus content on next/back", "#focus-content"))
                    .sub(JumpLinkItem.of("Within modal", "#within-modal"))
                    .sub(JumpLinkItem.of("With drawer", "#with-drawer"))
                    .sub(JumpLinkItem.of("With drawer and informational step", "#with-drawer-info-step")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Wizard props", "#props-wizard"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the tree-view demo page. */
    public static JumpLinks tocTreeView = ShowcaseToc.of("tree-view",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Single selectable", "#single-selectable"))
                    .sub(JumpLinkItem.of("Multiselectable", "#multiselectable"))
                    .sub(JumpLinkItem.of("With search", "#with-search"))
                    .sub(JumpLinkItem.of("With checkboxes", "#with-checkboxes"))
                    .sub(JumpLinkItem.of("With icons", "#with-icons"))
                    .sub(JumpLinkItem.of("With badges", "#with-badges"))
                    .sub(JumpLinkItem.of("With action item", "#with-action-item"))
                    .sub(JumpLinkItem.of("With non-expandable top level nodes", "#with-non-expandable-top-level-nodes"))
                    .sub(JumpLinkItem.of("With selectable, expandable nodes", "#with-selectable-expandable-nodes"))
                    .sub(JumpLinkItem.of("Guides", "#guides"))
                    .sub(JumpLinkItem.of("Compact", "#compact"))
                    .sub(JumpLinkItem.of("Compact, no background", "#compact-no-background")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the layout-bullseye page. */
    public static JumpLinks tocLayoutBullseye = ShowcaseToc.of("layout-bullseye",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("With content", "#with-content")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Bullseye props", "#props-bullseye"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the layout-flex page. */
    public static JumpLinks tocLayoutFlex = ShowcaseToc.of("layout-flex",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Direction column", "#direction-column"))
                    .sub(JumpLinkItem.of("With gap", "#with-gap"))
                    .sub(JumpLinkItem.of("Justify content", "#justify-content"))
                    .sub(JumpLinkItem.of("Nested", "#nested"))
                    .sub(JumpLinkItem.of("Nested with items", "#nested-with-items"))
                    .sub(JumpLinkItem.of("Spacing on parent", "#spacing-parent"))
                    .sub(JumpLinkItem.of("Spacing on children", "#spacing-children"))
                    .sub(JumpLinkItem.of("Spacing on parent and children", "#spacing-parent-children"))
                    .sub(JumpLinkItem.of("Row gap", "#row-gap"))
                    .sub(JumpLinkItem.of("Column gap", "#column-gap"))
                    .sub(JumpLinkItem.of("Default layout", "#default-layout"))
                    .sub(JumpLinkItem.of("Inline flex", "#inline-flex"))
                    .sub(JumpLinkItem.of("Grow", "#grow"))
                    .sub(JumpLinkItem.of("Flex 1", "#grow-1"))
                    .sub(JumpLinkItem.of("Flex 1 to 4", "#grow-1-4"))
                    .sub(JumpLinkItem.of("Nested columns", "#nested-columns"))
                    .sub(JumpLinkItem.of("Responsive direction", "#responsive-direction"))
                    .sub(JumpLinkItem.of("Align right", "#align-right"))
                    .sub(JumpLinkItem.of("Align right on single item", "#align-right-single"))
                    .sub(JumpLinkItem.of("Align right on multiple groups", "#align-right-groups"))
                    .sub(JumpLinkItem.of("Align self flex end", "#align-self-flex-end"))
                    .sub(JumpLinkItem.of("Align self center", "#align-self-center"))
                    .sub(JumpLinkItem.of("Align self baseline", "#align-self-baseline"))
                    .sub(JumpLinkItem.of("Align self stretch", "#align-self-stretch"))
                    .sub(JumpLinkItem.of("Justify content flex end", "#justify-content-flex-end"))
                    .sub(JumpLinkItem.of("Justify content space between", "#justify-content-space-between"))
                    .sub(JumpLinkItem.of("Justify content flex start", "#justify-content-flex-start"))
                    .sub(JumpLinkItem.of("Ordering", "#ordering"))
                    .sub(JumpLinkItem.of("First and last ordering", "#ordering-first-last"))
                    .sub(JumpLinkItem.of("Responsive first last ordering", "#ordering-first-last-responsive"))
                    .sub(JumpLinkItem.of("Responsive ordering", "#ordering-responsive")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Flex props", "#props-flex"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the layout-gallery page. */
    public static JumpLinks tocLayoutGallery = ShowcaseToc.of("layout-gallery",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("With gutter", "#with-gutter"))
                    .sub(JumpLinkItem.of("Custom min width", "#custom-min-width"))
                    .sub(JumpLinkItem.of("Max width", "#max-width"))
                    .sub(JumpLinkItem.of("Responsive min width", "#min-width-responsive"))
                    .sub(JumpLinkItem.of("Responsive max width", "#max-width-responsive"))
                    .sub(JumpLinkItem.of("Responsive min and max width", "#min-max-width-responsive"))
                    .sub(JumpLinkItem.of("As a list", "#as-list")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Gallery props", "#props-gallery"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the layout-grid page. */
    public static JumpLinks tocLayoutGrid = ShowcaseToc.of("layout-grid",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("With gutter", "#with-gutter"))
                    .sub(JumpLinkItem.of("Spans", "#spans"))
                    .sub(JumpLinkItem.of("Responsive spans", "#responsive-spans"))
                    .sub(JumpLinkItem.of("Smart", "#smart"))
                    .sub(JumpLinkItem.of("Smart with overrides", "#smart-overrides"))
                    .sub(JumpLinkItem.of("Nested", "#nested"))
                    .sub(JumpLinkItem.of("Offsets", "#offsets"))
                    .sub(JumpLinkItem.of("Row spans", "#row-spans"))
                    .sub(JumpLinkItem.of("As a list", "#as-list"))
                    .sub(JumpLinkItem.of("Ordering", "#ordering"))
                    .sub(JumpLinkItem.of("Responsive ordering", "#ordering-responsive"))
                    .sub(JumpLinkItem.of("Grouped ordering", "#ordering-grouped"))
                    .sub(JumpLinkItem.of("Grouped responsive ordering", "#ordering-grouped-responsive")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Grid props", "#props-grid"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the layout-level page. */
    public static JumpLinks tocLayoutLevel = ShowcaseToc.of("layout-level",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("With gutter", "#with-gutter"))
                    .sub(JumpLinkItem.of("Three items", "#three-items")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Level props", "#props-level"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the layout-split page. */
    public static JumpLinks tocLayoutSplit = ShowcaseToc.of("layout-split",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("With fill", "#with-fill"))
                    .sub(JumpLinkItem.of("With gutter", "#with-gutter"))
                    .sub(JumpLinkItem.of("Wrappable", "#wrappable")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Split props", "#props-split"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the layout-stack page. */
    public static JumpLinks tocLayoutStack = ShowcaseToc.of("layout-stack",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("With fill", "#with-fill"))
                    .sub(JumpLinkItem.of("With gutter", "#with-gutter")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Stack props", "#props-stack"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the log-viewer page. */
    public static JumpLinks tocLogViewer = ShowcaseToc.of("log-viewer",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Line numbers", "#line-numbers"))
                    .sub(JumpLinkItem.of("With toolbar", "#with-toolbar"))
                    .sub(JumpLinkItem.of("Wrap toggle", "#wrap-nowrap"))
                    .sub(JumpLinkItem.of("ANSI colors", "#ansi-colors"))
                    .sub(JumpLinkItem.of("Streaming (SSE)", "#streaming")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Log-viewer props", "#props-log-viewer"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the user-feedback page. */
    public static JumpLinks tocUserFeedback = ShowcaseToc.of("user-feedback",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Full menu", "#full"))
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("Pre-filled email", "#with-prefilled-email")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Props", "#props-user-feedback"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the i18n demo page. */
    public static JumpLinks tocI18n = ShowcaseToc.of("i18n",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Language switcher", "#i18n-language"))
                    .sub(JumpLinkItem.of("Localized profile card", "#i18n-profile")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the chip demo page. */
    public static JumpLinks tocChip = ShowcaseToc.of("chip",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("With badge", "#with-badge"))
                    .sub(JumpLinkItem.of("Removable", "#removable"))
                    .sub(JumpLinkItem.of("Overflow", "#overflow")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Chip props", "#props-chip"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the map demo page. */
    public static JumpLinks tocMap = ShowcaseToc.of("map",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("With markers", "#markers"))
                    .sub(JumpLinkItem.of("Dark theme", "#dark-theme"))
                    .sub(JumpLinkItem.of("Full controls", "#full-controls"))
                    .sub(JumpLinkItem.of("GeoJSON layer", "#geojson"))
                    .sub(JumpLinkItem.of("Compact", "#compact")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Map props", "#props-map"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the rich-text-editor demo page. */
    public static JumpLinks tocRichTextEditor = ShowcaseToc.of("rich-text-editor",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic (Snow theme)", "#basic"))
                    .sub(JumpLinkItem.of("Basic toolbar", "#basic-toolbar"))
                    .sub(JumpLinkItem.of("Minimal toolbar", "#minimal-toolbar"))
                    .sub(JumpLinkItem.of("Bubble theme", "#bubble"))
                    .sub(JumpLinkItem.of("Read-only viewer", "#readonly"))
                    .sub(JumpLinkItem.of("With hidden form input", "#with-form-input")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Rich text editor props", "#props-rich-text-editor"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the document-editor demo page. */
    public static JumpLinks tocDocumentEditor = ShowcaseToc.of("document-editor",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic (no document)", "#basic"))
                    .sub(JumpLinkItem.of("Text document", "#text-document"))
                    .sub(JumpLinkItem.of("Spreadsheet", "#spreadsheet"))
                    .sub(JumpLinkItem.of("Presentation", "#presentation"))
                    .sub(JumpLinkItem.of("With toolbar", "#with-toolbar"))
                    .sub(JumpLinkItem.of("Read-only viewer", "#readonly"))
                    .sub(JumpLinkItem.of("With status bar", "#with-status")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Document editor props", "#props-document-editor"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the rectangle-selection demo page. */
    public static JumpLinks tocRectangleSelection = ShowcaseToc.of("rectangle-selection",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic", "#basic"))
                    .sub(JumpLinkItem.of("With PatternFly cards", "#with-cards")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the click-to-edit demo page. */
    public static JumpLinks tocClickToEdit = ShowcaseToc.of("click-to-edit",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Edit a profile inline", "#basic")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the click-to-load demo page. */
    public static JumpLinks tocClickToLoad = ShowcaseToc.of("click-to-load",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Manual pagination", "#basic")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Click-to-load props", "#props-click-to-load"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the form-validation demo page. */
    public static JumpLinks tocFormValidation = ShowcaseToc.of("form-validation",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Server-side validated signup", "#basic")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the lazy-modal demo page. */
    public static JumpLinks tocLazyModal = ShowcaseToc.of("lazy-modal",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("On-demand content loading", "#basic")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Lazy modal props", "#props-lazy-modal"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the live-search demo page. */
    public static JumpLinks tocLiveSearch = ShowcaseToc.of("live-search",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Server-filtered search", "#basic")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Live search props", "#props-live-search"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the skeleton-loading demo page. */
    public static JumpLinks tocSkeletonLoading = ShowcaseToc.of("skeleton-loading",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("On-demand profile", "#basic")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the sortable-table demo page. */
    public static JumpLinks tocSortableTable = ShowcaseToc.of("sortable-table",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Sort, filter, and paginate", "#basic")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the toast-confirm demo page. */
    public static JumpLinks tocToastConfirm = ShowcaseToc.of("toast-confirm",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Toasts from the server", "#basic")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Usage", "#usage")));

    /** ToC for the video-player demo page. */
    public static JumpLinks tocVideoPlayer = ShowcaseToc.of("video-player",
            JumpLinkItem.of("Examples", "#examples")
                    .sub(JumpLinkItem.of("Basic (no source)", "#basic"))
                    .sub(JumpLinkItem.of("MP4 source", "#mp4"))
                    .sub(JumpLinkItem.of("Playback rates + muted autoplay", "#playback-rates"))
                    .sub(JumpLinkItem.of("With toolbar", "#with-toolbar")),
            JumpLinkItem.of("Documentation", "#documentation")
                    .sub(JumpLinkItem.of("Video player props", "#props-video-player"))
                    .sub(JumpLinkItem.of("Usage", "#usage")));
}
