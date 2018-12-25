package main;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import java.awt.image.BufferedImage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Stack;
import java.util.Timer;

import javax.imageio.ImageIO;

import javax.swing.JFileChooser;
import javax.swing.JPanel;

import javax.swing.event.MouseInputListener;

import javax.swing.filechooser.FileNameExtensionFilter;

import entity.ColourBoard.Colour;
import entity.EndStatePiece;
import entity.ExpansionBoard.Expansion;
import entity.GameBoard;
import entity.GameBoard.ImmobileStrike;
import entity.GameState;
import entity.Hand;
import entity.Piece;
import entity.Piece.Type;
import entity.Stopwatch;
import entity.Square;
import entity.TierExchange;

public class Panel extends JPanel implements Runnable, MouseListener, MouseWheelListener, MouseInputListener {
	
	private enum Component {
		
		BOARD,
		WHITE_HAND,
		BLACK_HAND
		
	}

	private enum Subcomponent {
		
		BOARD_SQUARE,
		WHITE_HAND_SQUARE,
		BLACK_HAND_SQUARE
		
	}
	
	private enum Result {
		
		DRAW,
		WHITE_WINS,
		BLACK_WINS
		
	}
	
	private static final long serialVersionUID = 0L;
	
	/*Dimensions*/
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 720;
	
	/*Thread Parameters*/
	private Thread thread;
	private boolean running;
	private static final int FPS = 60;
	private static final long targetTime = 1000 / FPS;
	
	/*Opacity*/
	private float alpha;
	
	/*Fonts*/
	private Font bigFont;
	private Font smallFont;
	private Font infoFont;
	
	/*Timer*/
	private Timer timer;
	
	/*File Management*/
	private FileNameExtensionFilter fileNameExtensionFilter;
	private JFileChooser fileChooser;
	private File file;
	
	/*Resources*/
	private BufferedImage	background, board, hand,
							down, up,
							darkHUD, lightHUD,
							highlight,
							blackArrow, blackBronze, blackClandestinite, blackDragonKing, blackGold, blackLance, blackPhoenix, blackPike, blackPistol, blackSilver,
							whiteArrow, whiteBronze, whiteClandestinite, whiteDragonKing, whiteGold, whiteLance, whitePhoenix, whitePike, whitePistol, whiteSilver,
							frontBlack, frontWhite,
							bow, captain, catapult, commander, fortress, hiddenDragon, pawn, prodigy, samurai, spy,
							blue, green, orange, purple, red, yellow;
	
	/*Entities*/
	private GameBoard gameBoard;
	private Hand whiteHand, blackHand;
	private Stopwatch whiteStopwatch, blackStopwatch;
	private Square mouseSquare, currSquare, originSquare;
	
	private Piece mousePiece;
	private Stack<Piece> mouseStack;
	
	private List<GameState> gameStateList;
	private List<EndStatePiece> endStatePieceList;
	
	/*Attributes*/
	private boolean isWhiteTurn, drawLightHUD, drawCoords, drawHighlight, drawExpansion, drawArrows, drawDownArrow, drawUpArrow, lastDrewDown, forcedRearrangement, gameOver;
	
	private int turnCount, whiteCaptureCount, blackCaptureCount;
	
	public static float endStatePieceSpeedModifier;
	
	private char resultChar;
	
	private String topFrontInfo, topBackInfo, middleFrontInfo, middleBackInfo, bottomFrontInfo, bottomBackInfo;
	
	public Panel() {
		
		super();
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setOpaque(true);
		setFocusable(true);
		requestFocus();
		
	}
	
	public void addNotify() {
		
		super.addNotify();
		
		if(thread != null)
			return;
		
		thread = new Thread(this);
		
		addMouseListener(this);
		addMouseWheelListener(this);
		
		addMouseMotionListener(this);
		
		thread.start();
		
	}
	
	private void initialize() {
		
		try {
			
			background = ImageIO.read(getClass().getResourceAsStream("/Graphics/Background.png"));
			board = ImageIO.read(getClass().getResourceAsStream("/Graphics/Board.png"));
			hand = ImageIO.read(getClass().getResourceAsStream("/Graphics/Hand.png"));
			
			down = ImageIO.read(getClass().getResourceAsStream("/Graphics/Arrows/Down.png"));
			up = ImageIO.read(getClass().getResourceAsStream("/Graphics/Arrows/Up.png"));
			
			darkHUD = ImageIO.read(getClass().getResourceAsStream("/Graphics/HUD/Dark.png"));
			lightHUD = ImageIO.read(getClass().getResourceAsStream("/Graphics/HUD/Light.png"));
			
			highlight = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Highlight.png"));
			
			blackArrow = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Back/Black/Arrow.png"));
			blackBronze = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Back/Black/Bronze.png"));
			blackClandestinite = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Back/Black/Clandestinite.png"));
			blackDragonKing = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Back/Black/Dragon_King.png"));
			blackGold = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Back/Black/Gold.png"));
			blackLance = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Back/Black/Lance.png"));
			blackPhoenix = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Back/Black/Phoenix.png"));
			blackPike = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Back/Black/Pike.png"));
			blackPistol = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Back/Black/Pistol.png"));
			blackSilver = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Back/Black/Silver.png"));
			
			whiteArrow = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Back/White/Arrow.png"));
			whiteBronze = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Back/White/Bronze.png"));
			whiteClandestinite = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Back/White/Clandestinite.png"));
			whiteDragonKing = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Back/White/Dragon_King.png"));
			whiteGold = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Back/White/Gold.png"));
			whiteLance = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Back/White/Lance.png"));
			whitePhoenix = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Back/White/Phoenix.png"));
			whitePike = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Back/White/Pike.png"));
			whitePistol = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Back/White/Pistol.png"));
			whiteSilver = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Back/White/Silver.png"));
			
			frontBlack = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Front/Black.png"));
			frontWhite = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Front/White.png"));
			
			bow = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Front/Faces/Bow.png"));
			captain = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Front/Faces/Captain.png"));
			catapult = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Front/Faces/Catapult.png"));
			commander = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Front/Faces/Commander.png"));
			fortress = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Front/Faces/Fortress.png"));
			hiddenDragon = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Front/Faces/Hidden_Dragon.png"));
			pawn = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Front/Faces/Pawn.png"));
			prodigy = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Front/Faces/Prodigy.png"));
			samurai = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Front/Faces/Samurai.png"));
			spy = ImageIO.read(getClass().getResourceAsStream("/Graphics/Pieces/Front/Faces/Spy.png"));
			
			blue = ImageIO.read(getClass().getResourceAsStream("/Graphics/Squares/Blue.png"));
			green = ImageIO.read(getClass().getResourceAsStream("/Graphics/Squares/Green.png"));
			orange = ImageIO.read(getClass().getResourceAsStream("/Graphics/Squares/Orange.png"));
			purple = ImageIO.read(getClass().getResourceAsStream("/Graphics/Squares/Purple.png"));
			red = ImageIO.read(getClass().getResourceAsStream("/Graphics/Squares/Red.png"));
			yellow = ImageIO.read(getClass().getResourceAsStream("/Graphics/Squares/Yellow.png"));
			
			infoFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/Fonts/Info.ttf")).deriveFont(Font.BOLD, 24);
			
		}
		
		catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
		bigFont = new Font(Font.SANS_SERIF, Font.BOLD, 48);
		smallFont = new Font(Font.SANS_SERIF, Font.BOLD, 24);
		
		timer = new Timer();
		
		fileNameExtensionFilter = new FileNameExtensionFilter("Gungi (*.gng)", "gng");
		
		fileChooser = new JFileChooser();
		fileChooser.setFileFilter(fileNameExtensionFilter);
		
		this.initializeGame(true);
		
		running = true;
		
	}
	
	public void run() {
		
		initialize();
		
		long start, elapsed, wait;
		
		/*Event Loop*/
		while(running) {
			
			start = System.nanoTime();
			
			update();
			repaint();
			
			elapsed = System.nanoTime() - start;
			wait = targetTime - elapsed / 1000000;
			
			if(wait < 0)
				wait = 5;
			
			try {
				
				Thread.sleep(wait);
				
			}
			
			catch(Exception e) {
				
				e.printStackTrace();
				
			}
			
		}
		
	}
	
	private void update() {
		
		if(gameOver)
			for(EndStatePiece endStatePiece : endStatePieceList)
				endStatePiece.updatePosition();
		
		else if(alpha < 1.0f)
			alpha += 0.05f;
		
	}
	
