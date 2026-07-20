import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Skeleton> skeletons = List.of(
        Skeleton.builder().id("sk-w25").width("25").screenReaderText("Loading (25%)").build(),
        Skeleton.builder().id("sk-w33").width("33").screenReaderText("Loading (33%)").build(),
        Skeleton.builder().id("sk-w50").width("50").screenReaderText("Loading (50%)").build(),
        Skeleton.builder().id("sk-w66").width("66").screenReaderText("Loading (66%)").build(),
        Skeleton.builder().id("sk-w75").width("75").screenReaderText("Loading (75%)").build(),
        Skeleton.builder().id("sk-w-full").screenReaderText("Loading (100%)").build());

// Template side, with the data in scope:
// {#for sk in skeletons}{#include components/feedback/skeleton skeleton=sk /}{/for}
