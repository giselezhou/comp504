package jz65_kl50.gamelobby.view;

/**
 * The adapter from game lobby view to model
 * @author zhouji
 *
 */
public interface ILobbyView2ModelAdapter {

	/**
	 * used when user invite another user
	 * @param ip the ip address of user who is invited
	 */
	public void invite(String ip);


	/**
	 * get the name of this game lobby
	 * @return the name of this game lobby
	 */
	public String getChatroomName();

	/**
	 * this method is used when game lobby view is closed or the user clicked on the leave button
	 */
	public void stop();

	/**
	 * this method is used when user clicks on send game button
	 */
	public void sendGame();


}
