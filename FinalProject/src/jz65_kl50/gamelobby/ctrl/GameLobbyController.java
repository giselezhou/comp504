package jz65_kl50.gamelobby.ctrl;

import java.awt.Container;
import java.rmi.RemoteException;

import common.IChatServer;
import common.IChatroom;
import common.IUser;
import jz65_kl50.gamelobby.model.GameLobby;
import jz65_kl50.gamelobby.model.GameLobbyModel;
import jz65_kl50.gamelobby.model.ILobby2MainAdapter;
import jz65_kl50.gamelobby.view.GameLobbyFrame;
import jz65_kl50.gamelobby.view.ILobbyView2ModelAdapter;
import jz65_kl50.gamelobby.model.ILobbyModel2ViewAdapter;

/**
 * The controller of game lobby
 * @author zhouji, kejun liu
 *
 */
public class GameLobbyController {

	/**
	 * lobby view
	 */
	private GameLobbyFrame _view;

	/**
	 * lobby model
	 */
	private GameLobbyModel _model;

	/**
	 * adapter from lobby to main MVC
	 */
	// private ILobby2MainAdapter _adpt;

	private GameLobby _lobby;

	/**
	 * The constructor of game lobby controller
	 * @param cr the IChatroom object of this game lobby
	 * @param userStub the user starts this game lobby
	 * @param iLobby2MainAdapter the adapter from game lobby to main MVC
	 */
	public GameLobbyController(IChatroom cr, IUser userStub, ILobby2MainAdapter iLobby2MainAdapter) {
		_lobby = new GameLobby(cr);

		_view = new GameLobbyFrame(new ILobbyView2ModelAdapter() {

			@Override
			public void invite(String ip) {
				_model.invite(ip);

			}

			@Override
			public String getChatroomName() {

				return _lobby.getName();
			}

			@Override
			public void stop() {
				_model.stop();

			}

			@Override
			public void sendGame() {
				_model.sendGame();
			}

		});
		
		_model = new GameLobbyModel(_lobby, userStub, iLobby2MainAdapter, new ILobbyModel2ViewAdapter() {

			@Override
			public Container getMemberPane() {
				// TODO Auto-generated method stub
				return _view.getMemberPane();
			}

			@Override
			public void removeMember(String s) {
				_view.removeMember(s);

			}

			@Override
			public void addMember(String s) {
				_view.addMember(s);

			}
		});

		

		iLobby2MainAdapter.install(_view, cr.getId());

		try {
			String msg = userStub.getName() + " @ " + userStub.getIP() + " created room " + cr.getName() + "\n";
			iLobby2MainAdapter.append(msg);
			System.out.println(msg);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * method to start the game lobby MVC
	 */
	public void start() {
		_view.start();
		_model.start();

	}

	/**
	 * get the stub of chatserver
	 * @return the stub of chatserver
	 */
	public IChatServer getStub() {
		// TODO Auto-generated method stub
		return _model.getStub();
	}

	/**
	 * method to stop this game lobby MVC
	 */
	public void stop() {
		_view.stop();
	}

}
