package structures.basic;

import akka.actor.ActorRef;
import com.fasterxml.jackson.annotation.JsonIgnore;
import commands.BasicCommands;
import scala.Int;
import structures.GameState;
import structures.basic.MoveableUnit;
import structures.basic.Player;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;
import utils.UnitCommands;

import java.util.List;
import java.util.Random;

public class Avatar implements MoveableUnit{
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
	private boolean provoke;

	private boolean isStunned = false;

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

	public boolean isStunned() {
		return isStunned;
	}

	public void setStunned(boolean stunned) {
		isStunned = stunned;
	}

	@Override
	public void attackUnit( ActorRef out, Tile tile, GameState gameState) {
		UnitCommands.attackUnit(this, out, tile, gameState);
		System.out.println("Horn health is " + player.getHornOfTheForsakenHealth());
		if (gameState.getPlayer1().getHornOfTheForsakenHealth() >0) {
			boolean wraithlingSummoned = false;
			Tile avatarTile = gameState.getPlayer1().getAvatar().getTile();
			int tileX = avatarTile.getTilex();
			int tileY = avatarTile.getTiley();

			if (UnitCommands.canAttack(this,tile, gameState)) {

				int[][] areaAroundUnit = {
						{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}
				};

				while (!wraithlingSummoned) {
					Random random = new Random();

					int randomSelection = random.nextInt(8);

					// Select a random area from above
					int[] randomArea = areaAroundUnit[randomSelection];

					// New coords
					int xToCheck = tileX + randomArea[0];
					int yToCheck = tileY + randomArea[1];

					Tile tileToCheck = gameState.getBoard().getTile(xToCheck, yToCheck);

					if (UnitCommands.canSummon(gameState, true, tileToCheck)) {
						Wraithling wraithling = new Wraithling();
						wraithling.summon(out, tileToCheck, gameState);
						wraithlingSummoned = true;
					}
				}
			}
		}
	}



	@Override
	public void moveUnit(ActorRef out, Tile tile, GameState gameState) {
		UnitCommands.moveUnit(this,out,tile,gameState);
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
	public void setCurrentHealth(int currentHealth, ActorRef out, GameState gameState) {
		this.currentHealth = currentHealth;
		BasicCommands.setUnitHealth(out, this.unit,this.currentHealth); //renders on front end
		this.player.setHealth(this.currentHealth, out); // to set player health when avatar takes dmg
		this.player.setHornOfTheForsakenHealth(this.player.getHornOfTheForsakenHealth() - 1);
		 /* try {
	            Thread.sleep(50);
	        } catch (InterruptedException e) {
	            e.printStackTrace();}*/
		
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
		UnitCommands.actionableTiles(this, out, gameState);
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

	@Override
	public void summon(ActorRef out, Tile tile, GameState gameState) {
		//not needed for Avatar (actually could call with in initialise for consistency)
	}
	@Override
	public boolean canStillAttack(int currentTurn) {
		if (this.getLastTurnAttacked()!= currentTurn){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean canStillMove(int currentTurn) {
		if (this.getLastTurnAttacked()!=currentTurn){
			if (this.getLastTurnMoved()!= currentTurn){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	@Override
	public boolean isProvoke() {
		return this.provoke;
	}

	@Override
	public void setProvoke(boolean provoke) {
		this.provoke = provoke;
		
	}


}
