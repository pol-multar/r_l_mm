package fr.unice.polytech.si3.lfa.mmultari.controler;

import fr.unice.polytech.si3.lfa.mmultari.modele.Modele;
import fr.unice.polytech.si3.lfa.mmultari.vue.VuePrincipale;

import java.io.InputStream;
import java.util.Scanner;

/**
 * @author Maxime on 27/07/2014.
 */
public class Controler {
    private Modele leModele;
    private VuePrincipale laVue;
    private static InputStream fdin=System.in;
    private static Scanner scanner = new Scanner(fdin);

    public Controler(Modele unModel, VuePrincipale uneVue){
        this.leModele=unModel;
        this.laVue=uneVue;
    }

    public int getNumber() {
        scanner=new Scanner(fdin);
        try
    }



}
