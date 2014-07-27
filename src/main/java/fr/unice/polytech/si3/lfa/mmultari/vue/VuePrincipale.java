package fr.unice.polytech.si3.lfa.mmultari.vue;

import fr.unice.polytech.si3.lfa.mmultari.modele.Grammaire;
import fr.unice.polytech.si3.lfa.mmultari.modele.Modele;
import fr.unice.polytech.si3.lfa.mmultari.modele.Production;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * @author Maxime on 27/07/2014.
 */


public class VuePrincipale implements Observer {

    private Modele leModele;

    public VuePrincipale(Modele unModele) {
        this.leModele = unModele;
        leModele.addObserver(this);
    }

    public void messageAcceuil() {
        System.out.println("Bienvenue dans le programme de transformation de grammaire\n\n");
    }

    public void questionPreliminaire() {
        System.out.println("Epsilon appartient-il au langage engendré par la grammaire ?(Y/N)");
    }

    public void menuPrincipal() {
        System.out.println("\n   ***   Menu principal :   ***   \n");
        System.out.println("1 -> Afficher la grammaire");
        System.out.println("2 -> Supprimer les improductifs de la grammaire");
        System.out.println("3 -> Supprimer les inaccessibles de la grammaire");
        System.out.println("4 -> Supprimer les epsilon-productions");
        System.out.println("5 -> Supprimer les renommages");
        System.out.println("6 -> Mettre la grammaire sous Forme Normale de Chomsky");
        System.out.println("\n\n0 -> Quitter le prgramme");
    }

    public void affichageGram(Grammaire uneGram) {
        List<String> listNonTerm = new ArrayList<>(uneGram.getN());
        List<String> listTerm = new ArrayList<>(uneGram.getT());
        List<Production> listProd = uneGram.getR();


        System.out.println("\n *** Affichage de la grammaire *** \n");
        System.out.println("Axiome :" + uneGram.getAxiome());
        System.out.print("Symboles terminaux : ");
        for (String s : listTerm) {
            System.out.print(s + ", ");
        }
        System.out.print("\nSymboles non terminaux : ");
        for (String s : listNonTerm) {
            System.out.print(s + ", ");
        }
        System.out.println("\nProductions : ");
        for (Production p : listProd) {
            affichageProd(p);
        }
        System.out.println("*****************************************");
    }

    private void affichageProd(Production uneProd) {
        System.out.println(uneProd);
    }

    public void badOption(){
        System.out.println("Commande inconnue, recommencez \n \n");
    }

    public void quitter(){
        System.out.println("\n Sortie du programme ... \n \n");
    }

    public void updateImprod(Object arg) {
        if ("2".equals(arg)) {
            System.out.println("Les improductifs ont été retirés de la grammaire");
            affichageGram(this.leModele.getLaGrammaire());
        }
    }

    public void updateInacc(Object arg) {
        if ("3".equals(arg)) {
            System.out.println("Les inaccessibles ont été retirés de la grammaire");
            affichageGram(this.leModele.getLaGrammaire());
        }
    }

    public void updateNettoy(Object arg) {
        if ("23".equals(arg)) {
            System.out.println("Les improductifs et les inaccessibles ont été retirés de la grammaire");
            affichageGram(this.leModele.getLaGrammaire());
        }
    }

    public void updateEpsilonProd(Object arg) {
        if ("4".equals(arg)) {
            System.out.println("Les epsilon-production ont été retirés de la grammaire");
            affichageGram(this.leModele.getLaGrammaire());
        }

    }

    public void updateRenom(Object arg) {
        if ("5".equals(arg)) {
            System.out.println("Les renommages ont été retirés de la grammaire");
            affichageGram(this.leModele.getLaGrammaire());
        }

    }

    public void updateChomsky(Object arg) {
        if ("6".equals(arg)) {
            System.out.println("La gramamire a été mise sous FNG");
            affichageGram(this.leModele.getLaGrammaire());
        }

    }


    @Override
    public void update(Observable o, Object arg) {
        updateImprod(arg);
        updateInacc(arg);
        updateNettoy(arg);
        updateEpsilonProd(arg);
        updateRenom(arg);
        updateChomsky(arg);

    }
}
