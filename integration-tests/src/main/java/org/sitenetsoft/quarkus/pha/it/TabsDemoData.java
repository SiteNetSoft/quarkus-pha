package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.TabItem;
import org.sitenetsoft.quarkus.pha.model.Tabs;

/**
 * Demo data for the tabs examples — the composed examples on /components/tabs
 * are populated from these Tabs models. The overflow, horizontal-overflow,
 * dynamic, vertical-expandable and help/close families stay hand-written:
 * their scroll/menu/expand machinery is a composition outside the model's
 * scope (their _action-tabs/_dynamic-flavor partials remain). Globals so the
 * standalone example route (which renders templates without data) can see
 * them. Each is mirrored by a snippet in resources/code-samples/tabs/ served
 * on the example card's Java tab — keep them in sync when editing.
 */
@TemplateGlobal
public class TabsDemoData {

    public static Tabs demoTabsBasic = Tabs.of("tabs-basic")
            .item(TabItem.of("tab-1", "Overview").panel("Overview panel — high-level summary."))
            .item(TabItem.of("tab-2", "Details").panel("Details panel — per-field values."))
            .item(TabItem.of("tab-3", "Logs").panel("Logs panel — recent activity."))
            .panelPadding()
            .build();

    public static Tabs demoTabsBox = Tabs.of("tabs-box").box()
            .item(TabItem.of("b1", "Tab 1").panel("Box variant — content one."))
            .item(TabItem.of("b2", "Tab 2").panel("Box variant — content two."))
            .panelPadding()
            .build();

    public static Tabs demoTabsBoxInset = Tabs.of("tabs-box-inset").box()
            .modifiers("pf-m-inset-sm-on-md pf-m-inset-lg-on-lg pf-m-inset-2xl-on-xl")
            .ariaLabel("Box tabs with adjusted insets")
            .item(TabItem.of("bi1", "Users").panel("Users panel content"))
            .item(TabItem.of("bi2", "Containers").panel("Containers panel content"))
            .item(TabItem.of("bi3", "Database").panel("Database panel content"))
            .build();

    public static Tabs demoTabsBoxSecondary = Tabs.of("tabs-box-secondary").box().secondary()
            .ariaLabel("Box secondary tabs")
            .item(TabItem.of("s1", "Users").panel("Users panel content"))
            .item(TabItem.of("s2", "Containers").panel("Containers panel content"))
            .item(TabItem.of("s3", "Database").panel("Database panel content"))
            .build();

    public static Tabs demoTabsBoxVertical = Tabs.of("tabs-box-vertical").box().vertical()
            .item(TabItem.of("bv1", "General").panel("General settings."))
            .item(TabItem.of("bv2", "Security").panel("Security settings."))
            .item(TabItem.of("bv3", "Notifications").panel("Notification preferences."))
            .panelPadding()
            .build();

    public static Tabs demoTabsVertical = Tabs.of("tabs-vertical").vertical()
            .item(TabItem.of("v1", "General").panel("General settings."))
            .item(TabItem.of("v2", "Security").panel("Security settings."))
            .item(TabItem.of("v3", "Notifications").panel("Notification preferences."))
            .panelPadding()
            .build();

    public static Tabs demoTabsIconsText = Tabs.of("tabs-icons-text")
            .ariaLabel("Tabs with icons and text")
            .item(TabItem.of("it1", "Users").icon("fa:users").panel("Users panel content"))
            .item(TabItem.of("it2", "Containers").icon("fa:cube").panel("Containers panel content"))
            .item(TabItem.of("it3", "Database").icon("fa:database").panel("Database panel content"))
            .build();

    public static Tabs demoTabsFilled = Tabs.of("tabs-filled").filled()
            .ariaLabel("Filled tabs")
            .item(TabItem.of("fl1", "Users").panel("Users panel content"))
            .item(TabItem.of("fl2", "Containers").panel("Containers panel content"))
            .item(TabItem.of("fl3", "Database").panel("Database panel content"))
            .build();

