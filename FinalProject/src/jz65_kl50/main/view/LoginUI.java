package jz65_kl50.main.view;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.SystemColor;

/**
 * The login frame
 * @author zhouji, kejun liu
 *
 */
public class LoginUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8635355844705057383L;
	private JPanel contentPane;
	private JTextField txtName;
	private JPanel panel;
	private JLabel lblUserName;
	private JPanel panel_1;
	private JLabel label;
	private JPanel panel_2;
	private ILoginAdapter _adpt;

	/**
	 * Create the login frame.
	 * @param iLoginModelAdapter the adpt to model
	 */
	public LoginUI(ILoginAdapter iLoginModelAdapter) {
		_adpt = iLoginModelAdapter;
		initGUI();
	}

	private void initGUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		panel_1 = new JPanel();
		panel_1.setBorder(null);
		panel_1.setBackground(SystemColor.window);
		panel.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		lblUserName = new JLabel("User Name:");
		panel_1.add(lblUserName);

		InetAddress addr;
		try {
			addr = InetAddress.getLocalHost();
			final String ip = addr.getHostAddress();
			txtName = new JTextField();
			txtName.setToolTipText("input your user name");
			panel_1.add(txtName);
			txtName.setColumns(10);

			label = new JLabel(ip);
			panel_1.add(label);

			panel_2 = new JPanel();
			panel_2.setBorder(null);
			panel_2.setBackground(SystemColor.window);
			contentPane.add(panel_2, BorderLayout.SOUTH);
			panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

			JButton btnLogin = new JButton("Login");
			btnLogin.setToolTipText("click to login");
			panel_2.add(btnLogin);
			btnLogin.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String username = txtName.getText();
					_adpt.initUser(username, ip);

				}
			});
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	/**
	 *  start the login view;
	 */
	public void start() {
		setVisible(true);
	}

	/**
	 * stop this view
	 */
	public void stop() {
		setVisible(false);
		this.dispose();
		
	}

}
