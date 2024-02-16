package structures;

import structures.basic.Player;
import structures.basic.Board;
/**
 * This class can be used to hold information about the on-going game.
 * Its created with the GameActor.
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class GameState {
	private Player player;
	private Board board;
	private int turnNumber = 1;
	private String lastMessage = "Initial message";
	public boolean gameInitalised = false;
	
	public boolean something = false;

	public GameState(Player player, Board board) {
		this.player = player;
		this.board = board;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public int getTurnNumber() {
		return turnNumber;
	}

	public void setTurnNumber(int turnNumber) {
		this.turnNumber = turnNumber;
	}

	public String getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}

	public boolean isGameInitalised() {
		return gameInitalised;
	}

	public void setGameInitalised(boolean gameInitalised) {
		this.gameInitalised = gameInitalised;
	}

	public boolean isSomething() {
		return something;
	}

	public void setSomething(boolean something) {
		this.something = something;
	}
}
