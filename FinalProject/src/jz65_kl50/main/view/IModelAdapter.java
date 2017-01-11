package jz65_kl50.main.view;

import java.util.HashSet;

import common.IChatroom;

/**
 * The adapter from main view to model
 *
 */
public interface IModelAdapter {


	/**
	 * calls the main model to create a chatroom of specified name
	 * @param text the name of chatroom
	 */
	public void createGameLobby(String text);

	/**
	 * when the frame is closed, it will call the model to stop all the mini mvca associated with this main MVC.
	 */
	public void quit();

	/**
	 * when user click on join button
	 * @param cr the chat room to join in
	 */
	public void joinRoom(IChatroom cr);

	/**
	 * when user connect to remote user to obtain the user's chat room list
	 * @param remoteIP remote user's ip
	 * @return the chat room the remote user in
	 */
	public HashSet<IChatroom> connectTo(String remoteIP);

	/**
	 * get local user's chat room
	 * @return local user's chat room
	 */
	public HashSet<IChatroom> getChatroom();



}
