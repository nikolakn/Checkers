package checkers;

import java.util.ArrayList;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * The Engine class represent data model for checkers game.
 * 
 */
public class Engine {

	// selected figure
	private boolean tileSelected = false;
	// selected tile
	private Tile selectedTile = null;
	// is red player turn
	private boolean isfirstplayer = true;
	// message to display on window
	private String currentMessage = "";
	//all red tiles on the board
	private ArrayList<Tile> redTiles;
	//all blue tiles on the board
	private ArrayList<Tile> blueTiles;
	//all legal moves for selected tile
	private ArrayList<Move> legalMoves;
	// for multiple move
	private boolean killingmode = false;
	// sound on-off
	private boolean mute = false;
	/**
	 * Engine class constructor.
	 * generate initial position on board.
	 */
	public Engine() {
		// generate starting position
		redTiles = new ArrayList<Tile>();
		blueTiles = new ArrayList<Tile>();
		setLegalMoves(new ArrayList<Move>());

		Tile[] blue = new Tile[12];
		Tile[] red = new Tile[12];
		// blue player start position
		blue[0] = new Tile(0, 0, Tile.BLUEPLAYER, false);
		blue[1] = new Tile(2, 0, Tile.BLUEPLAYER, false);
		blue[2] = new Tile(4, 0, Tile.BLUEPLAYER, false);
		blue[3] = new Tile(6, 0, Tile.BLUEPLAYER, false);
		blue[4] = new Tile(1, 1, Tile.BLUEPLAYER, false);
		blue[5] = new Tile(3, 1, Tile.BLUEPLAYER, false);
		blue[6] = new Tile(5, 1, Tile.BLUEPLAYER, false);
		blue[7] = new Tile(7, 1, Tile.BLUEPLAYER, false);
		blue[8] = new Tile(0, 2, Tile.BLUEPLAYER, false);
		blue[9] = new Tile(2, 2, Tile.BLUEPLAYER, false);
		blue[10] = new Tile(4, 2, Tile.BLUEPLAYER, false);
		blue[11] = new Tile(6, 2, Tile.BLUEPLAYER, false);
		// red player start position
		red[0] = new Tile(1, 5, Tile.REDPLAYER, false);
		red[1] = new Tile(3, 5, Tile.REDPLAYER, false);
		red[2] = new Tile(5, 5, Tile.REDPLAYER, false);
		red[3] = new Tile(7, 5, Tile.REDPLAYER, false);
		red[4] = new Tile(0, 6, Tile.REDPLAYER, false);
		red[5] = new Tile(2, 6, Tile.REDPLAYER, false);
		red[6] = new Tile(4, 6, Tile.REDPLAYER, false);
		red[7] = new Tile(6, 6, Tile.REDPLAYER, false);
		red[8] = new Tile(1, 7, Tile.REDPLAYER, false);
		red[9] = new Tile(3, 7, Tile.REDPLAYER, false);
		red[10] = new Tile(5, 7, Tile.REDPLAYER, false);
		red[11] = new Tile(7, 7, Tile.REDPLAYER, false);

		for (int i = 0; i < 12; i++) {
			redTiles.add(red[i]);
			blueTiles.add(blue[i]);
		}

	}

	/**
	 * Copy constructor
	 * 
	 * @param e Engine to copy
	 */
	public Engine(Engine e) {
		redTiles = new ArrayList<Tile>();
		blueTiles = new ArrayList<Tile>();
		setLegalMoves(new ArrayList<Move>());
		for (Tile rt : e.redTiles) {
			Tile temp = new Tile(rt.getIndexx(), rt.getIndexy(), rt.getPlayer(), rt.isKing());
			redTiles.add(temp);
		}
		for (Tile rt : e.blueTiles) {
			Tile temp = new Tile(rt.getIndexx(), rt.getIndexy(), rt.getPlayer(), rt.isKing());
			blueTiles.add(temp);
		}
		tileSelected = e.tileSelected;
		selectedTile = e.selectedTile;
		isfirstplayer = e.isfirstplayer;
		currentMessage = e.currentMessage;
		killingmode = e.killingmode;
		mute = e.mute;

	}

