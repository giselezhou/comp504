package jz65_kl50.game.server.msg;

import java.util.HashSet;

import common.msg.IChatMsg;
import jz65_kl50.game.APoint;

/**
 * The message server uses to notify players to start game
 * @author zhouji, kejun liu
 *
 */
public class StartGameMsg implements IChatMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5407608421756178802L;
	/**
	 * The time limit of game round
	 */
	private long _duration;
	/**
	 * The set of points of this round
	 */
	private HashSet<APoint> _pointSet;

	/**
	 * get the set of points in this round
	 * @return the set of points
	 */
	public HashSet<APoint> getPointSet() {
		return _pointSet;
	}

	/**
	 * The constructor of this message
	 * @param duration the time limit
	 * @param pointSet the set of points
	 */
	public StartGameMsg(long duration, HashSet<APoint> pointSet) {
		_duration = duration;
		_pointSet = pointSet;
	}

	/**
	 * get the time limit of this round
	 * @return the time limit of this round
	 */
	public long getDuration() {
		return _duration;
	}

}
