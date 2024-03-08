package structures.basic;

import akka.actor.ActorRef;
import allCards.SilverguardKnight;
import com.fasterxml.jackson.annotation.JsonIgnore;
import commands.BasicCommands;
import scala.Int;
import structures.GameState;
import structures.basic.MoveableUnit;
import structures.basic.Player;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;
import utils.UnitCommands;

import java.util.List;
import java.util.Random;

public class Avatar implements MoveableUnit {
	private int maxHealth;
	private int currentHealth;
	private int attack;
	private int turnSummoned;
	private int lastTurnMoved;
	private Unit unit;
	private Player player;
	private boolean userOwned;
	@JsonIgnore
	private Tile tile;
	private int lastTurnAttacked;
	private boolean provoke;

	private boolean isStunned = false;

	public Avatar(Player player) {
		this.player = player;
		this.maxHealth = player.getHealth();
		this.currentHealth = maxHealth;
		this.attack = 2;
		this.turnSummoned = 0;
		this.lastTurnMoved = 0;
		this.userOwned = player.isUserOwned();
		if (this.userOwned) { //if human
			this.unit = BasicObjectBuilders.loadUnit(StaticConfFiles.humanAvatar, 1000, Unit.class); //need to update
		} else { //if AI
			this.unit = BasicObjectBuilders.loadUnit(StaticConfFiles.aiAvatar, 1001, Unit.class); //need to update
		}
	}

	public boolean isStunned() {
		return isStunned;
	}

	public void setStunned(boolean stunned) {
		isStunned = stunned;
	}

	@Override
	public void attackUnit(ActorRef out, Tile tile, GameState gameState) {
		if (this.isStunned()) {
			this.setLastTurnAttacked(gameState.getTurnNumber());
			this.setLastTurnMoved(gameState.getTurnNumber());
			BasicCommands.addPlayer1Notification(out, "This avatar is stunned, it cannot move or attack this turn", 3);
			return;
		}
//		UnitCommands.attackUnit(this, out, tile, gameState);





//		int tilex = tile.getTilex();
//		int tiley = tile.getTiley();
//		//                   0          1        2       3       4      5          6        7
//		//					TL			TM		TR		ML		MR		BL		  BM		BR
//		int[][] offsets = {{-1, -1}, {0, -1}, {1, -1}, {-1, 0},{0, 0},{-1, 1},  {0, 1},  {1, 1}
//		};
//
////				{-1, -1}, {-1, 0}, {-1, 1},
////				{0, -1},           {0, 1},
////				{1, -1},  {1, 0},  {1, 1}
////		};
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
//		} else if (isToTheLeftRightTopOrBottom(this.getTile(), tile) == 2) {
//			//ie if the attacker is to the right of the victim
//			int[][] offsetsToUse = {offsets[1], offsets[2], offsets[6], offsets[7]};
//			provokeInclusiveAttack(offsetsToUse, out, tile, gameState, 0, 1);
//			System.out.println("PROVOKE ATTACK RIGHT");
//
//		} else if (isToTheLeftRightTopOrBottom(this.getTile(), tile) == 3) {
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
		UnitCommands.moveUnit(this,out,tile,gameState);
	}


