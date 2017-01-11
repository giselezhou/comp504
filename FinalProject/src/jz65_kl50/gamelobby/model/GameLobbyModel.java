package jz65_kl50.gamelobby.model;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

import common.AOurDataPacketAlgoCmd;
import common.IChatServer;
import common.IChatroom;
import common.ICmd2ModelAdapter;
import common.IComponentFactory;
import common.IUser;
import common.OurDataPacket;
import common.msg.IChatMsg;
import common.msg.chat.IAddCmdMsg;
import common.msg.chat.IAddMeMsg;
import common.msg.chat.ILeaveMsg;
import common.msg.chat.INewCmdReqMsg;
import common.msg.user.IInvite2RoomMsg;
import jz65_kl50.game.server.msg.ReceiveGameMsg;
import jz65_kl50.game.server.msg.cmd.ReceiveGameCmd;
import jz65_kl50.main.ChatServer;
import jz65_kl50.msg.AddMeMsg;
import jz65_kl50.msg.InviteMsg;
import jz65_kl50.msg.LeaveMsg;
import jz65_kl50.msg.NewCmdReqMsg;
import jz65_kl50.msg.cmd.AddCmd;
import jz65_kl50.msg.cmd.AddMeCmd;
import jz65_kl50.msg.cmd.LeaveMsgCmd;
import jz65_kl50.msg.cmd.NewReqCmd;
import jz65_kl50.util.DataPacketList;
import provided.datapacket.DataPacketAlgo;
import provided.mixedData.MixedDataDictionary;
import provided.mixedData.MixedDataKey;
import provided.rmiUtils.IRMIUtils;
import provided.rmiUtils.RMIUtils;
import provided.util.IVoidLambda;

/**
 * The model of game lobby
 * 
 * @author zhouji, kejun liu
 *
 */
public class GameLobbyModel {

