import org.sitenetsoft.quarkus.pha.model.*;

Panel panel = Panel.of("pn-scroll-auto")
        .scrollableAutoHeight().bordered().style("height: 100%").focusableMain()
        .body("""
                <p>Row 1</p>
                <p>Row 2</p>
                <p>Row 3</p>
                <p>Row 4</p>
                <p>Row 5</p>
                <p>Row 6</p>
                <p>Row 7</p>
                <p>Row 8</p>
                <p>Row 9</p>
                <p>Row 10</p>""")
        .build();

// Template side, with the data in scope:
// {#include components/data-display/panel panel=panel /}
