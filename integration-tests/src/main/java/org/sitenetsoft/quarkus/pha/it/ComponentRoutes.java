package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/components")
public class ComponentRoutes {

    @ConfigProperty(name = "collabora.url")
    String collaboraUrl;

    @ConfigProperty(name = "collabora.wopi-host")
    String collaboraWopiHost;

    @ConfigProperty(name = "collabora.access-token")
    String collaboraAccessToken;

    @Location("components/about-modal-demo")
    @Inject
    Template aboutModalPage;

    @Location("components/accordion-demo")
    @Inject
    Template accordionPage;

    @Location("components/action-list-demo")
    @Inject
    Template actionListPage;

    @Location("components/alert-demo")
    @Inject
    Template alertPage;

    @Location("components/avatar-demo")
    @Inject
    Template avatarPage;

    @Location("components/back-to-top-demo")
    @Inject
    Template backToTopPage;

    @Location("components/backdrop-demo")
    @Inject
    Template backdropPage;

    @Location("components/background-image-demo")
    @Inject
    Template backgroundImagePage;

    @Location("components/badge-demo")
    @Inject
    Template badgePage;

    @Location("components/banner-demo")
    @Inject
    Template bannerPage;

    @Location("components/brand-demo")
    @Inject
    Template brandPage;

    @Location("components/breadcrumb-demo")
    @Inject
    Template breadcrumbPage;

    @Location("components/button-demo")
    @Inject
    Template buttonPage;

    @Location("components/calendar-month-demo")
    @Inject
    Template calendarMonthPage;

    @Location("components/card-demo")
    @Inject
    Template cardPage;

    @Location("components/checkbox-demo")
    @Inject
    Template checkboxPage;

    @Location("components/chip")
    @Inject
    Template chipPage;

    @Location("components/clipboard-copy-demo")
    @Inject
    Template clipboardCopyPage;

    @Location("components/code-block-demo")
    @Inject
    Template codeBlockPage;

    @Location("components/code-editor-demo")
    @Inject
    Template codeEditorPage;

    @Location("components/content-demo")
    @Inject
    Template contentPage;

    @Location("components/context-selector-demo")
    @Inject
    Template contextSelectorPage;

    @Location("components/custom-menus-demo")
    @Inject
    Template customMenusPage;

    @Location("components/divider-demo")
    @Inject
    Template dividerPage;

    @Location("components/empty-state-demo")
    @Inject
    Template emptyStatePage;

    @Location("components/expandable-section-demo")
    @Inject
    Template expandableSectionPage;

    @Location("components/helper-text-demo")
    @Inject
    Template helperTextPage;

    @Location("components/hint-demo")
    @Inject
    Template hintPage;

    @Location("components/icon-demo")
    @Inject
    Template iconPage;

    @Location("components/label-demo")
    @Inject
    Template labelPage;

    @Location("components/list-demo")
    @Inject
    Template listPage;

    @Location("components/panel-demo")
    @Inject
    Template panelPage;

    @Location("components/progress-demo")
    @Inject
    Template progressPage;

    @Location("components/skeleton-demo")
    @Inject
    Template skeletonPage;

    @Location("components/spinner-demo")
    @Inject
    Template spinnerPage;

    @Location("components/ripple-demo")
    @Inject
    Template ripplePage;

    @Location("components/timestamp-demo")
    @Inject
    Template timestampPage;

    @Location("components/title-demo")
    @Inject
    Template titlePage;

    @Location("components/truncate-demo")
    @Inject
    Template truncatePage;

    @Location("components/data-list-demo")
    @Inject
    Template dataListPage;

    @Location("components/description-list-demo")
    @Inject
    Template descriptionListPage;

    @Location("components/form-demo")
    @Inject
    Template formPage;

    @Location("components/form-control-demo")
    @Inject
    Template formControlPage;

    @Location("components/form-select-demo")
    @Inject
    Template formSelectPage;

    @Location("components/number-input-demo")
    @Inject
    Template numberInputPage;

