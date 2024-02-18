package structures.basic;

import akka.actor.ActorRef;
import com.fasterxml.jackson.annotation.JsonIgnore;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.MoveableUnit;
import structures.basic.Player;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;

public class Avatar implements MoveableUnit {
    private int maxHealth;
    private int currentHealth;
    private int attack;
    private int turnSummoned;
    private int lastTurnMoved;
	private Unit unit;
    private Player player;
	private boolean userOwned;
	@JsonIgnore
	private Tile tile;
	private int lastTurnAttacked;

	public Avatar(Player player) {
		this.player = player;
		this.maxHealth = player.getHealth();
		this.currentHealth = maxHealth;
		this.attack = 2;
		this.turnSummoned = 0;
		this.lastTurnMoved = 0;
		this.userOwned = player.isUserOwned();
		if (this.userOwned){ //if human
			this.unit = BasicObjectBuilders.loadUnit(StaticConfFiles.humanAvatar, 1000, Unit.class); //need to update
		}else { //if AI
			this.unit = BasicObjectBuilders.loadUnit(StaticConfFiles.aiAvatar, 1001, Unit.class); //need to update
		}
	}



	@Override
	public void attackUnit( ActorRef out, Tile tile, GameState gameState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveUnit(ActorRef out, Tile tile, GameState gameState) {
		//insert logic about if move can occur
		this.setLastTurnMoved(gameState.getTurnNumber());
		gameState.setLastMessage(GameState.noEvent);
		this.tile.setUnit(null);
		tile.setUnit(this); //sets unit on tile in backend
		gameState.getBoard().renderBoard(out);
		BasicCommands.addPlayer1Notification(out, "moveUnitToTile", 3);
		BasicCommands.moveUnitToTile(out, this.unit, tile);
		try {Thread.sleep(4000);} catch (InterruptedException e) {e.printStackTrace();}



	}

	@Override
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
		
	}

	@Override
	public int getCurrentHealth() {
		return this.currentHealth;
	}

	@Override
	public void setCurrentHealth(int currentHealth, ActorRef out) {
		this.currentHealth = currentHealth;
		BasicCommands.setUnitHealth(out, this.unit,this.currentHealth); //renders on front end
		this.player.setHealth(this.currentHealth, out); // to set player health when avatar takes dmg
		
	}

	@Override
	public int getAttack() {

		return this.attack;
	}

	@Override
	public void setAttack(int attack, ActorRef out) {
		this.attack = attack;
		BasicCommands.setUnitAttack(out,this.unit,this.attack); //renders on front end
		
	}

	@Override
	public int getTurnSummoned() {

		return this.turnSummoned;
	}

	@Override
	public void setTurnSummoned(int turnSummoned) {
		this.turnSummoned = turnSummoned;
		
	}

	@Override
	public int getLastTurnMoved() {
		return this.lastTurnMoved;
	}

	@Override
	public void setLastTurnMoved(int lastTurnMoved) {
		this.lastTurnMoved = lastTurnMoved;
		
	}

	@Override
	public Unit getUnit() {
		return this.unit;
	}

	@Override
	public void setUnit(Unit unit) {
		this.unit = unit;
		
	}

	@Override
	public boolean isUserOwned() {
		return this.userOwned;
	}

	@Override
	public void setUserOwned(boolean userOwned) {
		this.userOwned = userOwned;
		
	}



	@Override
	public int getMaxHealth() {
		return this.maxHealth;
	}

