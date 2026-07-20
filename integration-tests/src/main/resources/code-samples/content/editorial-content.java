import org.sitenetsoft.quarkus.pha.model.*;

Content content = Content.wrapper().id("content-editorial").editorial()
        .html("""
                <h2>Editorial heading</h2>
                <p>
                  Editorial styling bumps the body and small text up one size tier. Use it for long-form prose where readability
                  matters more than density — release notes, article bodies, in-app documentation.
                </p>
                <p><small>Even small text is larger in editorial mode.</small></p>""")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/content contentModel=content /}
