import org.sitenetsoft.quarkus.pha.model.*;

Compass compass = Compass.of("cmp-basic").style("height: 320px; border: 1px dashed var(--pf-t--global--border--color--default)")
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

// Template side, with the data in scope:
// {#include components/navigation/compass compass=compass /}
