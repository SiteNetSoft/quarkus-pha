package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.ClipboardCopy;

/**
 * Demo data for the clipboard-copy examples — all examples on
 * /components/clipboard-copy are populated from these models. Globals so the
 * standalone example route (which renders templates without data) can see
 * them. Each is mirrored by a snippet in resources/code-samples/clipboard-copy/
 * served on the example card's Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class ClipboardCopyDemoData {

    public static ClipboardCopy demoCcBasic = ClipboardCopy
            .of("cc-basic", "Copy this string to your clipboard").build();

    public static ClipboardCopy demoCcExpandable = ClipboardCopy
            .of("cc-expandable", "One-line summary").expandable()
            .expandedText("Full multi-line content that becomes visible when the user clicks the toggle."
                    + " Useful for long commands or configuration snippets.").build();

    public static ClipboardCopy demoCcExpandedArray = ClipboardCopy
            .of("cc-expanded-array", "ssh-rsa AAAA... (3 keys)").expanded()
            .expandedHtml("""
                    ssh-rsa AAAAB3Nza...key-one user@host<br />
                    ssh-rsa AAAAB3Nza...key-two user@host<br />
                    ssh-rsa AAAAB3Nza...key-three user@host""").build();

    public static ClipboardCopy demoCcInlineCompact = ClipboardCopy
            .of("cc-inline-compact", "2.3.4-2-redhat").inline().build();

    public static ClipboardCopy demoCcInlineTruncate = ClipboardCopy
            .of("cc-inline-truncate", "https://app.example.com/api/v2/deployments/3f9c2a17-55de-4d1b-9d6f")
            .inline().block()
            .textStyle("overflow: hidden; text-overflow: ellipsis; white-space: nowrap;"
                    + " display: inline-block; max-width: 170px; vertical-align: bottom").build();

    public static ClipboardCopy demoCcInlineAction = ClipboardCopy
            .of("cc-inline-action", "oc new-app httpd-example").inline().code()
            .extraAction("fa:play", "Run in terminal").build();

    public static ClipboardCopy demoCcInline = ClipboardCopy
            .of("cc-inline", "npm install").inline().code().build();

    public static ClipboardCopy demoCcJsonPre = ClipboardCopy
            .of("cc-json-pre", "{ \"kind\": \"ConfigMap\", ... }").expanded()
            .expandedHtml("<pre>{ \"kind\": \"ConfigMap\", \"metadata\": { \"name\": \"app-config\" },"
                    + " \"data\": { \"LOG_LEVEL\": \"info\" } }</pre>").build();

    public static ClipboardCopy demoCcRoExpandedDefault = ClipboardCopy
            .of("cc-ro-expanded-default", "Starts expanded").readonly().expandable().expanded()
            .expandedText("This panel is open on load thanks to expanded=true.").build();

    public static ClipboardCopy demoCcRoExpanded = ClipboardCopy
            .of("cc-ro-expanded", "Read-only and expandable").readonly().expandable()
            .expandedText("The expandable panel content — still read-only.").build();

    public static ClipboardCopy demoCcReadonly = ClipboardCopy
            .of("cc-readonly", "ghp_a1b2c3d4e5f6g7h8i9j0k1l2m3n4o5p6q7").readonly().build();
}
