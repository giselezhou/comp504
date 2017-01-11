package jz65_kl50.main.model;

import common.IChatServer;

/**
 * The adapter from main MVC to game lobby MVC
 * @author zhouji, kejun liu
 *
 */
public interface IMain2LobbyAdapter {
	/**
	 * this method is used when main MVC is closing and stopping its child game lobby MVC
	 */
	public void stop();

	/**
	 * get the stub of server of the game lobby
	 * @return the stub
	 */
	public IChatServer getStub();

}