    @Location("components/radio-demo")
    @Inject
    Template radioPage;

    @Location("components/switch-demo")
    @Inject
    Template switchPage;

    @Location("components/text-area-demo")
    @Inject
    Template textAreaPage;

    @Location("components/text-input-demo")
    @Inject
    Template textInputPage;

    @Location("components/text-input-group-demo")
    @Inject
    Template textInputGroupPage;

    @Location("components/toggle-group-demo")
    @Inject
    Template toggleGroupPage;

    @Location("components/simple-list-demo")
    @Inject
    Template simpleListPage;

    @Location("components/simple-file-upload-demo")
    @Inject
    Template simpleFileUploadPage;

    @Location("components/tile-demo")
    @Inject
    Template tilePage;

    @Location("components/hero-demo")
    @Inject
    Template heroPage;

    @Location("components/compass-demo")
    @Inject
    Template compassPage;

    @Location("components/date-picker-demo")
    @Inject
    Template datePickerPage;

    @Location("components/dropdown-demo")
    @Inject
    Template dropdownPage;

    @Location("components/drawer-demo")
    @Inject
    Template drawerPage;

    @Location("components/inline-edit-demo")
    @Inject
    Template inlineEditPage;

    @Location("components/input-group-demo")
    @Inject
    Template inputGroupPage;

    @Location("components/jump-links-demo")
    @Inject
    Template jumpLinksPage;

    @Location("components/menu-demo")
    @Inject
    Template menuPage;

    @Location("components/menu-toggle-demo")
    @Inject
    Template menuTogglePage;

    @Location("components/modal-demo")
    @Inject
    Template modalPage;

    @Location("components/notification-badge-demo")
    @Inject
    Template notificationBadgePage;

    @Location("components/notification-drawer-demo")
    @Inject
    Template notificationDrawerPage;

    @Location("components/overflow-menu-demo")
    @Inject
    Template overflowMenuPage;

    @Location("components/popover-demo")
    @Inject
    Template popoverPage;

    @Location("components/tooltip-demo")
    @Inject
    Template tooltipPage;

    @Location("components/progress-stepper-demo")
    @Inject
    Template progressStepperPage;

    @Location("components/login-page-demo")
    @Inject
    Template loginPagePage;

    @Location("components/masthead-demo")
    @Inject
    Template mastheadPage;

    @Location("components/navigation-demo")
    @Inject
    Template navigationPage;

    @Location("components/page-demo")
    @Inject
    Template pagePage;

    @Location("components/pagination-demo")
    @Inject
    Template paginationPage;

    @Location("components/search-input-demo")
    @Inject
    Template searchInputPage;

    @Location("components/select-demo")
    @Inject
    Template selectPage;

    @Location("components/sidebar-demo")
    @Inject
    Template sidebarPage;

    @Location("components/skip-to-content-demo")
    @Inject
    Template skipToContentPage;

    @Location("components/slider-demo")
    @Inject
    Template sliderPage;

    @Location("components/tabs-demo")
    @Inject
    Template tabsPage;

    @Location("components/toolbar-demo")
    @Inject
    Template toolbarPage;

    @Location("components/tree-view-demo")
    @Inject
    Template treeViewPage;

    @Location("components/wizard-demo")
    @Inject
    Template wizardPage;

    @Location("components/application-launcher-demo")
    @Inject
    Template applicationLauncherPage;

    @Location("components/date-and-time-picker-demo")
    @Inject
    Template dateAndTimePickerPage;

    @Location("components/drag-and-drop-demo")
    @Inject
    Template dragAndDropPage;

    @Location("components/dual-list-selector-demo")
    @Inject
    Template dualListSelectorPage;

    @Location("components/multiple-file-upload-demo")
    @Inject
    Template multipleFileUploadPage;

    @Location("components/options-menu-demo")
    @Inject
    Template optionsMenuPage;

    @Location("components/password-generator-demo")
    @Inject
    Template passwordGeneratorPage;

