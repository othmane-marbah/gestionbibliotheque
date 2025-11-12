package controller;

import model.Emprunt;
import model.EmpruntModel;
import model.Livre;
import model.LivreModel;
import model.Utilisateur;
import model.UtilisateurModel;
import view.EmpruntView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import exceptions.UtilisateurException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpruntController {
    private EmpruntModel model = new EmpruntModel("Emprunts.csv");
    private EmpruntView view = new EmpruntView();
    private UtilisateurModel utilisateurModel;
    private LivreModel livreModel;

    public EmpruntController() throws UtilisateurException {
    	utilisateurModel = new UtilisateurModel("Utilisateurs.csv");
    	livreModel = new LivreModel("Livres.csv");
        // Populate combo boxes
        chargerUtilisateurs();
        chargerLivres();
        
        // Add button listeners
        view.getAddButton().addActionListener(e -> ajouterEmprunt());
        view.getEditButton().addActionListener(e -> modifierEmprunt());
        view.getHistoriqueButton().addActionListener(e -> afficherHistorique());


        // Load emprunts on startup
        chargerEmprunts();

    }
    private void chargerUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurModel.getListeUtilisateurs(); // Fetch from UtilisateurController
        view.setUtilisateurOptions(utilisateurs);
    }

    // Similar method to load books (livres)
    private void chargerLivres() {
        // Assuming you have a LivreController, you could use it similarly
        List<Livre> livres = livreModel.getListeLivres(); // Fetch from LivreController
        view.setLivreOptions(livres);
    }

    public void ajouterEmprunt() {
        try {
            Utilisateur utilisateur = (Utilisateur) view.getUtilisateurComboBox().getSelectedItem();
            Livre livre = (Livre) view.getLivreComboBox().getSelectedItem();
            String dateEmprunt = view.getDateEmpruntField().getText().trim();
            String dateRetourPrevue = view.getDateRetourPrevueField().getText().trim();

            if (utilisateur == null || livre == null || dateEmprunt.isEmpty() || dateRetourPrevue.isEmpty()) {
                throw new IllegalArgumentException("Tous les champs doivent être remplis.");
            }

            Emprunt emprunt = new Emprunt(utilisateur, livre, LocalDate.parse(dateEmprunt), LocalDate.parse(dateRetourPrevue));
            model.enregistrerEmprunt(emprunt);

            JOptionPane.showMessageDialog(view, "Emprunt ajouté avec succès !");
            chargerEmprunts();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void modifierEmprunt() {
        // Vérifiez si une ligne est sélectionnée
        int selectedRow = view.getEmpruntsTable().getSelectedRow();
        if (selectedRow != -1) {
            try {
                // Récupérer l'ID du livre à partir du modèle de table
                int id = (int) view.getEmpruntsTable().getValueAt(selectedRow, 0); // Supposons que l'ID est dans la colonne 0
                Emprunt emprunt = model.rechercherParId(id); // Recherchez le livre dans la base de données
                
                if (emprunt != null) {
                	JTextField dateRetourPrevueField = new JTextField();
                	Object[] message = {
                            "Prolongation (nombre de jours) :", dateRetourPrevueField,
                        };
                	int option = JOptionPane.showConfirmDialog(
                            null, message, "Modifier l'emprunt", JOptionPane.OK_CANCEL_OPTION);

                        // Si l'utilisateur clique sur "OK"
                        if (option == JOptionPane.OK_OPTION) {
                            try {
                                String text = dateRetourPrevueField.getText().trim();
                                int nbr = Integer.parseInt(text); // Convertit le texte en entier
                                
                                // Valider l'entrée
                                if (nbr <= 0) {
                                    throw new IllegalArgumentException("Veuillez entrer un nombre positif.");
                                }

                                // Prolonger la période de prêt
                                model.prolongerPeriodePret(emprunt, nbr);

                                // Mettre à jour l'affichage
                                chargerEmprunts();
                                JOptionPane.showMessageDialog(null, "Emprunt modifié avec succès !");
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "Veuillez entrer un nombre valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
                            } catch (IllegalArgumentException e) {
                                JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "L'emprunt sélectionné n'existe pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Une erreur s'est produite : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Veuillez sélectionner un emprunt à modifier.", "Avertissement", JOptionPane.WARNING_MESSAGE);
            }
        }

    private void afficherHistorique() {
        try {
            // Récupérer la liste des utilisateurs
            List<Utilisateur> utilisateurs = utilisateurModel.getListeUtilisateurs();
            
            // Vérifiez si la liste est vide
            if (utilisateurs.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Aucun utilisateur trouvé.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Créer une liste déroulante pour sélectionner un utilisateur
            JComboBox<Utilisateur> utilisateurComboBox = new JComboBox<>(utilisateurs.toArray(new Utilisateur[0]));
            Object[] message = {"Sélectionnez un utilisateur :", utilisateurComboBox};

            // Afficher le dialogue
            int option = JOptionPane.showConfirmDialog(view, message, "Historique des emprunts", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                // Récupérer l'utilisateur sélectionné
                Utilisateur utilisateur = (Utilisateur) utilisateurComboBox.getSelectedItem();
                if (utilisateur != null) {
                    // Récupérer les emprunts de cet utilisateur
                    List<Emprunt> emprunts = model.getEmpruntsParUtilisateur(utilisateur.getId());

                    // Vérifiez si des emprunts existent
                    if (emprunts.isEmpty()) {
                        JOptionPane.showMessageDialog(view, "Cet utilisateur n'a effectué aucun emprunt.", "Information", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        // Afficher les emprunts dans une table
                        view.afficherTableHistorique(utilisateur, emprunts);
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Une erreur s'est produite : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    
    private void chargerEmprunts() {
    	ArrayList<Emprunt> emprunts = EmpruntModel.getListeEmprunts();

        // Récupérez le modèle de la table
        DefaultTableModel tableModel = (DefaultTableModel) view.getEmpruntsTable().getModel();
        
        // Videz la table (ligne précédente + réinitialisation)
        tableModel.setRowCount(0); 

        for (Emprunt emprunt : emprunts) {
            tableModel.addRow(new Object[]{
            	emprunt.getId(),
                emprunt.getUtilisateur().getNom(), // Display the user's name
                emprunt.getLivre().getTitle(),    // Display the book's title
                emprunt.getDateEmprunt(),
                emprunt.getDateRetourPrevue()
            });
        }
    }
}
