import org.sitenetsoft.quarkus.pha.model.*;

Compass compass = Compass.of("cmp-docked").style("height: 320px; border: 1px dashed var(--pf-t--global--border--color--default)").docked()
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

// Template side, with the data in scope:
// {#include components/navigation/compass compass=compass /}
