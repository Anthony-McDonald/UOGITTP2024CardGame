package structures.basic;

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
	public void attackUnit(MoveableUnit m) {
		int enemyHealth = m.getCurrentHealth();
		enemyHealth = enemyHealth - this.attack;
		m.setCurrentHealth(enemyHealth);
	}


	@Override
	public void moveUnit() {
		// TODO Auto-generated method stub
		
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

	public void setCurrentHealth(int currentHealth) {
		this.currentHealth = currentHealth;
	}

	public int getAttack() {
		return attack;
	}

	public void setAttack(int attack) {
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

	
	
}
