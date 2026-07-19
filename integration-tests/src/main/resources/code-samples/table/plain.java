import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-plain").ariaLabel("Plain table example").gridMd().modifier("pf-m-plain")
        .caption("Q2 releases by service")
        .column("Service").column("Version").column("Released").column("Owner")
        .row("api-gateway", "2.14.0", "Apr 3", "Platform")
        .row("payments", "1.9.2", "May 12", "Commerce")
        .row("search", "3.1.0", "Jun 20", "Discovery")
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
