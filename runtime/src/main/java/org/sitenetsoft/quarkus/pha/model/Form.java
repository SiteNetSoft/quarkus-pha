package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Typed model for the Form component ({@code pf-v6-c-form}).
 *
 * <pre>
 * Form.of("form-basic")
 *     .group(Form.Group.of("Name", "form-basic-name-field")
 *         .control(TextInput.of("form-basic-name").placeholder("Enter name").build()).build())
 *     .build()
 * Form.of("f").horizontal()
 *     .group(Form.Group.of("Email", "f-email-field")
 *         .control(TextInput.of("f-email").type("email").validated("error").build())
 *         .helper("Enter a valid email address.", "error").build())
 *     .build()
 * Form.of("f").section("Section 1", group1).section("Section 2 (optional)", group2).build()
 * Form.of("f").grid(Form.GridItem.of("pf-m-6-col-on-md", first),
 *                   Form.GridItem.of("pf-m-12-col", email)).build()
 * </pre>
 *
 * <p>Ordered entries: form groups, titled sections of groups, PF-grid rows,
 * and inline danger alerts. A group's control is a composed {@link TextInput}
 * or raw HTML; helper text renders below the control (or above via
 * {@code helperOnTop()}) with per-variant icons derived in Java. Live-Alpine
 * forms (expandable field groups, as-you-type validation) stay hand-written.
 */
@TemplateData
public final class Form {

    /** One labeled form group. */
    @TemplateData
    public static final class Group {
        private final String label;
        private final String forId;
        private final boolean required;
        private final String labelInfo;
        private final boolean noPaddingTop;
        private final TextInput textInput;
        private final String controlHtml;
        private final String helperText;
        private final String helperVariant;
        private final boolean helperOnTop;

        private Group(Builder b) {
            this.label = b.label;
            this.forId = b.forId;
            this.required = b.required;
            this.labelInfo = b.labelInfo;
            this.noPaddingTop = b.noPaddingTop;
            this.textInput = b.textInput;
            this.controlHtml = b.controlHtml;
            this.helperText = b.helperText;
            this.helperVariant = b.helperVariant;
            this.helperOnTop = b.helperOnTop;
        }

        public static Builder of(String label, String forId) {
            Builder b = new Builder();
            b.label = Objects.requireNonNull(label, "label");
            b.forId = Objects.requireNonNull(forId, "forId");
            return b;
        }

        public String label() {
            return label;
        }

        public String forId() {
            return forId;
        }

        public boolean isRequired() {
            return required;
        }

        public String labelInfo() {
            return labelInfo;
        }

        public boolean isNoPaddingTop() {
            return noPaddingTop;
        }

        public TextInput textInput() {
            return textInput;
        }

        public String controlHtml() {
            return controlHtml;
        }

        public String helperText() {
            return helperText;
        }

        public String helperVariant() {
            return helperVariant;
        }

        /** Derived helper icon (error/success/warning); null for plain helpers. */
        public String helperIcon() {
            if (helperVariant == null) {
                return null;
            }
            return switch (helperVariant) {
                case "success" -> "fa:circle-check";
                case "warning" -> "fa:triangle-exclamation";
                case "error" -> "fa:circle-exclamation";
                default -> null;
            };
        }

        public boolean isHelperOnTop() {
            return helperOnTop;
        }

        public static final class Builder {
            private String label;
            private String forId;
            private boolean required;
            private String labelInfo;
            private boolean noPaddingTop;
            private TextInput textInput;
            private String controlHtml;
            private String helperText;
            private String helperVariant;
            private boolean helperOnTop;

            private Builder() {
            }

            public Builder required() {
                this.required = true;
                return this;
            }

            /** Extra info beside the label (group-label-main anatomy). */
            public Builder labelInfo(String labelInfo) {
                this.labelInfo = labelInfo;
                return this;
            }

            /** pf-m-no-padding-top on the label column (horizontal forms with stacked/on-top content). */
            public Builder noPaddingTop() {
                this.noPaddingTop = true;
                return this;
            }

            /** Composed text-input control. */
            public Builder control(TextInput textInput) {
                this.textInput = textInput;
                return this;
            }

            /** Raw HTML control content (form-control spans, check stacks, ...). */
            public Builder controlHtml(String controlHtml) {
                this.controlHtml = controlHtml;
                return this;
            }

