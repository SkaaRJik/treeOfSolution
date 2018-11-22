package treeOfSolution;

import utils.DataFactory;

import java.util.*;

public class TreeBuilder {

    Node tree;

    public TreeBuilder() {
        this.tree = null;
    }

    public Node getTree() {
        return tree;
    }

    public void run(String[][] data) {
        Node[][] nodes = DataFactory.convertDataToNode(data);
        int countOfTrees = data[0].length * (data[0].length - 1);
        //if(tree == null) tree = nodes[0][3];
        Node currentTree = null;
        int firstIndex = 0;
        int secondIndex = 1;
        for (int k = 0; k < countOfTrees; k++) {
            if(secondIndex >= nodes[0].length) {
                secondIndex = 0;
                firstIndex++;
            }
            try {
                for (int i = 0; i < nodes.length; i++) {
                    if (currentTree == null) currentTree = (Node) nodes[i][firstIndex].clone();
                    currentTree.push(nodes[i][firstIndex]);
                }
                for (int i = 0; i < nodes.length; i++) {
                    currentTree.push(nodes[i][secondIndex]);
                }
                if(this.tree == null) {
                    this.tree = currentTree;
                } else {
                    if(this.tree.calculateIGToKnowBestTreeToStart() < currentTree.calculateIGToKnowBestTreeToStart()) this.tree = currentTree;

                }
                currentTree = null;
                secondIndex++;
                if(firstIndex == secondIndex) secondIndex++;
            } catch (CloneNotSupportedException ex){
                ex.printStackTrace();
            }

                /*int afterHead = 0;
                for (int j = 0; j < nodes[0].length; j++) {
                    for (int i = 0; i < nodes.length; i++) {
                        if(tree == null) {
                            try {
                                tree = (Node) nodes[j][i].clone();
                            } catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    for (int l = 0; l < nodes.length; l++) {
                        if(tree == null) {
                            try {
                                tree = (Node) nodes[j][i].clone();
                            } catch (CloneNotSupportedException e) {
                                e.printStackTrace();
                            }
                        }
                    }*/

        }
    }

}
