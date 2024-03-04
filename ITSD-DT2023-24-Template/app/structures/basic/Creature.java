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
	protected boolean provoke;
	protected boolean isStunned;

	//need to change constructor for creature
	public Creature (int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature, String unitConfig) {
		super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);

	}

	@Override
	public void attackUnit( ActorRef out, Tile tile, GameState gameState) {
//		if (this.isStunned()) {
//			this.setLastTurnAttacked(gameState.getTurnNumber());
//			this.setLastTurnMoved(gameState.getTurnNumber());
//			BasicCommands.addPlayer1Notification(out, "This avatar is stunned, it cannot move or attack this turn", 3);
//			return;
//		}
//		int tilex = tile.getTilex();
//		int tiley = tile.getTiley();
//		//                   0          1        2       3       4      5          6        7
//		//					TL			TM		TR		ML		MR		BL		  BM		BR
//		int[][] offsets = {{-1, -1}, {0, -1}, {1, -1}, {-1, 0},{0, 0},{-1, 1},  {0, 1},  {1, 1}
//		};
//
//
//
//		if (isToTheLeftRightTopOrBottom(this.getTile(), tile) == 0) {
//			//ie if the attacker is to the left of the victim
//			int[][] offsetsToUse = {offsets[0], offsets[1], offsets[5], offsets[6]};
//			provokeInclusiveAttack(offsetsToUse, out, tile, gameState, 0, -1);
//			System.out.println("PROVOKE ATTACK LEFT");
//
//		} else if (isToTheLeftRightTopOrBottom(this.getTile(), tile) == 1) {
//			//ie if the attacker is to the top of the victim
//			int[][] offsetsToUse = {offsets[0], offsets[2], offsets[3], offsets[4]};
//			provokeInclusiveAttack(offsetsToUse, out, tile, gameState, 1, -1);
//			System.out.println("PROVOKE ATTACK TOP");
//
//		}else if (isToTheLeftRightTopOrBottom(this.getTile(), tile) == 2) {
//			//ie if the attacker is to the right of the victim
//			int[][] offsetsToUse = {offsets[1], offsets[2], offsets[6], offsets[7]};
//			provokeInclusiveAttack(offsetsToUse, out, tile, gameState, 0, 1);
//			System.out.println("PROVOKE ATTACK RIGHT");
//
//		}else if (isToTheLeftRightTopOrBottom(this.getTile(), tile) == 3) {
//			//ie if the attacker is to the bottom of the victim
//			int[][] offsetsToUse = {offsets[3], offsets[4], offsets[5], offsets[7]};
//			provokeInclusiveAttack(offsetsToUse, out, tile, gameState, 1, 1);
//			System.out.println("PROVOKE ATTACK BOTTOM");
//
//		}
		UnitCommands.attackUnit(this,out,tile,gameState);
	}

	private int isToTheLeftRightTopOrBottom(Tile attackerTile, Tile victimTile) {
		// returns 0 for left, 1 for top, 2 for right, 3 for bottom
		if (attackerTile.getTilex() < victimTile.getTilex()) {
			return 0;
		} else if (attackerTile.getTilex() > victimTile.getTilex()) {
			return 2;
		} else if (attackerTile.getTilex() == victimTile.getTilex()) {
			if (attackerTile.getTiley() < victimTile.getTiley()) {
				return 1;
			} else if (attackerTile.getTiley() > victimTile.getTiley()) {
				return 3;
			}
		}


		return 0;
	}

	private void provokeInclusiveAttack(int[][] tileCoordinatesToCheck, ActorRef out, Tile tile, GameState gameState, int horizontalOrVertical, int positionModifier) {
		System.out.println("checking coordinates around: " + tile.getTilex() + tile.getTiley());
		for (int[] coordinatePair: tileCoordinatesToCheck) {
			for (int i = 0; i < coordinatePair.length; i++) {
				int tilex = coordinatePair[0] + tile.getTilex();
				int tiley = coordinatePair[1] + tile.getTiley();
				Tile currentTile = gameState.getBoard().getTile(tilex, tiley);
				if (currentTile != null) {
					if (currentTile.getUnit() != null) {
						if (currentTile.getUnit() instanceof Provoke) {
//							UnitCommands.attackUnit(this,out,currentTile,gameState);
							if (horizontalOrVertical == 0) {
								UnitCommands.moveUnit(this, out,gameState.getBoard().getTile(currentTile.getTilex() + positionModifier, currentTile.getTiley()), gameState);
							} else if (horizontalOrVertical == 1) {
								UnitCommands.moveUnit(this, out,gameState.getBoard().getTile(currentTile.getTilex(), currentTile.getTiley() + positionModifier), gameState);
							}
							BasicCommands.addPlayer1Notification(out, "Your unit was provoked, attack interrupted", 3);
							return;
						} else {
							System.out.println("No unit found with provoke at " + tilex + tiley);
						}
					} else {
						System.out.println("No unit found at " + tilex + tiley);
					}
				} else {
					System.out.println("Tile is null");
				}


			}
		}
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
		EffectAnimation effect = BasicObjectBuilders.loadEffect(StaticConfFiles.f1_summon);
		try {Thread.sleep(BasicCommands.playEffectAnimation(out, effect, tile));} catch (InterruptedException e) {e.printStackTrace();}
	}

	@Override

	public boolean isProvoke() {
		return this.provoke;
	}

	@Override
	public void setProvoke(boolean provoke) {
		this.provoke = provoke;
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
