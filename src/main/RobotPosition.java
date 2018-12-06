package main;

public class RobotPosition {
	private double x;
	private double y;
	private double angle; //Right is 0 radians, positive clockwise
	
	public RobotPosition(double x, double y, double angle) {
		this.x = x;
		this.y = y;
		this.angle = angle;
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
}
