package structures.basic;

import akka.actor.ActorRef;
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

	public Avatar(Player player) {
		this.player = player;
		this.maxHealth = player.getHealth();
		this.currentHealth = maxHealth;
		this.attack = 2;
		this.turnSummoned = 1;
		this.lastTurnMoved = 0;
		this.userOwned = player.isUserOwned();
		if (this.userOwned){ //if human
			this.unit = BasicObjectBuilders.loadUnit(StaticConfFiles.humanAvatar, 0, Unit.class);
		}else { //if AI
			this.unit = BasicObjectBuilders.loadUnit(StaticConfFiles.aiAvatar, 0, Unit.class);
		}
	}

	@Override
	public void attackUnit( ActorRef out, Tile tile, GameState gameState) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveUnit(ActorRef out, Tile tile, GameState gameState) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMaxHealth(int maxHealth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getCurrentHealth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCurrentHealth(int currentHealth, ActorRef out) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return 0;
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



	@Override
	public int getMaxHealth() {
		return this.maxHealth;
	}


}
