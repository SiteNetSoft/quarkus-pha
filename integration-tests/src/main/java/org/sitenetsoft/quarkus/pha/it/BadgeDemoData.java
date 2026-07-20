package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Badge;

import java.util.List;

/**
 * Demo data for the badge examples — every example on /components/badge is
 * populated from these Badge models. Globals so the standalone example route
 * can see them; each is mirrored by a snippet in
 * resources/code-samples/badge/ served on the example card's Java tab.
 */
@TemplateGlobal
public class BadgeDemoData {

    public static List<Badge> demoBadgesUnread = List.of(
            Badge.of("7").id("badge-unread-7").variant("unread").build(),
            Badge.of("24").id("badge-unread-24").variant("unread").build(),
            Badge.of("999+").id("badge-unread-999").variant("unread").build());

    public static List<Badge> demoBadgesRead = List.of(
            Badge.of("7").id("badge-read-7").variant("read").build(),
            Badge.of("24").id("badge-read-24").variant("read").build(),
            Badge.of("999+").id("badge-read-999").variant("read").build());

    public static List<Badge> demoBadgesDisabled = List.of(
            Badge.of("7").id("badge-disabled-7").variant("disabled").build(),
            Badge.of("24").id("badge-disabled-24").variant("disabled").build(),
            Badge.of("999+").id("badge-disabled-999").variant("disabled").build());

    public static Badge demoBadgeScreenReader = Badge.of("3").id("badge-sr").variant("unread")
            .screenReaderText("unread notifications").build();
}
