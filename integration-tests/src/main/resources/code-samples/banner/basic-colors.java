import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Banner> banners = List.of(
        Banner.of("Default banner").id("banner").build(),
        Banner.of("Red banner").id("banner-red").variant("red").build(),
        Banner.of("Orangered banner").id("banner-orangered").variant("orangered").build(),
        Banner.of("Orange banner").id("banner-orange").variant("orange").build(),
        Banner.of("Yellow banner").id("banner-yellow").variant("yellow").build(),
        Banner.of("Green banner").id("banner-green").variant("green").build(),
        Banner.of("Teal banner").id("banner-teal").variant("teal").build(),
        Banner.of("Blue banner").id("banner-blue").variant("blue").build(),
        Banner.of("Purple banner").id("banner-purple").variant("purple").build());

// Template side, with the data in scope:
// {#for b in banners}{#include components/feedback/banner banner=b /}{/for}
