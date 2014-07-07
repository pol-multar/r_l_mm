package fr.unice.polytech.si3.lfa.mmultari.modele;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Maxime on 04/07/2014.
 * La classe Production est la classe qui définit une production utilisée dans une grammaire
 * Une production est définie par :
 *  - un symbole non terminal pour désigner la production
 *  - une ou plusieurs regles de production
 */
public class Production {
    private String nonTerm;
    private List regles;

    /**
     * Le constructeur de la classe Production
     * @param designation Le symbole non terminal pour désigner la production
     * @param lesRegles Les regles de production (séparées par le caractere "|")
     */
    public Production(String designation, String lesRegles){
        nonTerm=designation;
        String[] decoupe = lesRegles.split("\\|");
        regles=new ArrayList(Arrays.asList(decoupe));
    }

    /**
     * Le constructeurs sans paramètres de la classe Production
     */
    public Production() {
        nonTerm=null;
        regles=new ArrayList();
    }


    /**
     * Accesseur en écriture regles
     * @param newRegle la regle à ajouter
     */
    public void setRegles(String newRegle){
        this.regles.add(newRegle);
    }

    /**
     * Accesseur en écriture de nonTerm
     * @param nonTerm le non terminal qui désigne la production
     */
   public void setNonTerm (String nonTerm){
       this.nonTerm=nonTerm;
   }

    /**
     * Accesseur en lecture de nonTerm
     * @return nonTerm le symbole non terminal qui designe la production
     */
    public String getNonTerm (){
        return this.nonTerm;
    }

    /**
     * Accesseur en lecture de regles
     * @return regles la liste de regles de réécriture
     */
    public List getRegles(){
        return this.regles;
    }

    private String printRegles(List listeRegle){
        String s = new String();
        s+=listeRegle.get(0);
        for(int i=1;i<listeRegle.size();i++){
            s+=("|"+listeRegle.get(i));
            //s+=listeRegle.get(i);
        }
        return s;
    }

    public String toString(){
        return nonTerm + "->" + printRegles(regles);
    }



}
