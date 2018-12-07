package main;

public abstract class DriveMode {
	
	public static final DriveMode TANK_DRIVE = new DriveMode() {
		@Override
		public RobotPosition nextPosition(RobotModel robot, double... axes) {
			double left = axes[0] * robot.getMaxSpeed();
			double right = axes[2] * robot.getMaxSpeed();
			
			if(left == right) {
				return new RobotPosition(robot.getX() + left * Math.cos(robot.getAngle()),
						robot.getY() + left * Math.sin(robot.getAngle()),
						robot.getAngle());
			} else if((left<0 && right<=0) || (right<0 && left<=0)) {
				double theta = (left - right)/robot.getWidth();
				double r = (right * robot.getWidth())/(left-right);
				double newX = robot.getX() - Math.cos(theta/2+robot.getAngle()) * Math.sqrt(2*Math.pow(r+robot.getWidth()/2,2)*(1-Math.cos(theta)));
				double newY = robot.getY() - Math.sin(theta/2+robot.getAngle()) * Math.sqrt(2*Math.pow(r+robot.getWidth()/2,2)*(1-Math.cos(theta)));
				return new RobotPosition(newX, newY, robot.getAngle()+theta);
			} else {
				double theta = (left - right)/robot.getWidth();
				double r = (right * robot.getWidth())/(left-right);
				double newX = robot.getX() + Math.cos(theta/2+robot.getAngle()) * Math.sqrt(2*Math.pow(r+robot.getWidth()/2,2)*(1-Math.cos(theta)));
				double newY = robot.getY() + Math.sin(theta/2+robot.getAngle()) * Math.sqrt(2*Math.pow(r+robot.getWidth()/2,2)*(1-Math.cos(theta)));
				return new RobotPosition(newX, newY, robot.getAngle()+theta);
			}
		}
	};
	
	public static final DriveMode ARCADE_DRIVE = new DriveMode() {
		@Override
		public RobotPosition nextPosition(RobotModel robot, double... axes) {
			double left = axes[0] * robot.getMaxSpeed();
			double right = axes[0] * robot.getMaxSpeed();
			
			
			
			if(left == right) {
				return new RobotPosition(robot.getX() + left * Math.cos(robot.getAngle()),
						robot.getY() + left * Math.sin(robot.getAngle()),
						robot.getAngle());
			} else if((left<0 && right<=0) || (right<0 && left<=0)) {
				double theta = (left - right)/robot.getWidth();
				double r = (right * robot.getWidth())/(left-right);
				double newX = robot.getX() - Math.cos(theta/2+robot.getAngle()) * Math.sqrt(2*Math.pow(r+robot.getWidth()/2,2)*(1-Math.cos(theta)));
				double newY = robot.getY() - Math.sin(theta/2+robot.getAngle()) * Math.sqrt(2*Math.pow(r+robot.getWidth()/2,2)*(1-Math.cos(theta)));
				return new RobotPosition(newX, newY, robot.getAngle()+theta);
			} else {
				double theta = (left - right)/robot.getWidth();
				double r = (right * robot.getWidth())/(left-right);
				double newX = robot.getX() + Math.cos(theta/2+robot.getAngle()) * Math.sqrt(2*Math.pow(r+robot.getWidth()/2,2)*(1-Math.cos(theta)));
				double newY = robot.getY() + Math.sin(theta/2+robot.getAngle()) * Math.sqrt(2*Math.pow(r+robot.getWidth()/2,2)*(1-Math.cos(theta)));
				return new RobotPosition(newX, newY, robot.getAngle()+theta);
			}
		}
	};
	
	public abstract RobotPosition nextPosition(RobotModel robot, double... axes);
}
