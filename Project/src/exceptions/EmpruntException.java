package exceptions;

public class EmpruntException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Constructeur pour l'exception avec un message
    public EmpruntException(String message) {
        super(message);
    }

    // Messages d'exception que nous utiliserons dans la classe Emprunt
    public static final String UTILISATEUR_NUL = "L'utilisateur ne peut pas être nul.";
    public static final String LIVRE_NUL = "Le livre ne peut pas être nul.";
    public static final String DATES_NULLES = "Les dates ne peuvent pas être nulles.";
    public static final String DATE_RETOUR_AVANT_EMPRUNT = "La date de retour prévue ne peut pas être antérieure à la date d'emprunt.";
    public static final String ID_NEGATIF = "L'ID de l'emprunt doit être positif.";
    public static final String DATE_EMPRUNT_DANS_FUTUR = "La date d'emprunt ne peut pas être dans le futur.";
    public static final String EMPLRUNT_NUL = "L'emprunt ne peut pas être nul.";
    public static final String EMPLRUNT_NON_TROUVE = "L'emprunt avec l'ID donné n'a pas été trouvé.";
    public static final String JOURS_SUPPLEMENTAIRES_NEGATIFS = "Le nombre de jours supplémentaires doit être positif.";
    public static final String DATE_RETOUR_PASSE = "La date de retour actuelle ne peut pas être dans le passé.";
}

