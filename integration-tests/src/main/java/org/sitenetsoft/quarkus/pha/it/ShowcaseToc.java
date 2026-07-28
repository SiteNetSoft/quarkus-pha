package org.sitenetsoft.quarkus.pha.it;

import org.sitenetsoft.quarkus.pha.model.JumpLinkItem;
import org.sitenetsoft.quarkus.pha.model.JumpLinks;

/**
 * Builds a showcase page's table of contents in the ws-toc shape: vertical,
 * collapsed behind a toggle below 2xl, a plain right-rail list at 2xl+.
 * Not a template-global itself — {@link ShowcaseTocs} exposes the per-page
 * models. (In a @TemplateGlobal class every non-private static member becomes
 * a global, and globals must be parameterless.)
 */
final class ShowcaseToc {

    private ShowcaseToc() {
    }

    static JumpLinks of(String pageId, JumpLinkItem... items) {
        JumpLinks.Builder b = JumpLinks.of("toc-" + pageId)
                .ariaLabel("Table of contents")
                .vertical()
                .expandableResponsive("pf-m-non-expandable-on-2xl ws-toc", "Table of contents");
        for (JumpLinkItem item : items) {
            b.item(item);
        }
        return b.build();
    }
}
