package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.InlineEdit;

/**
 * Demo data for the inline-edit examples — all examples on
 * /components/inline-edit are populated from these models (five shapes with
 * generated Alpine). Globals so the standalone example route (which renders
 * templates without data) can see them. Each is mirrored by a snippet in
 * resources/code-samples/inline-edit/ served on the example card's Java tab —
 * keep them in sync when editing.
 */
@TemplateGlobal
public class InlineEditDemoData {

    public static InlineEdit demoIeBasic = InlineEdit.of("ie-basic", "Click the pencil to edit me").build();

    public static InlineEdit demoIeFreeForm = InlineEdit.freeForm("ie-free-form",
            "Free form text — click and type to edit.", "Editable free form text").build();

    public static InlineEdit demoIeMultiple = InlineEdit.multiple("ie-multiple").style("max-width: 32rem")
            .field("Name", "Jane Smith").field("Team", "Platform").field("Role", "Editor").build();

    public static InlineEdit demoIeValidated = InlineEdit.validated("ie-validated", "Display name",
            "Type to validate…", "Display name is required.").style("max-width: 28rem").build();

    public static InlineEdit demoIeLabel = InlineEdit.labeled("ie-label", "Title",
            "Senior Engineer", "Edit title").style("max-width: 28rem").build();
}
