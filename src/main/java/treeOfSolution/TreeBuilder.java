package treeOfSolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeBuilder {

    Node head;

    private class Node {
        Node parent;
        String label;
        List<String> attributes;
        String attributeName;
        List<Node> children;
        List<Integer> examples;
        int example;
        int level;
        List<List<Node>> ierarchy;

        /*void push(Node newNode, Boolean stop){
            if(this.children.get(0) != null){
                if(this.children.get(0).attributeName.equals(newNode.attributeName)){
                    for (Node child: this.children) {
                        if(child.label.equals(newNode.label)){
                            this.examples.add(newNode.example);
                            return;
                        }
                    }
                    newNode.examples.add(newNode.example);
                    children.add(newNode);
                    return;
                } else {
                    if(!stop.booleanValue()) {
                        for (Node child : this.children) {
                            if(stop.booleanValue()) return;
                            if (child.label.equals(newNode.label)) {
                                this.examples.add(newNode.example);
                                return;
                            }
                        }
                    }
                }

            }
        }*/

        Node findNodeWithChildrenByClassName(String attributeName){
            Node node = null;
                if(this.attributes.size() == 2 &  this.attributes.contains(attributeName)) return this;
                if(children != null) {
                    if(!children.isEmpty())
                        if(children.get(0).children.contains(attributeName))
                            node = children.get(0).findNodeWithChildrenByClassName(attributeName);
                }
            return node;
        }

        void addExampleToParrents(Integer example){
            if(this.parent != null) {
                this.parent.examples.add(example);
                this.parent.addExampleToParrents(example);
            }
        }

        public void push(Node newNode){
            //Если аттрибут уже есть в дереве, то нужно добавить пример в нужный аттрибут и в нужный класс
            if(attributes.contains(newNode.attributeName)){
                Node node = this.findNodeWithChildrenByClassName(newNode.attributeName);
                if(node != null){
                    //Если такой класс уже существует, то просто добавляем пример в узел, и дополняем родительские примеры
                    for(Node child : node.children){
                        if(child.label.equals(newNode.label)){
                            child.examples.add(newNode.example);
                            child.addExampleToParrents(newNode.example);
                            return;
                        }
                    }
                    //___________________________________________________________________________
                    //Если узла с таким классом нет, то нужно добавить класс в качестве дочернего
                    newNode.examples.add(example);
                    newNode.parent = node;
                    newNode.level = newNode.parent.level+1;
                    node.children.add(newNode);
                    newNode.addExampleToParrents(newNode.example);
                    //___________________________________________________________________________

                }
            }
            else{

            }
        }

        public void addNewNodeWithNewClass(){

        }


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


}
