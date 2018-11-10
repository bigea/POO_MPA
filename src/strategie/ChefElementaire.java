package strategie;

import gui2.Simulateur;

/**
 * Classe ChefElementaire
 *    Pemet de définir la stratégie globale
 */

public class ChefElementaire extends Chef {

    
    /*********************************************
    *
    * METHODES DE BASE
    */

    public ChefElementaire(Simulateur sim) {
        super(sim);
    }

    /*********************************************
    *
    * METHODES DE STRATEGIE
    */

    /* Méthode de stratégie
     * 		tant qu'il reste des incendies et des robots pas affectés, on fait la stratégie unitaire
     */
    public void strategie(){
        if (this.incendies.size()>0) {
            while(this.resteIncendiePasAffecte() && this.resteRobotPasAffecte()){
                this.strategieUnitaire();
            }
        } else {
            System.out.println("On a fini !");
        }
    }

}
