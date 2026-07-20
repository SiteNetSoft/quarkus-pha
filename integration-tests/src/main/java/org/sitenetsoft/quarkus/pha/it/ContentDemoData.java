package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Content;

import java.util.List;

/**
 * Demo data for the content examples — the examples on /components/content are
 * populated from these models. Globals so the standalone example route (which
 * renders templates without data) can see them. Each is mirrored by a snippet
 * in resources/code-samples/content/ served on the example card's Java tab —
 * keep them in sync when editing.
 */
@TemplateGlobal
public class ContentDemoData {

    public static List<Content> demoContentBody = List.of(
            Content.element("p").text("A standard paragraph rendered through the Content component."
                    + " It uses PF v6's body font size, line height, and text color tokens.").build(),
            Content.element("small").text("Small text — typically used for captions, footnotes,"
                    + " or secondary metadata.").build(),
            Content.element("blockquote").text("A blockquote with PF v6's editorial border accent"
                    + " on the inline-start edge.").build(),
            Content.element("pre").text("Preformatted text. It preserves whitespace and line breaks.").build());

    public static Content demoContentWrapper = Content.wrapper().id("content-wrapper")
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

    public static Content demoContentDl = Content.element("dl").id("content-dl")
            .html("""
                    <dt>Name</dt>
                    <dd>Example application</dd>
                    <dt>Version</dt>
                    <dd>1.0.0</dd>
                    <dt>Description</dt>
                    <dd>A sample definition list rendered through the Content component.</dd>""")
            .build();

    public static Content demoContentEditorial = Content.wrapper().id("content-editorial").editorial()
            .html("""
                    <h2>Editorial heading</h2>
                    <p>
                      Editorial styling bumps the body and small text up one size tier. Use it for long-form prose where readability
                      matters more than density — release notes, article bodies, in-app documentation.
                    </p>
                    <p><small>Even small text is larger in editorial mode.</small></p>""")
            .build();

    public static List<Content> demoContentHeadings = List.of(
            Content.element("h1").text("First level heading").build(),
            Content.element("h2").text("Second level heading").build(),
            Content.element("h3").text("Third level heading").build(),
            Content.element("h4").text("Fourth level heading").build(),
            Content.element("h5").text("Fifth level heading").build(),
            Content.element("h6").text("Sixth level heading").build());

    public static List<Content> demoContentLinks = List.of(
            Content.element("a").href("#default").text("Default link styling").build(),
            Content.element("a").href("#visited").visited().text("Visited link styling").build());

    public static Content demoContentOl = Content.element("ol").id("content-ol")
            .html("""
                    <li>First step</li>
                    <li>Second step</li>
                    <li>Third step</li>""")
            .build();

    public static Content demoContentPlain = Content.element("ul").id("content-plain").plainList()
            .html("""
                    <li>Unstyled item one</li>
                    <li>Unstyled item two</li>
                    <li>Unstyled item three</li>""")
            .build();

    public static Content demoContentUl = Content.element("ul").id("content-ul")
            .html("""
                    <li>First item</li>
                    <li>Second item</li>
                    <li>Third item</li>""")
            .build();
}
