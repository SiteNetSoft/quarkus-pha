import org.sitenetsoft.quarkus.pha.model.*;

Hero hero = Hero.of("hero-basic")
        .html("""
                <h2 class="pf-v6-c-title pf-m-2xl">Welcome to Quarkus PHA</h2>
                <p class="pf-v6-c-content--p" style="max-width: 48ch">
                  A prominent banner region for landing pages — title, supporting copy, and a call to action.
                </p>""")
        .cta(Button.of("Get started").variant("primary").build())
        .build();

// Template side, with the data in scope:
// {#include components/data-display/hero hero=hero /}
