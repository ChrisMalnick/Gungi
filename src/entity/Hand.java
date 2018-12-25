package entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import entity.Piece.Type;

public class Hand {
	
	private List<List<Stack<Piece>>> hand;
	
	public Hand() {
		
		initialize();
		
	}
	
	public Hand(Hand h) {
		
		initialize();
		
		for(int i = 0; i < 8; i ++)
			for(int j = 0; j < 3; j ++)
				this.hand.get(i).get(j).addAll(h.hand.get(i).get(j));
		
	}
	
	public void initialize() {
		
		this.hand = new ArrayList<List<Stack<Piece>>>(8);
		
		for(int i = 0; i < 8; i ++) {
			
			this.hand.add(new ArrayList<Stack<Piece>>(3));
			
			for(int j = 0; j < 3; j ++)
				this.hand.get(i).add(new Stack<Piece>());
			
		}
		
	}
	
	public void loadNewHand(boolean isWhite) {
		
		this.hand.get(0).get(0).push(new Piece(Type.COMMANDER, isWhite, false));
		this.hand.get(1).get(0).push(new Piece(Type.CAPTAIN, isWhite, false));
		this.hand.get(1).get(0).push(new Piece(Type.CAPTAIN, isWhite, false));
		this.hand.get(2).get(0).push(new Piece(Type.SAMURAI, isWhite, false));
		this.hand.get(2).get(0).push(new Piece(Type.SAMURAI, isWhite, false));
		this.hand.get(3).get(0).push(new Piece(Type.SPY, isWhite, false));
		this.hand.get(3).get(0).push(new Piece(Type.SPY, isWhite, false));
		this.hand.get(3).get(0).push(new Piece(Type.SPY, isWhite, false));
		this.hand.get(4).get(0).push(new Piece(Type.CATAPULT, isWhite, false));
		this.hand.get(5).get(0).push(new Piece(Type.FORTRESS, isWhite, false));
		this.hand.get(6).get(0).push(new Piece(Type.HIDDEN_DRAGON, isWhite, false));
		this.hand.get(7).get(0).push(new Piece(Type.PRODIGY, isWhite, false));
		
		this.hand.get(0).get(1).push(new Piece(Type.BOW, isWhite, false));
		this.hand.get(0).get(1).push(new Piece(Type.BOW, isWhite, false));
		this.hand.get(1).get(1).push(new Piece(Type.PAWN_BRONZE, isWhite, false));
		this.hand.get(1).get(1).push(new Piece(Type.PAWN_BRONZE, isWhite, false));
		this.hand.get(1).get(1).push(new Piece(Type.PAWN_BRONZE, isWhite, false));
		this.hand.get(1).get(1).push(new Piece(Type.PAWN_BRONZE, isWhite, false));
		this.hand.get(1).get(1).push(new Piece(Type.PAWN_BRONZE, isWhite, false));
		this.hand.get(1).get(1).push(new Piece(Type.PAWN_BRONZE, isWhite, false));
		this.hand.get(1).get(1).push(new Piece(Type.PAWN_BRONZE, isWhite, false));
		this.hand.get(2).get(1).push(new Piece(Type.PAWN_SILVER, isWhite, false));
		this.hand.get(3).get(1).push(new Piece(Type.PAWN_GOLD, isWhite, false));
		
	}
	
	public List<List<Stack<Piece>>> getHand() {
		
		return this.hand;
		
	}
	
	public Piece getPiece(int x, int y) {
		
		if(!this.hand.get(x).get(y).empty())
			return this.hand.get(x).get(y).peek();
		
		return null;
		
	}
	
	public Piece getPiece(Square s) {
		
		int x = s.getX();
		int y = s.getY();
		
		if(!this.hand.get(x).get(y).empty())
			return this.hand.get(x).get(y).peek();
		
		return null;
		
	}
	
	public void popPiece(Square s) {
		
		this.hand.get(s.getX()).get(s.getY()).pop();
		
	}
	
	public void pushPiece(Piece p, int x, int y) {
		
		this.hand.get(x).get(y).push(p);
		
	}
	
	public void pushPiece(Piece p, Square s) {
		
		this.hand.get(s.getX()).get(s.getY()).push(p);
		
	}
	
