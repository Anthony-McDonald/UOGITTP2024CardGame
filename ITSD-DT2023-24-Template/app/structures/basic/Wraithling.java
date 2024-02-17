package structures.basic;

import akka.actor.ActorRef;
import commands.BasicCommands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import structures.GameState;

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
	
	public Wraithling() {
		this.maxHealth = 1;
		this.attack = 1;
		this.userOwned = true;
	}
	
	@Override
	public void attackUnit(ActorRef out, Tile tile, GameState gameState) {
		MoveableUnit m = tile.getUnit();
		//insert logic about if attack is possible.
		int enemyHealth = m.getCurrentHealth();
		BasicCommands.playUnitAnimation(out, this.unit, UnitAnimationType.attack); //attack animation
		enemyHealth = enemyHealth - this.attack;
		m.setCurrentHealth(enemyHealth, out);
		if (enemyHealth>0){ //if enemy is alive, counterattack
			BasicCommands.playUnitAnimation(out, m.getUnit(), UnitAnimationType.attack);//unit attack animation
			this.setCurrentHealth((this.currentHealth-m.getAttack()),out);
		}
		
	}

	@Override
	public void moveUnit(ActorRef out, Tile tile, GameState gameState) {
		BasicCommands.moveUnitToTile(out, this.unit, tile); //front end rendering
		tile.setUnit(this); //sets tiles unit to be this.
		
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



}
