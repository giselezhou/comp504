package jz65_kl50.game;

import java.rmi.RemoteException;

import common.IChatServer;

/**
 * Player interface represents a player
 * @author zhouji, kejun liu
 *
 */
public interface IPlayer extends IChatServer {

	
	/**
	 * get the team this player in
	 * @return the team this player in
	 * @throws RemoteException
	 */
	public Team getTeam() throws RemoteException;
}
