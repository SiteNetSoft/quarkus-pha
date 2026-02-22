package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/components")
public class ComponentRoutes {

    @Location("components/about-modal-demo")
    @Inject
    Template aboutModalDemo;

    @Location("components/accordion")
    @Inject
    Template accordionPage;

    @Location("components/action-list")
    @Inject
    Template actionListPage;

    @Location("components/alert")
    @Inject
    Template alertPage;

    @Location("components/avatar")
    @Inject
    Template avatarPage;

    @Location("components/back-to-top")
    @Inject
    Template backToTopPage;

    @Location("components/backdrop")
    @Inject
    Template backdropPage;

    @Location("components/background-image")
    @Inject
    Template backgroundImagePage;

    @Location("components/badge")
    @Inject
    Template badgePage;

    @Location("components/banner")
    @Inject
    Template bannerPage;

    @Location("components/brand")
    @Inject
    Template brandPage;

    @Location("components/breadcrumb")
    @Inject
    Template breadcrumbPage;

    @Location("components/button")
    @Inject
    Template buttonPage;

    @Location("components/calendar-month")
    @Inject
    Template calendarMonthPage;

    @Location("components/card")
    @Inject
    Template cardPage;

    @Location("components/checkbox")
    @Inject
    Template checkboxPage;

    @Location("components/chip")
    @Inject
    Template chipPage;

    @Location("components/clipboard-copy")
    @Inject
    Template clipboardCopyPage;

    @Location("components/code-block")
    @Inject
    Template codeBlockPage;

    @Location("components/code-editor")
    @Inject
    Template codeEditorPage;

    @Location("components/content")
    @Inject
    Template contentPage;

    @Location("components/context-selector")
    @Inject
    Template contextSelectorPage;

    @Location("components/custom-menus")
    @Inject
    Template customMenusPage;

    @Location("components/divider")
    @Inject
    Template dividerPage;

    @Location("components/empty-state")
    @Inject
    Template emptyStatePage;

    @Location("components/expandable-section")
    @Inject
    Template expandableSectionPage;

    @Location("components/helper-text")
    @Inject
    Template helperTextPage;

    @Location("components/hint")
    @Inject
    Template hintPage;

    @Location("components/icon")
    @Inject
    Template iconPage;

    @Location("components/label")
    @Inject
    Template labelPage;

    @Location("components/list")
    @Inject
    Template listPage;

    @Location("components/panel")
    @Inject
    Template panelPage;

    @Location("components/progress")
    @Inject
    Template progressPage;

    @Location("components/skeleton")
    @Inject
    Template skeletonPage;

    @Location("components/spinner")
    @Inject
    Template spinnerPage;

    @Location("components/timestamp")
    @Inject
    Template timestampPage;

    @Location("components/title")
    @Inject
    Template titlePage;

    @Location("components/truncate")
    @Inject
    Template truncatePage;

    @Location("components/data-list")
    @Inject
    Template dataListPage;

    @Location("components/description-list")
    @Inject
    Template descriptionListPage;

    @Location("components/form")
    @Inject
    Template formPage;

    @Location("components/form-control")
    @Inject
    Template formControlPage;

    @Location("components/form-select")
    @Inject
    Template formSelectPage;

    @Location("components/number-input")
    @Inject
    Template numberInputPage;

    @Location("components/radio")
    @Inject
    Template radioPage;

    @Location("components/switch")
    @Inject
    Template switchPage;

    @Location("components/text-area")
    @Inject
    Template textAreaPage;

    @Location("components/text-input")
    @Inject
    Template textInputPage;

    @Location("components/text-input-group")
    @Inject
    Template textInputGroupPage;

    @Location("components/toggle-group")
    @Inject
    Template toggleGroupPage;

    @Location("components/simple-list")
    @Inject
    Template simpleListPage;

    @Location("components/simple-file-upload")
    @Inject
    Template simpleFileUploadPage;

    @Location("components/tile")
    @Inject
    Template tilePage;

    @Location("components/date-picker")
    @Inject
    Template datePickerPage;

