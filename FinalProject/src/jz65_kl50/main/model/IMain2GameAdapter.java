package jz65_kl50.main.model;

/**
 * The adapter from main MVC to game MVC
 * @author zhouji, kejun liu
 *
 */
public interface IMain2GameAdapter  {

	/**
	 * this method is used when main MVC is closing and stopping its child game MVC
	 */
	public void stop();

}
