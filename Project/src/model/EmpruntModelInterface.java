package model;
import java.util.List;

import exceptions.EmpruntException;

public interface EmpruntModelInterface {
    public void enregistrerEmprunt(Emprunt E) throws EmpruntException;
    public void consulterHistoriqueEmprunts(Utilisateur utilisateur) throws EmpruntException;
    public void prolongerPeriodePret(Emprunt e,int joursSupplementaires) throws EmpruntException;
	public void sauvegarderCSV();
	public void LireCSV();
	public Emprunt rechercherParId(int id) throws EmpruntException;
	public List<Emprunt> getEmpruntsParUtilisateur(int id);
}
