import java.util.HashMap;

public class DecisionTreeCalculator{
    
    private HashMap<String,DecisionTree> variables;
    private boolean lastOperationOk;
    
    public DecisionTreeCalculator(){
        variables = new HashMap<>();
        lastOperationOk = true;
    }

    public void create(String name){
        if (name != null) {
            variables.put(name, null);
            lastOperationOk = true;
        } else {
            lastOperationOk = false;
        }
    }
     
    public void assign(String a, String root){
        if (a != null && root != null && variables.containsKey(a)) {
            variables.put(a, new DecisionTree(root));
            lastOperationOk = true;
        } else {
            lastOperationOk = false;
        }
    }    
    
    public void assignUnary(String a, String b, char op, String[][] parameters){
        DecisionTree base = variables.get(b);
        if (base == null || a == null) {
            lastOperationOk = false;
            return;
        }

        DecisionTree result = null;

        switch(op){
            case '+':
                if (parameters != null && parameters.length > 0 && parameters[0].length == 3){
                    String parent = parameters[0][0];
                    String yes = parameters[0][1];
                    String no = parameters[0][2];
                    boolean success = base.add(parent, yes, no);
                    if (success) {
                        result = base;
                        lastOperationOk = true;
                    } else {
                        lastOperationOk = false;
                    }
                } else {
                    lastOperationOk = false;
                }
                break;

            case '-':
                if (parameters != null && parameters.length > 0 && parameters[0].length >= 1){
                    String nodeToDelete = parameters[0][0];
                    boolean success = base.delete(nodeToDelete);
                    if (success) {
                        result = base;
                        lastOperationOk = true;
                    } else {
                        lastOperationOk = false;
                    }
                } else {
                    lastOperationOk = false;
                }
                break;

            case '?':
                result = base.eval(parameters);
                lastOperationOk = (result != null);
                break;
                
            default:
                lastOperationOk = false;
        }

        if (result != null && lastOperationOk){
            variables.put(a, result);
        }
    }


    public void assignBinary(String a, String b, char op, String c){
        DecisionTree left = variables.get(b);
        DecisionTree right = variables.get(c);
        
        if (left == null || right == null || a == null) {
            lastOperationOk = false;
            return;
        }

        DecisionTree result = null;

        switch(op){
            case 'u':
                result = left.union(right);
                lastOperationOk = true;
                break;
                
            case 'i':
                result = intersection(left, right);
                lastOperationOk = (result != null);
                break;
                
            case 'd':
                result = difference(left, right);
                lastOperationOk = (result != null);
                break;
                
            default:
                lastOperationOk = false;
        }

        if (result != null && lastOperationOk){
            variables.put(a, result);
        }
    }
    
    private DecisionTree intersection(DecisionTree a, DecisionTree b){
        if (a == null || b == null) return null;
        
        String aStr = a.toString();
        String bStr = b.toString();
        
        if (aStr.equals(bStr)) {
            return new DecisionTree(a.toString().replaceAll("[()]", "").split(" ")[0]);
        }
        
        String aRoot = extractRoot(aStr);
        String bRoot = extractRoot(bStr);
        
        if (aRoot != null && aRoot.equals(bRoot)) {
            return new DecisionTree(aRoot);
        }
        
        return null;
    }
    
    private DecisionTree difference(DecisionTree a, DecisionTree b){
        if (a == null) return null;
        if (b == null) return a;
        
        String aStr = a.toString();
        String bStr = b.toString();
        
        
        if (aStr.equals(bStr)) return null;
        
        
        String aRoot = extractRoot(aStr);
        if (aRoot != null) {
            return new DecisionTree(aRoot);
        }
        
        return a;
    }
    
    private String extractRoot(String treeStr){
        if (treeStr == null || !treeStr.startsWith("(")) return null;
        
        int firstSpace = treeStr.indexOf(" ");
        int firstClose = treeStr.indexOf(")");
        
        if (firstSpace == -1) {
            // Solo raíz: (root)
            return treeStr.substring(1, firstClose);
        } else {
            // Árbol complejo: (root yes ...)
            return treeStr.substring(1, firstSpace);
        }
    }
    
    /**
     * Returns string representation of a decision tree variable
     * @param decisionTree variable name
     * @return tree string or null if variable doesn't exist
     */
    public String toString(String decisionTree){
        DecisionTree dt = variables.get(decisionTree);
        return (dt != null) ? dt.toString() : null;
    }
    
    /**
     * Checks if the last operation was successful
     * @return true if last operation succeeded, false otherwise
     */
    public boolean ok(){
        return lastOperationOk;
    }
}