import org.sitenetsoft.quarkus.pha.model.*;

Hint hint = Hint.of("ht-content").title("Hint with action")
        .body("Need to migrate? Read the migration guide before upgrading.")
        .footerHtml("<a class=\"pf-v6-c-button pf-m-link pf-m-inline\" href=\"#\">"
                + "<span class=\"pf-v6-c-button__text\">Read the guide</span></a>")
        .build();

// Template side, with the data in scope:
// {#include components/feedback/hint hint=hint /}
