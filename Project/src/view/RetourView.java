package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.Emprunt;

import java.awt.*;
import java.util.List;

public class RetourView extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable retoursTable;
    private JButton addButton;
    private JComboBox<Emprunt> empruntComboBox;
    private JTextField dateRetourEffectiveField;

    public RetourView() {
        setTitle("Gestion des Retours");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Table
        retoursTable = new JTable(new DefaultTableModel(
            new Object[]{"ID", "Emprunt", "Date Retour", "Etat", "Pénalité"}, 0
        ));
        JScrollPane tableScrollPane = new JScrollPane(retoursTable);

        // Form fields
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Détails du retour"));

        formPanel.add(new JLabel("Emprunt:"));
        empruntComboBox = new JComboBox<>();
        formPanel.add(empruntComboBox);

        formPanel.add(new JLabel("Date Retour (yyyy-MM-dd):"));
        dateRetourEffectiveField = new JTextField();
        formPanel.add(dateRetourEffectiveField);

        // Buttons
        addButton = new JButton("Ajouter");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);

        // Layout
        setLayout(new BorderLayout());
        add(tableScrollPane, BorderLayout.CENTER);
        add(formPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }

    public JTable getRetourTable() {
        return retoursTable;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JComboBox<Emprunt> getEmpruntComboBox() {
        return empruntComboBox;
    }

    public JTextField getDateRetourEffectiveField() {
        return dateRetourEffectiveField;
    }

    public void setEmpruntOptions(List<Emprunt> emprunts) {
        empruntComboBox.removeAllItems();
        for (Emprunt emprunt : emprunts) {
            empruntComboBox.addItem(emprunt);
        }
    }

    private void initTable() {
        // Définir les noms des colonnes
        String[] columnNames = {"ID", "Emprunt", "Date Retour", "Etat", "Pénalité"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        retoursTable = new JTable(tableModel);
        retoursTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }


}
