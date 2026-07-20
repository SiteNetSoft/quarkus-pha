import org.sitenetsoft.quarkus.pha.model.*;

SearchInput searchInput = SearchInput.of("si-advanced")
        .placeholder("Search…").inputId()
        .advancedField("Username", null).advancedField("Date", "YYYY-MM-DD").build();

// Template side, with the data in scope:
// {#include components/forms/search-input searchInput=searchInput /}