	/**
	 * The constructor of game lobby model
	 * 
	 * @param lobby
	 *            the lobby object
	 * @param userStub
	 *            the user starts this model
	 * @param i2MainAdapter
	 *            the adapter from game lobby to main MVC
	 * @param i2ViewAdapter
	 *            the adapter from game lobby model to view
	 */
	public GameLobbyModel(IChatroom lobby, IUser userStub, ILobby2MainAdapter i2MainAdapter,
			ILobbyModel2ViewAdapter i2ViewAdapter) {
		_2viewAdpt = i2ViewAdapter;
		_2mainAdpt = i2MainAdapter;

		_localDic = new MixedDataDictionary();
		initAlgo();

		// initialize message queue and message consumer for chatserver
		_2ModelAdpt = new IChat2ModelAdapter() {

			@Override
			public IChatServer getStub() {
				// TODO Auto-generated method stub
				return _serverStub;
			}

			@Override
			public void setCmd(Class<?> classIdx, AOurDataPacketAlgoCmd<?, IChatServer> cmd, UUID id) {
				_algo.setCmd(classIdx, cmd);
				// _msgConsumer.setAlgo(_algo);
				MixedDataKey<DataPacketList> key = new MixedDataKey<DataPacketList>(id, id.toString(),
						DataPacketList.class);
				DataPacketList list = _localDic.get(key);
				if (list != null) {
					while (list.size() != 0) {
						OurDataPacket<?, IChatServer> m = list.get(0);
						m.execute(_algo, _2ModelAdpt);
						list.remove(0);
					}

				}
			}

			@Override
			public boolean addStub(IChatServer sender) {
				boolean res = false;
				try {
					res = _server.getChatroom().addChatServer(sender);
					_2viewAdpt.addMember(sender.getUser().getName() + "@" + sender.getUser().getIP());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return res;
				}
				return res;
			}

			@Override
			public boolean removeStub(IChatServer sender) {
				try {
					if (!sender.equals(_serverStub)) {
						_server.getChatroom().removeChatServer(sender);
						_2viewAdpt.removeMember(sender.getUser().getName() + "@" + sender.getUser().getIP());
						return true;
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return false;
				}
				return false;
			}

			@SuppressWarnings("unchecked")
			@Override
			public AOurDataPacketAlgoCmd<?, IChatServer> getCmd(Class<?> reqClassIdx) {
				// TODO Auto-generated method stub
				return (AOurDataPacketAlgoCmd<?, IChatServer>) _algo.getCmd(reqClassIdx);
			}

			/*
			 * @Override public void append(String text) {
			 * _2mainAdpt.append(text);
			 * 
			 * }
			 */
		};
		/*
		 * _msgQueue = new LinkedBlockingQueue<OurDataPacket<IChatMsg>>();
		 * _msgConsumer = new ChatMsgConsumer(_msgQueue, _2ModelAdpt, _algo);
		 * new Thread(_msgConsumer).start();
		 */
		_lobby = new GameLobby(lobby);
		_server = new ChatServer(_lobby, userStub, _2ModelAdpt, _algo);
		// _lobby.setLocal(_server);

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
		AddCmd addCmd = new AddCmd();
		addCmd.setCmd2ModelAdpt(_cmdAdpt);
		_algo.setCmd(IAddCmdMsg.class, addCmd);
		AddMeCmd addMe = new AddMeCmd();
		addMe.setCmd2ModelAdpt(_cmdAdpt);
		_algo.setCmd(IAddMeMsg.class, addMe);
		LeaveMsgCmd leaveCmd = new LeaveMsgCmd();
		leaveCmd.setCmd2ModelAdpt(_cmdAdpt);
		_algo.setCmd(ILeaveMsg.class, leaveCmd);
		NewReqCmd newReq = new NewReqCmd();
		newReq.setCmd2ModelAdpt(_cmdAdpt);
		_algo.setCmd(INewCmdReqMsg.class, newReq);

		ReceiveGameCmd gameCmd = new ReceiveGameCmd();
		gameCmd.setCmd2ModelAdpt(_cmdAdpt);
		_algo.setCmd(ReceiveGameMsg.class, gameCmd);

	}

	/**
	 * The method to stop when a user leaves
	 */
	public void stop() {

		try {

			_server.getChatroom().getChatServers().remove(_serverStub);
			_server.getUser().getChatrooms().remove(_server.getChatroom());
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ILeaveMsg msg = new LeaveMsg();
		OurDataPacket<ILeaveMsg, IChatServer> m = new OurDataPacket<ILeaveMsg, IChatServer>(ILeaveMsg.class, msg,
				_serverStub);
		new Thread(new Runnable() {

			@Override
			public void run() {
				_lobby.send(m);
			}
		}).start();

	}

	/**
	 * The method to start this model
	 * 
	 * @return
	 */
	public IChatServer start() {

		// export server stub
		try {
			_serverStub = (IChatServer) UnicastRemoteObject.exportObject(_server, IChatServer.BOUND_PORT_CLIENT);
		} catch (RemoteException e) {
			e.printStackTrace();
		}

		try {

			for (IChatServer stub : _server.getChatroom().getChatServers()) {
				try {
					_2viewAdpt.addMember(stub.getUser().getName() + "@" + stub.getUser().getIP());
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// add local stub to chatroom stub list
		try {
			_server.getChatroom().getChatServers().add(_serverStub);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// send addme message to other stub in the chatroom
		IAddMeMsg addme = new AddMeMsg(_serverStub);
		OurDataPacket<IAddMeMsg, IChatServer> m = new OurDataPacket<IAddMeMsg, IChatServer>(IAddMeMsg.class, addme,
				_serverStub);
		new Thread(new Runnable() {

			@Override
			public void run() {
				_lobby.send(m);
			}
		}).start();

		return _serverStub;

	}

	/**
	 * get the the user's chatserver stub
	 * 
	 * @return
	 */
	public IChatServer getStub() {
		return _serverStub;
	}

	/**
	 * this method is used when user invite a remote user to game lobby
	 * 
	 * @param ip
	 */
	public void invite(String ip) {

		Registry registry = rmiUtils.getRemoteRegistry(ip);

		Runnable r = new Runnable() {

			@Override
			public void run() {
				try {
					IUser newUser = (IUser) registry.lookup(IUser.BOUND_NAME);
					IInvite2RoomMsg msg = new InviteMsg(_server.getChatroom());
					OurDataPacket<IInvite2RoomMsg, IUser> m = new OurDataPacket<IInvite2RoomMsg, IUser>(
							IInvite2RoomMsg.class, msg, _2mainAdpt.getUser());
					newUser.receive(m);
					_2mainAdpt.append(
							msg.getClass() + " message sent to " + newUser.getName() + " @" + newUser.getIP() + "\n");
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		Thread t = new Thread(r);

		t.start();

	}

	/**
	 * This method is called when a user clicked on the send game button
	 */
	public void sendGame() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				_2mainAdpt.createGame(_lobby, _server);
			}

		}).start();
	}

	/**
	 * the chatserver of the user
	 */
	private ChatServer _server;
	/**
	 * the stub of chatserver
	 */
	private IChatServer _serverStub;
	/**
	 * The game lobby object
	 */
	private GameLobby _lobby;

	/**
	 * The adapter from game lobby to main MVC
	 */
	private ILobby2MainAdapter _2mainAdpt;

	/**
	 * The adapter from game lobby model to view
	 */
	private ILobbyModel2ViewAdapter _2viewAdpt;
	private DataPacketAlgo<Void, Object> _algo;

	/**
	 * The adapter from Chatserver to model
	 */
	private IChat2ModelAdapter _2ModelAdpt;
	/**
	 * The local dictionary caches messages unprocessed
	 */
	private MixedDataDictionary _localDic;
	/**
	 * The adapter from cmd to this model
	 */
	private ICmd2ModelAdapter _cmdAdpt = new ICmd2ModelAdapter() {

		@Override
		public void buildComponentInScrollable(IComponentFactory fac) {
			_2mainAdpt.addInScrollable(fac.make());

		}

		@Override
		public void buildComponentInNonScrollable(IComponentFactory fac) {
			_2mainAdpt.addInNonScrollable(fac.make());

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
	 * output command used to put multiple strings up onto the view.
	 */
	private IVoidLambda<String> outputCmd = new IVoidLambda<String>() {

		@Override
		public void apply(String... params) {

		}
	};

	/**
	 * Factory for the Registry and other uses.
	 */
	IRMIUtils rmiUtils = new RMIUtils(outputCmd);

}
