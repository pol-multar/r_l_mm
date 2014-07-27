package fr.unice.polytech.si3.lfa.mmultari;

import fr.unice.polytech.si3.lfa.mmultari.controler.ChomskyGrammaire;
import fr.unice.polytech.si3.lfa.mmultari.controler.Controler;
import fr.unice.polytech.si3.lfa.mmultari.controler.GrammaireCleaner;
import fr.unice.polytech.si3.lfa.mmultari.controler.SupprRenomGram;
import fr.unice.polytech.si3.lfa.mmultari.modele.Grammaire;
import fr.unice.polytech.si3.lfa.mmultari.modele.Modele;
import fr.unice.polytech.si3.lfa.mmultari.vue.VuePrincipale;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App
{
    public App(){
        throw new AssertionError();
    }


    private static InputStream fdin=System.in;
    private static Scanner scanner= new Scanner(fdin);

    public static void main( String[] args )
    {
        if(args.length==2) {
            Modele modele = new Modele(args[1]);//TODO verifeir
            VuePrincipale vuePrincipale = new VuePrincipale(modele);
            Controler controler = new Controler(modele, vuePrincipale);

            vuePrincipale.messageAcceuil();
            vuePrincipale.questionPreliminaire();
            controler.run(0);
            //TODO recup
        }else{
Modele modele = new Modele("testAll.txt");
            VuePrincipale vuePrincipale = new VuePrincipale(modele);
            Controler controler = new Controler(modele,vuePrincipale);
            vuePrincipale.messageAcceuil();
            vuePrincipale.questionPreliminaire();
            controler.run(1);
        }


        //vuePrincipale.messageAcceuil();
        //vuePrincipale.questionPreliminaire();
        //vuePrincipale.menuPrincipal();


    }


}
