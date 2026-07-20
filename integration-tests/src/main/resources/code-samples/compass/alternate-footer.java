import org.sitenetsoft.quarkus.pha.model.*;

Compass compass = Compass.of("cmp-alt-footer").style("height: 320px; border: 1px dashed var(--pf-t--global--border--color--default)")
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

// Template side, with the data in scope:
// {#include components/navigation/compass compass=compass /}
