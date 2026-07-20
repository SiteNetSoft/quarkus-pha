package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.ExpandableSection;

/**
 * Demo data for the expandable-section examples — the examples on
 * /components/expandable-section are populated from these models
 * (custom-toggle stays hand-written: its toggle composes live icon/badge
 * markup, a slot use-case).
 * Globals so the standalone example route (which renders templates without
 * data) can see them. Each is mirrored by a snippet in
 * resources/code-samples/expandable-section/ served on the example card's
 * Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class ExpandableSectionDemoData {

    public static ExpandableSection demoEsCollapsed = ExpandableSection
            .of("es-collapsed", "Show advanced settings")
            .content("\n<p>Advanced settings only experienced users should touch — caching, retry policies, telemetry verbosity.</p>\n"
                    + "<p>Default values work for most use cases.</p>\n")
            .build();

    public static ExpandableSection demoEsExpanded = ExpandableSection
            .of("es-expanded", "Section open by default").asExpanded()
            .content("\n<p>Call <code>asExpanded()</code> on the builder to start the section in the open state.</p>\n")
            .build();

    public static ExpandableSection demoEsDetached = ExpandableSection
            .of("es-detached", "Show build details").asDetached()
            .content("<p>Build log details appear above the toggle that controls them.</p>")
            .build();

    public static ExpandableSection demoEsDisclosure = ExpandableSection
            .of("es-disclosure", "Disclosure variation").asDisclosure()
            .content("<p>The disclosure variation uses the large display size with a width limit.</p>")
            .build();

    public static ExpandableSection demoEsDynamicToggleText = ExpandableSection
            .of("es-dynamic-toggle-text", null)
            .dynamicToggle("Show more", "Show less")
            .content("<p>This content is revealed by the toggle, whose label flips between Show more and Show less.</p>")
            .build();

    public static ExpandableSection demoEsHeadingSemantics = ExpandableSection
            .of("es-heading-semantics", "Section wrapped in an h2").headingLevel("h2")
            .content("\n<p>The toggle sits inside a real heading element so the section lands in the page outline.</p>\n")
            .build();

    public static ExpandableSection demoEsIndented = ExpandableSection
            .of("es-indented", "Show indented content").asIndented()
            .content("<p>The content aligns under the toggle text instead of the icon (pf-m-indented).</p>")
            .build();

    public static ExpandableSection demoEsTruncateExpansion = ExpandableSection
            .of("es-truncate-expansion", null).asTruncate()
            .dynamicToggle("Show more", "Show less")
            .style("max-width: 480px")
            .content("\n<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus pretium est a porttitor vehicula. Quisque vel commodo urna. Morbi mattis rutrum ante, id vehicula ex accumsan ut. Morbi viverra, eros vel porttitor facilisis, eros purus aliquet erat, nec lobortis felis elit pulvinar sem. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus pretium est a porttitor vehicula. Quisque vel commodo urna. Morbi mattis rutrum ante, id vehicula ex accumsan ut. Morbi viverra, eros vel porttitor facilisis, eros purus aliquet erat, nec lobortis felis elit pulvinar sem.</p>\n")
            .build();
}
