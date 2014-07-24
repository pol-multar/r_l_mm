package fr.unice.polytech.si3.lfa.mmultari.controler;

import fr.unice.polytech.si3.lfa.mmultari.modele.Grammaire;
import fr.unice.polytech.si3.lfa.mmultari.modele.Production;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author Maxime on 24/07/2014.
 */
public class ChomskyGrammaireTest {
    private Grammaire g1;
    private ChomskyGrammaire chomskyGrammaire;
    private Set<String> s;

    public ChomskyGrammaireTest() {

    }

    @Before
    public void setUp() {
        g1 = new Grammaire();
        s = new HashSet<>();

    }

    @After
    public void tearDown() {
        g1 = null;

    }

    @Test
    public void testModifTerm() {
        g1.initGram("testsFN.txt");
        s.add("+");
        s.add("*");
        s.add("(");
        s.add(")");
        s.add("a");
        g1.setT(s);
        g1.setAxiome("E");
        chomskyGrammaire = new ChomskyGrammaire(g1);
        Hashtable<String, String> ht = new Hashtable<>();
        Hashtable<String, String> ht2 = new Hashtable<>();
        Set<String> s1 = g1.getT();
        Production production = g1.getR().get(0);
        System.out.print("Production avant modif : ");
        System.out.println(production);
        chomskyGrammaire.modifTerm(production, ht, s1);
        System.out.print("Production apres modif : ");
        System.out.println(production);
        assertNotEquals(ht2, ht);


    }

    @Test
    public void testMiseEnForme(){
        g1.initGram("testsFN.txt");
        s.add("+");
        s.add("*");
        s.add("(");
        s.add(")");
        s.add("a");
        g1.setT(s);
        g1.setAxiome("E");
        chomskyGrammaire = new ChomskyGrammaire(g1);
        chomskyGrammaire.miseEnFormeChomsky();
    }
}
