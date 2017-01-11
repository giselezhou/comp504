package jz65_kl50.main.controller;

import java.awt.Color;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.UUID;

import javax.swing.JComponent;

import common.IChatServer;
import common.IChatroom;
import common.IUser;
import jz65_kl50.game.Team;
import jz65_kl50.game.server.ctrl.GameServerController;
import jz65_kl50.gamelobby.ctrl.GameLobbyController;
import jz65_kl50.gamelobby.model.GameLobby;
import jz65_kl50.gamelobby.model.ILobby2MainAdapter;
import jz65_kl50.gamelobby.view.GameLobbyFrame;
import jz65_kl50.main.ChatServer;
import jz65_kl50.main.model.IMain2GameAdapter;
import jz65_kl50.main.model.IMain2LobbyAdapter;
import jz65_kl50.main.model.IUser2ModelAdapter;
import jz65_kl50.main.model.IViewAdapter;
import jz65_kl50.main.model.MainModel;
import jz65_kl50.main.model.User;
import jz65_kl50.main.view.AppFrame;
import jz65_kl50.main.view.ILoginAdapter;
import jz65_kl50.main.view.IModelAdapter;
import jz65_kl50.main.view.LoginUI;

/**
 * The controller of main MVC
 * @author zhouji,kejun liu
 *
 */
public class MainController {

	/**
	 * The view of the main MVC
	 */
	private LoginUI login_view;
	private AppFrame _view;
	/**
	 * The model of the main MVC
	 */
	private MainModel _model;

	/**
	 * The user whole of this MVC
	 */
	private IUser _user;
	private IUser _userStub;
	// private UserMessageConsumer _msgConsumer;
	// private BlockingQueue<OurDataPacket<IUserMsg>> _msgQueue;

	/**
	 * The mini-MVC
	 */
	public MainController() {

		// model
		_model = new MainModel(new IViewAdapter() {

			@Override
			public void append(String s) {

				_view.append(s);
			}

			@Override
			public IMain2LobbyAdapter createLobby(IChatroom cr) {
				GameLobbyController _lobbyCtrl = new GameLobbyController(cr, _userStub, new ILobby2MainAdapter() {

					@Override
					public void install(GameLobbyFrame view, UUID id) {
						_view.install(view, id);
					}

					@Override
					public void append(String s) {
						_view.append(s);

					}

					@Override
					public IUser getUser() {
						// TODO Auto-generated method stub
						return _userStub;
					}

					@Override
					public void createGame(GameLobby lobby, ChatServer server) {
						HashSet<IChatServer> slist1 = new HashSet<IChatServer>();
						HashSet<IChatServer> slist2 = new HashSet<IChatServer>();
						int i = 0;

						for (IChatServer stub : lobby.getChatServers()) {
							if (i % 2 == 0) {

								slist1.add(stub);
							} else {

								slist2.add(stub);
							}
							i++;
						}
						UUID gameID = UUID.randomUUID();
						Team _team1 = new Team(UUID.randomUUID(), "Red", slist1, Color.red);
						Team _team2 = new Team(UUID.randomUUID(), "Blue", slist2, Color.blue);

						GameServerController gamectrl = new GameServerController(gameID, server,
								new Team[] { _team1, _team2 });
						gamectrl.start();
						_model.addGameAdapter(gameID, new IMain2GameAdapter() {

							@Override
							public void stop() {
								gamectrl.stop();

							}
						});

					}

					@Override
					public void addInScrollable(JComponent make) {
						_view.addComponentInScrollable(make);

					}

					@Override
					public void addInNonScrollable(JComponent make) {
						_view.addComponentInNonScrollable(make);

					}

				});
				_lobbyCtrl.start();
				return new IMain2LobbyAdapter() {

					@Override
					public void stop() {
						_lobbyCtrl.stop();

					}

					@Override
					public IChatServer getStub() {

						return _lobbyCtrl.getStub();
					}

				};
			}

		});

		// login
		login_view = new LoginUI(new ILoginAdapter() {

			@Override
			public void initUser(String username, String ip) {
				// user
				UUID id = UUID.randomUUID();
				/*
				 * _msgQueue = new
				 * LinkedBlockingQueue<OurDataPacket<IUserMsg>>(); _msgConsumer
				 * = new UserMessageConsumer(_msgQueue, new IUser2ModelAdapter()
				 * {
				 * 
				 * @Override public void createChatroom(IChatroom cr) {
				 * _model.createChatroom(cr, _user); }
				 * 
				 * }); new Thread(_msgConsumer).start();
				 */
				_user = new User(username, id, ip, new IUser2ModelAdapter() {

					@Override
					public void createChatroom(IChatroom cr) {
						_model.createChatroom(cr);
					}

				});

				// set login window close
				login_view.stop();

				// main frame
				_view = new AppFrame(new IModelAdapter() {
					@Override
					public void createGameLobby(String roomName) {
						UUID id = UUID.randomUUID();
						GameLobby cr = new GameLobby(id, roomName);
						try {
							_model.createChatroom(cr);

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					@Override
					public void quit() {
						_model.stop();
					}

					@Override
					public void joinRoom(IChatroom cr) {
						_model.createChatroom(cr);
					}

					@Override
					public HashSet<IChatroom> connectTo(String remoteIP) {

						return _model.connectTo(remoteIP);
					}

					@Override
					public HashSet<IChatroom> getChatroom() {
						// TODO Auto-generated method stub
						try {
							return _user.getChatrooms();
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return null;
					}

				}, ip, username);
				_view.setTitle("User：" + username + " @" + ip);
				_view.start();
				_userStub = _model.start(_user);

				System.out.println("User：" + username + " @" + ip + " longin!");
			}

		});

	}

	/**
	 * Starts the view then the model. The view needs to be started first so
	 * that it can display the model status updates as it starts.
	 */
	public void start() {
		login_view.start();
	}

	/**
	 * Main() method of the application. Instantiates and then starts the
	 * controller.
	 * 
	 * @param args
	 *            ignored
	 */
	public static void main(String[] args) {
		(new MainController()).start();
	}

}
