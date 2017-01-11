package jz65_kl50.msg;

import java.util.UUID;

import common.AOurDataPacketAlgoCmd;
import common.IChatServer;
import common.msg.chat.IAddCmdMsg;

/**
 * The message send to chat server to notify to add the cmd 
 * @author zhouji, kejun liu
 *
 */
public class AddCmdMsg implements IAddCmdMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5415623674013907022L;
	private AOurDataPacketAlgoCmd<?,IChatServer> _cmd;
	private Class<?> _class;
	private UUID _id;

	/**
	 * The constructor of the message
	 * @param cmd new cmd
	 * @param rClass the type of cmd
	 * @param id the id of unprocess message before
	 */
	public AddCmdMsg(AOurDataPacketAlgoCmd<?,IChatServer> cmd, Class<?> rClass, UUID id) {
		_cmd = cmd;
		_class = rClass;
		_id = id;
	}

	@Override
	public AOurDataPacketAlgoCmd<?,IChatServer> getCmd() {
		// TODO Auto-generated method stub
		return _cmd;
	}

	@Override
	public Class<?> getClassIdx() {
		// TODO Auto-generated method stub
		return _class;
	}

	@Override
	public UUID getUUID() {
		// TODO Auto-generated method stub
		return _id;
	}

}
