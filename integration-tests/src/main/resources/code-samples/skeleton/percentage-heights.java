import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Skeleton> skeletons = List.of(
        Skeleton.builder().id("sk-h25").width("sm").height("25").screenReaderText("Loading (25%)").build(),
        Skeleton.builder().id("sk-h50").width("sm").height("50").screenReaderText("Loading (50%)").build(),
        Skeleton.builder().id("sk-h75").width("sm").height("75").screenReaderText("Loading (75%)").build(),
        Skeleton.builder().id("sk-h100").width("sm").height("100").screenReaderText("Loading (100%)").build());

// Template side, with the data in scope:
// {#for sk in skeletons}{#include components/feedback/skeleton skeleton=sk /}{/for}
