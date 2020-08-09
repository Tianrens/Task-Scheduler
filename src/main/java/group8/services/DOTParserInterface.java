package group8.services;

import java.util.List;

public interface DOTParserInterface {

    /**
     * Parses an input String, of any .dot file, to retrieve necessary graph data.
     * If it is the first line of the .dot file,
     *                  [0] contains the name of the graph retrieved from the .dot file
     * If it is a node (i.e. "a     |Weight=2|;"), String[].size = 2.
     *                  [0] contains the node id
     *                  [1] contains the weight
     *
     * If it is an edge(i.e. "a -> b |Weight=1|;"), String[].size = 3.
     *                  [0] contains source node id
     *                  [1] contains destination node id
     *                  [2] contains remote weight
     * @param line String to parse
     * @return List of graph data extracted from the input line
     */
    List<String> parseStringLine(String line);

}
