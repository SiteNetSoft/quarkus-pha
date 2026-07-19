import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-borderless").ariaLabel("Borderless table example").borderless()
        .column("Service").column("Region").column("Uptime")
        .row("api-gateway", "us-east-1", "99.99%")
        .row("payments", "eu-west-1", "99.97%")
        .row("search", "ap-southeast-1", "99.95%")
        .row("notifications", "us-west-2", "99.92%")
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
