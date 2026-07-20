import org.sitenetsoft.quarkus.pha.model.*;

Panel panel = Panel.of("panel-scrollable")
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

// Template side, with the data in scope:
// {#include components/data-display/panel panel=panel /}
