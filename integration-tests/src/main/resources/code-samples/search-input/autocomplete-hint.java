import org.sitenetsoft.quarkus.pha.model.*;

SearchInput searchInput = SearchInput.of("si-autocomplete-hint")
        .ariaLabel("Search with autocomplete hint").value("app").startOpen()
        .options("appleseed").hint().build();

// Template side, with the data in scope:
// {#include components/forms/search-input searchInput=searchInput /}
