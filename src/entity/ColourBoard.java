package entity;

public class ColourBoard {
	
	public enum Colour {
		
		BLUE,
		GREEN,
		ORANGE,
		RED,
		YELLOW
		
	}
	
	private Colour[][] colourBoard;
	
	public ColourBoard() {
		
		this.colourBoard = new Colour[9][9];
		
	}
	
	public Colour getColour(int x, int y) {
		
		return this.colourBoard[x][y];
		
	}
	
	public Colour getColour(Square s) {
		
		return this.colourBoard[s.getX()][s.getY()];
		
	}
	
	public void setColour(Colour c, Square s) {
		
		this.colourBoard[s.getX()][s.getY()] = c;
		
	}
	
	public void clearMoveColours() {
		
		for(int i = 0; i < 9; i ++)
			for(int j = 0; j < 9; j ++)
				if(this.colourBoard[j][i] == Colour.BLUE || this.colourBoard[j][i] == Colour.RED)
					this.colourBoard[j][i] = null;
		
	}
	
	public boolean isEmpty() {
		
		for(int i = 0; i < 9; i ++)
			for(int j = 0; j < 9; j ++)
				if(this.colourBoard[j][i] != null)
					return false;
		
		return true;
		
	}
	
	public void equals(ColourBoard cb) {
		
		
		
	}
	
}