	public synchronized void paintChildren(Graphics g) {
		
		if(!isInitialized())
			return;
		
		Graphics2D G2D = (Graphics2D) g;
		
		if(alpha < 1.0f)
			G2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		
		G2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		G2D.drawImage(background, 0, 0, null);
		G2D.drawImage(board, 0, 0, null);
		G2D.drawImage(hand, 720, 0, null);
		G2D.drawImage(drawLightHUD ? lightHUD : darkHUD, 720, 240, null);
		G2D.drawImage(hand, 720, 480, null);
		
		G2D.setColor(drawLightHUD ? Color.BLACK : Color.WHITE);
		
		G2D.setFont(bigFont);
		
		G2D.drawString(whiteStopwatch.getTimeAsString(), 1054, 306);
		G2D.drawString(blackStopwatch.getTimeAsString(), 1054, 418);
		
		G2D.setFont(infoFont);
		
		G2D.drawString(topFrontInfo, 748, 316);
		G2D.drawString(topBackInfo, 748, 342);
		G2D.drawString(middleFrontInfo, 748, 372);
		G2D.drawString(middleBackInfo, 748, 398);
		G2D.drawString(bottomFrontInfo, 748, 428);
		G2D.drawString(bottomBackInfo, 748, 454);
		
		if(gameBoard.getInCheck(true))
			G2D.drawString("帥", 1227, 344);
		
		if(gameBoard.getInCheck(false))
			G2D.drawString("帥", 1227, 456);
		
		G2D.setFont(smallFont);
		
		G2D.drawString("Captures: " + whiteCaptureCount, 1054, 343);
		G2D.drawString("Captures: " + blackCaptureCount, 1054, 455);
		
		G2D.drawString("Turn: " + turnCount, 748, 282);
		
		if(drawCoords)
			G2D.drawString((currSquare.getX() + 1) + "-" + (currSquare.getY() + 1) + "-" + (gameBoard.getTowerHeight(currSquare) + (((gameBoard.getColourBoard().getColour(currSquare) == Colour.YELLOW && mousePiece == null) || gameBoard.getColourBoard().getColour(currSquare) == Colour.RED) ? 0 : 1)), 957, 282);
		
		else if(gameOver)
			G2D.drawString(Character.toString(resultChar), resultChar == 'W' ? 988 : 995, 282);
		
		for(int i = 0; i < 9; i ++)
			for(int j = 0; j < 9; j ++) {
				
				if(gameBoard.getColourBoard().getColour(j, i) == Colour.BLUE)
					G2D.drawImage(blue, 2 + j * 80, 2 + i * 80, null);
				
				else if(gameBoard.getColourBoard().getColour(j, i) == Colour.GREEN)
					G2D.drawImage(green, 2 + j * 80, 2 + i * 80, null);
				
				else if(gameBoard.getColourBoard().getColour(j, i) == Colour.ORANGE)
					G2D.drawImage(orange, 2 + j * 80, 2 + i * 80, null);
				
				else if(gameBoard.getColourBoard().getColour(j, i) == Colour.RED)
					G2D.drawImage(red, 2 + j * 80, 2 + i * 80, null);
				
				else if(gameBoard.getColourBoard().getColour(j, i) == Colour.YELLOW)
					G2D.drawImage(yellow, 2 + j * 80, 2 + i * 80, null);
				
				else if(drawExpansion) {
					
					if(gameBoard.contains(Type.CATAPULT, currSquare)) {
						
						if(gameBoard.getPiece(Type.CATAPULT, currSquare).getIsWhite()) {
							
							if(gameBoard.getExpansionBoard().contains(Expansion.WHITE_CATAPULT, j, i))
								G2D.drawImage(purple, 2 + j * 80, 2 + i * 80, null);
							
						}
						
						else {
							
							if(gameBoard.getExpansionBoard().contains(Expansion.BLACK_CATAPULT, j, i))
								G2D.drawImage(purple, 2 + j * 80, 2 + i * 80, null);
							
						}
						
					}
					
					if(gameBoard.contains(Type.FORTRESS, currSquare)) {
						
						if(gameBoard.getPiece(Type.FORTRESS, currSquare).getIsWhite()) {
							
							if(gameBoard.getExpansionBoard().contains(Expansion.WHITE_FORTRESS, j, i))
								G2D.drawImage(purple, 2 + j * 80, 2 + i * 80, null);
							
						}
						
						else {
							
							if(gameBoard.getExpansionBoard().contains(Expansion.BLACK_FORTRESS, j, i))
								G2D.drawImage(purple, 2 + j * 80, 2 + i * 80, null);
							
						}
						
					}
					
				}
				
				for(int k = 0; k < 3; k ++)
					if(gameBoard.getPiece(j, i, k) != null)
						drawPiece(G2D, gameBoard.getPiece(j, i, k), 8 + j * 80, 8 + i * 80 - 8 * k);
				
			}
		
		G2D.setColor(new Color(255, 0, 63));
		
		for(int i = 0; i < 3; i ++)
			for(int j = 0; j < 8; j ++) {
				
				if(whiteHand.getPiece(j, i) != null)
					drawPiece(G2D, whiteHand.getPiece(j, i), 725 + j * 69, 12 + i * 76);
				
				if(blackHand.getPiece(j, i) != null)
					drawPiece(G2D, blackHand.getPiece(j, i), 725 + j * 69, 492 + i * 76);
				
				if(drawHighlight)
					if(j == currSquare.getX() && i == currSquare.getY())
						G2D.drawImage(highlight, 725 + j * 69, (isWhiteTurn ? 12 : 492) + i * 76, null);
				
				if(whiteHand.getPiece(j, i) != null && whiteHand.getStackSize(j, i) > 1)
					G2D.drawString("x" + whiteHand.getStackSize(j, i), 725 + j * 69 + 44, 12 + i * 76 + 64);
				
				if(blackHand.getPiece(j, i) != null && blackHand.getStackSize(j, i) > 1)
					G2D.drawString("x" + blackHand.getStackSize(j, i), 725 + j * 69 + 44, 492 + i * 76 + 64);
				
			}
		
		if(mousePiece != null)
			drawPiece(G2D, mousePiece, mouseSquare.getX(), mouseSquare.getY());
		
		else if(!mouseStack.empty()) {
			
			drawPiece(G2D, mouseStack.peek(), mouseSquare.getX(), mouseSquare.getY());
			G2D.drawString("x" + mouseStack.size(), mouseSquare.getX() + 44, mouseSquare.getY() + 64);
			
		}
		
		else if(drawArrows) {
			
			if(drawDownArrow)
				G2D.drawImage(down, mouseSquare.getX() + 12, mouseSquare.getY() + 12, null);
			
			else if(drawUpArrow)
				G2D.drawImage(up, mouseSquare.getX() + 12, mouseSquare.getY() + 12, null);
			
		}
		
		else if(gameOver)
			for(EndStatePiece endStatePiece : endStatePieceList)
				drawPiece(G2D, endStatePiece.getPiece(), endStatePiece.getX(), endStatePiece.getY());
		
	}
	
	private void drawPiece(Graphics2D G2D, Piece p, int x, int y) {
		
		if(p.getIsBack()) {
			
			switch(p.getType()) {
			
			case PISTOL:			G2D.drawImage(p.getIsWhite() ? whitePistol : blackPistol, x, y, null);					break;
			case PIKE:				G2D.drawImage(p.getIsWhite() ? whitePike : blackPike, x, y, null);						break;
			case CLANDESTINITE:		G2D.drawImage(p.getIsWhite() ? whiteClandestinite : blackClandestinite, x, y, null);	break;
			case LANCE_CATAPULT:	G2D.drawImage(p.getIsWhite() ? whiteLance : blackLance, x, y, null);					break;
			case LANCE_FORTRESS:	G2D.drawImage(p.getIsWhite() ? whiteLance : blackLance, x, y, null);					break;
			case DRAGON_KING:		G2D.drawImage(p.getIsWhite() ? whiteDragonKing : blackDragonKing, x, y, null);			break;
			case PHOENIX:			G2D.drawImage(p.getIsWhite() ? whitePhoenix : blackPhoenix, x, y, null);				break;
			case ARROW:				G2D.drawImage(p.getIsWhite() ? whiteArrow : blackArrow, x, y, null);					break;
			case BRONZE:			G2D.drawImage(p.getIsWhite() ? whiteBronze : blackBronze, x, y, null);					break;
			case SILVER:			G2D.drawImage(p.getIsWhite() ? whiteSilver : blackSilver, x, y, null);					break;
			case GOLD:				G2D.drawImage(p.getIsWhite() ? whiteGold : blackGold, x, y, null);						break;
			default:																										break;
			
			}
			
		}
		
		else {
			
			G2D.drawImage(p.getIsWhite() ? frontWhite : frontBlack, x, y, null);
			G2D.drawImage(frontFace(p), x, y, null);
			
		}
		
	}
	
