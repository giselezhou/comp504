package jz65_kl50.util;


/**
 * Utility class that supplies  class routines for generating various random values
 */
public class Randomizer implements IRandomizer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1062654243240121942L;
	/**
	 * Singleton object of randomizer.
	 */
	public static Randomizer singleton = new Randomizer();

	private Randomizer() {
	}



	/**
	 * Returns a random integer greater than or equal to min and less than or equal to max.
	 * @param min The minimum allowed value
	 * @param max The maximum allowed value
	 * @return an int subject to the given bounds
	 */
	public int randomInt(int min, int max) {
		return (int) Math.floor((Math.random() * (1 + max - min)) + min);
	}

	/**
	 * Returns a random double greater than or equal to min and less than max.
	 * @param min The minimum allowed value
	 * @param max The maximum allowed value
	 * @return a double subject to the given bounds
	 */
	public double randomDouble(double min, double max) {
		return (Math.random() * (max - min)) + min;
	}

	
}
