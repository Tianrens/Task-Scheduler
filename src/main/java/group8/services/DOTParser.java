package group8.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DOTParser {
    // Some regex expressions for checking validity of input -- not used atm. These are based on GraphViz DOT syntax.
    private final String IDACCEPTEDLANG = "/(\\w)+/g";
    private final String GRAPHTYPE = "/(?i)\\b(digraph)\\b/g";
    private final String GRAPHNAMEACCEPTEDLANG = "/\"(\\w)+\"/g";
    private final String ATTRACCEPTEDLANG = "/(?i)\\b(Weight)\\b/g";


    public List<String> parseString(String line) {
        ArrayList<String> graphInfo = new ArrayList<>();

        String[] stringElements = line.split(" ");
        if (line.contains(GRAPHTYPE)) {
            graphInfo.add(stringElements[1]);
        } else {
            graphInfo.addAll(parseNodes(stringElements[0]));
            graphInfo.add(parseAttr(stringElements[1]));
        }

        return graphInfo;
    }

    private List<String> parseNodes(String processingString) {
        String[] nodes;

        if (processingString.contains("->")) {
            nodes = processingString.split("->");
        } else {
            nodes = new String[] { processingString };
        }

        return Arrays.asList(nodes);
    }

    private String parseAttr(String processingString) {
        int posEquals = processingString.indexOf("=");
        int posClosingBrace = processingString.indexOf("]");

        return processingString.substring(posEquals, posClosingBrace);
    }
}