	/**
	 * player select tile on position x,y
	 * 
	 * @param x x coordinate on board
	 * @param y y coordinate on board
	 */
	public void selectTile(int x, int y) {
		if (isfirstplayer) {
			Tile t = getTile(x, y);
			if (t != null) {

				if (t.getPlayer() == Tile.REDPLAYER) {
					setSelectedTile(t);
					tileSelected = true;
					currentMessage = "Selected " + (x + 1) + "," + (8 - y);
					setLegalMoves(LegalMoves(t));
				} else {
					currentMessage = "RED player's Turn!";
				}
			}
		} else {
			Tile t = getTile(x, y);
			if (t != null) {
				if (t.getPlayer() == Tile.BLUEPLAYER) {
					setSelectedTile(t);
					tileSelected = true;
					setLegalMoves(LegalMoves(t));
				} else {
					currentMessage = "BLUE player's Turn!";
				}
			}
		}
	}

	/**
	 * get tile on x,y coordinate
	 * 	 
	 * @param x x coordinate on board
	 * @param y y coordinate on board
	 */
	private Tile getTile(int x, int y) {
		Tile t = null;
		for (Tile rt : redTiles) {

			if (rt.getIndexx() == x && rt.getIndexy() == y) {
				return rt;
			}
		}
		for (Tile bt : blueTiles) {
			if (bt.getIndexx() == x && bt.getIndexy() == y)
				return bt;
		}
		return t;
	}

	/**
	 * calculate can selected tile move on x,y coordinate
	 * 	
	 * @param x x coordinate on board
	 * @param y y coordinate on board
	 */
	public boolean isLegalMove(int x, int y) {
		if (isTileSelected()) {
			ArrayList<Move> mv = LegalMoves(getSelectedTile());
			for (Move m : mv) {
				if (m.x == x && m.y == y)
					return true;
			}
		}
		return false;
	}