    @Location("components/dropdown")
    @Inject
    Template dropdownPage;

    @Location("components/drawer")
    @Inject
    Template drawerPage;

    @Location("components/inline-edit")
    @Inject
    Template inlineEditPage;

    @Location("components/input-group")
    @Inject
    Template inputGroupPage;

    @Location("components/jump-links")
    @Inject
    Template jumpLinksPage;

    @Location("components/menu")
    @Inject
    Template menuPage;

    @Location("components/menu-toggle")
    @Inject
    Template menuTogglePage;

    @Location("components/modal")
    @Inject
    Template modalPage;

    @Location("components/notification-badge")
    @Inject
    Template notificationBadgePage;

    @Location("components/notification-drawer")
    @Inject
    Template notificationDrawerPage;

    @Location("components/overflow-menu")
    @Inject
    Template overflowMenuPage;

    @Location("components/popover")
    @Inject
    Template popoverPage;

    @Location("components/tooltip")
    @Inject
    Template tooltipPage;

    @Location("components/progress-stepper")
    @Inject
    Template progressStepperPage;

    @Location("components/login-page")
    @Inject
    Template loginPagePage;

    @Location("components/masthead")
    @Inject
    Template mastheadPage;

    @Location("components/navigation")
    @Inject
    Template navigationPage;

    @Location("components/page")
    @Inject
    Template pagePage;

    @Location("components/pagination")
    @Inject
    Template paginationPage;

    @Location("components/search-input")
    @Inject
    Template searchInputPage;

    @Location("components/select")
    @Inject
    Template selectPage;

    @Location("components/sidebar")
    @Inject
    Template sidebarPage;

    @Location("components/skip-to-content")
    @Inject
    Template skipToContentPage;

    @Location("components/slider")
    @Inject
    Template sliderPage;

    @Location("components/tabs")
    @Inject
    Template tabsPage;

    @Location("components/toolbar")
    @Inject
    Template toolbarPage;

    @Location("components/tree-view")
    @Inject
    Template treeViewPage;

    @Location("components/wizard")
    @Inject
    Template wizardPage;

    @Location("components/application-launcher")
    @Inject
    Template applicationLauncherPage;

    @Location("components/date-and-time-picker")
    @Inject
    Template dateAndTimePickerPage;

    @Location("components/drag-and-drop")
    @Inject
    Template dragAndDropPage;

    @Location("components/dual-list-selector")
    @Inject
    Template dualListSelectorPage;

    @Location("components/multiple-file-upload")
    @Inject
    Template multipleFileUploadPage;

    @Location("components/options-menu")
    @Inject
    Template optionsMenuPage;

    @Location("components/password-generator")
    @Inject
    Template passwordGeneratorPage;

    @Location("components/password-strength")
    @Inject
    Template passwordStrengthPage;

    @Location("components/table")
    @Inject
    Template tablePage;

    @Location("components/time-picker")
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

    @Location("components/chart")
    @Inject
    Template chartPage;

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

    @GET
    @Path("/about-modal")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance aboutModal() {
        return aboutModalDemo.data("applicationName", "quarkus-pha");
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

        return accordionPage.data("applicationName", "quarkus-pha")
            .data("sampleItems", sampleItems);
    }

