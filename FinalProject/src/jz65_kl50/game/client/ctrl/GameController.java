package jz65_kl50.game.client.ctrl;

import java.rmi.RemoteException;
import java.util.HashSet;

import common.IChatServer;
import jz65_kl50.game.APoint;
import jz65_kl50.game.IPlayer;
import jz65_kl50.game.IServer;
import jz65_kl50.game.Team;
import jz65_kl50.game.client.model.GameModel;
import jz65_kl50.game.client.model.IGameModel2ViewAdapter;
import jz65_kl50.game.client.model.IPlayer2ModelApdater;
import jz65_kl50.game.client.model.Player;
import jz65_kl50.game.client.view.GameView;
import jz65_kl50.game.client.view.IGameView2ModelAdapter;
import map.MapLayer;
import map.ToggleAnnotation;
/**
 * Controller of Game MVC
 * @author zhouji, kejun liu
 *
 */
public class GameController {

	private GameView _view;
	private GameModel _model;

	/**
	 * Constructor of Game controller
	 * @param team the team of this game mvc
	 * @param server the server starts this mvc
	 * @param player the player using this game mvc
	 */
	public GameController(Team team, IServer server, IChatServer player) {

		_view = new GameView(new IGameView2ModelAdapter() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -6112368869792547634L;

			@Override
			public void sendText(String text) {
				_model.sendText(text);

			}

			@Override
			public void sendStartGame() {
				_model.sendStartGame();

			}

			@Override
			public void notifySelected(ToggleAnnotation anno) {
				_model.notifySelected(anno);

			}

			@Override
			public void stop() {
				_model.stop();;
				
			}

		});
		Player _player = new Player(team, player, new IPlayer2ModelApdater() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 3579331821704130387L;

			@Override
			public void sendText(IPlayer sender, String text) {
				try {
					_view.append(sender.getUser().getName() + "@" + sender.getUser().getIP() + " : " + text + "\n");
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			@Override
			public void startGame(long time, HashSet<APoint> pointSet) {
				_model.startGame(time, pointSet);

			}

			@Override
			public void setTeamScore(Team t, APoint p) {
				_model.setTeamScore(t, p);

			}

			@Override
			public void stopGame(int score) {
				_model.stopGame(score);

			}
		});
		_model = new GameModel(team, server, _player, player, new IGameModel2ViewAdapter() {
			/**
			 * 
			 */
			private static final long serialVersionUID = -2905982243836731884L;

			@Override
			public void append(String txt) {
				_view.append(txt);

			}

			@Override
			public void updateTime(long time) {
				_view.updateTime(time);
			}

			@Override
			public void setStartDisable() {
				_view.setStartButton(false);

			}

			@Override
			public MapLayer getMapLayer() {
				// TODO Auto-generated method stub
				return _view.getMapLayer();
			}

			@Override
			public void addAnnotation(ToggleAnnotation t_anno) {

				_view.addAnnotation(t_anno);
			}

			@Override
			public void stop(int score) {
				_view.stop(score);

			}

		});
	}

	/**
	 * starts the mvc by calling view's and model's start method.
	 */
	public void start() {
		_view.start();
		_model.start();

	}
}
