package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

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
}
