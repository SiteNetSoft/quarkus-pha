package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Skeleton;

import java.util.List;

/**
 * Demo data for the skeleton examples — the examples on /components/skeleton
 * are populated from these models. Globals so the standalone example route
 * (which renders templates without data) can see them. Each is mirrored by a
 * snippet in resources/code-samples/skeleton/ served on the example card's
 * Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class SkeletonDemoData {

    public static Skeleton demoSkDefault = Skeleton.builder()
            .id("sk-default").screenReaderText("Loading").build();

    public static List<Skeleton> demoSksHeights = List.of(
            Skeleton.builder().id("sk-h25").width("sm").height("25").screenReaderText("Loading (25%)").build(),
            Skeleton.builder().id("sk-h50").width("sm").height("50").screenReaderText("Loading (50%)").build(),
            Skeleton.builder().id("sk-h75").width("sm").height("75").screenReaderText("Loading (75%)").build(),
            Skeleton.builder().id("sk-h100").width("sm").height("100").screenReaderText("Loading (100%)").build());

    public static List<Skeleton> demoSksWidths = List.of(
            Skeleton.builder().id("sk-w25").width("25").screenReaderText("Loading (25%)").build(),
            Skeleton.builder().id("sk-w33").width("33").screenReaderText("Loading (33%)").build(),
            Skeleton.builder().id("sk-w50").width("50").screenReaderText("Loading (50%)").build(),
            Skeleton.builder().id("sk-w66").width("66").screenReaderText("Loading (66%)").build(),
            Skeleton.builder().id("sk-w75").width("75").screenReaderText("Loading (75%)").build(),
            Skeleton.builder().id("sk-w-full").screenReaderText("Loading (100%)").build());

    public static Skeleton demoSkShapeCircleSm = Skeleton.builder()
            .id("sk-shape-circle-sm").shape("circle").width("sm").height("sm")
            .screenReaderText("Loading avatar").build();

    public static Skeleton demoSkShapeCircleMd = Skeleton.builder()
            .id("sk-shape-circle-md").shape("circle").width("md").height("md")
            .screenReaderText("Loading avatar").build();

    public static Skeleton demoSkShapeSquareMd = Skeleton.builder()
            .id("sk-shape-square-md").shape("square").width("md").height("md")
            .screenReaderText("Loading image").build();

    public static Skeleton demoSkShapePx = Skeleton.builder()
            .id("sk-shape-circle-px").shape("circle").widthValue("80px").heightValue("80px")
            .screenReaderText("Loading avatar (pixel-exact)").build();

    public static List<Skeleton> demoSksText = List.of(
            Skeleton.builder().id("sk-t-sm").fontSize("sm").width("75").screenReaderText("Loading small text").build(),
            Skeleton.builder().id("sk-t-md").fontSize("md").width("75").screenReaderText("Loading medium text").build(),
            Skeleton.builder().id("sk-t-lg").fontSize("lg").width("75").screenReaderText("Loading large text").build(),
            Skeleton.builder().id("sk-t-xl").fontSize("xl").width("75").screenReaderText("Loading xl text").build(),
            Skeleton.builder().id("sk-t-2xl").fontSize("2xl").width("50").screenReaderText("Loading 2xl text").build(),
            Skeleton.builder().id("sk-t-3xl").fontSize("3xl").width("50").screenReaderText("Loading 3xl text").build(),
            Skeleton.builder().id("sk-t-4xl").fontSize("4xl").width("50").screenReaderText("Loading 4xl heading").build());
}
