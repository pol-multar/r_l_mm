package fr.unice.polytech.si3.lfa.mmultari.modele;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mmultari on 04/07/14.
 */
public class Grammaire {
    private List alphabet;
    private List r;/* Ensemble fini de rêgles de réécriture, les productions */
    private List n;/* Ensemble de symboles non terminaux */
    private List t;/* Ensemble de symboles terminaux */
    private String s;/* L'axiome (symbole de départ) */

    /**
     * Constructeur sans paramètres de la classe Grammaire
     */
    public Grammaire(){
        alphabet=new ArrayList();
        r=new ArrayList();
        n=new ArrayList();
        t=new ArrayList();
        s=null;
    }

    /**
     * Methode qui ajoute une production dans la grammaire
     * @param nonTerm le nom terminal qui designe la production
     * @param term les regles de réécriture
     */
    public void addProd(String nonTerm, String term){
       Production prod= new Production(nonTerm,term);
        r.add(prod);

    }


}

