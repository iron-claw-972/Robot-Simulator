package main;

public abstract class InputMode {
	
	public static final InputMode KEYBOARD = new InputMode() {
		@Override
		public double[] getSpeeds() {
			MainWindow window = Main.window;
			
			int leftUD, leftLR, rightUD, rightLR;
			
			
			if(window.getKeysPressed().contains(new Character('w'))) {
				leftUD = 1;
			} else if(window.getKeysPressed().contains(new Character('s'))) {
				leftUD = -1;
			} else {
				leftUD = 0;
			}
			
			if(window.getKeysPressed().contains(new Character('a'))) {
				leftLR = -1;
			} else if(window.getKeysPressed().contains(new Character('d'))) 
			{
				leftLR = 1;
			} else {
				leftLR = 0;
			}
			
			if(window.getKeysPressed().contains(new Character('i'))) {
				rightUD = 1;
			} else if(window.getKeysPressed().contains(new Character('k'))) {
				rightUD = -1;
			} else {
				rightUD = 0;
			}
			
			if(window.getKeysPressed().contains(new Character('j'))) {
				rightLR = -1;
			} else if(window.getKeysPressed().contains(new Character('l'))) {
				rightLR = 1;
			} else {
				rightLR = 0;
			}
			
			return new double[] {leftUD, leftLR, rightUD, rightLR};
		}
	};
	
	public abstract double[] getSpeeds();
}