import org.sitenetsoft.quarkus.pha.model.*;

SearchInput searchInput = SearchInput.of("si-result-count")
        .placeholder("Search…").value("orange").count("3").inputId()
        .style("max-width: 360px").build();

// Template side, with the data in scope:
// {#include components/forms/search-input searchInput=searchInput /}