    public static Tabs demoTabsFilledBox = Tabs.of("tabs-filled-box").filled().box()
            .ariaLabel("Filled box tabs")
            .item(TabItem.of("fb1", "Users").panel("Users panel content"))
            .item(TabItem.of("fb2", "Containers").panel("Containers panel content"))
            .item(TabItem.of("fb3", "Database").panel("Database panel content"))
            .build();

    public static Tabs demoTabsFilledWithIcons = Tabs.of("tabs-filled-with-icons").filled()
            .ariaLabel("Filled tabs with icons")
            .item(TabItem.of("f1", "Users").icon("fa:users").panel("Users panel content"))
            .item(TabItem.of("f2", "Containers").icon("fa:cube").panel("Containers panel content"))
            .item(TabItem.of("f3", "Database").icon("fa:database").panel("Database panel content"))
            .build();

    public static Tabs demoTabsFilledBoxWithIcons = Tabs.of("tabs-filled-box-with-icons").filled().box()
            .ariaLabel("Filled box tabs with icons")
            .item(TabItem.of("fbi1", "Users").icon("fa:users").panel("Users panel content"))
            .item(TabItem.of("fbi2", "Containers").icon("fa:cube").panel("Containers panel content"))
            .item(TabItem.of("fbi3", "Database").icon("fa:database").panel("Database panel content"))
            .build();

    public static Tabs demoTabsInset = Tabs.of("tabs-inset")
            .modifiers("pf-m-inset-sm-on-md pf-m-inset-lg-on-lg pf-m-inset-2xl-on-xl")
            .ariaLabel("Tabs with adjusted inset")
            .item(TabItem.of("i1", "Users").panel("Users panel content"))
            .item(TabItem.of("i2", "Containers").panel("Containers panel content"))
            .item(TabItem.of("i3", "Database").panel("Database panel content"))
            .build();

    public static Tabs demoTabsPageInsets = Tabs.of("tabs-page-insets").pageInsets()
            .ariaLabel("Tabs with page insets")
            .item(TabItem.of("p1", "Users").panel("Users panel content"))
            .item(TabItem.of("p2", "Containers").panel("Containers panel content"))
            .item(TabItem.of("p3", "Database").panel("Database panel content"))
            .build();

    public static Tabs demoTabsContentBodyPadding = Tabs.of("tabs-content-body-padding")
            .ariaLabel("Tabs with padded content body")
            .item(TabItem.of("cb1", "Users")
                    .panel("This tab content body carries <code class=\"ws-code\">pf-m-padding</code>."))
            .item(TabItem.of("cb2", "Containers").panel("Containers panel — also padded."))
            .panelPadding()
            .build();

    public static Tabs demoTabsSubtabsMain = Tabs.of("tabs-subtabs-main")
            .ariaLabel("Main tabs").externalState()
            .item(TabItem.of("t1", "Users"))
            .item(TabItem.of("t2", "Containers"))
            .build();

    public static Tabs demoTabsSubtabsSecondary = Tabs.of("tabs-subtabs-secondary")
            .subtab().ariaLabel("Secondary tabs").externalState().stateVar("sub")
            .item(TabItem.of("s1", "Active"))
            .item(TabItem.of("s2", "Disabled"))
            .item(TabItem.of("s3", "Pending"))
            .build();

    public static Tabs demoTabsBoxSubtabsMain = Tabs.of("tabs-box-subtabs-main")
            .box().ariaLabel("Main box tabs").externalState()
            .item(TabItem.of("t1", "Users"))
            .item(TabItem.of("t2", "Containers"))
            .build();

    public static Tabs demoTabsBoxSubtabsSecondary = Tabs.of("tabs-box-subtabs-secondary")
            .subtab().ariaLabel("Secondary tabs").externalState().stateVar("sub")
            .item(TabItem.of("s1", "Active"))
            .item(TabItem.of("s2", "Disabled"))
            .item(TabItem.of("s3", "Pending"))
            .build();