	public void putBackPiece(Piece p) {
		
		for(int i = 0; i < 3; i ++)
			for(int j = 0; j < 8; j ++)
				if(!this.hand.get(j).get(i).empty() && this.hand.get(j).get(i).peek().getType() == p.getType()) {
					
					this.hand.get(j).get(i).push(p);
					return;
					
				}
		
		for(int i = 0; i < 3; i ++)
			for(int j = 0; j < 8; j ++)
				if(this.hand.get(j).get(i).empty()) {
					
					this.hand.get(j).get(i).push(p);
					return;
					
				}
		
	}
	
	public Stack<Piece> getStack(int x, int y) {
		
		return this.hand.get(x).get(y);
		
	}
	
	public Stack<Piece> getStack(Square s) {
		
		return this.hand.get(s.getX()).get(s.getY());
		
	}
	
	public void setStack(Stack<Piece> pieceStack, Square s) {
		
		this.hand.get(s.getX()).get(s.getY()).clear();
		this.hand.get(s.getX()).get(s.getY()).addAll(pieceStack);
		
	}
	
	public void clearStack(Square s) {
		
		this.hand.get(s.getX()).get(s.getY()).clear();
		
	}
	
	public void putBackStack(Stack<Piece> s) {
		
		for(int i = 0; i < 3; i ++)
			for(int j = 0; j < 8; j ++)
				if(this.hand.get(j).get(i).empty()) {
					
					this.hand.get(j).get(i).addAll(s);
					return;
					
				}
		
	}
	
	public int getStackSize(int x, int y) {
		
		return this.hand.get(x).get(y).size();
		
	}
	
	public int getStackSize(Square s) {
		
		return this.hand.get(s.getX()).get(s.getY()).size();
		
	}
	
	public boolean isEmpty(Square s) {
		
		return (this.hand.get(s.getX()).get(s.getY()).isEmpty());
		
	}
	
	public boolean isEmpty() {
		
		for(int i = 0; i < 3; i ++)
			for(int j = 0; j < 8; j ++)
				if(!this.hand.get(j).get(i).isEmpty())
					return false;
		
		return true;
		
	}
	
	public boolean contains(Piece p) {
		
		for(int i = 0; i < 3; i ++)
			for(int j = 0; j < 8; j ++)
				if(!this.hand.get(j).get(i).empty())
					if(this.hand.get(j).get(i).peek().getType() == p.getType())
						return true;
		
		return false;
		
	}
	
	public boolean onlyContainsPawnAndOrBronze() {
		
		for(int i = 0; i < 3; i ++)
			for(int j = 0; j < 8; j ++) {
				
				if(this.hand.get(j).get(i).isEmpty())
					continue;
				
				if(!this.hand.get(j).get(i).peek().isPawn() && this.hand.get(j).get(i).peek().getType() != Type.BRONZE)
					return false;
				
			}
		
		return true;
		
	}
	
	public boolean onlyContainsPawn() {
		
		for(int i = 0; i < 3; i ++)
			for(int j = 0; j < 8; j ++) {
				
				if(this.hand.get(j).get(i).isEmpty())
					continue;
				
				if(!this.hand.get(j).get(i).peek().isPawn())
					return false;
				
			}
		
		return true;
		
	}
	
	public boolean onlyContainsBronze() {
		
		for(int i = 0; i < 3; i ++)
			for(int j = 0; j < 8; j ++) {
				
				if(this.hand.get(j).get(i).isEmpty())
					continue;
				
				if(this.hand.get(j).get(i).peek().getType() != Type.BRONZE)
					return false;
				
			}
		
		return true;
		
	}
	
	public void removePawn() {
		
		for(int i = 0; i < 3; i ++)
			for(int j = 0; j < 8; j ++)
				if(!this.hand.get(j).get(i).empty() && this.hand.get(j).get(i).peek().isPawn()) {
					
					this.hand.get(j).get(i).pop();
					return;
					
				}
		
	}
	
	public void removeSpecifiedPiece(Piece p) {
		
		for(int i = 0; i < 3; i ++)
			for(int j = 0; j < 8; j ++)
				if(!this.hand.get(j).get(i).empty() && this.hand.get(j).get(i).peek().getType() == p.getType()) {
					
					this.hand.get(j).get(i).pop();
					return;
					
				}
		
	}
	
	public void removeSpecifiedStack(Piece p) {
		
		for(int i = 0; i < 3; i ++)
			for(int j = 0; j < 8; j ++)
				if(!this.hand.get(j).get(i).empty() && this.hand.get(j).get(i).peek().getType() == p.getType()) {
					
					this.hand.get(j).get(i).clear();
					return;
					
				}
		
	}
	
	public void equals(Hand h) {
		
		
		
	}
	
}
