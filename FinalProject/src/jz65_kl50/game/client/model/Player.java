package jz65_kl50.game.client.model;

import java.rmi.RemoteException;
import java.util.UUID;

import common.IChatServer;
import common.IChatroom;
import common.IUser;
import common.OurDataPacket;
import common.msg.IChatMsg;
import jz65_kl50.game.IPlayer;
import jz65_kl50.game.Team;
import provided.datapacket.DataPacketAlgo;

/**
 * The Concreate class of player
 * @author zhouji
 *
 */
public class Player implements IPlayer {

	private IUser _userStub;
	private Team _cr;
	private transient IPlayer2ModelApdater _adpt;
	private transient DataPacketAlgo<Void, Object> _algo;

	/**
	 * Constructor of player
	 * @param t the team the player in
	 * @param player the original chatserver of this player
	 * @param i2ModelApdater the adapter to model
	 */
	public Player(Team t,IChatServer player, IPlayer2ModelApdater i2ModelApdater) {

		try {
			_userStub = player.getUser();
			_cr = t;
			_adpt = i2ModelApdater;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public <S> void receive(OurDataPacket<? extends IChatMsg, S> dp) throws RemoteException {
		dp.execute(_algo, _adpt);

	}

	@Override
	public IUser getUser() throws RemoteException {
		// TODO Auto-generated method stub
		return _userStub;
	}

	@Override
	public IChatroom getChatroom() throws RemoteException {
		// TODO Auto-generated method stub
		return _cr;
	}

	/**
	 * set the algo for this player
	 * @param algo the algo of this player
	 */
	public void setAlgo(DataPacketAlgo<Void, Object> algo) {
		_algo = algo;

	}

	@Override
	public Team getTeam() throws RemoteException {
		// TODO Auto-generated method stub
		return _cr;
	}
	
	/**
	 * Overriden equals() method to simply compare UUID
	 * @return  Equal if the UUID are the same.  False otherwise.
	 */
	@Override
	public boolean equals(Object other){
		try {
			return this.getUser().getId() == ((IChatServer) other).getUser().getId();
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Show the name and IP address of the player
	 */
	@Override
	public String toString() {
		try {
			return this.getUser().getName() + "@" + this.getUser().getIP();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int hashCode() {
		UUID id=null;
		try {
			id = this.getUser().getId();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return id.hashCode();
		
	}

}
