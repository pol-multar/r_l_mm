package fr.unice.polytech.si3.lfa.mmultari.modele;

import fr.unice.polytech.si3.lfa.mmultari.utilitaires.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



/**
 * Created by mmultari on 04/07/14.
 */
public class Grammaire {
    private List<Production> r;/* Ensemble fini de rêgles de réécriture, les productions */
    private Set<String> n;/* Ensemble de symboles non terminaux (pas de doublons) */
    private Set<String> t;/* Ensemble de symboles terminaux (pas de doublons) */
    private String axiome;/* L'axiome (symbole de départ) */
    boolean containEpsilon;

    //TODO récupéréer les terminaux + si contient epsilon

    /**
     * Constructeur sans paramètres de la classe Grammaire
     */
    public Grammaire() {
        r = new ArrayList<Production>();
        n = new HashSet<String>();
        t = new HashSet<String>();
        axiome = null;
        containEpsilon=false;
    }

    public boolean getContainEpsilon(){
        return this.containEpsilon;
    }

    public Set<String> getT() {
        return t;
    }

    public Set<String> getN() {
        return n;
    }

    public List<Production> getR() {
        return r;
    }

    public String getAxiome() {
        return axiome;
    }

    public void setContainEpsilon(boolean yesOrNo){
        this.containEpsilon=yesOrNo;
    }

    public void setR(List<Production> newR) {
        this.r = newR;
    }

    public void setN(Set<String> newN) {
        this.n = newN;
    }

    public void setT(Set<String> newT) {
        this.t = newT;
    }

    public void setAxiome(String newA) {
        this.axiome=newA;
    }

    /**
     * Fonction d'initialisation de la grammaire
     * A partir du fichier lefic, elle appelle les méthodes
     * permettant d'initialiser la grammaire
     *
     * @param lefic le nom du fichier qui contient la grammaire
     */
    public void initGram(String lefic) {
        //Je lis le fichier
        String ficLu = lectureProd(lefic);
        //Je decoupe la string obtenue en tableau contenant les productions en String
        String[] lesProdLues = ficLu.split(";");
        //J'ajoute les productions dans la grammaire
        addAllProd(lesProdLues);
    }

    public void initGram2(String fic){
        //Je lis le fichier
        String ficLu = lectureProd(fic);
        //Je découpe une premiere fois le fichier
        String[] termEtRegles =ficLu.split("_");
        //La case 0 doit contenir les terminaux, la case 1 toutes les productions
        addAllTerm(termEtRegles[0]);
        //Je decoupe la string obtenue en 1 en tableau contenant les productions en String
        String[] lesProdLues = termEtRegles[1].split(";");
        //J'ajoute les productions dans la grammaire
        addAllProd(lesProdLues);
    }

    private void addAllTerm(String termEtRegle) {
        String[] lesTerm=termEtRegle.split(",");
        for (int i = 0; i <lesTerm.length ; i++) {
            t.add(lesTerm[i]);

        }

    }

    /**
     * Methode qui ajoute une production dans la grammaire
     *
     * @param nonTerm le nom terminal qui designe la production
     * @param term    les regles de réécriture
     */
    public void addProd(String nonTerm, String term) {
        Production prod = new Production(nonTerm, term);
        r.add(prod);
        n.add(nonTerm);

    }

    /**
     * Fonction qui va ajouter toutes les productions lues
     * pour les mettre dans la grammaire
     *
     * @param lesProd un tableau qui contient dans chaque case une production
     */
    public void addAllProd(String[] lesProd) {

        for (int i = 0; i < lesProd.length; i++) {
            String[] temp = lesProd[i].split(":");
            Production pTemp = new Production(temp[0], temp[1]);
            if(this.axiome==null){
                this.axiome=pTemp.getNonTerm();
            }
            r.add(pTemp);
            n.add(pTemp.getNonTerm());

        }

    }


    /**
     * Fonction qui va lire dans le fichier text les productions
     * et renvoyer ce qu'elle a lu sous forme de string
     *
     * @param fic le fichier qui contient les productions
     */
    public String lectureProd(String fic) {
        String line = null;
        String Sbuf = "";
        Path sourcePath = Paths.get(fic);
        try (BufferedReader reader = Files.newBufferedReader(sourcePath, StandardCharsets.UTF_8)) {
            while ((line = reader.readLine()) != null) {
                Sbuf += line;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            return Sbuf;
        }
    }

    /**
     * Une méthode qui retourne un non Terminal pas encore utilisé
     * et l'ajoute dans la liste des non-terminaux
     */

    public String addNewNonTerm(){
        char newNonTerm='A';
        while(n.contains(Character.toString(newNonTerm))){
            int i= utils.toASCII(newNonTerm);
            i=i+1;
            newNonTerm=utils.toChar(i);
        }
        n.add(Character.toString(newNonTerm));
        return Character.toString(newNonTerm);
    }

    public String toString(){
        //TODO modifier
        String s= new String();
        for(Production p:r){
            s=s+p.toString();
        }
        return s;
    }
}

