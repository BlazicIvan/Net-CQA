package Graph.Export;

import org.jgrapht.io.ExportException;

public class GraphExportHelperFactory {

    public static final String FORMAT_GEXF = "gexf";
    public static final String FORMAT_CSV = "csv";
    public static final String FORMAT_DOT = "dot";
    public static final String FORMAT_GML = "gml";

    public static final String DEFAULT_FORMAT = FORMAT_GEXF;

    public static GraphExportHelper createGraphExportHelper(String format) throws ExportException {

        switch (format.toLowerCase()) {

            case FORMAT_GEXF:    return new GexfExportHelper();
            case FORMAT_CSV:     return new CSVExportHelper();
            case FORMAT_DOT:     return new DOTExportHelper();
            case FORMAT_GML:     return new GMLExportHelper();

            default: throw new ExportException("Unsupported graph format \""+format+"\"");
        }
    }


}
