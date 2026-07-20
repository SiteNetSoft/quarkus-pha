package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Drawer;

/**
 * Demo data for the drawer examples — the examples on /components/drawer are
 * populated from these models (secondary-background and focus-trap stay
 * hand-written: scope-sharing chrome / advanced attrs compositions). Globals
 * so the standalone example route (which renders templates without data) can
 * see them. Each is mirrored by a snippet in resources/code-samples/drawer/
 * served on the example card's Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class DrawerDemoData {

    private static final String FOCUS_HEAD =
            "<span :tabindex=\"expanded ? '0' : '-1'\" x-ref=\"focus\">Drawer panel header</span>";
    private static final String PLAIN_HEAD = "<span>Drawer panel header</span>";

    public static Drawer demoDrBasic = Drawer.of("dr-basic").style("min-height: 240px")
            .toggleTextSwap("Open drawer", "Close drawer")
            .contentText("<p style=\"margin-top: 1rem\">Main page content. Click the button to slide"
                    + " the drawer panel in from the right.</p>")
            .panelSecondary().head("<h2>Panel title</h2>")
            .panelBody("<p>Drawer panel content — details, edit form, related items.</p>", true, false)
            .build();

    public static Drawer demoDrBasicInline = Drawer.of("dr-basic-inline").inline().style("min-height: 240px")
            .toggle("Toggle drawer")
            .contentText("<p style=\"margin-top: 1rem\"><code class=\"ws-code\">pf-m-inline</code> — the panel"
                    + " shares space with the content instead of overlapping it; no backdrop semantics.</p>")
            .head(FOCUS_HEAD).build();

    public static Drawer demoDrBreakpoint = Drawer.of("dr-breakpoint").style("min-height: 240px")
            .toggleFocus("Toggle drawer")
            .contentText("<p style=\"margin-top: 1rem\">The panel width is set with"
                    + " <code class=\"ws-code\">pf-m-width-33</code> — width modifiers (25/33/50/66/75/100)"
                    + " also come in -on-lg / -on-xl forms.</p>")
            .panelWidths("pf-m-width-33").head(FOCUS_HEAD).build();

    public static Drawer demoDrInlinePanelEnd = Drawer.of("dr-inline-panel-end").inline().style("min-height: 240px")
            .toggleFocus("Toggle drawer")
            .contentText("<p style=\"margin-top: 1rem\">Inline drawer, panel at the end —"
                    + " <code class=\"ws-code\">pf-m-inline</code>.</p>")
            .head(FOCUS_HEAD).build();

    public static Drawer demoDrInlinePanelStart = Drawer.of("dr-inline-panel-start").inline().panelLeft()
            .style("min-height: 240px").toggleFocus("Toggle drawer")
            .contentText("<p style=\"margin-top: 1rem\">Inline drawer with the panel at the start (left) —"
                    + " <code class=\"ws-code\">pf-m-inline pf-m-panel-left</code>.</p>")
            .head(FOCUS_HEAD).build();

    public static Drawer demoDrModifiedContentPadding = Drawer.of("dr-modified-content-padding")
            .style("min-height: 240px").toggleFocus("Toggle drawer")
            .contentText("<p style=\"margin-top: 1rem\">Content body with"
                    + " <code class=\"ws-code\">pf-m-padding</code> — drawer content bodies have no"
                    + " padding by default.</p>")
            .head(FOCUS_HEAD).build();

    public static Drawer demoDrModifiedPanelPadding = Drawer.of("dr-modified-panel-padding")
            .style("min-height: 240px").toggleFocus("Toggle drawer")
            .contentText("<p style=\"margin-top: 1rem\">The panel body opts out of its default padding"
                    + " with <code class=\"ws-code\">pf-m-no-padding</code>.</p>")
            .head(FOCUS_HEAD)
            .panelBody("Drawer panel body with no padding", false, true).build();

    public static Drawer demoDrPanelBottom = Drawer.of("dr-panel-bottom").panelBottom().style("height: 280px")
            .toggleFocus("Toggle drawer")
            .contentText("<p style=\"margin-top: 1rem\">The panel slides up from the bottom —"
                    + " <code class=\"ws-code\">pf-m-panel-bottom</code> on the drawer root. The drawer"
                    + " needs an explicit height for a bottom panel to be visible.</p>")
            .head(FOCUS_HEAD).build();

    public static Drawer demoDrPanelEnd = Drawer.of("dr-panel-end").style("min-height: 240px")
            .toggleFocus("Toggle drawer")
            .contentText("<p style=\"margin-top: 1rem\">Lorem ipsum dolor sit amet, consectetur adipiscing"
                    + " elit. Phasellus pretium est a porttitor vehicula. Quisque vel commodo urna. Morbi"
                    + " mattis rutrum ante, id vehicula ex accumsan ut.</p>")
            .head(FOCUS_HEAD).build();

    public static Drawer demoDrPanelStart = Drawer.of("dr-panel-start").panelLeft().style("min-height: 240px")
            .toggleFocus("Toggle drawer")
            .contentText("<p style=\"margin-top: 1rem\">The panel slides in from the start (left) edge —"
                    + " <code class=\"ws-code\">pf-m-panel-left</code> on the drawer root.</p>")
            .head(FOCUS_HEAD).build();

    public static Drawer demoDrPill = Drawer.of("dr-pill").pill().style("min-height: 280px")
            .toggleFocus("Toggle drawer")
            .contentText("<p style=\"margin-top: 1rem\"><code class=\"ws-code\">pf-m-pill</code> — the panel"
                    + " renders as a detached, rounded &quot;pill&quot; floating over the content.</p>")
            .head(FOCUS_HEAD).panelBody("Drawer panel body", false, false).build();

    public static Drawer demoDrPillInline = Drawer.of("dr-pill-inline").pill().inline().style("min-height: 280px")
            .toggleFocus("Toggle drawer")
            .contentText("<p style=\"margin-top: 1rem\">Pill panel in an inline drawer —"
                    + " <code class=\"ws-code\">pf-m-pill pf-m-inline</code>.</p>")
            .head(FOCUS_HEAD).panelBody("Drawer panel body", false, false).build();

    public static Drawer demoDrResizableBottom = Drawer.of("dr-resizable-bottom").panelBottom()
            .resizable(200, 100).style("height: 320px").toggle("Toggle drawer")
            .contentText("<p style=\"margin-top: 1rem\">Resizable bottom panel — the splitter is horizontal"
                    + " and arrow keys resize vertically.</p>")
            .head(PLAIN_HEAD).build();

    public static Drawer demoDrResizableEnd = Drawer.of("dr-resizable-end").resizable(500, 150)
            .style("min-height: 240px").toggle("Toggle drawer")
            .contentText("<p style=\"margin-top: 1rem\">Drag the splitter (or focus it and use the arrow"
                    + " keys) to resize the panel. Minimum size 150px, default 500px.</p>")
            .head(PLAIN_HEAD).build();

    public static Drawer demoDrResizableInline = Drawer.of("dr-resizable-inline").inline().resizable(500, 150)
            .style("min-height: 240px").toggle("Toggle drawer")
            .contentText("<p style=\"margin-top: 1rem\">Resizable panel in an inline drawer.</p>")
            .head(PLAIN_HEAD).build();

    public static Drawer demoDrResizableStart = Drawer.of("dr-resizable-start").panelLeft().resizable(500, 150)
            .style("min-height: 240px").toggle("Toggle drawer")
            .contentText("<p style=\"margin-top: 1rem\">Resizable panel on the start side — the splitter"
                    + " sits on the panel's trailing edge.</p>")
            .head(PLAIN_HEAD).build();

    public static Drawer demoDrStatic = Drawer.of("dr-static").staticMode().style("min-height: 240px")
            .contentText("<p>pf-m-static — the panel is always shown, no toggle. Useful for split-pane"
                    + " layouts.</p>")
            .panelSecondary().panelBody("<p>Static panel — always visible.</p>", true, false).build();

    public static Drawer demoDrAdditionalSection = Drawer.of("dr-additional-section").style("min-height: 280px")
            .section("drawer-section above the drawer content")
            .toggleFocus("Toggle drawer")
            .contentText("""
                    <p style="margin-top: 1rem">
                      A <code class="ws-code">pf-v6-c-drawer__section</code> sits outside <code class="ws-code">__main</code> — above
                      the content and panel.
                    </p>""")
            .head(FOCUS_HEAD).build();

    public static Drawer demoDrStacked = Drawer.of("dr-stacked-content-body").style("min-height: 280px")
            .firstBodyBare().toggleFocus("Toggle drawer")
            .contentBody("content-body with padding", true, false)
            .contentBody("content-body", false, false)
            .head("<h3 class=\"pf-v6-c-title pf-m-2xl\" :tabindex=\"expanded ? '0' : '-1'\" x-ref=\"focus\">\n"
                    + "  Drawer panel title\n</h3>")
            .panelBody("Drawer panel body with no padding", false, true)
            .panelBody("Drawer panel body", false, false).build();
}
