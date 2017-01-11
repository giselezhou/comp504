package jz65_kl50.game.client.msg;

import common.msg.IChatMsg;
import jz65_kl50.game.Team;

/**
 * 	the text message sending between players of the same team. Player sends this message to the game server then the server forwards this message to every player in this team.
 * @author zhouji, kejun liu
 *
 */
public class GameTextMsg implements IChatMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6424483328878789040L;

	private String _text;
	private Team _team;

	/**
	 * Constructor of the message
	 * @param content the content of the text message
	 * @param team the team this message belongs to
	 */
	public GameTextMsg(String content, Team team) {
		_text = content;
		_team = team;
	}

	/**
	 * get the context of this message
	 * @return the content
	 */
	public String getText() {

		return _text;
	}

	/**
	 * get the team of this message belongs to
	 * @return the team
	 */
	public Team getTeam() {
		return _team;
	}
}
