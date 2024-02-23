package events;


import actors.GameActor;
import com.fasterxml.jackson.databind.JsonNode;

import akka.actor.ActorRef;
import structures.GameState;
import structures.basic.Player;

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
public class CardClicked implements EventProcessor{

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {
		
		int handPosition = message.get("position").asInt();

		Player player1 = gameState.getPlayer1();

		if (gameState.getLastMessage().equals("NoEvent")) {
			player1.highlightCardInHand(handPosition, out);
		}

//		player1.playCard(handPosition);
		System.out.println(gameState.getLastMessage());
		
		
	}

}
