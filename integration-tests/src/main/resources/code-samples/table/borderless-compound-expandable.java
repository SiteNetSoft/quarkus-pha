import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-borderless-compound").ariaLabel("Borderless compound expandable table example")
        .borderless().gridMd()
        .column("Registry").column("Images").column("Tags").column("Visibility")
        .row(TableCell.text("registry.internal/platform").asRowHeader(),
                TableCell.compound("12 images", "images",
                        "Most pulled: pha-runtime (2.1K/day), pha-gateway (900/day). 3 images unused for 90+ days."),
                TableCell.compound("48 tags", "tags",
                        "Latest: 2.15.1, 2.15.0, 2.14.3. Untagged manifests are garbage-collected weekly."),
                TableCell.text("Private"))
        .row(TableCell.text("registry.internal/sandbox").asRowHeader(),
                TableCell.compound("5 images", "images",
                        "Scratch images for experiments; auto-deleted after 30 days of inactivity."),
                TableCell.compound("9 tags", "tags",
                        "Tags follow the branch name; only main is retained past a week."),
                TableCell.text("Team"))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
