package jz65_kl50.msg;

import common.IChatroom;
import common.msg.user.IInvite2RoomMsg;

/**
 * The message tells a user that is invited
 * @author zhouji, kejun liu
 *
 */
public class InviteMsg implements IInvite2RoomMsg {

	/**
	 * The constructor of the message
	 * @param cr the chat room to invite in
	 */
	public InviteMsg(IChatroom cr) {
		_cr=cr;
	}

	private IChatroom _cr;
	
	/**
	 * get the chat room that user is invited in
	 * @return the chat room
	 */
	public IChatroom getChatroom() {
		return _cr;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -6707254251604364194L;

	
}
