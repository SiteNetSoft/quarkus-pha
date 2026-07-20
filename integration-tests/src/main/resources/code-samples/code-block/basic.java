import org.sitenetsoft.quarkus.pha.model.*;

CodeBlock codeBlock = CodeBlock.of("cb-basic",
        "apiVersion: v1\nkind: Pod\nmetadata:\n  name: example\nspec:\n  containers:\n"
                + "    - name: nginx\n      image: nginx:1.25").build();

// Template side, with the data in scope:
// {#include components/data-display/code-block codeBlock=codeBlock /}
