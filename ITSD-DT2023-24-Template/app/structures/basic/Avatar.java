package structures.basic;

import structures.basic.MoveableUnit;
import structures.basic.Player;

public class Avatar implements MoveableUnit {
    private int maxHealth;
    private int currentHealth;
    private int attack;
    private int turnSummoned;
    private int lastTurnMoved;
	private Unit unit;
    private Player player;


	@Override
	public void attackUnit(MoveableUnit m) {
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
	public void setCurrentHealth(int currentHealth) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getAttack() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAttack(int attack) {
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
	public void moveUnit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getMaxHealth() {
		return this.maxHealth;
	}


}
