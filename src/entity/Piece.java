package entity;

import java.util.ArrayList;
import java.util.List;

public class Piece {
	
	public enum Type {
		
		COMMANDER,
		CAPTAIN,
		SAMURAI,
		SPY,
		CATAPULT,
		FORTRESS,
		HIDDEN_DRAGON,
		PRODIGY,
		BOW,
		PAWN_BRONZE,
		PAWN_SILVER,
		PAWN_GOLD,
		PISTOL,
		PIKE,
		CLANDESTINITE,
		LANCE_CATAPULT,
		LANCE_FORTRESS,
		DRAGON_KING,
		PHOENIX,
		ARROW,
		BRONZE,
		SILVER,
		GOLD
		
	}
	
	private Type type;
	private boolean isWhite, isActive;
	
	public Piece() {
		
		
		
	}
	
	public Piece(Type t, boolean isWhite, boolean isActive) {
		
		this.type = t;
		this.isWhite = isWhite;
		this.isActive = isActive;
		
	}
	
	public Piece(int ordinal, boolean isActive) {
		
		this.type = Type.values()[ordinal - ((ordinal >= 24) ? 24 : 1)];
		this.isWhite = ordinal >= 23;
		this.isActive = isActive;
		
	}
	
	public Piece(Piece p) {
		
		this.type = p.type;
		this.isWhite = p.isWhite;
		this.isActive = p.isActive;
		
	}
	
	public Type getType() {
		
		return this.type;
		
	}
	
	public int getOrdinal() {
		
		return (this.type.ordinal() + (this.isWhite ? 24 : 1));
		
	}
	
	public void flipPiece() {
		
		this.type = getReverse();
		
	}
	
	public boolean getIsWhite() {
		
		return this.isWhite;
		
	}
	
	public boolean getIsBack() {
		
		return this.type.ordinal() >= 12;
		
	}
	
	public boolean getIsWhiteAligned() {
		
		return (this.isWhite ? !getIsBack() : getIsBack());
		
	}
	
	public boolean getIsActive() {
		
		return this.isActive;
		
	}
	
	public void setIsActive(boolean b) {
		
		this.isActive = b;
		
	}
	
	public boolean isCatapultOrFortress() {
		
		return (this.type == Type.CATAPULT || this.type == Type.FORTRESS);
		
	}
	
	public boolean isPawn() {
		
		return (this.type == Type.PAWN_BRONZE || this.type == Type.PAWN_SILVER || this.type == Type.PAWN_GOLD);
		
	}
	
	public boolean isPawn(Type t) {
		
		return (t == Type.PAWN_BRONZE || t == Type.PAWN_SILVER || t == Type.PAWN_GOLD);
		
	}
	
	public boolean isLance() {
		
		return (this.type == Type.LANCE_CATAPULT || this.type == Type.LANCE_FORTRESS);
		
	}
	
	public boolean isLance(Type t) {
		
		return (t == Type.LANCE_CATAPULT || t == Type.LANCE_FORTRESS);
		
	}
	
	public boolean canJump() {
		
		return (this.type == Type.SPY || this.type == Type.BOW || this.type == Type.CLANDESTINITE);
		
	}
	
	public boolean canReceiveMRE() {
		
		return !(this.type == Type.HIDDEN_DRAGON || this.type == Type.PRODIGY || this.type == Type.DRAGON_KING || this.type == Type.PHOENIX);
		
	}
	
	public boolean pieceEquals(Piece p) {
		
		return (this.isWhite == p.isWhite && this.typeEquals(p));
		
	}
	
	public boolean typeEquals(Piece p) {
		
		return (this.type == p.type || (this.isPawn() && p.isPawn()) || (this.isLance() && p.isLance()));
		
	}
	
	public boolean typeEquals(Type t) {
		
		return (this.type == t || (this.isPawn() && isPawn(t)) || (this.isLance() && isLance(t)));
		
	}
	