	private BufferedImage frontFace(Piece p) {
		
		switch(p.getType()) {
		
		case COMMANDER:		return commander;
		case CAPTAIN:		return captain;
		case SAMURAI:		return samurai;
		case SPY:			return spy;
		case CATAPULT:		return catapult;
		case FORTRESS:		return fortress;
		case HIDDEN_DRAGON:	return hiddenDragon;
		case PRODIGY:		return prodigy;
		case BOW:			return bow;
		case PAWN_BRONZE:	return pawn;
		case PAWN_SILVER:	return pawn;
		case PAWN_GOLD:		return pawn;
		default:			return null;
		
		}
		
	}
	
	public void mouseClicked(MouseEvent e) {
		
		
		
	}
	
	public void mouseEntered(MouseEvent e) {
		
		
		
	}
	
	public void mouseExited(MouseEvent e) {
		
		resetParameters();
		
	}
	
	public void mousePressed(MouseEvent e) {
		
		handleClick(e);
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
		
		
	}
	
	public void mouseDragged(MouseEvent e) {
		
		handleClick(e);
		handleMove(e);
		
	}
	
	public void mouseMoved(MouseEvent e) {
		
		handleMove(e);
		
	}
	
	public void mouseWheelMoved(MouseWheelEvent e) {
		
		if(gameOver) {
			
			if(e.getWheelRotation() == 1) {
				
				if(endStatePieceSpeedModifier < 10)
					endStatePieceSpeedModifier += 0.05;
				
			}
			
			else if(e.getWheelRotation() == -1) {
				
				if(endStatePieceSpeedModifier > 0.25)
					endStatePieceSpeedModifier -= 0.05;
				
			}
			
		}
		
		else if(drawArrows) {
			
			drawDownArrow = lastDrewDown = (e.getWheelRotation() == 1);
			drawUpArrow = (e.getWheelRotation() == -1);
			
		}
		
	}
	
	private void handleMove(MouseEvent e) {
		
		if(!isInitialized())
			return;
		
		mouseSquare.setCoords(e.getX(), e.getY());
		
		resetParameters();
		
		if(isInComponent(Component.BOARD, e))
			handleBoardMove(e);
		
		else if(isInComponent(Component.WHITE_HAND, e))
			handleWhiteHandMove(e);
		
		else if(isInComponent(Component.BLACK_HAND, e))
			handleBlackHandMove(e);
		
	}
	
	private void handleBoardMove(MouseEvent e) {
		
		if(!isInSubcomponent(Subcomponent.BOARD_SQUARE, e))
			return;
		
		if(gameBoard.contains(Type.CATAPULT, currSquare) || gameBoard.contains(Type.FORTRESS, currSquare))
			drawExpansion = true;
		
		setPieceInfo(Component.BOARD, currSquare);
		
		if(gameOver)
			return;
		
		if(!mouseStack.empty())
			return;
		
		if(gameBoard.getIsInitialArrangement()) {
			
			if(mousePiece != null)
				if(isInOwnTerritory())
					if(gameBoard.isPlaceable(whiteHand, blackHand, mousePiece, currSquare)) {
						
						gameBoard.getColourBoard().setColour(Colour.YELLOW, currSquare);
						drawCoords = true;
						
					}
			
		}
		
		else {
			
			if(mousePiece == null) {
				
				ImmobileStrike tempImmobileStrike;
				
				if((tempImmobileStrike = gameBoard.isRed(currSquare, isWhiteTurn)) != null) {
					
					gameBoard.getColourBoard().setColour(Colour.RED, currSquare);
					
					if(gameBoard.isYellow(currSquare, isWhiteTurn))
						drawCoords = true;
					
					if(tempImmobileStrike == ImmobileStrike.BOTH) {
						
						drawArrows = true;
						
						if(lastDrewDown)
							drawDownArrow = true;
						
						else
							drawUpArrow = true;
						
					}
					
					else if(tempImmobileStrike == ImmobileStrike.UP)
						drawUpArrow = true;
					
					else if(tempImmobileStrike == ImmobileStrike.DOWN)
						drawDownArrow = true;
					
				}
				
				else if(gameBoard.isOrange(currSquare, isWhiteTurn)) {
					
					gameBoard.getColourBoard().setColour(Colour.ORANGE, currSquare);
					drawCoords = true;
					
				}
				
				else if(gameBoard.isYellow(currSquare, isWhiteTurn)) {
					
					gameBoard.getColourBoard().setColour(Colour.YELLOW, currSquare);
					drawCoords = true;
					
				}
				
			}
			
			else {
				
				if(mousePiece.getIsActive()) {
					
					if(forcedRearrangement) {
						
						if(isInOwnTerritory())
							if(!gameBoard.isOccupied(currSquare)) {
								
								gameBoard.getColourBoard().setColour(Colour.YELLOW, currSquare);
								drawCoords = true;
								
							}
						
					}
					
					else if(gameBoard.getColourBoard().getColour(currSquare) == Colour.BLUE)
						drawCoords = true;
					
					else if(gameBoard.getColourBoard().getColour(currSquare) == Colour.RED)
						drawCoords = true;
					
				}
				
				else {
					
					if(mousePiece.isCatapultOrFortress())
						if(!isInOwnTerritory())
							return;
					
					if(gameBoard.isPlaceable(whiteHand, blackHand, mousePiece, currSquare)) {
						
						gameBoard.getColourBoard().setColour(Colour.YELLOW, currSquare);
						drawCoords = true;
						
					}
					
				}
				
			}
			
		}
		
	}
	
	private void handleWhiteHandMove(MouseEvent e) {
		
		if(!isInSubcomponent(Subcomponent.WHITE_HAND_SQUARE, e))
			return;
		
		setPieceInfo(Component.WHITE_HAND, currSquare);
		
		if(gameOver)
			return;
		
		if(!isWhiteTurn)
			return;
		
		handleHandMove(whiteHand);
		
	}
	
	private void handleBlackHandMove(MouseEvent e) {
		
		if(!isInSubcomponent(Subcomponent.BLACK_HAND_SQUARE, e))
			return;
		
		setPieceInfo(Component.BLACK_HAND, currSquare);
		
		if(gameOver)
			return;
		
		if(isWhiteTurn)
			return;
		
		handleHandMove(blackHand);
		
	}
	
	private void handleHandMove(Hand hand) {
		
		if(mousePiece == null && mouseStack.isEmpty()) {
			
			if(!hand.isEmpty(currSquare))
				drawHighlight = true;
			
		}
		
		else if(mousePiece != null) {
			
			if(mousePiece.getIsActive())
				return;
			
			if(!hand.contains(mousePiece))
				drawHighlight = true;
			
			else if(!hand.isEmpty(currSquare) && hand.getPiece(currSquare).getType() == mousePiece.getType())
				drawHighlight = true;
			
		}
		
		else if(!mouseStack.isEmpty())
			drawHighlight = true;
		
	}
	
	private void handleClick(MouseEvent e) {
		
		if(gameOver)
			return;
		
		if(!isInitialized())
			return;
		
		if(e.getButton() == MouseEvent.BUTTON1)
			handleLeftClick(e);
		
		else if(e.getButton() == MouseEvent.BUTTON2)
			handleMiddleClick(e);
		
		else if(e.getButton() == MouseEvent.BUTTON3)
			handleRightClick(e);
		
		handleMove(e);
		
	}
	
	private void handleLeftClick(MouseEvent e) {
		
		if(isInComponent(Component.BOARD, e))
			handleLeftClickBoard(e);
		
		else if(isInComponent(Component.WHITE_HAND, e))
			handleLeftClickHand(whiteHand);
		
		else if(isInComponent(Component.BLACK_HAND, e))
			handleLeftClickHand(blackHand);
		
	}
	
