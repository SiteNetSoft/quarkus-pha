import org.sitenetsoft.quarkus.pha.model.*;

Content content = Content.element("ol").id("content-ol")
        .html("""
                <li>First step</li>
                <li>Second step</li>
                <li>Third step</li>""")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/content contentModel=content /}
