package jz65_kl50.game.client.msg.cmd;

import common.AOurDataPacketAlgoCmd;
import common.ICmd2ModelAdapter;
import common.OurDataPacket;
import jz65_kl50.game.IServer;
import jz65_kl50.game.server.msg.TeamScoreMsg;
import jz65_kl50.game.client.model.IPlayer2ModelApdater;

/**
 * The command deals with the message from server that when a team pinned a scored point.
 * @author zhouji, kejun liu
 *
 */
public class TeamScoreCmd extends AOurDataPacketAlgoCmd<TeamScoreMsg, IServer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2142698405636250940L;

	@Override
	public Void apply(Class<?> index, OurDataPacket<TeamScoreMsg, IServer> host, Object... params) {
		((IPlayer2ModelApdater) params[0]).setTeamScore(host.getData().getTeam(), host.getData().getPoint());
		return null;
	}

	@Override
	public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
		// TODO Auto-generated method stub

	}

}