	private void handleLeftClickBoard(MouseEvent e) {
		
		if(!isInSubcomponent(Subcomponent.BOARD_SQUARE, e))
			return;
		
		if(gameBoard.getColourBoard().getColour(currSquare) == Colour.BLUE) {
			
			handleActiveMove();
			
		}
		
		else if(gameBoard.getColourBoard().getColour(currSquare) == Colour.GREEN) {
			
			gameBoard.setPiece(mousePiece, currSquare);
			mousePiece = null;
			
			gameBoard.getColourBoard().setColour(null, currSquare);
			gameBoard.getColourBoard().clearMoveColours();
			
		}
		
		else if(gameBoard.getColourBoard().getColour(currSquare) == Colour.ORANGE) {
			
			handleActivePickup();
			
		}
		
		else if(gameBoard.getColourBoard().getColour(currSquare) == Colour.RED) {
			
			if(mousePiece == null) {
				
				if(gameBoard.isYellow(currSquare, isWhiteTurn))
					handleActivePickup();
				
			}
			
			else {
				
				if(!handleForcedRecovery(true)) {
					
					gameBoard.setCommSquare(mousePiece, currSquare);
					
					if(gameBoard.getPiece(currSquare).isLance() && !gameBoard.fullTerritory(isWhiteTurn)) {
						
						Piece tempPiece = mousePiece;
						
						gameBoard.getPiece(currSquare).flipPiece();
						mousePiece = gameBoard.getPiece(currSquare);
						
						gameBoard.removePiece(currSquare);
						gameBoard.setPiece(tempPiece, currSquare);
						
						setForcedRearrangement(true);
						
					}
					
					else {
						
						if(gameBoard.getTowerHeight(currSquare) == 1)
							gameBoard.removeExpansionRange(currSquare);
						
						handleActivePutBack(isWhiteTurn ? whiteHand : blackHand, gameBoard.getPiece(currSquare), true);
						
						gameBoard.removePiece(currSquare);
						gameBoard.setPiece(mousePiece, currSquare);
						
					}
					
					incrementCaptureCount();
					
					if(gameBoard.getPiece(currSquare).getType() == Type.BRONZE)
						handleBetrayal();
					
				}
				
				handleForcedRecovery(originSquare);
				
				gameBoard.getColourBoard().setColour(null, originSquare);
				gameBoard.getColourBoard().clearMoveColours();
				
				if(!forcedRearrangement) {
					
					mousePiece = null;
					switchTurn();
					
				}
				
			}
			
		}
		
		else if(gameBoard.getColourBoard().getColour(currSquare) == Colour.YELLOW) {
			
			if(mousePiece == null)
				handleActivePickup();
			
			else {
				
				if(gameBoard.getTowerHeight(currSquare) == 0)
					gameBoard.setExpansionRange(mousePiece, currSquare);
				
				if(forcedRearrangement)
					setForcedRearrangement(false);
				
				else {
					
					mousePiece.setIsActive(true);
					gameBoard.setCommSquare(mousePiece, currSquare);
					
				}
				
				gameBoard.setPiece(mousePiece, currSquare);
				mousePiece = null;
				
				handleForcedRecovery(currSquare);
				
				gameBoard.getColourBoard().setColour(null, currSquare);
				
				switchTurn();
				
			}
			
		}
		
	}
	
	private void handleLeftClickHand(Hand hand) {
		
		if(forcedRearrangement)
			return;
		
		if(!drawHighlight)
			return;
		
		if(mousePiece == null && mouseStack.isEmpty()) {
			
			mousePiece = hand.getPiece(currSquare);
			hand.popPiece(currSquare);
			
		}
		
		else if(mousePiece != null) {
			
			if(hand.contains(mousePiece) || hand.isEmpty(currSquare)) {
				
				hand.pushPiece(mousePiece, currSquare);
				mousePiece = null;
				
			}
			
			else {
				
				if(hand.getStackSize(currSquare) == 1)
					swapPieces(currSquare);
				
				else if(hand.getStackSize(currSquare) > 1) {
					
					mouseStack.addAll(hand.getStack(currSquare));
					hand.clearStack(currSquare);
					hand.pushPiece(mousePiece, currSquare);
					mousePiece = null;
					
				}
				
			}
			
		}
		
		else if(!mouseStack.isEmpty()) {
			
			if(hand.isEmpty(currSquare)) {
				
				hand.setStack(mouseStack, currSquare);
				mouseStack.clear();
				
			}
			
			else {
				
				if(hand.getStackSize(currSquare) == 1) {
					
					mousePiece = hand.getPiece(currSquare);
					hand.popPiece(currSquare);
					hand.setStack(mouseStack, currSquare);
					mouseStack.clear();
					
				}
				
				else if(hand.getStackSize(currSquare) > 1)
					swapStacks(currSquare);
				
			}
			
		}
		
	}
	
	private void handleMiddleClick(MouseEvent e) {
		
		if(forcedRearrangement)
			return;
		
		if(!isInComponent(Component.BOARD, e))
			return;
		
		if(!isInSubcomponent(Subcomponent.BOARD_SQUARE, e))
			return;
		
		if(gameBoard.getColourBoard().getColour(currSquare) == Colour.ORANGE) {
			
			if(gameBoard.getTowerHeight(currSquare) == 3)
				handleTierExchange();
			
			else if(gameBoard.getTowerHeight(currSquare) == 1)
				handleSubstitution();
			
		}
		
		else if(gameBoard.getColourBoard().getColour(currSquare) == Colour.RED) {
			
			if(mousePiece == null) {
				
				if(!gameBoard.isOrange(currSquare, isWhiteTurn))
					return;
				
				handleTierExchange();
				
			}
			
			else {
				
				if(gameBoard.getTowerHeight(currSquare) == 3)
					return;
				
				if(gameBoard.moveLeavesInCheck(mousePiece, currSquare))
					return;
				
				handleActiveMove();
				
			}
			
		}
		
	}
	
	private void handleRightClick(MouseEvent e) {
		
		if(forcedRearrangement)
			return;
		
		if(mousePiece == null && mouseStack.empty()) {
			
			if(isInComponent(Component.BOARD, e))
				handleRightClickBoard(e);
			
			else if(isInComponent(Component.WHITE_HAND, e))
				handleRightClickHand(whiteHand);
			
			else if(isInComponent(Component.BLACK_HAND, e))
				handleRightClickHand(blackHand);
			
		}
		
		else if(mousePiece != null) {
			
			if(mousePiece.getIsActive()) {
				
				gameBoard.setPiece(mousePiece, originSquare);
				mousePiece = null;
				
				gameBoard.getColourBoard().setColour(null, originSquare);
				gameBoard.getColourBoard().clearMoveColours();
				
			}
			
			else {
				
				if(isWhiteTurn)
					whiteHand.putBackPiece(mousePiece);
				
				else
					blackHand.putBackPiece(mousePiece);
				
				mousePiece = null;
				
				gameBoard.getColourBoard().setColour(null, currSquare);
				
			}
			
		}
		
		else if(!mouseStack.empty()) {
			
			if(isWhiteTurn)
				whiteHand.putBackStack(mouseStack);
			
			else
				blackHand.putBackStack(mouseStack);
			
			mouseStack.clear();
			
		}
		
	}
	
