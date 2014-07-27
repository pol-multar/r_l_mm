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

    /**
     * Permet de retourner un chaine de caractere entre par un utilisateur.<br />
     * L'utilisateur ne peut entre une mauvaise valeur.
     *
     * @return une chaine entre par l'utilisateur via le clavier.
     */
    public String getString() {
        scanner = new Scanner(fdin);
        try {
            return scanner.nextLine();
        } catch (InputMismatchException exp) {
            return null;
        }
    }

    public void run() {
        int choix = -1;
        String haveEps = new String();
        laVue.questionPreliminaire();
        haveEps = getString();
        menuEpsilon(haveEps);
        while (true) {
            try {
                laVue.menuPrincipal();
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
        switch (choix) {
            case 1:
                laVue.affichageGram(leModele.getLaGrammaire());
                break;
            case 2:
                leModele.supprimerImproductifs();
                break;
            case 3:
                leModele.supprimerInaccessibles();
                break;
            case 4:
                leModele.supprimerEpsilonProd();
                break;
            case 5:
                leModele.supprimerRenommage();
                break;
            case 6:
                leModele.miseEnFormeChomsky();
                break;
            default:
                laVue.badOption();
                break;
        }
    }

    public void menuEpsilon(String rep) {
        if (rep.equals("Y")) {
            leModele.getLaGrammaire().setContainEpsilon(true);
        } else if (rep.equals("N")) {
            leModele.getLaGrammaire().setContainEpsilon(false);
        } else {
            laVue.badOption();
        }
    }


}
