package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Button;
import org.sitenetsoft.quarkus.pha.model.Compass;

/**
 * Demo data for the compass examples — the examples on /components/compass are
 * populated from these models; dock and header actions are composed Button
 * models. Globals so the standalone example route (which renders templates
 * without data) can see them. Each is mirrored by a snippet in
 * resources/code-samples/compass/ served on the example card's Java tab —
 * keep them in sync when editing.
 */
@TemplateGlobal
public class CompassDemoData {

    private static final String DEMO_STYLE =
            "height: 320px; border: 1px dashed var(--pf-t--global--border--color--default)";

    public static Compass demoCmpBasic = Compass.of("cmp-basic").style(DEMO_STYLE)
            .header("Compass shell", "padding: 0.5rem 1rem").headerExpanded()
            .main("""
                    <div class="pf-v6-c-compass__content" style="padding: 1rem">
                      <p>Header, main, and footer regions inside the compass container.</p>
                    </div>""")
            .footerHtml("""
                    <div class="pf-v6-c-compass__message-bar" style="padding: 0.5rem 1rem">
                      <span class="pf-v6-c-form-control" style="width: 100%"><input type="text" aria-label="Message" placeholder="Send a message&#8230;" /></span>
                    </div>""")
            .footerExpanded()
            .build();

    public static Compass demoCmpAltFooter = Compass.of("cmp-alt-footer").style(DEMO_STYLE)
            .header("Alternate footer", "padding: 0.5rem 1rem").headerExpanded()
            .main("""
                    <div class="pf-v6-c-compass__content" style="padding: 1rem">
                      <p>The footer below carries links rather than a message bar.</p>
                    </div>""")
            .footerHtml("""
                    <div style="padding: 0.5rem 1rem">
                      <ul class="pf-v6-c-list pf-m-inline" role="list">
                        <li><a href="#">Terms</a></li>
                        <li><a href="#">Privacy</a></li>
                        <li><a href="#">Support</a></li>
                      </ul>
                    </div>""")
            .footerExpanded()
            .build();

    public static Compass demoCmpDocked = Compass.of("cmp-docked").style(DEMO_STYLE).docked()
            .dock("padding: 0.5rem; display: flex; flex-direction: column; gap: 0.5rem",
                    Button.icon("fa:house", "Home").variant("plain").build(),
                    Button.icon("fa:magnifying-glass", "Search").variant("plain").build(),
                    Button.icon("fa:gear", "Settings").variant("plain").build())
            .header("Docked nav", "padding: 0.5rem 1rem").headerExpanded()
            .main("""
                    <div class="pf-v6-c-compass__content" style="padding: 1rem">
                      <p>The dock rail sits outside the container; pf-m-docked reserves space for it.</p>
                    </div>""")
            .build();

    public static Compass demoCmpHeaderStructure = Compass.of("cmp-header-structure").style(DEMO_STYLE)
            .header("Main header structure",
                    "padding: 0.5rem 1rem; display: flex; align-items: center; gap: 1rem; justify-content: space-between")
            .headerActions(
                    Button.of("Share").variant("secondary").build(),
                    Button.icon("fa:ellipsis-vertical", "More options").variant("plain").build())
            .headerExpanded()
            .main("""
                    <div class="pf-v6-c-compass__content" style="padding: 1rem">
                      <p>Title in __main-header-content with a toolbar aligned to the end.</p>
                    </div>""")
            .build();
}
