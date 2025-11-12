package exceptions;

public class LivreException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LivreException(String message) {
        super(message);
    }

    public static LivreException idInvalide() {
        return new LivreException("L'ID du livre doit être positif.");
    }

    public static LivreException titreInvalide() {
        return new LivreException("Le titre du livre ne peut pas être vide.");
    }

    public static LivreException auteurInvalide() {
        return new LivreException("L'auteur du livre ne peut pas être vide.");
    }

    public static LivreException anneePublicationInvalide() {
        return new LivreException("L'année de publication doit être une valeur valide (entre 1000 et 2025).");
    }

    public static LivreException genreInvalide() {
        return new LivreException("Le genre du livre ne peut pas être vide.");
    }
}
