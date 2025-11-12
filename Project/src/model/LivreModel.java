package model;
import java.io.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import exceptions.LivreException;
public class LivreModel implements LivreModelInterface {
	private ArrayList<Livre> liste=new ArrayList<>();
	private String csvFileName = "Livres.csv";
	private ArrayList<Emprunt> emprunts; // Liste des emprunts pour ce livre
	public LivreModel() {
		super();
	}

	public LivreModel(String csvFileName) {
		super();
		this.csvFileName = csvFileName;
		this.LireCSV();
	}
	
	@Override
	public void ajouterLivre(Livre L) {
		if (L == null) {
	        throw new IllegalArgumentException("Le livre ne peut pas être nul.");
	    }
	    if (L.getTitle() == null || L.getTitle().trim().isEmpty()) {
	        throw new IllegalArgumentException("Le titre du livre ne peut pas être vide.");
	    }
	    if (L.getAuthor() == null || L.getAuthor().trim().isEmpty()) {
	        throw new IllegalArgumentException("L'auteur du livre ne peut pas être vide.");
	    }
		liste.add(L);
		this.sauvegarderCSV();
	}



	@Override
	public Livre rechercherParId(int id) {
//		for (Livre L:liste)
//			if(L.getId()==id)
//				return L;
//		return null;
	    Optional<Livre> L=
		liste.
		stream().
		filter(e->e.getId()==id).
		findFirst();
	    if (L.isPresent())
	    	return L.get();
	    else
	    	throw new IllegalArgumentException("Livre avec l'ID " + id + " non trouvé.");
	    }
	
	public Livre rechercherParTitre(String title) {
		if (title == null || title.trim().isEmpty()) {
	        throw new IllegalArgumentException("Le titre ne peut pas être vide.");
	    }
		System.out.println("Recherche du titre : '" + title.trim() + "'");
		LireCSV();
	    // Vérification de la taille de la liste
	    if (getListeLivres().isEmpty()) {
	        System.out.println("La liste des livres est vide.");
	    } else {
	        System.out.println("Titres dans la liste :");
	        getListeLivres().forEach(livre -> System.out.println("Titre disponible : '" + livre.getTitle() + "'"));
	    }

		 return getListeLivres().stream()
		            .filter(livre -> livre.getTitle().equalsIgnoreCase(title.trim())) // Comparaison insensible à la casse
		            .findFirst()
		            .orElseThrow(() -> new IllegalArgumentException("Livre avec le titre '" + title + "' non trouvé."));
	}
	
	public Livre rechercherParAuteur(String author) {
		if (author == null || author.trim().isEmpty()) {
	        throw new IllegalArgumentException("L'auteur ne peut pas être vide.");
	    }
		Optional<Livre> L = liste.stream()
                .filter(e -> e.getAuthor().equals(author))  // Comparaison correcte avec equals()
                .findFirst();
	    if (L.isPresent())
	    	return L.get();
	    else
	    	throw new IllegalArgumentException("Livre de l'auteur '" + author + "' non trouvé.");
	    }
	
	public Livre rechercherParGenre(String genre) {
		if (genre == null || genre.trim().isEmpty()) {
	        throw new IllegalArgumentException("Le genre ne peut pas être vide.");
	    }
	    Optional<Livre> L=
		liste.
		stream().
		filter(e->e.getGenre()==genre).
		findFirst();
	    if (L.isPresent())
	    	return L.get();
	    else
	    	throw new IllegalArgumentException("Livre avec le genre '" + genre + "' non trouvé.");
	    }
	
	@Override
	public void supprimerLivre(int id) {
		Livre L=rechercherParId(id);
		if(L!=null) {
			liste.remove(L);
			this.sauvegarderCSV();
		}
		else
			throw new IllegalArgumentException("Le livre avec l'ID " + id + " n'existe pas.");
	}

	@Override
	public void listerLivre() {
		System.out.println(liste);
		
	}
	

	@Override
    public void modifierLivre(int id, String nouveauTitle, String nouveauAuthor, int nouveauPyear, String nouveauGenre) {
        Livre L = rechercherParId(id);
        if (L != null) {
            if (nouveauTitle == null || nouveauTitle.trim().isEmpty()) {
                throw new IllegalArgumentException("Le titre ne peut pas être vide.");
            }
            if (nouveauAuthor == null || nouveauAuthor.trim().isEmpty()) {
                throw new IllegalArgumentException("L'auteur ne peut pas être vide.");
            }
            try {
				L.setTitle(nouveauTitle);
				L.setAuthor(nouveauAuthor);
				L.setPublicationYear(nouveauPyear);
				L.setGenre(nouveauGenre);
			} catch (LivreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            this.sauvegarderCSV();
        } else {
            throw new IllegalArgumentException("Le livre avec l'ID " + id + " n'existe pas.");
        }
    }

	public ArrayList<Livre> getListeLivres() {
	    return liste; // Retourne une copie pour éviter la modification directe
	}
	

	public ArrayList<Livre> RechercherLivres(String query){
		if(query == null || query.trim().isEmpty()) {
			return liste;
		}
		return (ArrayList<Livre>) liste.stream()
                .filter(livre -> 
                    String.valueOf(livre.getId()).contains(query) ||  // Search by ID
                    livre.getTitle().toLowerCase().contains(query.toLowerCase()) || // Search by title
                    livre.getAuthor().toLowerCase().contains(query.toLowerCase()) || // Search by author
                    livre.getGenre().toLowerCase().contains(query.toLowerCase()) || // Search by genre
                    String.valueOf(livre.getPublicationYear()).contains(query)) // Search by publication year
                .collect(Collectors.toList());

	}
	@Override
	public void sauvegarderCSV() {
		// TODO Auto-generated method stub
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(csvFileName));
			bw.write("id;title;author;publicationYear;genre");
			for (int i=0;i<liste.size();i++) {
				bw.newLine();
				bw.write(liste.get(i).toString());
			}
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	@Override
	public void LireCSV() {
		// TODO Auto-generated method stub
		try {
			BufferedReader br = new BufferedReader(new FileReader(csvFileName));
			br.readLine();
			String line;
			while((line=br.readLine())!=null) {
				String words[]=line.split(";");
				int id=Integer.parseInt(words[0]);
				String title= words[1];
				String author = words[2];
				int publicationYear = Integer.parseInt(words[3]);
				String genre = words[4];
				Livre l = new Livre();
				try {
					l.setId(id);
					l.setTitle(title);
					l.setAuthor(author);
					l.setPublicationYear(publicationYear);
					l.setGenre(genre);
				} catch (LivreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				liste.add(l);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public ArrayList<Emprunt> getEmprunts() {
		return emprunts;
	}

	public void setEmprunts(ArrayList<Emprunt> emprunts) {
		this.emprunts = emprunts;
	}
	
	    
	

}
