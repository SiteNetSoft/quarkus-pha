import org.sitenetsoft.quarkus.pha.model.*;

DescriptionList list = DescriptionList.builder()
        .id("dl-icons-on-terms").ariaLabel("Description list icons-on-terms")
        .group(DescriptionListGroup.of("Name", "Example").termIcon("fa:cube"))
        .group(DescriptionListGroup.link("Namespace", "mary-test").termIcon("fa:book"))
        .group(DescriptionListGroup.of("Labels", "example").termIcon("fa:key"))
        .group(DescriptionListGroup.linkButton("Pod selector", "app=MyApp", "fa:circle-plus").termIcon("fa:globe"))
        .group(DescriptionListGroup.of("Annotation", "2 Annotations").termIcon("fa:flag"))
        .build();

// Template side, with `list` in the template data:
// {#include components/data-display/description-list list=list /}
