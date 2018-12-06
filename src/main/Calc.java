package main;

public class Calc {
	
	public static double pixelsPerFoot = 50;
	
	public static int convertf(double feet) {
		return (int) Math.round(feet * pixelsPerFoot);
	}
	
	public static double convertp(int pixels) {
		return Math.round(pixels / pixelsPerFoot);
	}
}