	public Type getReverse() {
		
		switch(this.type) {
		
		case CAPTAIN:			return Type.PISTOL;
		case SAMURAI:			return Type.PIKE;
		case SPY:				return Type.CLANDESTINITE;
		case CATAPULT:			return Type.LANCE_CATAPULT;
		case FORTRESS:			return Type.LANCE_FORTRESS;
		case HIDDEN_DRAGON:		return Type.DRAGON_KING;
		case PRODIGY:			return Type.PHOENIX;
		case BOW:				return Type.ARROW;
		case PAWN_BRONZE:		return Type.BRONZE;
		case PAWN_SILVER:		return Type.SILVER;
		case PAWN_GOLD:			return Type.GOLD;
		case PISTOL:			return Type.CAPTAIN;
		case PIKE:				return Type.SAMURAI;
		case CLANDESTINITE:		return Type.SPY;
		case LANCE_CATAPULT:	return Type.CATAPULT;
		case LANCE_FORTRESS:	return Type.FORTRESS;
		case DRAGON_KING:		return Type.HIDDEN_DRAGON;
		case PHOENIX:			return Type.PRODIGY;
		case ARROW:				return Type.BOW;
		case BRONZE:			return Type.PAWN_BRONZE;
		case SILVER:			return Type.PAWN_SILVER;
		case GOLD:				return Type.PAWN_GOLD;
		default:				return null;
		
		}
		
	}
	
	public String getFrontInfo() {
		
		switch(this.type) {
		
		case COMMANDER:			return "帥 - Commander";
		case CAPTAIN:			return "謀 - Captain";
		case SAMURAI:			return "侍 - Samurai";
		case SPY:				return "忍 - Spy";
		case CATAPULT:			return "砲 - Catapult";
		case FORTRESS:			return "砦 - Fortress";
		case HIDDEN_DRAGON:		return "臥 - Hidden Dragon";
		case PRODIGY:			return "雛 - Prodigy";
		case BOW:				return "弓 - Bow";
		case PAWN_BRONZE:		return "兵 - Pawn";
		case PAWN_SILVER:		return "兵 - Pawn";
		case PAWN_GOLD:			return "兵 - Pawn";
		case PISTOL:			return "筒 - Pistol";
		case PIKE:				return "槍 - Pike";
		case CLANDESTINITE:		return "上 - Clandestinite";
		case LANCE_CATAPULT:	return "香 - Lance";
		case LANCE_FORTRESS:	return "香 - Lance";
		case DRAGON_KING:		return "龍 - Dragon King";
		case PHOENIX:			return "鳳 - Phoenix";
		case ARROW:				return "や - Arrow";
		case BRONZE:			return "へ - Bronze";
		case SILVER:			return "さ - Silver";
		case GOLD:				return "と - Gold";
		default:				return "";
		
		}
		
	}
	
	public String getBackInfo() {
		
		switch(this.type) {
		
		case CAPTAIN:			return "∟筒 - Pistol";
		case SAMURAI:			return "∟槍 - Pike";
		case SPY:				return "∟上 - Clandestinite";
		case CATAPULT:			return "∟香 - Lance";
		case FORTRESS:			return "∟香 - Lance";
		case HIDDEN_DRAGON:		return "∟龍 - Dragon King";
		case PRODIGY:			return "∟鳳 - Phoenix";
		case BOW:				return "∟や - Arrow";
		case PAWN_BRONZE:		return "∟へ - Bronze";
		case PAWN_SILVER:		return "∟さ - Silver";
		case PAWN_GOLD:			return "∟と - Gold";
		case PISTOL:			return "∟謀 - Captain";
		case PIKE:				return "∟侍 - Samurai";
		case CLANDESTINITE:		return "∟忍 - Spy";
		case LANCE_CATAPULT:	return "∟砲 - Catapult";
		case LANCE_FORTRESS:	return "∟砦 - Fortress";
		case DRAGON_KING:		return "∟臥 - Hidden Dragon";
		case PHOENIX:			return "∟雛 - Prodigy";
		case ARROW:				return "∟弓 - Bow";
		case BRONZE:			return "∟兵 - Pawn";
		case SILVER:			return "∟兵 - Pawn";
		case GOLD:				return "∟兵 - Pawn";
		default:				return "";
		
		}
		
	}
	
