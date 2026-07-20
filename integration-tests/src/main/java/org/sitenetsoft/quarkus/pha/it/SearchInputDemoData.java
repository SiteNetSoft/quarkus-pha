package org.sitenetsoft.quarkus.pha.it;

import io.quarkus.qute.TemplateGlobal;
import org.sitenetsoft.quarkus.pha.model.SearchInput;

/**
 * Demo data for the search-input examples — all examples on
 * /components/search-input are populated from these models (nine generated
 * shapes). Globals so the standalone example route (which renders templates
 * without data) can see them. Each is mirrored by a snippet in
 * resources/code-samples/search-input/ served on the example card's Java tab —
 * keep them in sync when editing.
 */
@TemplateGlobal
public class SearchInputDemoData {

    public static SearchInput demoSiBasic = SearchInput.of("si-basic").placeholder("Search…").build();

    public static SearchInput demoSiClear = SearchInput.of("si-clear")
            .placeholder("Search…").value("patternfly").build();

    public static SearchInput demoSiResultCount = SearchInput.of("si-result-count")
            .placeholder("Search…").value("orange").count("3").inputId()
            .style("max-width: 360px").build();

    public static SearchInput demoSiNoMatch = SearchInput.of("si-no-match")
            .placeholder("Find by name").ariaLabel("Search with no match").value("Joh")
            .style("max-width: 360px").build();

    public static SearchInput demoSiNavigable = SearchInput.of("si-navigable")
            .placeholder("Search…").value("orange").navigable(1, 3).inputId()
            .style("max-width: 420px").build();

    public static SearchInput demoSiSubmit = SearchInput.of("si-submit")
            .placeholder("Search…").submit().inputId().style("max-width: 420px").build();

    public static SearchInput demoSiExpandable = SearchInput.of("si-expandable")
            .placeholder("Search…").expandableToggle().build();

    public static SearchInput demoSiAutocomplete = SearchInput.of("si-autocomplete")
            .ariaLabel("Search with autocomplete").value("app").startOpen()
            .options("apple", "appleby", "appleseed").build();

    public static SearchInput demoSiAutocompleteHint = SearchInput.of("si-autocomplete-hint")
            .ariaLabel("Search with autocomplete hint").value("app").startOpen()
            .options("appleseed").hint().build();

    public static SearchInput demoSiAdvanced = SearchInput.of("si-advanced")
            .placeholder("Search…").inputId()
            .advancedField("Username", null).advancedField("Date", "YYYY-MM-DD").build();

    public static SearchInput demoSiAdvancedExpanded = SearchInput.of("si-advanced-expanded")
            .placeholder("Search…").inputId().startOpen()
            .advancedField("Username", null).advancedField("Date", "YYYY-MM-DD").build();
}
