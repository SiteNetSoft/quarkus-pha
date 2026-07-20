package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.NumberInput;

import java.util.List;

/**
 * Demo data for the number-input examples — the examples on
 * /components/number-input are populated from these models; the Alpine wiring
 * (steps, clamps, value-driven status) is generated at build(). Globals so the
 * standalone example route (which renders templates without data) can see
 * them. Each is mirrored by a snippet in resources/code-samples/number-input/
 * served on the example card's Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class NumberInputDemoData {

    public static NumberInput demoNiBasic = NumberInput.of("ni-basic", 5).build();

    public static NumberInput demoNiBounded = NumberInput.of("ni-bounded", 3).min(0).max(10).build();

    public static NumberInput demoNiCustomStep = NumberInput.of("ni-custom-step", 90).step(3).build();

    public static NumberInput demoNiCustomStepThreshold = NumberInput.of("ni-custom-step-threshold", 6)
            .step(3).min(0).max(12).build();

    public static NumberInput demoNiDisabled = NumberInput.of("ni-disabled", 5).disabled().build();

    public static List<NumberInput> demoNisSizes = List.of(
            NumberInput.of("ni-size-1", 1).widthCh(4).build(),
            NumberInput.of("ni-size-2", 1234567).widthCh(10).build(),
            NumberInput.of("ni-size-3", 12).widthCh(18).build());

    public static NumberInput demoNiStatus = NumberInput.of("ni-status", 1).statusFollowsValue().build();

    public static NumberInput demoNiUnit = NumberInput.of("ni-unit", 24).min(0).max(72).unit("hours").build();
}
