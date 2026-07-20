package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.TextArea;

import java.util.List;

/**
 * Demo data for the text-area examples — the examples on /components/text-area
 * are populated from these models (auto-resizing, not-resizable and validated
 * stay hand-written: live Alpine behavior / inline resize:none styling the
 * shell does not model). Globals so the standalone example route (which
 * renders templates without data) can see them. Each is mirrored by a snippet
 * in resources/code-samples/text-area/ served on the example card's Java tab —
 * keep them in sync when editing.
 */
@TemplateGlobal
public class TextAreaDemoData {

    public static TextArea demoTaBasic = TextArea.of("ta-basic")
            .placeholder("Enter your message…").rows(4).build();

    public static TextArea demoTaDisabledSolo = TextArea.of("ta-disabled-solo")
            .disabled().value("Disabled text area").build();

    public static List<TextArea> demoTasDisabledReadonly = List.of(
            TextArea.of("ta-disabled").rows(3).value("Disabled content").disabled().build(),
            TextArea.of("ta-readonly").rows(3).value("Read-only content").readonly().build());

    public static TextArea demoTaResizeH = TextArea.of("ta-resize-h")
            .resize("horizontal").rows(3).placeholder("Drag the corner to resize horizontally").build();

    public static TextArea demoTaInvalid = TextArea.of("ta-invalid")
            .rows(3).value("Too short.").validated("error").build();

    public static TextArea demoTaReadOnlySolo = TextArea.of("ta-read-only-solo")
            .readonly().value("Read-only content stays selectable but not editable.").build();

    public static TextArea demoTaResizeV = TextArea.of("ta-resize-v")
            .resize("vertical").rows(3).placeholder("Drag the corner to resize vertically").build();
}
