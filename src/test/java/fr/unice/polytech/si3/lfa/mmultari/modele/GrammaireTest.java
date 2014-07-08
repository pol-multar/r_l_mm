package fr.unice.polytech.si3.lfa.mmultari.modele;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
        s1="toto";
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
        returnTest=g1.lectureProd("test.txt");
        assertEquals(s1,returnTest);
    }
}
