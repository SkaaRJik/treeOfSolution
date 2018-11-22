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

    public void run(String[][] data){
        Node[][] nodes = DataFactory.convertDataToNode(data);
        int countOfTrees = data[0].length * data[0].length-1;
        List<Node> trees = new ArrayList<>();
            //if(tree == null) tree = nodes[0][3];

            for(int k = 0 ; k < countOfTrees; k++) {
                Node tree = null;
                int afterHead = 0;
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
                    }

                }
            }

        }
            /*for (int j = 0; j < nodes.length; j++) {
                for (int k = 0; k < nodes[0].length; k++) {

                }
                if(tree == null) {
                    try {
                        //tree = (Node) nodes[j][i].clone();
                    } catch (CloneNotSupportedException e) {
                        e.printStackTrace();
                    }
                }
                //tree.push(nodes[j][i]);
            }
            trees.add(tree);*/
            /*if(this.tree == null) this.tree = tree;
            if(this.tree.calculateIGToKnowBestTreeToStart() < tree.calculateIGToKnowBestTreeToStart()) this.tree = tree;
*/
        }
    }
