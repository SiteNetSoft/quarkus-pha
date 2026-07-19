import org.sitenetsoft.quarkus.pha.model.*;

Table table = Table.builder()
        .id("tbl-sticky-footer").ariaLabel("Table with a sticky footer")
        .stickyFooter().scrollable("max-block-size: 13rem; overflow: auto")
        .column("Invoice").column("Customer").column("Amount")
        .row("INV-1041", "Acme Corp", "$1,200")
        .row("INV-1042", "Globex", "$860")
        .row("INV-1043", "Initech", "$2,430")
        .row("INV-1044", "Umbrella", "$540")
        .row("INV-1045", "Stark Industries", "$3,100")
        .row("INV-1046", "Wayne Enterprises", "$1,780")
        .row("INV-1047", "Hooli", "$920")
        .row("INV-1048", "Vandelay", "$610")
        .footer(TableCell.text("Total").asRowHeader().withColspan(2), TableCell.text("$11,440"))
        .build();

// Template side, with `table` in the template data:
// {#include components/data-display/table table=table /}
