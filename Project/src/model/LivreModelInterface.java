package model;


import exceptions.LivreException;

public interface LivreModelInterface {
	public void ajouterLivre(Livre L);
	public Livre rechercherParId(int id);
	public void supprimerLivre(int id);
	public void modifierLivre(int id,String nouveauTitle,String nouveauAuthor,int nouveauPyear,String nouveauGenre);
	public void listerLivre();
	public void sauvegarderCSV();
	public void LireCSV() throws LivreException;
}
