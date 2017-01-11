package jz65_kl50.game.client.msg;

import common.msg.IChatMsg;
import jz65_kl50.game.APoint;

/**
 * The message is sent to the server by player after the player chooses a scored point.
 * @author zhouji, kejun liu
 *
 */
public class PointScoreMsg implements IChatMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1932719988280187862L;

	/**
	 * the point is selected
	 */
	private APoint _selectedPoint;

	/** 
	 * get the selected point
	 * @return the selected point
	 */
	public APoint getSelectedPoint() {
		return _selectedPoint;
	}

	/**
	 * Constructor of the message
	 * @param selectedPoint the selected point
	 */
	public PointScoreMsg(APoint selectedPoint) {
		_selectedPoint = selectedPoint;
	}
}
