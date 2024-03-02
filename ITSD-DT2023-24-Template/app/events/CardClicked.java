package events;


import actors.GameActor;
import allCards.WraithlingSwarm;
import com.fasterxml.jackson.databind.JsonNode;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.*;
import utils.UnitCommands;

import java.util.ArrayList;

/**
 * Indicates that the user has clicked an object on the game canvas, in this case a card.
 * The event returns the position in the player's hand the card resides within.
 * 
 * { 
 *   messageType = “cardClicked”
 *   position = <hand index position [1-6]>
 * }
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class CardClicked implements EventProcessor {

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {

		if (!gameState.isWraithlingSwarmSatisfied()) {
			BasicCommands.addPlayer1Notification(out, "Click on a tile!", 2);
			return;
		}

		int handPosition = message.get("position").asInt();
		gameState.getBoard().renderBoard(out);

		Player player1 = gameState.getPlayer1();
		ArrayList<Card> hand = player1.getHand();

		Card card = hand.get(handPosition - 2);
		System.out.println("Took card at index " + (handPosition - 2));
		player1.unhighlightAllCards(out);
		System.out.println(card.getCardname() + " clicked");
		if (player1.getMana() >= card.getManacost()) { //player has enough mana
			player1.highlightCardInHand(handPosition, out);

			gameState.setLastCardClicked(handPosition); // position in rendering
			if (card instanceof Creature) {
				gameState.setLastMessage(GameState.creatureCardClicked);
				UnitCommands.summonableTiles(out, gameState);
			} else if (card instanceof Spell) {
				gameState.setLastMessage(GameState.spellCardClicked); //I think we will have to do this per spell type

				Tile[][] tiles = gameState.getBoard().getAllTiles();
				for (int i = 0; i < tiles.length; i++) {
					for (int j = 0; j < tiles[i].length; j++) {
						Tile tile = tiles[i][j];
						if (card.getCardname().equals("Dark Terminus")) {
							if (tile.getUnit() != null) {
								if (!tile.getUnit().isUserOwned() && !(tile.getUnit() instanceof Avatar)) {
									BasicCommands.drawTile(out, tile, 2);
								}
							}
						}
						if (card.getCardname().equals("Horn of the Forsaken")) {
							if (tile.getUnit() != null) {
								if (tile.getUnit() instanceof Avatar && tile.getUnit().isUserOwned()) {
									BasicCommands.drawTile(out, tile, 1);
								}
							}
						}

					}
				}


			} else {
				//inform player not enough mana for this card
			}


//		if (gameState.getLastMessage().equals(GameState.noEvent)) { //idk if this is needed, we could just reset to noEvent once a card is clicked
//			player1.highlightCardInHand(handPosition);
//
//
//		}

////		player1.playCard(handPosition);
//		System.out.println(gameState.getLastMessage());
//
//
		}


	}
}
