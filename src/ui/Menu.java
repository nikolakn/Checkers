package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * The Menu class draw and animate menu 
 * 
 */
public class Menu {
	// oval left half color
	private Color color;
	// menu position on offsets
	private static final int offsety = -20;
	private static final int offsetx = -15;
	private static final int ovalsize = 80;
	private static final int halfovalsize = ovalsize / 2;
	private static final int halfovalsize2 = 30;

	// positions of menu items
	private static final int x1 = 550 + offsetx;
	private static final int y1 = 100 + offsety;

	private static final int x2 = 650 + offsetx;
	private static final int y2 = 200 + offsety;

	private static final int x3 = 550 + offsetx;
	private static final int y3 = 320 + offsety;

	private static final int x4 = 650 + offsetx;
	private static final int y4 = 450 + offsety;

	// text colors
	private Color textcolor1 = Color.white;
	private Color textcolor2 = Color.white;
	private Color textcolor3 = Color.white;
	private Color textcolor4 = Color.white;

	// oval colors
	private Color c1 = Color.cyan;
	private Color c2 = Color.cyan;
	private Color c3 = Color.cyan;
	private Color c4 = Color.cyan;
	private Color c5 = Color.cyan;
	private Color c6 = Color.cyan;
	private Color c7 = Color.cyan;
	private Color c8 = Color.cyan;
	private Color c9 = Color.cyan;
	private Color c10 = Color.cyan;
	private Color c11 = Color.cyan;
	// is option menu shown
	private boolean optionmenu = false;
	// is now menu shown
	private boolean newnmenu = false;

	public Menu() {
		color = Color.red;
	}

