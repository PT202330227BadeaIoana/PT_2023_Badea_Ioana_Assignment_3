package businessLayer;

public interface Validator<T> {
	/**
	 * validarea obiectelor care vor fi introduse in baza de date.
	 * @param t tipul obiectului care va fi validat
	 */
	public void validate(T t);
}
