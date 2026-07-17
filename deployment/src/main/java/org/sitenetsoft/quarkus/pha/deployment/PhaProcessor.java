package org.sitenetsoft.quarkus.pha.deployment;

import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.SystemPropertyBuildItem;
import io.quarkus.deployment.pkg.builditem.CurateOutcomeBuildItem;

class PhaProcessor {

    private static final String FEATURE = "pha";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    /**
     * Resolve this extension's version from the application model and hand it
     * to the runtime as the {@code pha.asset-version} system property. The
     * {@code pha:assetVersion} Qute namespace (see AssetVersion) appends it as
     * {@code ?v=...} to the asset URLs in layouts/_head.html, so consumer
     * browser caches turn over exactly when the extension upgrades.
     *
     * <p>Resolved from the dependency model — not from a stamped resource —
     * because Quarkus merges workspace-module resources into the app archive
     * straight from the source tree, which would bypass any processResources
     * filtering for every in-workspace build.
     */
    @BuildStep
    SystemPropertyBuildItem assetVersion(CurateOutcomeBuildItem curateOutcome) {
        String version = curateOutcome.getApplicationModel().getDependencies().stream()
            .filter(d -> "org.sitenetsoft".equals(d.getGroupId()) && "quarkus-pha".equals(d.getArtifactId()))
            .map(d -> d.getVersion())
            .findFirst()
            .orElse("unknown");
        return new SystemPropertyBuildItem("pha.asset-version", version);
    }
}
