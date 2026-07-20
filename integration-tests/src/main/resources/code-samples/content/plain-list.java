import org.sitenetsoft.quarkus.pha.model.*;

Content content = Content.element("ul").id("content-plain").plainList()
        .html("""
                <li>Unstyled item one</li>
                <li>Unstyled item two</li>
                <li>Unstyled item three</li>""")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/content contentModel=content /}
