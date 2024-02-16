package structures.basic;

import akka.actor.ActorRef;

public interface MoveableUnit {
	public void attackUnit(MoveableUnit m, ActorRef out);
	public void moveUnit(ActorRef out, Tile tile);

	public int getMaxHealth();
	
	public void setMaxHealth(int maxHealth);

	public int getCurrentHealth();

	public void setCurrentHealth(int currentHealth, ActorRef out);

	public int getAttack();

	public void setAttack(int attack, ActorRef out);

	public int getTurnSummoned();

	public void setTurnSummoned(int turnSummoned);

	public int getLastTurnMoved();

	public void setLastTurnMoved(int lastTurnMoved);
	
	public Unit getUnit();

	public void setUnit(Unit unit);
	
	public boolean isUserOwned();

	public void setUserOwned(boolean userOwned);	


}
