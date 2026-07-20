package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.Objects;

/**
 * One tab of a {@link Tabs} strip — key, label, optional icon/disabled state,
 * and the paired content panel.
 *
 * <pre>
 * TabItem.of("tab-1", "Overview").panel("Overview panel — high-level summary.")
 * TabItem.of("it1", "Users").icon("fa:users").panel("Users panel content")
 * </pre>
 *
 * <p>Panel content renders unescaped (trusted markup like
 * {@code <code class="ws-code">} is allowed).
 */
@TemplateData
public final class TabItem {

    private final String key;
    private final String text;
    private final String icon;
    private final boolean disabled;
    private final String panelHtml;

    private TabItem(String key, String text, String icon, boolean disabled, String panelHtml) {
        this.key = key;
        this.text = text;
        this.icon = icon;
        this.disabled = disabled;
        this.panelHtml = panelHtml;
    }

    public static TabItem of(String key, String text) {
        return new TabItem(Objects.requireNonNull(key, "key"), Objects.requireNonNull(text, "text"),
                null, false, null);
    }

    /** Leading icon, e.g. {@code "fa:users"}. */
    public TabItem icon(String icon) {
        return new TabItem(key, text, icon, disabled, panelHtml);
    }

    public TabItem asDisabled() {
        return new TabItem(key, text, icon, true, panelHtml);
    }

    /** The paired content panel's body (rendered unescaped). */
    public TabItem panel(String panelHtml) {
        return new TabItem(key, text, icon, disabled, panelHtml);
    }

    public String key() {
        return key;
    }

    public String text() {
        return text;
    }

    public String icon() {
        return icon;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public String panelHtml() {
        return panelHtml;
    }
}
