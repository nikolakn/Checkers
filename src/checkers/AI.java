package checkers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * The AI class implements Monte Carlo algorithm
 */
public class AI implements Runnable {
	
	
	//possible moves for current position
	private ArrayList<Move> posMoves;
	//current position
	private Engine posx;
	//copy of current position  
	private Engine e;
	//play sound after move
	boolean mute;

	/**
	 * AI class constructor.
	 * @param p current position
	 * @param m play sound after move
	 */
	public AI(Engine p, boolean m) {
		posx = p;
		mute = m;

	}
	
	/**
	 * find all possible moves and calculate score
	 * then select the move with the best score
	 */
	@Override
	public void run() {
		//copy current position
		e = new Engine(posx);
		//find all legal moves for blue player on current position
		posMoves = e.getAllLegalMoves(false);
		//calculate score for all legal moves
		if (posMoves.size() > 1) {
			//loot through all possible blue moves 
			for (Move x : posMoves) {
				//get score for move x
				x.winscore = score(x);
			}
		}
		//select the move with the best score 
		//it sorting move array by win score
		Collections.sort(posMoves);
		//get best move form top of sorted list
		Move mvv = posMoves.get(0);
		//select best move
		posx.selectTile(mvv.currentX, mvv.currentY);
		//play that move
		posx.moveTile(mvv.x, mvv.y, true);
		//if soude not muted play sound after move
		if(!mute)
			Engine.playMoveSound();
	}
	
	//calculate score for Move x 
	//called for run function
	private int score(Move x) {
		//initial score
		int sc = 0;
		//for every move play 4000 games ad calculate
		//score for that move based on how many games
		//blue wins 
		for (int i = 0; i < 4000; i++) {
			//copy current position
			Engine te = new Engine(e);
			//select move x
			te.selectTile(x.currentX, x.currentY);
			//play move x
			te.moveTile(x.x, x.y, true);
			//play selected position
			int ff = playposition(te);
			//add score
			sc += ff;
		}
		return sc;
	}
	//play one game - position
	//called for score function
	private int playposition(Engine pos) {
		//depth of calculation
		int maxd = 20;
		//get random time, to be sure that program will not play
		//same moves after restart
		long seed = System.nanoTime();
		//make moves until one side win game
		//or max 20 moves in depth
		while (!pos.isGameover()) {
			maxd--;
			// stop calculation if depth is achieved 
			if (maxd == 0) {
				return pos.isbluehavemore();
			}
			//get legal moves for player
			ArrayList<Move> tlm;
			if (pos.isIsfirstplayer())
				//in case of red player
				tlm = pos.getAllLegalMoves(true);
			else
				//in case of blue player
				tlm = pos.getAllLegalMoves(false);
			//choose random move
			Collections.shuffle(tlm, new Random(seed));
			//choose move in top of the list
			Move rm = tlm.get(0);
			//select best move
			pos.selectTile(rm.currentX, rm.currentY);
			//play move
			pos.moveTile(rm.x, rm.y, true);
		}
		//if blue have more tiles then red give it more score
		return pos.isbluehavemore();
	}
	

}
