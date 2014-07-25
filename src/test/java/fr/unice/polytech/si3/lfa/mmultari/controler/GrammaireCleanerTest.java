package fr.unice.polytech.si3.lfa.mmultari.controler;

import fr.unice.polytech.si3.lfa.mmultari.modele.Grammaire;
import fr.unice.polytech.si3.lfa.mmultari.modele.Production;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created by Maxime on 19/07/2014.
 */
public class GrammaireCleanerTest {

    private Set<String> s1,s2,s3;
    private Grammaire g1;
    private GrammaireCleaner gc1;
    private List<String> l1;

    public GrammaireCleanerTest(){

    }

    @Before
    public void setUp() {
        s1=new HashSet<String>();
        s2=new HashSet<String>();
        s3=new HashSet<String>();
    }

    @After
    public void tearDown() {
        s1=null;
        s2=null;
        s3=null;
        g1=null;
        gc1=null;
        l1=null;
    }

    /**
     * Tests de comportement d'équivalences d'ensembles
     */
    @Test
    public void testEquiv(){
        s1.add("a");
        s2.add("a");
        s3.add("b");
        s2.add("a");

        assertEquals(s1,s2);
        assertNotEquals(s2,s3);
    }

    /**
     * Test listProd1
     */
    @Test
    public void testListProd1(){
        g1=new Grammaire();
        g1.initGram("test2.txt");//r et n sont remplis
        Production p=new Production("C","aB|b");
        gc1=new GrammaireCleaner(g1);
        assertEquals("C", gc1.listProdEtape1(p));

        assertTrue(g1.getN().contains("S"));
        assertTrue(g1.getN().contains("A"));
        assertTrue(g1.getN().contains("B"));
        assertTrue(g1.getN().contains("C"));
        assertTrue(g1.getN().contains("D"));
        assertTrue(g1.getN().contains("E"));
        assertFalse(g1.getN().contains("F"));

        Production p2=new Production("E","BA");
        assertEquals(null,gc1.listProdEtape1(p2));
    }

    /**
     * Test de nettoyage de grammaire
     */
    @Test
    public void testListInutiles(){
        g1=new Grammaire();
        g1.initGram2("test4.txt");
        gc1=new GrammaireCleaner(g1);
        s2=gc1.listAccessibles();
        assertTrue(s2.contains("S"));
        assertTrue(s2.contains("A"));
        assertFalse(s2.contains("B"));
        assertTrue(s2.contains("C"));
        gc1.nettoyGrammaire();
        s3=g1.getN();
        assertTrue(s2.contains("S"));
        assertTrue(s2.contains("A"));
        assertFalse(s2.contains("B"));
        assertTrue(s2.contains("C"));

    }

/**
 * Tests de la méthode epsilon_plus_prod
 */
    @Test
    public void testEpsilonPlusProd(){

        g1=new Grammaire();
        g1.initGram("test3.txt");

        s1.add("a");
        s1.add("b");
        s1.add("c");
        g1.setT(s1);
        g1.setAxiome("S");

        gc1=new GrammaireCleaner(g1);

        Production p1=new Production("S","cB|cS");
        Production p2=new Production("B","aBb|ε");
        assertTrue(gc1.epsilon_plus_prod(p2,s2));
        s2.add("B");
        assertFalse(gc1.epsilon_plus_prod(p2,s2));
        assertFalse(gc1.epsilon_plus_prod(p1,s3));
        assertFalse(gc1.epsilon_plus_prod(p1,s2));


    }

    /**
     * Test de la méthode calc_deriv_eps
     */

    @Test
    public void testCalc_deriv_eps(){
        g1=new Grammaire();
        g1.initGram("test3.txt");

        s1.add("a");
        s1.add("b");
        s1.add("c");
        g1.setT(s1);
        g1.setAxiome("S");

        gc1=new GrammaireCleaner(g1);
        s2.add("B");

        assertEquals("cB",g1.getR().get(0).getRegles().get(0));
        assertEquals(2,g1.getR().size());
        assertEquals(s2,gc1.calc_deriv_eps());


    }

    /**
     * Test de la methode supprimer_epsilon_prod
     */
    @Test
    public void testSupprimer_epsilon_prod(){
        g1=new Grammaire();
        g1.initGram("test3.txt");

        s1.add("a");
        s1.add("b");
        s1.add("c");
        g1.setT(s1);
        g1.setAxiome("S");

        gc1=new GrammaireCleaner(g1);
        s2.add("B");
        //System.out.println("Lancement de la méthode supprimer_epsilon_prod");
        gc1.supprimer_epsilon_prod();
       List<Production> lTest= g1.getR();
       assertEquals(3, lTest.get(0).getRegles().size());
        assertEquals(2,lTest.get(1).getRegles().size());
    }


}
