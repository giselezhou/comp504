package jz65_kl50.game.client.model;

import java.io.Serializable;

import map.MapLayer;
import map.ToggleAnnotation;

/**
 * the adapter from game model to game view
 * @author zhouji, kejun liu
 *
 */
public interface IGameModel2ViewAdapter extends Serializable {

	/**
	 * append text message to the view
	 * @param txt the content of message
	 */
	public void append(String txt);

	
	/**
	 * update the time label on the game view
	 * @param allTime the remaining time of this round
	 */
	public void updateTime(long allTime);

	/**
	 * set the start button disabled after game started
	 */
	public void setStartDisable();

	/**
	 * get the map layer of the view
	 * @return the map layer of the view
	 */
	public MapLayer getMapLayer();

	
	/**
	 * add an annotation to the view
	 * @param t_anno
	 */
	public void addAnnotation(ToggleAnnotation t_anno);

	/**
	 * stop the game 
	 * @param score the score of this team when game is stopped
	 */
	public void stop(int score);

}
