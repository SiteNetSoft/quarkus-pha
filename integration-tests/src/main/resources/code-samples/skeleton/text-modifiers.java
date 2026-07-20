import org.sitenetsoft.quarkus.pha.model.*;

import java.util.List;

List<Skeleton> skeletons = List.of(
        Skeleton.builder().id("sk-t-sm").fontSize("sm").width("75").screenReaderText("Loading small text").build(),
        Skeleton.builder().id("sk-t-md").fontSize("md").width("75").screenReaderText("Loading medium text").build(),
        Skeleton.builder().id("sk-t-lg").fontSize("lg").width("75").screenReaderText("Loading large text").build(),
        Skeleton.builder().id("sk-t-xl").fontSize("xl").width("75").screenReaderText("Loading xl text").build(),
        Skeleton.builder().id("sk-t-2xl").fontSize("2xl").width("50").screenReaderText("Loading 2xl text").build(),
        Skeleton.builder().id("sk-t-3xl").fontSize("3xl").width("50").screenReaderText("Loading 3xl text").build(),
        Skeleton.builder().id("sk-t-4xl").fontSize("4xl").width("50").screenReaderText("Loading 4xl heading").build());

// Template side, with the data in scope:
// {#for sk in skeletons}{#include components/feedback/skeleton skeleton=sk /}{/for}
