package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Wizard component ({@code pf-v6-c-wizard}) — the
 * client-side Alpine step machine. Server-driven HTMX wizards stay on the
 * slot shell.
 *
 * <pre>
 * Wizard.of("wiz-plain").plain()
 *     .step(Step.of("Information", infoPaneHtml))
 *     .step(Step.of("Configuration", configPaneHtml))
 *     .step(Step.of("Review", reviewPaneHtml))
 *     .footerStandard(true).build()
 * Wizard.of("wiz").header("Create cluster", "Provision a new cluster in three steps.")…
 * Wizard.of("wiz").stateVars("mode: 'quick', custom: false")
 *     .step(Step.of("Custom configuration", html).showExpr("custom"))
 *     .footerButton("Next", "pf-m-primary", null, "step === 3",
 *                   "step = (step === 1 &amp;&amp; mode === 'custom') ? 2 : 3")…
 * </pre>
 *
 * <p>Steps get panel indexes over non-disabled entries; the expandable group
 * generates the {@code open} var. {@code footerStandard} emits clamped
 * Next/Back (+ optional Cancel); explicit footer buttons carry raw exprs.
 */
@TemplateData
public final class Wizard {

    /** One wizard step. */
    @TemplateData
    public static final class Step {
        private final String label;
        private final String paneHtml;
        private final boolean disabled;
        private final String status;
        private final String stepNum;
        private final String showExpr;
        private final int index;

        private Step(String label, String paneHtml, boolean disabled, String status,
                     String stepNum, String showExpr, int index) {
            this.label = label;
            this.paneHtml = paneHtml;
            this.disabled = disabled;
            this.status = status;
            this.stepNum = stepNum;
            this.showExpr = showExpr;
            this.index = index;
        }

        public static Step of(String label, String paneHtml) {
            return new Step(Objects.requireNonNull(label, "label"), paneHtml, false, null, null, null, 0);
        }

        /** Locked step — no pane, no index. */
        public static Step disabledStep(String label) {
            return new Step(Objects.requireNonNull(label, "label"), null, true, null, null, null, 0);
        }

        /** success | danger status icon. */
        public Step status(String status) {
            return new Step(label, paneHtml, disabled, status, stepNum, showExpr, index);
        }

        /** Number badge before the label. */
        public Step stepNum(String stepNum) {
            return new Step(label, paneHtml, disabled, status, stepNum, showExpr, index);
        }

        /** Alpine expression controlling the nav item's visibility (li x-show). */
        public Step showExpr(String showExpr) {
            return new Step(label, paneHtml, disabled, status, stepNum, showExpr, index);
        }

        Step indexed(int index) {
            return new Step(label, paneHtml, disabled, status, stepNum, showExpr, index);
        }

        public String label() {
            return label;
        }

        public String paneHtml() {
            return paneHtml;
        }

        public boolean isDisabled() {
            return disabled;
        }

        public String statusToken() {
            return status;
        }

        public boolean isSuccessStatus() {
            return "success".equals(status);
        }

        public String stepNumText() {
            return stepNum;
        }

        public String showExprText() {
            return showExpr;
        }

        public int stepIndex() {
            return index;
        }

        public String currentExpr() {
            return "step === " + index;
        }

        public String clickExpr() {
            return "step = " + index;
        }
    }

    /** One nav entry — a step or an expandable group of substeps. */
    @TemplateData
    public static final class Entry {
        private final Step step;
        private final String groupLabel;
        private final List<Step> substeps;

        private Entry(Step step, String groupLabel, List<Step> substeps) {
            this.step = step;
            this.groupLabel = groupLabel;
            this.substeps = substeps;
        }

        public boolean isGroup() {
            return groupLabel != null;
        }

        public Step step() {
            return step;
        }

        public String groupLabel() {
            return groupLabel;
        }

        public List<Step> substeps() {
            return substeps;
        }
    }

    /** One footer button with raw Alpine expressions. */
    @TemplateData
    public static final class FooterButton {
        private final String text;
        private final String classes;
        private final String showExpr;
        private final String disabledExpr;
        private final String clickExpr;

        private FooterButton(String text, String classes, String showExpr, String disabledExpr, String clickExpr) {
            this.text = text;
            this.classes = classes;
            this.showExpr = showExpr;
            this.disabledExpr = disabledExpr;
            this.clickExpr = clickExpr;
        }

        public String text() {
            return text;
        }

        public String classes() {
            return classes;
        }

        public String showExprText() {
            return showExpr;
        }

        public String disabledExprText() {
            return disabledExpr;
        }

        public String clickExprText() {
            return clickExpr;
        }
    }

    private final String id;
    private final String navLabel;
    private final boolean plain;
    private final boolean anchors;
    private final String headerTitle;
    private final String headerDescription;
    private final List<Entry> entries;
    private final List<Step> panels;
    private final List<FooterButton> footer;
    private final String xData;

