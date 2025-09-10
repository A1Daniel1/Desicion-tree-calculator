public class DecisionTree {
    
    
    public DecisionTree(String root){
    }
    
    public boolean add(String parent, String yesChild, String noChild){
        return true;
    }    
    
    public boolean delete(String node){
        return true;
    }
    
    public DecisionTree eval(String [][] values){
        return null;
    }
    
    public boolean contains(String node){
        return false;
    }
    
    public boolean isQuestion(String node){
        return false;
    }

    public boolean isDecision(String node){
        return false;
    }
    
    public DecisionTree union (DecisionTree dt){
        return null;
    }

    
    public int nodes(){
        return 0;
    }
    
   
    public int height(){
        return 0;
    }    
    
    
    public boolean equals(DecisionTree dt){
        return false;
    }
    
    public boolean equals(Object g){
        return equals((DecisionTree)g);
    }
    
    //Trees are inside parentesis. The names are in lowercase. The childs must always be in yes no order.
    //For example, (a yes (b yes (c) no (d)) no (e)) 
    public String toString() {
      return "";
    }
}
