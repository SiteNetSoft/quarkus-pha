import java.util.List;

import org.sitenetsoft.quarkus.pha.model.*;

List<DescriptionListGroup> groups = List.of(
        DescriptionListGroup.of("Name", "Example"),
        DescriptionListGroup.link("Namespace", "mary-test"),
        DescriptionListGroup.of("Labels", "example"),
        DescriptionListGroup.linkButton("Pod selector", "app=MyApp", "fa:circle-plus"),
        DescriptionListGroup.of("Annotation", "2 Annotations"));

DescriptionList list = DescriptionList.builder()
        .id("dl-large-display-size-and-card").ariaLabel("Description list large-display-size-and-card")
        .displayLg().columns("2-col-on-lg").cardGroups().groups(groups)
        .build();

// Template side, with `list` in the template data:
// {#include components/data-display/description-list list=list /}
