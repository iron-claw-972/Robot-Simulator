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
	private DriveMode drive = DriveMode.ARCADE_DRIVE;
	private InputMode input;
	
	private RobotPosition nextPos;
	
	public RobotModel(double x, double y, double angle, double width, double length, double maxSpeed) {
		this.x = x;
		this.y = y;
		this.angle = angle;
		
		this.width = width;
		this.length = length;
		updateHypot();
		
		this.maxSpeed = maxSpeed/40;
		
		input = new InputMode.Keyboard('w', 'a', 's', 'd', 'i', 'j', 'k', 'l');
	}
	
	public RobotModel() {}
	
	public Polygon getPolygon() {
		return new Polygon(new int[] {convertf(x+hypot*Math.cos(angle+theta)), convertf(x-hypot*Math.cos(-angle+theta)), convertf(x-hypot*Math.cos(angle+theta)), convertf(x+hypot*Math.cos(-angle+theta))},
				new int[] {convertf(y+hypot*Math.sin(angle+theta)), convertf(y+hypot*Math.sin(-angle+theta)), convertf(y-hypot*Math.sin(angle+theta)), convertf(y-hypot*Math.sin(-angle+theta))},
				4);
	}
	
	public RobotModel clone() {
		return new RobotModel(x, y, angle, width, length, maxSpeed);
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
	
	public RobotPosition getPosition() {
		return new RobotPosition(x, y, angle);
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
	
	public double getLength() {
		return length;
	}
	
	public void setWidth(double width) {
		this.width = width;
	}
	
	public void setLength(double length) {
		this.length = length;
	}
	
	public void updateHypot() {
		hypot = Math.hypot(length, width)/2;
		theta = Math.atan(width/length);
	}
	
	public double getMaxSpeed() {
		return maxSpeed;
	}
	
	public void setMaxSpeed(double speed) {
		maxSpeed = speed/40.0;
	}
	
	public void setDriveMode(DriveMode drive) {
		this.drive = drive;
	}
}
