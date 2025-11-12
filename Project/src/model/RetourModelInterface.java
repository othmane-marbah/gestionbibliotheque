package model;

import exceptions.EmpruntException;
import exceptions.RetourException;

public interface RetourModelInterface {
	public void ajouterRetour(Retour R) throws RetourException;
	public Retour rechercherParId(int id) throws RetourException;
	public void listerRetour();
	public void sauvegarderCSV();
	public void LireCSV() throws EmpruntException;
}
