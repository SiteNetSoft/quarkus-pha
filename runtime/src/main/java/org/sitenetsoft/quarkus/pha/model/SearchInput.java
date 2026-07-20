package org.sitenetsoft.quarkus.pha.model;

import io.quarkus.qute.TemplateData;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Typed model for the Search input pattern (a {@code pf-v6-c-text-input-group}
 * composed with the search icon, utilities, and optional menus/panels).
 *
 * <pre>
 * SearchInput.of("si-basic").build()                                  // static
 * SearchInput.of("si-clear").value("patternfly").build()              // clearable
 * SearchInput.of("si-count").value("orange").count("3").build()       // result count
 * SearchInput.of("si-nav").value("orange").navigable(1, 3).build()    // prev/next matches
 * SearchInput.of("si-submit").submit().build()                       // Search button + echo
 * SearchInput.of("si-exp").expandableToggle().build()                // collapsed magnifier
 * SearchInput.of("si-ac").value("app").startOpen()
 *     .options("apple", "appleby", "appleseed").build()              // autocomplete menu
 * SearchInput.of("si-hint").value("app").startOpen()
 *     .options("appleseed").hint().build()                           // + ghost tab-complete
 * SearchInput.of("si-adv").advancedField("Username", null)
 *     .advancedField("Date", "YYYY-MM-DD").build()                   // attribute form panel
 * </pre>
 *
 * <p>All Alpine state and expressions are generated at build time; advanced
 * field state vars derive from the lowercased label and the submit expression
 * composes the {@code label:value} query.
 */
@TemplateData
public final class SearchInput {

    /** One advanced-search form field. */
    @TemplateData
    public static final class Field {
        private final String label;
        private final String stateVar;
        private final String placeholder;

        private Field(String label, String stateVar, String placeholder) {
            this.label = label;
            this.stateVar = stateVar;
            this.placeholder = placeholder;
        }

        public String label() {
            return label;
        }

        public String stateVar() {
            return stateVar;
        }

        public String placeholder() {
            return placeholder;
        }
    }

    private final String id;
    private final String value;
    private final String placeholder;
    private final String ariaLabel;
    private final String style;
    private final boolean inputId;
    private final String count;
    private final Integer navCurrent;
    private final Integer navTotal;
    private final boolean submit;
    private final boolean expandableToggle;
    private final List<String> options;
    private final boolean hint;
    private final boolean startOpen;
    private final List<Field> fields;

    private SearchInput(Builder b) {
        this.id = b.id;
        this.value = b.value;
        this.placeholder = b.placeholder;
        this.ariaLabel = b.ariaLabel;
        this.style = b.style;
        this.inputId = b.inputId;
        this.count = b.count;
        this.navCurrent = b.navCurrent;
        this.navTotal = b.navTotal;
        this.submit = b.submit;
        this.expandableToggle = b.expandableToggle;
        this.options = b.options;
        this.hint = b.hint;
        this.startOpen = b.startOpen;
        this.fields = List.copyOf(b.fields);
    }

    public static Builder of(String id) {
        Builder b = new Builder();
        b.id = Objects.requireNonNull(id, "id");
        return b;
    }

    public String id() {
        return id;
    }

    public String placeholder() {
        return placeholder;
    }

    public String ariaLabel() {
        return ariaLabel != null ? ariaLabel : "Search";
    }

    public String style() {
        return style;
    }

    public boolean isInputId() {
        return inputId;
    }

    public boolean isStatic() {
        return value == null && !submit && !expandableToggle && options == null && fields.isEmpty();
    }

    public String count() {
        return count;
    }

    public boolean isNavigable() {
        return navCurrent != null;
    }

    public boolean isSubmit() {
        return submit;
    }

    public boolean isExpandableToggle() {
        return expandableToggle;
    }

    public boolean isAutocomplete() {
        return options != null;
    }

    public boolean isHint() {
        return hint;
    }

    public boolean isAdvanced() {
        return !fields.isEmpty();
    }

    public List<Field> fields() {
        return fields;
    }

