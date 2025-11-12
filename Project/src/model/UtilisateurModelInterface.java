package model;

import exceptions.UtilisateurException;

public interface UtilisateurModelInterface {
	public void ajouterUtilisateur(Utilisateur U);
	public Utilisateur rechercherUtilisateurParId(int id) throws UtilisateurException;
	public void supprimerUtilisateur(int id) throws UtilisateurException;
	public void modifierUtilisateur(int id,String nom,String email,String motDePasse,String Role) throws UtilisateurException;
	public void listerUtilisateur();
	public void definirRole(int id, String role) throws UtilisateurException;
	public void sauvegarderCSV();
	public void LireCSV() throws UtilisateurException;

}
