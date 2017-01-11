package jz65_kl50.gamelobby.model;

import java.util.UUID;

import javax.swing.JComponent;

import common.IUser;
import jz65_kl50.gamelobby.view.GameLobbyFrame;
import jz65_kl50.main.ChatServer;

/**
 * The adapter from game lobby to main MVC
 * @author zhouji
 *
 */
public interface ILobby2MainAdapter {

	/**
	 * install the game lobby view to main view
	 * @param _view the view of game lobby
	 * @param id the id of the game lobby
	 */
	public void install(GameLobbyFrame _view, UUID id);

	/**
	 * append a text message to main view
	 * @param s the message content
	 */
	public void append(String s);

	/**
	 * get the user
	 * @return the user
	 */
	public IUser getUser();

	/**
	 * asking the main model to create game mvc
	 * @param _lobby the lobby requiring
	 * @param _server the chatserver of this user
	 */
	public void createGame(GameLobby _lobby, ChatServer _server);

	/**
	 * create a frame for a component from other user
	 * @param make the component
	 */
	public void addInScrollable(JComponent make);

	/**
	 * create a frame for a component from other user
	 * @param make the component
	 */
	public void addInNonScrollable(JComponent make);

}
