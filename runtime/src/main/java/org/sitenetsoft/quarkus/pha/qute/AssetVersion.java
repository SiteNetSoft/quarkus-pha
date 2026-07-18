package org.sitenetsoft.quarkus.pha.qute;

import io.quarkus.qute.TemplateExtension;

/**
 * Extension asset version exposed as the {@code pha} Qute namespace.
 *
 * <p>Usage from a Qute template:
 * <pre>
 *   &lt;link rel="stylesheet" href="/web/css/pha.css?v={pha:assetVersion}" /&gt;
 * </pre>
 *
 * <p>layouts/_head.html suffixes every always-loaded asset URL with
 * {@code ?v={pha:assetVersion}} so consumer browser caches turn over exactly
 * when the extension upgrades (static resources are otherwise served with
 * {@code Cache-Control: public, immutable, max-age=86400}). For SNAPSHOT
 * builds the build step appends the build time, so the URL — and therefore
 * the immutable cache entry — turns over on every build, not only on
 * version upgrades.
 *
 * <p>The value arrives as the {@code pha.asset-version} system property, set
 * by the deployment module's {@code PhaProcessor#assetVersion} build step from
 * the application dependency model. It is deliberately NOT read from a
 * build-stamped resource: Quarkus merges workspace-module resources into the
 * app archive straight from the source tree, so a filtered resource is
 * silently shadowed by its unfiltered source copy in every in-workspace build.
 */
public final class AssetVersion {

    private AssetVersion() { }

    @TemplateExtension(namespace = "pha")
    public static String assetVersion() {
        return System.getProperty("pha.asset-version", "dev");
    }
}
