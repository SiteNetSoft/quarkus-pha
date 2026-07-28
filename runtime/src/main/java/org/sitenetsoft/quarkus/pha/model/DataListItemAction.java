package org.sitenetsoft.quarkus.pha.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.qute.TemplateData;

import java.util.List;

/**
 * One trailing action area of a {@link DataListItem}: either a group of
 * buttons or a kebab menu. Created via {@link DataListItem#action},
 * {@link DataListItem#kebab} and {@link DataListItem#responsiveActions}.
 */
@TemplateData
public final class DataListItemAction {

    private final boolean kebab;
    private final String ariaLabel;
    private final List<TableAction> actions;
    private final String modifiers;

    @JsonCreator
    DataListItemAction(@JsonProperty("kebab") boolean kebab,
            @JsonProperty("ariaLabel") String ariaLabel,
            @JsonProperty("actions") List<TableAction> actions,
            @JsonProperty("modifiers") String modifiers) {
        this.kebab = kebab;
        this.ariaLabel = ariaLabel;
        this.actions = actions == null ? List.of() : List.copyOf(actions);
        this.modifiers = modifiers;
    }

    public boolean isKebab() {
        return kebab;
    }

    /** aria-label of the kebab toggle. */
    public String ariaLabel() {
        return ariaLabel;
    }

    public List<TableAction> actions() {
        return actions;
    }

    /** Responsive visibility modifiers on the action area, or null. */
    public String css() {
        return modifiers;
    }
}