    @Location("components/password-strength-demo")
    @Inject
    Template passwordStrengthPage;

    @Location("components/table-demo")
    @Inject
    Template tablePage;

    @Location("components/time-picker-demo")
    @Inject
    Template timePickerPage;

    @Location("components/rectangle-selection")
    @Inject
    Template rectangleSelectionPage;

    @Location("components/map")
    @Inject
    Template mapPage;

    @Location("components/document-editor")
    @Inject
    Template documentEditorPage;

    @Location("components/form-validation")
    @Inject
    Template formValidationPage;

    @Location("components/sortable-table")
    @Inject
    Template sortableTablePage;

    @Location("components/click-to-edit")
    @Inject
    Template clickToEditPage;

    @Location("components/toast-confirm")
    @Inject
    Template toastConfirmPage;

    @Location("components/live-search")
    @Inject
    Template liveSearchPage;

    @Location("components/infinite-scroll")
    @Inject
    Template infiniteScrollPage;

    @Location("components/click-to-load")
    @Inject
    Template clickToLoadPage;

    @Location("components/lazy-modal")
    @Inject
    Template lazyModalPage;

    @Location("components/video-player")
    @Inject
    Template videoPlayerPage;

    @Location("components/rich-text-editor")
    @Inject
    Template richTextEditorPage;

    @GET
    @Path("/about-modal")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance aboutModal() {
        return aboutModalPage.instance();
    }

    @GET
    @Path("/accordion")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance accordion() {
        List<Map<String, Object>> sampleItems = List.of(
            Map.of("title", "Item one",
                   "content", "This is the expandable content for item one. It provides details and additional information."),
            Map.of("title", "Item two",
                   "content", "This is the expandable content for item two. It provides details and additional information."),
            Map.of("title", "Item three",
                   "content", "This is the expandable content for item three. It provides details and additional information."),
            Map.of("title", "Item four",
                   "content", "This is the expandable content for item four. It provides details and additional information.")
        );

        return accordionPage
            .data("sampleItems", sampleItems);
    }

    @GET
    @Path("/action-list")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance actionList() {
        return actionListPage.instance();
    }

    @GET
    @Path("/avatar")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance avatar() {
        return avatarPage.instance();
    }

    @GET
    @Path("/back-to-top")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance backToTop() {
        return backToTopPage.instance();
    }

    @GET
    @Path("/backdrop")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance backdrop() {
        return backdropPage.instance();
    }

    @GET
    @Path("/background-image")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance backgroundImage() {
        return backgroundImagePage.instance();
    }

    @GET
    @Path("/badge")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance badge() {
        return badgePage.instance();
    }

    @GET
    @Path("/banner")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance banner() {
        return bannerPage.instance();
    }

    @GET
    @Path("/brand")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance brand() {
        return brandPage.instance();
    }

    @GET
    @Path("/breadcrumb")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance breadcrumb() {
        return breadcrumbPage.instance();
    }

    @GET
    @Path("/button")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance button() {
        return buttonPage.instance();
    }

