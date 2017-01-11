package jz65_kl50.game.server.msg.cmd;

import common.AOurDataPacketAlgoCmd;
import common.ICmd2ModelAdapter;
import common.OurDataPacket;
import jz65_kl50.game.IPlayer;
import jz65_kl50.game.client.msg.StartGameMsg;
import jz65_kl50.game.server.model.IServer2ModelAdapter;

/**
 * The cmd processes the client.msg.StartGameMsg
 * @author zhouji,kejun liu
 *
 */
public class StartGameCmd extends AOurDataPacketAlgoCmd<jz65_kl50.game.client.msg.StartGameMsg, IPlayer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8026128773548815778L;

	@Override
	public Void apply(Class<?> index, OurDataPacket<StartGameMsg, IPlayer> host, Object... params) {
		((IServer2ModelAdapter)params[0]).startGame();
		return null;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub
		
	}

}
