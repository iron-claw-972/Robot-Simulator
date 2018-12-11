package main;

import java.awt.*;

public abstract class DriveMode {
	
	public static final DriveMode TANK_DRIVE = new DriveMode() {
		@Override
		public RobotPosition nextPosition(RobotModel robot, double... axes) {
			double left = axes[0] * robot.getMaxSpeed();
			double right = axes[2] * robot.getMaxSpeed();
			RobotPosition position;
			
			if(left == right) {
				position = new RobotPosition(robot.getX() + left * Math.cos(robot.getAngle()),
						robot.getY() + left * Math.sin(robot.getAngle()),
						robot.getAngle());
			} else if((left<0 && right<=0) || (right<0 && left<=0)) {
				double theta = (left - right)/robot.getWidth();
				double r = (right * robot.getWidth())/(left-right);
				double newX = robot.getX() - Math.cos(theta/2+robot.getAngle()) * Math.sqrt(2*Math.pow(r+robot.getWidth()/2,2)*(1-Math.cos(theta)));
				double newY = robot.getY() - Math.sin(theta/2+robot.getAngle()) * Math.sqrt(2*Math.pow(r+robot.getWidth()/2,2)*(1-Math.cos(theta)));
				position = new RobotPosition(newX, newY, robot.getAngle()+theta);
			} else {
				double theta = (left - right)/robot.getWidth();
				double r = (right * robot.getWidth())/(left-right);
				double newX = robot.getX() + Math.cos(theta/2+robot.getAngle()) * Math.sqrt(2*Math.pow(r+robot.getWidth()/2,2)*(1-Math.cos(theta)));
				double newY = robot.getY() + Math.sin(theta/2+robot.getAngle()) * Math.sqrt(2*Math.pow(r+robot.getWidth()/2,2)*(1-Math.cos(theta)));
				position = new RobotPosition(newX, newY, robot.getAngle()+theta);
			}
			
			if(touchingWall(robot, position) && Main.window.getWallCollision()) {
				return robot.getPosition();
			} else {
				return position;
			}
		}
	};
	
	public static final DriveMode ARCADE_DRIVE = new DriveMode() {
		@Override
		public RobotPosition nextPosition(RobotModel robot, double... axes) {
			double left = axes[0] * robot.getMaxSpeed();
			double right = axes[0] * robot.getMaxSpeed();
			RobotPosition position;
			
			if(axes[3]>0) {
				right *= 1-axes[3];
			} else if(axes[3]<0) {
				left *= 1+axes[3];
			}
			
			if(left == right) {
				position = new RobotPosition(robot.getX() + left * Math.cos(robot.getAngle()),
						robot.getY() + left * Math.sin(robot.getAngle()),
						robot.getAngle());
			} else if((left<0 && right<=0) || (right<0 && left<=0)) {
				double theta = (left - right)/robot.getWidth();
				double r = (right * robot.getWidth())/(left-right);
				double newX = robot.getX() - Math.cos(theta/2+robot.getAngle()) * Math.sqrt(2*Math.pow(r+robot.getWidth()/2,2)*(1-Math.cos(theta)));
				double newY = robot.getY() - Math.sin(theta/2+robot.getAngle()) * Math.sqrt(2*Math.pow(r+robot.getWidth()/2,2)*(1-Math.cos(theta)));
				position = new RobotPosition(newX, newY, robot.getAngle()+theta);
			} else {
				double theta = (left - right)/robot.getWidth();
				double r = (right * robot.getWidth())/(left-right);
				double newX = robot.getX() + Math.cos(theta/2+robot.getAngle()) * Math.sqrt(2*Math.pow(r+robot.getWidth()/2,2)*(1-Math.cos(theta)));
				double newY = robot.getY() + Math.sin(theta/2+robot.getAngle()) * Math.sqrt(2*Math.pow(r+robot.getWidth()/2,2)*(1-Math.cos(theta)));
				position = new RobotPosition(newX, newY, robot.getAngle()+theta);
			}
			
			if(touchingWall(robot, position) && Main.window.getWallCollision()) {
				return robot.getPosition();
			} else {
				return position;
			}
		}
	};
	
	public abstract RobotPosition nextPosition(RobotModel robot, double... axes);
	
	public boolean touchingWall(RobotModel newRobot, RobotPosition position) {
		RobotModel robot = newRobot.clone();
		robot.setPosition(position);
		Polygon polygon = robot.getPolygon();
		return polygon.intersects(0, -1, Main.window.getContentPane().getWidth(), 1) || 
				polygon.intersects(0, Main.window.getContentPane().getHeight(), Main.window.getContentPane().getWidth(), 1) || 
				polygon.intersects(-1, 0, 1, Main.window.getContentPane().getHeight()) || 
				polygon.intersects(Main.window.getContentPane().getWidth(), 0, 1, Main.window.getContentPane().getHeight());
	}
}
