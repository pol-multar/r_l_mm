package fr.unice.polytech.si3.lfa.mmultari.controler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by Maxime on 19/07/2014.
 */
public class GrammaireCleanerTest {

    private Set<String> s1,s2,s3;

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


}
