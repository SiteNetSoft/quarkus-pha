import org.sitenetsoft.quarkus.pha.model.*;

ClipboardCopy clipboardCopy = ClipboardCopy
        .of("cc-expandable", "One-line summary").expandable()
        .expandedText("Full multi-line content that becomes visible when the user clicks the toggle."
                + " Useful for long commands or configuration snippets.").build();

// Template side, with the data in scope:
// {#include components/forms/clipboard-copy clipboardCopy=clipboardCopy /}
