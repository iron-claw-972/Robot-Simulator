package main;

public abstract class InputMode {
	
	public static class Keyboard extends InputMode {
		public Character lu, ll, ld, lr, ru, rl, rd, rr;
		
		public Keyboard() {
			this('w', 'a', 's', 'd', 'i', 'j', 'k', 'l') ;
		}
		
		public Keyboard(Character lu, Character ll, Character ld, Character lr,
				Character ru, Character rl, Character rd, Character rr) {
			this.lu = lu;
			this.ll = ll;
			this.ld = ld;
			this.lr = lr;
			this.ru = ru;
			this.rl = rl;
			this.rd = rd;
			this.rr = rr;
		}
		
		@Override
		public double[] getSpeeds() {
			MainWindow window = Main.window;
			
			int leftUD, leftLR, rightUD, rightLR;
			
			
			if(window.getKeysPressed().contains(lu)) {
				leftUD = 1;
			} else if(window.getKeysPressed().contains(ld)) {
				leftUD = -1;
			} else {
				leftUD = 0;
			}
			
			if(window.getKeysPressed().contains(ll)) {
				leftLR = -1;
			} else if(window.getKeysPressed().contains(lr)) 
			{
				leftLR = 1;
			} else {
				leftLR = 0;
			}
			
			if(window.getKeysPressed().contains(ru)) {
				rightUD = 1;
			} else if(window.getKeysPressed().contains(rd)) {
				rightUD = -1;
			} else {
				rightUD = 0;
			}
			
			if(window.getKeysPressed().contains(rl)) {
				rightLR = -1;
			} else if(window.getKeysPressed().contains(rr)) {
				rightLR = 1;
			} else {
				rightLR = 0;
			}
			
			return new double[] {leftUD, leftLR, rightUD, rightLR};
		}
	};
	
	
	
	public abstract double[] getSpeeds();
}