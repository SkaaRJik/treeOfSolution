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
        short countOfAttributes = (short) data[0].length;
        int countOfTrees = countOfAttributes * (countOfAttributes - 1);
        //if(tree == null) tree = nodes[0][3];
        Node currentTree = null;
        short firstIndex = 0;
        short secondIndex = 1;
        short bestColumn1 = -1;
        short bestColumn2 = -1;
        try {
            for (int k = 0; k < countOfTrees; k++) {
                if (secondIndex >= countOfAttributes) {
                    secondIndex = 0;
                    firstIndex++;
                }

                for (int i = 0; i < nodes.length; i++) {
                    if (currentTree == null) currentTree = (Node) nodes[i][firstIndex].clone();
                    currentTree.push(nodes[i][firstIndex]);
                }
                for (int i = 0; i < nodes.length; i++) {
                    currentTree.push(nodes[i][secondIndex]);
                }
                if (this.tree == null) {
                    this.tree = currentTree;
                    this.tree.getIGOfLastAdding();
                    bestColumn1 = firstIndex;
                    bestColumn2 = secondIndex;
                } else {
                    if (this.tree.getLastIG() < currentTree.getIGOfLastAdding()) {
                        this.tree = currentTree;
                        bestColumn1 = firstIndex;
                        bestColumn2 = secondIndex;
                    }
                }
                currentTree = null;
                secondIndex++;
                if (firstIndex == secondIndex) secondIndex++;

            }
            List<Short> allowedColumns = new ArrayList<>(nodes[0].length);
            for (short i = 0; i < countOfAttributes; i++) {
                if(i == bestColumn1 || i == bestColumn2) continue;
                allowedColumns.add(i);
            }


            /*for (int i = 0; i < nodes.length; i++) {
                tree.push(nodes[i][allowedColumns.get(0)]);
            }
            tree.getIGOfLastAdding();
            int a = 0;*/

            //currentTree = (Node) this.tree.clone();

            while (!allowedColumns.isEmpty()){
                currentTree = this.tree;
                boolean secondStage = true;
                for (Short column: allowedColumns) {
                    for (int i = 0; i < nodes.length; i++) {
                        currentTree.push(nodes[i][column]);
                    }
                    if(secondStage) {
                        this.tree = (Node) currentTree.clone();
                        this.tree.getIGOfLastAdding();
                        secondStage = false;
                    }
                    if(this.tree.getLastIG() < currentTree.getIGOfLastAdding()){
                        this.tree = currentTree;
                        currentTree = (Node) this.tree.clone();
                        bestColumn2 = column;
                    }
                    if (allowedColumns.size() == 1){
                        this.tree = currentTree;
                        bestColumn2 = column;

                        break;
                    }
                    currentTree.deleteLastLevel(nodes[0][column].getAttributeName());
                }
                allowedColumns.remove(new Short(bestColumn2));
            }

            int a = 0;
        } catch(CloneNotSupportedException ex){
            System.out.println("Произошла ошибка в методе TreeBuilder.run");
        ex.printStackTrace();
        return;
        }
}

}
