package main;

public abstract class InputMode {
	
	public static final InputMode WSIK = new InputMode() {
		@Override
		public double[] getSpeeds() {
			MainWindow window = Main.window;
			
			int left, right;
			
			
			if(window.getKeysPressed().contains(new Character('w'))) {
				left = 1;
			} else if(window.getKeysPressed().contains(new Character('s'))) {
				left = -1;
			} else {
				left = 0;
			}
			
			if(window.getKeysPressed().contains(new Character('i'))) {
				right = 1;
			} else if(window.getKeysPressed().contains(new Character('k'))) {
				right = -1;
			} else {
				right = 0;
			}
			
			return new double[] {left, right};
		}
	};
	
	
	
	public abstract double[] getSpeeds();
}