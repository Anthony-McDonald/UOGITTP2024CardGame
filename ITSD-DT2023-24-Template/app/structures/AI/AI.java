package structures.AI;
import akka.actor.ActorRef;
import structures.GameState;
import structures.basic.*;
import utils.UnitCommands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class AI extends Player {
	private GameState gameState;
	private ActorRef actorRef;
	private ArrayList<MoveableUnit> allUnits;

	public AI(boolean userOwned, GameState gameState) {
		super(userOwned);
		this.gameState = gameState;

	}

	public static void main(String[] args) {
		GameState gameState1 = new GameState();
		Board board = gameState1.getBoard();
		Tile tile1 = board.getTile(2, 3);
		Tile tile2 = board.getTile(7, 3);
		Wraithling wraithling1 = new Wraithling();
		wraithling1.setUserOwned(false);
		tile1.setUnit(wraithling1);
		Wraithling wraithling2 = new Wraithling();
		wraithling2.setUserOwned(false);
		tile2.setUnit(wraithling2);
		AI newAI = new AI(false, gameState1);
		ArrayList<Tile> tiles = newAI.tilesForAIUnitSummons();
		for (Tile tile: tiles){
			System.out.println(tile.getTilex() + "," + tile.getTiley());
		}


	}

	public void setActorRef(ActorRef out){
		this.actorRef = out;
	}

	public void makeActions(){
		while(this.hasActions()){
			summonUnit();
			System.out.println("attempting to summon");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean hasActions(){ //expand class as functionality increases
		for (Card card:this.hand) {
			if (card instanceof Creature && card.getManacost() <= this.mana) { //has enough mana for summoning
				System.out.println("AI has actions remaining");
				return true;

			}
		}
		System.out.println("AI has no actions remaining");
		return false;

	}

	public static double calculateDistance(Tile startingTile, Tile targetTile){ //method for calculating distance between two tiles. multipurpose.
		double xDistance = startingTile.getTilex() - targetTile.getTilex();
		double yDistance = startingTile.getTiley() - targetTile.getTiley();
		double distance = Math.sqrt((xDistance * xDistance) + (yDistance*yDistance)); //pythagorean theorem for calculating distance to tile;
		return distance;

	}

	public void summonUnit(){
		/*
		if unit has health>attack, much higher chance of choosing tiles closer to AI avatar,
		if unit has attack<health, much higher chance of choosing tiles closer to player avatar.
		if unit has attack == health , any tile
		 */
		Creature creature = this.returnBestCreature();
		if (this.returnBestCreature().getCardname().equals("no creature")){
			return;
		}

		ArrayList<Tile> possibleTiles = this.tilesForAIUnitSummons();
//		int numberOfTiles = possibleTiles.size();
//		ArrayList<Tile>weightedTiles = new ArrayList<>(); // list for putting weighted array list
//		if (creature.getMaxHealth()>creature.getAttack()){
//			for (int i = 0; i<possibleTiles.size();i++){
//				for (int j = numberOfTiles; j<=1; j--){
//					weightedTiles.add(possibleTiles.get(i));
//				} numberOfTiles--;
//			}
//		} else if (creature.getAttack()> creature.getMaxHealth()) {
//			for (int i = possibleTiles.size()-1; i>=0;i--){
//				for (int j = 1; j<=numberOfTiles; j--){
//					weightedTiles.add(possibleTiles.get(i));
//				} numberOfTiles --;
//			}
//		} else{ //health is equal to attack
//			//do nothing since all tiles have an equal chance
//			weightedTiles.addAll(possibleTiles);
//		}
		Random random = new Random();
		System.out.println(creature);
		System.out.println(actorRef);
		System.out.println(gameState);
		if (this.getMana() >= creature.getManacost()) {
			System.out.println("Playing " + creature.getCardname());
			this.setMana(this.getMana() - creature.getManacost(), actorRef);
			this.hand.remove(creature);

			UnitCommands.summon(creature, actorRef, possibleTiles.get(random.nextInt(possibleTiles.size())), gameState);
		}


	}

	public ArrayList<Tile> tilesForAIUnitSummons(){
		ArrayList<TileSummonWrapper> tileSummonWrappers = new ArrayList<>();
		ArrayList<Tile> tilesForAIUnits = new ArrayList<>();
		ArrayList<MoveableUnit>AIUnits = gameState.getBoard().friendlyUnits(false); //returns all AI units
		for (MoveableUnit unit : AIUnits){
			int tileX = unit.getTile().getTilex();
			int tileY = unit.getTile().getTiley();
			for (int i = tileX - 1; i<=tileX+1;i++){ //loop to search adjacent tiles to units
				for (int j = tileY - 1; j<=tileY+1; j++){
					if ( 0<=i && i<=8 && 0<=j && j<=4 ) { //if coord in board range
						Tile possibleTile = gameState.getBoard().getTile(i,j);
						if (possibleTile.getUnit()==null){ //no unit on tile
							if (!tilesForAIUnits.contains(possibleTile)) { //if it doesn't contain the tile
								tilesForAIUnits.add(possibleTile);
							}
						}
					}
				}
			}
		}
		Tile avatarTile = null;
		//loop for finding Avatar unit tile
		for (MoveableUnit unit : AIUnits){
			if (!unit.isUserOwned()&& unit instanceof Avatar){
				avatarTile = unit.getTile();
			}
		}
		for (Tile tile : tilesForAIUnits){
			double distanceAIavatar = AI.calculateDistance(avatarTile, tile);
			TileSummonWrapper tileSummonWrapper = new TileSummonWrapper(tile,distanceAIavatar);
			tileSummonWrappers.add(tileSummonWrapper);
		}
		Collections.sort(tileSummonWrappers); //sort tiles based on distance from ai avatar
		ArrayList<Tile> sortedTiles = new ArrayList<>();
		for (TileSummonWrapper tileSummonWrapper : tileSummonWrappers){
			Tile tile = tileSummonWrapper.getTile();
			sortedTiles.add(tile);
		}
		return sortedTiles;
	}

	public Creature returnBestCreature(){
		Creature creature = new Creature(0, "no creature" ,-1, null, null, true, null);
		// possible fix to Jackson failing to handle null
		for (Card card : this.hand){
			if (card instanceof Creature){
				Creature possibleCreature = (Creature) card;
				if (possibleCreature.getManacost()<=this.mana){
					if (creature==null){
						creature = possibleCreature;
					}else{
						if (possibleCreature.getManacost()>creature.getManacost()){
							creature = possibleCreature;
						}
					}
				}
			}
		}
		System.out.println("Best creature is " +creature.getCardname());
		return creature;
	}


}

