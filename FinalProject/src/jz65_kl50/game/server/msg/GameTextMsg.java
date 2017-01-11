package jz65_kl50.game.server.msg;

import common.msg.IChatMsg;
import jz65_kl50.game.IPlayer;

/**
 * The message server used to forward players' text message
 * @author zhouji, kejun liu
 *
 */
public class GameTextMsg implements IChatMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2243115148443347887L;
	/**
	 * The content of message
	 */
	private String _text;
	/**
	 * The sender of this message
	 */
	private IPlayer _sender;

	/**
	 * The constructor of this message
	 * @param sender the sender of the message
	 * @param content the content of message
	 */
	public GameTextMsg(IPlayer sender, String content) {
		_text = content;
		_sender = sender;

	}

	/**
	 * get the context of message
	 * @return the content of message
	 */
	public String getText() {

		return _text;
	}

	/**
	 * get the sender of this message
	 * @return the sender of this message
	 */
	public IPlayer getSender() {
		return _sender;
	}

}
