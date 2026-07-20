package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Truncate;

/**
 * Demo data for the truncate examples — the examples on /components/truncate
 * are populated from these models (custom-tooltip-position stays hand-written:
 * it composes live tooltip markup around the truncation). Globals so the
 * standalone example route (which renders templates without data) can see
 * them. Each is mirrored by a snippet in resources/code-samples/truncate/
 * served on the example card's Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class TruncateDemoData {

    public static Truncate demoTruncEnd = Truncate.of("This is a long string of text that will be"
            + " truncated at the end of the line when the container is narrow.")
            .id("trunc-end").build();

    public static Truncate demoTruncStart = Truncate.of("/home/user/projects/very/deep/nested/path/to/a/file.txt")
            .id("trunc-start").position("start").build();

    public static Truncate demoTruncMiddle = Truncate.of("abcdefghijklmnopqrstuvwxyz1234567890")
            .id("trunc-middle").position("middle").trailingNumChars(8).build();

    public static Truncate demoTruncMaxChars = Truncate.of("Vestibulum interdum risus et enim faucibus,"
            + " sit amet molestie est accumsan."
            + " redhat_logo_black_and_white_reversed_simple_with_fedora_container.zip")
            .id("trunc-max-chars").maxChars(20).build();

    public static Truncate demoTruncLink = Truncate.of("Vestibulum interdum risus et enim faucibus,"
            + " sit amet molestie est accumsan."
            + " redhat_logo_black_and_white_reversed_simple_with_fedora_container.zip")
            .id("trunc-link").link("#").build();
}
