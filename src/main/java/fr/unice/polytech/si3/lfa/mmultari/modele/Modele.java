package fr.unice.polytech.si3.lfa.mmultari.modele;

import fr.unice.polytech.si3.lfa.mmultari.controler.ChomskyGrammaire;
import fr.unice.polytech.si3.lfa.mmultari.controler.GrammaireCleaner;
import fr.unice.polytech.si3.lfa.mmultari.controler.SupprRenomGram;

import java.util.Observable;

/**
 * @author Maxime on 27/07/2014.
 */
public class Modele extends Observable {
    private Grammaire laGrammaire;
    private ChomskyGrammaire miseEnFormeChom;
    private SupprRenomGram supprRenomGram;
    private GrammaireCleaner grammaireCleaner;

    /**
     * Le constructeur de la classe modele.
     * Le modele contient toutes les informations du programme
     * @param leFic le fichier qui contient la description de la grammaire
     */
    public Modele(String leFic){
        laGrammaire=new Grammaire();
        laGrammaire.initGram2(leFic);
        miseEnFormeChom= new ChomskyGrammaire(laGrammaire);
        supprRenomGram = new SupprRenomGram(laGrammaire);
        grammaireCleaner = new GrammaireCleaner(laGrammaire);
    }

    public Grammaire getLaGrammaire(){
        return laGrammaire;
    }

    public ChomskyGrammaire getMiseEnFormeChom(){
        return miseEnFormeChom;
    }

    public SupprRenomGram getSupprRenomGram(){
        return supprRenomGram;
    }

    public GrammaireCleaner getGrammaireCleaner(){
        return grammaireCleaner;
    }

    /**
     * Appelle la m&eacute;thode de suppression des improductifs
     * @see fr.unice.polytech.si3.lfa.mmultari.controler.GrammaireCleaner#nettoyNonProdGramm()
     */
    public void supprimerImproductifs(){
        grammaireCleaner.nettoyNonProdGramm();
        setChanged();
        notifyObservers("2");
    }

    /**
     * Appelle la m&eacute;thode de suppression des inaccessibles
     * @see fr.unice.polytech.si3.lfa.mmultari.controler.GrammaireCleaner#nettoyNonAccGramm()
     */
    public void supprimerInaccessibles(){
        grammaireCleaner.nettoyNonAccGramm();
        setChanged();
        notifyObservers("3");
    }

    /**
     * Appelle la m&eacute;thode de nettoyage de grammaire (suppression des improductifs puis des inaccessibles)
     * @see fr.unice.polytech.si3.lfa.mmultari.controler.GrammaireCleaner#nettoyNonProdGramm()
     * @see fr.unice.polytech.si3.lfa.mmultari.controler.GrammaireCleaner#nettoyNonAccGramm()
     */
    public void nettoyageGrammaire(){
        grammaireCleaner.nettoyGrammaire();
        setChanged();
        notifyObservers("23");
    }

    /**
     * Appelle la methode de suppression des epsilon-productions de la grammaire
     * @see fr.unice.polytech.si3.lfa.mmultari.controler.GrammaireCleaner#supprimer_epsilon_prod()
     */
    public void supprimerEpsilonProd(){
        grammaireCleaner.supprimer_epsilon_prod();
        setChanged();
        notifyObservers("4");
    }

    /**
     * Appelle la methode de suppression des renommages de la grammaire
     * @see fr.unice.polytech.si3.lfa.mmultari.controler.SupprRenomGram#supprRenom()
     */
    public void supprimerRenommage(){
        supprRenomGram.supprRenom();
        setChanged();
        notifyObservers("5");
    }

    /**
     * Appelle la methode de Mise en Forme de Chomsky d'une grammaire
     * @see fr.unice.polytech.si3.lfa.mmultari.controler.ChomskyGrammaire#miseEnFormeChomsky()
     */
    public void miseEnFormeChomsky(){
        miseEnFormeChom.miseEnFormeChomsky();
        setChanged();
        notifyObservers("6");
    }

    /**
     * Methode qui active le mode debug/verbose des méthodes du programme
     * @see fr.unice.polytech.si3.lfa.mmultari.controler.GrammaireCleaner#setDebug(boolean)
     * @see fr.unice.polytech.si3.lfa.mmultari.controler.SupprRenomGram#setDebug(boolean)
     * @see fr.unice.polytech.si3.lfa.mmultari.controler.ChomskyGrammaire#setDebug(boolean)
     */
    public void debugModeEn(){
        this.grammaireCleaner.setDebug(true);
        this.supprRenomGram.setDebug(true);
        this.miseEnFormeChom.setDebug(true);

    }
}
