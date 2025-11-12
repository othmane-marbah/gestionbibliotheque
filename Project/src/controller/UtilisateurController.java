package controller;

import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import exceptions.UtilisateurException;
import model.Utilisateur;
import model.UtilisateurModel;
import view.UtilisateurView;

public class UtilisateurController {
	private UtilisateurModel model = new UtilisateurModel("Utilisateurs.csv"); // Initialisation avec fichier CSV
    private UtilisateurView view = new UtilisateurView(); // Initialisation de la vue

    public UtilisateurController() {
        // Ajouter un listener pour le bouton "Ajouter"
        view.getAddButton().addActionListener(e -> ajouterUtilisateur());

        // Ajouter un listener pour le bouton "Modifier"
        view.getEditButton().addActionListener(e -> {
			try {
				modifierUtilisateur();
			} catch (UtilisateurException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

        // Ajouter un listener pour le bouton "Supprimer"
        view.getDeleteButton().addActionListener(e -> supprimerUtilisateur());

        // Charger les utilisateurs au démarrage
        chargerUtilisateurs();
    }

    public void ajouterUtilisateur() {
        try {
            // Récupérer les informations depuis la vue
            String nom = view.getNomTextField().getText();
            String email = view.getEmailTextField().getText();
            String motdepasse = view.getMotDePasseTextField().getText();
            String role = (String) view.getRoleComboBox().getSelectedItem();
            if (!role.equals("membre") && !role.equals("bibliothécaire") && !role.equals("administrateur")) {
                throw new IllegalArgumentException("Rôle invalide.");
            }


            // Vérification de la validité des données
            if (nom.trim().isEmpty() || email.trim().isEmpty() || motdepasse.trim().isEmpty()) {
                throw new IllegalArgumentException("Tous les champs doivent être remplis.");
            }

            // Création de l'utilisateur et ajout au modèle
            Utilisateur utilisateur = new Utilisateur(nom, email, motdepasse, role);
            model.ajouterUtilisateur(utilisateur);

            // Affichage du message de succès
            JOptionPane.showMessageDialog(view, "Utilisateur ajouté avec succès !");
            chargerUtilisateurs();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(view, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void modifierUtilisateur() throws UtilisateurException {
        int selectedRow = view.getUtilisateursTable().getSelectedRow();

        if (selectedRow != -1) {
            try {
                // Récupérer l'ID de l'utilisateur sélectionné
                int id = (int) view.getUtilisateursTable().getValueAt(selectedRow, 0);
                Utilisateur utilisateur = model.rechercherUtilisateurParId(id);

                if (utilisateur != null) {
                    // Fenêtre de modification
                    JTextField nomField = new JTextField(utilisateur.getNom());
                    JTextField emailField = new JTextField(utilisateur.getEmail());
                    JTextField motdepasseField = new JTextField(utilisateur.getMotDePasse());
                    JComboBox<String> roleField = new JComboBox<>(new String[]{"membre", "bibliothécaire", "administrateur"});
                    roleField.setSelectedItem(utilisateur.getRole());

                    Object[] message = {
                        "Nom :", nomField,
                        "Email :", emailField,
                        "Mot de passe :", motdepasseField,
                        "Role :", roleField,
                    };

                    int option = JOptionPane.showConfirmDialog(
                        null, message, "Modifier l'utilisateur", JOptionPane.OK_CANCEL_OPTION);

                    if (option == JOptionPane.OK_OPTION) {
                        String nouveauNom = nomField.getText().trim();
                        String nouveauEmail = emailField.getText().trim();
                        String nouveauMotdepasse = motdepasseField.getText().trim();
                        String nouveauRole = (String) roleField.getSelectedItem();

                        if (nouveauNom.isEmpty() || nouveauEmail.isEmpty() || nouveauMotdepasse.isEmpty()) {
                            throw new IllegalArgumentException("Tous les champs doivent être remplis.");
                        }
                        
                        if (!nouveauRole.equals("membre") && !nouveauRole.equals("bibliothécaire") && !nouveauRole.equals("administrateur")) {
                            throw new IllegalArgumentException("Rôle invalide.");
                        }

                        model.modifierUtilisateur(id, nouveauNom, nouveauEmail, nouveauMotdepasse, nouveauRole);
                        JOptionPane.showMessageDialog(view, "Utilisateur modifié avec succès !");
                        chargerUtilisateurs();
                    }
                } else {
                    JOptionPane.showMessageDialog(view, "Utilisateur introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(view, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un utilisateur à modifier.", "Avertissement", JOptionPane.WARNING_MESSAGE);
        }
    }

    public void supprimerUtilisateur() {
        int selectedRow = view.getUtilisateursTable().getSelectedRow();

        if (selectedRow != -1) {
            try {
                int id = (int) view.getUtilisateursTable().getValueAt(selectedRow, 0);
                model.supprimerUtilisateur(id);
                JOptionPane.showMessageDialog(view, "Utilisateur supprimé avec succès !");
                chargerUtilisateurs();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Erreur lors de la suppression : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un utilisateur à supprimer.", "Avertissement", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void chargerUtilisateurs() {
        ArrayList<Utilisateur> utilisateurs = model.getListeUtilisateurs();
        DefaultTableModel tableModel = (DefaultTableModel) view.getUtilisateursTable().getModel();
        tableModel.setRowCount(0);

        for (Utilisateur utilisateur : utilisateurs) {
            tableModel.addRow(new Object[]{
                utilisateur.getId(),
                utilisateur.getNom(),
                utilisateur.getEmail(),
                utilisateur.getMotDePasse(),
                utilisateur.getRole()
            });
        }
    }
}