            /** Plain helper text below the control. */
            public Builder helper(String helperText) {
                this.helperText = helperText;
                return this;
            }

            /** Variant helper text (error | success | warning) with the derived icon. */
            public Builder helper(String helperText, String variant) {
                this.helperText = helperText;
                this.helperVariant = variant;
                return this;
            }

            /** Render the helper above the control instead of below. */
            public Builder helperOnTop() {
                this.helperOnTop = true;
                return this;
            }

            public Group build() {
                if (textInput == null && controlHtml == null) {
                    throw new IllegalStateException("A form group needs control(...) or controlHtml(...)");
                }
                return new Group(this);
            }
        }
    }

    /** One PF-grid cell holding a group. */
    @TemplateData
    public static final class GridItem {
        private final String colClass;
        private final Group group;

        private GridItem(String colClass, Group group) {
            this.colClass = colClass;
            this.group = group;
        }

        public static GridItem of(String colClass, Group group) {
            return new GridItem(Objects.requireNonNull(colClass, "colClass"),
                    Objects.requireNonNull(group, "group"));
        }

        public String colClass() {
            return colClass;
        }

        public Group group() {
            return group;
        }
    }

    /** One ordered form entry — group, section, grid row, or danger alert. */
    @TemplateData
    public static final class Entry {
        private final Group group;
        private final String sectionTitle;
        private final List<Group> groups;
        private final List<GridItem> gridItems;
        private final String alertTitle;

        private Entry(Group group, String sectionTitle, List<Group> groups,
                      List<GridItem> gridItems, String alertTitle) {
            this.group = group;
            this.sectionTitle = sectionTitle;
            this.groups = groups;
            this.gridItems = gridItems;
            this.alertTitle = alertTitle;
        }

        public boolean isGroup() {
            return group != null;
        }

        public Group group() {
            return group;
        }

        public boolean isSection() {
            return sectionTitle != null;
        }

        public String sectionTitle() {
            return sectionTitle;
        }

        public List<Group> groups() {
            return groups;
        }

        public boolean isGrid() {
            return gridItems != null;
        }

        public List<GridItem> gridItems() {
            return gridItems;
        }

        public boolean isDangerAlert() {
            return alertTitle != null;
        }

        public String alertTitle() {
            return alertTitle;
        }
    }

    private final String id;
    private final boolean horizontal;
    private final boolean limitWidth;
    private final String style;
    private final List<Entry> entries;

    private Form(Builder b) {
        this.id = b.id;
        this.horizontal = b.horizontal;
        this.limitWidth = b.limitWidth;
        this.style = b.style;
        this.entries = List.copyOf(b.entries);
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    public String id() {
        return id;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public boolean isLimitWidth() {
        return limitWidth;
    }

    public String style() {
        return style;
    }

    public List<Entry> entries() {
        return entries;
    }

    public static final class Builder {
        private String id;
        private boolean horizontal;
        private boolean limitWidth;
        private String style;
        private final List<Entry> entries = new ArrayList<>();

        private Builder() {
        }

        /** Labels in a fixed-width column beside the controls (pf-m-horizontal). */
        public Builder horizontal() {
            this.horizontal = true;
            return this;
        }

        /** Cap the form width (pf-m-limit-width). */
        public Builder limitWidth() {
            this.limitWidth = true;
            return this;
        }

        /** Inline style on the form root (e.g. "max-width: 480px"). */
        public Builder style(String style) {
            this.style = style;
            return this;
        }

        public Builder group(Group group) {
            entries.add(new Entry(Objects.requireNonNull(group, "group"), null, null, null, null));
            return this;
        }

        /** Titled __section wrapping its groups. */
        public Builder section(String title, Group... groups) {
            entries.add(new Entry(null, Objects.requireNonNull(title, "title"),
                    List.of(groups), null, null));
            return this;
        }

        /** PF grid row (pf-v6-l-grid pf-m-gutter) of column-classed groups. */
        public Builder grid(GridItem... items) {
            entries.add(new Entry(null, null, null, List.of(items), null));
            return this;
        }

        /** Inline danger alert (invalid-form pattern). */
        public Builder dangerAlert(String title) {
            entries.add(new Entry(null, null, null, null, Objects.requireNonNull(title, "title")));
            return this;
        }

        public Form build() {
            if (entries.isEmpty()) {
                throw new IllegalStateException("A form needs at least one entry");
            }
            return new Form(this);
        }
    }
}
