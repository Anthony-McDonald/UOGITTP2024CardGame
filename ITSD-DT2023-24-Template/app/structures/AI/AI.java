package structures.AI;
import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.*;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;
import utils.UnitCommands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * This class extends Player and is what is used to represent Player 2 (the enemy AI).
 * It contains all the methods needed for the AI to perform its actions.
 */
public class AI extends Player {
	private final GameState gameState;
	private ActorRef actorRef;
	private ArrayList<MoveableUnit> allUnits;

	/**
	 * This is the constructor for the AI.
	 * @param userOwned
	 * @param gameState
	 */
	public AI(boolean userOwned, GameState gameState) {
		super(userOwned);
		this.gameState = gameState;

	}


	/**
	 * This sets the actorRef for the AI object. It allows the AI to render its moves on the front end.
	 * @param out
	 */
	public void setActorRef(ActorRef out){
		this.actorRef = out;
	}

	/**
	 * This is how the AI makes actions. First it checks if it can play any spells, checking thw appropriate actions.
	 * Then it checks if it can summon any units, summoning units until it no longer can. It prioritises summoning units with the
	 * highest manacost. Then it calls the method unitMakeMoves which loops through all the units, letting each unit make the appropriate move.
	 */
	public void makeActions(){

		playAnySpell();
		while(this.hasSummons()){

			summonUnit();

		}
		unitMakeMoves();

	}

	/**
	 * This iterates through the hand, checks for any Creature cards and if the AI has enough mana to summon them.
	 * If it has enough mana, it will return true.
	 * @return boolean if can summon
	 */
	public boolean hasSummons(){ //expand class as functionality increases
		for (Card card:this.hand) {
			if (card.getManacost() <= this.mana && card instanceof Creature) { //has enough mana for summoning
				System.out.println("AI has summons remaining");
				return true;
			}
		}

		System.out.println("AI has no summons remaining");
		return false;
	}

	/**
	 * This is a method used by the AI in multiple methods. It calculates the distance between two tiles, using
	 * Pythagorus's theorem. This distance is used for weighting actions for units and determining where to summon units.
	 * @param startingTile
	 * @param targetTile
	 * @return
	 */
	public static double calculateDistance(Tile startingTile, Tile targetTile){ //method for calculating distance between two tiles. multipurpose.
		double xDistance = startingTile.getTilex() - targetTile.getTilex();
		double yDistance = startingTile.getTiley() - targetTile.getTiley();
		double distance = Math.sqrt((xDistance * xDistance) + (yDistance*yDistance)); //pythagorean theorem for calculating distance to tile;
		return distance;

	}

	/**
	 * this is the method used by the AI to summon a unit. It gets the highest manacost creature from the hand (that it
	 * can summon), and it then summons the unit in a more offensive position (closer to the enemy Avatar) if the unit
	 * has more attack than health, or in  a more defensive position if the unit has more health than attack.
	 */
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

		List<Tile> possibleTiles = this.tilesForAIUnitSummons();
		List<Tile> backUpPossibleTiles = UnitCommands.getAllSummonableTiles(gameState,false);

