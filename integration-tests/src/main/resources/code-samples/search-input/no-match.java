import org.sitenetsoft.quarkus.pha.model.*;

SearchInput searchInput = SearchInput.of("si-no-match")
        .placeholder("Find by name").ariaLabel("Search with no match").value("Joh")
        .style("max-width: 360px").build();

// Template side, with the data in scope:
// {#include components/forms/search-input searchInput=searchInput /}
