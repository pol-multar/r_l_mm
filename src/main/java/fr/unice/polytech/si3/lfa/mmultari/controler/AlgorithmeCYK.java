package fr.unice.polytech.si3.lfa.mmultari.controler;

import fr.unice.polytech.si3.lfa.mmultari.modele.Grammaire;

import java.util.Set;

/**
 * @author Maxime on 24/07/2014.
 */
public class AlgorithmeCYK {

    private Grammaire laGrammaire;
    private String leMot;
    private int n; /* La longueur du mot */
    private String[][] CYKtab; /* Le tableau de l'algorithme */

    public AlgorithmeCYK(Grammaire uneGrammaire, String unMot) {
        this.laGrammaire = uneGrammaire;
        this.leMot = unMot;
        n = unMot.length();
        CYKtab = new String[n][n];

    }

    private void etape1(String[][] CYKtab, String leMot) {
        char[] tab = leMot.toCharArray();

        for (int i = 0; i < tab.length; i++) {
            CYKtab[1][i] = estDansLaProd(Character.toString(tab[i]));
        }
    }

    private String estDansLaProd(String s) {
        Set<String> listeTerm = laGrammaire.getT();
        /*On vérifie d'abord que la lettre fait bien partie des terminaux */
        if (listeTerm.contains(s)) {
        /* On peut chercher quelle est la regle qui l'admet */


        }
        return null;
    }


}
