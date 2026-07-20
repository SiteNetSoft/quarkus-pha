import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Popover> popovers = List.of(
        Popover.of("po-alert-custom").trigger("Show custom popover").variant("custom")
                .title("Custom popover").dismissable()
                .body("Severity styling via pf-m-custom; the custom variant carries no title icon.").build(),
        Popover.of("po-alert-info").trigger("Show info popover").variant("info")
                .title("Info popover").titleIcon("fa:circle-info").dismissable()
                .body("Severity styling via pf-m-info; the title icon picks up the status color.").build(),
        Popover.of("po-alert-success").trigger("Show success popover").variant("success")
                .title("Success popover").titleIcon("fa:circle-check").dismissable()
                .body("Severity styling via pf-m-success; the title icon picks up the status color.").build(),
        Popover.of("po-alert-warning").trigger("Show warning popover").variant("warning")
                .title("Warning popover").titleIcon("fa:exclamation-triangle").dismissable()
                .body("Severity styling via pf-m-warning; the title icon picks up the status color.").build());

// Template side, with the data in scope:
// {#for p in popovers}{#include components/feedback/popover popover=p /}{/for}
