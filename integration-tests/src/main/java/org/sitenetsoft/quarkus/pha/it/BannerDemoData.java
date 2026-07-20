package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Banner;

import java.util.List;

/**
 * Demo data for the banner examples — every example on /components/banner is
 * populated from these Banner models. Globals so the standalone example route
 * can see them; each is mirrored by a snippet in
 * resources/code-samples/banner/ served on the example card's Java tab.
 */
@TemplateGlobal
public class BannerDemoData {

    public static List<Banner> demoBannersColors = List.of(
            Banner.of("Default banner").id("banner").build(),
            Banner.of("Red banner").id("banner-red").variant("red").build(),
            Banner.of("Orangered banner").id("banner-orangered").variant("orangered").build(),
            Banner.of("Orange banner").id("banner-orange").variant("orange").build(),
            Banner.of("Yellow banner").id("banner-yellow").variant("yellow").build(),
            Banner.of("Green banner").id("banner-green").variant("green").build(),
            Banner.of("Teal banner").id("banner-teal").variant("teal").build(),
            Banner.of("Blue banner").id("banner-blue").variant("blue").build(),
            Banner.of("Purple banner").id("banner-purple").variant("purple").build());

    public static List<Banner> demoBannersStatus = List.of(
            Banner.of("Success banner message").id("banner-success").variant("success")
                    .screenReaderText("Success:").build(),
            Banner.of("Warning banner message").id("banner-warning").variant("warning")
                    .screenReaderText("Warning:").build(),
            Banner.of("Danger banner message").id("banner-danger").variant("danger")
                    .screenReaderText("Danger:").build(),
            Banner.of("Info banner message").id("banner-info").variant("info")
                    .screenReaderText("Info:").build(),
            Banner.of("Custom banner message").id("banner-custom").variant("custom").build());

    public static Banner demoBannerScreenReader = Banner.of("This banner has screen reader text.")
            .id("banner-sr").variant("info").screenReaderText("Info alert:").build();

    public static Banner demoBannerSticky = Banner.of("Sticky warning banner")
            .id("banner-sticky").variant("warning").asSticky().screenReaderText("Warning:").build();

    public static List<Banner> demoBannersWithLinks = List.of(
            Banner.of("Default banner with a <a href=\"#\">link</a>").build(),
            Banner.of("Default banner with a\n    <a\n            class=\"pf-m-disabled\"\n            role=\"link\"\n            aria-disabled=\"true\"\n    >disabled link</a>").build(),
            Banner.of("Blue banner with an <button class=\"pf-v6-c-button pf-m-link pf-m-inline\" type=\"button\" id=\"btn-plain\" aria-label=\"inline\"><span class=\"pf-v6-c-button__text\">inline link button</span></button>\n")
                    .variant("blue").build());
}
