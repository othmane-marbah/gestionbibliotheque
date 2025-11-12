package exceptions;

public class RetourException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Constants for error messages
    public static final String ID_NEGATIF = "L'ID du retour doit être positif.";
    public static final String EMPRUNT_NUL = "L'emprunt ne peut pas être nul.";
    public static final String DATE_RETROU_NULLE = "La date de retour effective ne peut pas être nulle.";
    public static final String DATE_RETROU_INVALIDE = "La date de retour effective ne peut pas être antérieure à la date de l'emprunt.";
    public static final String PENALITE_NEGATIVE = "La pénalité ne peut pas être négative.";
    public static final String ETAT_RETROU_VIDE = "L'état du retour ne peut pas être vide.";

    public RetourException(String message) {
        super(message);
    }

    // Additional constructor for allowing chained exceptions
    public RetourException(String message, Throwable cause) {
        super(message, cause);
    }
}
