package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.Engine;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.sitenetsoft.quarkus.pha.model.JumpLinkItem;
import org.sitenetsoft.quarkus.pha.model.JumpLinks;

import java.util.List;
import java.util.Map;

/**
 * Per-component docs-tab pages. The tab row itself is rendered by partials/showcase-page
 * (docsTabs param); this class serves the non-default tabs. "Java Builder" is the existing
 * /components/{name} demo page; "Qute template" documents pure-Qute usage — the include line and
 * header doc comment of every runtime template belonging to the component, generated from the
 * template sources by {@link QuteTemplateDocs}. The {name}/docs/{tab} shape avoids the
 * per-component /components/{name}/{example} standalone routes, which win JAX-RS matching on
 * literal chars.
 */
@Path("/components")
public class DocsTabRoutes {

    @Location("partials/qute-tab-page")
    @Inject
    Template quteTabPage;

    @Inject
    Engine engine;

    @GET
    @Path("/{name}/docs/qute")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance quteTab(@PathParam("name") String name) {
        List<Map<String, String>> components = HelloResource.buildComponentList();
        Map<String, String> entry = components.stream()
            .filter(c -> name.equals(c.get("id")) && ("/components/" + name).equals(c.get("href")))
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Unknown component: " + name));
        // Only component pages ({name}-demo.html) carry the tab row. i18n-demo.html is a
        // MessageBundle demo, not a component — it uses the shell but has no docs tabs.
        if ("i18n".equals(name) || engine.getTemplate("components/" + name + "-demo") == null) {
            throw new NotFoundException("No demo page for: " + name);
        }
        List<String> allIds = components.stream().map(c -> c.get("id")).toList();
        List<QuteTemplateDocs.TemplateDoc> docs = QuteTemplateDocs.docsFor(name, allIds);
        TemplateInstance ti = quteTabPage
            .data("slug", name)
            .data("title", entry.get("name"))
            .data("docs", docs);
        if (!docs.isEmpty()) {
            JumpLinkItem templates = JumpLinkItem.of("Templates", "#templates");
            for (QuteTemplateDocs.TemplateDoc d : docs) {
                templates = templates.sub(JumpLinkItem.of(d.name(), "#" + d.name()));
            }
            ti = ti.data("toc", ShowcaseToc.of("qute-" + name, templates));
        } else {
            ti = ti.data("toc", null);
        }
        return ti;
    }
}
