package jz65_kl50.game.server.msg.cmd;

import common.AOurDataPacketAlgoCmd;
import common.ICmd2ModelAdapter;
import common.OurDataPacket;
import jz65_kl50.game.IPlayer;
import jz65_kl50.game.client.msg.PlayerLeaveMsg;
import jz65_kl50.game.server.model.IServer2ModelAdapter;
/**
 * The cmd processes the PlayerLeaveMsg
 * @author zhouji,kejun liu
 *
 */
public class PlayerLeaveCmd extends AOurDataPacketAlgoCmd<PlayerLeaveMsg, IPlayer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4616105901920895727L;

	@Override
	public Void apply(Class<?> index, OurDataPacket<PlayerLeaveMsg, IPlayer> host, Object... params) {
		((IServer2ModelAdapter) params[0]).removePlayer(host.getSender());
		return null;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub

	}

}
