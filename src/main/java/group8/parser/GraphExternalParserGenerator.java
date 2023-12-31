package group8.parser;

import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import group8.models.Graph;
import group8.models.Node;

import java.util.Map;
import java.util.stream.Collectors;

import static group8.parser.DOTFileConstants.*;

/**
 * This generates a graph from the input DOT file using a {@link DOTExternalParser}. It is essentially a converter
 * service between the external parser's implementation of edges and nodes into our code bases' implementation of edges and nodes.
 */
public class GraphExternalParserGenerator implements IGraphGenerator {
    private DOTExternalParser _parser;

    public GraphExternalParserGenerator(DOTExternalParser externalParser) {
        _parser = externalParser;
    }

    /**
     *
     * @return graph object holding all the information of the input file
     */
    @Override
    public Graph generate() {
        Graph graph = new Graph();
        Map<String, GraphNode> nodes = _parser.getNodes();
        Map<String, GraphEdge> edges = _parser.getEdges();

        addNodesToGraph(graph, nodes);
        addEdgesToGraph(graph, edges);

        for (Node node : graph.getAllNodes().values()) {
            if (node.getBottomLevel() == -1 ){
                node.calculateBottomLevel(); // calculate bottom level for all nodes - later used in heuristic calculations
            }
        }

        graph.setUpForIdenticalNodes(); // Sets up the identical nodes mapping in the graph object
        return graph;
    }

    /**
     * Adds nodes obtained from the external parser into our graph object
     * @param graph
     * @param nodes
     */
    private void addNodesToGraph(Graph graph, Map<String, GraphNode> nodes) {
        for (String nodeId : nodes.keySet()) {
            GraphNode node = nodes.get(nodeId);

            String weightKey = getWeightKey(node.getAttributes());
            Integer weight = Integer.parseInt((String) node.getAttribute(weightKey)); // Retrieve the relevant attribute (Cost)

            Node newNode = new Node(weight, nodeId);
            graph.addNode(newNode);
        }
    }

    /**
     * Adds edges obtained from the external parser into our graph object
     * @param graph
     * @param edges
     */
    private void addEdgesToGraph(Graph graph, Map<String, GraphEdge> edges) {
        for (String edgeId : edges.keySet()) {
            GraphEdge edge = edges.get(edgeId);

            Node src = graph.getNode(edge.getNode1().getId());
            Node dst = graph.getNode(edge.getNode2().getId());

            String weightKey = getWeightKey(edge.getAttributes());
            Integer weight = Integer.parseInt((String) edge.getAttribute(weightKey));

            src.addDestination(dst, weight);
            dst.addParentNode(src);
        }
    }

    /**
     * Set it up so that the external parse can handle any case combination of the word "weight"
     * @param attrs
     * @return
     */
    private String getWeightKey(Map<String, Object> attrs) {
        return  attrs.keySet()
                .stream()
                .filter(s -> s.equalsIgnoreCase(WEIGHTATTR)) // Pattern match with any case of "Weight" attr key
                .collect(Collectors.toList()).get(0); // ASSUMPTION: each node and edge has ONE Weight attr.
    }
}
