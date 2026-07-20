package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.Pagination;

/**
 * Demo data for the pagination examples — every example on
 * /components/pagination is populated from one of these Pagination models
 * (the sticky/scroll demos keep their wrapper chrome, which owns the
 * outer-scope Alpine variable the sticky bindings reference). Globals so the
 * standalone example route (which renders templates without data) can see
 * them. Each is mirrored by a snippet in resources/code-samples/pagination/
 * served on the example card's Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class PaginationDemoData {

    public static Pagination demoPgTop = Pagination
            .of("pg-top", "Pagination nav - top example").build();

    public static Pagination demoPgBottom = Pagination
            .of("pg-bottom", "Pagination nav - bottom example").asBottom().build();

    public static Pagination demoPgBottomPlain = Pagination
            .of("pg-bottom-plain", "Pagination nav - bottom plain example")
            .asBottom().modifiers("pf-m-plain").build();

    public static Pagination demoPgBottomStatic = Pagination
            .of("pg-bottom-static", "Pagination nav - bottom static example")
            .asBottom().modifiers("pf-m-static").build();

    public static Pagination demoPgBottomSticky = Pagination
            .of("pg-bottom-sticky", "Pagination nav - bottom sticky example")
            .asBottom().modifiers("pf-m-sticky").build();

    public static Pagination demoPgBottomStickyBaseStuck = Pagination
            .of("pg-bottom-sticky-base-stuck", "Pagination nav - bottom sticky with base and stuck example")
            .asBottom().modifiers("pf-m-sticky-base").stickyScroll().build();

    public static Pagination demoPgCompact = Pagination
            .of("pg-compact", "Pagination nav - compact example").asCompact().build();

    public static Pagination demoPgCompactDisplayFull = Pagination
            .of("pg-compact-display-full", "Pagination nav - compact display full example")
            .asCompact().modifiers("pf-m-display-full").build();

    public static Pagination demoPgDisabled = Pagination
            .of("pg-disabled", "Pagination nav - disabled example").asDisabled().build();

    public static Pagination demoPgDisplayFull = Pagination
            .of("pg-display-full", "Pagination nav - display full example")
            .modifiers("pf-m-display-full").build();

    public static Pagination demoPgDisplayResponsive = Pagination
            .of("pg-display-responsive", "Pagination nav - responsive display example")
            .modifiers("pf-m-display-summary pf-m-display-full-on-lg pf-m-display-summary-on-xl pf-m-display-full-on-2xl")
            .build();

    public static Pagination demoPgDisplaySummary = Pagination
            .of("pg-display-summary", "Pagination nav - display summary example")
            .modifiers("pf-m-display-summary").build();

    public static Pagination demoPgDynamicSticky = Pagination
            .of("pg-dynamic-sticky", "Pagination nav - dynamic sticky example")
            .stickyToggle().build();

    public static Pagination demoPgIndeterminate = Pagination
            .of("pg-indeterminate", "Pagination nav - indeterminate example")
            .asIndeterminate().build();

    public static Pagination demoPgInset = Pagination
            .of("pg-inset", "Pagination nav - inset example")
            .modifiers("pf-m-inset-none pf-m-inset-md-on-md pf-m-inset-2xl-on-lg").build();

    public static Pagination demoPgNoItems = Pagination
            .of("pg-no-items", "Pagination nav - no items example").total(0).build();

    public static Pagination demoPgOffset = Pagination
            .of("pg-offset", "Pagination nav - offset example").startPage(3).build();

    public static Pagination demoPgOnePage = Pagination
            .of("pg-one-page", "Pagination nav - one page example").total(8).build();

    public static Pagination demoPgSticky = Pagination
            .of("pg-sticky", "Pagination nav - top sticky example")
            .modifiers("pf-m-sticky").build();

    public static Pagination demoPgStickyBaseStuck = Pagination
            .of("pg-sticky-base-stuck", "Pagination nav - top sticky with base and stuck example")
            .modifiers("pf-m-sticky-base").stickyScroll().build();

    /* Used by the hand-written table container-query-with-drawer demo. */

    public static Pagination demoPgTableCqMain = Pagination
            .of("tbl-cq-main-pagination", "Main table pagination").asBottom().build();

    public static Pagination demoPgTableCqPanel = Pagination
            .of("tbl-cq-panel-pagination", "Panel table pagination").asBottom().build();
}
