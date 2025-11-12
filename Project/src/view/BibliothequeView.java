package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.BibliothequeController;
import exceptions.UtilisateurException;
public class BibliothequeView extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BibliothequeController controller;

    public BibliothequeView(BibliothequeController controller) {
        this.controller = controller;
        initUI();
    }

    private void initUI() {
        setTitle("Gestion de la Bibliothèque");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10));

        JButton btnEmprunt = new JButton("Gérer les Emprunts");
        JButton btnUtilisateur = new JButton("Gérer les Utilisateurs");
        JButton btnLivre = new JButton("Gérer les Livres");
        JButton btnRetour = new JButton("Gérer les Retours");
        JButton btnStatistique = new JButton("Statistiques de la bibliotheque");

        btnEmprunt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					controller.ouvrirEmpruntController();
				} catch (UtilisateurException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });

        btnUtilisateur.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.ouvrirUtilisateurController();
            }
        });

        btnLivre.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.ouvrirLivreController();
            }
        });

        btnRetour.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.ouvrirRetourController();
            }
        });
        
        btnStatistique.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		controller.ouvrirStatistiqueController();
        	}
        });

        panel.add(btnEmprunt);
        panel.add(btnUtilisateur);
        panel.add(btnLivre);
        panel.add(btnRetour);
        panel.add(btnStatistique);

        add(panel);
    }
}
