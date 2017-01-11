package jz65_kl50.msg.cmd;

import common.ICmd2ModelAdapter;
import common.OurDataPacket;
import common.AOurDataPacketAlgoCmd;
import common.IChatServer;
import common.msg.chat.ILeaveMsg;
import jz65_kl50.gamelobby.model.IChat2ModelAdapter;

/**
 * The cmd is used to process leave message
 * @author zhouji
 *
 */
public class LeaveMsgCmd extends AOurDataPacketAlgoCmd<ILeaveMsg,IChatServer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7667038076443415765L;
	private transient ICmd2ModelAdapter _adpt;
	@Override
	public Void apply(Class<?> index, OurDataPacket<ILeaveMsg,IChatServer> host, Object... params) {
		((IChat2ModelAdapter) params[0]).removeStub(host.getSender());
		return null;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		_adpt=cmd2ModelAdpt;
		
	}

}
