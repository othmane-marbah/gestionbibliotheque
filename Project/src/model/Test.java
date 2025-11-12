package model;

import controller.BibliothequeController;
import exceptions.EmpruntException;
import exceptions.UtilisateurException;

public class Test {

	public static void main(String[] args) throws UtilisateurException, EmpruntException {

		UtilisateurModel tabUtilisateur=new UtilisateurModel();
		//tabUtilisateur.sauvegarderCSV();
		tabUtilisateur.LireCSV();
		EmpruntModel tabEmprunt=new EmpruntModel();
		//tabEmprunt.sauvegarderCSV();
		tabEmprunt.LireCSV();
	    LivreModel tabLivre=new LivreModel();
	    //tabLivre.sauvegarderCSV();
	    tabLivre.LireCSV();
	    RetourModel tabRetour=new RetourModel();
	    //tabRetour.sauvegarderCSV();
	    tabRetour.LireCSV();
	    //EmpruntController empruntController = new EmpruntController();
		//UtilisateurController utilisateurController = new UtilisateurController();
        //LivreController livreController = new LivreController();
        //RetourController retourController = new RetourController();
	    BibliothequeController bibliothequeController = new BibliothequeController();
        bibliothequeController.afficherVue();
	}
}
