import org.sitenetsoft.quarkus.pha.model.*;

ClipboardCopy clipboardCopy = ClipboardCopy
        .of("cc-expanded-array", "ssh-rsa AAAA... (3 keys)").expanded()
        .expandedHtml("""
                ssh-rsa AAAAB3Nza...key-one user@host<br />
                ssh-rsa AAAAB3Nza...key-two user@host<br />
                ssh-rsa AAAAB3Nza...key-three user@host""").build();

// Template side, with the data in scope:
// {#include components/forms/clipboard-copy clipboardCopy=clipboardCopy /}
