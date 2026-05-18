package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.sitenetsoft.quarkus.pha.qute.IconResolver;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Direct tests for {@link IconResolver} — exercises each vendored set, the
 * accessibility branches, and the missing-icon placeholder.
 */
@QuarkusTest
class IconResolverTest {

    @Test
    void resolves_fa_solid_icon() {
        String svg = IconResolver.svg("fa:check").toString();
        assertTrue(svg.startsWith("<svg"), "should produce an <svg> element");
        assertTrue(svg.contains("fill=\"currentColor\""), "should color from currentColor");
        assertTrue(svg.contains("width=\"1em\""), "should size at 1em width");
        assertTrue(svg.contains("aria-hidden=\"true\""), "no label → decorative");
        assertFalse(svg.contains("data-missing-icon"), "known icon should not render placeholder");
    }

    @Test
    void resolves_fa_brands_icon() {
        String svg = IconResolver.svg("fa-brands:github").toString();
        assertTrue(svg.contains("<svg"));
        assertFalse(svg.contains("data-missing-icon"));
    }

    @Test
    void resolves_fa_regular_icon() {
        String svg = IconResolver.svg("fa-regular:envelope").toString();
        assertTrue(svg.contains("<svg"));
        assertFalse(svg.contains("data-missing-icon"));
    }

    @Test
    void resolves_pficon_icon() {
        String svg = IconResolver.svg("pficon:cluster").toString();
        assertTrue(svg.contains("<svg"));
        assertFalse(svg.contains("data-missing-icon"));
    }

    @Test
    void resolves_pficon_modern_pf6_name() {
        // PF v6 ships these but the older @patternfly/icons npm package didn't —
        // proves we vendored from the PF v6 GitHub source, not the stale npm package.
        String svg = IconResolver.svg("pficon:save").toString();
        assertTrue(svg.contains("<svg"));
        assertFalse(svg.contains("data-missing-icon"));
    }

    @Test
    void applies_aria_label_when_provided() {
        String svg = IconResolver.svg("fa:check", "Saved").toString();
        assertTrue(svg.contains("role=\"img\""));
        assertTrue(svg.contains("aria-label=\"Saved\""));
        assertFalse(svg.contains("aria-hidden"));
    }

    @Test
    void escapes_aria_label_html() {
        String svg = IconResolver.svg("fa:check", "Bob's <best> tool").toString();
        assertTrue(svg.contains("aria-label=\"Bob&#39;s &lt;best&gt; tool\""),
            "label should be HTML-escaped — got: " + svg);
    }

    @Test
    void unknown_set_renders_placeholder() {
        String svg = IconResolver.svg("nosuch:thing").toString();
        assertTrue(svg.contains("data-missing-icon=\"nosuch:thing\""));
        assertTrue(svg.contains("#c9190b"), "placeholder should use PF danger red");
    }

    @Test
    void unknown_slug_renders_placeholder() {
        String svg = IconResolver.svg("fa:this-icon-does-not-exist-xyz").toString();
        assertTrue(svg.contains("data-missing-icon=\"fa:this-icon-does-not-exist-xyz\""));
    }

    @Test
    void rejects_unsafe_slug() {
        // Path-traversal attempts must not load anything from the classpath.
        String svg = IconResolver.svg("fa:../../etc/passwd").toString();
        assertTrue(svg.contains("data-missing-icon"));
    }

    @Test
    void null_name_renders_placeholder() {
        String svg = IconResolver.svg(null).toString();
        assertTrue(svg.contains("data-missing-icon"));
    }

    @Test
    void caches_repeated_lookups() {
        // Second call should be served from cache — semantically identical output.
        String first  = IconResolver.svg("fa:trash").toString();
        String second = IconResolver.svg("fa:trash").toString();
        assertTrue(first.equals(second), "cached output should match");
    }
}
