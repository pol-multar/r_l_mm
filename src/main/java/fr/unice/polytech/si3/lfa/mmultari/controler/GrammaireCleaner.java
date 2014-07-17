package fr.unice.polytech.si3.lfa.mmultari.controler;

import fr.unice.polytech.si3.lfa.mmultari.modele.Grammaire;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Maxime on 17/07/2014.
 */
public class GrammaireCleaner {
    private Grammaire gOrig;
    private Map<String, Pattern> lesProds;

    public GrammaireCleaner(Grammaire originale) {
        gOrig = originale;
        lesProds = new Hashtable<String, Pattern>();

        String[] tab = new String[2];

        try {
            for (int i = 0; i < (gOrig.getR().size()); i++) {
                tab = gOrig.getR().get(i).toTab();
                lesProds.put(tab[0], Pattern.compile(tab[1]));
            }
        } catch (PatternSyntaxException pse) {

        }
    }

    public List SuprProd() {
        List Ancien_N = new ArrayList();
        List Nouveau_N = new ArrayList();


        return Nouveau_N;
    }

}
