package jz65_kl50.gamelobby.model;

import java.awt.Container;
/**
 * The adapter from game lobby model to view-
 * @author zhouji
 *
 */
public interface ILobbyModel2ViewAdapter {
	/**
	 * get the member pane 
	 * @return the member pane 
	 */
	public Container getMemberPane();

	/**
	 * remove a user from list
	 * @param s the user
	 */
	public void removeMember(String s);

	/**
	 * add the user to the list
	 * @param s the user
	 */
	public void addMember(String s);
}
