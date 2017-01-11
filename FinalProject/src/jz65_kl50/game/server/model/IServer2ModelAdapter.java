package jz65_kl50.game.server.model;

import java.io.Serializable;
import java.util.UUID;

import common.AOurDataPacketAlgoCmd;
import common.IChatServer;
import jz65_kl50.game.APoint;
import jz65_kl50.game.IPlayer;

/**
 * The adapter from server to game server model
 * @author zhouji,kejun liu
 *
 */
public interface IServer2ModelAdapter extends Serializable {

	/**
	 * This method is used by server to tell model to replace player's stub
	 * @param player player's new stub
	 * @param toRemove the original stub
	 */
	public void setPlayer(IPlayer player, IChatServer toRemove);

	/**
	 * This method is used in Game Text cmd to process text massage from player.
	 * @param sender the sender of message
	 * @param text the content of message
	 */
	public void sendText(IPlayer sender, String text);

	/**
	 * This method is used in Start Game Cmd to call model's start game method.
	 */
	public void startGame();

	/**
	 * This method is used in New Cmd Request to get the corresponding cmd from model
	 * @param reqClassIdx the type of new cmd
	 * @return the cmd of new type
	 */
	public AOurDataPacketAlgoCmd<?, IChatServer> getCmd(Class<?> reqClassIdx);

	/**
	 * This method is used to get the server stub from model.
	 * @return the server stub
	 */
	public IChatServer getStub();

	/**
	 * This method is used in Point Score Cmd to call model's point score method.
	 * @param p the point is chosen
	 * @param id the id of team who win this point
	 */
	public void pointScore(APoint p, UUID id);

	/**
	 * This method is used in Player Leaving Message to call model's remove player method
	 * @param sender the player is leaving
	 */
	public void removePlayer(IPlayer sender);

}