    @GET
    @Path("/action-list")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance actionList() {
        String iconEdit = "<svg fill=\"currentColor\" viewBox=\"0 0 16 16\" width=\"1em\" height=\"1em\" aria-hidden=\"true\"><path d=\"M11.013 1.427a1.75 1.75 0 012.474 0l1.086 1.086a1.75 1.75 0 010 2.474l-8.61 8.61c-.21.21-.47.364-.756.445l-3.251.93a.75.75 0 01-.927-.928l.929-3.25a1.75 1.75 0 01.445-.758l8.61-8.61z\"/></svg>";
        String iconClone = "<svg fill=\"currentColor\" viewBox=\"0 0 16 16\" width=\"1em\" height=\"1em\" aria-hidden=\"true\"><path d=\"M0 6.75C0 5.784.784 5 1.75 5h1.5a.75.75 0 010 1.5h-1.5a.25.25 0 00-.25.25v7.5c0 .138.112.25.25.25h7.5a.25.25 0 00.25-.25v-1.5a.75.75 0 011.5 0v1.5A1.75 1.75 0 019.25 16h-7.5A1.75 1.75 0 010 14.25v-7.5z\"/><path d=\"M5 1.75C5 .784 5.784 0 6.75 0h7.5C15.216 0 16 .784 16 1.75v7.5A1.75 1.75 0 0114.25 11h-7.5A1.75 1.75 0 015 9.25v-7.5zm1.75-.25a.25.25 0 00-.25.25v7.5c0 .138.112.25.25.25h7.5a.25.25 0 00.25-.25v-7.5a.25.25 0 00-.25-.25h-7.5z\"/></svg>";
        String iconTrash = "<svg fill=\"currentColor\" viewBox=\"0 0 16 16\" width=\"1em\" height=\"1em\" aria-hidden=\"true\"><path d=\"M6.5 1.75a.25.25 0 01.25-.25h2.5a.25.25 0 01.25.25V3h-3V1.75zm4.5 0V3h2.25a.75.75 0 010 1.5H2.75a.75.75 0 010-1.5H5V1.75C5 .784 5.784 0 6.75 0h2.5C10.216 0 11 .784 11 1.75zM4.496 6.675a.75.75 0 10-1.492.15l.66 6.6A1.75 1.75 0 005.405 15h5.19a1.75 1.75 0 001.741-1.575l.66-6.6a.75.75 0 00-1.492-.15l-.66 6.6a.25.25 0 01-.249.225h-5.19a.25.25 0 01-.249-.225l-.66-6.6z\"/></svg>";

        // 1. Single group — Primary + Secondary
        List<Map<String, Object>> singleGroupData = List.of(
            Map.of("items", List.of(
                Map.of("text", "Next", "variant", "primary"),
                Map.of("text", "Back", "variant", "secondary")
            ))
        );

        // 2. With icons — root-level pf-m-icons
        List<Map<String, Object>> iconsData = List.of(
            Map.of("items", List.of(
                Map.of("icon", true, "ariaLabel", "Edit", "iconSvg", iconEdit),
                Map.of("icon", true, "ariaLabel", "Clone", "iconSvg", iconClone),
                Map.of("icon", true, "ariaLabel", "Delete", "iconSvg", iconTrash)
            ))
        );

        // 3. Group icons — pf-m-icons on individual groups
        List<Map<String, Object>> groupIconsData = List.of(
            Map.of("items", List.of(
                Map.of("icon", true, "ariaLabel", "Edit", "iconSvg", iconEdit),
                Map.of("icon", true, "ariaLabel", "Clone", "iconSvg", iconClone)
            ), "icons", true),
            Map.of("items", List.of(
                Map.of("text", "Save", "variant", "primary")
            ))
        );

        // 4. Multiple groups
        List<Map<String, Object>> multiGroupData = List.of(
            Map.of("items", List.of(
                Map.of("text", "Action 1", "variant", "primary"),
                Map.of("text", "Action 2", "variant", "secondary")
            )),
            Map.of("items", List.of(
                Map.of("text", "Action 3", "variant", "secondary"),
                Map.of("text", "Action 4", "variant", "secondary")
            ))
        );

        // 5. Cancel button — forms/modals
        List<Map<String, Object>> cancelFormData = List.of(
            Map.of("items", List.of(
                Map.of("text", "Save", "variant", "primary"),
                Map.of("text", "Cancel", "variant", "link")
            ))
        );

        // 6. Cancel button — wizards
        List<Map<String, Object>> cancelWizardData = List.of(
            Map.of("items", List.of(
                Map.of("text", "Next", "variant", "primary"),
                Map.of("text", "Back", "variant", "secondary")
            )),
            Map.of("items", List.of(
                Map.of("text", "Cancel", "variant", "link")
            ))
        );

        // 7. Vertical
        List<Map<String, Object>> verticalData = List.of(
            Map.of("items", List.of(
                Map.of("text", "Save", "variant", "primary"),
                Map.of("text", "Cancel", "variant", "link")
            ))
        );

        return actionListPage.data("applicationName", "quarkus-pha")
            .data("singleGroupData", singleGroupData)
            .data("iconsData", iconsData)
            .data("groupIconsData", groupIconsData)
            .data("multiGroupData", multiGroupData)
            .data("cancelFormData", cancelFormData)
            .data("cancelWizardData", cancelWizardData)
            .data("verticalData", verticalData);
    }

