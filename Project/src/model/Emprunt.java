package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import exceptions.EmpruntException;

public class Emprunt {
    private int id;
    private Utilisateur utilisateur;
    private Livre livre;
    private LocalDate dateEmprunt;
    private LocalDate dateRetourPrevue;
    private Retour retour; // Référence à l'objet Retour (clé étrangère dans Retour)

    // Constructeurs, getters et setters

    public Retour getRetour() {
        return retour;
    }

    public void setRetour(Retour retour) {
        this.retour = retour;
    }

    public Emprunt() {
        super();
    }
    
    private int calculerNombreEmprunts(String cheminFichierEmprunts) {
	    int nombreEmprunts = 0;
	    try {
	        Map<String, String> EmpruntsMap = new HashMap<>(); // ID -> Nom
	        BufferedReader EmpruntReader = new BufferedReader(new FileReader(cheminFichierEmprunts));
	        String line = EmpruntReader.readLine(); // Lire l'en-tête
	        while ((line = EmpruntReader.readLine()) != null) {
	            String[] values = line.split(";"); // Délimiteur: point-virgule
	            if (values.length < 2) continue; // Ignorer les lignes incorrectes
	            EmpruntsMap.put(values[0].trim(), values[1].trim()); // ID et Nom
	        }
	        nombreEmprunts = EmpruntsMap.size(); // Nombre d'utilisateurs uniques
	        EmpruntReader.close();
	    } catch (IOException e) {
	        e.printStackTrace(); // Gérer les erreurs
	    }
	    return nombreEmprunts;
	}

    public Emprunt(Utilisateur utilisateur, Livre livre, LocalDate date1, LocalDate date2) throws EmpruntException {
        super();
        if (utilisateur == null) {
            throw new EmpruntException(EmpruntException.UTILISATEUR_NUL);
        }
        if (livre == null) {
            throw new EmpruntException(EmpruntException.LIVRE_NUL);
        }
        if (date1 == null || date2 == null) {
            throw new EmpruntException(EmpruntException.DATES_NULLES);
        }
        if (date2.isBefore(date1)) {
            throw new EmpruntException(EmpruntException.DATE_RETOUR_AVANT_EMPRUNT);
        }
        this.id = (calculerNombreEmprunts("Emprunts.csv")+1);
        this.utilisateur = utilisateur;
        this.livre = livre;
        this.dateEmprunt = date1;
        this.dateRetourPrevue = date2;
        livre.ajouterEmprunt(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws EmpruntException {
        if (id < 0) {
            throw new EmpruntException(EmpruntException.ID_NEGATIF);
        }
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) throws EmpruntException {
        if (utilisateur == null) {
            throw new EmpruntException(EmpruntException.UTILISATEUR_NUL);
        }
        this.utilisateur = utilisateur;
    }

    public Livre getLivre() {
        return livre;
    }

    public void setLivre(Livre livre) throws EmpruntException {
        if (livre == null) {
            throw new EmpruntException(EmpruntException.LIVRE_NUL);
        }
        this.livre = livre;
    }

    public LocalDate getDateEmprunt() {
        return dateEmprunt;
    }

    public void setDateEmprunt(LocalDate dateEmprunt) throws EmpruntException {
        if (dateEmprunt == null) {
            throw new EmpruntException(EmpruntException.DATES_NULLES);
        }
        if (dateEmprunt.isAfter(LocalDate.now())) {
            throw new EmpruntException(EmpruntException.DATE_EMPRUNT_DANS_FUTUR);
        }
        this.dateEmprunt = dateEmprunt;
    }

    public LocalDate getDateRetourPrevue() {
        return dateRetourPrevue;
    }

    public void setDateRetourPrevue(LocalDate dateRetourPrevue) throws EmpruntException {
        if (dateRetourPrevue == null) {
            throw new EmpruntException(EmpruntException.DATES_NULLES);
        }
        if (dateRetourPrevue.isBefore(dateEmprunt)) {
            throw new EmpruntException(EmpruntException.DATE_RETOUR_AVANT_EMPRUNT);
        }
        this.dateRetourPrevue = dateRetourPrevue;
    }

    @Override
    public String toString() {
        return getId() + ";" + utilisateur.getId() + ";" + livre.getTitle() + ";" + dateEmprunt + ";" + dateRetourPrevue;
    }
}
