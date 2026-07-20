import org.sitenetsoft.quarkus.pha.model.*;

Hero hero = Hero.of("hero-glass").glass()
        .html("""
                <h2 class="pf-v6-c-title pf-m-2xl">Glass hero</h2>
                <p class="pf-v6-c-content--p" style="max-width: 48ch">The body sits on a translucent glass panel over the page background.</p>""")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/hero hero=hero /}
