package fr.unice.polytech.si3.lfa.mmultari.controler;

import fr.unice.polytech.si3.lfa.mmultari.modele.Grammaire;
import fr.unice.polytech.si3.lfa.mmultari.modele.Production;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * Created by Maxime on 17/07/2014.
 */
public class GrammaireCleaner {
    private Grammaire gOrig;
    private Map<String, Pattern> lesProds;
    private List<Production> lr;
    private Set<String> lt;
    private Set<String> ln;
    private boolean debug = false;

    public GrammaireCleaner(Grammaire gOriginale) {
        gOrig = gOriginale;
        lesProds = new Hashtable<String, Pattern>();
        lr = gOrig.getR();/* Liste des productions de la grammaire*/
        lt = gOrig.getT();/* Set des terminaux de la grammaire */
        ln = gOrig.getN();/* Set des non terminaux de la grammaire */

        //TODO experimentation, peut être inutile à la fin :

        String[] tab;

        try {
            for (int i = 0; i < (gOrig.getR().size()); i++) {
                tab = gOrig.getR().get(i).toTab();
                lesProds.put(tab[0], Pattern.compile(tab[1]));
            }
        } catch (PatternSyntaxException pse) {
            System.err.println("Erreur lors de la génération du pattern");
        }
    }

    ////////////////////////////// Partie méthodes utilisées dans plusieurs endroits //////////////////////////////////

    /**
     * Cette méthode est la même que listProdEtape2
     *
     * @param ancien_n
     * @param p
     * @return
     */
    private String parcoursRegles(Set<String> ancien_n, Production p) {
     /* La liste contenant toutes les regles de la production :*/
        List<String> l = p.getRegles();

     /* Un tableau de taille le nombre de regles. Si une regle contient un non terminal qui n'est pas dans ancien_n,
      * la cases d'indice correspondante dans le tableau aura la valeur True
      */
        Boolean[] haveNonTermUnex = new Boolean[l.size()];
        Boolean containOnlyExpected = false;

        /* Initialisation du tableau à false */
        for (int cpt = 0; cpt < haveNonTermUnex.length; cpt++) {
            haveNonTermUnex[cpt] = false;

        }

        /* On passe maintenant à l'analyse de chaque regle de production*/

        for (int i = 0; i < l.size(); i++) { //je parcours toutes les regles

            char[] tab = l.get(i).toCharArray(); // Je transforme la regle que j'analyse en tableau de caractere

            for (int j = 0; j < tab.length; j++) { //Je parcours la regle

                if ((!ancien_n.contains(Character.toString(tab[j]))) && (!lt.contains(Character.toString(tab[j])))) {//lt contient la liste des terminaux
                    haveNonTermUnex[i] = true;// Si une regle contient un non-terminal qui n'est pas dans ancien_n, je l'indique dans le tableau haveNonTerm
                }
            }
        }
/* On va maintenant parcourir le tableau haveNonTermUnex pour savoir si une regle au moins respecte les conditions
* autrement dit si au moins une case du tableau est à false
*/
        for (int k = 0; ((k < haveNonTermUnex.length) && (!containOnlyExpected)); k++) {
            if (haveNonTermUnex[k] == false) {
                containOnlyExpected = true;
            }
        }

        /* Si aucune regle n'est exempte de non terminaux on retourne null*/
        if (containOnlyExpected) {
            return p.getNonTerm();
        } else {
            return null;
        }
    }

    /**
     * Cette méthode est chargée de nettoyer une production de ses regles non productives
     *
     * @param oldProd
     * @return la production nettoyée, null si la production nettoyée ne contient plus de regles
     */
    public Production nettoyProd(Production oldProd) {
//TODO tester
        Boolean haveNonTermUnex = false;
        /* La liste contenant toutes les regles de la production :*/
        List<String> l = oldProd.getRegles();

        /* On analyse chaque regle de la production */
        for (int i = 0; i < l.size(); i++) { //je parcours toutes les regles

            char[] tab = l.get(i).toCharArray(); // Je transforme la regle que j'analyse en tableau de caractere

            for (int j = 0; j < tab.length && !haveNonTermUnex; j++) { //Je parcours la regle

                if ((!ln.contains(Character.toString(tab[j]))) && (!lt.contains(Character.toString(tab[j])))) {//ln contient la liste des non terminaux, lt celle des terminaux
                    haveNonTermUnex = true;
                }
            }
            /* Si la regle possede un non terminal que l'on a supprimé, on l'enleve */
            if (haveNonTermUnex) {
                l.remove(i);
            }
            /* On remet haveNonTermUnex à false pour la prochaine regle */
            haveNonTermUnex = false;
        }

        if (l.isEmpty()) {
            return null;
        } else {
            oldProd.setRegles(l);
            return oldProd;
        }
    }

