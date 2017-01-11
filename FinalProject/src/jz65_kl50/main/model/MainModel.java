package jz65_kl50.main.model;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import common.IChatServer;
import common.IChatroom;
import common.IUser;
import provided.rmiUtils.IRMIUtils;
import provided.rmiUtils.IRMI_Defs;
import provided.rmiUtils.RMIUtils;
import provided.util.IVoidLambda;

/**
 * main model of this app
 * 
 *
 */
public class MainModel {

	HashMap<UUID, IMain2LobbyAdapter> _lobbyAdptList = new HashMap<>();
	HashMap<UUID, IMain2GameAdapter> _gameAdptList = new HashMap<>();

	private IUser _user;
	private IUser _userStub;
	/**
	 * The adapter to the view
	 */
	private IViewAdapter _view;

	/**
	 * The RMI Registry
	 */
	private Registry registry;
	/**
	 * A command used as a wrapper around the view adapter for the IRMIUtils and
	 * the user.
	 */
	private IVoidLambda<String> outputCmd = new IVoidLambda<String>() {
		public void apply(String... strs) {
			/*
			 * for (String s : strs) _view.append(s);
			 */
		}
	};

	/**
	 * Utility object used to get the Registry
	 */
	private IRMIUtils rmiUtils = new RMIUtils(outputCmd);

	/**
	 * The constructor for the class
	 * 
	 * @param iViewAdapter
	 *            The adapter to the view
	 */
	public MainModel(IViewAdapter iViewAdapter) {
		_view = iViewAdapter;
	}

	/**
	 * Start the server by setting the necessary RMI system parameters, starting
	 * the security manager, locating the local Registry and binding an instance
	 * of the user to it. Also starts the class file server to enable remote
	 * dynamic class loading.
	 * 
	 * @param username
	 * @param ip
	 * @return successful or not
	 */
	public IUser start(IUser user) {
		rmiUtils.startRMI(IRMI_Defs.CLASS_SERVER_PORT_SERVER);

		try {
			_user = user;
			_view.append("New user: " + _user.getName() + "@" + _user.getIP() + "\n");
			if (_userStub == null) {
				_userStub = (IUser) UnicastRemoteObject.exportObject(_user, IUser.BOUND_PORT_SERVER);
			}
			// _view.append("Looking for registry..." + "\n");
			registry = rmiUtils.getLocalRegistry();
			// _view.append("Found registry: " + registry + "\n");
			registry.rebind(IUser.BOUND_NAME, _userStub);
			// _view.append("User bound to " + IUser.BOUND_NAME + "\n");
		} catch (Exception e) {

			System.err.println("User exception:" + "\n");
			e.printStackTrace();
			System.exit(-1);
			return null;
		}

		// _view.append("Waiting..." + "\n");
		return _userStub;

	}

	/**
	 * Stops the MainModel by unbinding the User from the Registry and stopping
	 * class file server and stop all mini MVC associated with it.
	 */
	public void stop() {

		try {
			if (!_lobbyAdptList.isEmpty()) {
				IMain2LobbyAdapter[] roomList = new IMain2LobbyAdapter[_lobbyAdptList.size()];
				roomList = (IMain2LobbyAdapter[]) (_lobbyAdptList.values()).toArray(roomList);

				for (int i = 0; i < roomList.length; i++) {
					IMain2LobbyAdapter roomAdpt = roomList[i];
					roomAdpt.stop();
				}
			}
			
			if(_gameAdptList.isEmpty()){
				IMain2GameAdapter[] gameList=new IMain2GameAdapter[_gameAdptList.size()];
				(_gameAdptList.values()).toArray(gameList);
				for(IMain2GameAdapter adpt:gameList){
					adpt.stop();
				}
			}
			registry.unbind(IUser.BOUND_NAME);
			System.out.println("Controller: " + IUser.BOUND_NAME + " has been unbound.");

			rmiUtils.stopRMI();

			System.exit(0);
		} catch (Exception e) {

			System.err.println("Controller: Error unbinding " + IUser.BOUND_NAME + ":\n" + e);
			System.exit(-1);
		}
	}

	/**
	 * create a chatroom with its name
	 * 
	 * @param roomName
	 *            the name of chatroom
	 */
	public IChatServer createChatroom(IChatroom cr) {
		try {
			_user.getChatrooms().add(cr);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (!_lobbyAdptList.containsKey(cr.getId())) {
			IMain2LobbyAdapter adpt = _view.createLobby(cr);
			_lobbyAdptList.put(cr.getId(), adpt);
			return adpt.getStub();
		} else {
			return _lobbyAdptList.get(cr.getId()).getStub();
		}

	}

	/**
	 * append message to the main view
	 * 
	 * @param s
	 *            message
	 */
	public void append(String s) {
		_view.append(s);
	}

	/**
	 * connect to a remote ip address 
	 * @param remoteIP 
	 * @return the list of chat room the remote user is in
	 */
	public HashSet<IChatroom> connectTo(String remoteIP) {

		try {
			Registry registry = rmiUtils.getRemoteRegistry(remoteIP);
			IUser remoteUser = (IUser) registry.lookup(IUser.BOUND_NAME);
			remoteUser.connectBack(_userStub);
			return remoteUser.getChatrooms();
		} catch (AccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * install the game MVC
	 * @param gameID the id of the game
	 * @param _adpt the adapter to the game MVC
	 */
	public void addGameAdapter(UUID gameID, IMain2GameAdapter _adpt) {

		_gameAdptList.put(gameID, _adpt);
	}

	

}
