package jz65_kl50.main;

import java.util.HashSet;
import java.util.UUID;

import common.IChatServer;
import common.IChatroom;

/**
 * An abstract class of IChatroom that overrides equals(), hashCode(), toString() method.
 * @author zhouji,kejun liu
 *
 */
public abstract class AChatroom implements IChatroom {

	/**
	 * The Id of the chat room
	 */
	protected UUID _id;
	/**
	 * The name of the chat room
	 */
	protected String _name;
	/**
	 * The list of chatservers of the chat room
	 */
	protected HashSet<IChatServer> _stubList;
	/**
	 * The description of the chat room
	 */
	protected String _desc;
	

	/**
	 * serial id
	 */
	private static final long serialVersionUID = 7627011806867869677L;

	/**
	 * The constructor of chat room
	 * @param id Id of the chat room
	 * @param name name of the chat room
	 * @param desc description of the chat room
	 * @param stubList list of chatservers of the chat room
	 */
	public AChatroom(UUID id, String name, String desc, HashSet<IChatServer> stubList) {
		_id = id;
		_name = name;
		_desc = desc;
		_stubList = stubList;
	}

	@Override
	public UUID getId() {
		return _id;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public HashSet<IChatServer> getChatServers() {
		
		return _stubList;
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
		if (!o.getClass().equals(IChatroom.class))
			return false;
		IChatroom s = (IChatroom) o;
		if (s.hashCode() == o.hashCode())
			return true;
		else
			return false;
	}

	/**
	 * Override hashCode() method to add the hashCode of ID and name 
	 * @return  the addition result
	 */
	@Override
	public int hashCode() {
		return _id.hashCode() + _name.hashCode();
	}

	/**
	 * Override toString() method 
	 * @return the description, name and ID of the chatroom 
	 */
	@Override
	public String toString() {
		return _desc + this.getName() + "-" + this.getId();
	}
}