	private void handleRightClickBoard(MouseEvent e) {
		
		if(!isInSubcomponent(Subcomponent.BOARD_SQUARE, e))
			return;
		
		if(gameBoard.getColourBoard().getColour(currSquare) != Colour.RED)
			return;
		
		if(gameBoard.getTowerHeight(currSquare) == 3) {
			
			if(drawArrows) {
				
				if(drawDownArrow) {
					
					if(!handleForcedRearrangement(0)) {
						
						gameBoard.removeExpansionRange(currSquare);
						handleActivePutBack(isWhiteTurn ? whiteHand : blackHand, gameBoard.getPiece(currSquare, 0), true);
						
					}
					
					gameBoard.setPiece(gameBoard.getPiece(currSquare, 1), currSquare, 0);
					gameBoard.setPiece(gameBoard.getPiece(currSquare, 2), currSquare, 1);
					gameBoard.removePiece(currSquare, 2);
					
					lastDrewDown = false;
					
				}
				
				else if(drawUpArrow) {
					
					if(!handleForcedRearrangement(2))
						handleActivePutBack(isWhiteTurn ? whiteHand : blackHand, gameBoard.getPiece(currSquare, 2), true);
					
					gameBoard.removePiece(currSquare, 2);
					
					if(gameBoard.getPiece(currSquare, 1).getType() == Type.BRONZE)
						handleBetrayal();
					
				}
				
				incrementCaptureCount();
				
			}
			
			else if(drawDownArrow) {
				
				if(gameBoard.getPiece(currSquare, 1).getIsWhiteAligned() == isWhiteTurn) {
					
					if(!handleForcedRearrangement(0)) {
						
						gameBoard.removeExpansionRange(currSquare);
						handleActivePutBack(isWhiteTurn ? whiteHand : blackHand, gameBoard.getPiece(currSquare, 0), true);
						
					}
					
					gameBoard.setPiece(gameBoard.getPiece(currSquare, 1), currSquare, 0);
					gameBoard.setPiece(gameBoard.getPiece(currSquare, 2), currSquare, 1);
					gameBoard.removePiece(currSquare, 2);
					
					incrementCaptureCount();
					
				}
				
				else {
					
					if(!handleForcedRecovery(2, 1)) {
						
						if(!handleForcedRearrangement(1))
							handleActivePutBack(isWhiteTurn ? whiteHand : blackHand, gameBoard.getPiece(currSquare, 1), true);
						
						gameBoard.setPiece(gameBoard.getPiece(currSquare, 2), currSquare, 1);
						gameBoard.removePiece(currSquare, 2);
						
						incrementCaptureCount();
						
					}
					
				}
				
			}
			
			else if(drawUpArrow) {
				
				if(gameBoard.getPiece(currSquare, 1).getIsWhiteAligned() == isWhiteTurn) {
					
					if(!handleForcedRecovery(1, 2)) {
						
						if(!handleForcedRearrangement(2))
							handleActivePutBack(isWhiteTurn ? whiteHand : blackHand, gameBoard.getPiece(currSquare, 2), true);
						
						gameBoard.removePiece(currSquare, 2);
						
						incrementCaptureCount();
						
					}
					
				}
				
				else {
					
					if(!handleForcedRearrangement(1))
						handleActivePutBack(isWhiteTurn ? whiteHand : blackHand, gameBoard.getPiece(currSquare, 1), true);
					
					gameBoard.setPiece(gameBoard.getPiece(currSquare, 2), currSquare, 1);
					gameBoard.removePiece(currSquare, 2);
					
					incrementCaptureCount();
					
				}
				
			}
			
		}
		
		else if(gameBoard.getTowerHeight(currSquare) == 2) {
			
			if(drawDownArrow) {
				
				if(!handleForcedRecovery(1, 0)) {
					
					if(!handleForcedRearrangement(0)) {
						
						gameBoard.removeExpansionRange(currSquare);
						handleActivePutBack(isWhiteTurn ? whiteHand : blackHand, gameBoard.getPiece(currSquare, 0), true);
						
					}
					
					gameBoard.setPiece(gameBoard.getPiece(currSquare, 1), currSquare, 0);
					gameBoard.removePiece(currSquare, 1);
					
					incrementCaptureCount();
					
				}
				
			}
			
			else if(drawUpArrow) {
				
				if(!handleForcedRecovery(0, 1)) {
					
					if(!handleForcedRearrangement(1))
						handleActivePutBack(isWhiteTurn ? whiteHand : blackHand, gameBoard.getPiece(currSquare, 1), true);
					
					gameBoard.removePiece(currSquare, 1);
					
					incrementCaptureCount();
					
				}
				
			}
			
		}
		
		handleForcedRecovery(currSquare);
		
		if(!forcedRearrangement)
			switchTurn();
		
	}
	
	private void handleRightClickHand(Hand hand) {
		
		if(!drawHighlight)
			return;
		
		if(hand.getStackSize(currSquare) == 1) {
			
			mousePiece = hand.getPiece(currSquare);
			hand.popPiece(currSquare);
			
		}
		
		else if(hand.getStackSize(currSquare) > 1) {
			
			mouseStack.addAll(hand.getStack(currSquare));
			hand.clearStack(currSquare);
			
		}
		
	}
	
	private void handleActivePickup() {
		
		originSquare.setCoords(currSquare);
		gameBoard.getColourBoard().setColour(Colour.GREEN, currSquare);
		
		gameBoard.setMoveColours(whiteHand, blackHand, currSquare);
		
		mousePiece = gameBoard.getPiece(currSquare);
		gameBoard.removePiece(currSquare);
		
	}
	
	private void handleActiveMove() {
		
		if(!handleForcedRecovery(false)) {
			
			gameBoard.setCommSquare(mousePiece, currSquare);
			gameBoard.setPiece(mousePiece, currSquare);
			
		}
		
		mousePiece = null;
		
		handleForcedRecovery(originSquare);
		
		gameBoard.getColourBoard().setColour(null, originSquare);
		gameBoard.getColourBoard().clearMoveColours();
		
		switchTurn();
		
	}
	
	private void handleActivePutBack(Hand hand, Piece piece, boolean flipPiece) {
		
		piece.setIsActive(false);
		
		if(flipPiece)
			piece.flipPiece();
		
		hand.putBackPiece(piece);
		
	}
	
	private boolean handleForcedRecovery(boolean capture) {
		
		if(!gameBoard.forcedRecovery(mousePiece, currSquare, capture))
			return false;
		
		handleActivePutBack(isWhiteTurn ? whiteHand : blackHand, mousePiece, false);
		
		if(capture)
			if(!gameBoard.getPiece(currSquare).isCatapultOrFortress()) {
				
				handleActivePutBack(isWhiteTurn ? blackHand : whiteHand, gameBoard.getPiece(currSquare), false);
				gameBoard.removePiece(currSquare);
				
				incrementCaptureCount();
				
			}
		
		return true;
		
	}
	
	//For immobile strikes
	private boolean handleForcedRecovery(int attacking, int attacked) {
		
		if(!gameBoard.forcedRecovery(currSquare, attacking, attacked))
			return false;
		
		handleActivePutBack(isWhiteTurn ? whiteHand : blackHand, gameBoard.getPiece(currSquare, attacking), false);
		gameBoard.removePiece(currSquare, attacking);
		
		if(!gameBoard.getPiece(currSquare, attacked).isCatapultOrFortress()) {
			
			handleActivePutBack(isWhiteTurn ? blackHand : whiteHand, gameBoard.getPiece(currSquare, attacked), false);
			gameBoard.removePiece(currSquare, attacked);
			
			incrementCaptureCount();
			
		}
		
		return true;
		
	}
	
	private void handleForcedRecovery(Square s) {
		
		if(!gameBoard.forcedRecovery(s))
			return;
		
		Piece p = gameBoard.getPiece(s);
		
		handleActivePutBack(p.getIsWhiteAligned() ? whiteHand : blackHand, p, false);
		gameBoard.removePiece(s);
		
		handleForcedRecovery(s);
		
	}
	
	private boolean handleForcedRearrangement(int z) {
		
		if(!gameBoard.getPiece(currSquare, z).isLance())
			return false;
		
		if(gameBoard.fullTerritory(isWhiteTurn))
			return false;
		
		gameBoard.getPiece(currSquare, z).flipPiece();
		mousePiece = gameBoard.getPiece(currSquare, z);
		
		setForcedRearrangement(true);
		
		return true;
		
	}
	
	private void handleBetrayal() {
		
		int z = gameBoard.getTowerHeight(currSquare) - 1;
		
		if(z == 2) {
			
			if(gameBoard.getPiece(currSquare, 1).getIsWhiteAligned() != isWhiteTurn) {
				
				gameBoard.getPiece(currSquare, 1).flipPiece();
				
				incrementCaptureCount();
				
				if(gameBoard.getPiece(currSquare, 1).isCatapultOrFortress()) {
					
					if(gameBoard.fullTerritory(isWhiteTurn))
						handleActivePutBack(isWhiteTurn ? whiteHand : blackHand, gameBoard.getPiece(currSquare, 1), false);
					
					else {
						
						mousePiece = gameBoard.getPiece(currSquare, 1);
						setForcedRearrangement(true);
						
					}
					
					gameBoard.setPiece(gameBoard.getPiece(currSquare, 2), currSquare, 1);
					gameBoard.removePiece(currSquare, 2);
					
				}
				
			}
			
			if(gameBoard.getPiece(currSquare, 0).getIsWhiteAligned() != isWhiteTurn) {
				
				gameBoard.removeExpansionRange(currSquare);
				
				gameBoard.getPiece(currSquare, 0).flipPiece();
				
				incrementCaptureCount();
				
				if(gameBoard.getPiece(currSquare, 0).isCatapultOrFortress()) {
					
					if(gameBoard.fullTerritory(isWhiteTurn))
						handleActivePutBack(isWhiteTurn ? whiteHand : blackHand, gameBoard.getPiece(currSquare, 0), false);
					
					else {
						
						mousePiece = gameBoard.getPiece(currSquare, 0);
						setForcedRearrangement(true);
						
					}
					
					gameBoard.setPiece(gameBoard.getPiece(currSquare, 1), currSquare, 0);
					gameBoard.setPiece(gameBoard.getPiece(currSquare, 2), currSquare, 1);
					gameBoard.removePiece(currSquare, 2);
					
				}
				
			}
			
		}
		
		if(z == 1) {
			
			if(gameBoard.getPiece(currSquare, 0).getIsWhiteAligned() != isWhiteTurn) {
				
				gameBoard.removeExpansionRange(currSquare);
				
				gameBoard.getPiece(currSquare, 0).flipPiece();
				
				incrementCaptureCount();
				
				if(gameBoard.getPiece(currSquare, 0).isCatapultOrFortress()) {
					
					if(gameBoard.fullTerritory(isWhiteTurn))
						handleActivePutBack(isWhiteTurn ? whiteHand : blackHand, gameBoard.getPiece(currSquare, 0), false);
					
					else {
						
						mousePiece = gameBoard.getPiece(currSquare, 0);
						setForcedRearrangement(true);
						
					}
					
					gameBoard.setPiece(gameBoard.getPiece(currSquare, 1), currSquare, 0);
					gameBoard.removePiece(currSquare, 1);
					
				}
				
			}
			
		}
		
	}
	
