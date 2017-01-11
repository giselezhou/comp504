package jz65_kl50.msg.cmd;

import common.ICmd2ModelAdapter;
import common.OurDataPacket;
import common.AOurDataPacketAlgoCmd;
import common.IChatServer;
import common.msg.chat.IAddCmdMsg;
import jz65_kl50.gamelobby.model.IChat2ModelAdapter;

/**
 * The cmd is used to process add cmd message
 * @author zhouji, kejun liu
 *
 */
public class AddCmd extends AOurDataPacketAlgoCmd<IAddCmdMsg, IChatServer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 563278050518491135L;
	private transient ICmd2ModelAdapter _adpt;

	@Override
	public Void apply(Class<?> index, OurDataPacket<IAddCmdMsg, IChatServer> host, Object... params) {

		IChat2ModelAdapter _toModel = (IChat2ModelAdapter) params[0];
		AOurDataPacketAlgoCmd<?, IChatServer> cmd = host.getData().getCmd();
		cmd.setCmd2ModelAdpt(_adpt);
		_toModel.setCmd(host.getData().getClassIdx(), host.getData().getCmd(), host.getData().getUUID());

		return null;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		_adpt = cmd2ModelAdpt;
	}

}