	/**
	 * Draw menu items on screen
	 * @param g
	 * @param mute
	 */
	public void draw(Graphics g, boolean mute) {
		Font currentFont = g.getFont();
		Font newFont = currentFont.deriveFont(currentFont.getSize() * 0.76f);
		g.setFont(newFont);
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		//draw lines
		g2.setColor(Color.black);
		g2.drawLine(x1 + halfovalsize, y1 + halfovalsize, x2 + halfovalsize, y2 + halfovalsize);
		g2.drawLine(x2 + halfovalsize, y2 + halfovalsize, x3 + halfovalsize, y3 + halfovalsize);
		g2.drawLine(x3 + halfovalsize, y3 + halfovalsize, x4 + halfovalsize, y4 + halfovalsize);

		g2.setPaint(new GradientPaint(new Point2D.Double(x1, y1), color, new Point2D.Double(x1 + 100, y1), c1));
		g2.fillOval(x1, y1, ovalsize, ovalsize);
		g2.setColor(textcolor1);
		if (!optionmenu) {
			g2.drawString("New", x1 + 22, y1 + 35);
			g2.drawString("Game", x1 + 18, y1 + 55);
		}
		g2.setPaint(new GradientPaint(new Point2D.Double(x2, y2), color, new Point2D.Double(x2 + 100, y2), c2));
		g2.fillOval(x2, y2, ovalsize, ovalsize);
		g2.setColor(textcolor2);
		if (!newnmenu)
			g2.drawString("Options", x2 + 11, y2 + 45);

		g2.setPaint(new GradientPaint(new Point2D.Double(x3, y3), color, new Point2D.Double(x3 + 100, y3), c3));
		g2.fillOval(x3, y3, ovalsize, ovalsize);
		g2.setColor(textcolor3);
		if (!optionmenu && !newnmenu)
			g2.drawString("Reassign", x3 + 8, y3 + 45);

		g2.setPaint(new GradientPaint(new Point2D.Double(x4, y4), color, new Point2D.Double(x4 + 100, y4), c4));
		g2.fillOval(x4, y4, ovalsize, ovalsize);
		g2.setColor(textcolor4);
		if (!optionmenu && !newnmenu)
			g2.drawString("Quit", x4 + 24, y4 + 45);

		// draw option menu if activated
		if (optionmenu) {
			g2.setColor(Color.black);
			g2.drawOval(x2, y2, 80, 80);
			Font newFont2 = currentFont.deriveFont(currentFont.getSize() * 0.62f);
			g.setFont(newFont2);
			int xx = x2 - 63;
			int yy = y2 + 10;
			g2.setPaint(new GradientPaint(new Point2D.Double(xx, yy), color, new Point2D.Double(xx + 80, yy), c5));
			g2.fillOval(xx, yy, 60, 60);
			g2.setColor(textcolor1);
			g2.drawString("Reload", xx + 10, yy + 35);

			xx = x2 + 10;
			yy = y2 + 83;
			g2.setPaint(new GradientPaint(new Point2D.Double(xx, yy), color, new Point2D.Double(xx + 80, yy), c6));
			g2.fillOval(xx, yy, 60, 60);
			g2.setColor(textcolor1);
			g2.drawString("Quit", xx + 18, yy + 35);

			xx = x2 + 83;
			yy = y2 + 10;
			g2.setPaint(new GradientPaint(new Point2D.Double(xx, yy), color, new Point2D.Double(xx + 80, yy), c7));
			g2.fillOval(xx, yy, 60, 60);
			g2.setColor(textcolor1);
			if(mute)
				g2.drawString("sound", xx + 15, yy + 35);
			else
				g2.drawString("mute", xx + 15, yy + 35);
			xx = x2 + 60;
			yy = y2 + 140;
			g2.setPaint(new GradientPaint(new Point2D.Double(xx, yy), color, new Point2D.Double(xx + 80, yy), c11));
			g2.fillOval(xx, yy, 60, 60);
			g2.setColor(textcolor1);
			g2.drawString("Credits", xx + 10, yy + 35);
			
			xx = x2 + 10;
			yy = y2 - 63;
			g2.setPaint(new GradientPaint(new Point2D.Double(xx, yy), color, new Point2D.Double(xx + 80, yy), c8));
			g2.fillOval(xx, yy, 60, 60);
			g2.setColor(textcolor1);
			g2.drawString("Tutorial", xx + 9, yy + 35);
		}

		// draw option menu if activated
		if (newnmenu) {
			g2.setColor(Color.black);
			g2.drawOval(x1, y1, 80, 80);
			Font newFont2 = currentFont.deriveFont(currentFont.getSize() * 0.62f);
			g.setFont(newFont2);
			int xx = x1 + 83;
			int yy = y1 + 10;
			g2.setPaint(new GradientPaint(new Point2D.Double(xx, yy), color, new Point2D.Double(xx + 80, yy), c9));
			g2.fillOval(xx, yy, 60, 60);
			g2.setColor(textcolor1);
			g2.drawString("Single", xx + 13, yy + 25);
			g2.drawString("Player", xx + 13, yy + 40);

			xx = x1 + 10;
			yy = y1 + 83;
			g2.setPaint(new GradientPaint(new Point2D.Double(xx, yy), color, new Point2D.Double(xx + 80, yy), c10));
			g2.fillOval(xx, yy, 60, 60);
			g2.setColor(textcolor1);
			g2.drawString("Two", xx + 17, yy + 25);
			g2.drawString("Player", xx + 13, yy + 40);
		}
	}

	/**
	 * highlights menu item on mouse hover
	 * @param x x-coordinate on screen
	 * @param y y-coordinate on screen
	 */
	public void mouseMove(int x, int y) {
		if (!optionmenu && !newnmenu) {
			textcolor1 = Color.white;
			textcolor2 = Color.white;
			textcolor3 = Color.white;
			textcolor4 = Color.white;
			c1 = Color.cyan;
			c2 = Color.cyan;
			c3 = Color.cyan;
			c4 = Color.cyan;
			c5 = Color.cyan;
			c6 = Color.cyan;
			c7 = Color.cyan;
			c8 = Color.cyan;
		    
			int cx = x1 + halfovalsize;
			int cy = y1 + halfovalsize;
			double distance = Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y));
			if (distance < halfovalsize) {
				textcolor1 = Color.yellow;
				c1 = Color.blue;
			}

