package controller;

import view.StatistiqueView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StatistiqueController {
    private StatistiqueView view = new StatistiqueView();

    public StatistiqueController() {

        // Mettre à jour les statistiques
        afficherStatistiques();

        // Ajouter un écouteur au bouton "Fermer"
        view.closeButtonListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fermerFenetre();
            }
        });
    }


	/**
     * Met à jour les statistiques dans la vue.
     */
    private void afficherStatistiques() {
        String livreLePlusEmprunte = null;
        String utilisateurLePlusActif = null;
        String nombreUtilisateurs = "0";
        String nombreLivres = "0";

        try {
            // Calculer le nombre total de livres
            Set<String> livres = new HashSet<>();
            BufferedReader livreReader = new BufferedReader(new FileReader("Livres.csv"));
            String line = livreReader.readLine(); // Lire l'en-tête
            while ((line = livreReader.readLine()) != null) {
                String[] values = line.split(";"); // Délimiteur: point-virgule
                if (values.length < 2) continue; // Ignorer les lignes incorrectes
                livres.add(values[1].trim()); // Ajouter le titre du livre
            }
            nombreLivres = String.valueOf(livres.size()); // Nombre de livres uniques
            livreReader.close();

            // Calculer le nombre total d'utilisateurs
            Map<String, String> utilisateursMap = new HashMap<>(); // ID -> Nom
            BufferedReader utilisateurReader = new BufferedReader(new FileReader("Utilisateurs.csv"));
            line = utilisateurReader.readLine(); // Lire l'en-tête
            while ((line = utilisateurReader.readLine()) != null) {
                String[] values = line.split(";"); // Délimiteur: point-virgule
                if (values.length < 2) continue; // Ignorer les lignes incorrectes
                utilisateursMap.put(values[0].trim(), values[1].trim()); // ID et Nom
            }
            nombreUtilisateurs = String.valueOf(utilisateursMap.size()); // Nombre d'utilisateurs uniques
            utilisateurReader.close();

            // Lire les emprunts pour calculer les statistiques
            Map<String, Integer> empruntsParLivre = new HashMap<>();
            Map<String, Integer> empruntsParUtilisateur = new HashMap<>();
            BufferedReader empruntsReader = new BufferedReader(new FileReader("Emprunts.csv"));
            line = empruntsReader.readLine(); // Lire l'en-tête
            while ((line = empruntsReader.readLine()) != null) {
                String[] values = line.split(";"); // Délimiteur: point-virgule
                if (values.length < 3) continue; // Ignorer les lignes incorrectes

                String utilisateurId = values[1].trim(); // ID de l'utilisateur
                String livreTitre = values[2].trim(); // Titre du livre

                // Compter les emprunts par livre
                empruntsParLivre.put(livreTitre, empruntsParLivre.getOrDefault(livreTitre, 0) + 1);

                // Compter les emprunts par utilisateur
                empruntsParUtilisateur.put(utilisateurId, empruntsParUtilisateur.getOrDefault(utilisateurId, 0) + 1);
            }
            empruntsReader.close();

            // Trouver le livre le plus emprunté
            int maxEmpruntsLivre = 0;
            for (Map.Entry<String, Integer> entry : empruntsParLivre.entrySet()) {
                if (entry.getValue() > maxEmpruntsLivre) {
                    maxEmpruntsLivre = entry.getValue();
                    livreLePlusEmprunte = entry.getKey();
                }
            }

            // Trouver l'utilisateur le plus actif
            int maxEmpruntsUtilisateur = 0;
            for (Map.Entry<String, Integer> entry : empruntsParUtilisateur.entrySet()) {
                if (entry.getValue() > maxEmpruntsUtilisateur) {
                    maxEmpruntsUtilisateur = entry.getValue();
                    utilisateurLePlusActif = utilisateursMap.get(entry.getKey()); // Convertir ID en nom
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace(); // Gérer les erreurs
        }

        // Mettre à jour les labels dans la vue
        view.getLivrelesplusempruntesField().setText(livreLePlusEmprunte != null ? livreLePlusEmprunte : "N/A");
        view.getUtilisateurplusactifsField().setText(utilisateurLePlusActif != null ? utilisateurLePlusActif : "N/A");
        view.getNombredutilisateursField().setText(nombreUtilisateurs);
        view.getNombredelivresField().setText(nombreLivres);
    }



    /**
     * Ferme la fenêtre de statistiques.
     */
    private void fermerFenetre() {
    	view.dispose();
    }
}
