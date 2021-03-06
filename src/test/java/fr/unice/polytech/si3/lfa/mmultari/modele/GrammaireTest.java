package fr.unice.polytech.si3.lfa.mmultari.modele;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Maxime on 08/07/2014.
 */
public class GrammaireTest {
    private Grammaire g1;
    private String s1;
    private String returnTest;


    public GrammaireTest() {

    }

    @Before
    public void setUp() {
        s1 = new String();
        returnTest = new String();
        g1 = new Grammaire();
    }

    @After
    public void tearDown() {
        s1 = null;
        returnTest = null;
        g1 = null;
    }

    /**
     * Test de la méthode de lecture de fichier
     */
    @Test
    public void testLecture() {
        s1 = "toto";
        returnTest = g1.lectureProd("toto.txt");
        assertEquals(s1, returnTest);
        assertNotEquals(s1, g1.lectureProd("test.txt"));
    }

    /**
     * Test de la méthode d'ajout de production
     */
    @Test
    public void testAddAllProd() {
        String[] t1 = {"S:abY|c|aX", "X:a|c"};
        g1.addAllProd(t1);
        List<Production> rTest = g1.getR();
        Set nTest = g1.getN();
        //Verification des non terminaux
        assertEquals(2, nTest.size());
        assertTrue(nTest.contains("S"));
        assertTrue(nTest.contains("X"));
        assertFalse(nTest.contains("Y"));
        assertFalse(nTest.contains("x"));
        //Verification des Productions
        assertEquals(2,rTest.size());
        Production p1 = new Production("S","abY|c|aX");
        Production p2 =rTest.get(0);
        assertEquals(p1.getNonTerm(),p2.getNonTerm());
        assertEquals(p1.getRegles(),p2.getRegles());
    }

    /**
     * Test de addNewNonTerm
     */
    @Test
    public void testAddNewNonTerm(){
        g1.initGram("test.txt");
        s1=g1.addNewNonTerm();
        assertEquals("A",s1);

        Grammaire g2=new Grammaire();
        g2.initGram("testsRU.txt");
        Set<String> lesN=g2.getN();
        assertTrue(lesN.contains("S"));
        String s2=g2.addNewNonTerm();
        assertEquals("D",s2);
    }

    /**
     * Test de l'initialisation avec les terminaux
     */
    public void testInitAvecTerm(){
        g1.initGram2("testAll.txt");
        Set<String> set1=g1.getT();
        assertEquals(5,set1.size());
        List<Production> l1=g1.getR();
        assertEquals(3,l1.size());
    }
}
