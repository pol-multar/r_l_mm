package fr.unice.polytech.si3.lfa.mmultari.controler;

import fr.unice.polytech.si3.lfa.mmultari.modele.Grammaire;
import fr.unice.polytech.si3.lfa.mmultari.modele.Production;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
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
    public void testSupprInutiles(){
        g1=new Grammaire();
        g1.initGram("test2.txt");//r et n sont remplis

        s1.add("a");
        s1.add("b");
        g1.setT(s1);//t est remplie
        g1.setAxiome("S");//axiome est remplie



        gc1=new GrammaireCleaner(g1);

        //gc1.nettoyGrammaire();


    }
}
