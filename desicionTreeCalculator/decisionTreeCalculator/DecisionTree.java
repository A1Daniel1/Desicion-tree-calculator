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
    
    public boolean delete(String node){
        return true;
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
    
    public DecisionTree union (DecisionTree dt){
        return null;
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
