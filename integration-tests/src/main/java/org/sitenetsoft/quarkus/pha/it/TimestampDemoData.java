package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Timestamp;

import java.util.List;

/**
 * Demo data for the timestamp examples — the examples on /components/timestamp
 * are populated from these models. Globals so the standalone example route
 * (which renders templates without data) can see them. Each is mirrored by a
 * snippet in resources/code-samples/timestamp/ served on the example card's
 * Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class TimestampDemoData {

    public static Timestamp demoTsBasic = Timestamp.of("May 20, 2026 at 2:30 PM")
            .id("ts-basic").datetime("2026-05-20T14:30:00Z").build();

    public static List<Timestamp> demoTssFormats = List.of(
            Timestamp.of("Wednesday, May 20, 2026 at 2:30:00 PM UTC")
                    .id("ts-format-full").datetime("2026-05-20T14:30:00Z").build(),
            Timestamp.of("May 20, 2026 at 2:30:00 PM UTC")
                    .id("ts-format-long").datetime("2026-05-20T14:30:00Z").build(),
            Timestamp.of("May 20, 2026, 2:30 PM")
                    .id("ts-format-medium").datetime("2026-05-20T14:30:00Z").build(),
            Timestamp.of("5/20/26, 2:30 PM")
                    .id("ts-format-short").datetime("2026-05-20T14:30:00Z").build());

    public static Timestamp demoTsCustomContent = Timestamp.of("3 weeks ago")
            .id("ts-custom-content").datetime("2026-05-20T14:30:00Z").build();

    public static Timestamp demoTsCustomFormat = Timestamp.of("2026-05-20 · 14:30 UTC")
            .id("ts-custom-format").datetime("2026-05-20T14:30:00Z").build();

    public static Timestamp demoTsInline = Timestamp.of("2 hours ago")
            .id("ts-inline").datetime("2026-05-20T14:30:00Z").inline().build();

    public static Timestamp demoTsTooltip = Timestamp.of("2 hours ago")
            .id("ts-tooltip").datetime("2026-05-20T14:30:00Z")
            .tooltip("2026-05-20 14:30:00 UTC").build();
}
