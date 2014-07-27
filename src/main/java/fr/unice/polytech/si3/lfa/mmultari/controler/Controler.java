package fr.unice.polytech.si3.lfa.mmultari.controler;

import fr.unice.polytech.si3.lfa.mmultari.modele.Modele;
import fr.unice.polytech.si3.lfa.mmultari.vue.VuePrincipale;

import java.io.InputStream;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * @author Maxime on 27/07/2014.
 */
public class Controler {
    private Modele leModele;
    private VuePrincipale laVue;
    private static InputStream fdin = System.in;
    private static Scanner scanner = new Scanner(fdin);

    public Controler(Modele unModel, VuePrincipale uneVue) {
        this.leModele = unModel;
        this.laVue = uneVue;
    }

    /**
     * Permet de retourner un numero entre par un utilisateur
     *
     * @return un nombre tapé par l'utilisateur avec le clavier
     */
    public int getNumber() {
        scanner = new Scanner(fdin);
        try {
            return scanner.nextInt();
        } catch (InputMismatchException exp) {
            return -1;
        }
    }

    public void run(int numMenu) {
        int choix = -1;
        while (true) {
            try {
                affichage(numMenu);
                choix = getNumber();
                if (choix != 0) {
                    choixMenu(choix);
                } else {
                    laVue.quitter();
                    break;
                }
            } catch (ClassCastException expected) {
                laVue.badOption();
            }
        }
    }

    public void choixMenu(int choix) {
        if (choix == 1) {

        } else if (choix == 2) {
            leModele.supprimerImproductifs();
        } else if (choix == 3) {
            leModele.supprimerInaccessibles();
        } else if (choix == 4) {
            leModele.supprimerEpsilonProd();
        } else if (choix == 5) {
            leModele.supprimerRenommage();
        } else if (choix == 6) {
            leModele.miseEnFormeChomsky();
        }

    }

    public void affichage(int menu) {

    }

}
