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

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
/**
 * The Tile class represent figures on the board.
 * 
 */
public class Tile {
	//constant first player name
	public final static String REDPLAYER = "red";
	//constant second player name
	public final static String BLUEPLAYER = "blue";
	//x coordinate for drawing
	private int x;
	//y coordinate for drawing
	private int y;
	//x coordinate on board 
	//coordinate starts on top left corner of board
	private int indexx;
	//y coordinate on board
	//coordinate starts on top left corner of board
	private int indexy;
	//color for drawing
	private Color color;
	//is figure king
	private boolean king;
	private String player = REDPLAYER;


	/**
	 * Tile class constructor.
	 * @param xx x coordinate on board
	 * @param yy y coordinate on board
	 * @param p player name
	 * @param isking is tile king
	 */
	public Tile(int xx, int yy, String p, boolean isking) {
		indexx = xx;
		indexy = yy;
		//convert board coordinate to window coordinate
		x = (xx * 64) + 10 + 5;
		y = (yy * 64) + 10 + 5;
		player = p;
		if (player == REDPLAYER)
			color = Color.RED;
		else
			color = Color.BLUE;
		king = isking;
	}

	/**
	 * Draw tiles on board
	 */
	public void drawTile(Graphics g) {
		// g.drawOval(x, y, 44, 44);
		Graphics2D g2 = (Graphics2D) g;
		// Paint oldPaint = g2.getPaint();
		g2.setColor(color);
		g2.fillOval(x, y, 54, 54);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(new GradientPaint(new Point2D.Double(x, y), color, new Point2D.Double(x + 100, y), Color.WHITE));

		g2.fillOval(x, y, 54, 54);
		// g2.setPaint(oldPaint);
		if (king) {
			Font currentFont = g.getFont();
			if (player == REDPLAYER)
				g2.setColor(Color.black);
			else
				g2.setColor(Color.white);
			Font newFont2 = currentFont.deriveFont(currentFont.getSize() * 1f);
			g2.setFont(newFont2);
			g2.drawString("K", x + 20, y + 34);
		}

	}
	
	//getters and setters
	
	/**
	 * is tile king
	 * @return	true if tile is king
	 */
	public boolean isKing() {
		return king;
	}
	/**
	 * set is tile king
	 */
	public void setKing(boolean king) {
		this.king = king;
	}
	/**
	 * Returns player name
	 * @return player name
	 */
	public String getPlayer() {
		return player;
	}
	/**
	 * Returns x coordinate on board
	 * @return x coordinate on board
	 */	
	public int getIndexx() {
		return indexx;
	}
	/**
	 * setter for x coordinate on board
	 */	
	public void setIndexx(int indexx) {
		x = (indexx * 64) + 10 + 5;
		this.indexx = indexx;
	}
	/**
	 * Returns y coordinate on board
	 * @return y coordinate on board
	 */	
	public int getIndexy() {	
		return indexy;
	}
	/**
	 * setter for y coordinate on board
	 */	
	public void setIndexy(int indexy) {
		y = (indexy * 64) + 10 + 5;
		this.indexy = indexy;
	}
	/**
	 * setter for player name
	 */		
	public void setPlayer(String player) {
		this.player = player;
	}
	
}