	public void actionableTiles(ActorRef out, GameState gameState){
		//need to add logic about last turnMoved and lastTurn attacked and turnSummoned
		System.out.println("Actionable tiles is running.");
		int xPos = this.tile.getTilex();
		int yPos = this.tile.getTiley();
		Board board = gameState.getBoard();

		if (this.turnSummoned!=gameState.getTurnNumber()){//hasn't been summoned this turn, allow action
			System.out.println("Unit hasn't been summoned this turn");
			if (this.lastTurnAttacked != gameState.getTurnNumber()){//hasn't attacked this turn can still move and attack
				System.out.println("Unit hasn't attacked this turn so it can still move and attack");
				if (this.lastTurnMoved!= gameState.getTurnNumber()){//hasn't moved this turn
					System.out.println("Highlighting tiles white");
					//can still move and attack
					//highlighting for moving (white)
					gameState.setLastUnitClicked(this);
					gameState.setLastMessage(GameState.friendlyUnitClicked);
					for (int i = xPos - 1; i<=xPos+1;i++){ // i is x
						for (int j = yPos -1 ; j<=yPos+1;j++){ // j is y
							if ( 0<=i && i<=8 && 0<=j && j<=4 ){ //if coord in board range
								Tile highlightTile = board.getTile(i,j);
								if (highlightTile.getUnit()==null){//tile has no unit, safe for highlighting
									BasicCommands.drawTile(out,highlightTile, 1);
//									System.out.println(i + " " + j);
								}
							}
						}
					}
					//the below conditions are for highlighting directions +2 in cardinal directions for movement
					if(xPos-2>=0){
						if (board.getTile(xPos-1,yPos).getUnit() == null) { //if space - 1 is empty
							Tile highlightTile = board.getTile(xPos-2, yPos);
							BasicCommands.drawTile(out, highlightTile,1);

						}
					}
					if(xPos+2<=8){
						if (board.getTile(xPos+1,yPos).getUnit() == null) { //if space + 1 is empty
							Tile highlightTile = board.getTile(xPos+2, yPos);
							BasicCommands.drawTile(out, highlightTile,1);

						}
					}
					if(yPos-2>=0){
						if (board.getTile(xPos,yPos-1).getUnit() == null) { //if space - 1 is empty
							Tile highlightTile = board.getTile(xPos, yPos-2);
							BasicCommands.drawTile(out, highlightTile,1);

						}
					}
					if(yPos+2<=4){
						if (board.getTile(xPos,yPos+1).getUnit() == null) { //if space + 1 is empty
							Tile highlightTile = board.getTile(xPos, yPos+2);
							BasicCommands.drawTile(out, highlightTile,1);

						}
					}
					//code for then highlighting tiles red
					for (int i = xPos - 1; i<=xPos+1;i++){ // i is x
						for (int j = yPos -1 ; j<=yPos+1;j++){ // j is y
							if ( 0<=i && i<=8 && 0<=j && j<=4 ){ //if coord in board range
								Tile highlightTile = board.getTile(i,j);
								if (highlightTile.getUnit()!= null && this.userOwned != highlightTile.getUnit().isUserOwned()){//tile has unit and if enemy unit
									BasicCommands.drawTile(out,highlightTile, 2);

								}
							}
						}
					}

				}else { //has moved, can only attack
					try {Thread.sleep(250);} catch (InterruptedException e) {e.printStackTrace();}
					BasicCommands.addPlayer1Notification(out, "This unit has already moved, it can only attack", 2);
					System.out.println("Has moved, can only attack");
					gameState.setLastUnitClicked(this);
					gameState.setLastMessage(GameState.friendlyUnitClicked);
					for (int i = xPos - 1; i<=xPos+1;i++){ // i is x
						for (int j = yPos -1 ; j<=yPos+1;j++){ // j is y
							if ( 0<=i && i<=8 && 0<=j && j<=4 ){ //if coord in board range
								Tile highlightTile = board.getTile(i,j);
								if (highlightTile.getUnit()!= null && this.userOwned != highlightTile.getUnit().isUserOwned()){//tile has unit and if enemy unit
									BasicCommands.drawTile(out,highlightTile, 2);

								}
							}
						}
					}
				}
			}else{
				//has attacked this turn, can't move or attack again
				//inform user
				try {Thread.sleep(250);} catch (InterruptedException e) {e.printStackTrace();}
				gameState.setLastMessage(GameState.noEvent);
				BasicCommands.addPlayer1Notification(out, "This unit has already attacked, it can't perform another action", 2);



			}
		}else{ //summoned this turn, no action
			//insert code notifying user
			System.out.println("Summoned this turn");
			try {Thread.sleep(250);} catch (InterruptedException e) {e.printStackTrace();}
			gameState.setLastMessage(GameState.noEvent);
			BasicCommands.addPlayer1Notification(out, "This unit was summoned this turn, it can't perform an action.", 2);

		}
	}
	public Tile getTile() {
		return this.tile;
	}
	
	public void setTile(Tile tile) {
		 this.tile = tile;
	}

	@Override
	public int getLastTurnAttacked() {
		return this.lastTurnAttacked;
	}

	@Override
	public void setLastTurnAttacked(int lastTurnAttacked) {
		this.lastTurnAttacked=lastTurnAttacked;
	}


}
