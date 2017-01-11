package jz65_kl50.msg.cmd;

import common.AOurDataPacketAlgoCmd;
import common.IChatServer;
import common.ICmd2ModelAdapter;
import common.OurDataPacket;
import common.msg.chat.IAddMeMsg;
import jz65_kl50.gamelobby.model.IChat2ModelAdapter;

/**
 * The message is used to process add me message
 * @author zhouji, kejun liu
 *
 */
public class AddMeCmd extends AOurDataPacketAlgoCmd<IAddMeMsg,IChatServer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4295902263070219438L;

	private transient ICmd2ModelAdapter _adpt;

	@Override
	public Void apply(Class<?> index, OurDataPacket<IAddMeMsg,IChatServer> host, Object... params) {
		

			IChat2ModelAdapter adpt=(IChat2ModelAdapter) params[0];
			adpt.addStub(host.getSender());
		
			return null;
	
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		_adpt = cmd2ModelAdpt;

	}

}
