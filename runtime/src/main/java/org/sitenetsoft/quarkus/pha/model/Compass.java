package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Compass application shell ({@code pf-v6-c-compass}).
 *
 * <pre>
 * Compass.of("cmp-basic").style("height: 320px")
 *     .header("Compass shell", "padding: 0.5rem 1rem").headerExpanded()
 *     .main(contentHtml)
 *     .footerHtml(messageBarHtml).footerExpanded().build()
 * Compass.of("cmp-docked").docked()
 *     .dock("padding: 0.5rem; display: flex; flex-direction: column; gap: 0.5rem",
 *           Button.icon("fa:house", "Home").variant("plain").build(), ...)
 *     .header("Docked nav", "padding: 0.5rem 1rem").headerExpanded()
 *     .main(contentHtml).build()
 * </pre>
 *
 * <p>The dock rail and header actions are composed {@link Button} models; main
 * and footer content are raw HTML. Sidebars stay on the slot shell.
 */
@TemplateData
public final class Compass {

    private final String id;
    private final String style;
    private final boolean docked;
    private final String dockStyle;
    private final List<Button> dockButtons;
    private final String headerTitle;
    private final String headerContentStyle;
    private final List<Button> headerActions;
    private final boolean headerExpanded;
    private final String mainHtml;
    private final String footerHtml;
    private final boolean footerExpanded;

    private Compass(Builder b) {
        this.id = b.id;
        this.style = b.style;
        this.docked = b.docked;
        this.dockStyle = b.dockStyle;
        this.dockButtons = b.dockButtons;
        this.headerTitle = b.headerTitle;
        this.headerContentStyle = b.headerContentStyle;
        this.headerActions = b.headerActions;
        this.headerExpanded = b.headerExpanded;
        this.mainHtml = b.mainHtml;
        this.footerHtml = b.footerHtml;
        this.footerExpanded = b.footerExpanded;
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    public String id() {
        return id;
    }

    public String style() {
        return style;
    }

    public boolean isDocked() {
        return docked;
    }

    public boolean isHasDock() {
        return dockButtons != null;
    }

    public String dockStyle() {
        return dockStyle;
    }

    public List<Button> dockButtons() {
        return dockButtons;
    }

    public boolean isHasHeader() {
        return headerTitle != null;
    }

    public String headerTitle() {
        return headerTitle;
    }

    public String headerContentStyle() {
        return headerContentStyle;
    }

    public boolean isHasHeaderActions() {
        return headerActions != null && !headerActions.isEmpty();
    }

    public List<Button> headerActions() {
        return headerActions;
    }

    public boolean isHeaderExpanded() {
        return headerExpanded;
    }

    public String mainHtml() {
        return mainHtml;
    }

    public String footerHtml() {
        return footerHtml;
    }

    public boolean isFooterExpanded() {
        return footerExpanded;
    }

    public static final class Builder {
        private String id;
        private String style;
        private boolean docked;
        private String dockStyle;
        private List<Button> dockButtons;
        private String headerTitle;
        private String headerContentStyle;
        private List<Button> headerActions;
        private boolean headerExpanded;
        private String mainHtml;
        private String footerHtml;
        private boolean footerExpanded;

        private Builder() {
        }

        /** Inline style on the compass root. */
        public Builder style(String style) {
            this.style = style;
            return this;
        }

        /** pf-m-docked — reserves space for the dock rail. */
        public Builder docked() {
            this.docked = true;
            return this;
        }

        /** Dock rail: a styled __dock-main column of composed buttons. */
        public Builder dock(String dockStyle, Button... buttons) {
            this.dockStyle = dockStyle;
            this.dockButtons = List.of(buttons);
            return this;
        }

        /** Header region: __main-header-content with a title. */
        public Builder header(String title, String contentStyle) {
            this.headerTitle = Objects.requireNonNull(title, "title");
            this.headerContentStyle = contentStyle;
            return this;
        }

        /** Trailing header toolbar of composed buttons. */
        public Builder headerActions(Button... actions) {
            this.headerActions = List.of(actions);
            return this;
        }

        public Builder headerExpanded() {
            this.headerExpanded = true;
            return this;
        }

        /** Main region content (raw HTML). */
        public Builder main(String mainHtml) {
            this.mainHtml = mainHtml;
            return this;
        }

        /** Footer region content (raw HTML). */
        public Builder footerHtml(String footerHtml) {
            this.footerHtml = footerHtml;
            return this;
        }

        public Builder footerExpanded() {
            this.footerExpanded = true;
            return this;
        }

        public Compass build() {
            if (mainHtml == null) {
                throw new IllegalStateException("A compass needs main(...) content");
            }
            return new Compass(this);
        }
    }
}
