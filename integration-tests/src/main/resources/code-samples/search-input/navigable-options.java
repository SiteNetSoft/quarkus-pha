import org.sitenetsoft.quarkus.pha.model.*;

SearchInput searchInput = SearchInput.of("si-navigable")
        .placeholder("Search…").value("orange").navigable(1, 3).inputId()
        .style("max-width: 420px").build();

// Template side, with the data in scope:
// {#include components/forms/search-input searchInput=searchInput /}
