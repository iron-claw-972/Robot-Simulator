package main;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import static main.Calc.*;

public class MainWindow extends JFrame {
	private RobotModel robot;
	private Toolkit toolkit;
	private JPanel pane;
	private ArrayList<Character> keysPressed;
	
	private boolean wallCollision = false;
	
	private Color robotColor;
	
	private JMenuBar menuBar;
	private JMenu stageMenu;
	private JCheckBoxMenuItem wallCollisionMenuItem;
	private JPopupMenu robotPopupMenu;
	private JMenuItem robotColorMenuItem;
	private JMenu robotDriveMenu;
	private JRadioButtonMenuItem tankDriveMenuItem;
	private JRadioButtonMenuItem arcadeDriveMenuItem;
	private JMenuItem maxSpeedMenuItem;
	private JMenuItem dimensionsMenuItem;
	
	public MainWindow() {
		super("Robot Simulator v.-9999.232");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		toolkit = Toolkit.getDefaultToolkit();
		setSize(toolkit.getScreenSize());
		setResizable(false);
		robotColor = Color.RED;
		
		//Paint the Robot on the JPanel
		pane = new JPanel() {
			{
				addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if(robot.getPolygon().contains(e.getPoint()) && SwingUtilities.isRightMouseButton(e)) {
							robotPopupMenu.show(MainWindow.this, e.getX()+5, e.getY()+5+robotPopupMenu.getHeight()/2);
						};
					}
				});
			}
			
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(robotColor);
				g.fillPolygon(robot.getPolygon());
				g.setColor(Color.BLACK);
				g.fillOval(robot.getFrontX()-5, robot.getFrontY()-5, 10, 10);
			}
		};
		setContentPane(pane);
		pane.setBackground(Color.WHITE);
		
		robot = new RobotModel(convertp((int)(toolkit.getScreenSize().getWidth()/2)), convertp((int)(toolkit.getScreenSize().getHeight()/2)), 0.0,
				2.0, 2.5, 5);
		
		//For detecting which chars are pressed in keysPressed
		keysPressed = new ArrayList<>();
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(!keysPressed.contains(new Character(e.getKeyChar()))) {
					keysPressed.add(e.getKeyChar());
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				keysPressed.remove(new Character(e.getKeyChar()));
			}
		});
		
		createMenus();
		
		//Update Robot Position Periodically
		new Thread(new Runnable() {
			public void run() {
				while(true) {
					try {Thread.sleep(25);} catch(Exception e) {System.exit(0);}
					robot.getNextPosition();
					robot.updatePosition();
					pane.repaint();
				}
			}
		}).start();
		
		addKeyListener(new RainbowListener());
		
		
		
	}
	
	private class RainbowListener extends KeyAdapter {
		private boolean pressed9 = false;
		private boolean pressed7 = false;
		
		
		public void keyPressed(KeyEvent event) {
			switch (event.getKeyChar()) {
				case '2':
					if(pressed9 && pressed7) {
						MainWindow.this.rainbow();
						MainWindow.this.removeKeyListener(this);
					}
					break;
				case '7':
					if(pressed9) {
						pressed7 = true;
					}
					break;
				case '9':
					pressed9 = true;
					break;
			}
		}
		
		public void keyReleased(KeyEvent event) {
			switch (event.getKeyChar()) {
				case '7':
					pressed7 = false;
					break;
				case '9':
					pressed7 = false;
					pressed9 = false;
					break;
			}
		}
	}

	
	public void rainbow() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				float cycle = 0.00f;
				float saturation = 0.00f;
				while(true) {
					pane.setBackground(Color.getHSBColor(cycle, saturation, 1.0f));
					
					cycle+= 0.0050;
					if(cycle>=1.0) {
						cycle = 0.0f;
					}
					if(saturation<0.3) {
						saturation +=0.01;
					}
					try {Thread.sleep(40);} catch(Exception e) {System.exit(0);}
				}
			}
		}).start();
	}
	
	public ArrayList<Character> getKeysPressed() {
		return keysPressed;
	}
	
	public boolean getWallCollision() {
		return wallCollision;
	}
	
	public void createMenus() {
		menuBar = new JMenuBar() {
			public Insets getInsets() {
				return new Insets(0, 10, 0, 0);
			}
		};
		
		stageMenu = new JMenu("Stage");
		menuBar.add(stageMenu);
		
		wallCollisionMenuItem = new JCheckBoxMenuItem("Wall Collision");
		wallCollisionMenuItem.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				wallCollision = !wallCollision;
			}
		});
		stageMenu.add(wallCollisionMenuItem);
		
		robotPopupMenu = new JPopupMenu("Robot");
		
		robotColorMenuItem = new JMenuItem("Change Color");
		robotColorMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				robotColor = JColorChooser.showDialog(MainWindow.this, "Choose Robot Color", robotColor);
			}
		});
		robotPopupMenu.add(robotColorMenuItem);
		
		robotDriveMenu = new JMenu("Drive Mode");
		tankDriveMenuItem = new JRadioButtonMenuItem("Tank Drive");
		arcadeDriveMenuItem = new JRadioButtonMenuItem("Arcade Drive");
		ButtonGroup group = new ButtonGroup();
		group.add(tankDriveMenuItem);
		group.add(arcadeDriveMenuItem);
		arcadeDriveMenuItem.setSelected(true);
		tankDriveMenuItem.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				robot.setDriveMode(DriveMode.TANK_DRIVE);
			}
		});
		arcadeDriveMenuItem.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				robot.setDriveMode(DriveMode.ARCADE_DRIVE);
			}
		});
		robotDriveMenu.add(tankDriveMenuItem);
		robotDriveMenu.add(arcadeDriveMenuItem);
		robotPopupMenu.add(robotDriveMenu);
		
		maxSpeedMenuItem = new JMenuItem("Change Max Speed");
		maxSpeedMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JSpinner spinner = new JSpinner(new SpinnerNumberModel(robot.getMaxSpeed()*40, 0.0, 983571056.0, 0.1));
				JLabel label = new JLabel("<html><sup>ft</sup>" + Character.toString((char) 9585) + "<sub>s</sub></html>");
				JPanel panel = new JPanel();
				panel.setLayout(new FlowLayout());
				panel.add(spinner);
				panel.add(label);
				int option = JOptionPane.showOptionDialog(null, panel, "Change Max Speed", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(), null, null);
				if (option == JOptionPane.OK_OPTION) {
				    robot.setMaxSpeed((double) spinner.getValue()); 
				}
			}
		});
		robotPopupMenu.add(maxSpeedMenuItem);
		
		dimensionsMenuItem = new JMenuItem("Change Dimensions");
		dimensionsMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel();
				panel.setLayout(new GridBagLayout());
				GridBagConstraints c = new GridBagConstraints();
				c.gridx=0; c.gridy=0; c.weightx=1; c.anchor=GridBagConstraints.LINE_END; c.insets = new Insets(0,0,0,10);
				panel.add(new JLabel("<html>Length <sub>(ft)</sub> :</html>"), c);
				c.gridx=0; c.gridy=1;
				panel.add(new JLabel("<html>Width <sub>(ft)</sub> :</html>"), c);
				c.gridx=1; c.gridy=0; c.weightx=5; c.fill=GridBagConstraints.HORIZONTAL; c.insets = new Insets(0,0,0,0);
				JSpinner lengthSpinner = new JSpinner(new SpinnerNumberModel(robot.getLength(), 0.1, 10.0, 0.1));
				panel.add(lengthSpinner, c);
				c.gridx=1; c.gridy=1;
				JSpinner widthSpinner = new JSpinner(new SpinnerNumberModel(robot.getWidth(), 0.1, 10.0, 0.1));
				panel.add(widthSpinner, c);
				int option = JOptionPane.showOptionDialog(null, panel, "Change Robot Dimensions", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, new ImageIcon(), null, null);
				if (option == JOptionPane.OK_OPTION) {
				    robot.setLength((double) lengthSpinner.getValue()); 
				    robot.setWidth((double) widthSpinner.getValue()); 
				    robot.updateHypot();
				}
			}
		});
		robotPopupMenu.add(dimensionsMenuItem);
		
		setJMenuBar(menuBar);
	}
}
