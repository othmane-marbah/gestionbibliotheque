package model;
import java.util.ArrayList;
import java.io.*;
import java.util.Optional;

import exceptions.UtilisateurException;

public class UtilisateurModel implements UtilisateurModelInterface {
	private ArrayList<Utilisateur> tab=new ArrayList<>();
	private String csvFileName="Utilisateurs.csv";
	
	public UtilisateurModel() {
		super();
	}

	public UtilisateurModel(String csvFileName){
		super();
		this.csvFileName = csvFileName;
		try {
			this.LireCSV();
		} catch (UtilisateurException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void ajouterUtilisateur(Utilisateur U) {
		if (U == null) {
	        throw new IllegalArgumentException("L'utilisateur ne peut pas être nul.");
	    }
		tab.add(U);
		this.sauvegarderCSV();
	}
	
	public ArrayList<Utilisateur> getListeUtilisateurs() {
	    return new ArrayList<>(tab); // Retourne une copie pour éviter la modification directe
	}
	
	@Override
	public Utilisateur rechercherUtilisateurParId(int id) throws UtilisateurException {
		if (id <= 0) {
	        throw new IllegalArgumentException("L'ID du livre doit être positif.");
	    }
		LireCSV();
		Optional<Utilisateur> U=
		tab.
		stream().
		filter(e->e.getId()==id).
		findFirst();
	    if (U.isPresent())
	    	return U.get();
	    return null;
	    }
	

	@Override
	public void supprimerUtilisateur(int id) throws UtilisateurException {
		Utilisateur U=rechercherUtilisateurParId(id);
		if(U!=null) {
			tab.remove(U);
			this.sauvegarderCSV();
		}
		else
			throw new IllegalStateException("L'utilisateur avec l'ID " + id + " n'existe pas.");
	}

	@Override
	public void modifierUtilisateur(int id, String nom, String email, String motDePasse, String Role) throws UtilisateurException {
		if (id <= 0) {
	        throw new IllegalArgumentException("L'ID du livre doit être positif.");
	    }
	    if (nom == null || nom.isEmpty() || email == null || email.isEmpty()) {
	        throw new IllegalArgumentException("Le nom et l'email doivent être valides.");
	    }
		Utilisateur U = null;
		try {
			U = rechercherUtilisateurParId(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if(U!=null)
			{
				U.setId(id);
				U.setNom(nom);
				U.setEmail(email);
				U.setMotDePasse(motDePasse);
				U.setRole(Role);
				this.sauvegarderCSV();
			}
			else
				throw new IllegalStateException("L'utilisateur avec l'ID " + id + " n'existe pas.");
		} catch (UtilisateurException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void listerUtilisateur() {
		if (tab.isEmpty()) {
	        System.out.println("Aucun utilisateur à afficher.");
	    } else {
	        System.out.println(tab);
	    }
	}

	@Override
	public void definirRole(int id, String role) throws UtilisateurException {
		if (role == null || role.isEmpty()) {
	        throw new IllegalArgumentException("Le rôle ne peut pas être nul ou vide.");
	    }
		Utilisateur U=rechercherUtilisateurParId(id);
		if (U != null) {
            U.setRole(role);
            this.sauvegarderCSV();
        }
		else
			throw new IllegalStateException("L'utilisateur avec l'ID " + id + " n'existe pas.");
        
	}

	@Override
	public void sauvegarderCSV() {
		// TODO Auto-generated method stub
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(csvFileName));
			bw.write("id;nom;email;motDePasse;role");
			for (int i=0;i<tab.size();i++) {
				bw.newLine();
				bw.write(tab.get(i).toString());
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void LireCSV() throws UtilisateurException {
		// TODO Auto-generated method stub
		try {
			BufferedReader br = new BufferedReader(new FileReader(csvFileName));
			br.readLine();
			String line;
			while((line=br.readLine())!=null) {
				String words[]=line.split(";");
				int id=Integer.parseInt(words[0]);
				String nom= words[1];
				String email = words[2];
				String motDePasse = words[3];
				String role = words[4];
				Utilisateur u = new Utilisateur();
				u.setId(id);
				u.setEmail(email);
				u.setMotDePasse(motDePasse);
				u.setNom(nom);
				u.setRole(role);
				tab.add(u);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
