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
		try {Thread.sleep(250);} catch (InterruptedException e) {e.printStackTrace();}
		int tilex = message.get("tilex").asInt();
		int tiley = message.get("tiley").asInt();



		// needed for wraithling summons
		if (!WraithlingSwarm.isSatisfied) {
			UnitCommands.summonableTiles(out,gameState);
			Tile currentTile = gameState.getBoard().getTile(tilex,tiley);
			System.out.println(currentTile.getUnit());
			if (UnitCommands.canSummon(gameState, true, currentTile)) {
				WraithlingSwarm.getxCoords().add(tilex);
				WraithlingSwarm.getyCoords().add(tiley);
				WraithlingSwarm.checkSatisfied(out, gameState);
			} else {
				BasicCommands.addPlayer1Notification(out,"Can't summon here", 2);
			}


		}

		if (gameState.something == true) {
			// do some logic
			Tile currentTile = gameState.getBoard().getTile(tilex, tiley);

			if (currentTile.getUnit() != null){
				System.out.println(currentTile.getUnit().toString());

				//logic for if current tile has unit

				if(currentTile.getUnit().isUserOwned()){
					if (gameState.getLastMessage().equals(GameState.wraithlingSwarmCompleted)) {
						System.out.println("attempting to set to noevent ------------------------------------------------");
						gameState.setLastMessage(GameState.noEvent);
						return;
					}
					if (!currentTile.getUnit().isStunned()) {
						//if unit clicked was friendly.
						if (gameState.getLastMessage().equals(GameState.noEvent)){
							//insert logic about highlighting appropriate tiles for move/attack


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
						Player player1 = gameState.getPlayer1();
						try {
							Card card = player1.getHand().get(gameState.getLastCardClicked() - 2);
							System.out.println(card.getCardname());

							if (card.getCardname().equals("Dark Terminus")) {
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
					Player player1 = gameState.getPlayer1();
					Card card = player1.getHand().get(gameState.getLastCardClicked()-2);
					System.out.println(card.getCardname());
					player1.playCard(gameState.getLastCardClicked(), out);
					MoveableUnit m = (Creature) card;
					m.summon(out,currentTile, gameState);

					//
				}else if (gameState.getLastMessage().equals(GameState.noEvent)){
					//inform player no action
				}else if (gameState.getLastMessage().equals(GameState.spellCardClicked)){
					//depends on card, if Dark terminus, won't work
					//if Wraithling swarm or Horn, might work? we need to decide
					Player player1 = gameState.getPlayer1();
					try {
						Card card = player1.getHand().get(gameState.getLastCardClicked() - 2);
						System.out.println(card.getCardname());
						player1.playCard(gameState.getLastCardClicked(), out);

						if (card.getCardname().equals("Wraithling Swarm")) {
							System.out.println("wraithling swarm clicked");
							((WraithlingSwarm) card).setSatisfied(false);
							UnitCommands.summonableTiles(out, gameState);

							((Spell) card).spellEffect(out, gameState, tilex, tiley);
						} else if (card.getCardname().equals("Horn of the Forsaken")) {
							System.out.println("THE HORN HAS BEEN BLOWN");
							((Spell) card).spellEffect(out, gameState);
						} else if (card.isCreature()) {
							// essentially just a catch to stop creatures being cast to spell
							System.out.println("CAUGHT THE BUGGIEST OF BUGS");
						} else {
							((Spell) card).spellEffect(out, gameState);
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
