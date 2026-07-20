import org.sitenetsoft.quarkus.pha.model.*;

Compass compass = Compass.of("cmp-header-structure").style("height: 320px; border: 1px dashed var(--pf-t--global--border--color--default)")
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

// Template side, with the data in scope:
// {#include components/navigation/compass compass=compass /}