    private String suppressionChar(String s, int positionRemplacee) {
        ArrayList<Character> charList = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            charList.add(s.charAt(i));
        }
        charList.remove(positionRemplacee);
        String newS = new String();
        for (int i = 0; i < charList.size(); i++) {
            newS = newS + charList.get(i);
        }
        return newS;
    }


    /**
     * Méthode chargée d'executer tous les nettoyages de grammaire
     */
    public void nettoyGrammaire() {
        //On supprime d'abord les improductifs
        this.nettoyNonProdGramm();
        this.nettoyNonAccGramm();
    }

    ///////////////////////////////////// Partie suppression des improductifs /////////////////////////////////////

    /**
     * Cette méthode est chargée de lister les productions productives
     *
     * @return un set des productions productives
     */
    public Set<String> listProductifs() {
        Set<String> Ancien_N = new HashSet<String>();
        Set<String> Nouveau_N = new HashSet<String>();
        String s = new String();

        /* Etape 1 : On cherche les production contenant des regles sans non-terminaux*/
        for (Production p : lr) {
            s = listProdEtape1(p);
            if (!(s == null)) {
                Nouveau_N.add(s);
            }
        }

        s = null;
        /* Après cette étape, Nouveau_N contient les nom terminaux désignant les productions qui possedent au moins une regle sans non-terminaux*/

        /* Etape 2 : on reparcourt les productions et on ajout dans notre ensemble celles qui possèdent des regles sans non-terminaux et des regles avec les non-terminaux précédents*/
        /* On répete cette étape tant que l'on ajoute des Productions productives dans Nouveau_N */
        while (!Ancien_N.equals(Nouveau_N)) {
            Ancien_N = Nouveau_N;
            for (Production p : lr) {
                s = listProdEtape2(p, Ancien_N);
                if (!(s == null)) {
                    Nouveau_N.add(s);
                }
            }
        }

        /* On retourne la liste contenant toutes les productions productives */
        return Nouveau_N;
    }

    /**
     * Cette méthode est chargée de vérifier si les regles d'une production ne contiennent pas de non-terminaux
     *
     * @param prod la production à analyser
     * @return la désignation de la production si celle si possède au moins une regle sans non-terminaux, null sinon
     */
    public String listProdEtape1(Production prod) {

/* La liste contenant toutes les regles de la production :*/
        List<String> l = prod.getRegles();
/* Un tableau de taille le nombre de regles. Si une regle contient un non terminal,
 * la cases d'indice correspondante dans le tableau aura la valeur True
 */
        Boolean[] haveNonTerm = new Boolean[l.size()];
        Boolean containOnlyTerm = false;

        /* Initialisation du tableau à false */
        for (int cpt = 0; cpt < haveNonTerm.length; cpt++) {
            haveNonTerm[cpt] = false;

        }
       /* On va analyser toutes les regles de la production pour savoir si elles contiennent des non-terminaux */
        for (int i = 0; i < l.size(); i++) { //je parcours toutes les regles

            char[] tab = l.get(i).toCharArray(); // Je transforme la regle que j'analyse en tableau de caractere

            for (int j = 0; j < tab.length; j++) { //Je parcourt la regle afin de voir si elle possede un non terminal

                if (ln.contains(Character.toString(tab[j]))) {//ln contient la liste des non terminaux
                    haveNonTerm[i] = true;// Si une regle contient un non-terminal, je l'indique dans le tableau haveNonTerm
                }
            }
        }

/* On va maintenant parcourir le tableau haveNonTerm pour savoir si une regle au moins ne possede pas de non-terminaux,
* autrement dit si au moins une case du tableau est à false
*/
        for (int k = 0; (k < haveNonTerm.length); k++) {
            if (haveNonTerm[k] == false) {
                containOnlyTerm = true;
            }
        }

        /* Si aucune regle n'est exempte de non terminaux on retourne null*/
        if (containOnlyTerm) {
            return prod.getNonTerm();
        } else {
            return null;
        }
    }

    /**
     * Cette méthode est chargée de vérifier si les regles d'une production ne contiennent
     *
     * @param p       la production dont nous allons analyser les regles
     * @param nActuel les non-terminaux productifs
     * @return le non-terminal qui désigne la production si elle est productive, null sinon
     */
    public String listProdEtape2(Production p, Set<String> nActuel) {
        //TODO tester
        return parcoursRegles(nActuel, p);
    }


    /**
     * Cette méthode est chargée d'executer le nettoyage des productions non productives de la grammaire
     */
    public void nettoyNonProdGramm() {

        /* je met à jour ln pour qu'elle ne contiennent que les non terminaux productifs */
        ln = listProductifs();
        Production p, pNet;

        /* Je parcours toutes les productions de la grammaire et je retire le improductifs */
        for (int i = 0; i < lr.size(); i++) {
            p = lr.get(i);
            pNet = nettoyProd(p);
            if (pNet != null) {
                lr.set(i, pNet);
            } else {
                lr.remove(i);
            }
        }

        /* Je met a jour les données de la grammaire après modification */
        gOrig.setN(this.ln);
        gOrig.setR(this.lr);
    }

    ///////////////////////////////////// Partie suppression des inaccessibles /////////////////////////////////////

    /**
     * Cette méthode est chargée de lister les productions productives
     * Pour trouver les variables accessibles, il suffit de parcourir les regles en partant de l'axiome
     *
     * @return un set des productions accessibles
     */
    public Set<String> listAccessibles() {
        Set<String> Ancien_N = new HashSet<String>();
        Set<String> Nouveau_N = new HashSet<String>();

        Nouveau_N.add(gOrig.getAxiome());

        while (!Ancien_N.equals(Nouveau_N)) {
            Ancien_N = Nouveau_N;
            for (Production p : lr) {// Je parcours toutes les productions
                if (Ancien_N.contains(p.getNonTerm())) { //Si celle-ci fait partie de Ancien_N, je l'analyse
                    String s = parcoursRegles(Ancien_N, p);// s contient les nouvelles variables accessibles depuis la producrion analysée
                    if (!(s == null)) {
                        Nouveau_N.add(s);
                    }
                }
            }
        }

        /* On retourne la liste contenant toutes les productions accessibles*/
        return Nouveau_N;
    }

    //A la fin de cette méthode, je possède, en théorie, la liste de toutes les variables accessibles

    /**
     * Cette méthode est chargée d'executer le nettoyage des productions non productives de la grammaire
     */

    public void nettoyNonAccGramm() {

        /* je met à jour ln pour qu'elle ne contiennent que les non terminaux accessibles */
        ln = listAccessibles();
        Production p, pNet;

        /* Je parcours toutes les productions de la grammaire et je retire le inaccessibles */
        for (int i = 0; i < lr.size(); i++) {
            p = lr.get(i);
            pNet = nettoyProd(p);

            if (pNet != null) {
                lr.set(i, pNet);
            } else {
                lr.remove(i);
            }
        }

        /* Je met a jour les données de la grammaire après modification */
        gOrig.setN(this.ln);
        gOrig.setR(this.lr);
    }

    ///////////////////////////////////// Partie suppression des ε-production /////////////////////////////////////

    /* Etape 1: calculer les variables annulables */
    /* Etape 2: ajouter dans toutes les regles les différentes possibilitées oul 'on a remplacé par epsilon*/
    /* Etape 3: supprimer toutes les regles contenant epsilon*/

    /**
     * Méthode chargée de calculer l'ensemble des non terminaux dérivant epsilon directement ou indirectement
     *
     * @return l'ensemble contenant les non-terminaux derivant epsilon directement ou indirectement
     */

    public Set<String> calc_deriv_eps() {
        //Initialiser epsilon_plus : au départ l'ensemble vide
        Set<String> epsilon_plus = new HashSet<String>();
        boolean epsilon_plus_grossit = true;

        //Tant que l'ensemble grossit
        while (epsilon_plus_grossit) {
            epsilon_plus_grossit = false;
            //Pour chaque règle de la grammaire
            for (Production p : lr) {
                epsilon_plus_grossit = epsilon_plus_prod(p, epsilon_plus);
            }
        }
        return epsilon_plus;
    }

    /**
     * Méthode chargée de calculer si une production dérive epsilon directement ou indirectement
     *
     * @param p            la production dont nous allons analyser les regles
     * @param epsilon_plus l'ensemble qui contient les non-terminaux dérivant epsilon directement ou indirectement
     * @return
     */
    public boolean epsilon_plus_prod(Production p, Set<String> epsilon_plus) {

        //Si la partie gauche n'est pas déjà dans epsilon_plus
        if (!epsilon_plus.contains(p.getNonTerm())) {
            /* La liste contenant toutes les regles de la production :*/
            List<String> l = p.getRegles();
            boolean[] AreAllInEpsilon = new boolean[l.size()];
            boolean isOk = true;
            //initialisation du tableau à true
            for (int cpt = 0; cpt < AreAllInEpsilon.length; cpt++) {
                AreAllInEpsilon[cpt] = true;
            }

            for (int i = 0; i < l.size(); i++) { //je parcours toutes les regles
                if (l.get(i).equals("ε")) {//si une regle est epsilon(chaine vide), j'ajoute la production et je sort
                    epsilon_plus.add(p.getNonTerm());
                    return true;
                } else {//Sinon je regarde si tous les non-terminaux de la partie droite sont dans epsilon_plus
                    char[] tab = l.get(i).toCharArray(); // Je transforme la regle que j'analyse en tableau de caractere
                    for (int j = 0; j < tab.length; j++) {
                        if (ln.contains(Character.toString(tab[j])) && epsilon_plus.contains(Character.toString(tab[j]))) {// Si c'est un non terminal et qu'il est dans epsilon_plus on continue
                            continue;
                        } else if (lt.contains(Character.toString(tab[j]))) {//Si c'est un terminal on continue
                            continue;
                        } else {//Sinon ce sont des non terminaux qui ne sont pas dans epsilon_plus
                            AreAllInEpsilon[i] = false;
                        }
                    }
                }
            }
            // On va maintenant vérifier que tous les symboles de la partie droite sont bien dans epsilon_plus
            for (int i = 0; i < AreAllInEpsilon.length; i++) {
                if (!AreAllInEpsilon[i]) {
                    isOk = false;
                }
            }

            //Si isOk est à true, tous les symboles de la partie droite appartiennet à epsilon_plus, donc on ajoute la partie gauche dans epsilon_plus
            //et on retourne true car epsilon_plus a grossit

            if (isOk) {
                epsilon_plus.add(p.getNonTerm());
                return true;
            } else {
                return false;
            }

        }
        //Si on arrive là, la partie gauche est déjà dans epsilon_plus
        return false;
    }

    /**
     * Méthode chargée de supprimer les e-prod dans la grammaire
     */
    public void supprimer_epsilon_prod() {

        Set<String> epsilon_plus = calc_deriv_eps();
        if (debug) {
            System.out.println("J'ai fini d'executer calc_deriv_eps");
        }
        for (int i = 0; i < lr.size(); i++) {
            Production p = lr.get(i);
            if (debug) {
                System.out.println("Je vais nettoyer la production :" + i);
            }
            lr.set(i, supprimer_epsilon_regle(p, epsilon_plus));
            if (debug) {
                System.out.println("J'ai fini de nettoyer la production :" + i);
            }
        }
        if (debug) {
            System.out.println("Je sort du for du supprimer_epsilon_regle");
        }
        gOrig.setR(lr);
        // Maintenant, si epsilon appartient au langage engendré par la grammaire
        if (gOrig.getContainEpsilon()) {
            //On crée un nouvel axiome distinct de l'axiome de la grammaire
            gOrig.addProd("S2", "ε|" + gOrig.getAxiome());
            gOrig.setAxiome("S2");
        }

    }

    /**
     * Méthode chargée de supprimer les e-prod dans une production
     *
     * @param p
     * @param epsilon_plus
     * @return
     */
    private Production supprimer_epsilon_regle(Production p, Set<String> epsilon_plus) {
        /* Contient les regles actuelles de la production */
        List<String> old_rules = p.getRegles();
        /* Contiendra les nouvelles regles de la production */
        List<String> new_rules = new ArrayList<>();
        /**
         * sigma_rules est construit de la manière suivante :
         * Pour chaque symbole non-terminal x de la partie droite d'une regle de old_rules,
         * tel que x appartient à epsilon_plus, on considère l'alternative :
         * garder x dans la partie droite, ou bien l'enlever.
         * sigma_rules est constitué de toute la combinatoire de ces alternatives, moins la chaine vide.
         * C'est à dire que si ε apparait dans la combinatoire, on ne la retient pas dans sigma_rules
         */
        List<String> sigma_rules = new ArrayList<>();
        sigma_rules.add("ε");

        for (int i = 0; i < old_rules.size(); i++) {

            char tab[] = old_rules.get(i).toCharArray();
            if (debug) {
                if (debug) {
                    System.out.println("Je suis rentré dans le premier for, je regarde la regle :" + i);
                }
            }
            for (int j = 0; j < tab.length; j++) {

                if (ln.contains(Character.toString(tab[j]))) { //Pour chaque symbole x de la partie droite de old_rule

                    if (epsilon_plus.contains(Character.toString(tab[j]))) {//si x appartient à epsilon_plus
                        //Je met dans sigma_rules la chaine de caractere sans x ...
                        String s = old_rules.get(i);
                        s = suppressionChar(s, j);
                        sigma_rules.add(s);
                        //... et avec x
                        if (!(sigma_rules.contains(old_rules.get(i)))) {
                            sigma_rules.add(old_rules.get(i));
                        }
                    } else {//Sinon je met la chaine+x dans sigma_rules
                        if (!(sigma_rules.contains(old_rules.get(i)))) {
                            sigma_rules.add(old_rules.get(i));
                        }
                    }


                }
            }//fin du parcours d'une regle

        }//fin  du parcours des old_rules

        /* On va maintenant créer les new_rules à partir de sigma rules : */
        /* Il suffit d'ajouter toutes les regles sauf celles de la forme X -> ε */

        for (int i = 0; i < sigma_rules.size(); i++) {
            if (!(sigma_rules.get(i).equals("ε"))) {
                new_rules.add(sigma_rules.get(i));
            }
        }
        Production newP = new Production();
        newP.setNonTerm(p.getNonTerm());
        newP.setRegles(new_rules);

        return newP;
    }

    ///////////////////////////////////// Partie suppression des renommages /////////////////////////////////////

    /**
     * Méthode chargée de mettre les ensembles de renommages dans une hashmap
     *
     * @return
     */
    public Hashtable<String, Set<String>> calc_ens_ren() {
        /* Ren est l'ensemble qui contient les variables en lesquelles chaque variable peut être renommé */
        Hashtable<String, Set<String>> ren = new Hashtable<>();
        //La key est le nom de la production, et la donnée associée la liste des variables en lequelles la clé peut être renommée

        for (Production p : lr) {
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
        Set<String> oldEns = new HashSet<>();
        Set<String> newEns = new HashSet<>();

        /* Première étape : Ren0 qui contient seulement le non Terminal de la production qu'on analyse  */
        newEns.add(p.getNonTerm());
        /* Etape suivante : on complete l'ensemble en parcourant chacune des productions de celui-ci à la recherche de nouvelles regles unitaires */
        while (!(oldEns.equals(newEns))) {
            oldEns = newEns;
            for (Production production : lr) {
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
        /*Avant de supprimer les renommages, on calcul les variables de renommage */
        Hashtable<String, Set<String>> EnsRen = calc_ens_ren();
        /* On supprime les regles unitaires */
        for (Production p : lr) {
            supprRegleUnitaire(p);
        }
        System.out.println("supprRenom : J'ai fini le premier for");
        System.out.println("Affichage des regles nettoyées :");
        for (Production p : lr) {
            System.out.println(p);
        }
        /* On complete les regles de chaque production, à partir des ensembles calculés */

        for (Production p : lr) {
            ajoutRegleRen(p, EnsRen.get(p.getNonTerm()));
        }
        System.out.println("supprRenom : j'ai fini le deuxieme for");

        for (Production p : lr) {
            System.out.println(p);
        }
    }


    /**
     * Une méthode chargée de supprimer les regles qui ne possèdent qu'un non-terminal dans une production
     *
     * @param p la production à nettoyer
     */
    private void supprRegleUnitaire(Production p) {
        List<String> l = p.getRegles();
        /* On parcourt toutes les regles de la production */
        for (int i = 0; i < l.size(); i++) {
            /* Si la regle qu'on est en train de lire est composé d'un seul non terminal... */
            if (l.get(i).length()==1) {
                /* On l'enleve */
                l.remove(i);
            }
        }
        /* On modifie la liste de regles de la production */
        p.setRegles(l);
    }

    /**
     * Une méthode chargée d'ajouter les regles manquantes à une production après suppression des renommages
     *
     * @param p      la production à compléter
     * @param ensRen
     */
    private void ajoutRegleRen(Production p, Set<String> ensRen) {
        /*On récupère les regles que possede actuellement la production */
        List<String> l = p.getRegles();

        List<String> listEnsRen = new ArrayList<>(ensRen);

        String variable = p.getNonTerm();

        for (String varRen : listEnsRen) {
            if (varRen.equals(variable)) {//Obligatoire car la production est contenue dans Ren0
                continue;
            } else {
                for (Production prodDeR : lr) {//Je cherche la production désignée dans listEnsRen dans la liste des production afin de récupérer les regles à ajouter
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