	private void handleTierExchange() {
		
		Piece tempPiece;
		
		if(gameBoard.getPieceHeight(Type.CAPTAIN, currSquare) == 2) {
			
			tempPiece = gameBoard.getPiece(currSquare, 2);
			gameBoard.setPiece(gameBoard.getPiece(currSquare, 0), currSquare, 2);
			gameBoard.setPiece(tempPiece, currSquare, 0);
			
		}
		
		else if(gameBoard.getPieceHeight(Type.CAPTAIN, currSquare) == 0){
			
			tempPiece = gameBoard.getPiece(currSquare, 0);
			gameBoard.setPiece(gameBoard.getPiece(currSquare, 2), currSquare, 0);
			gameBoard.setPiece(tempPiece, currSquare, 2);
			
		}
		
		gameBoard.getTierExchangeList().add(new TierExchange(currSquare, turnCount));
		
		gameBoard.getColourBoard().setColour(null, currSquare);
		
		switchTurn();
		
	}
	
	private void handleSubstitution() {
		
		Piece tempPiece = gameBoard.getPiece(currSquare, 0);
		
		int x = currSquare.getX();
		int y = currSquare.getY();
		
		if(y - 1 >= 0 && gameBoard.isOccupied(x, y - 1) && gameBoard.getPiece(x, y - 1).getType() == Type.COMMANDER && gameBoard.getPiece(x, y - 1).getIsWhite() == isWhiteTurn) {
			
			gameBoard.setPiece(gameBoard.getPiece(x, y - 1), x, y, 0);
			gameBoard.setPiece(tempPiece, x, y - 1, gameBoard.getTowerHeight(x, y - 1) - 1);
			
		}
		
		else if(x + 1 <= 8 && gameBoard.isOccupied(x + 1, y) && gameBoard.getPiece(x + 1, y).getType() == Type.COMMANDER && gameBoard.getPiece(x + 1, y).getIsWhite() == isWhiteTurn) {
			
			gameBoard.setPiece(gameBoard.getPiece(x + 1, y), x, y, 0);
			gameBoard.setPiece(tempPiece, x + 1, y, gameBoard.getTowerHeight(x + 1, y) - 1);
			
		}
		
		else if(y + 1 <= 8 && gameBoard.isOccupied(x, y + 1) && gameBoard.getPiece(x, y + 1).getType() == Type.COMMANDER && gameBoard.getPiece(x, y + 1).getIsWhite() == isWhiteTurn) {
			
			gameBoard.setPiece(gameBoard.getPiece(x, y + 1), x, y, 0);
			gameBoard.setPiece(tempPiece, x, y + 1, gameBoard.getTowerHeight(x, y + 1) - 1);
			
		}
		
		else if(x - 1 >= 0 && gameBoard.isOccupied(x - 1, y) && gameBoard.getPiece(x - 1, y).getType() == Type.COMMANDER && gameBoard.getPiece(x - 1, y).getIsWhite() == isWhiteTurn) {
			
			gameBoard.setPiece(gameBoard.getPiece(x - 1, y), x, y, 0);
			gameBoard.setPiece(tempPiece, x - 1, y, gameBoard.getTowerHeight(x - 1, y) - 1);
			
		}
		
		gameBoard.getCommSquare(isWhiteTurn).setCoords(currSquare);
		
		gameBoard.getColourBoard().setColour(null, currSquare);
		
		switchTurn();
		
	}
    
	private boolean isInComponent(Component component, MouseEvent e) {
		
		if(component == Component.BOARD) {
			
			if((e.getX() > 0 && e.getX() < 720) && (e.getY() > 0 && e.getY() < 720))
				return true;
			
		}
		
		else if(component == Component.WHITE_HAND) {
			
			if((e.getX() > 720 && e.getX() < 1280) && (e.getY() > 0 && e.getY() < 240))
				return true;
			
		}
		
		else if(component == Component.BLACK_HAND) {
			
			if((e.getX() > 720 && e.getX() < 1280) && (e.getY() > 480 && e.getY() < 720))
				return true;
			
		}
		
		return false;
		
	}
	
	private boolean isInSubcomponent(Subcomponent subcomponent, MouseEvent e) {
		
		if(subcomponent == Subcomponent.BOARD_SQUARE) {
			
			for(int i = 0; i < 9; i ++)
				for(int j = 0; j < 9; j ++)
					if((e.getX() > j * 80 && e.getX() < j * 80 + 80) && (e.getY() > i * 80 && e.getY() < i * 80 + 80)) {
						
						currSquare.setCoords(j, i);
						return true;
						
					}
			
		}
		
		else if(subcomponent == Subcomponent.WHITE_HAND_SQUARE) {
			
			for(int i = 0; i < 3; i ++)
				for(int j = 0; j < 8; j ++)
					if((e.getX() > 725 + j * 69 && e.getX() < 725 + j * 69 + 64) && (e.getY() > 12 + i * 76 && e.getY() < 12 + i * 76 + 64)) {
						
						currSquare.setCoords(j, i);
						return true;
						
					}
			
		}
		
		else if(subcomponent == Subcomponent.BLACK_HAND_SQUARE) {
			
			for(int i = 0; i < 3; i ++)
				for(int j = 0; j < 8; j ++)
					if((e.getX() > 725 + j * 69 && e.getX() < 725 + j * 69 + 64) && (e.getY() > 492 + i * 76 && e.getY() < 492 + i * 76 + 64)) {
						
						currSquare.setCoords(j, i);
						return true;
						
					}
			
		}
		
		return false;
		
	}
	
	private boolean isInOwnTerritory() {
		
		return (isWhiteTurn ? (currSquare.getY() < 3) : (currSquare.getY() > 5));
		
	}
	
	private void incrementCaptureCount() {
		
		if(isWhiteTurn)
			whiteCaptureCount ++;
		
		else
			blackCaptureCount ++;
		
	}
	
	private void switchTurn() {
		
		turnCount ++;
		
		if(turnCount == 47) {
			
			if(gameBoard.isUnderAttack(true)) {
				
				gameBoard.setInCheck(true, true);
				gameOver(Result.BLACK_WINS);
				
			}
			
			if(gameBoard.isUnderAttack(false)) {
				
				gameBoard.setInCheck(false, true);
				
				if(gameBoard.checkmate(whiteHand, blackHand, false))
					gameOver(Result.WHITE_WINS);
				
			}
			
			addGameState(new GameState(gameBoard.getPieceBoard()));
			
			gameBoard.setIsInitialArrangement(false);
			
		}
		
		else if(turnCount > 47) {
			
			if(addGameState(new GameState(gameBoard.getPieceBoard())))
				gameOver(Result.DRAW);
			
			if(gameBoard.isUnderAttack(isWhiteTurn)) {
				
				gameBoard.setInCheck(isWhiteTurn, true);
				gameOver(isWhiteTurn ? Result.BLACK_WINS : Result.WHITE_WINS);
				
			}
			
			else
				gameBoard.setInCheck(isWhiteTurn, false);
			
			if(gameBoard.isUnderAttack(!isWhiteTurn)) {
				
				gameBoard.setInCheck(!isWhiteTurn, true);
				
				if(gameBoard.checkmate(whiteHand, blackHand, !isWhiteTurn))
					gameOver(isWhiteTurn ? Result.WHITE_WINS : Result.BLACK_WINS);
				
			}
			
			else
				gameBoard.setInCheck(!isWhiteTurn, false);
			
			if(gameBoard.getTierExchangeList().size() > 0)
				if(gameBoard.getTierExchangeList().get(0).getTurn() == turnCount - 3)
					gameBoard.getTierExchangeList().remove(0);
			
		}
		
		if(gameOver) {
			
			whiteStopwatch.setTiming(false);
			blackStopwatch.setTiming(false);
			
		}
		
		else {
			
			whiteStopwatch.changeTiming();
			blackStopwatch.changeTiming();
			
		}
		
		isWhiteTurn ^= true;
		
	}
	
	private boolean addGameState(GameState gs) {
		
		for(GameState gameState : gameStateList)
			if(Arrays.deepEquals(gs.getPieceOrdinalBoard(), gameState.getPieceOrdinalBoard())) {
				
				gameState.incrementOccurrences();
				
				if(gameState.getOccurrences() == 4)
					return true;
				
				return false;
				
			}
		
		gameStateList.add(gs);
		
		return false;
		
	}
	
