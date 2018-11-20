package treeOfSolution;

import java.util.*;

public class Node implements Cloneable{
    private Node parent;
    private String label;
    private Set<String> attributes;
    private String attributeName;
    private List<Node> children;
    private List<Integer> examples;
    private int example;
    private int level;
    private List< List< List< Node > > > ierarchy;

    private int countOfAttributes;


    public Node(String attributeName, String label, int example, int countOfAttributes) {
        this.attributeName = attributeName;
        this.label = label;
        this.example = example;
        this.examples = new ArrayList<>(10);
        this.examples.add(example);
        this.countOfAttributes = countOfAttributes;
        this.attributes = new HashSet<>();
        this.attributes.add(attributeName);
        this.children = new ArrayList<>();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Node clone = new Node(this.attributeName, this.label, this.example, this.countOfAttributes);
        return clone;
    }


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

        /*Node findNodeWithChildrenByAttributeName(String attributeName){
            Node node = null;
                if(this.attributes.size() == 2 &  this.attributes.contains(attributeName)) return this;
                if(children != null) {
                    if(!children.isEmpty())
                        if(children.get(0).children.contains(attributeName))
                            for(Node child : this.children)
                            node = children.get(0).findNodeWithChildrenByAttributeName(attributeName);
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
                Node node = this.findNodeWithChildrenByAttributeName(newNode.attributeName);
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

        }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return example == node.example &&
                level == node.level &&
                Objects.equals(label, node.label) &&
                Objects.equals(attributeName, node.attributeName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, attributeName, example, level);
    }

    public void push(Node newNode){
        //Для головы
        if(this.parent == null && this.children.isEmpty() && this.equals(newNode)){
            try {
                    /*newNode.attributes = new ArrayList<>(this.countOfAttributes);
                    newNode.attributes.add(newNode.attributeName);*/

                Node copyNode = (Node) newNode.clone();
                copyNode.parent = newNode;

                newNode.level = 0;
                copyNode.level = 1;

                newNode.label = "HEAD";

                    /*newNode.examples.add(newNode.example);
                    copyNode.examples.add(copyNode.example);*/

                this.children.add(copyNode);
                return;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        //Добавочные узлы к корню
        if(this.parent == null && !this.children.isEmpty() && !this.examples.contains(newNode.example)){
            if(this.children.get(0).attributeName.equals(newNode.attributeName)) {
                for (Node child : children) {
                    if (child.label.equals(newNode.label)) {
                        child.examples.add(newNode.example);
                        child.parent.examples.add(newNode.example);
                        return;
                    }
                }
                newNode.parent = this;
                newNode.parent.examples.add(newNode.example);
                newNode.level = newNode.parent.level+1;
                this.children.add(newNode);
                return;
            }
        }
        //Пошло добавление
        //Если аттрибут уже есть в дереве, то нужно добавить пример в нужный аттрибут и в нужный класс
        if(attributes.contains(newNode.attributeName)){
            Node node = this.findParentForNewNode(newNode);
            if(node != null){
                //Если такой класс уже существует, то просто добавляем пример в узел, и дополняем родительские примеры
                if(!children.isEmpty()) {
                    for (Node child : node.children) {
                        if (child.label.equals(newNode.label)) {
                            child.examples.add(newNode.example);
                            return;
                        }
                    }
                }
                //___________________________________________________________________________
                //Если узла с таким классом нет, то нужно добавить класс в качестве дочернего
                newNode.parent = node;
                newNode.level = newNode.parent.level+1;
                node.children.add(newNode);
                return;
                //___________________________________________________________________________
            }
        }
        else {
            Node node = this.findParentForNewNode(newNode);
            newNode.parent = node;
            newNode.level = node.level+1;
            node.children.add(newNode);
            newNode.addAttributesToParrents(newNode.attributeName);

        }
    }

    Node findParentForNewNode(Node newNode){
        Node parentNode = null;
        //if(this.attributes.size() == 2 &&  this.attributes.contains(newNode.attributeName) && this.examples.contains(newNode.example)) return this;

        if(this.attributes.size() == 1 &&  !this.attributes.contains(newNode.attributeName)){
            if(children != null) {
                if(!children.isEmpty())
                    for(Node child : this.children) {
                        if(child.examples.contains(newNode.example))
                            return child;
                    }
            }
        }
        if(children != null) {
            if(!children.isEmpty())
                for(Node child : this.children) {
                    if(child.examples.contains(newNode.example) && child.attributes.contains(newNode.attributeName))
                        return child.findParentForNewNode(newNode);
                    else if(child.examples.contains(newNode.example) && !child.attributes.contains(newNode.attributeName))
                        return child;
                }

        }
        return parentNode;
    }

    void addAttributesToParrents(String attributeName){
        if(this.parent != null) {
            this.parent.attributes.add(attributeName);
            this.parent.addAttributesToParrents(attributeName);
        }
    }

}