package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.Emprunt;
import model.Livre;
import model.Utilisateur;

import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class EmpruntView extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable empruntsTable;
    private JButton addButton;
    private JButton editButton;
    private JButton HistoriqueButton;
    private JComboBox<Utilisateur> utilisateurComboBox;
    private JComboBox<Livre> livreComboBox;
    
    private JTextField dateEmpruntField;
    private JTextField dateRetourPrevueField;

    public EmpruntView() {
        setTitle("Gestion des Emprunts");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Table
        empruntsTable = new JTable(new DefaultTableModel(
            new Object[]{"ID", "Utilisateur", "Livre", "Date Emprunt", "Date Retour Prévue"}, 0
        ));
        JScrollPane tableScrollPane = new JScrollPane(empruntsTable);

        // Form fields
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Détails de l'emprunt"));

        formPanel.add(new JLabel("Utilisateur:"));
        utilisateurComboBox = new JComboBox<>();
        formPanel.add(utilisateurComboBox);

        formPanel.add(new JLabel("Livre:"));
        livreComboBox = new JComboBox<>();
        formPanel.add(livreComboBox);

        formPanel.add(new JLabel("Date Emprunt (yyyy-MM-dd):"));
        dateEmpruntField = new JTextField();
        formPanel.add(dateEmpruntField);

        formPanel.add(new JLabel("Date Retour Prévue (yyyy-MM-dd):"));
        dateRetourPrevueField = new JTextField();
        formPanel.add(dateRetourPrevueField);

        // Buttons
        addButton = new JButton("Ajouter");
        editButton = new JButton("Prolonger");
        HistoriqueButton = new JButton("Historique");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(HistoriqueButton);

        // Layout
        setLayout(new BorderLayout());
        add(tableScrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public JTable getEmpruntsTable() {
        return empruntsTable;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getHistoriqueButton() {
        return HistoriqueButton;
    }

    public JComboBox<Utilisateur> getUtilisateurComboBox() {
        return utilisateurComboBox;
    }

    public JComboBox<Livre> getLivreComboBox() {
        return livreComboBox;
    }

    public JTextField getDateEmpruntField() {
        return dateEmpruntField;
    }

    public JTextField getDateRetourPrevueField() {
        return dateRetourPrevueField;
    }
    
    @SuppressWarnings("unchecked")
	public List<Emprunt> getEmpruntsParUtilisateur(Utilisateur utilisateur) {
        return ((Collection<Emprunt>) getEmpruntsTable()).stream()
                .filter(emprunt -> emprunt.getUtilisateur().equals(utilisateur))
                .collect(Collectors.toList());
    }

    public void setUtilisateurOptions(List<Utilisateur> utilisateurs) {
        utilisateurComboBox.removeAllItems();
        for (Utilisateur utilisateur : utilisateurs) {
            utilisateurComboBox.addItem(utilisateur);
        }
    }

    public void setLivreOptions(List<Livre> livres) {
        livreComboBox.removeAllItems();
        for (Livre livre : livres) {
            livreComboBox.addItem(livre);
        }
    }
    public void afficherTableHistorique(Utilisateur utilisateur, List<Emprunt> emprunts) {
        // Définir les colonnes
        String[] columnNames = {"ID", "Utilisateur", "Livre", "Date d'emprunt", "Date de retour prévue"};
        Object[][] data = new Object[emprunts.size()][columnNames.length];

        // Remplir les données pour chaque emprunt
        for (int i = 0; i < emprunts.size(); i++) {
            Emprunt emprunt = emprunts.get(i);
            data[i][0] = emprunt.getId(); // ID de l'emprunt
            data[i][1] = emprunt.getUtilisateur().getNom(); // Nom de l'utilisateur
            data[i][2] = emprunt.getLivre().getTitle(); // Titre du livre
            data[i][3] = emprunt.getDateEmprunt().toString(); // Date d'emprunt
            data[i][4] = emprunt.getDateRetourPrevue().toString(); // Date de retour prévue
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);

        // Afficher dans une nouvelle fenêtre
        JFrame frame = new JFrame("Historique des emprunts - " + utilisateur.getNom());
        frame.add(scrollPane);
        frame.setSize(800, 400); // Taille de la fenêtre
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

}
