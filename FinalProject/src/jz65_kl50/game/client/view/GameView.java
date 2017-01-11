package jz65_kl50.game.client.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import gov.nasa.worldwind.event.SelectEvent;
import gov.nasa.worldwind.event.SelectListener;
import gov.nasa.worldwind.globes.Earth;
import map.MapLayer;
import map.ToggleAnnotation;

import javax.swing.JLabel;

/**
 * The view of the game
 * @author zhouji, kejun liu
 *
 */
public class GameView extends JFrame {

	
	/**
	 * Constructor of the game view
	 * @param adpt the adapter from view to model
	 */
	public GameView(IGameView2ModelAdapter adpt) {
		_model = adpt;
		getContentPane().setPreferredSize(new Dimension(900, 500));
		initGUI();

	}

	private void initGUI() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				stop();
			}
		});
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 600, 300 };
		gridBagLayout.rowHeights = new int[] { 400, 100 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0 };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0 };
		getContentPane().setLayout(gridBagLayout);

		_txtPanel = new JPanel();
		_txtPanel.setSize(new Dimension(400, 100));
		GridBagConstraints gbc__txtPanel = new GridBagConstraints();
		gbc__txtPanel.anchor = GridBagConstraints.NORTHWEST;
		gbc__txtPanel.gridx = 0;
		gbc__txtPanel.gridy = 1;
		getContentPane().add(_txtPanel, gbc__txtPanel);
		_txtPanel.setLayout(new BorderLayout(0, 0));

		_txtArea = new JTextArea();
		_txtArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					e.consume();
					btnSendText.doClick();

				}
			}

		});
		_txtArea.setBorder(
				new TitledBorder(null, "Message to teammate:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		_txtArea.setPreferredSize(new Dimension(600, 80));
		_txtArea.setSize(new Dimension(380, 80));
		_txtArea.setColumns(10);
		_txtPanel.add(_txtArea);

		_msgPanel = new JPanel();
		_msgPanel.setBorder(null);
		_msgPanel.setSize(new Dimension(300, 400));
		GridBagConstraints gbc__msgPanel = new GridBagConstraints();
		gbc__msgPanel.weightx = 0.33;
		gbc__msgPanel.anchor = GridBagConstraints.NORTHWEST;
		gbc__msgPanel.gridx = 1;
		gbc__msgPanel.gridy = 0;
		getContentPane().add(_msgPanel, gbc__msgPanel);
		_msgPanel.setLayout(new BorderLayout(0, 0));

		scrollPane = new JScrollPane();
		scrollPane.setViewportBorder(
				new TitledBorder(null, "Message", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(300, 400));
		_msgPanel.add(scrollPane);

		_msgArea = new JTextArea();
		_msgArea.setLineWrap(true);
		_msgArea.setEditable(false);
		_msgArea.setPreferredSize(new Dimension(140, 380));
		scrollPane.setViewportView(_msgArea);
		_mapPanel = new MyMapPanel(Earth.class) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 7262486164710193123L;

			private SelectListener _l;

			/**
			 * Add a select listener to toggle ToggleAnnotation objects
			 */
			@Override
			public void setupAnnotationToggling() {

				getWWD().addSelectListener(new SelectListener() {
					public void selected(SelectEvent event) {
						if (event.getEventAction().equals(SelectEvent.LEFT_CLICK)) {
							if (event.hasObjects()) {
								Object obj = event.getTopObject();
								if (obj instanceof ToggleAnnotation) {
									ToggleAnnotation annotation = (ToggleAnnotation) obj;
									_model.notifySelected(annotation);
								}
							}
						}
					}
				});
			}
		};

		GridBagConstraints gbc__mapPanel = new GridBagConstraints();
		gbc__mapPanel.weightx = 0.66;
		gbc__mapPanel.anchor = GridBagConstraints.NORTHWEST;
		gbc__mapPanel.gridx = 0;
		gbc__mapPanel.gridy = 0;
		getContentPane().add(_mapPanel, gbc__mapPanel);
		_mapPanel.setPreferredSize(new java.awt.Dimension(600, 400));

		_ctrlPanel = new JPanel();
		GridBagConstraints gbc__ctrlPanel = new GridBagConstraints();
		gbc__ctrlPanel.fill = GridBagConstraints.BOTH;
		gbc__ctrlPanel.gridx = 1;
		gbc__ctrlPanel.gridy = 1;
		getContentPane().add(_ctrlPanel, gbc__ctrlPanel);
		_ctrlPanel.setLayout(new BorderLayout(0, 0));

		lblTime = new JLabel("");
		_ctrlPanel.add(lblTime, BorderLayout.SOUTH);

		lblScore = new JLabel("");
		_ctrlPanel.add(lblScore);

		panel = new JPanel();
		_ctrlPanel.add(panel, BorderLayout.NORTH);

		btnStart = new JButton("Start");
		panel.add(btnStart);

		btnSendText = new JButton("Send Text");
		panel.add(btnSendText);
		btnSendText.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendText();
			}
		});
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				_model.sendStartGame();
			}
		});

		pack();

	}

	/**
	 * stop the game model and view
	 */
	public void stop() {

		_model.stop();
		this.setVisible(false);
	}

	private MyMapPanel _mapPanel;
	private IGameView2ModelAdapter _model;
	private JPanel _msgPanel;
	private JPanel _txtPanel;
	private JPanel _ctrlPanel;
	/**
	 * 
	 */
	private static final long serialVersionUID = 833092388766564977L;
	private JTextArea _txtArea;
	private JScrollPane scrollPane;
	private JTextArea _msgArea;
	private JButton btnSendText;
	private JButton btnStart;
	private JLabel lblTime;
	private ArrayList<ToggleAnnotation> _annoList;
	private MapLayer _layer;
	private JLabel lblScore;
	private JPanel panel;

	/**
	 * start the game view
	 */
	public void start() {
		_mapPanel.start();
		_layer = new MapLayer();
		_mapPanel.addLayer(_layer);
		_annoList = new ArrayList<ToggleAnnotation>();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void sendText() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				_model.sendText(_txtArea.getText());
				_txtArea.setText("");
			}

		}).start();

	}

	/**
	 * used by the adapter from model to view when append text message to view
	 * @param txt
	 */
	public void append(String txt) {
		_msgArea.append(txt);

	}

	/**
	 * used by the adapter from model to view updating remaining game time
	 * @param time
	 */
	public void updateTime(long time) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				long second = (time / 1000) % 60;
				long minute = ((time / 1000) - second) / 60;
				if (minute == 0 & second == 0) {
					lblTime.setForeground(Color.red);
					lblTime.setBackground(Color.BLUE);
				}
				lblTime.setText(minute + " : " + second);

			}
		}).start();

	}

	/**
	 * used by the adapter from model to view when game starts to set "start" button disabled
	 * @param enable
	 */
	public void setStartButton(boolean enable) {
		btnStart.setEnabled(enable);

	}

	/**
	 * used by the adapter from model to view to get the map layer
	 * @return the map layer
	 */
	public MapLayer getMapLayer() {
		// TODO Auto-generated method stub
		return _layer;
	}

	/**
	 * used by the adapter from model to view to add an annotation
	 * @param t_anno the annotation to add
	 */
	public void addAnnotation(ToggleAnnotation t_anno) {

		_annoList.add(t_anno);
		_layer.addAnnotation(t_anno);

	}

	/**
	 * used by the adapter from model to view to tell view stop game
	 * @param score the score of the team the player in
	 */
	public void stop(int score) {

		btnStart.setEnabled(true);
		updateTime(0);
		new Thread(new Runnable() {

			@Override
			public void run() {
				lblScore.setText("Your team scored " + score + " !");

			}

		}).start();
		_mapPanel.removeLayer(_layer);
		_layer = new MapLayer();
		_mapPanel.addLayer(_layer);

	}
}
