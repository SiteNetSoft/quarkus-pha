package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Progress;

/**
 * Demo data for the progress examples — every example on /components/progress
 * is populated from one of these Progress models. Globals so the standalone
 * example route (which renders templates without data) can see them. Each is
 * mirrored by a snippet in resources/code-samples/progress/ served on the
 * example card's Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class ProgressDemoData {

    public static Progress demoProgBasic = Progress.of("prog-basic", 33)
            .title("Basic progress").build();

    public static Progress demoProgStaticWidth = Progress.of("prog-static-width", 33)
            .title("Static width measure")
            .staticWidthMeasure()
            .build();

    public static Progress demoProgSmall = Progress.of("prog-small", 33)
            .title("Small progress").size("sm").build();

    public static Progress demoProgLarge = Progress.of("prog-large", 33)
            .title("Large progress").size("lg").build();

    public static Progress demoProgInside = Progress.of("prog-inside", 33)
            .title("Inside measure").measureInside().size("lg").build();

    public static Progress demoProgOutside = Progress.of("prog-outside", 33)
            .title("Outside measure").measureOutside().build();

    public static Progress demoProgSingleline = Progress.of("prog-singleline", 33)
            .ariaLabel("Single-line progress").asSingleline().build();

    public static Progress demoProgNoMeasure = Progress.of("prog-no-measure", 33)
            .title("Without measure").noMeasure().build();

    public static Progress demoProgSuccess = Progress.of("prog-success", 100)
            .title("Backup complete").variant("success").build();

    public static Progress demoProgFailure = Progress.of("prog-failure", 33)
            .title("Backup failed").variant("danger")
            .helperText("Run failed at 33% — see the audit log for details.").build();

    public static Progress demoProgWarning = Progress.of("prog-warning", 66)
            .title("Disk usage high").variant("warning")
            .helperText("Approaching the configured 80% threshold.").build();

    public static Progress demoProgFailNoMeasure = Progress.of("prog-fail-no-measure", 42)
            .title("Index rebuild interrupted").variant("danger").noMeasure().build();

    public static Progress demoProgFiniteStep = Progress.of("prog-finite-step", 2)
            .range(0, 5).title("Finite step progress")
            .label("Step 2 of 5").valueText("Step 2 of 5").build();

    public static Progress demoProgHelperText = Progress.of("prog-helper-text", 33)
            .title("Progress with helper text")
            .helperText("Disk usage projected to peak at 78% before the next nightly cleanup.").build();

    public static Progress demoProgInsideSuccess = Progress.of("prog-inside-success", 100)
            .title("Backup complete").variant("success").measureInside().size("lg").build();

    public static Progress demoProgOutsideFailure = Progress.of("prog-outside-failure", 42)
            .title("Sync failed").variant("danger").measureOutside().build();

    public static Progress demoProgStepInstructions = Progress.of("prog-step-instructions", 2)
            .range(0, 5).title("Onboarding")
            .label("Step 2 of 5").valueText("Step 2 of 5: configure billing")
            .helperText("Configure billing details. Next step: invite team members.").build();

    public static Progress demoProgTitleOutside = Progress.of("prog-title-outside", 33)
            .title("Title outside progress").measureOutside().build();

    public static Progress demoProgTruncate = Progress.of("prog-truncate", 42)
            .title("A really really long progress title that will run out of space and need to be truncated with an ellipsis")
            .asTruncatedTitle().build();
}
