package jz65_kl50.game;

import java.io.Serializable;

import jz65_kl50.game.server.MyPoint;
/**
 * An abstract class represents points on the map using the latitude and longitude to compare if two points are the same point
 * @author zhouji, kejun liu
 *
 */
public abstract class APoint implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -459005251293318769L;

	@Override
	public String toString() {
		return "MyPoint [_score=" + this.getScore() + ", _latitude=" + this.getLatitude() + ", _longitude="
				+ this.getLongitude() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(this.getLatitude());
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(this.getLongitude());
		result = prime * result + (int) (temp ^ (temp >>> 32));

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (!(obj instanceof MyPoint))
			return false;
		else {
			MyPoint other = (MyPoint) obj;
			if (Double.doubleToLongBits(this.getLatitude()) != Double.doubleToLongBits(other.getLatitude()))
				return false;
			if (Double.doubleToLongBits(this.getLongitude()) != Double.doubleToLongBits(other.getLongitude()))
				return false;
		}
		return true;
	}

	/**
	 * get the score of this point
	 * @return the score of this point
	 */
	abstract public int getScore();

	/**
	 * get the latitude of this point 
	 * @return the latitude of this point
	 */
	abstract public double getLatitude();
	
	/**
	 * get the longitude of this point 
	 * @return the longitude of this point
	 */
	abstract public double getLongitude();

	/**
	 * get the if the point has been selected
	 * @return true if if this point has score and has been selected by other player otherwise false
	 */
	abstract public boolean selected();

	/**
	 * set the selection status of the point
	 * @param b
	 */
	abstract public void setSelected(boolean b);
	
	/**
	 * every point has an index shown on the map, get the index of this point
	 * @return the index of this point
	 */
	abstract public int getIndex();
}
