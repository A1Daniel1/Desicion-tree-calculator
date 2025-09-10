import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class DecisionTreeTest{

    
    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp(){
    }

    
     @Test
    public void shouldCreateSmallestDecisionTree(){
        DecisionTree dt=new DecisionTree("Be hungry");
        assertEquals(1, dt.nodes());
        assertEquals(1, dt.height());      
    }    
   
    @Test
    public void shouldCreateOtherDecisionTree(){
        DecisionTree dt=new DecisionTree("Be hungry");
        assertTrue(dt.add("Be hungry","Have 25", "Go to sleep"));
        assertTrue(dt.add("Have 25", "Go to restaurant", "Buy a hamburger"));
        assertEquals(5, dt.nodes());
        assertEquals(3, dt.height());
    }    
    
    @Test
    public void shoulDifferentiateQuestionsDecisions(){
        DecisionTree dt=new DecisionTree("Be hungry");
        dt.add("Be hungry","Have 25", "Go to sleep");
        dt.add("Have 25", "Go to restaurant", "Buy a hamburger");
        assertTrue(dt.isDecision("Buy a hamburger"));        
        assertFalse(dt.isQuestion("Buy a hamburger"));
        assertTrue(dt.isQuestion("Have 25")); 
        assertFalse(dt.isDecision("Have 25"));    
    }    
    @Test
    public void shouldNotHaveDuplicateNodes(){
        DecisionTree dt=new DecisionTree("Be hungry");
        assertTrue(dt.add("Be hungry","Have 25", "Go to sleep"));
        assertFalse(dt.add("Have 25","Be hungry", "Go to shop"));
        assertEquals(3, dt.nodes());
        assertEquals(2, dt.height());
    }    

    @Test
    public void shouldNotHaveMoreThanTwoChildrens(){
        DecisionTree dt=new DecisionTree("Be hungry");
        assertTrue(dt.add("Be hungry","Have 25", "Go to sleep"));
        assertFalse(dt.add("Be hungry","Be to restaurant", "Go to shop"));
        assertEquals(3, dt.nodes());
        assertEquals(2, dt.height());
    }    
    
    @Test
    public void shouldNotBeCaseSensitive(){     
        DecisionTree dt=new DecisionTree("Be Hungry");
        assertTrue(dt.add("Be hungry","HAVE 25", "Go to sleep"));
        assertTrue(dt.add("Have 25", "Go to restaurant", "BUY a hamburger"));
        assertEquals(5, dt.nodes());
        assertEquals(3, dt.height());
    }
    
    @Test
    public void shouldConvertToString(){
        DecisionTree dt=new DecisionTree("Be hungry");
        assertTrue(dt.add("Be hungry","Have 25", "Go to sleep"));
        assertTrue(dt.add("Have 25", "Go to restaurant", "Buy a hamburger"));
        String data="(be hungry yes (have 25 yes (go to restaurant) no (buy a hamburger)) no (go to sleep))";
        assertEquals(data, dt.toString());
    }

    @Test
    public void shouldValityEquality(){
        DecisionTree dt=new DecisionTree("Be hungry");
        assertTrue(dt.add("Be hungry","Have 25", "Go to sleep"));
        assertTrue(dt.add("Have 25", "Go to restaurant", "Buy a hamburger"));
        DecisionTree dto=new DecisionTree("Be Hungry");
        assertTrue(dto.add("Be hungry","HAVE 25", "Go to sleep"));
        assertTrue(dto.add("Have 25", "Go to restaurant", "Buy a hamburger"));
        assertEquals(dt,dto);
    }    


    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
}