		if (creature.getMaxHealth()>creature.getAttack()){
			System.out.println("defensive placement since unit has more attack than health");
			int cutoffPoint = (int) (possibleTiles.size()*0.5);
			System.out.println("Cutoff is " + cutoffPoint);
			if (cutoffPoint == 0){
				cutoffPoint =1; //prevents list from being null
			}
			System.out.println(possibleTiles.size() + " is list size before cutoff");
			if(possibleTiles.size()>0) {
				possibleTiles = possibleTiles.subList(0, cutoffPoint);
				System.out.println(possibleTiles.size() + " is list size after cutoff");
			}else{
				possibleTiles = backUpPossibleTiles;
			}
			//takes bottom % of possibleTiles that are closer to the AI avatar so defensive units are more likely to be summoned in a defensive position
		} else if (creature.getAttack()> creature.getMaxHealth()) {
			System.out.println("offensive placement");
			int cutoffPoint = (int)(possibleTiles.size()*0.5);
			System.out.println("Cutoff is " + cutoffPoint);
			System.out.println(possibleTiles.size() + " is list size before cutoff");
			if(possibleTiles.size()>0) {
				possibleTiles = possibleTiles.subList(0, cutoffPoint);
				System.out.println(possibleTiles.size() + " is list size after cutoff");
			}else{
				possibleTiles = backUpPossibleTiles;
			}
			//takes top % of possibleTiles that are further from the AI avatar so offensive units are more likely to be summoned in an offensive position
		} else{ //health is equal to attack
			//no changes to possible tiles, equal chance of spawning anywhere
		}
		Random random = new Random();
		System.out.println(creature);
		System.out.println(actorRef);
		System.out.println(gameState);
		if (possibleTiles ==null ){
			possibleTiles = backUpPossibleTiles;
		}
		if (this.getMana() >= creature.getManacost()) {
			System.out.println("Playing " + creature.getCardname());
			this.setMana(this.getMana() - creature.getManacost(), actorRef);
			this.hand.remove(creature);

			Tile tileToSummonOn = null;
			EffectAnimation effect = null;
			try {
				tileToSummonOn = possibleTiles.get(random.nextInt(possibleTiles.size()));

				creature.summon(actorRef, tileToSummonOn, gameState);

				effect = BasicObjectBuilders.loadEffect(StaticConfFiles.f1_summon);
				try {Thread.sleep(BasicCommands.playEffectAnimation(this.actorRef, effect, tileToSummonOn));} catch (InterruptedException e) {e.printStackTrace();}
			} catch (Exception e) {
			}
		}


	}


	/**
	 * Chooses a tile on the board and if spellChooser returns a spell, plays it there
	 */
	public void playAnySpell() {
		Tile[][] tiles = this.gameState.getBoard().getAllTiles();
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				Tile tile = tiles[i][j];
				if (tile.getUnit() != null) {
					Spell spellToPlay = spellChooser(tile);
					if (spellToPlay != null) {
						playSpell(spellToPlay, tile);
					} else {
						System.out.println("spellToPlay returned null");
					}
				}
			}
		}
	}

	/**
	 * Removes spellCard from hand and, decrements mana and plays the spell card
	 * @param spellCard
	 * @param tile
	 */
	public void playSpell(Spell spellCard, Tile tile) {
		this.hand.remove(spellCard);
		this.setMana(this.getMana() - spellCard.getManacost(), actorRef);
		spellCard.spellEffect(tile, this.actorRef, this.gameState);
	}


	/**
	 * Logic for choosing which spell to play, seees if a spell is in hand and if it is returns it if conditions are met
	 * @param tile
	 * @return
	 */
	private Spell spellChooser(Tile tile) {
		for (Card card : this.hand) {
			if (card instanceof Spell) {
				if (this.getMana() > card.getManacost()) {
					if (tile.getUnit().isUserOwned()) {
						if (card.getCardname().equals("Beamshock")) {
							int distanceFromAvatar = Math.abs(gameState.getPlayer2().getAvatar().getTile().getTilex() - tile.getTilex());
							if (distanceFromAvatar < 2 && tile.getUnit().getAttack() >= 4) {
								return (Spell) card;
							} else {
								System.out.println("BEAMSHOCK PLAY CONDITIONS NOT MET");
							}
						}
						if (card.getCardname().equals("Truestrike")) {
							if (tile.getUnit().getCurrentHealth() <= 2) {
								return (Spell) card;
							}else {
								System.out.println("TRUESTRIKE PLAY CONDITIONS NOT MET");
							}
						}
					} else {
						if (card.getCardname().equals("Sundrop Elixir")) {
							if (tile.getUnit().getCurrentHealth() <= tile.getUnit().getMaxHealth() - 4) {
								return (Spell) card;
							}else {
								System.out.println("SUNDROP ELIXIR PLAY CONDITIONS NOT MET");
							}
						}
					}
				}

			}
		}

			return null;
	}


	/**
	 * This method is used to determine the tiles on which the AI player can summon units. It considers the distance
	 * between the AI's avatar and the human player's avatar to prioritise summoning units on tiles that are ideally
	 * in between the two avatars. If no such tiles are available, it falls back to a backup list of possible tiles.
	 * It also returns the tiles in a sorted order based on their distance from the AI avatar. This is used
	 * to determine how defensive we want the summoning to be.
	 * @return Tiles for summoning
	 */
	public ArrayList<Tile> tilesForAIUnitSummons(){


		ArrayList<TileSummonWrapper> tileSummonWrappers = new ArrayList<>(); //used for sorting tiles based on distance from AI avatar
		ArrayList<Tile> tilesForAIUnits = new ArrayList<>();
		ArrayList<MoveableUnit>AIUnits = gameState.getBoard().friendlyUnits(false); //returns all AI units
		ArrayList<Tile> backupPossibleTiles = new ArrayList<>(); //backup in case no other possible tiles using this algorithm
		//e.g. if Avatars are next to each other, this doesn't work

		Tile avatarAITile = null;
		//loop for finding AI Avatar unit tile
		for (MoveableUnit unit : AIUnits){
			if (!unit.isUserOwned()&& unit instanceof Avatar){
				avatarAITile = unit.getTile();
			}
		}

		Tile avatarhumanTile = null;
		//loop for finding AI Avatar unit tile
		for (MoveableUnit unit : gameState.getBoard().friendlyUnits(true)){
			if (unit.isUserOwned()&& unit instanceof Avatar){
				avatarhumanTile = unit.getTile();
			}
		}
		double avatarToAvatarDist = AI.calculateDistance(avatarhumanTile, avatarAITile);
		for (MoveableUnit unit : AIUnits){
			int tileX = unit.getTile().getTilex();
			int tileY = unit.getTile().getTiley();
			for (int i = tileX - 1; i<=tileX+1;i++){ //loop to search adjacent tiles to units
				for (int j = tileY - 1; j<=tileY+1; j++){
					if ( 0<=i && i<=8 && 0<=j && j<=4 ) { //if coord in board range
						Tile possibleTile = gameState.getBoard().getTile(i,j);
						if (possibleTile.getUnit()==null){ //no unit on tile
							backupPossibleTiles.add(possibleTile); //adds tiles to backup list
							if (!tilesForAIUnits.contains(possibleTile)) { //if it doesn't contain the tile
								double distanceToHumanAvatar = AI.calculateDistance(possibleTile,avatarhumanTile);
								if (distanceToHumanAvatar<=avatarToAvatarDist) {
									//only summons on tiles that are ideally in between AI avatar and human Avatar
									//prevents units from being summoned "behind" AI avatar
									tilesForAIUnits.add(possibleTile);
								}
							}
						}
					}
				}
			}
		}

		for (Tile tile : tilesForAIUnits){
			double xdistanceAIavatar = avatarAITile.getTilex()- tile.getTilex(); //sorts tiles based on their distance from ai avatar in terms of x axis
			if (xdistanceAIavatar<0){
				xdistanceAIavatar= xdistanceAIavatar*-1;
			}
			TileSummonWrapper tileSummonWrapper = new TileSummonWrapper(tile,xdistanceAIavatar);
			tileSummonWrappers.add(tileSummonWrapper);
		}
		Collections.sort(tileSummonWrappers); //sort tiles based on distance from ai avatar
		ArrayList<Tile> sortedTiles = new ArrayList<>();
		for (TileSummonWrapper tileSummonWrapper : tileSummonWrappers){
			Tile tile = tileSummonWrapper.getTile();
			sortedTiles.add(tile);
		}
		if (sortedTiles != null) {
			return sortedTiles;
		}else{
			return backupPossibleTiles;
		}
	}

	/**
	 * This returns the highest manacost creature that the AI can summon. This ensures the AI plays the best cards first
	 * @return best Creature card
	 */
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

	/**
	 * This method iterates through each AI unit. It creeates a UnitActionChecker for each unit that can make moves.
	 * The action checker determines the weighting of each move available and then chooses an action randomly (weighted
	 * random) for the unit to perform.
	 */
	public void unitMakeMoves(){
		System.out.println("AI is making moves with units");
		ArrayList<MoveableUnit>aiUnits = gameState.getBoard().friendlyUnits(false);
		for (MoveableUnit unit: aiUnits){
			if (unit instanceof Avatar){
				if (gameState.getTurnNumber()<3){
					//this allows the AI avatar to play a safer game and spawn some units in front of it
					break;
				}
			}
			if (unit instanceof Creature){
				System.out.println(((Creature) unit).getCardname()+ " is making action");
			}
			if (unit.getTurnSummoned()!= gameState.getTurnNumber()){
				UnitActionChecker unitActionChecker = new UnitActionChecker(unit, gameState, actorRef);
				unitActionChecker.makeAction();
				System.out.println("Unit made action, now move on to next unit");
				try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
			}
			System.out.println();
			System.out.println();

			for (MoveableUnit humanUnit : gameState.getBoard().friendlyUnits(true)){
				if (humanUnit instanceof Avatar){
					if (humanUnit.getCurrentHealth()<=0){ //stops AI making moves once game is done
						return;
					}
				}
			}
		}
	}


}

