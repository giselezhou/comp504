package jz65_kl50.gamelobby.model;

import java.util.UUID;

import common.AOurDataPacketAlgoCmd;
import common.IChatServer;
/**
 * The adapter from chatserver to model
 * @author zhouji
 *
 */
public interface IChat2ModelAdapter {

	/**
	 * get the stub of chatserver
	 * @return
	 */
	public IChatServer getStub();

	/**
	 * set the cmd of unknown type
	 * @param classIdx the cmd type
	 * @param aOurDataPacketAlgoCmd the cmd
	 * @param uuid the id of message cached before
	 */
	public void setCmd(Class<?> classIdx, AOurDataPacketAlgoCmd<?, IChatServer> aOurDataPacketAlgoCmd, UUID uuid);

	/**
	 * add a chatserver
	 * @param sender the one to add
	 * @return if success
	 */
	public boolean addStub(IChatServer sender);

	/**
	 * remove a chatserver
	 * @param sender the one to remove
	 * @return if success
	 */
	public boolean removeStub(IChatServer sender);

	/**
	 * get the cmd from model
	 * @param reqClassIdx the type of cmd
	 * @return the cmd
	 */
	public AOurDataPacketAlgoCmd<?,IChatServer> getCmd(Class<?> reqClassIdx);

	
	//public void append(String text);

}
