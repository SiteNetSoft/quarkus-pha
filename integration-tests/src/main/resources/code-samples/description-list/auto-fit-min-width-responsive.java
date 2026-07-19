import java.util.List;

import org.sitenetsoft.quarkus.pha.model.*;

List<DescriptionListGroup> groups = List.of(
        DescriptionListGroup.of("Name", "Example"),
        DescriptionListGroup.link("Namespace", "mary-test"),
        DescriptionListGroup.of("Labels", "example"),
        DescriptionListGroup.linkButton("Pod selector", "app=MyApp", "fa:circle-plus"),
        DescriptionListGroup.of("Annotation", "2 Annotations"));

DescriptionList list = DescriptionList.builder()
        .id("dl-auto-fit-min-width-responsive").ariaLabel("Description list auto-fit-min-width-responsive")
        .autoFit()
        .styleVars("--pf-v6-c-description-list--GridTemplateColumns--min-on-md: 100px; "
                + "--pf-v6-c-description-list--GridTemplateColumns--min-on-lg: 150px; "
                + "--pf-v6-c-description-list--GridTemplateColumns--min-on-xl: 200px; "
                + "--pf-v6-c-description-list--GridTemplateColumns--min-on-2xl: 300px")
        .groups(groups)
        .build();

// Template side, with `list` in the template data:
// {#include components/data-display/description-list list=list /}
