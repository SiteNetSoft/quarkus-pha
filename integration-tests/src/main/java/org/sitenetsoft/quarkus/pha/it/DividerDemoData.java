package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Divider;

/**
 * Demo data for the divider examples — the examples on /components/divider are
 * populated from these Divider models (using-li stays hand-written: it demos
 * the raw class on decorative aria-hidden list separators). Globals so the
 * standalone example route can see them; each is mirrored by a snippet in
 * resources/code-samples/divider/ served on the example card's Java tab.
 */
@TemplateGlobal
public class DividerDemoData {

    public static Divider demoDivHr = Divider.builder().build();

    public static Divider demoDivDiv = Divider.builder().component("div").build();

    public static Divider demoDivInsetMd = Divider.builder().inset("md").build();

    public static Divider demoDivInsetBreakpoints = Divider.builder()
            .insetOn("md", "sm").insetOn("lg", "md").insetOn("xl", "lg").insetOn("2xl", "xl").build();

    public static Divider demoDivVertical = Divider.builder().vertical().build();

    public static Divider demoDivVerticalInsetSm = Divider.builder().vertical().inset("sm").build();

    public static Divider demoDivVerticalInsetBreakpoints = Divider.builder().vertical()
            .insetOn("md", "xs").insetOn("lg", "sm").insetOn("xl", "md").build();

    public static Divider demoDivVerticalOnMd = Divider.builder().verticalOn("md").build();
}
