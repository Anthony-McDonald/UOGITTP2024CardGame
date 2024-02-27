package structures;

import structures.basic.*;

/**
 * This class can be used to hold information about the on-going game.
 * Its created with the GameActor.
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class GameState {
	private Player player1;
	private AI player2; //convert to AI class when we make it?
	private Board board;
	private int turnNumber = 1;
	private int lastCardClicked; //gives hand index of card
	private Tile lastTileClicked;
	private MoveableUnit lastUnitClicked;

	private String lastMessage = "NoEvent";
	/*
	Possible messages:
	NoEvent : Nothing of consequence
	SpellCardClicked
	CreatureCardClicked (creature CARD clicked)
	FriendlyUnitClicked (friendly avatar, wraithling or creature unit clicked)


	 */

	public boolean gameInitalised = false;
	
	public boolean something = false;

	public static final String noEvent = "NoEvent";
	public static final String spellCardClicked = "SpellCardClicked";
	public static final String friendlyUnitClicked = "FriendlyUnitClicked";
	public static final String creatureCardClicked = "CreatureCardClicked";

	public static final String beamshockClicked = "BeamShockClicked";
	public static final String sundropElixirClicked = "SundropElixirClicked";
	private int frontEndUnitID;
	private int frontEndCardID;

	public GameState() {
		this.player1 = new Player(true);
		this.player2 = new AI(false,this);
		this.board = new Board();
	}

	public Player getPlayer1() {
		return player1;
	}

	public Player getPlayer2() {
		return player2;
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
		System.out.println(this.lastMessage);
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
	public MoveableUnit getLastUnitClicked() {
		return lastUnitClicked;
	}

	public void setLastUnitClicked(MoveableUnit lastUnitClicked) {
		this.lastUnitClicked = lastUnitClicked;
	}

	public int getLastCardClicked() {
		return lastCardClicked;
	}

	public void setLastCardClicked(int lastCardClicked) {
		this.lastCardClicked = lastCardClicked;
	}

	public Tile getLastTileClicked() {
		return lastTileClicked;
	}

	public void setLastTileClicked(Tile lastTileClicked) {
		this.lastTileClicked = lastTileClicked;
	}

	public int getFrontEndUnitID() {
		this.frontEndUnitID++;
		return frontEndUnitID;
	}

	public int getFrontEndCardID() {
		this.frontEndCardID++;
		return frontEndCardID;
	}
}
