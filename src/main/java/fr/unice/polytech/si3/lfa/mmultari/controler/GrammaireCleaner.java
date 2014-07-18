package fr.unice.polytech.si3.lfa.mmultari.controler;

import fr.unice.polytech.si3.lfa.mmultari.modele.Grammaire;
import fr.unice.polytech.si3.lfa.mmultari.modele.Production;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Maxime on 17/07/2014.
 */
public class GrammaireCleaner {
    private Grammaire gOrig;
    private Map<String, Pattern> lesProds;
    private List<Production> lr;
    private Set<String> lt;
    private Set<String> ln;

    public GrammaireCleaner(Grammaire originale) {
        gOrig = originale;
        lesProds = new Hashtable<String, Pattern>();
        lr = gOrig.getR();
        lt = gOrig.getT();
        ln = gOrig.getN();

        String[] tab = new String[2];

        try {
            for (int i = 0; i < (gOrig.getR().size()); i++) {
                tab = gOrig.getR().get(i).toTab();
                lesProds.put(tab[0], Pattern.compile(tab[1]));
            }
        } catch (PatternSyntaxException pse) {

        }
    }

    /**
     * Cette méthode est chargée de supprimer les productions non productives
     * @return un set des productions productives
     */
    public Set<String> SuprProd() {
        Set<String> Ancien_N = new HashSet<String>();
        Set<String> Nouveau_N = new HashSet<String>();

        /* Etape 1 : On cherche les production contenant des regles sans non-terminaux*/
        for (Production p : lr) {
            String s = SuprProdEtape1(p);
            if (!(s.equals(null))) {
                Nouveau_N.add(s);
            }
        }

        /* Etape 2 :*/

        //TODO Compléter

        return Nouveau_N;
    }

    /**
     * Cette méthode est chargée de vérifier si les regles d'une production ne contiennent pas de non-terminaux
     *
     * @param prod la production à analyser
     * @return la désignation de la production si celle si possède au moins une regle sans non-terminaux, null sinon
     */
    public String SuprProdEtape1(Production prod) {
        //TODO tester
/* La liste contenant toutes les regles de la production :*/
        List<String> l = prod.getRegles();
/* Un tableau de taille le nombre de regles. Si une regle contient un non terminal,
 * la cases d'indice correspondante dans le tableau aura la valeur True
 */
        Boolean[] haveNonTerm = new Boolean[l.size()];
        Boolean containOnlyTerm = false;

        /* Initialisation du tableau à false */
        for (int cpt = 0; cpt < haveNonTerm.length; cpt++) {
            haveNonTerm[cpt] = false;

        }
       /* On va analyser toutes les regles de la production pour savoir si elles contiennent des non-terminaux */
        for (int i = 0; i < l.size(); i++) { //je parcours toutes les regles

            char[] tab = l.get(i).toCharArray(); // Je transforme la regle que j'analyse en tableau de caractere

            for (int j = 0; j < tab.length; j++) { //Je parcourt la regle afin de voir si elle possede un non terminal

                if (ln.contains(tab[j])) {//ln contient la liste des non terminaux
                    haveNonTerm[i] = true;// Si une regle contient un nom terminal, je l'indique de le tableau haveNonTerm
                }
            }
        }

/* On va maintenant parcourir le tableau haveNonTerm pour savoir si une regle au moins ne possede pas de nonTerminaux,
* autrement dis si une case du tableau est à false
*/
        for (int k = 0; k < haveNonTerm.length; k++) {
            if (haveNonTerm[k] == false) {
                containOnlyTerm = true;
            }

        }

        /* Si aucune regle n'est exempt de non terminaux on retourne null*/
        if (containOnlyTerm) {
            return prod.getNonTerm();
        } else {
            return null;
        }
    }

}
