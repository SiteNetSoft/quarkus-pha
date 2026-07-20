package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Masthead;

/**
 * Demo data for the masthead examples — the examples on /components/masthead
 * are populated from these models (basic and mixed-content stay hand-written:
 * their content areas compose toolbars / live button includes, a slot
 * use-case). Globals so the standalone example route (which renders templates
 * without data) can see them. Each is mirrored by a snippet in
 * resources/code-samples/masthead/ served on the example card's Java tab —
 * keep them in sync when editing.
 */
@TemplateGlobal
public class MastheadDemoData {

    public static Masthead demoMhCustomLogo = Masthead.of("mh-custom-logo")
            .toggle("Global navigation")
            .logoImages("#", "PHA Showcase home", "/web/images/quarkus-pha-logo.svg",
                    "/web/images/quarkus-pha-logo-dark.svg", "Quarkus PHA", "36px")
            .content("Content").build();

    public static Masthead demoMhInline = Masthead.of("mh-inline").displayInline()
            .toggle("Global navigation").logoText("PHA Showcase")
            .content("Brand and content sit inline (side by side) — pf-m-display-inline.").build();

    public static Masthead demoMhStack = Masthead.of("mh-stack").displayStack()
            .brandHtml("<strong>Display stack — brand on top</strong>")
            .content("Content row sits below the brand row when display-stack is active.").build();

    public static Masthead demoMhResponsive = Masthead.of("mh-responsive")
            .displayStack().modifiers("pf-m-display-inline-on-lg")
            .toggle("Global navigation").logoText("PHA Showcase")
            .content("Stacked by default, switching to inline from the <code>lg</code> breakpoint up —"
                    + " pf-m-display-stack pf-m-display-inline-on-lg. Resize the window to see it switch.")
            .build();

    public static Masthead demoMhInsets = Masthead.of("mh-insets")
            .modifiers("pf-m-inset-sm pf-m-inset-md-on-md pf-m-inset-lg-on-lg pf-m-inset-xl-on-xl")
            .style("outline: 1px dashed var(--pf-t--global--border--color--default, #ccc)")
            .toggle("Global navigation").logoText("PHA Showcase")
            .content("Inset padding scales up with the breakpoint (sm → md → lg → xl). The dashed outline"
                    + " marks the masthead edge so the growing inset is visible.")
            .build();
}