    @GET
    @Path("/calendar-month")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance calendarMonth() {
        // October 2020 — date selected (day 9 = current, day 21 = selected)
        List<List<Map<String, Object>>> selectedWeeks = List.of(
            List.of(day(29, "adjacent"), day(30, "adjacent"), day(1), day(2), day(3), day(4), day(5)),
            List.of(day(6), day(7), day(8), day(9, "current"), day(10), day(11), day(12)),
            List.of(day(13), day(14), day(15), day(16), day(17), day(18), day(19)),
            List.of(day(20), day(21, "selected"), day(22), day(23), day(24), day(25), day(26)),
            List.of(day(27), day(28), day(29), day(30), day(31), day(1, "adjacent"), day(2, "adjacent"))
        );

        // October 2020 — date range (day 9 = current, range 11–29)
        List<List<Map<String, Object>>> rangeWeeks = List.of(
            List.of(day(29, "adjacent"), day(30, "adjacent"), day(1), day(2), day(3), day(4), day(5)),
            List.of(day(6), day(7), day(8), day(9, "current"), day(10),
                    day(11, "selected", "startRange", "inRange"), day(12, "inRange")),
            List.of(day(13, "inRange"), day(14, "inRange"), day(15, "inRange"), day(16, "inRange"),
                    day(17, "inRange"), day(18, "inRange"), day(19, "inRange")),
            List.of(day(20, "inRange"), day(21, "inRange"), day(22, "inRange"), day(23, "inRange"),
                    day(24, "inRange"), day(25, "inRange"), day(26, "inRange")),
            List.of(day(27, "inRange"), day(28, "inRange"),
                    day(29, "selected", "endRange", "inRange"), day(30), day(31),
                    day(1, "adjacent"), day(2, "adjacent"))
        );

        return calendarMonthPage
            .data("selectedMonth", "October").data("selectedYear", 2020).data("selectedWeeks", selectedWeeks)
            .data("rangeMonth", "October").data("rangeYear", 2020).data("rangeWeeks", rangeWeeks);
    }

    @GET
    @Path("/card")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance card() {
        return cardPage.instance();
    }

    @GET
    @Path("/checkbox")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance checkbox() {
        return checkboxPage.instance();
    }

    @GET
    @Path("/chip")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance chip() {
        return chipPage.instance();
    }

    @GET
    @Path("/clipboard-copy")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance clipboardCopy() {
        return clipboardCopyPage.instance();
    }

    @GET
    @Path("/code-block")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance codeBlock() {
        return codeBlockPage.instance();
    }

    @GET
    @Path("/code-editor")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance codeEditor() {
        return codeEditorPage.instance();
    }

    @GET
    @Path("/content")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance content() {
        return contentPage.instance();
    }

    @GET
    @Path("/context-selector")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance contextSelector() {
        return contextSelectorPage.instance();
    }

    @GET
    @Path("/custom-menus")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance customMenus() {
        return customMenusPage.instance();
    }

    @GET
    @Path("/divider")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance divider() {
        return dividerPage.instance();
    }

    @GET
    @Path("/empty-state")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance emptyState() {
        return emptyStatePage.instance();
    }

    @GET
    @Path("/expandable-section")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance expandableSection() {
        return expandableSectionPage.instance();
    }

    @GET
    @Path("/helper-text")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance helperText() {
        return helperTextPage.instance();
    }

    @GET
    @Path("/hint")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance hint() {
        return hintPage.instance();
    }

    @GET
    @Path("/icon")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance icon() {
        return iconPage.instance();
    }

    @GET
    @Path("/label")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance label() {
        return labelPage.instance();
    }

    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance list() {
        return listPage.instance();
    }

    @GET
    @Path("/panel")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance panel() {
        return panelPage.instance();
    }

    @GET
    @Path("/progress")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance progress() {
        return progressPage.instance();
    }

    @GET
    @Path("/skeleton")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance skeleton() {
        return skeletonPage.instance();
    }

    @GET
    @Path("/spinner")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance spinner() {
        return spinnerPage.instance();
    }

    @GET
    @Path("/ripple")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance ripple() {
        return ripplePage.instance();
    }

    @GET
    @Path("/timestamp")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance timestamp() {
        return timestampPage.instance();
    }

    @GET
    @Path("/title")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance title() {
        return titlePage.instance();
    }

    @GET
    @Path("/truncate")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance truncate() {
        return truncatePage.instance();
    }

    @GET
    @Path("/data-list")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance dataList() {
        return dataListPage.instance();
    }

    @GET
    @Path("/description-list")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance descriptionList() {
        return descriptionListPage.instance();
    }

    @GET
    @Path("/form")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance form() {
        return formPage.instance();
    }

    @GET
    @Path("/form-control")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance formControl() {
        return formControlPage.instance();
    }

    @GET
    @Path("/form-select")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance formSelect() {
        return formSelectPage.instance();
    }

