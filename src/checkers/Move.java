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
/**
 * The Move class represent one move on the board.
 * Moves can be compare by score calculated by AI
 */
public class Move implements Comparable<Move>  {
	/**
	 * x position before move
	 */
	public int currentX;
	
	/**
	 * y position before move
	 */
	public int currentY;
	
	/**
	 * x position after move
	 */
	public int x;
	
	/**
	 * y position after move
	 */
	public int y;
	
	
	/**
	 * true if the move kills opponent's figure
	 */
	public boolean iskill=false;
	
	/**
	 * killed figure
	 */	
	Tile killed= null;
	public int winscore=0;
	
	public Move(int cx,int cy,int i, int j, boolean kill, Tile k) {
		x = i;
		y = j;
		currentX = cx;
		currentY = cy;
		iskill = kill;
		killed = k;
	}
	/**
	 * compare moves based on winscore that ai gives it
	 */	
	@Override
	public int compareTo(Move o) {
		// TODO Auto-generated method stub
		return  Integer.compare(o.winscore, winscore);
	}

}
