import org.sitenetsoft.quarkus.pha.model.*;

Panel panel = Panel.of("panel-scrollable-hf")
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

// Template side, with the data in scope:
// {#include components/data-display/panel panel=panel /}
