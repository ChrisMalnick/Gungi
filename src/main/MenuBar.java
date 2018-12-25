package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

public class MenuBar extends JMenuBar {
	
	private static final long serialVersionUID = 0L;
	
	private static JMenu fileMenu;
	private static JMenuItem newMenuItem;
	private static JMenuItem openMenuItem;
	private static JMenuItem saveMenuItem;
	private static JMenuItem saveAsMenuItem;
	private static JMenuItem exitMenuItem;
	
	private static JMenu optionsMenu;
	private static ButtonGroup hudButtonGroup;
	private static JRadioButtonMenuItem hudDarkJRadioButtonMenuItem;
	private static JRadioButtonMenuItem hudLightJRadioButtonMenuItem;
	
	private static JMenu helpMenu;
	private static JMenuItem howToPlayMenuItem;
	private static JMenuItem aboutMenuItem;
	
	public MenuBar() {
		
		super();
		
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		this.add(fileMenu);
		
		newMenuItem = new JMenuItem("New", KeyEvent.VK_N);
		newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.ALT_MASK | ActionEvent.SHIFT_MASK));
		newMenuItem.addActionListener(new MenuActionListener());
		fileMenu.add(newMenuItem);
		
		openMenuItem = new JMenuItem("Open...", KeyEvent.VK_O);
		openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		openMenuItem.addActionListener(new MenuActionListener());
		fileMenu.add(openMenuItem);
		
		fileMenu.addSeparator();
		
		saveMenuItem = new JMenuItem("Save", KeyEvent.VK_S);
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveMenuItem.addActionListener(new MenuActionListener());
		fileMenu.add(saveMenuItem);
		
		saveAsMenuItem = new JMenuItem("Save As...");
		saveAsMenuItem.addActionListener(new MenuActionListener());
		fileMenu.add(saveAsMenuItem);
		
		fileMenu.addSeparator();
		
		exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new MenuActionListener());
		fileMenu.add(exitMenuItem);
		
		optionsMenu = new JMenu("Options");
		this.add(optionsMenu);
		
		hudButtonGroup = new ButtonGroup();
		
		hudDarkJRadioButtonMenuItem = new JRadioButtonMenuItem("Dark HUD");
		hudDarkJRadioButtonMenuItem.setSelected(true);
		hudDarkJRadioButtonMenuItem.addActionListener(new MenuActionListener());
		
		hudButtonGroup.add(hudDarkJRadioButtonMenuItem);
		optionsMenu.add(hudDarkJRadioButtonMenuItem);
		
		hudLightJRadioButtonMenuItem = new JRadioButtonMenuItem("Light HUD");
		hudLightJRadioButtonMenuItem.addActionListener(new MenuActionListener());
		
		hudButtonGroup.add(hudLightJRadioButtonMenuItem);
		optionsMenu.add(hudLightJRadioButtonMenuItem);
		
		helpMenu = new JMenu("Help");
		this.add(helpMenu);
		
		howToPlayMenuItem = new JMenuItem("How To Play");
		howToPlayMenuItem.addActionListener(new MenuActionListener());
		helpMenu.add(howToPlayMenuItem);
		
		aboutMenuItem = new JMenuItem("About");
		aboutMenuItem.addActionListener(new MenuActionListener());
		helpMenu.add(aboutMenuItem);
		
	}
	
	private class MenuActionListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			
			switch(e.getActionCommand().toString()) {
			
			case "New":			Frame.newGame();			break;
			case "Open...":		Frame.openGame();			break;
			case "Save":		Frame.saveGame(false);		break;
			case "Save As...":	Frame.saveGame(true);		break;
			case "Exit":		System.exit(0);				break;
			case "Dark HUD":	Frame.setHUDColor(false);	break;
			case "Light HUD":	Frame.setHUDColor(true);	break;
			case "How To Play":								break;
			case "About":									break;
			
			}
			
		}
		
	}
	
	public void enableSave(boolean b) {
		
		saveMenuItem.setEnabled(b);
		saveAsMenuItem.setEnabled(b);
		
	}
	
	public void equals(MenuBar menuBar) {
		
		
		
	}
	
}