    public String xData() {
        if (submit) {
            return "{ value: '', submitted: '' }";
        }
        if (expandableToggle) {
            return "{ open: false, value: '' }";
        }
        if (isAdvanced()) {
            StringBuilder sb = new StringBuilder("{ open: ").append(startOpen).append(", value: ''");
            for (Field f : fields) {
                sb.append(", ").append(f.stateVar).append(": ''");
            }
            return sb.append(" }").toString();
        }
        if (isAutocomplete()) {
            StringBuilder sb = new StringBuilder("{ value: '").append(esc(value)).append("', open: ")
                    .append(startOpen).append(", options: [");
            for (int i = 0; i < options.size(); i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append("'").append(esc(options.get(i))).append("'");
            }
            sb.append("], get matches() { return this.options.filter((o) => this.value !== ''")
                    .append(" && o.startsWith(this.value) && o !== this.value) }");
            if (hint) {
                sb.append(", get hint() { return this.matches.length === 1 ? this.matches[0] : '' }");
            }
            return sb.append(" }").toString();
        }
        if (isNavigable()) {
            return "{ value: '" + esc(value) + "', current: " + navCurrent + ", total: " + navTotal + " }";
        }
        if (value != null) {
            return "{ value: '" + esc(value) + "' }";
        }
        return null;
    }

    /** Advanced submit expression composing the label:value query. */
    public String advancedSubmitExpr() {
        StringBuilder sb = new StringBuilder("value = ");
        for (int i = 0; i < fields.size(); i++) {
            Field f = fields.get(i);
            boolean last = i == fields.size() - 1;
            if (i > 0) {
                sb.append(" + ");
            }
            sb.append("(").append(f.stateVar).append(" !== '' ? '")
                    .append(f.label.toLowerCase(Locale.ROOT)).append(":' + ").append(f.stateVar)
                    .append(last ? "" : " + ' '").append(" : '')");
        }
        return sb.append("; open = false").toString();
    }

    /** Advanced reset expression clearing every field and the query. */
    public String advancedResetExpr() {
        StringBuilder sb = new StringBuilder();
        for (Field f : fields) {
            sb.append(f.stateVar).append(" = ''; ");
        }
        return sb.append("value = ''").toString();
    }

    public Integer navCurrent() {
        return navCurrent;
    }

    public Integer navTotal() {
        return navTotal;
    }

    private static String esc(String s) {
        return s == null ? "" : s.replace("'", "\\'");
    }

    public static final class Builder {
        private String id;
        private String value;
        private String placeholder;
        private String ariaLabel;
        private String style;
        private boolean inputId;
        private String count;
        private Integer navCurrent;
        private Integer navTotal;
        private boolean submit;
        private boolean expandableToggle;
        private List<String> options;
        private boolean hint;
        private boolean startOpen;
        private final List<Field> fields = new ArrayList<>();

        private Builder() {
        }

        /** Initial value — makes the input stateful with a clear utility. */
        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Builder placeholder(String placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public Builder ariaLabel(String ariaLabel) {
            this.ariaLabel = ariaLabel;
            return this;
        }

        /** Inline style on the root (demo sizing). */
        public Builder style(String style) {
            this.style = style;
            return this;
        }

        /** Emit id="{id}-input" on the input element. */
        public Builder inputId() {
            this.inputId = true;
            return this;
        }

        /** Static match-count badge next to the clear button. */
        public Builder count(String count) {
            this.count = count;
            return this;
        }

        /** Prev/next match navigation with a current/total badge. */
        public Builder navigable(int current, int total) {
            this.navCurrent = current;
            this.navTotal = total;
            return this;
        }

        /** Input-group wrapper with a Search control button and result echo. */
        public Builder submit() {
            this.submit = true;
            return this;
        }

        /** Collapsed magnifier button that expands into the input. */
        public Builder expandableToggle() {
            this.expandableToggle = true;
            return this;
        }

        /** Autocomplete option list — a menu of startsWith matches opens under the input. */
        public Builder options(String... options) {
            this.options = List.of(options);
            return this;
        }

        /** Ghost pf-m-hint input previewing the single remaining completion (Tab completes). */
        public Builder hint() {
            this.hint = true;
            return this;
        }

        /** Start with the menu/panel open. */
        public Builder startOpen() {
            this.startOpen = true;
            return this;
        }

        /** Advanced-search form field (state var = lowercased label). */
        public Builder advancedField(String label, String placeholder) {
            fields.add(new Field(Objects.requireNonNull(label, "label"),
                    label.toLowerCase(Locale.ROOT).replace(" ", ""), placeholder));
            return this;
        }

        public SearchInput build() {
            return new SearchInput(this);
        }
    }
}
