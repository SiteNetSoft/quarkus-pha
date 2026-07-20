import org.sitenetsoft.quarkus.pha.model.*;

SearchInput searchInput = SearchInput.of("si-submit")
        .placeholder("Search…").submit().inputId().style("max-width: 420px").build();

// Template side, with the data in scope:
// {#include components/forms/search-input searchInput=searchInput /}
