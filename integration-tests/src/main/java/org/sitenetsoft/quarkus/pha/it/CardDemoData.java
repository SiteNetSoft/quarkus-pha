package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Card;

import java.util.List;

/**
 * Demo data for the card examples — every example on /components/card except
 * the live modifier playground is populated from these Card models. Selection
 * and click state binds to chrome-owned Alpine variables declared in the
 * example files' wrapper divs. Globals so the standalone example route (which
 * renders templates without data) can see them. Each is mirrored by a snippet
 * in resources/code-samples/card/ served on the example card's Java tab —
 * keep them in sync when editing.
 */
@TemplateGlobal
public class CardDemoData {

    private static final String BRAND = "/web/images/quarkus-pha-icon.svg";

    public static Card demoCardBasic = Card.of("cd-basic")
            .title("Project Apollo")
            .body("Ship the new dashboard by end of quarter. Owner: Alice. Status: on track.")
            .footer("Last updated 2 hours ago")
            .build();

    public static List<Card> demoCardsSecondary = List.of(
            Card.of("cd-secondary-1").secondary()
                    .title("First card")
                    .body("<code>pf-m-secondary</code> uses the secondary background color variant.")
                    .build(),
            Card.of("cd-secondary-2").secondary()
                    .title("Second card")
                    .body("Secondary cards pair well on a primary surface.")
                    .build(),
            Card.of("cd-secondary-3").secondary()
                    .title("Third card")
                    .body("All three share the secondary treatment.")
                    .build());

    public static Card demoCardCompact = Card.of("cd-compact").compact()
            .title("Compact card")
            .body("Tighter padding — useful in dense card grids.")
            .build();

    public static Card demoCardFlat = Card.of("cd-flat")
            .title("Flat card")
            .body("No shadow — sits flush against the background.")
            .build();

    public static Card demoCardBodySectionFills = Card.of("cd-body-section-fills").fullHeight()
            .title("Card title")
            .bodyNoFill("Body section with <code>pf-m-no-fill</code>")
            .bodyNoFill("Another no-fill body section")
            .body("This default body section fills the remaining card height.")
            .footer("Footer")
            .build();

    public static Card demoCardMultipleBodySections = Card.of("cd-multiple-body-sections")
            .title("Card title")
            .body("First body section")
            .body("Second body section")
            .body("Third body section")
            .footer("Footer")
            .build();

    public static Card demoCardWithDividers = Card.of("cd-with-dividers")
            .title("Card title")
            .divider()
            .body("First body section")
            .divider()
            .body("Second body section")
            .divider()
            .footer("Footer")
            .build();

    public static Card demoCardHeadingElement = Card.of("cd-heading-element")
            .title("This title is an &lt;h4&gt;", null, "h4")
            .body("Pick the heading level that fits the page outline — the styling comes from the class, not the tag.")
            .footer("Footer")
            .build();

    public static Card demoCardSubtitle = Card.of("cd-subtitle")
            .header(Card.Header.create()
                    .mainTitle("Card title", null)
                    .mainSubtitle("Card subtitle"))
            .body("The subtitle sits in its own <code>pf-v6-c-card__subtitle</code> element below the title.")
            .footer("Footer")
            .build();

    public static Card demoCardSubtitleActions = Card.of("cd-subtitle-actions")
            .header(Card.Header.create()
                    .kebab("Card actions")
                    .checkbox("cd-subtitle-actions-check", "cd-subtitle-actions-title")
                    .mainTitle("Card title", "cd-subtitle-actions-title")
                    .mainSubtitle("Card subtitle"))
            .body("Header actions (kebab + checkbox) pair with a title + subtitle in the header main area.")
            .footer("Footer")
            .build();

    public static Card demoCardHeaderImagesActions = Card.of("cd-header-images-actions")
            .header(Card.Header.create()
                    .kebab("Card actions")
                    .checkbox("cd-header-images-actions-check", "cd-header-images-actions-title")
                    .brand(BRAND, "Quarkus PHA", "40px"))
            .title("Card title", "cd-header-images-actions-title", null)
            .body("A brand image in the header with actions aligned to the end.")
            .footer("Footer")
            .build();

