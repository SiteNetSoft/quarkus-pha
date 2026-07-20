import org.sitenetsoft.quarkus.pha.model.*;

Content content = Content.element("ul").id("content-ul")
        .html("""
                <li>First item</li>
                <li>Second item</li>
                <li>Third item</li>""")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/content contentModel=content /}
