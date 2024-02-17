package structures.basic;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;

public class Creature extends Card implements MoveableUnit {

	private int maxHealth;
	private int currentHealth;
	private int attack;
	private int turnSummoned;
	private int lastTurnMoved;
	private Unit unit;
	private boolean userOwned;
//need to change constructor for creature
	public Creature (int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature, String unitConfig, int maxHealth, int currentHealth, int attack, int turnSummoned, int lastTurnMoved, Unit unit, boolean userOwned) {
		super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
		this.maxHealth = maxHealth;
		this.currentHealth = currentHealth;
		this.attack = attack;
		this.turnSummoned = turnSummoned;
		this.lastTurnMoved = lastTurnMoved;
		this.unit = unit; //need to change this with basic object builder
		this.userOwned = userOwned;
	}

	@Override
	public void attackUnit( ActorRef out, Tile tile, GameState gameState) {

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
		//logic about whether they can move will be within TileClicked
		BasicCommands.moveUnitToTile(out, this.unit, tile); //front end rendering
		tile.setUnit(this); //sets tiles unit to be this.



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

	public void setCurrentHealth(int currentHealth, ActorRef out) {
		//need to add logic about dying? add in death animation?
		this.currentHealth = currentHealth;
		BasicCommands.setUnitHealth(out, this.unit, currentHealth);
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

	}



}
