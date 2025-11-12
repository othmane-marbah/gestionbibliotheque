package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class LivreView extends JFrame {
    private static final long serialVersionUID = 1L;

    // Labels
    private JLabel titleLabel = new JLabel("Titre du livre");
    private JLabel authorLabel = new JLabel("Auteur");
    private JLabel yearLabel = new JLabel("Année de publication");
    private JLabel genreLabel = new JLabel("Genre");
    private JLabel rechercheLabel = new JLabel("Recherche");
    
    // Text Fields
    private JTextField idTextField = new JTextField(20);
    private JTextField titleTextField = new JTextField(20);
    private JTextField authorTextField = new JTextField(20);
    private JTextField yearTextField = new JTextField(4);
    private JTextField genreTextField = new JTextField(15);
    private JTextField rechercheTextField = new JTextField(20);

    // Buttons
    private JComboBox<String>filterComboBox = new JComboBox<>(new String[]{"Tous", "Disponibles", "Empruntés"});
    private JButton addButton = new JButton("Ajouter");
    private JButton editButton = new JButton("Modifier");
    private JButton deleteButton = new JButton("Supprimer");

    // Table for displaying books
    private JTable livresTable;

    public LivreView() {
        this.setTitle("Gestion des Livres");
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);

        // Initialize components
        initTable();
        addComponents();

        this.setVisible(true);
    }

    private void addComponents() {
        // Panel for form fields
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Informations du Livre"));
        formPanel.add(titleLabel);
        formPanel.add(titleTextField);
        formPanel.add(authorLabel);
        formPanel.add(authorTextField);
        formPanel.add(yearLabel);
        formPanel.add(yearTextField);
        formPanel.add(genreLabel);
        formPanel.add(genreTextField);
        formPanel.add(rechercheLabel);
        formPanel.add(rechercheTextField);
        formPanel.add(filterComboBox);

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Liste des Livres"));
        tablePanel.add(new JScrollPane(livresTable), BorderLayout.CENTER);

        // Main layout
        this.setLayout(new BorderLayout(10, 10));
        this.add(formPanel, BorderLayout.NORTH);
        this.add(tablePanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }
    private void initTable() {
        // Définir les noms des colonnes
        String[] columnNames = {"ID", "Titre", "Auteur", "Année", "Genre"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        livresTable = new JTable(tableModel);
        livresTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    // Getters for components
    public JTextField getTitleTextField() {
        return titleTextField;
    }

    public JTextField getAuthorTextField() {
        return authorTextField;
    }

    public JTextField getYearTextField() {
        return yearTextField;
    }

    public JTextField getGenreTextField() {
        return genreTextField;
    }
    
    public JTextField getRechercheTextField() {
    	return rechercheTextField;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getEditButton() {
        return editButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }

    public JTable getLivresTable() {
        return livresTable;
    }
    
    public JComboBox<String> getFilterComboBox() {
        return filterComboBox;
    }
    

	public JTextField getIdTextField() {
		// TODO Auto-generated method stub
		return idTextField;
	}
}
