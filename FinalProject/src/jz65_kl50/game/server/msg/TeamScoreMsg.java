package jz65_kl50.game.server.msg;

import common.msg.IChatMsg;
import jz65_kl50.game.APoint;
import jz65_kl50.game.Team;

/**
 * Server uses this message to tell players that some team one a point.
 * @author zhouji, kejun liu
 *
 */
public class TeamScoreMsg implements IChatMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3015591870273769847L;
	/**
	 * The team wins the point
	 */
	private Team _t;
	/**
	 * The point team selects
	 */
	private APoint _p;

	/**
	 * get the team wins the point
	 * @return the team wins the point
	 */
	public Team getTeam() {
		return _t;
	}

	/**
	 * get the point team selects
	 * @return the point team selects
	 */
	public APoint getPoint() {
		return _p;
	}

	/**
	 * The constructor of this message
	 * @param p the point team selects
	 * @param t the team wins the point
	 */
	public TeamScoreMsg(APoint p, Team t) {
		_t = t;
		_p = p;
	}

}
