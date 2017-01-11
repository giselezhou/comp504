package jz65_kl50.main.model;

import java.rmi.RemoteException;
import java.util.UUID;
import common.IUser;
import common.OurDataPacket;
import common.msg.IUserMsg;
import common.msg.user.IInvite2RoomMsg;
import jz65_kl50.main.AUser;
import jz65_kl50.msg.cmd.InviteMsgCmd;
import provided.datapacket.DataPacketAlgo;

/**
 * The concrete class of user
 * @author zhouji,kejun liu
 *
 */
public class User extends AUser {

	/**
	 * The algo the user uses to process message
	 */
	transient private DataPacketAlgo<Void, Object> _algo;
	/**
	 * The adapter from user to model
	 */
	transient private IUser2ModelAdapter _adpt;

	/**
	 * The constructor of user
	 * @param name the name of user
	 * @param id the id of user
	 * @param ip the ip address of user
	 * @param adpt adapter from user to model
	 */
	public User(String name, UUID id, String ip, IUser2ModelAdapter adpt) {
		super(name, id, ip);
		_adpt = adpt;
		initAlgo();
	}


	private void initAlgo() {
		_algo = new DataPacketAlgo<Void, Object>(null);
		_algo.setCmd(IInvite2RoomMsg.class, new InviteMsgCmd());
	}

	@Override
	public void connectBack(IUser userStub) throws RemoteException {
		// TODO Auto-generated method stub

	}

	@Override
	public <S> void receive(OurDataPacket<? extends IUserMsg, S> dp) throws RemoteException {
		dp.execute(_algo, _adpt);

	}

}
