public class DecisionTree {

    private class Node{
        String name;
        Node yes;
        Node no;
        
        Node(String name){
            this.name=name.toLowerCase();
        }
    }

    private Node root;
    private int size;

    public DecisionTree(String root){
        this.root=new Node(root);
        size=1;
    }
    
    public boolean add(String parent, String yesChild, String noChild){
        Node parentNode = find(root, parent.toLowerCase());

        if (parentNode == null) return false;
    
        if (parentNode.yes != null || parentNode.no != null) return false;

        if (contains(yesChild) || contains(noChild)) return false;

        // crear hijos
        parentNode.yes = new Node(yesChild);
        parentNode.no = new Node(noChild);
        size += 2;

        return true;
    }    

    private Node find(Node node, String value) {
        if (node == null) {
            return null;
        }

        if (node.name.equals(value)) {
            return node;
        }

        Node left = find(node.yes, value);
        if (left != null) return left;
        
        return find(node.no, value);
    }
    
    public boolean delete(String nodeValue){
        if (nodeValue == null || root == null) return false;
        nodeValue = nodeValue.toLowerCase();

        // caso especial: si borran la raíz
        if (root.name.equals(nodeValue)){
            root = null;
            size = 0;
            return true;
        }
        return delete(root, nodeValue);
    }

    private boolean delete(Node parent, String value){
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

        return delete(parent.yes, value) || delete(parent.no, value);
    }

    private int countNodes(Node node){
        if (node == null) return 0;
        return 1 + countNodes(node.yes) + countNodes(node.no);
    }

    
    public DecisionTree eval(String [][] values) {
        if (root == null) return null;

        Node current = root;

        for (String[] qa : values) {
            if (current == null) break; // protección

            String question = qa[0].toLowerCase();
            String answer   = qa[1].toLowerCase();

            if (current.name.equals(question)) {
                if (answer.equals("yes") || answer.equals("true")) {
                    if (current.yes != null) {
                        current = current.yes;
                    } else {
                        break; // no hay rama yes
                    }
                } else if (answer.equals("no") || answer.equals("false")) {
                    if (current.no != null) {
                        current = current.no;
                    } else {
                        break; // no hay rama no
                    }
                }
            }
        }

        if (current == null) return null; 
        return new DecisionTree(current.name);
    }

    
    public boolean contains(String node){
        return find(root, node.toLowerCase()) != null;
    }
    
    public boolean isQuestion(String node){
        Node n = find(root, node.toLowerCase());
        if (n == null) {
            return false;
        }
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
        result.root = unionNodes(this.root, dt.root);
        return result;
    }

    private Node unionNodes(Node a, Node b){
        if (a == null) return copy(b);
        if (b == null) return copy(a);

        Node merged = new Node(a.name.equals(b.name) ? a.name : a.name + "|" + b.name);
        merged.yes = unionNodes(a.yes, b.yes);
        merged.no  = unionNodes(a.no, b.no);
        return merged;
    }
    
    private Node copy(Node n){
    if (n == null) return null;
    Node newNode = new Node(n.name);
    newNode.yes = copy(n.yes);
    newNode.no  = copy(n.no);
    return newNode;
    }

    
    public int nodes(){
        return size;
    }
    
   
    public int height(){
        return height(root);
    }    

    private int height(Node node) {
    if (node == null) return 0;
    return 1 + Math.max(height(node.yes), height(node.no));
    }
    
    
    public boolean equals(Object g){
        return equals((DecisionTree)g);
    }

    //Trees are inside parentesis. The names are in lowercase. The childs must always be in yes no order.
    //For example, (a yes (b yes (c) no (d)) no (e)) 
    public String toString() {
        return toString(root);
    }

    private String toString(Node node) {
        if (node == null) return "";

        if (node.yes == null && node.no == null) {
            return "(" + node.name + ")";
        }

        return "(" + node.name 
            + " yes " + toString(node.yes) 
            + " no " + toString(node.no) + ")";
    }
}
