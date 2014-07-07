package fr.unice.polytech.si3.lfa.mmultari.modele;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Maxime on 07/07/2014.
 */
public class ProductionTest {
    private Production p1;
    private String s1;
    private String s2;
    private List r1;


    public ProductionTest() {

    }

    @Before
    public void setUp() {
        s1 = "S";
        s2 = "a*b|c|d|ef";
        p1 = new Production(s1, s2);
    }

    @After
    public void tearDown() {
        s1 = null;
        s2 = null;
        p1 = null;
    }

    @Test
    public void testAffichage() {
        assertEquals("S->a*b|c|d|ef", p1.toString());
    }

    @Test
    public void testDecoupage(){
        r1=p1.getRegles();
        assertEquals("a*b",r1.get(0));
        assertEquals("d",r1.get(2));
    }
}
