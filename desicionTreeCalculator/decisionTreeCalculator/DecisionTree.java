public class DecisionTree {

    private class Node{
        String name;
        Node yes;
        Node no;
        
        Node(String name){
            this.name = name.toLowerCase();
        }
    }

    private Node root;
    private int size;

    public DecisionTree(String root){
        this.root = new Node(root);
        size = 1;
    }
    
    public boolean add(String parent, String yesChild, String noChild){
        Node parentNode = find(root, parent.toLowerCase());
        if (parentNode == null) return false;
        if (parentNode.yes != null || parentNode.no != null) return false;
        if (contains(yesChild) || contains(noChild)) return false;

        parentNode.yes = new Node(yesChild);
        parentNode.no = new Node(noChild);
        size += 2;
        return true;
    }    

    private Node find(Node node, String value) {
        if (node == null) return null;
        if (node.name.equals(value)) return node;

        Node found = find(node.yes, value);
        if (found != null) return found;
        return find(node.no, value);
    }
    
    public boolean delete(String nodeValue){
        if (nodeValue == null || root == null) return false;
        nodeValue = nodeValue.toLowerCase();

        if (root.name.equals(nodeValue)){
            root = null;
            size = 0;
            return true;
        }
        return deleteHelper(root, nodeValue);
    }

    private boolean deleteHelper(Node parent, String value){
        if (parent == null) return false;

        if (parent.yes != null && parent.yes.name.equals(value)){
            size -= countNodes(parent.yes);
            parent.yes = null;
            return true;
        }
        if (parent.no != null && parent.no.name.equals(value)){
            size -= countNodes(parent.no);
            parent.no = null;
            return true;
        }

        return deleteHelper(parent.yes, value) || deleteHelper(parent.no, value);
    }

    private int countNodes(Node node){
        if (node == null) return 0;
        return 1 + countNodes(node.yes) + countNodes(node.no);
    }

    public DecisionTree eval(String[][] values) {
        if (root == null || values == null) return null;

        Node current = root;
        for (String[] qa : values) {
            if (qa == null || qa.length < 2) continue;

            String question = qa[0].toLowerCase();
            String answer = qa[1].toLowerCase();

            if (current.name.equals(question)) {
                if (answer.equals("yes") || answer.equals("true")) {
                    if (current.yes != null) {
                        current = current.yes;
                    } else {
                        break;
                    }
                } else if (answer.equals("no") || answer.equals("false")) {
                    if (current.no != null) {
                        current = current.no;
                    } else {
                        break;
                    }
                }
            }
        }

        if (current != null) {
            DecisionTree result = new DecisionTree(current.name);
            result.root = copyNode(current);
            result.size = countNodes(result.root);
            return result;
        }
        return null;
    }

    private Node copyNode(Node node) {
        if (node == null) return null;
        Node newNode = new Node(node.name);
        newNode.yes = copyNode(node.yes);
        newNode.no = copyNode(node.no);
        return newNode;
    }
    
    public boolean contains(String node){
        return find(root, node.toLowerCase()) != null;
    }
    
    public boolean isQuestion(String node){
        Node n = find(root, node.toLowerCase());
        if (n == null) return false;
        return (n.yes != null || n.no != null);
    }

    public boolean isDecision(String node){
        Node n = find(root, node.toLowerCase());
        if (n == null) return false; 
        return (n.yes == null && n.no == null);
    }
    
    public DecisionTree union(DecisionTree dt){
        if (dt == null || dt.root == null) return this;
        DecisionTree result = new DecisionTree(this.root.name);
        result.root = mergeNodes(this.root, dt.root);
        return result;
    }

    private Node mergeNodes(Node a, Node b){
        if (a == null) return copyNode(b);
        if (b == null) return copyNode(a);

        String newName = a.name.equals(b.name) ? a.name : a.name + "|" + b.name;
        Node merged = new Node(newName);
        merged.yes = mergeNodes(a.yes, b.yes);
        merged.no = mergeNodes(a.no, b.no);
        return merged;
    }
    
    public int nodes(){
        return size;
    }
    
    public int height(){
        return getHeight(root);
    }    

    private int getHeight(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeight(node.yes), getHeight(node.no));
    }
    
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DecisionTree other = (DecisionTree) obj;
        return nodesEqual(this.root, other.root);
    }

    private boolean nodesEqual(Node node1, Node node2) {
        if (node1 == null && node2 == null) return true;
        if (node1 == null || node2 == null) return false;
        if (!node1.name.equals(node2.name)) return false;
        return nodesEqual(node1.yes, node2.yes) && nodesEqual(node1.no, node2.no);
    }

    public String toString() {
        return nodeToString(root);
    }

    private String nodeToString(Node node) {
        if (node == null) return "";
        if (node.yes == null && node.no == null) {
            return "(" + node.name + ")";
        }
        return "(" + node.name + " yes " + nodeToString(node.yes) + " no " + nodeToString(node.no) + ")";
    }
}