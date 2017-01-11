package jz65_kl50.msg;

import common.IChatServer;
import common.msg.chat.IAddMeMsg;

/**
 * The message used to tell other chat server to add the message sender to chat server list
 * @author zhouji
 *
 */
public class AddMeMsg implements IAddMeMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5194234271557997751L;
	private IChatServer _server;
	public  AddMeMsg(IChatServer server){
		_server=server;
	}


}
