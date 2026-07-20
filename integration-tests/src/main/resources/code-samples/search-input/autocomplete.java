import org.sitenetsoft.quarkus.pha.model.*;

SearchInput searchInput = SearchInput.of("si-autocomplete")
        .ariaLabel("Search with autocomplete").value("app").startOpen()
        .options("apple", "appleby", "appleseed").build();

// Template side, with the data in scope:
// {#include components/forms/search-input searchInput=searchInput /}
