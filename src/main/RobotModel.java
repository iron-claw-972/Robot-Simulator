package main;

import static main.Calc.*;
import javax.swing.*;
import java.awt.Polygon;

public class RobotModel {
	private double x;
	private double y;
	private double angle;
	
	private double width; //all in feet
	private double length;
	private double hypot;
	private double theta;
	
	private double maxSpeed; //in ft/ms
	private MainWindow frame;
	private DriveMode drive = DriveMode.TANK_DRIVE;
	private InputMode input = InputMode.KEYBOARD;
	
	private RobotPosition nextPos;
	
	public RobotModel(double x, double y, double angle, double width, double length, double maxSpeed, MainWindow frame) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		
		this.width = width;
		this.length = length;
		hypot = Math.hypot(width, length)/2;
		theta = Math.atan(width/length);
		
		this.maxSpeed = maxSpeed/40;
		this.frame = frame;
	}
	
	public Polygon getPolygon() {
		return new Polygon(new int[] {convertf(x+hypot*Math.cos(angle+theta)), convertf(x-hypot*Math.cos(-angle+theta)), convertf(x-hypot*Math.cos(angle+theta)), convertf(x+hypot*Math.cos(-angle+theta))},
				new int[] {convertf(y+hypot*Math.sin(angle+theta)), convertf(y+hypot*Math.sin(-angle+theta)), convertf(y-hypot*Math.sin(angle+theta)), convertf(y-hypot*Math.sin(-angle+theta))},
				4);
	}
	
	public int getFrontX() {
		return convertf(x+ Math.cos(angle) * length/4);
	}
	
	public int getFrontY() {
		return convertf(y+ Math.sin(angle) * length/4);
	}
	
	public void getNextPosition() {
		nextPos = drive.nextPosition(this, input.getSpeeds());
	}
	
	public void updatePosition() {
		setPosition(nextPos);
	}
	
	public void setPosition(RobotPosition pos) {
		x = pos.getX();
		y= pos.getY();
		angle = pos.getAngle();
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getAngle() {
		return angle;
	}
	
	public double getWidth() {
		return width;
	}
	
	public double getMaxSpeed() {
		return maxSpeed;
	}
	
	public MainWindow getFrame() {
		return frame;
	}
}
