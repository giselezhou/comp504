package jz65_kl50.main.model;

import common.IChatroom;

/**
 * The adapter from user to model
 * @author zhouji, kejun liu
 *
 */
public interface IUser2ModelAdapter {

	/**
	 * calling model to create chat room MVC
	 * @param cr the chat room to be created
	 */
	void createChatroom(IChatroom cr);

}
