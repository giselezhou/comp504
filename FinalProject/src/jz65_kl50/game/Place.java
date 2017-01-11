package jz65_kl50.game;


/**
 * the concrete class of APoint on the client side of game,
 * @author zhouji, kejun liu
 *
 */
public class Place extends APoint {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5577597361424021885L;

	private int _score;
	private double _latitude;
	private double _longitude;
	private boolean _selected = false;
	private int _index;
	// private Position _p;

	/**
	 * constuct a Place instance with a APoint object
	 * @param p
	 */
	public Place(APoint p) {
		_score = p.getScore();
		_latitude = p.getLatitude();
		_longitude = p.getLongitude();
		// _p = Position.fromDegrees(p.getLatitude(), p.getLongitude());
		_index = p.getIndex();
	}

	@Override
	public int getScore() {
		// TODO Auto-generated method stub
		return _score;
	}

	@Override
	public double getLatitude() {
		// TODO Auto-generated method stub
		return _latitude;
	}

	@Override
	public double getLongitude() {
		// TODO Auto-generated method stub
		return _longitude;
	}

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
		return _index;
	}

	/*
	 * public Position getPos() { return _p; }
	 */

}
