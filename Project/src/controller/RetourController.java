package controller;

import model.Emprunt;
import model.EmpruntModel;
import model.Retour;
import model.RetourModel;
import view.RetourView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class RetourController {
    private RetourModel model = new RetourModel("Retours.csv");
    private RetourView view = new RetourView();

    public RetourController() {    
        view.getAddButton().addActionListener(e -> ajouterRetour());
        chargerEmprunts();
        chargerRetours();
    }
    
    private void chargerEmprunts() {
        List<Emprunt> emprunts = null;
		try {
			emprunts = EmpruntModel.getListeEmprunts();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Fetch from UtilisateurController
        view.setEmpruntOptions(emprunts);
    }

    public void ajouterRetour() {
        try {
            Emprunt emprunt = (Emprunt) view.getEmpruntComboBox().getSelectedItem();
            String dateRetourEffectivetext = view.getDateRetourEffectiveField().getText().trim();
            if (emprunt == null || dateRetourEffectivetext.isEmpty()) {
                throw new IllegalArgumentException("Tous les champs doivent être remplis.");
            }
            LocalDate dateRetourEffective = LocalDate.parse(dateRetourEffectivetext);
            Retour retour = new Retour(emprunt, dateRetourEffective);
            model.ajouterRetour(retour);

            JOptionPane.showMessageDialog(view, "Retour ajouté avec succès !");
            chargerRetours();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
    
 
    
    private void chargerRetours() {
        ArrayList<Retour> retours = model.getListeRetours();
        DefaultTableModel tableModel = (DefaultTableModel) view.getRetourTable().getModel();
        tableModel.setRowCount(0);

        for (Retour retour : retours) {
            tableModel.addRow(new Object[]{
            	retour.getId(),
            	retour.getEmprunt().getId(), // Display the user's name
            	retour.getDateRetourEffective(),    // Display the book's title
            	retour.getPenalite(),
            	retour.getEtatRetour()
            });
        }
    }
}
