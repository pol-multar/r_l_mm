package fr.unice.polytech.si3.lfa.mmultari;

import fr.unice.polytech.si3.lfa.mmultari.controler.ChomskyGrammaire;
import fr.unice.polytech.si3.lfa.mmultari.controler.GrammaireCleaner;
import fr.unice.polytech.si3.lfa.mmultari.controler.SupprRenomGram;
import fr.unice.polytech.si3.lfa.mmultari.modele.Grammaire;
import fr.unice.polytech.si3.lfa.mmultari.vue.VuePrincipale;

import java.io.InputStream;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App
{


    private static InputStream fdin=System.in;
    private static Scanner scanner= new Scanner(fdin);

    public static void main( String[] args )
    {
        VuePrincipale vuePrincipale=new VuePrincipale();

        vuePrincipale.messageAcceuil();
        vuePrincipale.questionPreliminaire();
        vuePrincipale.menuPrincipal();


    }


}
