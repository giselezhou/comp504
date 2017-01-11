package jz65_kl50.game.server.msg;

import common.msg.IChatMsg;
import jz65_kl50.game.IServer;
import jz65_kl50.game.Team;
import jz65_kl50.game.server.model.I2MainAdapter;

/**
 * The message used by server to notify players to receive game 
 * @author zhouji, kejun liu
 *
 */
public class ReceiveGameMsg implements IChatMsg {

	/**
	 * The adapter from game to main MVC
	 */
	private I2MainAdapter _serverAdpt;
	/**
	 * The team the player in
	 */
	private Team _team;
	/**
	 * The server stub
	 */
	private IServer _server;
	private static final long serialVersionUID = 3514483742009424165L;

	/**
	 * The constructor of this message
	 * @param server the server stub
	 * @param adpt the adapter to main MVC
	 */
	public ReceiveGameMsg(IServer server, I2MainAdapter adpt) {
		_serverAdpt = adpt;
		_server = server;

	}

	/**
	 * get the adapter to main MVC
	 * @return the adapter to main MVC
	 */
	public I2MainAdapter getServerAdpt() {
		return _serverAdpt;
	}

	/**
	 * set the team of the player
	 * @param t the team the player should be in
	 */
	public void setTeam(Team t) {
		_team = t;
	}

	/**
	 * get the team of the player
	 * @return the team the player in
	 */
	public Team getTeam() {
		return _team;
	}

	/**
	 * get the server stub
	 * @return the server stub
	 */
	public IServer getServer() {
		return _server;
	}

}
