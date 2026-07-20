package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Button;
import org.sitenetsoft.quarkus.pha.model.Tooltip;

import java.util.List;

/**
 * Demo data for the tooltip examples — the examples on /components/tooltip are
 * populated from these models (dynamic-content stays hand-written: its tip
 * content and trigger share live Alpine state, a composition use-case). The
 * trigger is a composed Button model. Globals so the standalone example route
 * (which renders templates without data) can see them. Each is mirrored by a
 * snippet in resources/code-samples/tooltip/ served on the example card's Java
 * tab — keep them in sync when editing.
 */
@TemplateGlobal
public class TooltipDemoData {

    public static Tooltip demoTtBasic = Tooltip.of("tt-basic").position("top")
            .trigger(Button.of("Hover or focus me").variant("secondary").build())
            .tip("I'm a tooltip").build();

    public static List<Tooltip> demoTtsPositions = List.of(
            Tooltip.of("tt-top").position("top")
                    .trigger(Button.of("Top").variant("secondary").build()).tip("Top tooltip").build(),
            Tooltip.of("tt-right").position("right")
                    .trigger(Button.of("Right").variant("secondary").build()).tip("Right tooltip").build(),
            Tooltip.of("tt-bottom").position("bottom")
                    .trigger(Button.of("Bottom").variant("secondary").build()).tip("Bottom tooltip").build(),
            Tooltip.of("tt-left").position("left")
                    .trigger(Button.of("Left").variant("secondary").build()).tip("Left tooltip").build());

    public static List<Tooltip> demoTtsDiagonal = List.of(
            Tooltip.of("tt-top-left").position("top-left")
                    .trigger(Button.of("Top left").variant("secondary").build()).tip("Top left tooltip").build(),
            Tooltip.of("tt-top-right").position("top-right")
                    .trigger(Button.of("Top right").variant("secondary").build()).tip("Top right tooltip").build(),
            Tooltip.of("tt-bottom-left").position("bottom-left")
                    .trigger(Button.of("Bottom left").variant("secondary").build()).tip("Bottom left tooltip").build(),
            Tooltip.of("tt-bottom-right").position("bottom-right")
                    .trigger(Button.of("Bottom right").variant("secondary").build()).tip("Bottom right tooltip").build(),
            Tooltip.of("tt-left-top").position("left-top")
                    .trigger(Button.of("Left top").variant("secondary").build()).tip("Left top tooltip").build(),
            Tooltip.of("tt-left-bottom").position("left-bottom")
                    .trigger(Button.of("Left bottom").variant("secondary").build()).tip("Left bottom tooltip").build(),
            Tooltip.of("tt-right-top").position("right-top")
                    .trigger(Button.of("Right top").variant("secondary").build()).tip("Right top tooltip").build(),
            Tooltip.of("tt-right-bottom").position("right-bottom")
                    .trigger(Button.of("Right bottom").variant("secondary").build()).tip("Right bottom tooltip").build());

    public static Tooltip demoTtLeftAligned = Tooltip.of("tt-left-aligned").position("top").textAlignLeft()
            .trigger(Button.of("Left aligned text").variant("secondary").build())
            .tip("This tooltip wraps onto several lines because it carries a lot of text, and"
                    + " pf-m-text-align-left keeps every line ragged-right instead of centered.").build();

    public static Tooltip demoTtLong = Tooltip.of("tt-long").position("bottom")
            .trigger(Button.of("Long tooltip").variant("secondary").build())
            .tip("This tooltip carries a lot of text. PatternFly caps the bubble at 18.75rem wide,"
                    + " so the content wraps onto multiple lines rather than stretching across the page.").build();

    public static Tooltip demoTtIcon = Tooltip.of("tt-icon").position("top")
            .trigger(Button.icon("fa:circle-info", "More information").variant("plain").build())
            .tip("More information about this field").build();
}
