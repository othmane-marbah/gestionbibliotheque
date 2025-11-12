package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class UtilisateurView extends JFrame {
    private static final long serialVersionUID = 1L;

    // Labels
    private JLabel nomLabel = new JLabel("Nom complet");
    private JLabel emailLabel = new JLabel("Email");
    private JLabel motdepasseLabel = new JLabel("Mot de passe");
    private JLabel roleLabel = new JLabel("Role");

    // Text Fields
    private JTextField nomTextField = new JTextField(20);
    private JTextField emailTextField = new JTextField(20);
    private JTextField motdepasseTextField = new JTextField(4);
    private JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"membre", "bibliothécaire", "administrateur"});

    // Buttons
    private JButton addButton = new JButton("Ajouter");
    private JButton editButton = new JButton("Modifier");
    private JButton deleteButton = new JButton("Supprimer");

    // Table for displaying books
    private JTable utilisateursTable;

    public UtilisateurView() {
        this.setTitle("Gestion des Utilisateurs");
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
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Informations sur l'utilisateur"));
        formPanel.add(nomLabel);
        formPanel.add(nomTextField);
        formPanel.add(emailLabel);
        formPanel.add(emailTextField);
        formPanel.add(motdepasseLabel);
        formPanel.add(motdepasseTextField);
        formPanel.add(roleLabel);
        formPanel.add(roleComboBox);

        // Panel for buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Liste des utilisateurs"));
        tablePanel.add(new JScrollPane(utilisateursTable), BorderLayout.CENTER);

        // Main layout
        this.setLayout(new BorderLayout(10, 10));
        this.add(formPanel, BorderLayout.NORTH);
        this.add(tablePanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }
    private void initTable() {
        // Définir les noms des colonnes
        String[] columnNames = {"ID", "Nom", "Email", "Mot de Passe", "Role"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        utilisateursTable = new JTable(tableModel);
        utilisateursTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    public JTextField getNomTextField() {
        return nomTextField;
    }

    public JTextField getEmailTextField() {
        return emailTextField;
    }

    public JTextField getMotDePasseTextField() {
        return motdepasseTextField;
    }

    public JComboBox<String> getRoleComboBox() {
        return roleComboBox;
    }

    public void addAjouterButtonListener(java.awt.event.ActionListener listenAjouterButton) {
        addButton.addActionListener(listenAjouterButton);
    }

    public void resetFields() {
        nomTextField.setText("");
        emailTextField.setText("");
        motdepasseTextField.setText("");
        roleComboBox.setSelectedIndex(0);
    }
    public JButton getAddButton() {
        return addButton;
    }
    public JButton getDeleteButton() {
        return deleteButton;
    }
    public JButton getEditButton() {
        return editButton;
    }
    public JTable getUtilisateursTable() {
        return utilisateursTable;
    }
    
}