	public List<Square> getSquareList(int x, int y, int z) {
		
		List<Square> squareList = new ArrayList<Square>();
		
		switch(this.type) {
		
		case COMMANDER:
			squareList.addAll(getCornerSquareList(x, y));
			squareList.addAll(getCardinalSquareList(x, y));
			break;
		
		case CAPTAIN:
			squareList.addAll(getCornerSquareList(x, y));
			
			if(z == 0 || z == 1)
				squareList.add(new Square(x, y - 1 * (getIsWhiteAligned() ? -1 : 1)));
			
			if(z == 1)
				squareList.add(new Square(x, y + 1 * (getIsWhiteAligned() ? -1 : 1)));
			
			if(z == 2) {
				
				squareList.add(new Square(x - 2, y - 2 * (getIsWhiteAligned() ? -1 : 1)));
				squareList.add(new Square(x + 2, y - 2 * (getIsWhiteAligned() ? -1 : 1)));
				squareList.add(new Square(x - 2, y));
				squareList.add(new Square(x + 2, y));
				
			}
			
			break;
		
		case SAMURAI:
			squareList.add(new Square(x - 1, y - 1 * (getIsWhiteAligned() ? -1 : 1)));
			squareList.add(new Square(x + 1, y - 1 * (getIsWhiteAligned() ? -1 : 1)));
			squareList.add(new Square(x - 1, y));
			squareList.add(new Square(x + 1, y));
			
			if(z == 0)
				squareList.add(new Square(x, y - 1 * (getIsWhiteAligned() ? -1 : 1)));
			
			else {
				
				squareList.add(new Square(x, y - 2));
				squareList.add(new Square(x, y + 2));
				
			}
			
			break;
		
		case SPY:
			squareList.add(new Square(x - 1, y - 2 * (getIsWhiteAligned() ? -1 : 1)));
			squareList.add(new Square(x + 1, y - 2 * (getIsWhiteAligned() ? -1 : 1)));
			
			if(z == 1 || z == 2) {
				
				squareList.add(new Square(x - 1, y - 1 * (getIsWhiteAligned() ? -1 : 1)));
				squareList.add(new Square(x + 1, y - 1 * (getIsWhiteAligned() ? -1 : 1)));
				
			}
			
			break;
		
		case CATAPULT:
			break;
		
		case FORTRESS:
			break;
		
		case HIDDEN_DRAGON:
			if(z == 0)
				squareList.addAll(getExtendedCardinalSquareList(x, y));
			
			else
				squareList.addAll(getCornerSquareList(x, y));
			
			break;
		
		case PRODIGY:
			if(z == 0)
				squareList.addAll(getExtendedCornerSquareList(x, y));
			
			else
				squareList.addAll(getCardinalSquareList(x, y));
			
			break;
		
		case BOW:
			if(z == 0 || z == 2) {
				
				squareList.add(new Square(x - 2, y));
				squareList.add(new Square(x + 2, y));
				
			}
			
			if(z == 1 || z == 2) {
				
				squareList.add(new Square(x - 2, y - 2 * (getIsWhiteAligned() ? -1 : 1)));
				squareList.add(new Square(x + 2, y - 2 * (getIsWhiteAligned() ? -1 : 1)));
				
			}
			
			if(z == 0)
				squareList.add(new Square(x, y - 2 * (getIsWhiteAligned() ? -1 : 1)));
			
			if(z == 1) {
				
				squareList.add(new Square(x, y - 1));
				squareList.add(new Square(x, y + 1));
				
			}
			
			if(z == 2)
				squareList.add(new Square(x, y + 2 * (getIsWhiteAligned() ? -1 : 1)));
			
			break;
		
		case PAWN_BRONZE:
			squareList.addAll(getPawnSquareList(x, y, z));
			break;
		
		case PAWN_SILVER:
			squareList.addAll(getPawnSquareList(x, y, z));
			break;
		
		case PAWN_GOLD:
			squareList.addAll(getPawnSquareList(x, y, z));
			break;
		
		case PISTOL:
			if(z == 0)
				squareList.addAll(getCornerSquareList(x, y));
			
			else
				squareList.addAll(getCardinalSquareList(x, y));
			
			break;
		
		case PIKE:
			if(z == 0) {
				
				squareList.add(new Square(x, y - 2 * (getIsWhiteAligned() ? -1 : 1)));
				squareList.addAll(getCardinalSquareList(x, y));
				
			}
			
			else
				squareList.addAll(getCornerSquareList(x, y));
			
			break;
		
		case CLANDESTINITE:
			squareList.add(new Square(x - 1, y - 2 * (getIsWhiteAligned() ? -1 : 1)));
			squareList.add(new Square(x + 1, y - 2 * (getIsWhiteAligned() ? -1 : 1)));
			squareList.add(new Square(x, y + 1 * (getIsWhiteAligned() ? -1 : 1)));
			
			if(z == 1 || z == 2) {
				
				squareList.add(new Square(x - 1, y - 1 * (getIsWhiteAligned() ? -1 : 1)));
				squareList.add(new Square(x + 1, y - 1 * (getIsWhiteAligned() ? -1 : 1)));
				
			}
			
			if(z == 2) {
				
				squareList.add(new Square(x - 2, y + 2 * (getIsWhiteAligned() ? -1 : 1)));
				squareList.add(new Square(x - 1, y + 2 * (getIsWhiteAligned() ? -1 : 1)));
				squareList.add(new Square(x + 1, y + 2 * (getIsWhiteAligned() ? -1 : 1)));
				squareList.add(new Square(x + 2, y + 2 * (getIsWhiteAligned() ? -1 : 1)));
				
			}
			
			break;
		
		case LANCE_CATAPULT:
			squareList = getLanceSquareList(x, y, z);
			break;
		
		case LANCE_FORTRESS:
			squareList = getLanceSquareList(x, y, z);
			break;
		
		case DRAGON_KING:
			squareList.addAll(getCornerSquareList(x, y));
			
			if(z == 0)
				squareList.addAll(getExtendedCardinalSquareList(x, y));
			
			break;
		
		case PHOENIX:
			squareList.addAll(getCardinalSquareList(x, y));
			
			if(z == 0)
				squareList.addAll(getExtendedCornerSquareList(x, y));
			
			break;
		
		case ARROW:
			squareList.add(new Square(x, y - 1));
			squareList.add(new Square(x, y + 1));
			
			if(z == 0 || z == 2) {
				
				squareList.add(new Square(x - 1, y + 1 * (getIsWhiteAligned() ? -1 : 1)));
				squareList.add(new Square(x + 1, y + 1 * (getIsWhiteAligned() ? -1 : 1)));
				
			}
			
			if(z == 1 || z == 2) {
				
				squareList.add(new Square(x - 2, y + 2 * (getIsWhiteAligned() ? -1 : 1)));
				squareList.add(new Square(x + 2, y + 2 * (getIsWhiteAligned() ? -1 : 1)));
				
			}
			
			break;
		
		case BRONZE:
			squareList.add(new Square(x - 1, y));
			squareList.add(new Square(x + 1, y));
			break;
		
		case SILVER:
			if(z == 0)
				squareList.addAll(getCardinalSquareList(x, y));
			
			else
				squareList.addAll(getCornerSquareList(x, y));
			
			break;
		
		case GOLD:
			squareList = getGoldSquareList(x, y);
			break;
		
		default:
			break;
		
		}
		
		return squareList;
		
	}
	
