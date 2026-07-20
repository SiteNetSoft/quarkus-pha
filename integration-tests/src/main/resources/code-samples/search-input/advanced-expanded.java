import org.sitenetsoft.quarkus.pha.model.*;

SearchInput searchInput = SearchInput.of("si-advanced-expanded")
        .placeholder("Search…").inputId().startOpen()
        .advancedField("Username", null).advancedField("Date", "YYYY-MM-DD").build();

// Template side, with the data in scope:
// {#include components/forms/search-input searchInput=searchInput /}
