import org.sitenetsoft.quarkus.pha.model.*;

SearchInput searchInput = SearchInput.of("si-expandable")
        .placeholder("Search…").expandableToggle().build();

// Template side, with the data in scope:
// {#include components/forms/search-input searchInput=searchInput /}
