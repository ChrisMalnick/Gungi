package entity;

public class TierExchange {
	
	private Square s;
	private int turn;
	
	public TierExchange() {
		
		
		
	}
	
	public TierExchange(int x, int y, int turn) {
		
		this.s = new Square(x, y);
		this.turn = turn;
		
	}
	
	public TierExchange(Square s, int turn) {
		
		this.s = new Square(s);
		this.turn = turn;
		
	}
	
	public int getX() {
		
		return this.s.getX();
		
	}
	
	public int getY() {
		
		return this.s.getY();
		
	}
	
	public int getTurn() {
		
		return this.turn;
		
	}
	
	public boolean squareEquals(Square s) {
		
		return (this.s.coordsEqual(s));
		
	}
	
	public void equals(TierExchange tierExchange) {
		
		
		
	}
	
}
