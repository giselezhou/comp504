package jz65_kl50.game.client.model;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.Timer;

import common.IChatServer;
import common.OurDataPacket;
import common.msg.IChatMsg;
import gov.nasa.worldwind.geom.Position;
import jz65_kl50.game.APoint;
import jz65_kl50.game.IPlayer;
import jz65_kl50.game.IServer;
import jz65_kl50.game.Place;
import jz65_kl50.game.Team;
import jz65_kl50.game.client.msg.GameTextMsg;
import jz65_kl50.game.client.msg.PlayerLeaveMsg;
import jz65_kl50.game.client.msg.PointScoreMsg;
import map.ToggleAnnotation;
import provided.datapacket.DataPacketAlgo;

/**
 * The model of game MVC
 * @author zhouji, kejun liu
 *
 */
public class GameModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 112726402526186412L;
	
	/**
	 * the stub of game server
	 */
	private IServer _server;
	
	private Player _player;
	/**
	 * the adapter from game model to game view
	 */
	private IGameModel2ViewAdapter _view;
	/**
	 * the team of this player 
	 */
	private Team _team;
	private DataPacketAlgo<Void, Object> _algo;
	private IPlayer _playerStub;
	private IChatServer _origin;

	private int _timeSlice = 500;
	private MyTimer _timer;

	private boolean _started = false;

	/**
	 * ~~~~~~~~~game~~~~~~~~
	 */

	public int teamPoints = 0;

	/**
	 * constructor of this model
	 * @param team the team the player in
	 * @param server the game server
	 * @param player the player owns this mvc
	 * @param origin the player's corresponding chatserver
	 * @param view the adapter from game model to game view
	 */
	public GameModel(Team team, IServer server, Player player, IChatServer origin, IGameModel2ViewAdapter view) {
		_server = server;
		_player = player;
		_origin = origin;
		_team = team;
		_view = view;
		initAlgo();
		_player.setAlgo(_algo);
	}

	private void initAlgo() {
		_algo = new DataPacketAlgo<Void, Object>(null);
		_algo.setCmd(jz65_kl50.game.server.msg.GameTextMsg.class, new jz65_kl50.game.client.msg.cmd.GameTextCmd());
		_algo.setCmd(jz65_kl50.game.server.msg.StartGameMsg.class, new jz65_kl50.game.client.msg.cmd.StartGameCmd());
		_algo.setCmd(jz65_kl50.game.server.msg.TeamScoreMsg.class, new jz65_kl50.game.client.msg.cmd.TeamScoreCmd());
		_algo.setCmd(jz65_kl50.game.server.msg.StopGameMsg.class, new jz65_kl50.game.client.msg.cmd.StopGameCmd());
	}

	
	private void initDots(HashSet<APoint> pointSet) {
		_annoMap = new HashMap<ToggleAnnotation, Place>();
		_placeMap = new HashMap<Integer, ToggleAnnotation>();

		for (APoint p : pointSet) {

			Place t_p = new Place(p);
			ToggleAnnotation t_anno = new ToggleAnnotation(t_p.getIndex() + "", t_p.getIndex() + " selected",
					Position.fromDegrees(t_p.getLatitude(), t_p.getLongitude()));
			if (t_p.getScore() == 0)

				t_anno.setSelectedBackgroundColor(Color.green);
			_annoMap.put(t_anno, t_p);
			_placeMap.put(t_p.getIndex(), t_anno);
			System.out.println("dot is " + t_p.toString());
			_view.addAnnotation(t_anno);

		}
	}

	
	private HashMap<ToggleAnnotation, Place> _annoMap;
	private HashMap<Integer, ToggleAnnotation> _placeMap;

	
	/**
	 * start the game when receive a message from game server
	 * @param time the duration of the game
	 * @param pointSet the set of point used this time
	 */
	public void startGame(long time, HashSet<APoint> pointSet) {

		initDots(pointSet);
		startTimer(time);
		_started = true;
		_view.setStartDisable();

	}

	/** 
	 * start the timer after receiving start game message
	 * @param time the duration of game
	 */
	private void startTimer(long time) {
		_timer = new MyTimer(time, _timeSlice, new ActionListener() {
			/**
			 * The timer "ticks" by calling this method every _timeslice
			 * milliseconds
			 */
			public void actionPerformed(ActionEvent e) {
				_timer.allTime -= _timeSlice;
				if (_timer.allTime >= 0)
					_view.updateTime(_timer.allTime);
			}
		});
		_timer.start();

	}

	/**
	 * send message to the server after player clicked on the start button to tell server to start game
	 */
	public void sendStartGame() {
		jz65_kl50.game.client.msg.StartGameMsg msg = new jz65_kl50.game.client.msg.StartGameMsg();
		sendMsgToServerAsync(msg, jz65_kl50.game.client.msg.StartGameMsg.class);
	}

	/**
	 * start this model. export the player stub and connect back to game server
	 */
	public void start() {

		try {
			_playerStub = (IPlayer) UnicastRemoteObject.exportObject(_player, IChatServer.BOUND_PORT_CLIENT);
			try {
				_server.connectBack(_player, _origin);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * send text message to the team
	 * @param text the content of message
	 */
	public void sendText(String text) {
		GameTextMsg msg = new GameTextMsg(text, _team);
		sendMsgToServerSync(msg, GameTextMsg.class);
	}

	/**
	 * send message to server asynchronously 
	 * @param msg the message sending to server
	 * @param index the type of message sending to server
	 */
	private <T extends IChatMsg> void sendMsgToServerAsync(T msg, Class<T> index) {
		OurDataPacket<T, IPlayer> m = new OurDataPacket<T, IPlayer>(index, msg, _playerStub);
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					_server.receive(m);
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}).start();

	}

	/**
	 * send message to server synchronously 
	 * @param msg the message sending to server
	 * @param index the type of message sending to server
	 */
	private <T extends IChatMsg> void sendMsgToServerSync(T msg, Class<T> index) {
		OurDataPacket<T, IPlayer> m = new OurDataPacket<T, IPlayer>(index, msg, _playerStub);
		try {
			_server.receive(m);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * notify the server when a scored point is selected
	 * @param anno the point corresponding annotation on the map
	 */
	public void notifySelected(ToggleAnnotation anno) {
		if (_started) {
			Place t_p = _annoMap.get(anno);
			System.out.println(t_p);

			if (t_p.getScore() != 0) {
				if (!t_p.selected()) {
					PointScoreMsg msg = new PointScoreMsg(t_p);
					sendMsgToServerSync(msg, PointScoreMsg.class);
				}

			} else {
				anno.setSelected(true);
				anno.setText("No");
			}
		}
	}

	/**
	 * set the annotation as scored after receiving a message from the server
	 * @param t the team wins this point
	 * @param p the point that is selected
	 */
	public void setTeamScore(Team t, APoint p) {
		ToggleAnnotation t_anno = _placeMap.get(p.getIndex());
		t_anno.setSelectedBackgroundColor(t.getColor());
		Place _p = _annoMap.get(t_anno);
		_p.setSelected(true);
		if (_team.getId() == t.getId()) {
			t_anno.setSelected(true);
			t_anno.setText("Your team scored " + p.getScore());
		} else {
			t_anno.setSelected(true);
			t_anno.setText("Team " + t.getName() + " scored " + p.getScore());
		}

	}

	/**
	 * called after receiving the stop game message from the game server
	 * @param score the score of the team this player in
	 */
	public void stopGame(int score) {
		_started = false;
		_timer.stop();
		_view.stop(score);

	}

	/** 
	 * stop this game mvc of this player
	 */
	public void stop() {
		PlayerLeaveMsg msg = new PlayerLeaveMsg();
		sendMsgToServerSync(msg, PlayerLeaveMsg.class);

	}

}

class MyTimer extends Timer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2479084202227543045L;
	long allTime;

	public MyTimer(long time, int delay, ActionListener listener) {
		super(delay, listener);
		allTime = time;
	}

}