	private void gameOver(Result result) {
		
		resetParameters();
		
		if(result == Result.WHITE_WINS)
			resultChar = 'W';
		
		else if(result == Result.BLACK_WINS)
			resultChar = 'B';
		
		else if(result == Result.DRAW)
			resultChar = 'D';
		
		for(int i = 0; i < 9; i ++)
			for(int j = 0; j < 9; j ++)
				for(int k = 0; k < 3; k ++)
					if(gameBoard.getPiece(j, i, k) != null) {
						
						if(result == Result.DRAW)
							endStatePieceList.add(new EndStatePiece(gameBoard.getPiece(j, i, k)));
						
						else if(result == Result.WHITE_WINS) {
							
							if(gameBoard.getPiece(j, i, k).getIsWhiteAligned() == false)
								endStatePieceList.add(new EndStatePiece(gameBoard.getPiece(j, i, k)));
							
						}
						
						else if(result == Result.BLACK_WINS) {
							
							if(gameBoard.getPiece(j, i, k).getIsWhiteAligned() == true)
								endStatePieceList.add(new EndStatePiece(gameBoard.getPiece(j, i, k)));
							
						}
						
					}
		
		for(int i = 0; i < 3; i ++)
			for(int j = 0; j < 8; j ++) {
				
				if(result == Result.DRAW || result == Result.WHITE_WINS) {
					
					for(Piece p : blackHand.getStack(j, i))
						endStatePieceList.add(new EndStatePiece(p));
					
				}
				
				else if(result == Result.DRAW || result == Result.BLACK_WINS) {
					
					for(Piece p : whiteHand.getStack(j, i))
						endStatePieceList.add(new EndStatePiece(p));
					
				}
				
			}
		
		gameOver = true;
		Frame.enableSave(false);
		
	}
	
	private void swapPieces(Square s) {
		
		Piece tempPiece = mousePiece;
		
		if(isWhiteTurn) {
			
			mousePiece = whiteHand.getPiece(s);
			whiteHand.popPiece(s);
			whiteHand.pushPiece(tempPiece, s);
			
		}
		
		else {
			
			mousePiece = blackHand.getPiece(s);
			blackHand.popPiece(s);
			blackHand.pushPiece(tempPiece, s);
			
		}
		
	}
	
	private void swapStacks(Square s) {
		
		Stack<Piece> tempPieceStack = new Stack<Piece>();
		tempPieceStack.addAll(mouseStack);
		mouseStack.clear();
		
		if(isWhiteTurn) {
			
			mouseStack.addAll(whiteHand.getStack(s));
			whiteHand.setStack(tempPieceStack, s);
			
		}
		
		else {
			
			mouseStack.addAll(blackHand.getStack(s));
			blackHand.setStack(tempPieceStack, s);
			
		}
		
		tempPieceStack.clear();
		
	}
	
	private void setPieceInfo(Component component, Square s) {
		
		int x = s.getX();
		int y = s.getY();
		
		if(component == Component.BOARD) {
			
			if(gameBoard.getPiece(x, y, 0) != null) {
				
				bottomFrontInfo = gameBoard.getPiece(x, y, 0).getFrontInfo();
				bottomBackInfo = gameBoard.getPiece(x, y, 0).getBackInfo();
				
				if(gameBoard.getPiece(x, y, 1) != null) {
					
					middleFrontInfo = gameBoard.getPiece(x, y, 1).getFrontInfo();
					middleBackInfo = gameBoard.getPiece(x, y, 1).getBackInfo();
					
					if(gameBoard.getPiece(x, y, 2) != null) {
						
						topFrontInfo = gameBoard.getPiece(x, y, 2).getFrontInfo();
						topBackInfo = gameBoard.getPiece(x, y, 2).getBackInfo();
						
					}
					
				}
				
			}
			
		}
		
		else if(component == Component.WHITE_HAND) {
			
			if(whiteHand.getPiece(x, y) != null) {
				
				bottomFrontInfo = whiteHand.getPiece(x, y).getFrontInfo();
				bottomBackInfo = whiteHand.getPiece(x, y).getBackInfo();
				
			}
			
		}
		
		else if(component == Component.BLACK_HAND) {
			
			if(blackHand.getPiece(x, y) != null) {
				
				bottomFrontInfo = blackHand.getPiece(x, y).getFrontInfo();
				bottomBackInfo = blackHand.getPiece(x, y).getBackInfo();
				
			}
			
		}
		
	}
	
	private void resetParameters() {
		
		if(!isInitialized())
			return;
		
		if(gameBoard.getIsInitialArrangement() ? (mousePiece != null) : (mousePiece == null || !mousePiece.getIsActive() || forcedRearrangement))
			gameBoard.getColourBoard().setColour(null, currSquare);
		
		drawCoords = drawHighlight = drawExpansion = false;
		
		if(mousePiece == null && gameBoard.getColourBoard().getColour(currSquare) != Colour.RED)
			drawArrows = drawDownArrow = drawUpArrow = false;
		
		topFrontInfo = topBackInfo = middleFrontInfo = middleBackInfo = bottomFrontInfo = bottomBackInfo = "";
		
	}
	
	private boolean isInitialized() {
		
		return (mouseSquare != null);
		
	}
	
	public void setDrawLightHUD(boolean b) {
		
		drawLightHUD = b;
		
	}
	
	private void setForcedRearrangement(boolean b) {
		
		forcedRearrangement = b;
		Frame.enableSave(!b);
		
	}
	
	public void initializeGame(boolean newGame) {
		
		Frame.enableSave(true);
		
		alpha = 0.0f;
		
		gameBoard = new GameBoard();
		
		whiteHand = new Hand();
		blackHand = new Hand();
		
		whiteStopwatch = new Stopwatch();
		blackStopwatch = new Stopwatch();
		
		mouseSquare = new Square();
		currSquare = new Square();
		originSquare = new Square();
		
		mousePiece = null;
		mouseStack = new Stack<Piece>();
		
		gameStateList = new ArrayList<GameState>();
		endStatePieceList = new ArrayList<EndStatePiece>();
		
		isWhiteTurn = drawCoords = drawHighlight = drawExpansion = drawArrows = drawDownArrow = drawUpArrow = lastDrewDown = forcedRearrangement = gameOver = false;
		
		turnCount = 1;
		whiteCaptureCount = blackCaptureCount = 0;
		
		endStatePieceSpeedModifier = 0.25f;
		
		topFrontInfo = topBackInfo = middleFrontInfo = middleBackInfo = bottomFrontInfo = bottomBackInfo = "";
		
		resultChar = '\u0000';
		
		timer.schedule(whiteStopwatch, 0, 1000);
		timer.schedule(blackStopwatch, 0, 1000);
		
		if(newGame) {
			
			whiteHand.loadNewHand(true);
			blackHand.loadNewHand(false);
			
			blackStopwatch.changeTiming();
			
		}
		
	}
	
