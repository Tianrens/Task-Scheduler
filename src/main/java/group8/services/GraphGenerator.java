package group8.services;

import group8.models.Graph;

import java.util.List;

public class GraphGenerator {

    /**
     * Constructor
     */
    GraphGenerator(){

    }

    /**
     *
     * @param inputFilePath path to .dot file location
     * @return Graph generated from .dot file
     */
    public Graph getGraph(String inputFilePath){

        IOServiceInterface ioService = null;//WAITING ON IMPLEMENTATION
        Graph graph = new Graph();

        List<String[]> graphInput  = ioService.getFileAsStringArray(inputFilePath);
        for(String[] graphData : graphInput){

            if(graphData.length==2){
                /**WAITING ON IMPLEMENTATION
                 *  graph.addNode(new Node(graphData[0], graphData[1]))
                 */

            }else if(graphData.length==3) {
                // The .dot file input can be assumed to be sequential. Therefore all nodes
                // will have been previously initialised before they are referenced as an edge

                /**WAITING ON IMPLEMENTATION
                 *  Node src = graph.getNode(graphData[0]);
                 *  Node dst = graph.getNode(graphData[1]);
                 *  src.addDestination(dst, graphData[2])
                 *  dst.addParent(src)
                 */
            }
        }

        return graph;
    }
}