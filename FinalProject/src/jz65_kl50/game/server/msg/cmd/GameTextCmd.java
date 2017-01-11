package jz65_kl50.game.server.msg.cmd;

import common.AOurDataPacketAlgoCmd;
import common.ICmd2ModelAdapter;
import common.OurDataPacket;
import jz65_kl50.game.IPlayer;
import jz65_kl50.game.client.msg.GameTextMsg;
import jz65_kl50.game.server.model.IServer2ModelAdapter;
/**
 * The cmd processes the GameTextMsg
 * @author zhouji,kejun liu
 *
 */
public class GameTextCmd extends AOurDataPacketAlgoCmd<GameTextMsg, IPlayer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5811940176782374511L;

	@Override
	public Void apply(Class<?> index, OurDataPacket<GameTextMsg, IPlayer> host, Object... params) {
		IServer2ModelAdapter _adpt = (IServer2ModelAdapter) params[0];
		_adpt.sendText(host.getSender(), host.getData().getText());
		return null;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub

	}

}
