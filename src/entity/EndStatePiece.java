package entity;

import java.util.Random;

import main.Panel;

public class EndStatePiece {
	
	private static final int PIECE_DIAMETER = 64;
	
	private Piece p;
	private int x, y;
	private float speed;
	
	public EndStatePiece() {
		
		
		
	}
	
	public EndStatePiece(Piece p) {
		
		this.p = new Piece(p);
		randomizeParameters();
		
	}
	
	private void randomizeParameters() {
		
		Random random = new Random();
		
		this.x = random.nextInt(Panel.WIDTH - PIECE_DIAMETER + 1);
		this.y = random.nextInt(Panel.HEIGHT - PIECE_DIAMETER + 1) - Panel.HEIGHT;
		
		this.speed = random.nextInt(10) + Panel.endStatePieceSpeedModifier + 1;
		
	}
	
	public Piece getPiece() {
		
		return this.p;
		
	}
	
	public int getX() {
		
		return this.x;
		
	}
	
	public int getY() {
		
		return this.y;
		
	}
	
	public void updatePosition() {
		
		this.speed += 0.25 / speed;
		
		this.y += this.speed;
		
		if(this.y > Panel.HEIGHT)
			randomizeParameters();
		
	}
	
	public void equals(EndStatePiece endStatePiece) {
		
		
		
	}
	
}