    public static Tabs demoTabsNav = Tabs.of("tabs-nav").nav()
            .ariaLabel("Tabs linked to nav elements")
            .item(TabItem.of("n1", "Cluster overview").panel("Cluster overview panel"))
            .item(TabItem.of("n2", "Nodes").panel("Nodes panel"))
            .item(TabItem.of("n3", "Workloads").panel("Workloads panel"))
            .build();

    public static Tabs demoTabsNavMain = Tabs.of("tabs-nav-main").nav()
            .ariaLabel("Main navigation tabs").externalState()
            .item(TabItem.of("m1", "Cluster"))
            .item(TabItem.of("m2", "Nodes"))
            .build();

    public static Tabs demoTabsNavSubtab = Tabs.of("tabs-nav-subtab").nav().subtab()
            .ariaLabel("Secondary navigation tabs").externalState().stateVar("sub")
            .item(TabItem.of("sb1", "Details"))
            .item(TabItem.of("sb2", "Metrics"))
            .item(TabItem.of("sb3", "Events"))
            .build();

    public static Tabs demoTabsSiteNav = Tabs.of("tabs-site-nav").nav().pageInsets().noBorderBottom()
            .ariaLabel("Site navigation tabs")
            .item(TabItem.of("sn1", "Home"))
            .item(TabItem.of("sn2", "Documentation"))
            .item(TabItem.of("sn3", "Community"))
            .item(TabItem.of("sn4", "Support"))
            .build();

    public static Tabs demoTabsPanels = Tabs.of("tabs-panels").panelsOnly()
            .item(TabItem.of("tc1", "Panel 1").panel("Panel 1"))
            .item(TabItem.of("tc2", "Panel 2").panel("Panel 2"))
            .item(TabItem.of("tc3", "Panel 3").panel("Panel 3"))
            .build();

    public static Tabs demoTabsPanelsSecondary = Tabs.of("tabs-panels-secondary").panelsOnly().panelSecondary()
            .item(TabItem.of("tcs1", "Panel 1").panel("Panel 1"))
            .item(TabItem.of("tcs2", "Panel 2").panel("Panel 2"))
            .item(TabItem.of("tcs3", "Panel 3").panel("Panel 3"))
            .build();

    public static Tabs demoTabsAnimateDefault = Tabs.of("tabs-animate-default").animated()
            .ariaLabel("Animated tabs")
            .item(TabItem.of("ad1", "Users").panel("Users panel content"))
            .item(TabItem.of("ad2", "Containers").panel("Containers panel content"))
            .item(TabItem.of("ad3", "Database").panel("Database panel content"))
            .build();

    public static Tabs demoTabsAnimateFilled = Tabs.of("tabs-animate-filled").animated().filled()
            .ariaLabel("Animated filled tabs")
            .item(TabItem.of("af1", "Users").panel("Users panel content"))
            .item(TabItem.of("af2", "Containers").panel("Containers panel content"))
            .item(TabItem.of("af3", "Database").panel("Database panel content"))
            .build();

    public static Tabs demoTabsAnimateSubtabsMain = Tabs.of("tabs-animate-subtabs-main")
            .modifiers("pf-m-animate-current").ariaLabel("Animated main tabs").externalState()
            .item(TabItem.of("t1", "Users"))
            .item(TabItem.of("t2", "Containers"))
            .build();

    public static Tabs demoTabsAnimateSubtabsSecondary = Tabs.of("tabs-animate-subtabs-secondary")
            .subtab().modifiers("pf-m-animate-current").ariaLabel("Animated secondary tabs")
            .externalState().stateVar("sub")
            .item(TabItem.of("s1", "Active"))
            .item(TabItem.of("s2", "Disabled"))
            .item(TabItem.of("s3", "Pending"))
            .build();

    public static Tabs demoTabsAnimateVertical = Tabs.of("tabs-animate-vertical").animated().vertical()
            .ariaLabel("Animated vertical tabs")
            .item(TabItem.of("av1", "General").panel("General settings."))
            .item(TabItem.of("av2", "Security").panel("Security settings."))
            .item(TabItem.of("av3", "Notifications").panel("Notification preferences."))
            .panelPadding()
            .build();
}
