package structures.basic;

import akka.actor.ActorRef;
import structures.GameState;

public interface MoveableUnit {
	void attackUnit(ActorRef out, Tile tile, GameState gameState);
	void moveUnit(ActorRef out, Tile tile, GameState gameState);

	boolean isStunned();
	void setStunned(boolean stunned);

	int getMaxHealth();
	
	void setMaxHealth(int maxHealth);

	int getCurrentHealth();

	void setCurrentHealth(int currentHealth, ActorRef out, GameState gameState);

	int getAttack();

	void setAttack(int attack, ActorRef out);

	int getTurnSummoned();

	void setTurnSummoned(int turnSummoned);

	int getLastTurnMoved();

	void setLastTurnMoved(int lastTurnMoved);
	
	Unit getUnit();

	void setUnit(Unit unit);
	
	boolean isUserOwned();

	void setUserOwned(boolean userOwned);

	void actionableTiles(ActorRef out, GameState gameState);

	Tile getTile();
	
	void setTile(Tile tile);

	int getLastTurnAttacked();

	void setLastTurnAttacked(int lastTurnAttacked);

	void summon(ActorRef out, Tile tile, GameState gameState);

	
	boolean isProvoke();
		
	void setProvoke(boolean provoke);


	boolean canStillAttack(int currentTurn);

	boolean canStillMove(int currentTurn);

}
