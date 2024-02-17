package structures.basic;

import akka.actor.ActorRef;
import structures.GameState;

public interface MoveableUnit {
	public void attackUnit(ActorRef out, Tile tile, GameState gameState);
	public void moveUnit(ActorRef out, Tile tile, GameState gameState);

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

	public void actionableTiles(ActorRef out, GameState gameState);

	public Tile getTile();
	
	public void setTile(Tile tile);
}
