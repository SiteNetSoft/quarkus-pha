import org.sitenetsoft.quarkus.pha.model.*;
import org.sitenetsoft.quarkus.pha.model.Page.Section;

Page page = Page.of("pg-vertical-nav").style("min-height: 400px; border: 1px solid var(--pf-t--global--border--color--default)")
        .brand("Logo").toggle("Vertical nav demo").toolbarText("header-tools")
        .sidebar(Nav.builder().ariaLabel("Vertical nav demo secondary")
                .item("Nav item 1", "#", true).item("Nav item 2", "#").item("Nav item 3", "#").build())
        .section(Section.of("<h2 id=\"pg-vertical-nav-s1\" class=\"pf-v6-c-title pf-m-xl\">Vertical nav"
                + " example section 1</h2>").ariaLabelledBy("pg-vertical-nav-s1"))
        .section(Section.of("""
                <h2 id="pg-vertical-nav-s2" class="pf-v6-c-title pf-m-xl">
                  Vertical nav example section 2 with secondary variant styling
                </h2>""").modifiers("pf-m-secondary").ariaLabelledBy("pg-vertical-nav-s2"))
        .section(Section.of("<h2 id=\"pg-vertical-nav-s3\" class=\"pf-v6-c-title pf-m-xl\">Vertical nav"
                + " example section 3</h2>").ariaLabelledBy("pg-vertical-nav-s3"))
        .build();

// Template side, with the data in scope:
// {#include components/navigation/page page=page /}
