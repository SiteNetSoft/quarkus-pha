import org.sitenetsoft.quarkus.pha.model.*;

Content content = Content.element("dl").id("content-dl")
        .html("""
                <dt>Name</dt>
                <dd>Example application</dd>
                <dt>Version</dt>
                <dd>1.0.0</dd>
                <dt>Description</dt>
                <dd>A sample definition list rendered through the Content component.</dd>""")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/content contentModel=content /}
