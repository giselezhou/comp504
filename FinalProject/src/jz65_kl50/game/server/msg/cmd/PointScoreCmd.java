package jz65_kl50.game.server.msg.cmd;

import java.rmi.RemoteException;

import common.AOurDataPacketAlgoCmd;
import common.ICmd2ModelAdapter;
import common.OurDataPacket;
import jz65_kl50.game.APoint;
import jz65_kl50.game.IPlayer;
import jz65_kl50.game.Team;
import jz65_kl50.game.server.model.IServer2ModelAdapter;
/**
 * The cmd processes the client.msg.PointScoreMsg
 * @author zhouji,kejun liu
 *
 */
public class PointScoreCmd extends AOurDataPacketAlgoCmd<jz65_kl50.game.client.msg.PointScoreMsg, IPlayer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8587329151506317835L;

	@Override
	public Void apply(Class<?> index, OurDataPacket<jz65_kl50.game.client.msg.PointScoreMsg, IPlayer> host,
			Object... params) {

		try {
			APoint p = host.getData().getSelectedPoint();
			Team t = host.getSender().getTeam();
			((IServer2ModelAdapter) params[0]).pointScore(p, t.getId());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub

	}

}
