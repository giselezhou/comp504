package jz65_kl50.game.client.view;

import gov.nasa.worldwind.globes.Globe;
import map.MapPanel;


/**
 * a class encapsulates MapPanel to set select listener
 * @author zhouji, kejun liu
 *
 */
public class MyMapPanel extends MapPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8342661033234949875L;

	public MyMapPanel(Class<? extends Globe> globeTypeClass) {
		super(globeTypeClass);
	}



}
