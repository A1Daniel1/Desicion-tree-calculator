import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class DecisionTreeCalculatorTest {

    private DecisionTreeCalculator calc;

    @Before
    public void setUp() {
        calc = new DecisionTreeCalculator();
    }

    /* ---------------- CICLO 1: BASICS ---------------- */

    @Test
    public void shouldCreateAndAssignTree() {
        calc.create("A");
        calc.assign("A", "Be hungry");
        assertEquals("(be hungry)", calc.toString("A"));
    }

    @Test
    public void shouldNotCrashOnUnknownVariable() {
        assertNull(calc.toString("X"));
    }

    @Test
    public void shouldReplaceTreeWhenReassigned() {
        calc.create("A");
        calc.assign("A", "Be hungry");
        calc.assign("A", "Have 25");
        assertEquals("(have 25)", calc.toString("A"));
    }

    /* ---------------- CICLO 2: UNARY OPS ---------------- */

    @Test
    public void shouldAddChildrenWithUnaryPlus() {
        calc.create("A");
        calc.assign("A", "Be hungry");

        String[][] params = { {"Be hungry", "Have 25", "Go to sleep"} };
        calc.assignUnary("A", "A", '+', params);

        assertEquals("(be hungry yes (have 25) no (go to sleep))", calc.toString("A"));
    }

    @Test
    public void shouldEvalTreeWithUnaryQuestion() {
        calc.create("A");
        calc.assign("A", "Be hungry");
        String[][] add1 = { {"Be hungry", "Have 25", "Go to sleep"} };
        calc.assignUnary("A", "A", '+', add1);
        String[][] add2 = { {"Have 25", "Go to restaurant", "Buy a hamburger"} };
        calc.assignUnary("A", "A", '+', add2);

        // eval: hungry = yes, have 25 = no → buy a hamburger
        String[][] answers = { {"Be hungry", "yes"}, {"Have 25", "no"} };
        calc.assignUnary("B", "A", '?', answers);

        assertEquals("(buy a hamburger)", calc.toString("B"));
    }

    @Test
    public void shouldDeleteNodeWithUnaryMinus() {
        calc.create("A");
        calc.assign("A", "Be hungry");
        String[][] add1 = { {"Be hungry", "Have 25", "Go to sleep"} };
        calc.assignUnary("A", "A", '+', add1);

        // delete "Have 25"
        String[][] del = { {"Have 25"} };
        calc.assignUnary("A", "A", '-', del);

        // queda solo "Be hungry" con un hijo NO
        assertTrue(calc.toString("A").contains("(go to sleep)"));
    }

    /* ---------------- CICLO 3: BINARY OPS ---------------- */

    @Test
    public void shouldUnionTwoTrees() {
        calc.create("A");
        calc.assign("A", "X");
        calc.create("B");
        calc.assign("B", "Y");

        calc.assignBinary("C", "A", 'u', "B");

        assertNotNull(calc.toString("C"));
    }

    @Test
    public void shouldIntersectTwoTrees() {
        calc.create("A");
        calc.assign("A", "X");
        calc.create("B");
        calc.assign("B", "X");

        calc.assignBinary("C", "A", 'i', "B");

        assertEquals("(x)", calc.toString("C"));
    }

    @Test
    public void shouldDifferenceTwoTrees() {
        calc.create("A");
        calc.assign("A", "X");
        calc.create("B");
        calc.assign("B", "Y");

        calc.assignBinary("C", "A", 'd', "B");

        assertEquals("(x)", calc.toString("C")); 
    }
}
