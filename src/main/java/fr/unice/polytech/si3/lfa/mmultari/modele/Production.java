package fr.unice.polytech.si3.lfa.mmultari.modele;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Maxime on 04/07/2014.
 * La classe Production est la classe qui définit une production utilisée dans une grammaire
 * Une production est définie par :
 * - un symbole non terminal pour désigner la production
 * - une ou plusieurs regles de production
 */
public class Production {
    private String nonTerm;
    private List<String> regles;

    /**
     * Le constructeur de la classe Production
     *
     * @param designation Le symbole non terminal pour désigner la production
     * @param lesRegles   Les regles de production (séparées par le caractere "|")
     */
    public Production(String designation, String lesRegles) {
        nonTerm = designation;
        String[] decoupe = lesRegles.split("\\|");
        regles = new ArrayList<String>(Arrays.asList(decoupe));
    }

    public Production(){
        nonTerm=new String();
        regles=new ArrayList<String>();
    }

    /**
     * Accesseur en écriture de regles
     *
     * @param newListRegle la nouvelle liste de regles de la production
     */
    public void setRegles(List newListRegle){
        this.regles=newListRegle;
    }

    /**
     * Méthode pour ajouter une regle à une production
     *
     * @param newRegle la regle à ajouter
     */
    public void addRegles(String newRegle) {
        this.regles.add(newRegle);
    }

    /**
     * Accesseur en écriture de nonTerm
     *
     * @param nonTerm le non terminal qui désigne la production
     */
    public void setNonTerm(String nonTerm) {
        this.nonTerm = nonTerm;
    }

    /**
     * Accesseur en lecture de nonTerm
     *
     * @return nonTerm le symbole non terminal qui designe la production
     */
    public String getNonTerm() {
        return this.nonTerm;
    }

    /**
     * Accesseur en lecture de regles
     *
     * @return regles la liste de regles de réécriture
     */
    public List<String> getRegles() {
        return this.regles;
    }

    /**
     * Fonction qui va permettre de mettre en forme les regles de production de la manière suivante :
     * "AB|a"
     * @param listeRegle la liste qui contient toutes les regles de production
     * @return la chaine de caracteres qui contient la nouvelle mise en forme des regles
     */
    private String printRegles(List listeRegle) {
        String s = new String();
        s += listeRegle.get(0);
        for (int i = 1; i < listeRegle.size(); i++) {
            s += ("|" + listeRegle.get(i));
        }
        return s;
    }

    /**
     * Fonction qui va transformer une production en un tableau de chaine de caractères
     * où la première case contient le non-terminal qui désigne la production
     * et la seconde la chaine de caractère contenant les regles sous la forme "AB|a"
     *
     * @return
     */
    public String[] toTab() {
        String[] leTab = new String[2];
        leTab[0] = nonTerm;
        leTab[1] = printRegles(regles);
        return leTab;
    }

    public String toString() {
        return nonTerm + "->" + printRegles(regles);
    }


}
