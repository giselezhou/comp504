package jz65_kl50.gamelobby.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * The frame of game lobby
 * @author zhouji, kejun liu
 *
 */
public class GameLobbyFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9163082991104233965L;
	private ILobbyView2ModelAdapter _view2ModelAdapter;
	private JTextField invite_txt;
	private JPanel display_pnl;
	private JPanel ctrl_pnl;
	private JPanel teamPanel;

	private JButton btnInvite;
	private JButton btnSendGame;
	private JButton btnLeave;
	
	private JLabel lblTeam;
	private DefaultListModel<String> listModel;
	private JList<String> member_list;

	public GameLobbyFrame(ILobbyView2ModelAdapter view2ModelAdapter) {
		_view2ModelAdapter = view2ModelAdapter;
		initGUI();
	}

	private void initGUI() {
		getContentPane().setLayout(new BorderLayout(0, 0));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);


		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				stop();
			}
		});
		ctrl_pnl = new JPanel();
		ctrl_pnl.setToolTipText("The panel to do some controls.");
		getContentPane().add(ctrl_pnl, BorderLayout.NORTH);
		setBounds(110, 110, 510, 380);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				stop();
			}
		});

		display_pnl = new JPanel();
		getContentPane().add(display_pnl);
		ctrl_pnl.setToolTipText("The panel to do some controls.");
		ctrl_pnl.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		invite_txt = new JTextField();
		ctrl_pnl.add(invite_txt);
		invite_txt.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "IP address",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		invite_txt.setColumns(11);

		btnInvite = new JButton("Invite");
		ctrl_pnl.add(btnInvite);
		btnInvite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String remoteIP = invite_txt.getText();
				if (!isValidIP(remoteIP)) {
					Toolkit.getDefaultToolkit().beep();
					invite_txt.requestFocusInWindow();
					JOptionPane.showMessageDialog(getContentPane(), "REMOTE IP IS NOT VALID!");
					return;
				}

				_view2ModelAdapter.invite(remoteIP);

			}

		});


		display_pnl.setLayout(new GridLayout(0, 1, 0, 0));

		listModel = new DefaultListModel<String>();
		member_list = new JList<String>(listModel);

		member_list.setVisibleRowCount(12);
		JScrollPane member_scroll = new JScrollPane(member_list);
		member_scroll.setBorder(
				new TitledBorder(null, "Room Member List", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		display_pnl.add(member_scroll);

		teamPanel = new JPanel();
		getContentPane().add(teamPanel, BorderLayout.SOUTH);
		lblTeam = new JLabel("");
		FlowLayout fl_teamPanel = new FlowLayout(FlowLayout.LEFT, 5, 5);
		teamPanel.setLayout(fl_teamPanel);

		btnSendGame = new JButton("Send Game");
		teamPanel.add(btnSendGame);
		btnSendGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_view2ModelAdapter.sendGame();
			}
		});

		btnLeave = new JButton("Leave");
		teamPanel.add(btnLeave);
		btnLeave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				stop();
			}

		});

		teamPanel.add(lblTeam);

	}

	/**
	 * start this chatroom GUI
	 */
	public void start() {
		setVisible(true);
		setTitle(_view2ModelAdapter.getChatroomName());
	}

	/**
	 * leave this chatroom
	 */
	public void stop() {
		_view2ModelAdapter.stop();
		this.dispose();
	}

	/**
	 * get the pane that shows member
	 * 
	 * @return the pane that shows member
	 */
	public Container getMemberPane() {
		return display_pnl;
	}

	/**
	 * add a member to the member list
	 * 
	 * @param s
	 *            the string that can describe a user
	 */
	public void addMember(String s) {
		listModel.addElement(s);
	}

	/**
	 * remove a member to the member list
	 * 
	 * @param s
	 *            the string that can describe a user
	 */
	public void removeMember(String s) {
		listModel.removeElement(s);
	}
	private boolean isValidIP(String ip) {

		boolean b = false;
		String IP = ip.trim();
		if (IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) {
			String s[] = IP.split("\\.");
			if (Integer.parseInt(s[0]) < 255)
				if (Integer.parseInt(s[1]) < 255)
					if (Integer.parseInt(s[2]) < 255)
						if (Integer.parseInt(s[3]) < 255)
							b = true;
		}
		return b;
	}
}
