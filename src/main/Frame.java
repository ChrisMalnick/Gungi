package main;

import java.awt.Image;

import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Frame {
	
	private static JFrame frame;
	private static MenuBar menuBar;
	private static Panel panel;
	private static List<Image> icons;
	
	public Frame() {
		
		
		
	}
	
	public static void main(String[] args) {
		
		frame = new JFrame("Gungi");
		menuBar = new MenuBar();
		panel = new Panel();
		
		initialize();
		
		frame.setIconImages(icons);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(menuBar);
		frame.setContentPane(panel);
		frame.pack();
		frame.getContentPane().setSize(Panel.WIDTH, Panel.HEIGHT);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
	}
	
	private static void initialize() {
		
		icons = new ArrayList<Image>();
		
		try {
			
			icons.add(new ImageIcon(ImageIO.read(ClassLoader.class.getResourceAsStream("/Graphics/Icons/Big.ico"))).getImage());
			icons.add(new ImageIcon(ImageIO.read(ClassLoader.class.getResourceAsStream("/Graphics/Icons/Small.ico"))).getImage());
			
		}
		
		catch(Exception e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	public static void newGame() {
		
		panel.initializeGame(true);
		
	}
	
	public static void openGame() {
		
		panel.open();
		
	}
	
	public static void saveGame(boolean saveAs) {
		
		panel.save(saveAs);
		
	}
	
	public static void setHUDColor(boolean b) {
		
		panel.setDrawLightHUD(b);
		
	}
	
	public static void enableSave(boolean b) {
		
		menuBar.enableSave(b);
		
	}
	
	public void equals(Frame frame) {
		
		
		
	}
	
}
