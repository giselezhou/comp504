package jz65_kl50.main.model;

import common.IChatroom;

/**
 * An adapter from maim model to view
 *
 */
public interface IViewAdapter {

	/**
	 * append message in the main view
	 * @param s message
	 */
	public void append(String s);

	public IMain2LobbyAdapter createLobby(IChatroom cr);




}
