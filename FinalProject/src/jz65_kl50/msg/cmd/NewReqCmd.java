package jz65_kl50.msg.cmd;

import java.rmi.RemoteException;

import common.AOurDataPacketAlgoCmd;
import common.IChatServer;
import common.ICmd2ModelAdapter;
import common.OurDataPacket;
import common.msg.chat.IAddCmdMsg;
import common.msg.chat.INewCmdReqMsg;
import jz65_kl50.gamelobby.model.IChat2ModelAdapter;
import jz65_kl50.msg.AddCmdMsg;

/**
 * This cmd is used to process INewCmdReqMsg
 * @author zhouji, kejun liu
 *
 */
public class NewReqCmd extends AOurDataPacketAlgoCmd<INewCmdReqMsg,IChatServer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4619618043657075637L;
	private transient ICmd2ModelAdapter _adpt;

	@Override
	public Void apply(Class<?> index, OurDataPacket<INewCmdReqMsg,IChatServer> host, Object... params) {
		AOurDataPacketAlgoCmd<?,IChatServer> newCmd = ((IChat2ModelAdapter) params[0]).getCmd(host.getData().getReqClassIdx());
		IAddCmdMsg msg = new AddCmdMsg(newCmd, host.getData().getReqClassIdx(), host.getData().getUUID());
		OurDataPacket<IAddCmdMsg,IChatServer> m=new OurDataPacket<IAddCmdMsg,IChatServer>(IAddCmdMsg.class,msg,((IChat2ModelAdapter) params[0]).getStub());
		try {
			host.getSender().receive(m);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		_adpt = cmd2ModelAdpt;
	}

}
