package jz65_kl50.game.server.msg.cmd;

import common.AOurDataPacketAlgoCmd;
import common.ICmd2ModelAdapter;
import common.OurDataPacket;
import jz65_kl50.game.IServer;
import jz65_kl50.game.client.ctrl.GameController;
import jz65_kl50.game.server.msg.ReceiveGameMsg;

/**
 * The cmd processes the ReceiveGameMsg
 * @author zhouji,kejun liu
 *
 */
public class ReceiveGameCmd extends AOurDataPacketAlgoCmd<ReceiveGameMsg, IServer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7954341539888245904L;
	private transient ICmd2ModelAdapter _adpt;

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		_adpt = cmd2ModelAdpt;

	}

	@Override
	public Void apply(Class<?> index, OurDataPacket<ReceiveGameMsg, IServer> host, Object... params) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				ReceiveGameMsg data = host.getData();
				GameController ctrl = new GameController(data.getTeam(), host.getSender(), _adpt.getChatServer());
				ctrl.start();
			}

		}).start();
		return null;
	}

}
