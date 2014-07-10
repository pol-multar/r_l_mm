package fr.unice.polytech.si3.lfa.mmultari.modele;

import java.io.*;
import java.nio.charset.Charset;
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
    private List r;/* Ensemble fini de rêgles de réécriture, les productions */
    private Set n;/* Ensemble de symboles non terminaux (pas de doublons) */
    private Set t;/* Ensemble de symboles terminaux (pas de doublons) */
    private String s;/* L'axiome (symbole de départ) */

    /**
     * Constructeur sans paramètres de la classe Grammaire
     */
    public Grammaire() {
        r = new ArrayList();
        n = new HashSet();
        t = new HashSet();
        s = null;
    }

    public void initGram(String lefic){
        String ficLu=lectureProd(lefic);
    }

    /**
     * Methode qui ajoute une production dans la grammaire
     *
     * @param nonTerm le nom terminal qui designe la production
     * @param term    les regles de réécriture
     */
    private void addProd(String nonTerm, String term) {
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
        Production prod = new Production();
//TODO découper les productions lues (terms + non term), trouver 2 points de decoupage

    }

    /**
     * Fonction qui découpe un chaine de caractère à chaque endroit ou
     * elle trouve le signe ":"
     * @param initProd les chaine de caractère à découper
     * @return un tableau contenant la chaine découpée
     */
    public String [] splitProd(String initProd){
        String decoupe [] = initProd.split(":");
        return decoupe;
    }

    /**
     * Fonction qui va lire dans le fichier text les productions
     * et renvoyer ce qu'elle a lu sous forme de string
     *
     * @param fic le fichier qui contient les productions
     */
    private String lectureProd(String fic) {
        String line = null;
        String Sbuf = "";
        Path sourcePath = Paths.get(fic);
        try (BufferedReader reader = Files.newBufferedReader(sourcePath, StandardCharsets.UTF_8)) {
            while ((line = reader.readLine()) != null) {
                Sbuf += line;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {

            return Sbuf;
        }
    }


}

