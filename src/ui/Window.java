/**
* (C) Copyright 2015
* Author: Nikola Knezevic <nkcodeplus@gmail.com>
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; version 2
* of the License.
*/

package ui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.net.URI;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import checkers.Board;
/**
 * The Window class represent user interface it create 
 * game board and menu.
 */
@SuppressWarnings("serial")
public class Window extends JPanel implements Runnable, MouseMotionListener, Observer {

	//private static final long serialVersionUID = 1L;
	//window size
	private final int B_WIDTH = 800;	
	private final int B_HEIGHT = 570;
	private final int DELAY = 25;
	private Thread animator;
	/**
	* Board that is displayed on window
	*/
	private Board board;
	/**
	* Menu that is displayed on window
	*/
	private Menu menu;

	/**
	* Window class constructor create Board and Menu.
	*/
	public Window() {
		board = new Board();
		board.addObserver(this);
		menu = new Menu();
		init();
		addMouseListener(new CheckersMouseListener());
		addMouseMotionListener(this);

	}
	
	/**
	* initialize window properties.
	*/
	private void init() {
		setFocusable(true);
		requestFocus();
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		setDoubleBuffered(true);
		loadImage();
	}
	
	/**
	* After everything is initialized load images.
	*/
	private void loadImage() {
		board.loadImage();
	}
	/**
	* Draw everything on window.
	*/
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		board.checkGameOver();
		board.drawBoard(g, this);
		menu.draw(g,board.isMute());
		Toolkit.getDefaultToolkit().sync();

	}
	/**
	* Repaint window after time interval.
	*/
	@Override
	public void run() {
		long beforeTime, timeDiff, sleep;
		beforeTime = System.currentTimeMillis();
		while (true) {
			repaint();
			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = DELAY - timeDiff;
			if (sleep < 0) {
				sleep = 2;
			}
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println("Interrupted: " + e.getMessage());
			}
			beforeTime = System.currentTimeMillis();
		}
	}
	@Override
	public void addNotify() {
		super.addNotify();
		animator = new Thread(this);
		animator.start();
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		//highlight menu item if mouse is over it
		menu.mouseMove(e.getX(), e.getY());
	}

	private class CheckersMouseListener extends MouseInputAdapter {
		public void mousePressed(MouseEvent e) {
			board.onMousePressed(e.getX(), e.getY());
			int i = menu.menuPressed(e.getX(), e.getY());
			repaint();

			switch (i) {
			case 0:
				//no item selected
				menu.hideOptionsMenu();
				menu.hideNewGamesMenu();
				break;
			case 1:
				//new game
				Menu.playMenuSound(board.isMute());
				menu.displayNewGameMenu();
				break;
			case 2:
				//options
				Menu.playMenuSound(board.isMute());
				menu.displayOptionsMenu();
				break;
			case 3:
				//Reassign
				Menu.playMenuSound(board.isMute());
				board.restart();
				break;
			case 4:
				//quit main menu
				Menu.playMenuSound(board.isMute());
				System.exit(0);
				break;
			case 5:
				//reload
				Menu.playMenuSound(board.isMute());
				board.restart();
				break;
			case 6:
				//quit
				System.exit(0);
				break;
			case 7:
				//mute
				board.setMute(!board.isMute());
				Menu.playMenuSound(board.isMute());
				break;
			case 8:
				//tutorial
				Menu.playMenuSound(board.isMute());
				try {
					openWebpage(new URI("https://www.youtube.com/watch?v=SuQY1_fCVsA"));
			    } catch (Exception ex) {
			        ex.printStackTrace();
			    }
				break;
			case 9:
				//single player
				Menu.playMenuSound(board.isMute());
				board.startSinglePleyer();
				break;
			case 10:
				//two players
				Menu.playMenuSound(board.isMute());
				board.startTwoPlayerGame();
				break;
			case 11:
				//credit
				Menu.playMenuSound(board.isMute());
				showCredit();
				break;
			}

			repaint();
		}
	}
	/**
	* show credit dialog.
	*/
	public void showCredit(){
		JOptionPane.showMessageDialog(null, "Nikola Knezevic 2015", "About", JOptionPane.PLAIN_MESSAGE);
	}
	/**
	* Opens web browser and display tutorial page
	*/
	public static void openWebpage(URI uri) {
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(uri);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
	/**
	* redraw window after move is played.
	*/
	@Override
	public void update(Observable o, Object arg) {
		board.setmessage("Thinking...");
		repaint();
		board.play();
	}
}