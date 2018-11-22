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
    private float E0;
    private float E;
    private float IG;
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
        float E0;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Node clone = new Node(this.attributeName, this.label, this.example, this.countOfAttributes);
        return clone;
    }


    public float calculateIGToKnowBestTreeToStart(){
        float value = 0;

        if(this.attributes.size() == 2){
            E0 = 0;
            float p;
            for(Node child : children){
                p = (float) child.examples.size() / this.examples.size();
                E0 += (Math.log10(p) * p);
            }
            E0 *= -1;
            for(Node child : children){
                child.E = 0;
                if(child.children.size() == 1) {continue;}
                else {
                    for (Node postChild : child.children) {
                        p = (float) postChild.examples.size() / child.examples.size();
                        child.E += Math.log10(p) * p;
                    }
                    child.E *= -1;
                }
            }
            for(Node child : children){
                value += child.E * (float) child.examples.size() / this.examples.size();
            }

        }

        return E0 - value;
    }

    public double log(float x, int base) {
        return (Math.log(x) / Math.log(base));
    }

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
        try {
            Node copyNewNode = (Node) newNode.clone();

            //Для головы
            if(this.parent == null && this.children.isEmpty() && this.equals(newNode)){
                try {
                        /*newNode.attributes = new ArrayList<>(this.countOfAttributes);
                        newNode.attributes.add(newNode.attributeName);*/

                    Node copyNode = (Node) this.clone();
                    copyNode.parent = this;

                    this.level = 0;
                    copyNode.level = 1;

                    this.label = "HEAD";

                        /*copyNewNode.examples.add(copyNewNode.example);
                        copyNode.examples.add(copyNode.example);*/

                    this.children.add(copyNode);
                    return;
                } catch (CloneNotSupportedException e) {
                    e.printStackTrace();
                }
            }
            //Добавочные узлы к корню
            if(this.parent == null && !this.children.isEmpty() && !this.examples.contains(copyNewNode.example)){
                if(this.children.get(0).attributeName.equals(copyNewNode.attributeName)) {
                    for (Node child : children) {
                        if (child.label.equals(copyNewNode.label)) {
                            child.examples.add(copyNewNode.example);
                            child.parent.examples.add(copyNewNode.example);
                            return;
                        }
                    }
                    copyNewNode.parent = this;
                    copyNewNode.parent.examples.add(copyNewNode.example);
                    copyNewNode.level = copyNewNode.parent.level+1;
                    this.children.add(copyNewNode);
                    return;
                }
            }
            //Пошло добавление
            //Если аттрибут уже есть в дереве, то нужно добавить пример в нужный аттрибут и в нужный класс
            if(attributes.contains(copyNewNode.attributeName)){
                Node node = this.findParentForNewNode(copyNewNode);
                if(node != null){
                    //Если такой класс уже существует, то просто добавляем пример в узел, и дополняем родительские примеры
                    if(!children.isEmpty()) {
                        for (Node child : node.children) {
                            if (child.label.equals(copyNewNode.label)) {
                                child.examples.add(copyNewNode.example);
                                return;
                            }
                        }
                    }
                    //___________________________________________________________________________
                    //Если узла с таким классом нет, то нужно добавить класс в качестве дочернего
                    copyNewNode.parent = node;
                    copyNewNode.level = copyNewNode.parent.level+1;
                    node.children.add(copyNewNode);
                    return;
                    //___________________________________________________________________________
                }
            }
            else {
                Node node = this.findParentForNewNode(copyNewNode);
                copyNewNode.parent = node;
                copyNewNode.level = node.level+1;
                node.children.add(copyNewNode);
                copyNewNode.addAttributesToParrents(copyNewNode.attributeName);

            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
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