package jz65_kl50.game;

import java.rmi.RemoteException;

import common.IChatServer;
import provided.datapacket.DataPacketAlgo;


/**
 * the remote interface of game server
 * @author zhouji
 *
 */
public interface IServer extends IChatServer {



	/**
	 *  let the server add player's stub and remove its correspoding chatserver stub
	 * @param player the player stub to add
	 * @param toRemove the chatserver stub to remove
	 * @throws RemoteException
	 */
	public void connectBack(IPlayer player, IChatServer toRemove) throws RemoteException;

	
	/**
	 * set the server's algo
	 * @param _algo
	 * @throws RemoteException
	 */
	public void setAlgo(DataPacketAlgo<Void, Object> _algo) throws RemoteException;
}