	@Override
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
		
	}

	@Override
	public int getCurrentHealth() {
		return this.currentHealth;
	}

	@Override
	public void setCurrentHealth(int currentHealth, ActorRef out, GameState gameState) {
		boolean damageTaken = false;
		if (currentHealth < this.player.getHealth()) {
			damageTaken = true;
		}
		this.currentHealth = currentHealth;
		BasicCommands.setUnitHealth(out, this.unit,this.currentHealth); //renders on front end
		this.player.setHealth(this.currentHealth, out); // to set player health when avatar takes dmg

		Tile avatarTile = gameState.getPlayer1().getAvatar().getTile();
		// If player has taken damage and the avatar taking damage is the avatar we are working with
		if (this == gameState.getPlayer1().getAvatar() && damageTaken) {
			this.player.setHornOfTheForsakenHealth(this.player.getHornOfTheForsakenHealth() - 1);
			System.out.println("Horn health is " + player.getHornOfTheForsakenHealth());
			// If horn of the forsaken still has health
			if (gameState.getPlayer1().getHornOfTheForsakenHealth() > 0 ) {
				boolean wraithlingSummoned = false;
				int tileX = avatarTile.getTilex();
				int tileY = avatarTile.getTiley();

				// List of coords that can be used to get all squares around the unit
					int[][] areaAroundUnit = {
							{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}
					};

					// While a wraithling hasn't been summoned yet
					while (!wraithlingSummoned) {
						// Choose a random number
						Random random = new Random();

						int randomSelection = random.nextInt(8);

						// Select a random area from above
						int[] randomArea = areaAroundUnit[randomSelection];

						// New coords
						int xToCheck = tileX + randomArea[0];
						int yToCheck = tileY + randomArea[1];

						// Choose a random tile from the random above
						Tile tileToCheck = gameState.getBoard().getTile(xToCheck, yToCheck);

						System.out.println("ATTEMPTING TO SUMMON A WRAITHLING ---------------");
						// If summonable, summon a wraithling and set the wraithlingSummoned boolean to true
						if (UnitCommands.canSummon(gameState, true, tileToCheck)) {
							Wraithling wraithling = new Wraithling();
							wraithling.summon(out, tileToCheck, gameState);
							try {Thread.sleep(100);} catch (InterruptedException e) {e.printStackTrace();}
							wraithlingSummoned = true;
						}
					}
			}
			// If the avatar getting its health changed is the AI avatar
		} else if (this == gameState.getPlayer2().getAvatar()) {
			// Check all tiles on the board
			Tile[][] tileList = gameState.getBoard().getAllTiles();
			for (Tile[] tileL : tileList) {
				for (Tile tileI : tileL) {
					// If a SilverguardKnight is on the board, increase it's attack by 2
					if (tileI.getUnit() instanceof SilverguardKnight) {
						tileI.getUnit().setAttack(tileI.getUnit().getAttack() + 2,out);
					}
				}
			}
		}

		// If either player's avatar's health reaches 0 or less, end the game
		if (this.getCurrentHealth() <= 0) {
			BasicCommands.addPlayer1Notification(out, "Avatar health is 0, game over folks!", 20);
		}
		
	}

	@Override
	public int getAttack() {

		return this.attack;
	}

	@Override
	public void setAttack(int attack, ActorRef out) {
		this.attack = attack;
		BasicCommands.setUnitAttack(out,this.unit,this.attack); //renders on front end
		
	}

	@Override
	public int getTurnSummoned() {

		return this.turnSummoned;
	}

	@Override
	public void setTurnSummoned(int turnSummoned) {
		this.turnSummoned = turnSummoned;
		
	}

	@Override
	public int getLastTurnMoved() {
		return this.lastTurnMoved;
	}

	@Override
	public void setLastTurnMoved(int lastTurnMoved) {
		this.lastTurnMoved = lastTurnMoved;
		
	}

	@Override
	public Unit getUnit() {
		return this.unit;
	}

	@Override
	public void setUnit(Unit unit) {
		this.unit = unit;
		
	}

	@Override
	public boolean isUserOwned() {
		return this.userOwned;
	}

	@Override
	public void setUserOwned(boolean userOwned) {
		this.userOwned = userOwned;
		
	}



	@Override
	public int getMaxHealth() {
		return this.maxHealth;
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
		//not needed for Avatar (actually could call with in initialise for consistency)
	}
	@Override
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

	@Override
	public boolean isProvoke() {
		return this.provoke;
	}

	@Override
	public void setProvoke(boolean provoke) {
		this.provoke = provoke;
		
	}


}
