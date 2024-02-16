package structures.basic;

public interface MoveableUnit {
	public void attackUnit(MoveableUnit m);
	public void moveUnit();

	public int getMaxHealth();
	
	public void setMaxHealth(int maxHealth);

	public int getCurrentHealth();

	public void setCurrentHealth(int currentHealth);

	public int getAttack();

	public void setAttack(int attack);

	public int getTurnSummoned();

	public void setTurnSummoned(int turnSummoned);

	public int getLastTurnMoved();

	public void setLastTurnMoved(int lastTurnMoved);
	
	public MoveableUnit getUnit();

	public void setUnit(MoveableUnit unit);
	
	public boolean isUserOwned();

	public void setUserOwned(boolean userOwned);	


}
