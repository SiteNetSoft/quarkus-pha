import org.sitenetsoft.quarkus.pha.model.*;

Content content = Content.wrapper().id("content-wrapper")
        .html("""
                <h2>Mixed content wrapper</h2>
                <p>
                  The wrapper styles every direct descendant — headings, paragraphs, lists, blockquotes — using PF v6 typography. Use
                  this mode when you have a block of arbitrary HTML you want consistently styled (e.g. server-rendered article body,
                  markdown output).
                </p>
                <ul>
                  <li>Bullets get list styling</li>
                  <li>Links get link styling</li>
                  <li>No extra wrappers required</li>
                </ul>
                <blockquote>A blockquote inside the wrapper picks up the editorial border treatment.</blockquote>""")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/content contentModel=content /}
