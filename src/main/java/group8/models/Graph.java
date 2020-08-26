package group8.models;

import java.util.*;

public class Graph {

    //Contains all the nodes of the graph
    //Key is the nodeId and value is the node itself
    private HashMap<String, Node> _nodes = new HashMap<>();

    private List<List<Node>> _identicalNodes = new ArrayList<>(); // Index represents the identical group id
    private List<Integer> _identicalNodeOrders = new ArrayList<>(); // Index represents the identical group id, value is the next order

    //heuristicCost is the graph's initial heuristic cost
    //Acts as a baseline for comparision for schedules that spawn from this graph
    //Larger schedule heuristic costs are discarded
    private int heuristicCost;

    /**
     * Method used by GraphGenerator to add a new node into the Graph
     * @param newNode node to add to the graph
     */
    public void addNode(Node newNode){
        String nodeID = newNode.getId();
        _nodes.put(nodeID, newNode);
    }

    /**
     * Check for any identical node groupings in the Graph. If there is, create a mapping of it in this {@link Graph}
     * class, and return true, else false.
     * @return {@link boolean} on whether there is an identical node grouping.
     */
    public boolean setUpForIdenticalNodes() {
        boolean result = false;
        List<Node> skip = new ArrayList<>();
        int id = 0;
        for (Node node : _nodes.values()) {
            List<Node> queue = new ArrayList<>();

            if (skip.contains(node)) {
                continue;
            }

            for (Node nodeToCompare : _nodes.values()) {

                if (node.getCost() == nodeToCompare.getCost()) {
                    if (node.getEdgeList().equals(nodeToCompare.getEdgeList())) {
                        if (node.getParentNodeList().equals(nodeToCompare.getParentNodeList())) {
                            if (node.getChildren().equals(nodeToCompare.getChildren())) {
                                result = true;

                                if (node.getIdenticalNodeId() == -1) {
                                    node.setIdenticalNodeId(id);
                                    queue.add(node);
                                    skip.add(node);
                                }
                                nodeToCompare.setIdenticalNodeId(id);
                                queue.add(nodeToCompare);
                                skip.add(nodeToCompare);
                            }
                        }
                    }
                }
            }
            if (! queue.isEmpty()) {
                _identicalNodes.add(id, queue);
                _identicalNodeOrders.add(id, 0);
                id++;
            }
        }

        return result;
    }

    /**
     * Method used to obtain a specific node given its ID
     * @param nodeID the node ID
     * @return the node whose ID was given
     */

    public Node getNode(String nodeID){
        return _nodes.get(nodeID);
    }

    /**
     * Method returns all of the graph's nodes
     * @return Hashmap of all nodes
     */
    public HashMap<String, Node> getAllNodes(){
        return _nodes;
    }

    /**
     * Get the identical node group associated and poll for the NEXT fixed order node.
     * @param identicalGroupId
     * @return {@link Node} The next node to be scheduled for this identical group of nodes (FIXED ORDER).
     */
    public Node getFixedOrderNode(int identicalGroupId) {
        Integer order = _identicalNodeOrders.get(identicalGroupId);
        Node node = _identicalNodes.get(identicalGroupId).get(order); // Get the node that is in the fixed order

        if (order == _identicalNodes.get(identicalGroupId).size() - 1) {
            _identicalNodeOrders.set(identicalGroupId, 0); // Next order
        } else {
            _identicalNodeOrders.set(identicalGroupId, order + 1); // Next order
        }

        return node;
    }

    public List<Node> getGroupOfIdenticalNodes(int identicalGroupId) {
        return _identicalNodes.get(identicalGroupId);
    }

    public int getHeuristicCost() {
        return heuristicCost;
    }

    public void setHeuristicCost(int heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    /**
     * This method is used to bypass having to create a Node and adding it to graph manually
     * @param graphData
     */
    public void addData(List<String> graphData) {
        if (graphData.size() == 1) {
            /** Graph name */
        } else if (graphData.size() == 2) {
            this.addNode(new Node(Integer.parseInt(graphData.get(1)), graphData.get(0)));
        } else if (graphData.size() == 3) {
            // The .dot file input can be assumed to be sequential. Therefore all nodes
            // will have been previously initialised before they are referenced as an edge
            Node src = this.getNode(graphData.get(0));
            Node dst = this.getNode(graphData.get(1));
            src.addDestination(dst, Integer.parseInt(graphData.get(2)));
            dst.addParentNode(src);
        }
    }
}
