import java.util.HashMap;

/** DecisionTreeCalculator.java
 * 
 * @author ESCUELA 2025-02
 */
    
public class DecisionTreeCalculator{
    
    private HashMap<String,DecisionTree> variables;
    
    public DecisionTreeCalculator(){
        variables = new HashMap<>();
    }

    //Create a new variable
    public void create(String name){
        if (name != null && !variables.containsKey(name)) {
        variables.put(name, null);
    }
    }
     
    //Create a decision tree and assign to an existing variable
    //a := decisionTree
    public void assign(String a, String root){
        if (a != null && variables.containsKey(a)) {
            variables.put(a, new DecisionTree(root));
        }
    }    
    
    
    //Assigns the value of a unary operation to a variable
    // a = b op parameters
    //The operator characters are: '+' adding sons, '-' removing a node, '?' eval a decision tree
    //The parameters for '+' are [[parent, yesChild, noChild]]
    //The parameters for '-' are [[nodeName]]
    //The parameters for '?' are [[node1, val1], [node2, val2], ....]

    public void assignUnary(String a, String b, char op, String [][] parameters){
    DecisionTree base = variables.get(b);
    if (base == null) return;

    DecisionTree result = null;

    switch(op){
        case '+':
            if (parameters != null && parameters.length > 0 && parameters[0].length == 3){
                String parent = parameters[0][0];
                String yes = parameters[0][1];
                String no  = parameters[0][2];
                base.add(parent, yes, no);
            }
            result = base;
            break;

        case '-':
            if (parameters != null && parameters.length > 0){
                String nodeToDelete = parameters[0][0];
                base.delete(nodeToDelete);
            }
            result = base;
            break;

        case '?':
            result = base.eval(parameters);
            break;
    }

    if (result != null){
        variables.put(a, result);
    }
}

    
    //Assigns the value of a binary operation to a variable
    // a = b op c
    //The operator characters are:  'u' union, 'i' intersection, 'd' difference
    public void assignBinary(String a, String b, char op, String c){
    DecisionTree left  = variables.get(b);
    DecisionTree right = variables.get(c);
    if (left == null || right == null) return;

    DecisionTree result = null;

    switch(op){
        case 'u': // union
            result = left.union(right);
            break;
        case 'i': // intersection
            // (este lo defines después en DecisionTree)
            break;
        case 'd': // difference
            // (este lo defines después en DecisionTree)
            break;
    }

    if (result != null){
        variables.put(a, result);
    }
}

    
    //Returns the decisionTree in alphabetical order.
    public String toString(String decisionTree){
        DecisionTree dt = variables.get(decisionTree);
        if (dt != null) {
            return dt.toString();
        }
        return null;
    }
    
    
    //If the last operation was successfully completed
    public boolean ok(){
    return variables != null && !variables.isEmpty();
    }

}
    



