package jz65_kl50.game.client.msg.cmd;

import common.AOurDataPacketAlgoCmd;
import common.ICmd2ModelAdapter;
import common.OurDataPacket;
import jz65_kl50.game.IServer;
import jz65_kl50.game.client.model.IPlayer2ModelApdater;
import jz65_kl50.game.server.msg.GameTextMsg;

/**
 * A command used when the server forwards a text message from a player in this team
 * @author zhouji
 *
 */
public class GameTextCmd extends AOurDataPacketAlgoCmd<GameTextMsg, IServer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5811940176782374511L;


	@Override
	public Void apply(Class<?> index, OurDataPacket<GameTextMsg, IServer> host, Object... params) {
		IPlayer2ModelApdater _adpt = (IPlayer2ModelApdater) params[0];
		_adpt.sendText(host.getData().getSender(), host.getData().getText());
		return null;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub

	}

}
