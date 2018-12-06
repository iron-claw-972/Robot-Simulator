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
	
	public MainWindow() {
		super("Robot Simulator v.-9999.232");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		toolkit = Toolkit.getDefaultToolkit();
		setSize(toolkit.getScreenSize());
		
		pane = new JPanel() {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				g.setColor(Color.RED);
				g.fillPolygon(robot.getPolygon());
				g.setColor(Color.BLACK);
				g.fillOval(robot.getFrontX()-5, robot.getFrontY()-5, 10, 10);
			}
		};
		setContentPane(pane);
		pane.setBackground(Color.WHITE);
		
		robot = new RobotModel(convertp((int)(toolkit.getScreenSize().getWidth()/2)), convertp((int)(toolkit.getScreenSize().getHeight()/2)), 0.0,
				2.0, 2.5, 4, this);
		
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
					if(saturation<0.2) {
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
	
	public MainWindow get() {
		return this;
	}
}
