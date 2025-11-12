package controller;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import exceptions.LivreException;
import model.Livre;
import model.LivreModel;
import view.LivreView;

public class LivreController {
    private LivreModel model = new LivreModel("Livres.csv"); // Initialisation directe
    private LivreView view = new LivreView();   // Initialisation directe

    public LivreController() {
        // Ajouter un listener sur le bouton "Ajouter"
        view.getAddButton().addActionListener(
                e -> {
					try {
						ajouterClick();
					} catch (LivreException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				});
     // ActionListener pour Modifier Button
        view.getEditButton().addActionListener(e -> modifierClick());

        // ActionListener pour Supprimer Button
        view.getDeleteButton().addActionListener(e -> supprimerClick());
        
        view.getFilterComboBox().addActionListener(e -> filtrerLivres());

        // Charger les livres et les afficher dans la table
        chargerLivres();
        view.getRechercheTextField().getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				rechercherLivres();

			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				rechercherLivres();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				// TODO Auto-generated method stub
				rechercherLivres();
			}});
    }

    public void ajouterClick() throws LivreException {
        // Récupérer les données saisies dans la vue
        String title = view.getTitleTextField().getText();
        String author = view.getAuthorTextField().getText();
        String genre = view.getGenreTextField().getText();

        try {
            int year = Integer.parseInt(view.getYearTextField().getText());

            // Créer un nouveau livre et l'ajouter au modèle
            Livre livre = new Livre(title, author, year, genre);
            model.ajouterLivre(livre);

            // Afficher un message de succès
            JOptionPane.showMessageDialog(view, "Le livre a été ajouté avec succès");

        } catch (NumberFormatException e1) {
            // Gestion des erreurs pour une année invalide
            JOptionPane.showMessageDialog(view, "L'année doit être un nombre valide", "Erreur", JOptionPane.ERROR_MESSAGE);

        } catch (IllegalArgumentException e1) {
            // Gestion des erreurs pour des données invalides dans le modèle
            JOptionPane.showMessageDialog(view, e1.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }

        // Lister les livres après ajout (si nécessaire, à adapter à la vue)
        chargerLivres();
        model.listerLivre();
        
    }
 // Modifier un livre
    private void modifierClick() {
        // Vérifiez si une ligne est sélectionnée
        int selectedRow = view.getLivresTable().getSelectedRow();
        if (selectedRow != -1) {
            try {
                // Récupérer l'ID du livre à partir du modèle de table
                int id = (int) view.getLivresTable().getValueAt(selectedRow, 0); // Supposons que l'ID est dans la colonne 0
                Livre livre = model.rechercherParId(id); // Recherchez le livre dans la base de données
                
                if (livre != null) {
                    // Création de la fenêtre de modification
                    JTextField titleField = new JTextField(livre.getTitle());
                    JTextField authorField = new JTextField(livre.getAuthor());
                    JTextField yearField = new JTextField(String.valueOf(livre.getPublicationYear()));
                    JTextField genreField = new JTextField(livre.getGenre());

                    Object[] message = {
                        "Titre :", titleField,
                        "Auteur :", authorField,
                        "Année de publication :", yearField,
                        "Genre :", genreField,
                    };

                    int option = JOptionPane.showConfirmDialog(
                        null, message, "Modifier le livre", JOptionPane.OK_CANCEL_OPTION);

                    if (option == JOptionPane.OK_OPTION) {
                        String nouveauTitle = titleField.getText().trim();
                        String nouveauAuthor = authorField.getText().trim();
                        int nouveauPyear;
                        try {
                            nouveauPyear = Integer.parseInt(yearField.getText().trim());
                        } catch (NumberFormatException e) {
                            throw new IllegalArgumentException("L'année de publication doit être un nombre valide.");
                        }
                        String nouveauGenre = genreField.getText().trim();

                        // Modifier le livre
                        model.modifierLivre(id, nouveauTitle, nouveauAuthor, nouveauPyear, nouveauGenre);

                        // Mettre à jour l'affichage
                        chargerLivres(); // Implémentez une méthode pour recharger les données de la table
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Le livre sélectionné n'existe pas.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(null, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Veuillez sélectionner un livre à modifier.", "Avertissement", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void rechercherLivres() {
    	String query = view.getRechercheTextField().getText().trim();
    	ArrayList<Livre> resultats=model.RechercherLivres(query);
    	DefaultTableModel tableModel = (DefaultTableModel) view.getLivresTable().getModel();
    	tableModel.setRowCount(0);
    	for (Livre livre : resultats) {
            tableModel.addRow(new Object[]{
                livre.getId(),
                livre.getTitle(),
                livre.getAuthor(),
                livre.getPublicationYear(),
                livre.getGenre()
            });
        }
    }
    

 // Supprimer un livre
    private void supprimerClick() {
        int selectedRow = view.getLivresTable().getSelectedRow();
        if (selectedRow != -1) {
            try {
                int id = (int) view.getLivresTable().getValueAt(selectedRow, 0);
                model.supprimerLivre(id);
                JOptionPane.showMessageDialog(view, "Le livre a été supprimé avec succès.");

                // Mettre à jour la table
                chargerLivres();

            } catch (Exception e) {
                JOptionPane.showMessageDialog(view, "Erreur lors de la suppression.");
            }
        } else {
            JOptionPane.showMessageDialog(view, "Veuillez sélectionner un livre à supprimer.");
        }
    }

    // Charger les livres depuis le modèle et les afficher dans la table
    private void chargerLivres() {
        ArrayList<Livre> livres = model.getListeLivres();
        DefaultTableModel tableModel = (DefaultTableModel) view.getLivresTable().getModel();

        // Effacer les données existantes dans la table
        tableModel.setRowCount(0);

        // Ajouter les nouvelles lignes à partir de la liste de livres
        for (Livre livre : livres) {
            tableModel.addRow(new Object[]{
                livre.getId(),
                livre.getTitle(),
                livre.getAuthor(),
                livre.getPublicationYear(),
                livre.getGenre()
            });
        }
    }
    
    private void filtrerLivres() {
        String filtre = (String) view.getFilterComboBox().getSelectedItem();
        String query = view.getRechercheTextField().getText().trim(); // Texte de recherche
        ArrayList<Livre> tousLesLivres = model.getListeLivres(); // Liste complète des livres
        ArrayList<Livre> livresFiltrés = new ArrayList<>();

        // Étape 1 : Recherche par titre ou auteur
        for (Livre livre : tousLesLivres) {
            if (livre.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                livre.getAuthor().toLowerCase().contains(query.toLowerCase())) {
                livresFiltrés.add(livre);
            }
        }

        // Étape 2 : Filtrage par disponibilité
        if ("Disponibles".equals(filtre)) {
        	System.out.println("Filtre sélectionné : " + filtre);
            livresFiltrés.removeIf(livre -> !livre.isDisponible()); // Garder uniquement les livres disponibles
        } else if ("Empruntés".equals(filtre)) {
        	System.out.println("Filtre sélectionné : " + filtre);
            livresFiltrés.removeIf(Livre::isDisponible); // Garder uniquement les livres empruntés
        }
        for (Livre livre : tousLesLivres) {
            System.out.println("Livre : " + livre.getTitle() + ", Disponible : " + livre.isDisponible());
        }

        // Mise à jour de la table
        DefaultTableModel tableModel = (DefaultTableModel) view.getLivresTable().getModel();
        tableModel.setRowCount(0);

        for (Livre livre : livresFiltrés) {
            tableModel.addRow(new Object[]{
                livre.getId(),
                livre.getTitle(),
                livre.getAuthor(),
                livre.getPublicationYear(),
                livre.getGenre()
            });
        }
    }





}