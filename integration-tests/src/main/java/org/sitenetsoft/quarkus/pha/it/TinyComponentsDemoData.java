package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.BackToTop;
import org.sitenetsoft.quarkus.pha.model.BackgroundImage;
import org.sitenetsoft.quarkus.pha.model.Button;
import org.sitenetsoft.quarkus.pha.model.Hero;
import org.sitenetsoft.quarkus.pha.model.NotificationBadge;
import org.sitenetsoft.quarkus.pha.model.SkipToContent;
import org.sitenetsoft.quarkus.pha.model.Title;

import java.util.List;

/**
 * Demo data for the tiny-component examples (title, notification-badge,
 * skip-to-content, hero, back-to-top, background-image) — one class since each
 * bucket holds only a handful of globals. Globals so the standalone example
 * routes (which render templates without data) can see them. Each is mirrored
 * by a snippet in resources/code-samples/{component}/ served on the example
 * card's Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class TinyComponentsDemoData {

    public static List<Title> demoTitlesDefault = List.of(
            Title.of("h1 — default").build(),
            Title.of("h2 — default").headingLevel("h2").build(),
            Title.of("h3 — default").headingLevel("h3").build(),
            Title.of("h4 — default").headingLevel("h4").build(),
            Title.of("h5 — default").headingLevel("h5").build(),
            Title.of("h6 — default").headingLevel("h6").build());

    public static List<Title> demoTitlesCustom = List.of(
            Title.of("4xl title").headingLevel("h1").size("4xl").build(),
            Title.of("3xl title").headingLevel("h2").size("3xl").build(),
            Title.of("2xl title").headingLevel("h3").size("2xl").build(),
            Title.of("xl title").headingLevel("h4").size("xl").build(),
            Title.of("lg title").headingLevel("h5").size("lg").build(),
            Title.of("md title").headingLevel("h6").size("md").build());

    public static NotificationBadge demoNbUnread = NotificationBadge
            .of("nb-unread", "3 unread notifications").variant("unread").count("3").build();

    public static NotificationBadge demoNbRead = NotificationBadge
            .of("nb-read", "Notifications, all read").variant("read").build();

    public static NotificationBadge demoNbAttention = NotificationBadge
            .of("nb-attention", "Critical notifications need attention").variant("attention").count("!").build();

    public static SkipToContent demoStcBasic = SkipToContent.of("#main-content", "Skip to content")
            .id("stc-basic").build();

    public static SkipToContent demoStcCustom = SkipToContent.of("#main-content", "Jump to main content")
            .id("stc-custom").build();

    public static Hero demoHeroBasic = Hero.of("hero-basic")
            .html("""
                    <h2 class="pf-v6-c-title pf-m-2xl">Welcome to Quarkus PHA</h2>
                    <p class="pf-v6-c-content--p" style="max-width: 48ch">
                      A prominent banner region for landing pages — title, supporting copy, and a call to action.
                    </p>""")
            .cta(Button.of("Get started").variant("primary").build())
            .build();

    public static Hero demoHeroGlass = Hero.of("hero-glass").glass()
            .html("""
                    <h2 class="pf-v6-c-title pf-m-2xl">Glass hero</h2>
                    <p class="pf-v6-c-content--p" style="max-width: 48ch">The body sits on a translucent glass panel over the page background.</p>""")
            .build();

    public static BackToTop demoBttBasic = BackToTop.of().id("back-to-top-basic").build();

    public static BackgroundImage demoBgBasic = BackgroundImage.of("/web/images/pf-background.svg")
            .id("bg-image-basic").build();
}