    @GET
    @Path("/number-input")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance numberInput() {
        return numberInputPage.instance();
    }

    @GET
    @Path("/radio")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance radio() {
        return radioPage.instance();
    }

    @GET
    @Path("/switch")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance switchComponent() {
        return switchPage.instance();
    }

    @GET
    @Path("/text-area")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance textArea() {
        return textAreaPage.instance();
    }

    @GET
    @Path("/text-input")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance textInput() {
        return textInputPage.instance();
    }

    @GET
    @Path("/text-input-group")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance textInputGroup() {
        return textInputGroupPage.instance();
    }

    @GET
    @Path("/toggle-group")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance toggleGroup() {
        return toggleGroupPage.instance();
    }

    @GET
    @Path("/simple-list")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance simpleList() {
        return simpleListPage.instance();
    }

    @GET
    @Path("/simple-file-upload")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance simpleFileUpload() {
        return simpleFileUploadPage.instance();
    }

    @GET
    @Path("/tile")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance tile() {
        return tilePage.instance();
    }

    @GET
    @Path("/hero")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance hero() {
        return heroPage.instance();
    }

    @GET
    @Path("/input-group")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance inputGroup() {
        return inputGroupPage.instance();
    }

    @GET
    @Path("/compass")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance compass() {
        return compassPage.instance();
    }

    @GET
    @Path("/date-picker")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance datePicker() {
        return datePickerPage.instance();
    }

    @GET
    @Path("/dropdown")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance dropdown() {
        return dropdownPage.instance();
    }

    @GET
    @Path("/drawer")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance drawer() {
        return drawerPage.instance();
    }

    @GET
    @Path("/inline-edit")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance inlineEdit() {
        return inlineEditPage.instance();
    }

    @GET
    @Path("/jump-links")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance jumpLinks() {
        return jumpLinksPage.instance();
    }

    @GET
    @Path("/menu")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance menu() {
        return menuPage.instance();
    }

    @GET
    @Path("/menu-toggle")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance menuToggle() {
        return menuTogglePage.instance();
    }

    @GET
    @Path("/modal")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance modal() {
        return modalPage.instance();
    }

    @GET
    @Path("/notification-badge")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance notificationBadge() {
        return notificationBadgePage.instance();
    }

    @GET
    @Path("/notification-drawer")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance notificationDrawer() {
        return notificationDrawerPage.instance();
    }

    @GET
    @Path("/overflow-menu")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance overflowMenu() {
        return overflowMenuPage.instance();
    }

    @GET
    @Path("/popover")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance popover() {
        return popoverPage.instance();
    }

    @GET
    @Path("/tooltip")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance tooltip() {
        return tooltipPage.instance();
    }

    @GET
    @Path("/progress-stepper")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance progressStepper() {
        return progressStepperPage.instance();
    }

    @GET
    @Path("/login-page")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance loginPage() {
        return loginPagePage.instance();
    }

    @GET
    @Path("/masthead")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance masthead() {
        return mastheadPage.instance();
    }

    @GET
    @Path("/navigation")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance navigation() {
        return navigationPage.instance();
    }

    @GET
    @Path("/page")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance page() {
        return pagePage.instance();
    }

    @GET
    @Path("/pagination")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance pagination() {
        return paginationPage.instance();
    }

    @GET
    @Path("/search-input")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance searchInput() {
        return searchInputPage.instance();
    }

    @GET
    @Path("/select")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance selectComponent() {
        return selectPage.instance();
    }

    @GET
    @Path("/sidebar")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance sidebar() {
        return sidebarPage.instance();
    }

    @GET
    @Path("/skip-to-content")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance skipToContent() {
        return skipToContentPage.instance();
    }

    @GET
    @Path("/slider")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance slider() {
        return sliderPage.instance();
    }

    @GET
    @Path("/tabs")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance tabs() {
        return tabsPage.instance();
    }