    public static Card demoCardHeaderWithoutTitle = Card.of("cd-header-without-title")
            .header(Card.Header.create()
                    .kebab("Card actions")
                    .checkbox("cd-header-without-title-check", "cd-header-without-title-title")
                    .checkboxAriaLabel("Select card"))
            .body("This is the card body. The header above carries only actions — no title element at all.")
            .build();

    public static Card demoCardHeaderWraps = Card.of("cd-header-wraps")
            .header(Card.Header.create()
                    .asWrapped()
                    .kebab("Card actions")
                    .checkbox("cd-header-wraps-check", "cd-header-wraps-title")
                    .mainTitle("This is a really really really really really really really really really really long header",
                            "cd-header-wraps-title"))
            .body("<code>pf-m-wrap</code> on the header lets a long title wrap under the actions.")
            .build();

    public static Card demoCardTitleInlineImagesActions = Card.of("cd-title-inline-images-actions")
            .header(Card.Header.create()
                    .kebab("Card actions")
                    .checkbox("cd-title-inline-images-actions-check", "cd-title-inline-images-actions-title")
                    .brandMain(BRAND, "Quarkus PHA", "32px")
                    .mainTitle("This title sits in the card head", "cd-title-inline-images-actions-title"))
            .body("Image and title share the header main area, inline with the actions.")
            .footer("Footer")
            .build();

    public static Card demoCardExpandable = Card.of("cd-expandable").expandable()
            .header(Card.Header.create()
                    .withExpandToggle()
                    .kebab("Card actions")
                    .checkbox("cd-expandable-check", "cd-expandable-title")
                    .title("Expandable card", "cd-expandable-title"))
            .expandableContent("The expandable content — hidden until the toggle is expanded.", "Footer")
            .build();

    public static Card demoCardExpandableWithIcon = Card.of("cd-expandable-with-icon").expandable()
            .header(Card.Header.create()
                    .withExpandToggle()
                    .kebab("Card actions")
                    .checkbox("cd-expandable-with-icon-check", "cd-expandable-with-icon-title")
                    .checkboxAriaLabel("Select card")
                    .brandMain(BRAND, "Quarkus PHA", "32px"))
            .expandableContent("A brand image takes the title&#39;s place in the header.", "Footer")
            .build();

    public static List<Card> demoCardsSelectable = List.of(
            Card.of("cd-selectable-1").selectable().selectedExpr("sel1")
                    .header(Card.Header.create()
                            .selectCheckbox("cd-selectable-1-check", "cd-selectable-1-title", "sel1")
                            .title("First card", "cd-selectable-1-title"))
                    .body("This card is selectable.")
                    .build(),
            Card.of("cd-selectable-2").selectable().selectedExpr("sel2")
                    .header(Card.Header.create()
                            .selectCheckbox("cd-selectable-2-check", "cd-selectable-2-title", "sel2")
                            .title("Second card", "cd-selectable-2-title"))
                    .body("This card is selectable.")
                    .build(),
            Card.of("cd-selectable-3").selectable().asDisabled()
                    .header(Card.Header.create()
                            .selectCheckbox("cd-selectable-3-check", "cd-selectable-3-title", null)
                            .asSelectDisabled()
                            .title("Third card", "cd-selectable-3-title"))
                    .body("This card is disabled.")
                    .build());

    public static List<Card> demoCardsSingleSelectable = List.of(
            Card.of("cd-single-selectable-1").selectable().selectedExpr("chosen === 'first'")
                    .header(Card.Header.create()
                            .selectRadio("cd-single-selectable-radio", "cd-single-selectable-1-radio",
                                    "first", "cd-single-selectable-1-title", "chosen")
                            .title("First card", "cd-single-selectable-1-title"))
                    .body("Only one card in this set can be selected.")
                    .build(),
            Card.of("cd-single-selectable-2").selectable().selectedExpr("chosen === 'second'")
                    .header(Card.Header.create()
                            .selectRadio("cd-single-selectable-radio", "cd-single-selectable-2-radio",
                                    "second", "cd-single-selectable-2-title", "chosen")
                            .title("Second card", "cd-single-selectable-2-title"))
                    .body("Radio-backed single selection.")
                    .build(),
            Card.of("cd-single-selectable-3").selectable().selectedExpr("chosen === 'third'")
                    .header(Card.Header.create()
                            .selectRadio("cd-single-selectable-radio", "cd-single-selectable-3-radio",
                                    "third", "cd-single-selectable-3-title", "chosen")
                            .title("Third card", "cd-single-selectable-3-title"))
                    .body("Pick me instead.")
                    .build());

