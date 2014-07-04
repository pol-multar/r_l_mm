package fr.unice.polytech.si3.lfa.mmultari.modele;

import java.util.List;
import java.util.Map;

/**
 * Created by mmultari on 04/07/14.
 */
public class Grammaire {
    private List alphabet;
    private Map <String,String> r;/* Ensemble fini de rêgles de réécriture, les productions */
    private List n;/* Ensemble de symboles non terminaux */
    private List t;/* Ensemble de symboles terminaux */
    private String s;/* L'axiome (symbole de départ) */

}
