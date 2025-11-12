package controller;

import exceptions.UtilisateurException;
import view.BibliothequeView;

public class BibliothequeController {
    private BibliothequeView view;

    public BibliothequeController() {
        this.view = new BibliothequeView(this);
    }
    
    public void ouvrirStatistiqueController(){
        StatistiqueController statistiqueController = new StatistiqueController();
    }

    public void ouvrirEmpruntController() throws UtilisateurException {
        EmpruntController empruntController = new EmpruntController();
    }

    public void ouvrirUtilisateurController() {
        UtilisateurController utilisateurController = new UtilisateurController();
    }

    public void ouvrirLivreController() {
        LivreController livreController = new LivreController();
    }

    public void ouvrirRetourController() {
        RetourController retourController = new RetourController();
    }

    public void afficherVue() {
        this.view.setVisible(true);
    }
}
