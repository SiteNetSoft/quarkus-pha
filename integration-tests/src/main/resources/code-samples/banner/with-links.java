import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Banner> banners = List.of(
        Banner.of("Default banner with a <a href=\"#\">link</a>").build(),
        Banner.of("Default banner with a\n    <a\n            class=\"pf-m-disabled\"\n            role=\"link\"\n            aria-disabled=\"true\"\n    >disabled link</a>").build(),
        Banner.of("Blue banner with an <button class=\"pf-v6-c-button pf-m-link pf-m-inline\" type=\"button\" id=\"btn-plain\" aria-label=\"inline\"><span class=\"pf-v6-c-button__text\">inline link button</span></button>\n")
                .variant("blue").build());

// Template side, with the data in scope:
// {#for b in banners}{#include components/feedback/banner banner=b /}{/for}
