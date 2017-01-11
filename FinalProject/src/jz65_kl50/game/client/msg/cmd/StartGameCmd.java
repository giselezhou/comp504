package jz65_kl50.game.client.msg.cmd;

import common.AOurDataPacketAlgoCmd;
import common.ICmd2ModelAdapter;
import common.OurDataPacket;
import jz65_kl50.game.IServer;
import jz65_kl50.game.client.model.IPlayer2ModelApdater;


/**
 * The command used when the server sends start game message
 * @author zhouji, kejun liu
 *
 */
public class StartGameCmd extends AOurDataPacketAlgoCmd<jz65_kl50.game.server.msg.StartGameMsg, IServer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7025764704140970274L;

	@Override
	public Void apply(Class<?> index, OurDataPacket<jz65_kl50.game.server.msg.StartGameMsg, IServer> host,
			Object... params) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				((IPlayer2ModelApdater) params[0]).startGame(host.getData().getDuration(),host.getData().getPointSet());

			}

		}).start();
		return null;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub

	}

}
