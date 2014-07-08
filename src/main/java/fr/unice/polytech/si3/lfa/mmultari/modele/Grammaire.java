package fr.unice.polytech.si3.lfa.mmultari.modele;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mmultari on 04/07/14.
 */
public class Grammaire {
    private List alphabet;
    private List r;/* Ensemble fini de rêgles de réécriture, les productions */
    private List n;/* Ensemble de symboles non terminaux */
    private List t;/* Ensemble de symboles terminaux */
    private String s;/* L'axiome (symbole de départ) */

    /**
     * Constructeur sans paramètres de la classe Grammaire
     */
    public Grammaire() {
        alphabet = new ArrayList();
        r = new ArrayList();
        n = new ArrayList();
        t = new ArrayList();
        s = null;
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

    }

    /**
     * Fonction qui va ajouter toutes les productions lues
     * pour les mettre dans la grammaire
     *
     * @param lesProd les productions lues
     */
    public void addAllProd(String[] lesProd) {
        Production prod = new Production();
//TODO découper les productions lues (terms + non term), trouver 2 points de decoupage
    }

    /**
     * Fonction qui va lire dans le fichier text les productions
     *
     * @param fic le fichier qui contient les productions
     */
    public String lectureProd(String fic) {
//TODO lire les productions dans le fichier les mettres sous forme de tableau de String
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

