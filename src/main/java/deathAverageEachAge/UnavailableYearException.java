package deathAverageEachAge;

public class UnavailableYearException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7319488551460294624L;

	@Override
	public String getMessage() {
		return "Available years are only from 1896 to 2009";
	}
}
