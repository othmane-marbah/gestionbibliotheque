package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Optional;

import exceptions.EmpruntException;
import exceptions.RetourException;

public class RetourModel implements RetourModelInterface {
    private ArrayList<Retour> tabRetour = new ArrayList<>();
    private String csvFileName = "Retours.csv";

    public RetourModel() {
        super();
    }

    public RetourModel(String csvFileName){
        super();
        this.csvFileName = csvFileName;
        try {
			this.LireCSV();
		} catch (EmpruntException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void ajouterRetour(Retour retour) throws RetourException {
        if (retour == null) {
            throw new RetourException(RetourException.ID_NEGATIF);  // Using the RetourException message
        }
        tabRetour.add(retour);
        this.sauvegarderCSV();
    }

    @Override
    public Retour rechercherParId(int id) throws RetourException {
        Optional<Retour> retour = tabRetour.stream()
                .filter(e -> e.getId() == id)
                .findFirst();
        
        if (retour.isPresent()) {
            return retour.get();
        } else {
            throw new RetourException("Retour avec l'ID " + id + " non trouvé.");
        }
    }

    public ArrayList<Retour> getRetourEmprunts() {
        return new ArrayList<>(tabRetour);  // Retourne une copie pour éviter la modification directe
    }

    @Override
    public void listerRetour() {
        System.out.println(tabRetour);
    }

    @Override
    public void sauvegarderCSV() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFileName))) {
            bw.write("id;EmpruntId;DateRetourEffective;Etat;Penalite");
            for (Retour retour : tabRetour) {
                bw.newLine();
                bw.write(retour.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void LireCSV() throws EmpruntException {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFileName))) {
            String line;
            br.readLine();  // Skip the header row
            
            while ((line = br.readLine()) != null) {
                String[] words = line.split(";");
                if (words.length != 5) {
                    throw new RetourException("Le format du fichier CSV est incorrect." + words.length + " " +words[0] + " " + words[1] + words[2] + words[3]);
                }

                try {
                    int id = Integer.parseInt(words[0]);
                    int empruntId = Integer.parseInt(words[1]);
                    LocalDate dateRetourEffective = LocalDate.parse(words[2]);
                    int penalite = Integer.parseInt(words[3]);
                    String etatRetour = words[4];

                    EmpruntModel empruntModel = new EmpruntModel();
                    Emprunt emprunt = empruntModel.rechercherParId(empruntId);

                    Retour retour = new Retour();
                    retour.setId(id);
                    retour.setEmprunt(emprunt);
                    retour.setDateRetourEffective(dateRetourEffective);
                    retour.setPenalite(penalite);
                    retour.setEtatRetour(etatRetour);

                    tabRetour.add(retour);

                } catch (NumberFormatException | DateTimeParseException e) {
                    throw new RetourException("Erreur de format dans les données CSV: " + e.getMessage());
                }
            }
        } catch (IOException | RetourException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Retour> getListeRetours() {
        return new ArrayList<>(tabRetour);  // Retourne une copie pour éviter la modification directe
    }
}