    private Wizard(Builder b) {
        this.id = b.id;
        this.navLabel = b.navLabel;
        this.plain = b.plain;
        this.anchors = b.anchors;
        this.headerTitle = b.headerTitle;
        this.headerDescription = b.headerDescription;

        int idx = 0;
        List<Entry> resolvedEntries = new ArrayList<>();
        List<Step> resolvedPanels = new ArrayList<>();
        boolean hasGroup = false;
        for (Entry e : b.entries) {
            if (e.isGroup()) {
                hasGroup = true;
                List<Step> subs = new ArrayList<>();
                for (Step s : e.substeps) {
                    Step r = s.indexed(++idx);
                    subs.add(r);
                    resolvedPanels.add(r);
                }
                resolvedEntries.add(new Entry(null, e.groupLabel, List.copyOf(subs)));
            } else if (e.step.disabled) {
                resolvedEntries.add(e);
            } else {
                Step r = e.step.indexed(++idx);
                resolvedEntries.add(new Entry(r, null, null));
                resolvedPanels.add(r);
            }
        }
        this.entries = List.copyOf(resolvedEntries);
        this.panels = List.copyOf(resolvedPanels);

        List<FooterButton> fb = new ArrayList<>();
        if (b.standardFooter) {
            int max = idx;
            fb.add(new FooterButton(b.nextText, "pf-m-primary", null,
                    "step === " + max, "step = Math.min(" + max + ", step + 1)"));
            fb.add(new FooterButton(b.backText, "pf-m-secondary", null,
                    "step === 1", "step = Math.max(1, step - 1)"));
            if (b.cancel) {
                fb.add(new FooterButton("Cancel", "pf-m-link", null, null, null));
            }
        }
        fb.addAll(b.footerButtons);
        this.footer = List.copyOf(fb);

        StringBuilder xd = new StringBuilder("{ step: 1");
        if (hasGroup) {
            xd.append(", open: true");
        }
        if (b.stateVars != null) {
            xd.append(", ").append(b.stateVars);
        }
        this.xData = xd.append(" }").toString();
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    public String id() {
        return id;
    }

    public String navLabel() {
        return navLabel != null ? navLabel : "Steps";
    }

    public boolean isPlain() {
        return plain;
    }

    public boolean isAnchors() {
        return anchors;
    }

    public boolean isHasHeader() {
        return headerTitle != null;
    }

    public String headerTitle() {
        return headerTitle;
    }

    public String headerDescription() {
        return headerDescription;
    }

    public List<Entry> entries() {
        return entries;
    }

    public List<Step> panels() {
        return panels;
    }

    public List<FooterButton> footer() {
        return footer;
    }

    public String xData() {
        return xData;
    }

    public static final class Builder {
        private String id;
        private String navLabel;
        private boolean plain;
        private boolean anchors;
        private String headerTitle;
        private String headerDescription;
        private String stateVars;
        private boolean standardFooter;
        private String nextText = "Next";
        private String backText = "Back";
        private boolean cancel;
        private final List<Entry> entries = new ArrayList<>();
        private final List<FooterButton> footerButtons = new ArrayList<>();

        private Builder() {
        }

        public Builder navLabel(String navLabel) {
            this.navLabel = navLabel;
            return this;
        }

        /** No nav border/chrome (pf-m-plain). */
        public Builder plain() {
            this.plain = true;
            return this;
        }

        /** Anchor nav links instead of buttons. */
        public Builder anchors() {
            this.anchors = true;
            return this;
        }

        /** Header region with close button, title and description. */
        public Builder header(String title, String description) {
            this.headerTitle = Objects.requireNonNull(title, "title");
            this.headerDescription = description;
            return this;
        }

        /** Extra x-data entries appended after {@code step: 1} (raw Alpine). */
        public Builder stateVars(String stateVars) {
            this.stateVars = stateVars;
            return this;
        }

        public Builder step(Step step) {
            entries.add(new Entry(Objects.requireNonNull(step, "step"), null, null));
            return this;
        }

        /** Expandable nav group of substeps (generates the {@code open} var). */
        public Builder expandableGroup(String label, Step... substeps) {
            entries.add(new Entry(null, Objects.requireNonNull(label, "label"), List.of(substeps)));
            return this;
        }

        /** Clamped Next/Back footer, optionally with a Cancel link. */
        public Builder footerStandard(boolean cancel) {
            this.standardFooter = true;
            this.cancel = cancel;
            return this;
        }

        /** Explicit footer button with raw Alpine expressions (nullable each). */
        public Builder footerButton(String text, String classes, String showExpr,
                                    String disabledExpr, String clickExpr) {
            footerButtons.add(new FooterButton(Objects.requireNonNull(text, "text"),
                    Objects.requireNonNull(classes, "classes"), showExpr, disabledExpr, clickExpr));
            return this;
        }

        public Wizard build() {
            if (entries.isEmpty()) {
                throw new IllegalStateException("A wizard needs at least one step");
            }
            return new Wizard(this);
        }
    }
}
