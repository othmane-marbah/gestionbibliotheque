package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import exceptions.RetourException;

public class Retour {
    private int id;
    private Emprunt emprunt;
    private LocalDate DateRetourEffective;
    private int Penalite;
    private String EtatRetour;

    public Retour() {
        super();
    }
    
    private int calculerNombreRetours(String cheminFichierRetours) {
	    int nombreRetours = 0;
	    try {
	        Map<String, String> retoursMap = new HashMap<>(); // ID -> Nom
	        BufferedReader retoursReader = new BufferedReader(new FileReader(cheminFichierRetours));
	        String line = retoursReader.readLine(); // Lire l'en-tête
	        while ((line = retoursReader.readLine()) != null) {
	            String[] values = line.split(";"); // Délimiteur: point-virgule
	            if (values.length < 2) continue; // Ignorer les lignes incorrectes
	            retoursMap.put(values[0].trim(), values[1].trim()); // ID et Nom
	        }
	        nombreRetours = retoursMap.size(); // Nombre d'utilisateurs uniques
	        retoursReader.close();
	    } catch (IOException e) {
	        e.printStackTrace(); // Gérer les erreurs
	    }
	    return nombreRetours;
	}

    public Retour(Emprunt emprunt, LocalDate dateRetourEffective) throws RetourException {
        super();
        this.id = (calculerNombreRetours("Retours.csv")+1);
        this.setEmprunt(emprunt); // Validate emprunt
        this.setDateRetourEffective(dateRetourEffective); // Validate date
        LocalDate dateRetourPrevue = emprunt.getDateRetourPrevue();
        long delay = ChronoUnit.DAYS.between(dateRetourPrevue, dateRetourEffective);
        if (delay > 0) {
            this.Penalite = (int) delay * 20; // Each day late costs 20
            this.EtatRetour = "retour tardif";
        } else {
            this.Penalite = 0;
            this.EtatRetour = "retourné avant délai";
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws RetourException {
        if (id <= 0) {
            throw new RetourException(RetourException.ID_NEGATIF);
        }
        this.id = id;
    }

    public Emprunt getEmprunt() {
        return emprunt;
    }

    public void setEmprunt(Emprunt emprunt) throws RetourException {
        if (emprunt == null) {
            throw new RetourException(RetourException.EMPRUNT_NUL);
        }
        this.emprunt = emprunt;
    }

    public LocalDate getDateRetourEffective() {
        return DateRetourEffective;
    }

    public void setDateRetourEffective(LocalDate dateRetourEffective) throws RetourException {
        if (dateRetourEffective == null) {
            throw new RetourException(RetourException.DATE_RETROU_NULLE);
        }
        if (dateRetourEffective.isBefore(emprunt.getDateEmprunt())) {
            throw new RetourException(RetourException.DATE_RETROU_INVALIDE);
        }
        this.DateRetourEffective = dateRetourEffective;
    }

    public int getPenalite() {
        return Penalite;
    }

    public void setPenalite(int penalite) throws RetourException {
        if (penalite < 0) {
            throw new RetourException(RetourException.PENALITE_NEGATIVE);
        }
        this.Penalite = penalite;
    }

    public String getEtatRetour() {
        return EtatRetour;
    }

    public void setEtatRetour(String etatRetour) throws RetourException {
        if (etatRetour == null || etatRetour.trim().isEmpty()) {
            throw new RetourException(RetourException.ETAT_RETROU_VIDE);
        }
        this.EtatRetour = etatRetour;
    }

    @Override
    public String toString() {
        return getId() + ";" + emprunt.getId() + ";" + DateRetourEffective + ";" + Penalite + ";" + EtatRetour;
    }
}
