package jz65_kl50.main;

import java.rmi.RemoteException;

import common.AChatServer;
import common.IChatroom;
import common.IUser;
import common.OurDataPacket;
import common.msg.IChatMsg;
import jz65_kl50.gamelobby.model.IChat2ModelAdapter;
import provided.datapacket.DataPacketAlgo;

/**
 * A concrete class of chatserver
 * @author zhouji
 *
 */
public class ChatServer extends AChatServer {

	private IUser _userStub;
	/**
	 * the chatroom the chatserver in
	 */
	private IChatroom _cr;
	/**
	 * the adapter from chatserver to model
	 */
	transient private IChat2ModelAdapter _adpt;
	/**
	 * the algo the server used to process message
	 */
	private DataPacketAlgo<Void, Object> _algo;
	// private transient BlockingQueue<OurDataPacket<IChatMsg>> _msgQueue;

	/**
	 * The constructor of chatserver
	 * @param cr the chatroom the chatserver in
	 * @param userStub the user stub of the server
	 * @param adpt the adapter from chatserver to model
	 * @param algo the algo the server used to process message
	 */
	public ChatServer(IChatroom cr, IUser userStub, IChat2ModelAdapter adpt, DataPacketAlgo<Void, Object> algo) {
		_cr = cr;
		_userStub = userStub;
		_adpt = adpt;
		_algo = algo;
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

	/**
	 * Set the algo for the chatserver to process message
	 * @param algo the 
	 */
	public void setAlgo(DataPacketAlgo<Void, Object> algo) {
		_algo = algo;
	}

	
	@Override
	public <S> void receive(OurDataPacket<? extends IChatMsg, S> dp) throws RemoteException {
		dp.execute(_algo, _adpt);

	}

}