    public static List<Card> demoCardsActionable = List.of(
            Card.of("cd-actionable-1").clickable()
                    .header(Card.Header.create()
                            .clickButton("cd-actionable-1-title", "clicks++")
                            .title("First card", "cd-actionable-1-title"))
                    .body("Performs an action on click — clicked <span x-text=\"clicks\">0</span> times.")
                    .build(),
            Card.of("cd-actionable-2").clickable()
                    .header(Card.Header.create()
                            .clickAnchor("#", "cd-actionable-2-title")
                            .title("Second card", "cd-actionable-2-title"))
                    .body("This whole card is a link.")
                    .build(),
            Card.of("cd-actionable-3").clickable().asDisabled()
                    .header(Card.Header.create()
                            .clickButton("cd-actionable-3-title", null)
                            .asSelectDisabled()
                            .title("Third card", "cd-actionable-3-title"))
                    .body("This clickable card is disabled.")
                    .build());

    public static Card demoCardActionableSelectable = Card.of("cd-actionable-selectable-1")
            .clickable().selectable().selectedExpr("sel1")
            .header(Card.Header.create()
                    .selectCheckbox("cd-actionable-selectable-1-check", "cd-actionable-selectable-1-title", "sel1")
                    .title("<button class=\"pf-v6-c-card__clickable-action\" type=\"button\" @click=\"clicks++\" style=\"all: unset; cursor: pointer\">Clickable title</button>",
                            "cd-actionable-selectable-1-title"))
            .body("Selectable via the checkbox AND actionable via the title — clicked <span x-text=\"clicks\">0</span> times.")
            .build();

    public static List<Card> demoCardsMultiSelectableTiles = List.of(
            Card.of("cd-mtile-1").selectable().selectedExpr("t1")
                    .header(Card.Header.create()
                            .selectCheckbox("cd-mtile-1-input", "cd-mtile-1-title", "t1")
                            .mainIcon("fa:server")
                            .title("Tile one", "cd-mtile-1-title"))
                    .body("Pick any number of tiles.")
                    .build(),
            Card.of("cd-mtile-2").selectable().selectedExpr("t2")
                    .header(Card.Header.create()
                            .selectCheckbox("cd-mtile-2-input", "cd-mtile-2-title", "t2")
                            .mainIcon("fa:server")
                            .title("Tile two", "cd-mtile-2-title"))
                    .body("Checkbox-backed multi selection.")
                    .build(),
            Card.of("cd-mtile-3").selectable().selectedExpr("t3")
                    .header(Card.Header.create()
                            .selectCheckbox("cd-mtile-3-input", "cd-mtile-3-title", "t3")
                            .mainIcon("fa:server")
                            .title("Tile three", "cd-mtile-3-title"))
                    .body("Toggle freely.")
                    .build());

    public static List<Card> demoCardsSingleSelectableTiles = List.of(
            Card.of("cd-tile-1").selectable().selectedExpr("chosen === 'one'")
                    .header(Card.Header.create()
                            .selectRadio("cd-tile-radio", "cd-tile-1-input", "one", "cd-tile-1-title", "chosen")
                            .mainIcon("fa:server")
                            .title("Tile one", "cd-tile-1-title"))
                    .body("Pick exactly one tile.")
                    .build(),
            Card.of("cd-tile-2").selectable().selectedExpr("chosen === 'two'")
                    .header(Card.Header.create()
                            .selectRadio("cd-tile-radio", "cd-tile-2-input", "two", "cd-tile-2-title", "chosen")
                            .mainIcon("fa:server")
                            .title("Tile two", "cd-tile-2-title"))
                    .body("Radio-backed selectable card.")
                    .build(),
            Card.of("cd-tile-3").selectable().selectedExpr("chosen === 'three'")
                    .header(Card.Header.create()
                            .selectRadio("cd-tile-radio", "cd-tile-3-input", "three", "cd-tile-3-title", "chosen")
                            .mainIcon("fa:server")
                            .title("Tile three", "cd-tile-3-title"))
                    .body("Tiles are just selectable cards.")
                    .build());
}
