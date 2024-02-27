package structures.basic;

import akka.actor.ActorRef;
import com.fasterxml.jackson.annotation.JsonIgnore;
import commands.BasicCommands;
import structures.GameState;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;
import utils.UnitCommands;

public class Creature extends Card implements MoveableUnit {

	protected int maxHealth;
	protected int currentHealth;
	protected int attack;
	protected int turnSummoned;
	protected int lastTurnMoved;
	protected Unit unit;
	protected boolean userOwned;
	@JsonIgnore
	protected Tile tile;
	protected int lastTurnAttacked;

	protected boolean isStunned;

	//need to change constructor for creature
	public Creature (int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature, String unitConfig) {
		super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);

	}

	@Override
	public void attackUnit( ActorRef out, Tile tile, GameState gameState) {
		UnitCommands.attackUnit(this,out,tile,gameState);
	}


	@Override
	public void moveUnit(ActorRef out, Tile tile, GameState gameState) {
		UnitCommands.moveUnit(this, out, tile, gameState);


	}
	@Override
	public boolean isStunned() {
		return isStunned;
	}

	@Override
	public void setStunned(boolean stunned) {
		isStunned = stunned;
	}


	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {

		this.maxHealth = maxHealth;
	}

	public int getCurrentHealth() {
		return currentHealth;
	}

	public void setCurrentHealth(int currentHealth, ActorRef out, GameState gameState) {
		this.currentHealth = currentHealth;
		BasicCommands.setUnitHealth(out, this.unit, currentHealth);
		
		/*try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();}
		*/
		if (this.currentHealth < 1) {
			BasicCommands.addPlayer1Notification(out, this.getCardname() + " killed", 3);
			BasicCommands.playUnitAnimation(out, unit, UnitAnimationType.death);
			try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
			BasicCommands.deleteUnit(out,this.unit);
			gameState.getBoard().unitDeath(out,gameState);

			this.tile.setUnit(null);
		}
		
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack, ActorRef out) {
		BasicCommands.setUnitAttack(out, this.unit, attack); //renders attack on front end
		this.attack = attack;
	}

	public int getTurnSummoned() {
		return turnSummoned;
	}

	public void setTurnSummoned(int turnSummoned) {
		this.turnSummoned = turnSummoned;
	}

	public int getLastTurnMoved() {
		return lastTurnMoved;
	}

	public void setLastTurnMoved(int lastTurnMoved) {
		this.lastTurnMoved = lastTurnMoved;
	}

	public Unit getUnit() {
		return unit;
	}

	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public boolean isUserOwned() {
		return userOwned;
	}

	public void setUserOwned(boolean userOwned) {
		this.userOwned = userOwned;
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
		this.unit = BasicObjectBuilders.loadUnit(this.unitConfig, gameState.getFrontEndUnitID(), Unit.class);
		UnitCommands.summon(this,out, tile, gameState);


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


}
