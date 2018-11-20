package treeOfSolution;

import utils.DataFactory;

import java.util.*;

public class TreeBuilder {

    Node tree;

    public TreeBuilder() {
    }

    public Node getTree() {
        return tree;
    }

    void dataByPass(String[][] data){
        for (int i = 0; i < data[0].length; i++) {
            Map< String, List<String>> zippedData = new HashMap<String, List<String>>();
            List<String> line = new ArrayList<String>(data[0].length);
            for (int j = 1; j < data[i].length; j++) {
                //line.add(data[])
            }
            //zippedData.put(data[0][j], )
        }
    }

    public void run(String[][] data){
        Node[][] nodes = DataFactory.convertDataToNode(data);

        for (int i = 0; i < 1; i++) {
            if(tree == null) tree = nodes[0][3];
            for (int j = 0; j < nodes.length; j++) {
                tree.push(nodes[j][3]);
            }
            for (int j = 0; j < nodes.length; j++) {
                tree.push(nodes[j][1]);
            }
        }
    }


}