    @GET
    @Path("/avatar")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance avatar() {
        return avatarPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/back-to-top")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance backToTop() {
        return backToTopPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/backdrop")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance backdrop() {
        return backdropPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/background-image")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance backgroundImage() {
        return backgroundImagePage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/badge")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance badge() {
        return badgePage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/banner")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance banner() {
        return bannerPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/brand")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance brand() {
        return brandPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/breadcrumb")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance breadcrumb() {
        List<Map<String, Object>> basicItems = List.of(
            Map.of("text", "Section home", "href", "#"),
            Map.of("text", "Section title", "href", "#"),
            Map.of("text", "Section title", "href", "#"),
            Map.of("text", "Section landing", "href", "#", "current", true)
        );

        List<Map<String, Object>> noHomeItems = List.of(
            Map.of("text", "Section home"),
            Map.of("text", "Section title", "href", "#"),
            Map.of("text", "Section title", "href", "#"),
            Map.of("text", "Section title", "href", "#"),
            Map.of("text", "Section landing", "href", "#", "current", true)
        );

        List<Map<String, Object>> headingItems = List.of(
            Map.of("text", "Section home", "href", "#"),
            Map.of("text", "Section title", "href", "#"),
            Map.of("text", "Section title", "href", "#"),
            Map.of("text", "Section landing", "href", "#", "heading", true)
        );

        return breadcrumbPage.data("applicationName", "quarkus-pha")
            .data("basicItems", basicItems)
            .data("noHomeItems", noHomeItems)
            .data("headingItems", headingItems);
    }

    @GET
    @Path("/button")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance button() {
        return buttonPage.data("applicationName", "quarkus-pha");
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

        return calendarMonthPage.data("applicationName", "quarkus-pha")
            .data("selectedMonth", "October").data("selectedYear", 2020).data("selectedWeeks", selectedWeeks)
            .data("rangeMonth", "October").data("rangeYear", 2020).data("rangeWeeks", rangeWeeks);
    }

