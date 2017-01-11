package jz65_kl50.game.server.msg;

import common.msg.IChatMsg;

/**
 * The message is used when server notify player that game is over due to time limit or some team wins
 * @author zhouji, kejun liu
 *
 */
public class StopGameMsg implements IChatMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5849323625808131839L;

	/**
	 * The score of the team the player in
	 */
	private int _score;

	/**
	 * The constructor of this message
	 * @param score the score of the team the player in
	 */ 
	public StopGameMsg(int score) {
		_score = score;
	}

	/**
	 * get the score of the team the player in
	 * @return the score of the team the player in
	 */
	public int getScore() {
		return _score;
	}

}
