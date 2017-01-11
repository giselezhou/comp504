package jz65_kl50.game;

import java.awt.Color;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.UUID;

import common.IChatServer;
import common.IChatroom;
import common.OurDataPacket;
import common.msg.IChatMsg;
import jz65_kl50.main.AChatroom;

/**
 *  a concrete class of Chatroom represents a game room after game was sent to players
 * @author zhouji
 *
 */
public class Team extends AChatroom {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2183801473980985645L;
	/**
	 * the list of players on this team
	 */
	private HashSet<IPlayer> playerList;
	private int score = 0;

	/**
	 * the color of the team, red or blue
	 */
	private Color _color;

	
	/**
	 * construct a team with UUID, name, players and the color
	 * @param id the id of this team
	 * @param name the name of this team
	 * @param stubList the players' corresponding chatserver stubs of this team
	 * @param c the color of this team
	 */
	public Team(UUID id, String name, HashSet<IChatServer> stubList, Color c) {
		super(id, name, "Team", stubList);
		playerList = new HashSet<>();
		_color = c;
	}

	/**
	 * construct a team from an existing IChatroom object
	 * @param chatroom
	 */
	public Team(IChatroom chatroom) {
		super(chatroom.getId(), chatroom.getName(), "Team", chatroom.getChatServers());
	}

	@Override
	public boolean addChatServer(IChatServer chatStub) {
		return _stubList.add(chatStub);
	}

	@Override
	public boolean removeChatServer(IChatServer chatServer) {

		return _stubList.remove(chatServer);
	}

	@Override
	public <S> void send(OurDataPacket<? extends IChatMsg, S> dp) {
		for (IChatServer stub : _stubList) {
			try {
				stub.receive(dp);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean addPlayer(IPlayer player) {
		return playerList.add(player);

	}

	public boolean removePlayer(IPlayer player) {
		return playerList.remove(player);

	}

	/**
	 * send datapacket to players instead of chatserver after game is instantiated
	 * @param dp
	 */
	public void sendToTeam(OurDataPacket<? extends IChatMsg, IServer> dp) {
		for (IPlayer stub : playerList) {
			try {
				stub.receive(dp);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public int getScore() {
		return score;
	}

	
	/**
	 * add score to original score
	 * @param offset the score add to
	 */
	public void changeScore(int offset) {
		score += offset;
	}

	public void setScore() {

	}

	public Color getColor() {
		return _color;
	}

}
