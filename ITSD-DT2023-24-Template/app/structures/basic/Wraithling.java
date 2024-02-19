package structures.basic;

import akka.actor.ActorRef;
import commands.BasicCommands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import structures.GameState;
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

	public Wraithling() {
		this.maxHealth = 1;
		this.attack = 1;
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
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public void setMaxHealth(int maxHealth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCurrentHealth() {
		return this.currentHealth;
	}

	@Override
	public void setCurrentHealth(int currentHealth, ActorRef out) {
		this.currentHealth = currentHealth;
		BasicCommands.setUnitHealth(out, this.unit, currentHealth);
		
		if (this.currentHealth < 1) {
			BasicCommands.addPlayer1Notification(out, "playUnitAnimation [Death]", 3);
			BasicCommands.playUnitAnimation(out, unit, UnitAnimationType.death);
			try {Thread.sleep(3000);} catch (InterruptedException e) {e.printStackTrace();}
			this.tile.setUnit(null);

		}
	}

	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return this.attack;
	}

	@Override
	public void setAttack(int attack, ActorRef out) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getTurnSummoned() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setTurnSummoned(int turnSummoned) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getLastTurnMoved() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setLastTurnMoved(int lastTurnMoved) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Unit getUnit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUnit(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isUserOwned() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setUserOwned(boolean userOwned) {
		// TODO Auto-generated method stub
		
	}

	public void actionableTiles(ActorRef out, GameState gameState){

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



}
