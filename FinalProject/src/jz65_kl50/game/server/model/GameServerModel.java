package jz65_kl50.game.server.model;

import java.io.Serializable;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import common.AOurDataPacketAlgoCmd;
import common.IChatServer;
import common.ICmd2ModelAdapter;
import common.IComponentFactory;
import common.OurDataPacket;
import common.msg.IChatMsg;
import common.msg.chat.INewCmdReqMsg;
import jz65_kl50.game.APoint;
import jz65_kl50.game.IPlayer;
import jz65_kl50.game.IServer;

import jz65_kl50.game.Team;
import jz65_kl50.game.server.MyPoint;
import jz65_kl50.game.server.msg.ReceiveGameMsg;
import jz65_kl50.game.server.msg.StopGameMsg;
import jz65_kl50.game.server.msg.TeamScoreMsg;
import jz65_kl50.game.server.msg.cmd.ReceiveGameCmd;
import jz65_kl50.msg.NewCmdReqMsg;
import jz65_kl50.util.DataPacketList;
import jz65_kl50.util.IRandomizer;
import jz65_kl50.util.Randomizer;
import provided.datapacket.DataPacketAlgo;
import provided.mixedData.MixedDataDictionary;
import provided.mixedData.MixedDataKey;

/**
 * The model of game server
 * @author zhouji
 *
 */
