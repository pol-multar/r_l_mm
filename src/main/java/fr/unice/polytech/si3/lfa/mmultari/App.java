package fr.unice.polytech.si3.lfa.mmultari;

import fr.unice.polytech.si3.lfa.mmultari.controler.Controler;
import fr.unice.polytech.si3.lfa.mmultari.modele.Modele;
import fr.unice.polytech.si3.lfa.mmultari.vue.VuePrincipale;

/**
 * Hello world!
 */
public class App {
    public App() {
        throw new AssertionError();
    }

    public static void main(String[] args) {
        if (args.length == 1) {
            Modele modele = new Modele(args[0]);
            VuePrincipale vuePrincipale = new VuePrincipale(modele);
            Controler controler = new Controler(modele, vuePrincipale);
            vuePrincipale.messageAcceuil();
            controler.run();
        } else {//Si on ne donne pas de fichier de grammaire
            Modele modele = new Modele("testAll.txt");
            VuePrincipale vuePrincipale = new VuePrincipale(modele);
            Controler controler = new Controler(modele, vuePrincipale);
            vuePrincipale.messageAcceuil();
            controler.run();
        }
    }


}