	private List<Square> getPawnSquareList(int x, int y, int z) {
		
		List<Square> squareList = new ArrayList<Square>();
		
		if(z == 0 || z == 1)
			squareList.add(new Square(x, y - 1 * (getIsWhiteAligned() ? -1 : 1)));
		
		if(z == 1 || z == 2) {
			
			squareList.add(new Square(x - 2, y));
			squareList.add(new Square(x + 2, y));
			
		}
		
		if(z == 2) {
			
			squareList.add(new Square(x - 1, y - 1 * (getIsWhiteAligned() ? -1 : 1)));
			squareList.add(new Square(x + 1, y - 1 * (getIsWhiteAligned() ? -1 : 1)));
			
		}
		
		return squareList;
		
	}
	
	private List<Square> getLanceSquareList(int x, int y, int z) {
		
		List<Square> squareList = new ArrayList<Square>();
		
		if(z == 0) {
			
			if(getIsWhiteAligned())
				for(int i = y + 1; i <= 8; i ++)
					squareList.add(new Square(x, i));
			
			else
				for(int i = y - 1; i >= 0; i --)
					squareList.add(new Square(x, i));
			
		}
		
		else
			squareList.addAll(getCornerSquareList(x, y));
		
		return squareList;
		
	}
	
	public List<Square> getGoldSquareList(int x, int y) {
		
		List<Square> squareList = new ArrayList<Square>();
		
		squareList.add(new Square(x - 1, y - 1 * (getIsWhiteAligned() ? -1 : 1)));
		squareList.add(new Square(x + 1, y - 1 * (getIsWhiteAligned() ? -1 : 1)));
		squareList.addAll(getCardinalSquareList(x, y));
		
		return squareList;
		
	}
	
