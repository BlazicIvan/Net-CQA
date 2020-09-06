package Report.Composition;

import com.hp.gagawa.java.elements.Div;
import com.hp.gagawa.java.elements.Li;
import com.hp.gagawa.java.elements.Ul;

import java.util.Map;

public class TableReportElement extends ReportElement {

    private final Map table;
    private static final String MAP_ENTRY_FORMAT = "%s: %s";

    public TableReportElement(Map table) {
        this.table = table;
    }

    @Override
    public String generate() {

        String content = "";

        if (!table.keySet().isEmpty() && !table.entrySet().isEmpty()) {

            Div div = new Div();
            Ul ul = new Ul();

            div.setCSSClass("map");

            table.keySet().stream().sorted().forEach(key -> {
                Li li = new Li();
                String listEntry = String.format(MAP_ENTRY_FORMAT, key, table.get(key));
                li.appendText(listEntry);
                ul.appendChild(li);
            });

            div.appendChild(ul);

            content = div.write();
        }



        return content;
    }
}
