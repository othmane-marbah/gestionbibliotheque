package exceptions;

public class UtilisateurException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UtilisateurException(String message) {
        super(message);
    }

    public static UtilisateurException idInvalide() {
        return new UtilisateurException("L'ID de l'utilisateur doit être positif.");
    }

    public static UtilisateurException nomInvalide() {
        return new UtilisateurException("Le nom de l'utilisateur ne peut pas être null ou vide.");
    }

    public static UtilisateurException emailInvalide() {
        return new UtilisateurException("L'email de l'utilisateur ne peut pas être null ou vide.");
    }

    public static UtilisateurException emailFormatInvalide() {
        return new UtilisateurException("L'email n'est pas au bon format.");
    }

    public static UtilisateurException motDePasseInvalide() {
        return new UtilisateurException("Le mot de passe de l'utilisateur ne peut pas être null ou vide.");
    }

    public static UtilisateurException motDePasseTropCourt() {
        return new UtilisateurException("Le mot de passe de l'utilisateur doit contenir au moins 6 caractères.");
    }
}