	private List<Square> getCornerSquareList(int x, int y) {
		
		List<Square> squareList = new ArrayList<Square>();
		
		squareList.add(new Square(x - 1, y - 1));
		squareList.add(new Square(x + 1, y - 1));
		squareList.add(new Square(x - 1, y + 1));
		squareList.add(new Square(x + 1, y + 1));
		
		return squareList;
		
	}
	
	private List<Square> getCardinalSquareList(int x, int y) {
		
		List<Square> squareList = new ArrayList<Square>();
		
		squareList.add(new Square(x, y - 1));
		squareList.add(new Square(x - 1, y));
		squareList.add(new Square(x + 1, y));
		squareList.add(new Square(x, y + 1));
		
		return squareList;
		
	}
	
	private List<Square> getExtendedCornerSquareList(int x, int y) {
		
		List<Square> squareList = new ArrayList<Square>();
		
		int i = x - 1;
		int j = y - 1;
		
		while(i >= 0 && j >= 0)
			squareList.add(new Square(i --, j --));
		
		i = x + 1;
		j = y - 1;
		
		while(i <= 8 && j >= 0)
			squareList.add(new Square(i ++, j --));
		
		i = x - 1;
		j = y + 1;
		
		while(i >= 0 && j <= 8)
			squareList.add(new Square(i --, j ++));
		
		i = x + 1;
		j = y + 1;
		
		while(i <= 8 && j <= 8)
			squareList.add(new Square(i ++, j ++));
		
		return squareList;
		
	}
	
	private List<Square> getExtendedCardinalSquareList(int x, int y) {
		
		List<Square> squareList = new ArrayList<Square>();
		
		for(int i = y - 1; i >= 0; i --)
			squareList.add(new Square(x, i));
		
		for(int i = x - 1; i >= 0; i --)
			squareList.add(new Square(i, y));
		
		for(int i = x + 1; i <= 8; i ++)
			squareList.add(new Square(i, y));
		
		for(int i = y + 1; i <= 8; i ++)
			squareList.add(new Square(x, i));
		
		return squareList;
		
	}
	
	public void equals(Piece p) {
		
		
		
	}
	
}