	/**
	 * Calculate legal moves for tile
	 * 
	 * @param t tile on board
	 * @return list of possible moves
	 */
	public ArrayList<Move> LegalMoves(Tile t) {
		ArrayList<Move> lm = new ArrayList<Move>();
		if (t == null)
			return lm;
		int xx = t.getIndexx();
		int yy = t.getIndexy();
		// red player
		if (t.getPlayer() == Tile.REDPLAYER) {
			// tile is not king
			if (!t.isKing()) {
				// may go left
				if (t.getIndexy() > 0) {
					if (t.getIndexx() > 0) {
						// if left diagonal square is empty
						if (getTile(xx - 1, yy - 1) == null)
							lm.add(new Move(xx, yy, xx - 1, yy - 1, false, null));
						else {
							// if left diagonal square is occupied but next squareis free
							if (getTile(xx - 1, yy - 1).getPlayer() == Tile.BLUEPLAYER) {
								if ((t.getIndexx() > 1 && t.getIndexy() > 1) && getTile(xx - 2, yy - 2) == null) {
									lm.add(new Move(xx, yy, xx - 2, yy - 2, true, getTile(xx - 1, yy - 1)));
								}
							}
						}
					}
					// may go right
					if (t.getIndexx() < 7) {
						// if right diagonal square is empty
						if (getTile(xx + 1, yy - 1) == null)
							lm.add(new Move(xx, yy, xx + 1, yy - 1, false, null));
						else {
							// if right diagonal square is occupied but next square is free
							if (getTile(xx + 1, yy - 1).getPlayer() == Tile.BLUEPLAYER) {
								if ((t.getIndexx() < 6 && t.getIndexy() > 1) && getTile(xx + 2, yy - 2) == null) {
									lm.add(new Move(xx, yy, xx + 2, yy - 2, true, getTile(xx + 1, yy - 1)));
								}
							}
						}
					}
				}
				// tile is king
			} else {
				if (t.getIndexy() > 0) {
					if (t.getIndexx() > 0) {
						// if left diagonal square is empty
						if (getTile(xx - 1, yy - 1) == null)
							lm.add(new Move(xx, yy, xx - 1, yy - 1, false, null));
						else {
							// if left diagonal square is occupied but next squareis free
							if (getTile(xx - 1, yy - 1).getPlayer() == Tile.BLUEPLAYER) {
								if ((t.getIndexx() > 1 && t.getIndexy() > 1) && getTile(xx - 2, yy - 2) == null) {
									lm.add(new Move(xx, yy, xx - 2, yy - 2, true, getTile(xx - 1, yy - 1)));
								}
							}
						}
					}
					// may go right
					if (t.getIndexx() < 7) {
						// if right diagonal square is empty
						if (getTile(xx + 1, yy - 1) == null)
							lm.add(new Move(xx, yy, xx + 1, yy - 1, false, null));
						else {
							// if right diagonal square is occupied but next square is free
							if (getTile(xx + 1, yy - 1).getPlayer() == Tile.BLUEPLAYER) {
								if ((t.getIndexx() < 6 && t.getIndexy() > 1) && getTile(xx + 2, yy - 2) == null) {
									lm.add(new Move(xx, yy, xx + 2, yy - 2, true, getTile(xx + 1, yy - 1)));
								}
							}
						}
					}
				}
				if (t.getIndexy() < 7) {
					// may go left
					if (t.getIndexx() > 0) {
						// if left diagonal square is empty
						if (getTile(xx - 1, yy + 1) == null)
							lm.add(new Move(xx, yy, xx - 1, yy + 1, false, null));
						else {
							// killing
							if (getTile(xx - 1, yy + 1).getPlayer() == Tile.BLUEPLAYER) {
								if ((t.getIndexx() > 1 && t.getIndexy() < 6) && getTile(xx - 2, yy + 2) == null) {
									lm.add(new Move(xx, yy, xx - 2, yy + 2, true, getTile(xx - 1, yy + 1)));
								}
							}
						}
					}
					// may go right
					if (t.getIndexx() < 7) {
						// if right diagonal square is empty
						if (getTile(xx + 1, yy + 1) == null)
							lm.add(new Move(xx, yy, xx + 1, yy + 1, false, null));
						else {
							// if right diagonal square is occupied but next square is free
							if (getTile(xx + 1, yy + 1).getPlayer() == Tile.BLUEPLAYER) {
								if ((t.getIndexx() < 6 && t.getIndexy() < 6) && getTile(xx + 2, yy + 2) == null) {
									lm.add(new Move(xx, yy, xx + 2, yy + 2, true, getTile(xx + 1, yy + 1)));
								}
							}
						}
					}

				}

			}
			// blue player
		} else {

			// tile is not king
			if (!t.isKing()) {
				if (t.getIndexy() < 7) {
					// may go left
					if (t.getIndexx() > 0) {
						// if left diagonal square is empty
						if (getTile(xx - 1, yy + 1) == null)
							lm.add(new Move(xx, yy, xx - 1, yy + 1, false, null));
						else {
							// killing
							if (getTile(xx - 1, yy + 1).getPlayer() == Tile.REDPLAYER) {
								if ((t.getIndexx() > 1 && t.getIndexy() < 6) && getTile(xx - 2, yy + 2) == null) {
									lm.add(new Move(xx, yy, xx - 2, yy + 2, true, getTile(xx - 1, yy + 1)));
								}
							}
						}
					}
					// may go right
					if (t.getIndexx() < 7) {
						// if right diagonal square is empty
						if (getTile(xx + 1, yy + 1) == null)
							lm.add(new Move(xx, yy, xx + 1, yy + 1, false, null));
						else {
							// if right diagonal square is occupied but next square is free
							if (getTile(xx + 1, yy + 1).getPlayer() == Tile.REDPLAYER) {
								if ((t.getIndexx() < 6 && t.getIndexy() < 6) && getTile(xx + 2, yy + 2) == null) {
									lm.add(new Move(xx, yy, xx + 2, yy + 2, true, getTile(xx + 1, yy + 1)));
								}
							}
						}
					}
				}
				// tile is king
			} else {
				if (t.getIndexy() > 0) {
					if (t.getIndexx() > 0) {
						// if left diagonal square is empty
						if (getTile(xx - 1, yy - 1) == null)
							lm.add(new Move(xx, yy, xx - 1, yy - 1, false, null));
						else {
							// if left diagonal square is occupied but next squareis free
							if (getTile(xx - 1, yy - 1).getPlayer() == Tile.REDPLAYER) {
								if ((t.getIndexx() > 1 && t.getIndexy() > 1) && getTile(xx - 2, yy - 2) == null) {
									lm.add(new Move(xx, yy, xx - 2, yy - 2, true, getTile(xx - 1, yy - 1)));
								}
							}
						}
					}
					// may go right
					if (t.getIndexx() < 7) {
						// if right diagonal square is empty
						if (getTile(xx + 1, yy - 1) == null)
							lm.add(new Move(xx, yy, xx + 1, yy - 1, false, null));
						else {
							// if right diagonal square is occupied but next  square is free
							if (getTile(xx + 1, yy - 1).getPlayer() == Tile.REDPLAYER) {
								if ((t.getIndexx() < 6 && t.getIndexy() > 1) && getTile(xx + 2, yy - 2) == null) {
									lm.add(new Move(xx, yy, xx + 2, yy - 2, true, getTile(xx + 1, yy - 1)));
								}
							}
						}
					}
				}
				if (t.getIndexy() < 7) {
					// may go left
					if (t.getIndexx() > 0) {
						// if left diagonal square is empty
						if (getTile(xx - 1, yy + 1) == null)
							lm.add(new Move(xx, yy, xx - 1, yy + 1, false, null));
						else {
							// killing
							if (getTile(xx - 1, yy + 1).getPlayer() == Tile.REDPLAYER) {
								if ((t.getIndexx() > 1 && t.getIndexy() < 6) && getTile(xx - 2, yy + 2) == null) {
									lm.add(new Move(xx, yy, xx - 2, yy + 2, true, getTile(xx - 1, yy + 1)));
								}
							}
						}
					}
					// may go right
					if (t.getIndexx() < 7) {
						// if right diagonal square is empty
						if (getTile(xx + 1, yy + 1) == null)
							lm.add(new Move(xx, yy, xx + 1, yy + 1, false, null));
						else {
							// if right diagonal square is occupied but next
							// square
							// is free
							if (getTile(xx + 1, yy + 1).getPlayer() == Tile.REDPLAYER) {
								if ((t.getIndexx() < 6 && t.getIndexy() < 6) && getTile(xx + 2, yy + 2) == null) {
									lm.add(new Move(xx, yy, xx + 2, yy + 2, true, getTile(xx + 1, yy + 1)));
								}
							}
						}
					}

				}
			}

		}
		// if in killing mode only kill moves are legal
		if (killingmode) {
			ArrayList<Move> rmv = new ArrayList<Move>();
			for (Move m : lm) {
				if (!m.iskill)
					rmv.add(m);

			}
			//filter killing moves only
			for (Move m : rmv) {
				lm.remove(m);
			}
		}
		return lm;
	}


