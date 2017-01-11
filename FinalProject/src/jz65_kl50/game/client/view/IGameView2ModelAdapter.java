package jz65_kl50.game.client.view;

import java.io.Serializable;

import map.ToggleAnnotation;

/**
 * The interface of adapter from game view to model
 * @author zhouji, kejun liu
 *
 */
public interface IGameView2ModelAdapter extends Serializable {

	/**
	 * called by game view after player send a text message
	 * @param text the content of message
	 */
	public void sendText(String text);

	/**
	 * called by game view after a player click on "start" button to start game
	 */
	public void sendStartGame();

	/**
	 * called by game view after a player chooses a annotation whose score is not zero and has not been chosen by someone else
	 * @param anno the chosen annotation point
	 */
	public void notifySelected(ToggleAnnotation anno);

	
	/**
	 * called by game view when the window is closed to tell model to notify server that the player is leaving
	 */
	public void stop();

}
