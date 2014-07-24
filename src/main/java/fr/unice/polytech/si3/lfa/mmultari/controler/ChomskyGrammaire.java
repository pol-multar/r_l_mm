package fr.unice.polytech.si3.lfa.mmultari.controler;

import fr.unice.polytech.si3.lfa.mmultari.modele.Grammaire;
import fr.unice.polytech.si3.lfa.mmultari.modele.Production;

import java.util.ArrayList;
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

    public void setDebug(boolean yesOrNo){
        this.debug=yesOrNo;
    }

    public void miseEnFormeChomsky() {
        List<Production> listProd = laGrammaire.getR();
        List<Production> listProdTerm = new ArrayList<>();//Contient la liste des nouvelles productions à la fin de la premiere étape
        List<Production> listProdNonTerm = new ArrayList<>();//Contient la liste des nouvelles productions à la fin de la deuxième étape
        Set<String> listTerm = laGrammaire.getT();
        Hashtable<String, String> renTerm = new Hashtable<>();//Contiendra les equivalences de "renommage" des terminaux
        Hashtable<String, String> renNonTerm = new Hashtable<>();//Contiendra les equivalences de "renommage" des nonterminaux


        /**
         * Etape 1 : on modifie les regles de maniere à ce qu'elles soient de la forme :
         *
         * X -> A1A2A3...An avec An appartenant à l'ensemble des non Terminaux
         * X -> a           avec a appartenant à l'ensemble des terminaux
         */
        if (debug) {
            System.out.println("miseEnFormeChomsky: Avant suppression des terminaux :");
            for (Production p : listProd) {
                System.out.println(p);
            }
        }

        for (Production production : listProd) {
            List<Production> list = modifTerm(production, renTerm, listTerm);
            listProdTerm.addAll(list);
        }

        if (debug) {
            System.out.println("miseEnFormeChomsky: Après suppression des terminaux :");

            for (Production p : listProd) {
                System.out.println(p);
            }
        }
        /**
         * Etape 2 : on introduit de nouvelles variables de maniere à mettre toutes les regles qu'ils nous reste sous la forme
         * X -> AB avec A,B appartenant à l'ensemble des non terminaux
         */
        List<Production> oldListProd;
        do {
            oldListProd = listProd;

            for (Production production : listProd) {
                List<Production> list = modifNonTerm(production, renNonTerm, listTerm);
                listProdNonTerm.addAll(list);
            }

            listProd.addAll(listProdNonTerm);

        } while (!listProd.equals(oldListProd));

        if (debug) {
            System.out.println("miseEnFormeChomsky: Après mise en place des regles de taille 2 ou moins :");

            for (Production production : listProd) {
                System.out.println(production);
            }
        }
        /**
         * On ajoute les regles contenant les terminaux de la première étape
         */

        listProd.addAll(listProdTerm);

        if (debug) {
            System.out.println("miseEnFormeChomsky: Après ajout des regles contenant des terminaux :");
            for (Production production : listProd) {
                System.out.println(production);
            }
        }
        laGrammaire.setR(listProd);

    }


    /**
     * Méthode chargée de supprimer les terminaux mélangés à des non terminaux dans les regles d'une production
     *
     * @param production la production dont on souhaite modifier les regles
     * @param renTerm    la hashtable contenant le renoomage des terminaux
     * @param listTerm   la liste des symboles terminaux de la grammaire
     * @return la liste des nouvelles productions créées
     */
    public List<Production> modifTerm(Production production, Hashtable<String, String> renTerm, Set<String> listTerm) {

        List<String> lesRegles = production.getRegles();
        List<Production> lesNouvellesProd = new ArrayList<Production>();

        for (int i = 0; i < lesRegles.size(); i++) {//Parcours des regles
            String regle = lesRegles.get(i);

            char[] tab = regle.toCharArray();
            if (tab.length > 1) {//Si la regle n'est pas composée d'un seul terminal (la grammaire etant nettoyée)

                for (int j = 0; j < tab.length; j++) {//Parcours de la regle
                    if (listTerm.contains(Character.toString(tab[j]))) {//Si je lis un terminal

                        /* Je vérifie qu'il n'a pas déjà été renommé */
                        if (renTerm.containsKey(Character.toString(tab[j]))) {//C'est le cas, on remplace
                            String s = renTerm.get(Character.toString(tab[j]));
                            tab[j] = s.charAt(0);
                        } else {//Ce n'est pas le cas, on renomme le terminal, on remplace et on crée la nouvelle production.
                            String newName = laGrammaire.addNewNonTerm();
                            renTerm.put(Character.toString(tab[j]), newName);
                            Production newProd = new Production(newName, Character.toString(tab[j]));
                            lesNouvellesProd.add(newProd);
                            if(debug) {
                                System.out.println("Nouvelle production ajoutée :" + newProd);
                            }
                            tab[j] = newName.charAt(0);
                        }
                    }
                }
                /* On n'oublie pas de modifier la regle dans la liste */
                lesRegles.set(i, new String(tab));
            }
        }
        /* On met à jour les regles */
        production.setRegles(lesRegles);

        return lesNouvellesProd;

    }

    /**
     * Méthode chargée de mettre chaque regle d'une production sous la forme X -> AB avec A,B apaprtenant à N
     *
     * @param production la production dont on souhaite modifier les regles
     * @param renNonTerm la table d'équivalence des nouvelles productions
     * @param listTerm   la liste des symboles terminaux de la grammaire
     */
    private List<Production> modifNonTerm(Production production, Hashtable<String, String> renNonTerm, Set<String> listTerm) {

        List<String> lesRegles = production.getRegles();
        List<String> oldRegles = new ArrayList<>();

        List<Production> newProds = new ArrayList<>();

        for (int i = 0; i < lesRegles.size(); i++) {
            String regle = lesRegles.get(i);


            if (regle.length() > 2) {// Dans ce cas on va devoir encore découper
                String s1 = regle.substring(0, 1);//On garde la premiere partie
                String newRegle = regle.substring(1);

                /* on vérifie qu'une clé avec la même chaine n'existe pas ... */
                if (renNonTerm.containsKey(newRegle)) {//Si c'est le cas on récupère la correspondance
                    String s2 = renNonTerm.get(newRegle);
                    regle = s1 + s2;
                } else {//Sinon on la crée
                    String newName = laGrammaire.addNewNonTerm();
                    renNonTerm.put(newRegle, newName);
                    newProds.add(new Production(newName, newRegle));
                    regle = s1 + newName;
                }
                lesRegles.set(i, regle);
            }
        }
        /* On met à jour les regles */
        production.setRegles(lesRegles);

        return newProds;
    }


}
