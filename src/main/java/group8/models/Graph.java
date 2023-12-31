package group8.models;

import java.util.*;

/**
 * Class used to model the graph derived from the input dot file
 */
public class Graph {

    //Contains all the nodes of the graph
    //Key is the nodeId and value is the node itself
    private HashMap<String, Node> _nodes = new HashMap<>();
    private List<List<Node>> _identicalNodes = new ArrayList<>(); // Index represents the identical group id
    private List<Integer> _identicalNodeOrders = new ArrayList<>(); // Index represents the identical group id, value is the next order

    //heuristicCost is the graph's initial heuristic cost
    //Acts as a baseline for comparision for schedules that spawn from this graph
    //Larger schedule heuristic costs are discarded
    private double heuristicCost = -1;

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

            if (skip.contains(node)) { // Skips if it is already part of a group. At all times it will only belong to ONE group
                continue;
            }

            for (Node nodeToCompare : _nodes.values()) { // Loops through all other nodes to compare

                if (node.getId().equals(nodeToCompare.getId())) {
                    continue;
                }

                if (node.getCost() == nodeToCompare.getCost()) { // Cost
                    if (node.getEdgeList().equals(nodeToCompare.getEdgeList())) { // Children
                        if (node.getParentNodeList().equals(nodeToCompare.getParentNodeList())) { // Parent nodes are the same

                            boolean isSame = true;
                            for (Node parent : node.getParentNodeList()) { // Parent node edges costs to node and nodeToCompare are the same
                                if (parent.getEdgeList().get(node) != parent.getEdgeList().get(nodeToCompare)) {
                                    isSame = false;
                                    break;
                                }
                            }

                            if (!isSame) {
                                continue;
                            }

                            // They are identified as identical now. Assign them their identical group ids.
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
            if (! queue.isEmpty()) {
                _identicalNodes.add(id, queue);
                _identicalNodeOrders.add(id, 0); // Initialise to order 0
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

        // Set next order for the identical nodes group
        if (order == _identicalNodes.get(identicalGroupId).size() - 1) {
            _identicalNodeOrders.set(identicalGroupId, 0); // wraps around
        } else {
            _identicalNodeOrders.set(identicalGroupId, order + 1);
        }

        return node;
    }


    public List<Node> getGroupOfIdenticalNodes(int identicalGroupId) {
        return _identicalNodes.get(identicalGroupId);
    }

    public double getHeuristicCost() {
        return heuristicCost;
    }

    public void setHeuristicCost(double heuristicCost) {
        this.heuristicCost = heuristicCost;
    }
}
