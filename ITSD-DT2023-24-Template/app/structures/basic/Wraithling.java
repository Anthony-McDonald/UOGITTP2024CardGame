package structures.basic;

import akka.actor.ActorRef;
import commands.BasicCommands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import structures.GameState;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;
import utils.UnitCommands;

public class Wraithling implements MoveableUnit{
	private int maxHealth;
	private int currentHealth;
	private int attack;
	private int turnSummoned;
	private int lastTurnMoved;
	private Unit unit;
	private boolean userOwned;
	@JsonIgnore
	private Tile tile;
	private int lastTurnAttacked;

	private boolean isStunned;

	public Wraithling() {
		this.maxHealth = 1;
		this.attack = 1;
		this.currentHealth = this.maxHealth;
		this.userOwned = true;
	}
	
	@Override
	public void attackUnit(ActorRef out, Tile tile, GameState gameState) {
		UnitCommands.attackUnit(this, out, tile,gameState);
		
	}

	@Override
	public void moveUnit(ActorRef out, Tile tile, GameState gameState) {
		UnitCommands.moveUnit(this,out,tile,gameState);
	}

	@Override
	public int getMaxHealth() {
		return this.maxHealth;
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
		BasicCommands.setUnitHealth(out, this.unit, currentHealth);
		
		if (this.currentHealth < 1) {
			BasicCommands.addPlayer1Notification(out, "playUnitAnimation [Death]", 3);
			BasicCommands.playUnitAnimation(out, unit, UnitAnimationType.death);
			try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
			gameState.getBoard().unitDeath(out,gameState);
			BasicCommands.deleteUnit(out,this.unit);
			this.tile.setUnit(null);

		}
	}

	@Override
	public int getAttack() {

		return this.attack;
	}

	@Override
	public void setAttack(int attack, ActorRef out) {
		this.attack = attack;
		BasicCommands.setUnitAttack(out, this.unit, attack); //renders attack on front end


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

	public void actionableTiles(ActorRef out, GameState gameState){
		UnitCommands.actionableTiles(this, out, gameState);
	}

	@Override
	public Tile getTile() {
		return this.tile;
	}

	@Override
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
		this.unit = BasicObjectBuilders.loadUnit(StaticConfFiles.wraithling, gameState.getFrontEndUnitID(), Unit.class);
		UnitCommands.summon(this,out, tile, gameState);
	}

	@Override
	public boolean isStunned() {
		return isStunned;
	}

	@Override
	public void setStunned(boolean stunned) {
		isStunned = stunned;
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setProvoke(boolean provoke) {
		// TODO Auto-generated method stub
		
	}


}
