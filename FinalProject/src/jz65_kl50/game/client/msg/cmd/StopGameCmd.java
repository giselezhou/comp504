package jz65_kl50.game.client.msg.cmd;

import common.AOurDataPacketAlgoCmd;
import common.ICmd2ModelAdapter;
import common.OurDataPacket;
import jz65_kl50.game.IPlayer;
import jz65_kl50.game.server.msg.StopGameMsg;
import jz65_kl50.game.client.model.IPlayer2ModelApdater;

/**
 * The command used by player when server sends stop game message. Calling the adapter's from player to model stop game method
 * @author zhouji
 *
 */
public class StopGameCmd extends AOurDataPacketAlgoCmd<StopGameMsg, IPlayer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4398468178664987618L;

	@Override
	public Void apply(Class<?> index, OurDataPacket<StopGameMsg, IPlayer> host, Object... params) {
		((IPlayer2ModelApdater)params[0]).stopGame(host.getData().getScore());
		return null;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub

	}

}
