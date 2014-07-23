package fr.unice.polytech.si3.lfa.mmultari.controler;

import fr.unice.polytech.si3.lfa.mmultari.modele.Grammaire;
import fr.unice.polytech.si3.lfa.mmultari.modele.Production;

import java.util.*;

/**
 * Created by Maxime on 23/07/2014.
 */
public class SupprRenomGram {
    private Grammaire laGrammaire;
    private boolean debug=false;

    public SupprRenomGram(Grammaire uneGrammaire){
        this.laGrammaire=uneGrammaire;
    }

    ///////////////////////////////////// Partie suppression des renommages /////////////////////////////////////

    /**
     * Méthode chargée de mettre les ensembles de renommages dans une hashmap
     *
     * @return
     */
    public Hashtable<String, Set<String>> calc_ens_ren() {
        List<Production> listeProd=laGrammaire.getR();
        /* Ren est l'ensemble qui contient les variables en lesquelles chaque variable peut être renommé */
        Hashtable<String, Set<String>> ren = new Hashtable<>();
        //La key est le nom de la production, et la donnée associée la liste des variables en lequelles la clé peut être renommée

        for (Production p : listeProd) {
            Set<String> prodRen = calc_Renommages_1Prod(p);
            ren.put(p.getNonTerm(), prodRen);
        }

        return ren;
    }

    /**
     * Méthode chargée de calculer l'ensemble de renommage d'une variable X donnée en argument
     *
     * @param p la variable X dont on souhaite connaitre les renommages
     * @return l'ensemble de variable en lesquelle la variable X peut être renommée
     */
    public Set<String> calc_Renommages_1Prod(Production p) {
        List<Production> listeProd=laGrammaire.getR();
        Set<String> oldEns = new HashSet<>();
        Set<String> newEns = new HashSet<>();

        /* Première étape : Ren0 qui contient seulement le non Terminal de la production qu'on analyse  */
        newEns.add(p.getNonTerm());
        /* Etape suivante : on complete l'ensemble en parcourant chacune des productions de celui-ci à la recherche de nouvelles regles unitaires */
        while (!(oldEns.equals(newEns))) {
            oldEns = newEns;
            for (Production production : listeProd) {
                if (debug) {
                    System.out.println("calc_Renommages_1Prod : 1er for, analyse de la production : " + production.getNonTerm());
                }
                if (oldEns.contains(production.getNonTerm())) {// Si la production est pas dans l'ensemble, on la parcourt pour savoir si elle contient des regles unitaires
                    if (debug) {
                        System.out.println("calc_Renommages_1Prod : La production " + production.getNonTerm() + " est dans oldEns, je lance recherche unitaire dessus");
                    }
                    rechercheUnitaire(production, newEns);
                }
            }
        }

        return newEns;
    }

    /**
     * Méthode chargée de compléter l'ensemble de renommage
     *
     * @param production la production que l'on souhaite analyser
     * @param ensemble   l'ensemble de renommage actuel
     */
    private void rechercheUnitaire(Production production, Set<String> ensemble) {
        Set<String> ln=laGrammaire.getN();
        if (debug) {
            System.out.println("rechercheUnitaire : Je vais analyser les regles de la production:" + production.getNonTerm());
        }
        List<String> l = production.getRegles();
    /* On parcourt toutes les regles de la production */
        for (String regle : l) {
            /* Si la regle qu'on est en train de lire est composé d'un seul non terminal... */
            if (ln.contains(regle)) {
                /*... et que celui-ci ne fait pas encore partie de l'ensemble de renommage */
                if (!(ensemble.contains(regle))) {
                    /* on l'ajoute : */
                    ensemble.add(regle);
                    if (debug) {
                        System.out.println("rechercheUnitaire : J'ajoute le non terminal :" + regle + " à l'ensemble");
                    }
                }
            }
        }

    }

    /**
     * La méthode chargée de la suppression des renommages
     */
    public void supprRenom() {
        List<Production> listeProd=laGrammaire.getR();
        /*Avant de supprimer les renommages, on calcul les variables de renommage */
        Hashtable<String, Set<String>> EnsRen = calc_ens_ren();
        System.out.println("Affichage des regles avant nettoyage :");
        for (Production p : listeProd) {
            System.out.println(p);
        }

        /* On supprime les regles unitaires */
        for (Production p : listeProd) {
            supprRegleUnitaire(p);
        }
        System.out.println("supprRenom : J'ai fini le premier for");
        System.out.println("Affichage des regles nettoyées :");
        for (Production p : listeProd) {
            System.out.println(p);
        }
        /* On complete les regles de chaque production, à partir des ensembles calculés */

        for (Production p : listeProd) {
            ajoutRegleRen(p, EnsRen.get(p.getNonTerm()));
        }
        System.out.println("supprRenom : j'ai fini le deuxieme for");

        for (Production p : listeProd) {
            System.out.println(p);
        }

        //TODO On va vérifier que toutes les productions soient bien différentes


    }


    /**
     * Une méthode chargée de supprimer les regles qui ne possèdent qu'un non-terminal dans une production
     *
     * @param p la production à nettoyer
     */
    private void supprRegleUnitaire(Production p) {
        Set<String> ln=laGrammaire.getN();
        List<String> oldRegles = p.getRegles();
        List<String> newRegles = new ArrayList<>();/* la nouvelle liste contenant */
        /* On parcourt toutes les regles de la production */
        for (String regle : oldRegles) {
            System.out.println("Regle analysée : "+ regle);
            /* Si la regle qu'on est en train de lire est composé d'un seul non terminal... */
            if (!(ln.contains(regle))) {
                System.out.println("Regle ajoutée : "+regle);
                /* On l'enleve */
                newRegles.add(regle);
            }else{
                System.out.println("Regle oubliée : "+regle);
            }
        }
        /* On modifie la liste de regles de la production */
        p.setRegles(newRegles);
    }

    /**
     * Une méthode chargée d'ajouter les regles manquantes à une production après suppression des renommages
     *
     * @param p      la production à compléter
     * @param ensRen
     */
    private void ajoutRegleRen(Production p, Set<String> ensRen) {
        List<Production> listeProd=laGrammaire.getR();
        /*On récupère les regles que possede actuellement la production */
        List<String> l = p.getRegles();

        List<String> listEnsRen = new ArrayList<>(ensRen);

        String variable = p.getNonTerm();

        for (String varRen : listEnsRen) {
            if (varRen.equals(variable)) {//Obligatoire car la production est contenue dans Ren0
                continue;
            } else {
                for (Production prodDeR : listeProd) {//Je cherche la production désignée dans listEnsRen dans la liste des production afin de récupérer les regles à ajouter
                    if (prodDeR.getNonTerm().equals(varRen) && !(prodDeR.getNonTerm().equals(variable))) {
                        ajoutRegleProd(l, prodDeR);
                    }
                }
            }
        }

        p.setRegles(l);
    }

    /**
     * Méthode chargée d'ajouter les regle d'une production à une liste donnée en parametre
     *
     * @param l       la liste où ajouter les regles
     * @param prodDeR la production ou l'on récupère les regles
     */
    private void ajoutRegleProd(List<String> l, Production prodDeR) {
        List<String> temp = prodDeR.getRegles();

        for (String regle : temp) {
            if (!(l.contains(regle)))
                l.add(regle);
        }
    }

}