    @GET
    @Path("/card")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance card() {
        return cardPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/checkbox")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance checkbox() {
        return checkboxPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/chip")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance chip() {
        return chipPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/clipboard-copy")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance clipboardCopy() {
        return clipboardCopyPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/code-block")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance codeBlock() {
        return codeBlockPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/code-editor")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance codeEditor() {
        return codeEditorPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/content")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance content() {
        return contentPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/context-selector")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance contextSelector() {
        return contextSelectorPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/custom-menus")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance customMenus() {
        return customMenusPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/divider")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance divider() {
        return dividerPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/empty-state")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance emptyState() {
        return emptyStatePage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/expandable-section")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance expandableSection() {
        return expandableSectionPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/helper-text")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance helperText() {
        return helperTextPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/hint")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance hint() {
        return hintPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/icon")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance icon() {
        return iconPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/label")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance label() {
        return labelPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/list")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance list() {
        return listPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/panel")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance panel() {
        return panelPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/progress")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance progress() {
        return progressPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/skeleton")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance skeleton() {
        return skeletonPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/spinner")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance spinner() {
        return spinnerPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/timestamp")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance timestamp() {
        return timestampPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/title")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance title() {
        return titlePage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/truncate")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance truncate() {
        return truncatePage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/data-list")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance dataList() {
        return dataListPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/description-list")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance descriptionList() {
        return descriptionListPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/form")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance form() {
        return formPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/form-control")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance formControl() {
        return formControlPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/form-select")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance formSelect() {
        return formSelectPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/number-input")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance numberInput() {
        return numberInputPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/radio")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance radio() {
        return radioPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/switch")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance switchComponent() {
        return switchPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/text-area")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance textArea() {
        return textAreaPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/text-input")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance textInput() {
        return textInputPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/text-input-group")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance textInputGroup() {
        return textInputGroupPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/toggle-group")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance toggleGroup() {
        return toggleGroupPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/simple-list")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance simpleList() {
        return simpleListPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/simple-file-upload")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance simpleFileUpload() {
        return simpleFileUploadPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/tile")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance tile() {
        return tilePage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/date-picker")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance datePicker() {
        return datePickerPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/dropdown")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance dropdown() {
        return dropdownPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/drawer")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance drawer() {
        return drawerPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/inline-edit")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance inlineEdit() {
        return inlineEditPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/input-group")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance inputGroup() {
        return inputGroupPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/jump-links")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance jumpLinks() {
        return jumpLinksPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/menu")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance menu() {
        return menuPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/menu-toggle")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance menuToggle() {
        return menuTogglePage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/modal")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance modal() {
        return modalPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/notification-badge")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance notificationBadge() {
        return notificationBadgePage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/notification-drawer")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance notificationDrawer() {
        return notificationDrawerPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/overflow-menu")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance overflowMenu() {
        return overflowMenuPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/popover")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance popover() {
        return popoverPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/tooltip")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance tooltip() {
        return tooltipPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/progress-stepper")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance progressStepper() {
        return progressStepperPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/login-page")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance loginPage() {
        return loginPagePage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/masthead")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance masthead() {
        return mastheadPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/navigation")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance navigation() {
        return navigationPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/page")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance page() {
        return pagePage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/pagination")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance pagination() {
        return paginationPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/search-input")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance searchInput() {
        return searchInputPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/select")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance selectComponent() {
        return selectPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/sidebar")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance sidebar() {
        return sidebarPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/skip-to-content")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance skipToContent() {
        return skipToContentPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/slider")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance slider() {
        return sliderPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/tabs")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance tabs() {
        return tabsPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/toolbar")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance toolbar() {
        return toolbarPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/tree-view")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance treeView() {
        return treeViewPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/wizard")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance wizard() {
        return wizardPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/application-launcher")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance applicationLauncher() {
        return applicationLauncherPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/date-and-time-picker")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance dateAndTimePicker() {
        return dateAndTimePickerPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/drag-and-drop")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance dragAndDrop() {
        return dragAndDropPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/dual-list-selector")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance dualListSelector() {
        return dualListSelectorPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/multiple-file-upload")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance multipleFileUpload() {
        return multipleFileUploadPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/options-menu")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance optionsMenu() {
        return optionsMenuPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/password-generator")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance passwordGenerator() {
        return passwordGeneratorPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/password-strength")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance passwordStrength() {
        return passwordStrengthPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/table")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance tableComponent() {
        return tablePage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/time-picker")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance timePicker() {
        return timePickerPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/rectangle-selection")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance rectangleSelection() {
        return rectangleSelectionPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/map")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance map() {
        return mapPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/document-editor")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance documentEditor() {
        return documentEditorPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/chart")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance chart() {
        return chartPage.data("applicationName", "quarkus-pha");
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

        return alertPage.data("applicationName", "quarkus-pha")
            .data("sampleActionLinks", sampleActionLinks);
    }

    @GET
    @Path("/live-search")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance liveSearch() {
        return liveSearchPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/infinite-scroll")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance infiniteScroll() {
        return infiniteScrollPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/click-to-load")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance clickToLoad() {
        return clickToLoadPage.data("applicationName", "quarkus-pha");
    }

    @GET
    @Path("/lazy-modal")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance lazyModal() {
        return lazyModalPage.data("applicationName", "quarkus-pha");
    }
}
