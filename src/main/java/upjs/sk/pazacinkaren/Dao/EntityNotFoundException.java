package upjs.sk.pazacinkaren.Dao;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 2839777726016759578L;

	public EntityNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public EntityNotFoundException(String message) {
		super(message);
	}
}