package jz65_kl50.main.view;

/**
 * An adapter from Login view to main model;
 * @author zhouji, kejun liu
 *
 */
public interface ILoginAdapter {


	/**
	 * instantiates the model of local user with the input name and local ip address
	 * @param username the input name
	 * @param ip local ip address
	 */
	public void initUser(String username, String ip);

}