	/**
	 * move selected tile to x,y position
	 * 
	 * @param x coordinate on board
	 * @param y coordinate on board
	 * @param sim is move played by ai 
	 */
	public  void  moveTile(int x, int y, boolean sim) {
		ArrayList<Move> mv = LegalMoves(getSelectedTile());
		boolean kill = false;
		//removes opponent tile if move is kill 
		for (Move m : mv) {
			if (m.x == x && m.y == y)
				if (m.iskill) {
					kill = true;
					if (!isMute() && !sim)
						playKillSound();
					if (m.killed.getPlayer() == Tile.REDPLAYER)
						redTiles.remove(m.killed);
						
					else
						blueTiles.remove(m.killed);
				}
		}
		//move selected tile
		getSelectedTile().setIndexx(x);
		getSelectedTile().setIndexy(y);
		// regenerate legale moves list 
		setLegalMoves(new ArrayList<Move>());
		ArrayList<Move> lm = LegalMoves(getSelectedTile());
		 
		for (Move m : lm) {
			//in killing mode   
			if (m.iskill && kill) {
				killingmode = true;
				legalMoves = LegalMoves(getSelectedTile());
				if(sim){
					if(legalMoves.size()>0)
					moveTile(legalMoves.get(0).x,legalMoves.get(0).y,true);
				}
				return;
			}

		}
		//promote to king for red player
		if (getSelectedTile().getPlayer() == Tile.REDPLAYER && getSelectedTile().getIndexy() == 0) {
			getSelectedTile().setKing(true);
		}
		//promote to king for blue player
		if (getSelectedTile().getPlayer() == Tile.BLUEPLAYER && getSelectedTile().getIndexy() == 7) {
			getSelectedTile().setKing(true);
		}
		if (!isMute() && !sim)
			playMoveSound();
		//switch player turn
		killingmode = false;
		if (isfirstplayer) {
			isfirstplayer = false;
			currentMessage = "";
		} else {
			isfirstplayer = true;
			currentMessage = "";
		}

		// selectedTile = null;
		tileSelected = false;
	}

