package model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import exceptions.UtilisateurException;
public class Utilisateur {
	    private int id;
	    private String nom;
	    private String email;
	    private String motDePasse;
	    private String role;
	    
		public Utilisateur() {
			super();
		}
		
		private int calculerNombreUtilisateurs(String cheminFichierUtilisateurs) {
		    int nombreUtilisateurs = 0;
		    try {
		        Map<String, String> utilisateursMap = new HashMap<>(); // ID -> Nom
		        BufferedReader utilisateurReader = new BufferedReader(new FileReader(cheminFichierUtilisateurs));
		        String line = utilisateurReader.readLine(); // Lire l'en-tête
		        while ((line = utilisateurReader.readLine()) != null) {
		            String[] values = line.split(";"); // Délimiteur: point-virgule
		            if (values.length < 2) continue; // Ignorer les lignes incorrectes
		            utilisateursMap.put(values[0].trim(), values[1].trim()); // ID et Nom
		        }
		        nombreUtilisateurs = utilisateursMap.size(); // Nombre d'utilisateurs uniques
		        utilisateurReader.close();
		    } catch (IOException e) {
		        e.printStackTrace(); // Gérer les erreurs
		    }
		    return nombreUtilisateurs;
		}

		public Utilisateur(String nom, String email, String motDePasse, String role) {
			super();
			this.id = (calculerNombreUtilisateurs("Utilisateurs.csv")+1);
			this.nom = nom;
			this.email = email;
			this.motDePasse = motDePasse;
			this.role = role;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) throws UtilisateurException {
			if (id <= 0) {
				throw UtilisateurException.idInvalide();
		    }
	        this.id = id;
		}

		public String getNom() {
			return nom;
		}

		public void setNom(String nom) throws UtilisateurException {
	        if (nom == null || nom.trim().isEmpty()) {
	            throw UtilisateurException.nomInvalide(); // Utilisation de l'exception
	        }
	        this.nom = nom;
	    }

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) throws UtilisateurException {
	        if (email == null || email.trim().isEmpty()) {
	            throw UtilisateurException.emailInvalide(); // Utilisation de l'exception
	        }
	        if (!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
	            throw UtilisateurException.emailFormatInvalide(); // Utilisation de l'exception
	        }
	        this.email = email;
	    }

		public String getMotDePasse() {
			return motDePasse;
		}

		public void setMotDePasse(String motDePasse) throws UtilisateurException {
	        if (motDePasse == null || motDePasse.trim().isEmpty()) {
	            throw UtilisateurException.motDePasseInvalide(); // Utilisation de l'exception
	        }
	        if (motDePasse.length() < 6) {
	            throw UtilisateurException.motDePasseTropCourt(); // Utilisation de l'exception
	        }
	        this.motDePasse = motDePasse;
	    }

		public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}

		@Override
		public String toString() {
			return id + ";" + nom + ";" + email + ";" + motDePasse + ";" + role;
		}
		
		
	    
	    

}
