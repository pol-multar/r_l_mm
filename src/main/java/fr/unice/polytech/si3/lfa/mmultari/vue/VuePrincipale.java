package fr.unice.polytech.si3.lfa.mmultari.vue;

import fr.unice.polytech.si3.lfa.mmultari.modele.Grammaire;
import fr.unice.polytech.si3.lfa.mmultari.modele.Modele;

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
        System.out.println("Epsilon appartient-il au langage engendré par la grammaire ?");
    }

    public void menuPrincipal() {
        System.out.println("\n   ***   Menu principal :   ***   \n");
        System.out.println("1 -> Afficher la grammaire");
        System.out.println("2 -> Supprimer les improductifs de la grammaire");
        System.out.println("3 -> Supprimer les inaccessibles de la grammaire");
        System.out.println("4 -> Supprimer les epsilon-productions");
        System.out.println("5 -> Supprimer les renommages");
        System.out.println("6 -> Mettre la grammaire sous Forme Normale de Chomsky");
    }

    public void affichageGram(Grammaire uneGram) {

    }

    private void affichageProds(Grammaire uneGram) {
    }

    public void updateImprod(Object arg) {
        if ("2".equals(arg)) {
            affichageProds(this.leModele.getLaGrammaire());
        }
    }

    public void updateInacc(Object arg) {

    }

    public void updateNettoy(Object arg) {

    }

    public void updateEpsilonProd(Object arg) {

    }

    public void updateRenom(Object arg) {

    }

    public void updateChomsky(Object arg) {

    }


    @Override
    public void update(Observable o, Object arg) {

    }
}
