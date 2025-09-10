import java.util.HashMap;

/** DecisionTreeCalculator.java
 * 
 * @author ESCUELA 2025-02
 */
    
public class DecisionTreeCalculator{
    
    private HashMap<String,DecisionTree> variables;
    
    public DecisionTreeCalculator(){
    }

    //Create a new variable
    public void create(String name){
    }
     
    //Create a decision tree and assign to an existing variable
    //a := decisionTree
    public void assign(String a, String root){
    }    
    
    
    //Assigns the value of a unary operation to a variable
    // a = b op parameters
    //The operator characters are: '+' adding sons, '-' removing a node, '?' eval a decision tree
    //The parameters for '+' are [[parent, yesChild, noChild]]
    //The parameters for '-' are [[nodeName]]
    //The parameters for '?' are [[node1, val1], [node2, val2], ....]

    public void assignUnary(String a, String b, char op, String [][] parameters){
    }
      
    
    //Assigns the value of a binary operation to a variable
    // a = b op c
    //The operator characters are:  'u' union, 'i' intersection, 'd' difference
    public void assignBinary(String a, String b, char op, String c){
    }
  
    
    //Returns the decisionTree in alphabetical order.
    public String toString(String decisionTree){
        return null;
    }
    
    
    //If the last operation was successfully completed
    public boolean ok(){
        return false;
    }
}
    



