package structures.basic;

import akka.actor.ActorRef;

public class Wraithling implements MoveableUnit{
	
	@Override
	public void attackUnit(MoveableUnit m, ActorRef out) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moveUnit(ActorRef out, Tile tile) {
		// TODO Auto-generated method stub
		
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
		return 1;
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



}
