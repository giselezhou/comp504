package jz65_kl50.msg.cmd;


import common.IChatroom;
import common.ICmd2ModelAdapter;
import common.IUser;
import common.OurDataPacket;
import common.AOurDataPacketAlgoCmd;
import common.msg.user.IInvite2RoomMsg;
import jz65_kl50.main.model.IUser2ModelAdapter;

/**
 * The cmd is used to process invite 2 room message
 * @author zhouji, kejun liu
 *
 */
public class InviteMsgCmd extends AOurDataPacketAlgoCmd<IInvite2RoomMsg,IUser> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8213172779338011015L;

	transient private ICmd2ModelAdapter _adpt;
	
	
	@Override
	public Void apply(Class<?> index, OurDataPacket<IInvite2RoomMsg,IUser> host, Object... params) {
		IChatroom cr = host.getData().getChatroom();
		((IUser2ModelAdapter) (params[0])).createChatroom(cr);
		return null;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {

		_adpt = cmd2ModelAdpt;
	}


}
