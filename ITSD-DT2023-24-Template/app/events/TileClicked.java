package events;


import allCards.WraithlingSwarm;
import com.fasterxml.jackson.databind.JsonNode;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.*;
import utils.UnitCommands;

/**
 * Indicates that the user has clicked an object on the game canvas, in this case a tile.
 * The event returns the x (horizontal) and y (vertical) indices of the tile that was
 * clicked. Tile indices start at 1.
 * 
 * { 
 *   messageType = “tileClicked”
 *   tilex = <x index of the tile>
 *   tiley = <y index of the tile>
 * }
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class TileClicked implements EventProcessor{
	/**
	 * NOTE FOR US GUYS: I think when the last Tile clicked has a unit that was an enemy, I think we set the last
	 * message sent in gamestate to OtherClicked, the reason for this
	 *
	 *
	 */
	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {
		try {
			Thread.sleep(250);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Assign the x and y coordinates of the tile clicked to variables
		int tilex = message.get("tilex").asInt();
		int tiley = message.get("tiley").asInt();


		// needed for wraithling summons
		if (gameState.getWraithlingSwarmCounter() != 3) {
			UnitCommands.summonableTiles(out, gameState);
			Tile currentTile = gameState.getBoard().getTile(tilex, tiley);
			if (UnitCommands.canSummon(gameState, true, currentTile)) {
				Wraithling wraithling = new Wraithling();
				wraithling.summon(out, currentTile, gameState);
				gameState.setWraithlingSwarmCounter(gameState.getWraithlingSwarmCounter() + 1);
				if (gameState.getWraithlingSwarmCounter() != 3) {
					UnitCommands.summonableTiles(out, gameState);
				} else {
					gameState.setWraithlingSwarmSatisfied(true);
				}
			} else {
				BasicCommands.addPlayer1Notification(out, "Can't summon here", 2);
			}
			return;

		}


		//TileClicked main logic
		if (gameState.something) {
			// do some logic
			Tile currentTile = gameState.getBoard().getTile(tilex, tiley);
			Player player1 = gameState.getPlayer1();

			if (currentTile.getUnit() != null){
				System.out.println(currentTile.getUnit().toString());

				//logic for if current tile has unit

				if(currentTile.getUnit().isUserOwned()){
					try {
						Card card = player1.getHand().get(gameState.getLastCardClicked() - 2);

						// If there is an avatar on the current tile, then that is a valid target for horn of the forsaken, cast the card
						if (currentTile.getUnit() instanceof Avatar) {
							if (card.getCardname().equals("Horn of the Forsaken")) {
								if (currentTile.getUnit().getMaxHealth() == 20) {
									gameState.getPlayer1().setLastCardClickedCard(card);
									player1.playCard(gameState.getLastCardClicked(), out);
									((Spell) card).spellEffect(out, gameState);
									BasicCommands.drawTile(out, currentTile, 0);
									// Using this as the reset to noevent below allows for horn to function properly
									gameState.setLastMessage(GameState.wraithlingSwarmCompleted);

								}

							}
						}
					} catch (Exception e) {
					}

					// Reset the gamestate message if wraithling swarm is done
					if (gameState.getLastMessage().equals(GameState.wraithlingSwarmCompleted)) {
						System.out.println("attempting to set to noevent ------------------------------------------------");
						gameState.setLastMessage(GameState.noEvent);
						return;
					}

					// Logic to handle the moving of the avatar
					if (!currentTile.getUnit().isStunned()) {
						//if unit clicked was friendly.
						if (gameState.getLastMessage().equals(GameState.noEvent)){
							//insert logic about highlighting appropriate tiles for move/attack
							System.out.println("CLICKED ON AVATAR");


							MoveableUnit unit = currentTile.getUnit();
							System.out.println("Friendly unit clicked, test that it has detected");
							unit.actionableTiles(out,gameState);

						}else if(gameState.getLastMessage().equals(GameState.spellCardClicked)||gameState.getLastMessage().equals("CreatureCardClicked")){
							//set last message to NoEvent#
							//dehighlight card?
						}else{
							//other logic?? think can delete
						}
					} else {
						BasicCommands.addPlayer1Notification(out,"This unit is stunned this turn", 3);
					}

				}else{
					//if unit clicked was enemy
					if (gameState.getLastMessage().equals(GameState.friendlyUnitClicked)){
						//initiate attack logic

						MoveableUnit attacker = gameState.getLastUnitClicked();
						attacker.attackUnit(out, currentTile,gameState);

					}else if (gameState.getLastMessage().equals(GameState.spellCardClicked)) {
						try {
							Card card = player1.getHand().get(gameState.getLastCardClicked() - 2);
							System.out.println(card.getCardname());

							if (card.getCardname().equals("Dark Terminus")) {
								Tile[][] tiles = gameState.getBoard().getAllTiles();
								for (int i = 0; i < tiles.length; i++) {
									for (int j = 0; j < tiles[i].length; j++) {
										Tile tile = tiles[i][j];
										BasicCommands.drawTile(out, tile, 0);
									}
								}

								if (currentTile.getUnit().getMaxHealth() != 20) {
									gameState.getPlayer1().setLastCardClickedCard(card);
									player1.playCard(gameState.getLastCardClicked(), out);
									((Spell) card).spellEffect(currentTile, out, gameState);

								} else {
									BasicCommands.addPlayer1Notification(out, "Choose a non-avatar unit", 2);
								}

							}


							// Use the card variable as needed
						} catch (IndexOutOfBoundsException e) {
							// Handle the exception gracefully
//						System.out.println("Index is out of bounds. Cannot retrieve the card from the hand.");
//						e.printStackTrace(); // or log the exception
						}

					} else if (gameState.getLastMessage().equals(GameState.noEvent)){
						//no action, inform player

					}else if(gameState.getLastMessage().equals(GameState.creatureCardClicked)){
						//inform player that not action can be taken
						//set last message to NoEvent
						//dehighlight card
					}
				}

			}else{
				//logic for if current tile has no unit
				if (gameState.getLastMessage().equals(GameState.friendlyUnitClicked)){
					MoveableUnit unitToMove = gameState.getLastUnitClicked();
					unitToMove.moveUnit(out,currentTile,gameState);

				}else if (gameState.getLastMessage().equals(GameState.creatureCardClicked)){
					//initiate summon logic
					System.out.println("summon logic");

					try {
						Card card = player1.getHand().get(gameState.getLastCardClicked()-2);
						System.out.println(card.getCardname());
						player1.playCard(gameState.getLastCardClicked(), out);
						MoveableUnit m = (Creature) card;
						m.summon(out,currentTile, gameState);
					} catch (Exception e) {
						throw new RuntimeException(e);
					}

					//
				}else if (gameState.getLastMessage().equals(GameState.noEvent)){
					//inform player no action
				}else if (gameState.getLastMessage().equals(GameState.spellCardClicked)){
					//depends on card, if Dark terminus, won't work
					//if Wraithling swarm or Horn, might work? we need to decide
					try {
						Card card = player1.getHand().get(gameState.getLastCardClicked() - 2);
						System.out.println(card.getCardname());

						if (card.getCardname().equals("Wraithling Swarm")) {
							System.out.println("wraithling swarm clicked");
							UnitCommands.summonableTiles(out, gameState);

							((Spell) card).spellEffect(out, gameState, tilex, tiley);
							player1.playCard(gameState.getLastCardClicked(), out);
						} else if (card.isCreature()) {
							// essentially just a catch to stop creatures being cast to spell
							System.out.println("CAUGHT THE BUGGIEST OF BUGS");
						} else {
//							((Spell) card).spellEffect(out, gameState);
						}
						// Use the card variable as needed
					} catch (IndexOutOfBoundsException e) {
						// Handle the exception gracefully
//						System.out.println("Index is out of bounds. Cannot retrieve the card from the hand.");
//						e.printStackTrace(); // or log the exception
					}



				}


			}
		}




		//set to appropriate message after all logic
	}

}
