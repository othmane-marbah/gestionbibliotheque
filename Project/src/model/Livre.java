package model;

import exceptions.LivreException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Livre {
    private int id;
    private String title;
    private String author;
    private int publicationYear;
    private String genre;
    private ArrayList<Emprunt> emprunts;

    public Livre() {
        super();
    }

    
    private int calculerNombreLivres(String cheminFichierLivres) {
        int nombreLivres = 0;
        try {
            Set<String> livres = new HashSet<>();
            BufferedReader livreReader = new BufferedReader(new FileReader(cheminFichierLivres));
            String line = livreReader.readLine(); // Lire l'en-tête
            while ((line = livreReader.readLine()) != null) {
                String[] values = line.split(";"); // Délimiteur: point-virgule
                if (values.length < 2) continue; // Ignorer les lignes incorrectes
                livres.add(values[1].trim()); // Ajouter le titre du livre
            }
            nombreLivres = livres.size(); // Nombre de livres uniques
            livreReader.close();
        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs
        }
        return nombreLivres;
    }
    
    public void ajouterEmprunt(Emprunt emprunt) {
        if (this.emprunts == null) {
            this.emprunts = new ArrayList<>();
        }
        this.emprunts.add(emprunt);
    }
    
    public Livre(String title, String author, int publicationYear, String genre) throws LivreException {
        super();
        this.id = (calculerNombreLivres("Livres.csv")+1);
        this.setTitle(title); // Utilise la méthode de validation
        this.setAuthor(author); // Utilise la méthode de validation
        this.setPublicationYear(publicationYear); // Utilise la méthode de validation
        this.setGenre(genre); // Utilise la méthode de validation
        this.emprunts = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) throws LivreException {
        if (id <= 0) {
            throw LivreException.idInvalide(); // Utilisation de l'exception
        }
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) throws LivreException {
        if (title == null || title.trim().isEmpty()) {
            throw LivreException.titreInvalide(); // Utilisation de l'exception
        }
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) throws LivreException {
        if (author == null || author.trim().isEmpty()) {
            throw LivreException.auteurInvalide(); // Utilisation de l'exception
        }
        this.author = author;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) throws LivreException {
        if (publicationYear < 1000 || publicationYear > 2025) {
            throw LivreException.anneePublicationInvalide(); // Utilisation de l'exception
        }
        this.publicationYear = publicationYear;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) throws LivreException {
        if (genre == null || genre.trim().isEmpty()) {
            throw LivreException.genreInvalide(); // Utilisation de l'exception
        }
        this.genre = genre;
    }

    @Override
    public String toString() {
        return id + ";" + title + ";" + author + ";" + publicationYear + ";" + genre;
    }

    public boolean isDisponible() {
        // Vérifier si la liste des emprunts est null ou vide
        if (emprunts == null || emprunts.isEmpty()) {
            System.out.println("Aucun emprunt associé au livre : disponible.");
            return true; // Livre disponible, aucun emprunt actif
        }

        // Parcourir tous les emprunts associés
        for (Emprunt emprunt : emprunts) {
            if (emprunt == null) {
                System.out.println("Emprunt null détecté : vérifier la base de données.");
                continue; // Ignorer les emprunts non valides
            }

            // Récupérer le retour associé à l'emprunt
            Retour retour = emprunt.getRetour();

            // Vérifications détaillées
            if (retour == null) {
                System.out.println("Aucun retour pour l'emprunt ID : " + emprunt.getId() + " => Livre non disponible.");
                return false; // Pas de retour associé, le livre est emprunté
            }

            // Vérifier si la date de retour effective est null ou passée
            if (retour.getDateRetourEffective() == null) {
                System.out.println("Retour non effectif pour l'emprunt ID : " + emprunt.getId() + " => Livre non disponible.");
                return false; // Retour non effectif, le livre est encore emprunté
            }

            // Si la date de retour effective est dans le futur, le livre est encore emprunté
            if (retour.getDateRetourEffective().isAfter(LocalDate.now())) {
                System.out.println("Retour prévu après la date actuelle pour l'emprunt ID : " + emprunt.getId() + " => Livre non disponible.");
                return false; // Le retour est prévu dans le futur, donc il est encore emprunté
            }
        }

        // Si aucun emprunt actif, le livre est disponible
        System.out.println("Tous les emprunts associés ont été retournés : livre disponible.");
        return true;
    }
}
