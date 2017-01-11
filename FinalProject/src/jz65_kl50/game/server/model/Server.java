package jz65_kl50.game.server.model;

import java.rmi.RemoteException;

import common.IChatServer;
import common.IChatroom;
import common.IUser;
import common.OurDataPacket;
import common.msg.IChatMsg;
import jz65_kl50.game.IPlayer;
import jz65_kl50.game.IServer;
import provided.datapacket.DataPacketAlgo;

/**
 * The game server remote class
 * @author zhouji, kejun liu
 *
 */
public class Server implements IServer {

	//public static final int BOUND_PORT = IChatServer.BOUND_PORT_SERVER;

	private IUser _userStub;
	private IChatroom _cr;
	/**
	 * The adapter from server to model
	 */
	private transient IServer2ModelAdapter _adpt;
	/**
	 * The server's algo
	 */
	private DataPacketAlgo<Void, Object> _algo;

	/**
	 * The constructor of server
	 * @param server the chat server of server
	 * @param adpt the adapter from server to model
	 */
	public Server(IChatServer server, IServer2ModelAdapter adpt) {

		try {
			_userStub = server.getUser();
			_cr = server.getChatroom();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		_adpt = adpt;
	}

	@Override
	public <S> void receive(OurDataPacket<? extends IChatMsg, S> dp) throws RemoteException {
		dp.execute(_algo, _adpt);

	}

	@Override
	public IUser getUser() throws RemoteException {
		// TODO Auto-generated method stub
		return _userStub;
	}

	@Override
	public IChatroom getChatroom() throws RemoteException {
		// TODO Auto-generated method stub
		return _cr;
	}

	@Override
	public void connectBack(IPlayer player, IChatServer toRemove) throws RemoteException {
		_adpt.setPlayer(player, toRemove);

	}

	/**
	 * used to set the algo for server
	 */
	public void setAlgo(DataPacketAlgo<Void, Object> algo) throws RemoteException {
		_algo = algo;

	}

}