	/**
	 * can red player moves
	 */
	public boolean canRedPlayerMakeMove() {
		for (Tile r : redTiles) {
			if (!LegalMoves(r).isEmpty())
				return true;
		}
		return false;
	}
	/**
	 * can blue player moves
	 */
	public boolean canBluePlayerMakeMove() {
		for (Tile r : blueTiles) {
			if (!LegalMoves(r).isEmpty())
				return true;
		}
		return false;
	}
	/**
	 * Returns game state and set message to be displayed
	 * @return true if game is over
	 */
	public boolean isGameover() {
		if (isfirstplayer) {
			if (!canRedPlayerMakeMove()) {
				currentMessage = "Blue WIN";
				return true;
			}
		} else {
			if (!canBluePlayerMakeMove()) {
				currentMessage = "Red WIN";
				return true;
			}
		}
		return false;
	}
	/**
	 * plays move sound 
	 */
	public static synchronized void playMoveSound() {
		new Thread(new Runnable() {
			// The wrapper thread is unnecessary, unless it blocks on the
			// Clip finishing; see comments.
			public void run() {
				try {
					Clip clip1 = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem
							.getAudioInputStream(this.getClass().getResource("/resources/s2.wav"));
					clip1.open(inputStream);
					clip1.start();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}
	/**
	 * plays kill sound 
	 */
	public static synchronized void playKillSound() {
		;
		new Thread(new Runnable() {
			// The wrapper thread is unnecessary, unless it blocks on the
			// Clip finishing; see comments.
			public void run() {
				try {
					Clip clip1 = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem
							.getAudioInputStream(this.getClass().getResource("/resources/s1.wav"));
					clip1.open(inputStream);
					clip1.start();
				} catch (Exception e) {
					System.err.println(e.getMessage());
				}
			}
		}).start();
	}


	/**
	 *  Calculate all legal moves for one side
	 * @param isRed true - red player; false - blue player
	 * @return list of moves
	 */
	public ArrayList<Move> getAllLegalMoves(boolean isRed) {
		ArrayList<Move> temp = new ArrayList<Move>();
		if (isRed) {
			for (Tile r : redTiles) {
				for (Move lm : LegalMoves(r))
					temp.add(lm);
			}
		} else {
			for (Tile r : blueTiles) {
				for (Move lm : LegalMoves(r))
					temp.add(lm);
			}
		}
		return temp;
	}
	/**
	 * single player mode, AI plays for blue 
	 */
	public  void play(boolean mute) {
		(new Thread(new AI(this,mute))).start();
	}
	
	/**
	 * who have more kings
	 * 
	 */
	public int isbluehavemorekings() {
		int kingblue = 0;
		int kingred = 0;
		for (Tile xx : blueTiles) {
			if (xx.isKing())
				kingblue++;
		}
		for (Tile xx : redTiles) {
			if (xx.isKing())
				kingred++;
		}
		return ((kingblue - kingred));
	}
	
	//getters and setters
	
	public boolean isKillingmode() {
		return killingmode;
	}

	public void setKillingmode(boolean killingmode) {
		this.killingmode = killingmode;
	}

	public void setCurrentMessage(String currentMessage) {
		this.currentMessage = currentMessage;
	}

	public int isbluehavemore() {
		return ((blueTiles.size() - redTiles.size()));
	}

	public boolean isIsfirstplayer() {
		return isfirstplayer;
	}

	public void setIsfirstplayer(boolean isfirstplayer) {
		this.isfirstplayer = isfirstplayer;
	}

	public ArrayList<Tile> getRedTiles() {
		return redTiles;
	}

	public ArrayList<Tile> getBlueTiles() {
		return blueTiles;
	}

	/**
	 * Returns message to be displayed on screen
	 * @return string
	 */
	public String getCurrentTurn() {
		if (isfirstplayer)
			return "Current turn: RED";
		return "Current turn: BLUE";
	}

	public String getCurrentMessage() {
		return currentMessage;
	}

	public boolean isTileSelected() {
		return tileSelected;
	}

	public Tile getSelectedTile() {
		return selectedTile;
	}

	public void setSelectedTile(Tile selectedTile) {
		if (!killingmode)
			this.selectedTile = selectedTile;
	}
	
	public ArrayList<Move> getLegalMoves() {
		return legalMoves;
	}

	public void setLegalMoves(ArrayList<Move> legalMoves) {
		this.legalMoves = legalMoves;
	}
	public boolean isMute() {
		return mute;
	}

	public void setMute(boolean mute) {
		this.mute = mute;
	}

}
