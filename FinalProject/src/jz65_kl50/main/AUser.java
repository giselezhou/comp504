package jz65_kl50.main;

import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.UUID;

import common.IChatroom;
import common.IUser;
/**
 * An abstract class of IUser that overrides equals(), hashCode() method.
 * @author zhouji,kejun liu
 *
 */
public abstract class AUser implements IUser {

	/**
	 * The name of the user
	 */
	protected String _name;
	/**
	 * The Id of the user
	 */
	protected UUID _id;
	/**
	 * The ip address of user
	 */
	protected String _ip;
	/**
	 * The list of rooms the user is in
	 */
	protected HashSet<IChatroom> _roomList;

	/**
	 * The constructor of the abstract user
	 * @param name  name of the user
	 * @param id Id of the user
	 * @param ip ip address of user
	 */
	public AUser(String name, UUID id, String ip) {
		_name = name;
		_id = id;
		_ip = ip;
		_roomList = new HashSet<IChatroom>();
	}

	@Override
	public UUID getId() throws RemoteException {
		return _id;
	}

	@Override
	public String getName() throws RemoteException {
		return _name;
	}

	@Override
	public String getIP() throws RemoteException {
		return _ip;
	}

	@Override
	public HashSet<IChatroom> getChatrooms() throws RemoteException {
		return _roomList;
	}

	/**
	 * Override equals() method to simply compare hashcode
	 * @return  Equal if the hashcode are the same.  False otherwise.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (!o.getClass().equals(IUser.class))
			return false;
		IUser s = (IUser) o;
		return s.hashCode() == o.hashCode();
	}

	/**
	 * Override hashCode() method to add the hashCode of ID and ip 
	 * @return  the addition result
	 */
	@Override
	public int hashCode() {
		return _id.hashCode() + _ip.hashCode();
	}
}
