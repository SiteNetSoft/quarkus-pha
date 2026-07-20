package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Spinner;

import java.util.List;

/**
 * Demo data for the spinner examples — the examples on /components/spinner are
 * populated from these models. Globals so the standalone example route (which
 * renders templates without data) can see them. Each is mirrored by a snippet
 * in resources/code-samples/spinner/ served on the example card's Java tab —
 * keep them in sync when editing.
 */
@TemplateGlobal
public class SpinnerDemoData {

    public static Spinner demoSpBasic = Spinner.of("Loading").build();

    public static List<Spinner> demoSpsDiameters = List.of(
            Spinner.of("Loading (2rem)").diameter("2rem").build(),
            Spinner.of("Loading (80px)").diameter("80px").build(),
            Spinner.of("Loading (6rem)").diameter("6rem").build());

    public static Spinner demoSpInline = Spinner.of("Loading").inline().build();

    public static List<Spinner> demoSpsSizes = List.of(
            Spinner.of("Loading (xs)").size("xs").build(),
            Spinner.of("Loading (sm)").size("sm").build(),
            Spinner.of("Loading (md)").size("md").build(),
            Spinner.of("Loading (lg)").size("lg").build(),
            Spinner.of("Loading (xl)").size("xl").build());
}