	public void open() {
		
		int returnValue = fileChooser.showOpenDialog(this);
		
		if(returnValue == JFileChooser.APPROVE_OPTION)
			file = fileChooser.getSelectedFile();
		
		else
			return;
		
		try {
			
			if(!file.exists() || !file.getName().substring(file.getName().length() - 4).equalsIgnoreCase(".gng"))
				return;
			
			initializeGame(false);
			
			FileReader fileReader = new FileReader(file.getAbsoluteFile());
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String line;
			
			int row = 0;
			int multiplier = -1;
			
			while((line = bufferedReader.readLine()) != null) {
				
				if(!line.isEmpty())
					line = decrypt(line);
				
				if(row == 0)
					turnCount = Integer.parseInt(line);
				
				else if(row == 1)
					whiteStopwatch.setTime(line);
				
				else if(row == 2)
					blackStopwatch.setTime(line);
				
				else if(row == 3)
					whiteCaptureCount = Integer.parseInt(line);
				
				else if(row == 4)
					blackCaptureCount = Integer.parseInt(line);
				
				else if(row >= 5 && row <= 7)
					loadHand(whiteHand, line, row);
				
				else if(row >= 8 && row <= 10)
					loadHand(blackHand, line, row);
				
				else if(row >= 11 && row <= 19) {
					
					String[] squares = line.split(";");
					
					for(int i = 0; i < squares.length; i ++)
						if(!squares[i].equals("-,-,-")) {
							
							String[] pieces = squares[i].split(",");
							
							for(String s : pieces)
								if(!s.equals("-")) {
									
									gameBoard.setPiece(new Piece(Integer.parseInt(s), true), i, row - 11);
									
									if(s.equals("1"))
										gameBoard.getCommSquare(false).setCoords(i, row - 11);
									
									else if(s.equals("5"))
										gameBoard.getExpansionBoard().setExpansionRange(Expansion.BLACK_CATAPULT, i, row - 11);
									
									else if(s.equals("6"))
										gameBoard.getExpansionBoard().setExpansionRange(Expansion.BLACK_FORTRESS, i, row - 11);
									
									else if(s.equals("24"))
										gameBoard.getCommSquare(true).setCoords(i, row - 11);
									
									else if(s.equals("28"))
										gameBoard.getExpansionBoard().setExpansionRange(Expansion.WHITE_CATAPULT, i, row - 11);
									
									else if(s.equals("29"))
										gameBoard.getExpansionBoard().setExpansionRange(Expansion.WHITE_FORTRESS, i, row - 11);
									
								}
							
						}
					
				}
				
				else if(row == 20) {
					
					if(!line.isEmpty()) {
						
						String[] exchanges = line.split(";");
						
						for(int i = 0; i < exchanges.length; i ++) {
							
							String[] values = exchanges[i].split(",");
							gameBoard.getTierExchangeList().add(new TierExchange(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2])));
							
						}
						
					}
					
				}
				
				else if(row >= 21) {
					
					if(!line.isEmpty()) {
						
						if((row - 11) % 10 == 0) {
							
							addGameState(new GameState());
							gameStateList.get(gameStateList.size() - 1).setOccurences(Integer.parseInt(line));
							
							multiplier ++;
							
						}
						
						else {
							
							String[] squares = line.split(";");
							
							for(int i = 0; i < squares.length; i ++) {
								
								String[] pieces = squares[i].split(",");
								
								for(int j = 0; j < pieces.length; j ++)
									gameStateList.get(gameStateList.size() - 1).setPieceOrdinal(Integer.parseInt(pieces[j]), i, (row - 22) - 10 * multiplier, j);
								
							}
							
						}
						
					}
					
				}
				
				row ++;
				
			}
			
			bufferedReader.close();
			fileReader.close();
			
			isWhiteTurn = ((turnCount & 1) == 0);
			gameBoard.setIsInitialArrangement(turnCount < 47);
			
			if(gameBoard.isUnderAttack(false))
				gameBoard.setInCheck(false, true);
			
			if(gameBoard.isUnderAttack(true))
				gameBoard.setInCheck(true, true);
			
			if(isWhiteTurn)
				whiteStopwatch.changeTiming();
			
			else
				blackStopwatch.changeTiming();
			
		}
		
		catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	private void loadHand(Hand hand, String line, int row) {
		
		String[] stacks = line.split(";");
		
		for(int i = 0; i < stacks.length; i ++)
			if(!stacks[i].equals("_")) {
				
				if(stacks[i].contains(",")) {
					
					String[] pieces = stacks[i].split(",");
					
					for(String s : pieces)
						hand.pushPiece(new Piece(Integer.parseInt(s), false), i, row - ((row < 8) ? 5 : 8));
					
				}
				
				else
					hand.pushPiece(new Piece(Integer.parseInt(stacks[i]), false), i, row - ((row < 8) ? 5 : 8));
				
			}
		
	}
	
	public void save(boolean saveAs) {
		
		if(saveAs || file == null) {
			
			int returnValue = fileChooser.showSaveDialog(this);
			
			if(returnValue == JFileChooser.APPROVE_OPTION)
				file = fileChooser.getSelectedFile();
			
			else
				return;
			
		}
		
		try {
			
			if(!file.getName().contains(".") || !file.getName().substring(file.getName().lastIndexOf(".")).equalsIgnoreCase(".gng"))
				file = new File(file.toString() + ".gng");
			
			if(!file.exists())
				file.createNewFile();
			
			if(mousePiece != null) {
				
				if(!mousePiece.getIsActive()) {
					
					if(isWhiteTurn)
						whiteHand.putBackPiece(mousePiece);
					
					else
						blackHand.putBackPiece(mousePiece);
					
				}
				
				else
					gameBoard.setPiece(mousePiece, originSquare);
				
			}
			
			else if(!mouseStack.empty()) {
				
				if(isWhiteTurn)
					whiteHand.putBackStack(mouseStack);
				
				else
					blackHand.putBackStack(mouseStack);
				
			}
			
			FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			
			String line;
			
			bufferedWriter.write(
				encrypt(Integer.toString(turnCount))			+ System.getProperty("line.separator")	+
				encrypt(whiteStopwatch.getTimeAsString())		+ System.getProperty("line.separator")	+
				encrypt(blackStopwatch.getTimeAsString())		+ System.getProperty("line.separator")	+
				encrypt(Integer.toString(whiteCaptureCount))	+ System.getProperty("line.separator")	+
				encrypt(Integer.toString(blackCaptureCount))	+ System.getProperty("line.separator")
			);
			
			saveHand(whiteHand, bufferedWriter);
			saveHand(blackHand, bufferedWriter);
			
			for(int i = 0; i < 9; i ++) {
				
				line = "";
				
				for(int j = 0; j < 9; j ++) {
					
					for(int k = 0; k < 3; k ++) {
						
						if(gameBoard.getPiece(j, i, k) != null)
							line += gameBoard.getPiece(j, i, k).getOrdinal();
						
						else
							line += "-";
						
						if(k != 2)
							line += ",";
						
					}
					
					if(j != 8)
						line += ";";
					
				}
				
				bufferedWriter.write(encrypt(line) + System.getProperty("line.separator"));
				
			}
			
			line = "";
			
			for(int i = 0; i < gameBoard.getTierExchangeList().size(); i ++) {
				
				line += gameBoard.getTierExchangeList().get(i).getX() + "," +
						gameBoard.getTierExchangeList().get(i).getY() + "," +
						gameBoard.getTierExchangeList().get(i).getTurn();
				
				if(i != gameBoard.getTierExchangeList().size() - 1)
					line += ";";
				
			}
			
			bufferedWriter.write(encrypt(line) + System.getProperty("line.separator"));
			
			for(int i = 0; i < gameStateList.size(); i ++) {
				
				bufferedWriter.write(encrypt(Integer.toString(gameStateList.get(i).getOccurrences())) + System.getProperty("line.separator"));
				
				for(int j = 0; j < 9; j ++) {
					
					line = "";
					
					for(int k = 0; k < 9; k ++) {
						
						for(int l = 0; l < 3; l ++) {
							
							line += gameStateList.get(i).getOrdinal(k, j, l);
							
							if(l != 2)
								line += ",";
							
						}
						
						if(k != 8)
							line += ";";
						
					}
					
					bufferedWriter.write(encrypt(line));
					
					if(j != 8 || i != gameStateList.size() - 1)
						bufferedWriter.write(System.getProperty("line.separator"));
					
				}
				
			}
			
			bufferedWriter.close();
			fileWriter.close();
			
			if(mousePiece != null) {
				
				if(!mousePiece.getIsActive()) {
					
					if(isWhiteTurn)
						whiteHand.removeSpecifiedPiece(mousePiece);
					
					else
						blackHand.removeSpecifiedPiece(mousePiece);
					
				}
				
				else
					gameBoard.removePiece(originSquare);
				
			}
			
			else if(!mouseStack.empty()) {
				
				if(isWhiteTurn)
					whiteHand.removeSpecifiedStack(mouseStack.peek());
				
				else
					blackHand.removeSpecifiedStack(mouseStack.peek());
				
			}
			
		}
		
		catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	private void saveHand(Hand hand, BufferedWriter bufferedWriter) {
		
		String line;
		
		try {
			
			for(int i = 0; i < 3; i ++) {
				
				line = "";
				
				for(int j = 0; j < 8; j ++) {
					
					if(!hand.getHand().get(j).get(i).empty()) {
						
						for(int k = 0; k < hand.getHand().get(j).get(i).size(); k ++) {
							
							line += hand.getPiece(j, i).getOrdinal();
							
							if(k != hand.getHand().get(j).get(i).size() - 1)
								line += ",";
							
						}
						
					}
					
					else
						line += "_";
					
					if(j != 7)
						line += ";";
					
				}
				
				bufferedWriter.write(encrypt(line) + System.getProperty("line.separator"));
				
			}
			
		}
		
		catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	private String encrypt(String s) {
		
		byte[] encryption = Base64.getEncoder().withoutPadding().encode(s.getBytes());
		return new String(encryption);
		
	}
	
	private String decrypt(String s) {
		
		byte[] decryption = Base64.getDecoder().decode(s);
		return new String(decryption);
		
	}
	
	public void equals(Panel panel) {
		
		
		
	}
	
}
