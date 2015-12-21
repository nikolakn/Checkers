/**
* (C) Copyright 2015
* Author: Nikola Knezevic <nkcodeplus@gmail.com>
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License
* as published by the Free Software Foundation; version 2
* of the License.
*/
package checkers;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.util.ArrayList;
import java.util.Observable;

import javax.swing.ImageIcon;

import ui.Window;
/**
 * The Board class represent board on the window.
 * 
 */
public class Board extends Observable {
	//image fir black tile
	private Image Bsquare;
	//image for white tile
	private Image Wsquare;
	//game engine
	private Engine engine;
	private boolean isGameStart = false;
	private boolean mute = false;
	//is single player game
	private boolean isCPU = false;
	
	/**
	 * Board class constructor
	 * creates engine
	 */
	public Board() {
		engine = new Engine();
	}
	/**
	 * load images from resources
	 */
	public void loadImage() {
		
		URL url = Window.class.getResource("/resources/Bsquare.png");
		URL url2 = Window.class.getResource("/resources/Wsquare.png");

		ImageIcon ii = new ImageIcon(url);
		ImageIcon ii2 = new ImageIcon(url2);
		Bsquare = ii.getImage();
		Wsquare = ii2.getImage();
	}
	/**
	 * Draw everything on board
	 */
	public synchronized void drawBoard(Graphics g, ImageObserver io) {
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(Color.black);
		Font currentFont = g.getFont();
		Font newFont = currentFont.deriveFont(currentFont.getSize() * 2.0f);
		g.setFont(newFont);
		//display messages
		if(isGameStart){
			g.drawString(engine.getCurrentTurn(), 550, 30);
			g.drawString(engine.getCurrentMessage(), 550, 60);
		} else {
			g.drawString("Start new game", 550, 30);
			g.drawString(engine.getCurrentMessage(), 550, 60);
		}
		//draw empty board
		int x = 10;
		int y = 10;
		for (int k = 1; k < 9; k++) {
			for (int i = 1; i < 9; i++) {
				if (k % 2 == 0) {
					if (i % 2 == 0)
						g.drawImage(Wsquare, x, y, io);
					else
						g.drawImage(Bsquare, x, y, io);
					x = x + 64;
				} else {
					if (i % 2 == 0)
						g.drawImage(Bsquare, x, y, io);
					else
						g.drawImage(Wsquare, x, y, io);
					x = x + 64;
				}

			}
			y = y + 64;
			x = 10;
		}
		//draw tiles
		if (engine.getSelectedTile()!=null) {
			int dx = engine.getSelectedTile().getIndexx();
			int dy = engine.getSelectedTile().getIndexy();
			dx = (dx * 64) + 10;
			dy = (dy * 64) + 10;
			Stroke oldStroke = g2.getStroke();
			g2.setStroke(new BasicStroke(3));
			g2.setColor(Color.yellow);
			g2.drawRect(dx, dy, 64, 64);
			ArrayList<Move> m = engine.getLegalMoves();
			for (Move mv : m) {
				g2.setStroke(new BasicStroke(2));
				dx = mv.x;
				dy = mv.y;
				dx = (dx * 64) + 10;
				dy = (dy * 64) + 10;
				g2.setColor(Color.LIGHT_GRAY);
				g2.drawRect(dx, dy, 64, 64);
			}
			g2.setStroke(oldStroke);
		}
		//draw selected tiles
		try{
			for (Tile t : engine.getRedTiles()) {
				t.drawTile(g);
			}
			for (Tile t : engine.getBlueTiles()) {
				t.drawTile(g);
			}
		} catch (Exception e){
			
		}
	}
	/**
	 * checks id tile is clicked
	 * @param x coordinate on screen
	 * @param y coordinate on screen
	 */
	public void onMousePressed(int x, int y) {
		if (isGameStart) {
			if(isCPU && !engine.isIsfirstplayer())
				return;
			// board offset
			x -= 10;
			y -= 10;
			// coordinate
			x /= 64;
			y /= 64;
			if (x < 0 || x > 7)
				return;
			if (y < 0 || y > 7)
				return;
			//check is game over
			if (engine.isGameover()) {
				isGameStart = false;
				return;
			}
			if (engine.isTileSelected() && engine.isLegalMove(x, y)) {
				engine.moveTile(x, y,false);
				if(isCPU){
					setChanged();
					notifyObservers();
				}
			} else {
				engine.selectTile(x, y);
			}
			//check is game over
			if (engine.isGameover()) {
				isGameStart = false;
				return;
			}
		}
	}
	/**
	 * new single player game
	 */
	public void startSinglePleyer() {
		engine = new Engine();
		isGameStart = true;
		isCPU = true;
	}
	/**
	 * new two player game
	 */
	public void startTwoPlayerGame() {
		engine = new Engine();
		isCPU = false;
		isGameStart = true;
	}
	/**
	 * if it is single player game play cpu move 
	 */
	public void play() {
		if(!engine.isIsfirstplayer()){
			if (engine.isGameover()) {
				isGameStart = false;
				return;
			}
			engine.play(isMute());
		}		
	}
	/**
	 * is game started
	 * @return true if game is started
	 */
	public boolean isGameStart() {
		return isGameStart;
	}
	/**
	 * Starts game
	 */
	public void setGameStart(boolean isGameStart) {
		this.isGameStart = isGameStart;
	}

	public boolean isMute() {
		return mute;
	}

	public void setMute(boolean mute) {
		this.mute = mute;
		engine.setMute(mute);
	}
	/**
	 * is game in single player mode
	 * 
	 * @return true if single player mode
	 */
	public boolean isCPU() {
		return isCPU;
	}
	/**
	 * set single payer game
	 * @param isCPU true if single player game
	 */
	public void setCPU(boolean isCPU) {
		this.isCPU = isCPU;
	}

	public void setmessage(String m) {
		engine.setCurrentMessage(m);
		
	}
	/**
	 * restart current game
	 */
	public void restart() {
		if(isCPU)
			startSinglePleyer();
		else
			startTwoPlayerGame();
		
	}
	/**
	 * finish game if it is game over
	 */
	public void checkGameOver(){
		if (engine.isGameover()) {
			isGameStart = false;
			return;
		}
	}
}