    @GET
    @Path("/toolbar")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance toolbar() {
        return toolbarPage.instance();
    }

    @GET
    @Path("/tree-view")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance treeView() {
        return treeViewPage.instance();
    }

    @GET
    @Path("/wizard")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance wizard() {
        return wizardPage.instance();
    }

    @GET
    @Path("/application-launcher")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance applicationLauncher() {
        return applicationLauncherPage.instance();
    }

    @GET
    @Path("/date-and-time-picker")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance dateAndTimePicker() {
        return dateAndTimePickerPage.instance();
    }

    @GET
    @Path("/drag-and-drop")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance dragAndDrop() {
        return dragAndDropPage.instance();
    }

    @GET
    @Path("/dual-list-selector")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance dualListSelector() {
        return dualListSelectorPage.instance();
    }

    @GET
    @Path("/multiple-file-upload")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance multipleFileUpload() {
        return multipleFileUploadPage.instance();
    }

    @GET
    @Path("/options-menu")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance optionsMenu() {
        return optionsMenuPage.instance();
    }

    @GET
    @Path("/password-generator")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance passwordGenerator() {
        return passwordGeneratorPage.instance();
    }

    @GET
    @Path("/password-strength")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance passwordStrength() {
        return passwordStrengthPage.instance();
    }

    @GET
    @Path("/table")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance tableComponent() {
        return tablePage.instance();
    }

    @GET
    @Path("/time-picker")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance timePicker() {
        return timePickerPage.instance();
    }

    @GET
    @Path("/rectangle-selection")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance rectangleSelection() {
        return rectangleSelectionPage.instance();
    }

    @GET
    @Path("/map")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance map() {
        return mapPage.instance();
    }

    @GET
    @Path("/document-editor")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance documentEditor() {
        return documentEditorPage.instance()
            .data("collaboraUrl", collaboraUrl)
            .data("accessToken", collaboraAccessToken)
            .data("wopiWelcome", collaboraWopiHost + "/wopi/files/welcome")
            .data("wopiBudget",  collaboraWopiHost + "/wopi/files/budget")
            .data("wopiSlides",  collaboraWopiHost + "/wopi/files/slides")
            .data("wopiPolicy",  collaboraWopiHost + "/wopi/files/policy");
    }


    private static Map<String, Object> day(int date, String... flags) {
        Map<String, Object> d = new HashMap<>();
        d.put("date", date);
        for (String flag : flags) d.put(flag, true);
        return d;
    }

    @GET
    @Path("/alert")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance alert() {
        List<Map<String, String>> sampleActionLinks = List.of(
            Map.of("text", "View details", "href", "#"),
            Map.of("text", "Ignore", "href", "#")
        );

        return alertPage
            .data("sampleActionLinks", sampleActionLinks);
    }

    @GET
    @Path("/form-validation")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance formValidation() {
        return formValidationPage.instance();
    }

    @GET
    @Path("/sortable-table")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance sortableTable() {
        return sortableTablePage.instance();
    }

    @GET
    @Path("/click-to-edit")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance clickToEdit() {
        return clickToEditPage.instance();
    }

    @GET
    @Path("/toast-confirm")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance toastConfirm() {
        return toastConfirmPage.instance();
    }

    @GET
    @Path("/live-search")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance liveSearch() {
        return liveSearchPage.instance();
    }

    @GET
    @Path("/infinite-scroll")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance infiniteScroll() {
        return infiniteScrollPage.instance();
    }

    @GET
    @Path("/click-to-load")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance clickToLoad() {
        return clickToLoadPage.instance();
    }

    @GET
    @Path("/lazy-modal")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance lazyModal() {
        return lazyModalPage.instance();
    }

    @GET
    @Path("/video-player")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance videoPlayer() {
        return videoPlayerPage.instance();
    }

    @GET
    @Path("/rich-text-editor")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance richTextEditor() {
        return richTextEditorPage.instance();
    }
}
