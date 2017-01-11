package jz65_kl50.game.server;

import jz65_kl50.game.APoint;

/**
 * A concrete class of APoint on the server side
 * @author zhouji
 *
 */
public class MyPoint extends APoint {

	/**
	 * score of this point
	 */
	private int _score;
	/**
	 * the latitude of this point
	 */
	private double _latitude;
	/**
	 * the longtide of this point
	 */
	private double _longitude;
	
	/**
	 * the status of this point selected or unselected, default false
	 */
	private boolean _selected = false;
	
	/**
	 * the index of this point on the map
	 */
	private int _index;

	public MyPoint(int score, double latitude, double longitude, int index) {
		_score = score;
		_latitude = latitude;
		_longitude = longitude;
		_index = index;
	}

	@Override
	public int getScore() {
		return _score;
	}

	@Override
	public double getLatitude() {
		return _latitude;
	}

	@Override
	public double getLongitude() {
		return _longitude;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7767725363913785471L;

	@Override
	public boolean selected() {
		// TODO Auto-generated method stub
		return _selected;
	}

	@Override
	public void setSelected(boolean b) {
		_selected = b;

	}

	@Override
	public int getIndex() {
		// TODO Auto-generated method stub
		return _index;
	}

}
