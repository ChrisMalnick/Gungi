package entity;

public class Square {
	
	private int x, y;
	
	public Square() {
		
		
		
	}
	
	public Square(int x, int y) {
		
		this.x = x;
		this.y = y;
		
	}
	
	public Square(Square s) {
		
		this.x = s.x;
		this.y = s.y;
		
	}
	
	public int getX() {
		
		return this.x;
		
	}
	
	public int getY() {
		
		return this.y;
		
	}
	
	public void setCoords(int x, int y) {
		
		this.x = x;
		this.y = y;
		
	}
	
	public void setCoords(Square s) {
		
		this.x = s.x;
		this.y = s.y;
		
	}
	
	public boolean coordsEqual(Square s) {
		
		return (this.x == s.x && this.y == s.y);
		
	}
	
	public void equals(Square s) {
		
		
		
	}
	
}
