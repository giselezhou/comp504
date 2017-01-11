package jz65_kl50.game.server.ctrl;

import java.util.UUID;

import common.AOurDataPacketAlgoCmd;
import common.IChatServer;
import jz65_kl50.game.APoint;
import jz65_kl50.game.IPlayer;
import jz65_kl50.game.Team;
import jz65_kl50.game.server.model.GameServerModel;
import jz65_kl50.game.server.model.I2MainAdapter;
import jz65_kl50.game.server.model.IServer2ModelAdapter;
import jz65_kl50.game.server.model.Server;

/**
 * The controller of game server 
 * @author zhouji, kejun liu
 *
 */
public class GameServerController {

	/**
	 * the adapter from game server controller to main model
	 */
	I2MainAdapter _adpt;
	
	/**
	 * The model of game server
	 */
	GameServerModel _model;
	/**
	 * The remote object of server
	 */
	Server _server;
	/**
	 * The stub of server
	 */
	Server _serverStub;

	/**
	 * The constructor of game server controller
	 * @param gameID the ID of the game
	 * @param server the server object
	 * @param teams the team joined in this game
	 */
	public GameServerController(UUID gameID, IChatServer server, Team[] teams) {
		_server = new Server(server, new IServer2ModelAdapter() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 140052695376739964L;

			@Override
			public void setPlayer(IPlayer player, IChatServer toRemove) {
				_model.setPlayer(player, server);

			}

			@Override
			public void sendText(IPlayer sender, String text) {
				_model.sendText(sender, text);

			}

			@Override
			public void startGame() {
				_model.startGame();

			}

			@Override
			public AOurDataPacketAlgoCmd<?, IChatServer> getCmd(Class<?> reqClassIdx) {
				// TODO Auto-generated method stub
				return _model.getCmd(reqClassIdx);
			}

			@Override
			public IChatServer getStub() {
				// TODO Auto-generated method stub
				return _model.getStub();
			}

			@Override
			public void pointScore(APoint p, UUID id) {
				_model.pointScore(p, id);

			}

			@Override
			public void removePlayer(IPlayer sender) {
				_model.removePlayer(sender);

			}

		});
		_model = new GameServerModel(teams, _server);

	}

	/**
	 * used to tell server model to stop when main model choose to stop this game
	 */
	public void stop() {
		_model.stop();

	}

	/**
	 * used when main model want to start game server
	 */
	public void start() {
		_model.start();

	}

}
