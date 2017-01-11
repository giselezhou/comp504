package jz65_kl50.main.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import common.IChatroom;
import jz65_kl50.gamelobby.view.GameLobbyFrame;

/**
 * Main frame of chatroom with some system log and a field of create new
 * chatroom
 * 
 * @author zhouji,kejun liu
 *
 */
public class AppFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2398164006591763439L;

	/**
	 * The constructor of AppFrame 
	 * @param iModelAdapter the adapter from view to model
	 * @param ip local ip address
	 * @param username the user name
	 */
	public AppFrame(IModelAdapter iModelAdapter, String ip, String username) {
		_username = username;
		_IP = ip;
		_adpt = iModelAdapter;
		initGUI();
	}

	private void initGUI() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 510, 380);

		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				quit();
			}
		});
		if (txtRoomName.getText().isEmpty())
			btnCreate.setEnabled(false);
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));

		contentPane.add(upperPanel);
		upperPanel.setLayout(new GridLayout(0, 1, 0, 0));
		txtRoomName.setToolTipText("input chat room name");
		txtRoomName.setColumns(10);
		upperPanel.add(createRoomPanel);
		createRoomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		createRoomPanel.add(lblRoomName);

		createRoomPanel.add(txtRoomName);
		txtRoomName.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				btnCreate.setEnabled(true);

			}

		});
		btnCreate.setToolTipText("create this chat room");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String roomName = txtRoomName.getText();
				if (roomName == null || roomName.isEmpty()) {
					btnCreate.setEnabled(false);
					return;
				}
				_adpt.createGameLobby(roomName);
			}
		});
		createRoomPanel.add(btnCreate);

		upperPanel.add(connectPanel);
		JScrollPane scroll = new JScrollPane();
		scroll.setAutoscrolls(true);
		scroll.setViewportView(outputTA);

		outputTA.setBorder(new TitledBorder(null, "System Log", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		outputTA.setLineWrap(true);
		outputTA.setEditable(false);

		contentPane.add(logPanel);
		logPanel.setLayout(new BorderLayout(0, 0));
		logPanel.add(scroll);
		lblIPAdd = new JLabel("IP Address : " + _IP);
		lblUserName = new JLabel("User Name : " + _username);
		logPanel.add(localInfoPanel, BorderLayout.SOUTH);
		localInfoPanel.add(lblIPAdd);
		localInfoPanel.add(lblUserName);

		connectPanel.add(lblRemoteIp);
		txtRemoteIP.setColumns(10);
		connectPanel.add(txtRemoteIP);

		txtRemoteIP.addKeyListener(new KeyAdapter() {

			@Override
			public void keyTyped(KeyEvent e) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						btnConnect.setEnabled(true);
						lblPrompt.setText("");
					}
				}).start();
				;
			}

		});
		btnConnect.setEnabled(false);
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						String remoteIP = txtRemoteIP.getText();
						if (!isValidIP(remoteIP)) {
							Toolkit.getDefaultToolkit().beep();
							txtRemoteIP.requestFocusInWindow();
							lblPrompt.setText("REMOTE IP IS NOT VALID!");
							lblPrompt.setOpaque(true);
							lblPrompt.setBackground(Color.ORANGE);
							btnConnect.setEnabled(false);
							return;
						}
						HashSet<IChatroom> list = _adpt.connectTo(remoteIP);

						int index = roomCombo.getItemCount();

						for (IChatroom cr : list) {
							roomCombo.insertItemAt(cr, index++);
						}

					}
				}).start();

			}
		});

		roomCombo.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {

				btnJoin.setEnabled(e.getStateChange() == ItemEvent.SELECTED);

			}
		});

		connectPanel.add(btnConnect);
		panel.add(lblPrompt);

		joinPanel.add(roomCombo);

		btnJoin.setEnabled(false);
		btnJoin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IChatroom cr = (IChatroom) roomCombo.getSelectedItem();
				_adpt.joinRoom(cr);
			}
		});

		upperPanel.add(panel);
		joinPanel.add(btnJoin);

		upperPanel.add(joinPanel);

	}

	private IModelAdapter _adpt;
	private final JPanel contentPane = new JPanel();
	private final JPanel createRoomPanel = new JPanel();
	private final JLabel lblRoomName = new JLabel("Game Lobby Name :");
	private final JTextField txtRoomName = new JTextField();
	private final JButton btnCreate = new JButton("CREATE");
	private JTextArea outputTA = new JTextArea();

	private final JPanel localInfoPanel = new JPanel();
	private JLabel lblIPAdd;
	private JLabel lblUserName;
	private String _username;
	private String _IP;

	private final JTextField txtRemoteIP = new JTextField();
	private final JPanel joinPanel = new JPanel();
	private final JButton btnJoin = new JButton("JOIN");
	private JComboBox<IChatroom> roomCombo = new JComboBox<IChatroom>();
	private final JPanel logPanel = new JPanel();
	private HashMap<UUID, GameLobbyFrame> lobbyList = new HashMap<>();
	private final JPanel upperPanel = new JPanel();

	private final JPanel connectPanel = new JPanel();
	private final JLabel lblRemoteIp = new JLabel("Remote IP : ");
	private final JButton btnConnect = new JButton("CONNECT");
	private JLabel lblPrompt = new JLabel("");
	private final JPanel panel = new JPanel();

	/**
	 * Append the given string(s) to the view's output text adapter.
	 * 
	 * @param s
	 *            the string to display.
	 */
	public void append(String s) {
		outputTA.append(s);
		// Force the JScrollPane to go to scroll down to the new text
		outputTA.setCaretPosition(outputTA.getText().length());
	}

	/**
	 * Starts the already initialized frame, making it visible and ready to
	 * interact with the user.
	 */
	public void start() {
		setVisible(true);
	}

	/**
	 * Centralized management point for exiting the application. All calls to
	 * exit the system should go through here. Shuts system down by stopping the
	 * model.
	 */
	public void quit() {
		System.out.println(_IP + " is quitting...");
		_adpt.quit();

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

	/**
	 * installs the game lobby view to main view
	 * @param _view
	 * @param id
	 */
	public void install(GameLobbyFrame _view, UUID id) {
		lobbyList.put(id, _view);

	}

	/**
	 * add a component in scrollable place
	 * @param make
	 */
	public void addComponentInScrollable(JComponent make) {
		JFrame frame = new JFrame();
		JScrollPane pane = new JScrollPane();
		pane.setViewportView(make);
		frame.setLayout(new BorderLayout());
		frame.add(pane, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * add a component in non-scrollable place
	 * @param make
	 */
	public void addComponentInNonScrollable(JComponent make) {
		JFrame frame = new JFrame();

		frame.setLayout(new BorderLayout());
		frame.add(make, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);

	}

}
