package jz65_kl50.util;

import java.io.Serializable;

/**
 * Interface to generate various kind of random object
 */
public interface IRandomizer extends Serializable{
	
	/**
	 * Returns a random integer greater than or equal to min and less than or equal to max.
	 * @param min The minimum allowed value
	 * @param max The maximum allowed value
	 * @return an int subject to the given bounds
	 */
	public int randomInt(int min, int max);

	/**
	 * Returns a random double greater than or equal to min and less than max.
	 * @param min The minimum allowed value
	 * @param max The maximum allowed value
	 * @return a double subject to the given bounds
	 */
	public double randomDouble(double min, double max);

}
