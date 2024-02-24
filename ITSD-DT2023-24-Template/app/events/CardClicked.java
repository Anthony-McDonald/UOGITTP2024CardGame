package events;


import actors.GameActor;
import com.fasterxml.jackson.databind.JsonNode;

import akka.actor.ActorRef;
import structures.GameState;
import structures.basic.Card;
import structures.basic.Creature;
import structures.basic.Player;
import structures.basic.Spell;

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
public class CardClicked implements EventProcessor{

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {
		
		int handPosition = message.get("position").asInt();

		Player player1 = gameState.getPlayer1();
		ArrayList<Card> hand = player1.getHand();

		Card card = hand.get(handPosition -2 );
		System.out.println(card.getCardname() + " clicked");
		if (player1.getMana()>=card.getManacost()) { //player has enough mana
			player1.highlightCardInHand(handPosition,out);
			gameState.setLastCardClicked(handPosition); // position in rendering
			if (card instanceof Creature) {
				gameState.setLastMessage(GameState.creatureCardClicked);
			} else if (card instanceof Spell) {
				gameState.setLastMessage(GameState.spellCardClicked); //I think we will have to do this per spell type
			}
		}else{
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