public class GameServerModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9064241702238868876L;
	/**
	 * the hash table of ID of the team and teams in this game
	 */
	private Hashtable<UUID, Team> _teamList;
	/**
	 * The server object
	 */
	private IServer _server;
	/**
	 * The server stub
	 */
	private IServer _serverStub;
	/**
	 * The algo this model used to process messages
	 */
	private DataPacketAlgo<Void, Object> _algo;

	/**
	 * The constructor of game server
	 * @param teams the teams in this game
	 * @param server the server object of this game
	 */
	public GameServerModel(Team[] teams, Server server) {
		_teamList = new Hashtable<>();
		for (Team t : teams) {
			_teamList.put(t.getId(), t);
		}
		_server = server;

		_localDic = new MixedDataDictionary();
		initAlgo();
		try {
			_server.setAlgo(_algo);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void initAlgo() {
		// --------------------- default cmd-------------------------
		AOurDataPacketAlgoCmd<IChatMsg, IChatServer> defaultCmd = new AOurDataPacketAlgoCmd<IChatMsg, IChatServer>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -3906753936984369790L;
			private transient ICmd2ModelAdapter _adpt;

			@Override
			public Void apply(Class<?> index, OurDataPacket<IChatMsg, IChatServer> host, Object... params) {

				new Thread(new Runnable() {
					public void run() {
						// --------- cache unknown message ----------------
						UUID id = UUID.randomUUID();
						MixedDataKey<DataPacketList> key = new MixedDataKey<DataPacketList>(id, id.toString(),
								DataPacketList.class);

						DataPacketList list = _adpt.getFromLocalDict(key);
						if (list == null)
							list = new DataPacketList();
						list.add((OurDataPacket<IChatMsg, IChatServer>) host);
						_adpt.putIntoLocalDict(key, list);

						// --------- send new cmd req message to sender--------
						INewCmdReqMsg req = new NewCmdReqMsg(index, id);
						OurDataPacket<INewCmdReqMsg, IChatServer> m = new OurDataPacket<INewCmdReqMsg, IChatServer>(
								INewCmdReqMsg.class, req, _adpt.getChatServer());
						try {
							host.getSender().receive(m);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}).start();
				return null;
			}

			@Override
			public void setCmd2ModelAdpt(ICmd2ModelAdapter cmd2ModelAdpt) {
				_adpt = cmd2ModelAdpt;
			}

		};
		defaultCmd.setCmd2ModelAdpt(_cmdAdpt);
		_algo = new DataPacketAlgo<Void, Object>(defaultCmd);
		_algo.setCmd(jz65_kl50.game.client.msg.GameTextMsg.class, new jz65_kl50.game.server.msg.cmd.GameTextCmd());
		_algo.setCmd(jz65_kl50.game.client.msg.StartGameMsg.class, new jz65_kl50.game.server.msg.cmd.StartGameCmd());
		_algo.setCmd(jz65_kl50.game.client.msg.PointScoreMsg.class, new jz65_kl50.game.server.msg.cmd.PointScoreCmd());
		_algo.setCmd(jz65_kl50.game.client.msg.PlayerLeaveMsg.class,
				new jz65_kl50.game.server.msg.cmd.PlayerLeaveCmd());
		jz65_kl50.game.server.msg.cmd.NewReqCmd newReq = new jz65_kl50.game.server.msg.cmd.NewReqCmd();
		newReq.setCmd2ModelAdpt(_cmdAdpt);
		_algo.setCmd(INewCmdReqMsg.class, newReq);

		ReceiveGameCmd gameCmd = new ReceiveGameCmd();
		gameCmd.setCmd2ModelAdpt(_cmdAdpt);
		_algo.setCmd(ReceiveGameMsg.class, gameCmd);

	}

	/**
	 * The local dictionary used to cache unprocessed messages
	 */
	private transient MixedDataDictionary _localDic;
	/**
	 * The local adapter from command to this model
	 */
	private transient ICmd2ModelAdapter _cmdAdpt = new ICmd2ModelAdapter() {

		@Override
		public void buildComponentInScrollable(IComponentFactory fac) {
			// TODO Auto-generated method stub

		}

		@Override
		public void buildComponentInNonScrollable(IComponentFactory fac) {
			// TODO Auto-generated method stub

		}

		@Override
		public <T> boolean putIntoLocalDict(MixedDataKey<T> key, T value) {
			return _localDic.put(key, value) == null;
		}

		@Override
		public <T> T getFromLocalDict(MixedDataKey<T> key) {
			// TODO Auto-generated method stub
			return _localDic.get(key);
		}

		@Override
		public <T extends IChatMsg> void sendMsg2LocalChatroom(Class<T> index, T msg) {
			OurDataPacket<T, IChatServer> m = new OurDataPacket<T, IChatServer>(index, msg, _serverStub);
			m.execute(_algo, _serverStub);
		}

		@Override
		public IChatServer getChatServer() {
			// TODO Auto-generated method stub
			return _serverStub;
		}

	};

	/**
	 * The start process of model: 
	 * 1 -exports the server stub
	 * 2 -send receive game message to every player 
	 */
	public void start() {
		try {
			_serverStub = (IServer) UnicastRemoteObject.exportObject(_server, IChatServer.BOUND_PORT_SERVER);
			ReceiveGameMsg msg = new ReceiveGameMsg(_serverStub, new I2MainAdapter() {

				/**
				 * 
				 */
				private static final long serialVersionUID = -2789720157806813297L;

			});
			Team[] tlist = new Team[_teamList.size()];
			_teamList.values().toArray(tlist);
			for (Team t : tlist) {
				msg.setTeam(t);
				OurDataPacket<ReceiveGameMsg, IChatServer> m = new OurDataPacket<ReceiveGameMsg, IChatServer>(
						ReceiveGameMsg.class, msg, _serverStub);
				t.send(m);
			}

		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * The stop model process that un-exports server stub
	 */
	public void stop() {
		try {
			UnicastRemoteObject.unexportObject(_server, true);
		} catch (NoSuchObjectException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private <T extends IChatMsg> void sendMsgToTeamASync(Team t, T msg, Class<T> index) {
		OurDataPacket<T, IServer> m = new OurDataPacket<T, IServer>(index, msg, _serverStub);
		new Thread(new Runnable() {

			@Override
			public void run() {
				t.sendToTeam(m);
			}
		}).start();
		;
	}

	private <T extends IChatMsg> void sendMsgToTeamSync(Team t, T msg, Class<T> index) {
		OurDataPacket<T, IServer> m = new OurDataPacket<T, IServer>(index, msg, _serverStub);
		t.sendToTeam(m);
	}

	/**
	 * set player stub to replace original chatserver
	 * @param player the stub of player
	 * @param toRemove the player's original chatserver
	 */
	public void setPlayer(IPlayer player, IChatServer toRemove) {

		try {
			Team t = _teamList.get(player.getTeam().getId());
			t.removeChatServer(toRemove);
			t.addPlayer(player);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * Forwarding text message after receive text message from a player
	 * @param sender the sender of this message
	 * @param text the content of message
	 */
	public void sendText(IPlayer sender, String text) {

		try {
			Team t = _teamList.get(sender.getTeam().getId());
			jz65_kl50.game.server.msg.GameTextMsg msg = new jz65_kl50.game.server.msg.GameTextMsg(sender, text);
			sendMsgToTeamASync(t, msg, jz65_kl50.game.server.msg.GameTextMsg.class);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * called by the adapter from Server to model to notify stopping game. And then send stop game message to every player participated in this game
	 */
	public void stopGame() {

		Team[] tlist = new Team[_teamList.size()];
		_teamList.values().toArray(tlist);
		for (Team t : tlist) {
			StopGameMsg msg = new StopGameMsg(t.getScore());
			sendMsgToTeamSync(t, msg, jz65_kl50.game.server.msg.StopGameMsg.class);
		}

	}

	/**
	 * the duration time of game
	 */
	private long _gameTime = 2 * 60 * 1000;
	/**
	 * the remaining time of game
	 */
	private long _remainTime;
	/**
	 * local timer that starts when the game is started
	 */
	Timer _timer;

	/**
	 * This method is called by the adapter from Server to model when server receives the start game message from player.
	 * 1 -initialize points of random latitude and longitude and some of which have scores not equal to zero.
	 * 2 -send start game message to every player with the time of game round and set of points synchronously
	 * 3 -start the timer 
	 */
	public void startGame() {

		initPoints();

		jz65_kl50.game.server.msg.StartGameMsg msg = new jz65_kl50.game.server.msg.StartGameMsg(_gameTime, _pointSet);
		Team[] tlist = new Team[_teamList.size()];
		_teamList.values().toArray(tlist);
		for (Team t : tlist) {
			sendMsgToTeamSync(t, msg, jz65_kl50.game.server.msg.StartGameMsg.class);
		}
		_remainTime = _gameTime;
		_timer = new Timer();
		_timer.schedule(new TimerTask() {

			@Override
			public void run() {

				_remainTime -= 100;
				if (_remainTime < 0) {
					stopGame();
					_timer.cancel();
				}

			}

		}, 100, 100);
	}

	/**
	 * The set of points in game
	 */
	private HashSet<APoint> _pointSet;
	/**
	 * The number of points in each round of game
	 */
	int _numOfPoint = 20;
	/**
	 * The number of points whose scores are not zero.
	 */
	int _numOfScore = 3;
	/**
	 * The lower bound of scores that are not zero.
	 */
	int _scoreLow = 100;
	/**
	 * The higher bound of scores that are not zero.
	 */
	int _scoreHigh = 1000;

	private IRandomizer _rand = Randomizer.singleton;
	/**
	 * The lower bound of latitude
	 */
	private double _latLow = -90;
	/**
	 * The higher bound of latitude
	 */
	private double _latHigh = 90;
	/**
	 * The lower bound of longitude
	 */
	private double _longLow = -180;
	/**
	 * The higher bound of longitude
	 */
	private double _longHigh = 180;
	/**
	 * The set of points whose score is not zero
	 */
	private HashSet<APoint> _scorePlace;

	private void initPoints() {
		_scorePlace = new HashSet<APoint>();
		_pointSet = new HashSet<APoint>();
		int _remainScore = _numOfScore;

		for (int i = 0; i < _numOfPoint; i++) {
			double t_lat = _rand.randomDouble(_latLow, _latHigh);
			double t_long = _rand.randomDouble(_longLow, _longHigh);
			int t_score = 0;
			if (_remainScore >= 0) {
				t_score = _rand.randomInt(_scoreLow, _scoreHigh);
				MyPoint p = new MyPoint(t_score, t_lat, t_long, i + 1);
				p = addPoint(p);
				_scorePlace.add(p);
				_remainScore--;
				continue;
			}
			MyPoint p = new MyPoint(t_score, t_lat, t_long, i + 1);
			addPoint(p);
		}

	}

	private MyPoint addPoint(MyPoint p) {
		double t_lat = p.getLatitude();
		double t_long = p.getLongitude();
		int t_score = p.getScore();
		double lat_step = 20;
		double long_step = 10;
		MyPoint t_p = new MyPoint(t_score, t_lat, t_long, p.getIndex());
		while (!_pointSet.add(t_p)) {
			t_lat += lat_step;
			if (t_lat > _latHigh) {
				t_lat = _latHigh - (t_lat - _latHigh);
				lat_step = -lat_step;
			} else if (t_lat < _latLow) {
				t_lat = _latLow + (_latLow - t_lat);
				lat_step = -lat_step;
			}
			t_long += long_step;
			if (t_long > _longHigh) {
				t_long = _longHigh - (t_long - _longHigh);
				long_step = -long_step;
			} else if (t_long < _longLow) {
				t_long = _longLow + (_longLow - t_long);
				long_step = -long_step;
			}
			t_p = new MyPoint(t_score, t_lat, t_long, p.getIndex());
		}
		return t_p;
	}

	/**
	 * This method is called by the adapter from Server to model when server receives new command request message
	 * @param reqClassIdx the type of message
	 * @return the command to process the message
	 */
	@SuppressWarnings("unchecked")
	public AOurDataPacketAlgoCmd<?, IChatServer> getCmd(Class<?> reqClassIdx) {
		// TODO Auto-generated method stub
		return (AOurDataPacketAlgoCmd<?, IChatServer>) _algo.getCmd(reqClassIdx);
	}

	/**
	 * This method is called by the adapter from Server to model when server sends player receive game message to pass the server stub to player 
	 * @return the stub of server
	 */
	public IChatServer getStub() {
		// TODO Auto-generated method stub
		return _serverStub;
	}

	/**
	 * This method is called by the adapter from Server to model when server receives Point Score Message. 
	 * 1 -change the score of the team who chose the point
	 * 2 -mark the point as selected
	 * 3 -remove the point from score point set
	 * 4 -send Point Score Message to all players to mark the point on map.
	 * @param p the point that was selected
	 * @param id the id of the team who chose this point
	 */
	public void pointScore(APoint p, UUID id) {
		Team team = _teamList.get(id);
		team.changeScore(p.getScore());
		_scorePlace.remove(p);
		p.setSelected(true);
		TeamScoreMsg msg = new TeamScoreMsg(p, team);
		Team[] tlist = new Team[_teamList.size()];
		_teamList.values().toArray(tlist);
		for (Team t : tlist) {
			sendMsgToTeamSync(t, msg, jz65_kl50.game.server.msg.TeamScoreMsg.class);
		}
		if (_scorePlace.size() == 0) {
			stopGame();
		}

	}

	/**
	 * This method is called by the adapter from Server to model when server receives player's leaving message. Removes the player from its team
	 * @param sender the player who is leaving
	 */
	public void removePlayer(IPlayer sender) {
		try {
			_teamList.get(sender.getTeam().getId()).removePlayer(sender);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
