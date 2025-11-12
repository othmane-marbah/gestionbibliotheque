package model;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import exceptions.EmpruntException;

import java.time.LocalDate;
import java.io.*;


public class EmpruntModel implements EmpruntModelInterface {
	private static ArrayList<Emprunt> tab1=new ArrayList<>();
	private String csvFileName="Emprunts.csv";
	
	@Override
	public void enregistrerEmprunt(Emprunt E) throws EmpruntException {
		if (E == null) {
			throw new EmpruntException(EmpruntException.EMPLRUNT_NUL);
	    }
		tab1.add(E);
		this.sauvegarderCSV();
	}
	public EmpruntModel() {
		super();
	}
	public EmpruntModel(String csvFileName) {
		super();
		this.csvFileName = csvFileName;
		this.LireCSV();
	}

	@Override
	public void consulterHistoriqueEmprunts(Utilisateur utilisateur) throws EmpruntException {
		if (utilisateur == null) {
            throw new EmpruntException(EmpruntException.UTILISATEUR_NUL); // Utilisation d'EmpruntException
        }
		System.out.println("Historique des emprunts de " + utilisateur.getNom() + ":");
		boolean empruntTrouve = false;
        for (Emprunt e : tab1) {
            if (e.getUtilisateur().getId()==utilisateur.getId()) {
                System.out.println(e);
                empruntTrouve = true;
            }
        }
        if (!empruntTrouve) {
            System.out.println("Aucun emprunt trouvé pour cet utilisateur.");
        }
		
	}
	
	public Emprunt rechercherParId(int id) throws EmpruntException {
		Optional<Emprunt> L=
				tab1.
				stream().
				filter(e->e.getId()==id).
				findFirst();
			    if (L.isPresent())
			    	return L.get();
			    else
			    	throw new EmpruntException(EmpruntException.EMPLRUNT_NON_TROUVE);
	}

	@Override
    public void prolongerPeriodePret(Emprunt e, int joursSupplementaires) throws EmpruntException {
        if (e == null) {
            throw new EmpruntException(EmpruntException.EMPLRUNT_NUL); // Utilisation d'EmpruntException
        }
        if (joursSupplementaires <= 0) {
            throw new EmpruntException(EmpruntException.JOURS_SUPPLEMENTAIRES_NEGATIFS); // EmpruntException
        }
        LocalDate dateRetourActuelle = e.getDateRetourPrevue();
        if (dateRetourActuelle.isBefore(LocalDate.now())) {
            throw new EmpruntException(EmpruntException.DATE_RETOUR_PASSE); // EmpruntException
        }
        LocalDate nouvelleDateRetour = dateRetourActuelle.plusDays(joursSupplementaires);
        e.setDateRetourPrevue(nouvelleDateRetour);
        this.sauvegarderCSV();
    }

	@Override
	public void sauvegarderCSV() {
		// TODO Auto-generated method stub
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(csvFileName));
			bw.write("Id;Utilisateur;Livre;DateEmprunt;DateRetour");
			for (int i=0;i<tab1.size();i++) {
				bw.newLine();
				bw.write(tab1.get(i).toString());
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public static ArrayList<Emprunt> getListeEmprunts() {
	    return new ArrayList<>(tab1); // Retourne une copie pour éviter la modification directe
	}
	@Override
	public void LireCSV() {
	    try (BufferedReader br = new BufferedReader(new FileReader(csvFileName))) {
	    	tab1.clear();
	        br.readLine(); // Ignore l'en-tête
	        String line;
	        while ((line = br.readLine()) != null) {
	            try {
	                String[] words = line.split(";");
	                UtilisateurModel utilisateurModel = new UtilisateurModel();
	                LivreModel livreModel = new LivreModel();
	                int empruntId = Integer.parseInt(words[0]);
	                int utilisateurId = Integer.parseInt(words[1]);
	                Utilisateur u = utilisateurModel.rechercherUtilisateurParId(utilisateurId);

	                String livretitre = words[2];
	                Livre l = livreModel.rechercherParTitre(livretitre);
	                LocalDate D1 = LocalDate.parse(words[3]);
	                LocalDate D2 = LocalDate.parse(words[4]);

	                Emprunt e = new Emprunt();
	                e.setId(empruntId);
	                e.setUtilisateur(u);
	                e.setLivre(l);
	                e.setDateEmprunt(D1);
	                e.setDateRetourPrevue(D2);
	                
	                tab1.add(e);
	            } catch (Exception ex) {
	                System.err.println("Erreur lors du traitement de la ligne : " + ex.getMessage());
	            }
	        }
	        System.out.println(tab1.toString());
	        br.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	public List<Emprunt> getEmpruntsParUtilisateur(int id) {
	    List<Emprunt> empruntsUtilisateur = new ArrayList<>();

	    // Parcourir tous les emprunts enregistrés
	    for (Emprunt emprunt : getListeEmprunts()) {
	        // Vérifier si l'utilisateur de l'emprunt correspond à l'utilisateur donné
	        if (emprunt.getUtilisateur().getId()==id) {
	            empruntsUtilisateur.add(emprunt);
	        }
	    }

	    return empruntsUtilisateur;
	}
	

}
