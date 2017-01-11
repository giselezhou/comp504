package jz65_kl50.msg;

import java.util.UUID;

import common.msg.chat.INewCmdReqMsg;

/**
 * A concrete class of new cmd req message
 * @author zhouji, kejun liu
 *
 */
public class NewCmdReqMsg implements INewCmdReqMsg {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 934167866086916088L;
	private Class<?> _reqClass;
	private UUID _id;
	
	/**
	 * The constructor of this message
	 * @param reqClass the type of message
	 * @param id the unprocessed message id
	 */
	public NewCmdReqMsg(Class<?> reqClass, UUID id){
		_reqClass=reqClass;
		_id=id;
	}
	@Override
	public Class<?> getReqClassIdx() {
		// TODO Auto-generated method stub
		return _reqClass;
	}

	@Override
	public UUID getUUID() {
		// TODO Auto-generated method stub
		return _id;
	}

}