			cx = x2 + halfovalsize;
			cy = y2 + halfovalsize;
			distance = Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y));
			if (distance < halfovalsize) {
				textcolor2 = Color.yellow;
				c2 = Color.blue;
			}

			cx = x3 + halfovalsize;
			cy = y3 + halfovalsize;
			distance = Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y));
			if (distance < halfovalsize) {
				textcolor3 = Color.yellow;
				c3 = Color.blue;
			}

			cx = x4 + halfovalsize;
			cy = y4 + halfovalsize;
			distance = Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y));
			if (distance < halfovalsize) {
				textcolor4 = Color.yellow;
				c4 = Color.blue;
			}
		} else if (optionmenu) {
			c5 = Color.cyan;
			c6 = Color.cyan;
			c7 = Color.cyan;
			c8 = Color.cyan;
			c11 = Color.cyan;
			int xx = x2 - 63;
			int yy = y2 + 10;
			int cx = xx + 30;
			int cy = yy + 30;
			double distance = Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y));
			if (distance < halfovalsize2)
				c5 = Color.blue;
			xx = x2 + 10;
			yy = y2 + 83;
			cx = xx + 30;
			cy = yy + 30;
			distance = Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y));
			if (distance < halfovalsize2)
				c6 = Color.blue;
			xx = x2 + 83;
			yy = y2 + 10;
			cx = xx + 30;
			cy = yy + 30;
			distance = Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y));
			if (distance < halfovalsize2)
				c7 = Color.blue;
			xx = x2 + 10;
			yy = y2 - 63;
			cx = xx + 30;
			cy = yy + 30;
			distance = Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y));
			if (distance < halfovalsize2)
				c8 = Color.blue;
			xx = x2 + 60;
			yy = y2 + 140;
			cx = xx + 30;
			cy = yy + 30;
			distance = Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y));
			if (distance < halfovalsize2)
				c11 = Color.blue;
		} else {
			c9 = Color.cyan;
			c10 = Color.cyan;
			
			int xx = x1 + 83;
			int yy = y1 + 10;
			int cx = xx + 30;
			int cy = yy + 30;
			double distance = Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y));
			if (distance < halfovalsize2)
				c9 = Color.blue;
			xx = x1 + 10;
			yy = y1 + 83;
			cx = xx + 30;
			cy = yy + 30;
			distance = Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y));
			if (distance < halfovalsize2)
				c10 = Color.blue;
		}
	}

	/**
	 * Returns menu item on x,y coordinate
	 * @param x x-coordinate on screen
	 * @param y y-coordinate on screen
	 * @return item id , if no item return 0
	 */
	public int menuPressed(int x, int y) {
		if (!optionmenu && !newnmenu) {
			int cx = x1 + halfovalsize;
			int cy = y1 + halfovalsize;
			double distance = Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y));
			if (distance < halfovalsize)
				return 1;
			cx = x2 + halfovalsize;
			cy = y2 + halfovalsize;
			distance = Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y));
			if (distance < halfovalsize)
				return 2;
			cx = x3 + halfovalsize;
			cy = y3 + halfovalsize;
			distance = Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y));
			if (distance < halfovalsize)
				return 3;
			cx = x4 + halfovalsize;
			cy = y4 + halfovalsize;
			distance = Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y));
			if (distance < halfovalsize)
				return 4;
		} else if (optionmenu) {
			int xx = x2 - 63;
			int yy = y2 + 10;
			int cx = xx + 30;
			int cy = yy + 30;
			double distance = Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y));
			if (distance < halfovalsize2){
				optionmenu = false;
				textcolor1 = Color.white;
				textcolor2 = Color.white;
				textcolor3 = Color.white;
				textcolor4 = Color.white;
				c1 = Color.cyan;
				c2 = Color.cyan;
				c3 = Color.cyan;
				c4 = Color.cyan;
			
				return 5;
			}
			xx = x2 + 10;
			yy = y2 + 83;
			cx = xx + 30;
			cy = yy + 30;
			distance = Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y));
			if (distance < halfovalsize2){
				optionmenu = false;
				textcolor1 = Color.white;
				textcolor2 = Color.white;
				textcolor3 = Color.white;
				textcolor4 = Color.white;
				c1 = Color.cyan;
				c2 = Color.cyan;
				c3 = Color.cyan;
				c4 = Color.cyan;
				return 6;
			}
			xx = x2 + 83;
			yy = y2 + 10;
			cx = xx + 30;
			cy = yy + 30;
			distance = Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y));
			if (distance < halfovalsize2){
				optionmenu = false;
				textcolor1 = Color.white;
				textcolor2 = Color.white;
				textcolor3 = Color.white;
				textcolor4 = Color.white;
				c1 = Color.cyan;
				c2 = Color.cyan;
				c3 = Color.cyan;
				c4 = Color.cyan;
				return 7;
			}
			xx = x2 + 10;
			yy = y2 - 63;
			cx = xx + 30;
			cy = yy + 30;
			distance = Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y));
			if (distance < halfovalsize2){
				optionmenu = false;
				textcolor1 = Color.white;
				textcolor2 = Color.white;
				textcolor3 = Color.white;
				textcolor4 = Color.white;
				c1 = Color.cyan;
				c2 = Color.cyan;
				c3 = Color.cyan;
				c4 = Color.cyan;
				return 8;
			}
			xx = x2 + 60;
			yy = y2 + 140;
			cx = xx + 30;
			cy = yy + 30;
			distance = Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y));
			if (distance < halfovalsize2){
				optionmenu = false;
				textcolor1 = Color.white;
				textcolor2 = Color.white;
				textcolor3 = Color.white;
				textcolor4 = Color.white;
				c1 = Color.cyan;
				c2 = Color.cyan;
				c3 = Color.cyan;
				c4 = Color.cyan;
				return 11;
			}
		} else {
			c9 = Color.cyan;
			c10 = Color.cyan;
			int xx = x1 + 83;
			int yy = y1 + 10;
			int cx = xx + 30;
			int cy = yy + 30;
			double distance = Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y));
			if (distance < halfovalsize){
				newnmenu = false;
				textcolor1 = Color.white;
				textcolor2 = Color.white;
				textcolor3 = Color.white;
				textcolor4 = Color.white;
				c1 = Color.cyan;
				c2 = Color.cyan;
				c3 = Color.cyan;
				c4 = Color.cyan;
				return 9;
			}
			xx = x1 + 10;
			yy = y1 + 83;
			cx = xx + 30;
			cy = yy + 30;
			distance = Math.sqrt((cx - x) * (cx - x) + (cy - y) * (cy - y));
			if (distance < halfovalsize){
				newnmenu = false;
				textcolor1 = Color.white;
				textcolor2 = Color.white;
				textcolor3 = Color.white;
				textcolor4 = Color.white;
				c1 = Color.cyan;
				c2 = Color.cyan;
				c3 = Color.cyan;
				c4 = Color.cyan;
				return 10;
			}
		}
		return 0;
	}

	/**
	 * show option menu items on window
	 */
	public void displayOptionsMenu() {
		textcolor1 = Color.white;
		textcolor2 = Color.white;
		textcolor3 = Color.white;
		textcolor4 = Color.white;
		c1 = Color.white;
		c2 = Color.white;
		c3 = Color.white;
		c4 = Color.white;
		optionmenu = true;
	}

	/**
	 *  hide option menu items
	 */
	public void hideOptionsMenu() {
		optionmenu = false;
		textcolor1 = Color.white;
		textcolor2 = Color.white;
		textcolor3 = Color.white;
		textcolor4 = Color.white;
		c1 = Color.cyan;
		c2 = Color.cyan;
		c3 = Color.cyan;
		c4 = Color.cyan;
	}
	/**
	 * show new menu items on window
	 */
	public void displayNewGameMenu() {
		textcolor1 = Color.white;
		textcolor2 = Color.white;
		textcolor3 = Color.white;
		textcolor4 = Color.white;
		c1 = Color.white;
		c2 = Color.white;
		c3 = Color.white;
		c4 = Color.white;
		newnmenu = true;
	}
	/**
	 *  hide new menu items
	 */
	public void hideNewGamesMenu() {
		newnmenu = false;
		textcolor1 = Color.white;
		textcolor2 = Color.white;
		textcolor3 = Color.white;
		textcolor4 = Color.white;
		c1 = Color.cyan;
		c2 = Color.cyan;
		c3 = Color.cyan;
		c4 = Color.cyan;
	}
	/**
	 * Play sound when menu item is clicked
	 * @param m if true sound is muted
	 */
	public static synchronized void playMenuSound(boolean m) {
		//if mute return
		 if(m)
			 return;
		  new Thread(new Runnable() {
		  // The wrapper thread is unnecessary, unless it blocks on the
		  // Clip finishing; see comments.
		    public void run() {
		      try {
		        Clip clip1 = AudioSystem.getClip();
		        AudioInputStream inputStream = AudioSystem.getAudioInputStream(
		        this.getClass().getResource("/resources/s4.wav"));
		        clip1.open(inputStream);
		        clip1.start(); 
		      } catch (Exception e) {
		        System.err.println(e.getMessage());
		      }
		    }
		  }).start();
		}
	
}
