package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Slider;

/**
 * Demo data for the slider examples — the examples on /components/slider are
 * populated from these models; the rail-click and WAI-ARIA keyboard Alpine is
 * generated at build() (actions and value-input stay hand-written: flanking
 * compositions sharing outer Alpine state). Globals so the standalone example
 * route (which renders templates without data) can see them. Each is mirrored
 * by a snippet in resources/code-samples/slider/ served on the example card's
 * Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class SliderDemoData {

    public static Slider demoSlBasic = Slider.of("sl-basic", 50).build();

    public static Slider demoSlContinuous = Slider.of("sl-continuous", 42).build();

    public static Slider demoSlTemp = Slider.of("sl-temp", 20).min(-10).max(40).build();

    public static Slider demoSlDisabled = Slider.of("sl-disabled", 30).disabled().build();
}
