import org.sitenetsoft.quarkus.pha.model.*;

CodeBlock codeBlock = CodeBlock.of("cb-copy",
        "curl -X POST https://api.example.com/v1/projects \\\n"
                + "  -H \"Authorization: Bearer $TOKEN\" \\\n"
                + "  -d '{\"name\": \"my-project\"}'").withCopy().build();

// Template side, with the data in scope:
// {#include components/data-display/code-block codeBlock=codeBlock /}
