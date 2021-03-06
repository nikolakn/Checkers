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

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * The Game class is the main class it create window and UI 
 * 
 */
public class Game {
	/**
	* Game class constructor create window and set size and title.
	*/
	public Game() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				JFrame frame = new JFrame("Checkers");
				frame.setSize(800, 570);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.add(new Window());
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
	}

	public static void main(String[] args) {
		new Game();
	}

}