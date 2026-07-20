package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Panel;

/**
 * Demo data for the panel examples — the examples on /components/panel are
 * populated from these models. Globals so the standalone example route (which
 * renders templates without data) can see them. Each is mirrored by a snippet
 * in resources/code-samples/panel/ served on the example card's Java tab —
 * keep them in sync when editing.
 */
@TemplateGlobal
public class PanelDemoData {

    public static Panel demoPnBasic = Panel.of("panel-basic")
            .body("Main body content of the panel.").build();

    public static Panel demoPnBordered = Panel.of("panel-bordered").bordered()
            .header("<strong>Bordered panel</strong>")
            .body("The <code class=\"ws-code\">.bordered()</code> builder call applies"
                    + " <code class=\"ws-code\">pf-m-bordered</code> — outlines the panel with a 1px border.")
            .build();

    public static Panel demoPnFooter = Panel.of("panel-footer")
            .body("Main body sits above the footer. The footer gets PF's automatic top border.")
            .footer("Footer content — typically actions or summary text.")
            .build();

    public static Panel demoPnHeaderFooter = Panel.of("panel-header-footer")
            .header("<strong>Header</strong>")
            .body("Body sandwiched between header and footer.")
            .footer("Footer")
            .build();

    public static Panel demoPnHeader = Panel.of("panel-header")
            .header("<strong>Panel header</strong>")
            .body("Main body sits below the header. PF v6 doesn't auto-insert a divider —"
                    + " add one if the visual separation matters.")
            .build();

    public static Panel demoPnNoBody = Panel.of("panel-no-body")
            .main("Direct child of panel-main — no panel-main-body wrapper."
                    + " The container provides no extra padding here.")
            .build();

    public static Panel demoPnPill = Panel.of("pn-pill").pill().style("max-width: 400px")
            .body("<code class=\"ws-code\">pf-m-pill</code> — rounded pill styling on the panel.")
            .build();

    public static Panel demoPnRaised = Panel.of("panel-raised").raised()
            .header("<strong>Raised panel</strong>")
            .body("The <code class=\"ws-code\">.raised()</code> builder call applies"
                    + " <code class=\"ws-code\">pf-m-raised</code> — adds a drop shadow that lifts"
                    + " the panel off the background.")
            .build();

    public static Panel demoPnScrollAuto = Panel.of("pn-scroll-auto")
            .scrollableAutoHeight().bordered().style("height: 100%").focusableMain()
            .body("""
                    <p>Row 1</p>
                    <p>Row 2</p>
                    <p>Row 3</p>
                    <p>Row 4</p>
                    <p>Row 5</p>
                    <p>Row 6</p>
                    <p>Row 7</p>
                    <p>Row 8</p>
                    <p>Row 9</p>
                    <p>Row 10</p>""")
            .build();

    public static Panel demoPnScrollable = Panel.of("panel-scrollable")
            .scrollable().bordered().maxHeight("16rem")
            .body("""
                    <p>
                      Scrollable panels pair <code class="ws-code">.scrollable()</code> on the builder with
                      <code class="ws-code">.maxHeight("16rem")</code>. The body overflows the constrained height and grows
                      a scrollbar.
                    </p>
                    <p>Filler 1 — Lorem ipsum dolor sit amet, consectetur adipiscing elit.</p>
                    <p>Filler 2 — Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.</p>
                    <p>Filler 3 — Ut enim ad minim veniam, quis nostrud exercitation ullamco.</p>
                    <p>Filler 4 — Duis aute irure dolor in reprehenderit in voluptate velit esse.</p>
                    <p>Filler 5 — Excepteur sint occaecat cupidatat non proident, sunt in culpa.</p>
                    <p>Filler 6 — Sunt in culpa qui officia deserunt mollit anim id est laborum.</p>""")
            .build();

    public static Panel demoPnScrollableHf = Panel.of("panel-scrollable-hf")
            .scrollable().bordered().maxHeight("14rem")
            .header("<strong>Sticky header</strong>")
            .body("""
                    <p>
                      The header and footer stay in place while panel-main scrolls. Useful for long-form content with persistent context
                      (filter chips above, action buttons below).
                    </p>
                    <p>Row 1 — placeholder content.</p>
                    <p>Row 2 — placeholder content.</p>
                    <p>Row 3 — placeholder content.</p>
                    <p>Row 4 — placeholder content.</p>
                    <p>Row 5 — placeholder content.</p>
                    <p>Row 6 — placeholder content.</p>
                    <p>Row 7 — placeholder content.</p>
                    <p>Row 8 — placeholder content.</p>""")
            .footer("Sticky footer — actions live here.")
            .build();

    public static Panel demoPnSecondary = Panel.of("panel-secondary").secondary()
            .header("<strong>Secondary panel</strong>")
            .body("The <code class=\"ws-code\">.secondary()</code> builder call applies"
                    + " <code class=\"ws-code\">pf-m-secondary</code> — uses the PF v6 secondary background"
                    + " token, useful for nested panels or aside content.")
            .build();
}
