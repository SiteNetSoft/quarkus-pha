import org.sitenetsoft.quarkus.pha.model.*;

Skeleton circleSm = Skeleton.builder()
.id("sk-shape-circle-sm").shape("circle").width("sm").height("sm")
.screenReaderText("Loading avatar").build();

Skeleton circleMd = Skeleton.builder()
.id("sk-shape-circle-md").shape("circle").width("md").height("md")
.screenReaderText("Loading avatar").build();

Skeleton squareMd = Skeleton.builder()
.id("sk-shape-square-md").shape("square").width("md").height("md")
.screenReaderText("Loading image").build();

Skeleton pixelExact = Skeleton.builder()
.id("sk-shape-circle-px").shape("circle").widthValue("80px").heightValue("80px")
.screenReaderText("Loading avatar (pixel-exact)").build();

// Template side, with the data in scope:
// {#include components/feedback/skeleton skeleton=circleSm /} (one include per skeleton)
