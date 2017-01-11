package jz65_kl50.gamelobby.model;

import java.rmi.ConnectException;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.UUID;

import common.IChatServer;
import common.IChatroom;
import common.OurDataPacket;
import common.msg.IChatMsg;
import jz65_kl50.main.AChatroom;

/**
 * The concrete class of AChatroom
 * @author zhouji,  kejun liu
 *
 */
public class GameLobby extends AChatroom {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5289027116951606446L;
	//private transient ChatServer local;

	/**
	 * The constructor of game lobby using a chatroom object
	 * @param cr the passing chatroom object
	 */
	public GameLobby(IChatroom cr) {
		super(cr.getId(), cr.getName(), "Game Lobby", cr.getChatServers());

	}

	/**
	 * The constructor of game lobby using id and name
	 * @param id the of this room
	 * @param roomName the name of this game lobby
	 */
	public GameLobby(UUID id, String roomName) {
		super(id, roomName, "Game Lobby", new HashSet<IChatServer>());
	}

	/**
	 * add chat server
	 */
	public boolean addChatServer(IChatServer chatStub) {

		return _stubList.add(chatStub);
	}

	/**
	 * remove chat server
	 * @return true if the chat server is in the game lobby before else false
	 */
	public boolean removeChatServer(IChatServer chatServer) {

		return _stubList.remove(chatServer);
	}


	public <S> void send(OurDataPacket<? extends IChatMsg, S> dp) {

		try {
			for (IChatServer stub : getChatServers()) {

				stub.receive(dp);
			}
		} catch (ConnectException e) {

			System.out.print(e.getMessage());
		} catch (RemoteException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*public void setLocal(ChatServer cs) {
		local = cs;
	}*/

}
