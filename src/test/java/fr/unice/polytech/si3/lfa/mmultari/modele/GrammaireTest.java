package fr.unice.polytech.si3.lfa.mmultari.modele;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Maxime on 08/07/2014.
 */
public class GrammaireTest {
    private Grammaire g1;
    private String s1;
    private String returnTest;

    public GrammaireTest(){

    }

    @Before
    public void setUp() {
        s1 = new String();
        returnTest = new String();
        g1= new Grammaire();
    }

    @After
    public void tearDown() {
        s1 = null;
        returnTest=null;
        g1 = null;
    }

    @Test
    public void testLecture(){
        s1="toto";
        returnTest=g1.lectureProd("toto.txt");
        assertEquals(s1,returnTest);
        assertNotEquals(s1,g1.lectureProd("test.txt"));
    }
}
