package jz65_kl50.game.client.model;

import java.io.Serializable;
import java.util.HashSet;

import jz65_kl50.game.APoint;
import jz65_kl50.game.IPlayer;
import jz65_kl50.game.Team;

/**
 * the adapter from local player to model
 * @author zhouji, kejun liu
 *
 */
public interface IPlayer2ModelApdater extends Serializable {

	/**
	 * append text massage to local
	 * 
	 * @param sender the sender of the message
	 * @param text the content of message
	 */
	public void sendText(IPlayer sender, String text);

	/**
	 * tell model to start game, providing the time of game round and set points
	 * @param time the time duration of game round
	 * @param pointSet set of points of this round
	 */
	public void startGame(long time, HashSet<APoint> pointSet);

	/**
	 * used each time a score point is pinned. 
	 * @param t the team who wins the point
	 * @param p the point 
	 */
	public void setTeamScore(Team t, APoint p);

	/**
	 *  telling model to stop game after receiving Stop Game Msg from server
	 * @param score the score of the team the player in
	 */
	public void stopGame(int score);

}
