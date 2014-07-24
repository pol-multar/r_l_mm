package fr.unice.polytech.si3.lfa.mmultari.controler;

import fr.unice.polytech.si3.lfa.mmultari.modele.Grammaire;
import fr.unice.polytech.si3.lfa.mmultari.modele.Production;

import java.util.Hashtable;
import java.util.List;
import java.util.Set;

/**
 * Cette classe permet, à partir d'une grammaire sans renommage, ni epsilon-production
 * de mettre celle-ci sous Forme Normale de Chomsky.
 *
 * @author Maxime on 24/07/2014.
 */
public class ChomskyGrammaire {
    private Grammaire laGrammaire;
    private boolean debug = false;

    public ChomskyGrammaire(Grammaire uneGrammaire) {
        this.laGrammaire = uneGrammaire;
    }

    public void miseEnFormeChomsky() {
        List<Production> listProd = laGrammaire.getR();
        Set<String> listTerm = laGrammaire.getT();
        Hashtable<String, String> renTerm = new Hashtable<>();//Contiendra les equivalences de "renommage" des terminaux


        /**
         * Etape 1 : on modifie les regles de maniere à ce qu'elles soient de la forme :
         *
         * X -> A1A2A3...An avec An appartenant à l'ensemble des non Terminaux
         * X -> a           avec a appartenant à l'ensemble des terminaux
         */

        for (Production production : listProd) {
            modifTerm(production, renTerm, listTerm);
        }
    }

    private void modifTerm(Production production, Hashtable<String, String> renTerm, Set<String> listTerm) {
        List<String> lesRegles = production.getRegles();

        for (int i=0;i<lesRegles.size();i++) {//Parcours des regles
            String regle=lesRegles.get(i);
            char[] tab = regle.toCharArray();
            if (tab.length > 1) {//Si la regle n'est pas composée d'un seul terminal (la grammaire etant nettoyée)
                for (int j = 0; j < tab.length; j++) {//Parcours de la regle
                    if (listTerm.contains(tab[j])) {//Si je lis un terminal
                        /* Je vérifie qu'il n'a pas déjà été renommé */
                        if (renTerm.containsKey(Character.toString(tab[j]))) {//C'est le cas, on remplace
                            String s =renTerm.get(Character.toString(tab[j]));
                            tab[j]=s.charAt(0);
                        }else {//Ce n'est pas le cas, on renomme le terminal, on remplace.
                            String newKey=laGrammaire.addNewNonTerm();
                            renTerm.put(newKey,Character.toString(tab[j]));
                            tab[j]=newKey.charAt(0);
                        }
                    }

                }
                /* On n'oublie pas de modifier la regle dans la liste */
               lesRegles.set(i ,new String(tab));


            }

        }
/* On met à jour les regles */
        production.setRegles(lesRegles);

    }


}
