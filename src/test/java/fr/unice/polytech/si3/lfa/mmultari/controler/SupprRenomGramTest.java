package fr.unice.polytech.si3.lfa.mmultari.controler;

import fr.unice.polytech.si3.lfa.mmultari.modele.Grammaire;
import fr.unice.polytech.si3.lfa.mmultari.modele.Production;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Maxime on 23/07/2014.
 */
public class SupprRenomGramTest {
    private Grammaire g1;
    private SupprRenomGram supprRenomGram1;
    private Set<String> s1,s2,s3;

    public SupprRenomGramTest(){

    }

    @Before
    public void setUp() {
        s1=new HashSet<String>();
        s2=new HashSet<String>();
        s3=new HashSet<String>();
        g1=new Grammaire();
    }

    @After
    public void tearDown() {

    }

    /**
     * Test des méthodes calc_Renommages_1Prod et donc avec de rechercheUnitaire
     */

    @Test
    public void testCreatEnsRen(){

        g1.initGram("testsRU.txt");

        s1.add("a");
        s1.add("b");
        s1.add("c");
        g1.setT(s1);
        g1.setAxiome("S");

        supprRenomGram1=new SupprRenomGram(g1);
        List<Production> lTest=g1.getR();
        assertEquals(4, lTest.size());
        //System.out.println("Test avec la production : "+lTest.get(0).getNonTerm());
        s2=supprRenomGram1.calc_Renommages_1Prod(lTest.get(0));
        assertEquals(4,s2.size());
        assertTrue(s2.contains("S"));
        assertTrue(s2.contains("B"));
        assertTrue(s2.contains("C"));

    }

    /**
     * Test de calc_ens_ren
     */
    @Test
    public void testCalcEnsRen(){

        g1.initGram("testsRU.txt");

        s1.add("a");
        s1.add("b");
        s1.add("c");
        g1.setT(s1);
        g1.setAxiome("S");

        supprRenomGram1=new SupprRenomGram(g1);
        List<Production> lTest=g1.getR();
        assertEquals(4,lTest.size());

        Hashtable<String, Set<String>> htest =supprRenomGram1.calc_ens_ren();
        assertEquals(4,htest.size());
        s3.add("S");
        s3.add("A");
        s3.add("B");
        s3.add("C");
        assertEquals(s3,htest.get("S"));
        s2.add("A");
        s2.add("B");
        s2.add("C");
        assertEquals(s2,htest.get("B"));
        assertNotEquals(htest.get("S"),htest.get("A"));
        assertNotEquals(htest.get("C"),htest.get("B"));
    }

    /**
     * Test de la méthode supprRenom
     */
    @Test
    public void testSupprRenom(){

        g1.initGram("testsRU.txt");

        s1.add("a");
        s1.add("b");
        s1.add("c");
        g1.setT(s1);
        g1.setAxiome("S");
        assertTrue(g1.getN().contains("S"));
        assertTrue(g1.getN().contains("A"));
        assertTrue(g1.getN().contains("B"));
        assertTrue(g1.getN().contains("C"));
        assertEquals("B",g1.getR().get(0).getRegles().get(3));
        supprRenomGram1=new SupprRenomGram(g1);

        supprRenomGram1.supprRenom();

    }
}
