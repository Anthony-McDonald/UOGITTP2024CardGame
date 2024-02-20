package events;

import com.fasterxml.jackson.databind.JsonNode;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.Player;

/**
 * Indicates that the user has clicked an object on the game canvas, in this case
 * the end-turn button.
 * 
 * { 
 *   messageType = “endTurnClicked”
 * }
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class EndTurnClicked implements EventProcessor{

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {
		//remove player 1's unspent mana
		//insert AI method where AI makes moves
		//remove player 2's unspent mana
		//insert draw card for each player
		gameState.setTurnNumber(gameState.getTurnNumber()+1); //increase turnNumber
		//increase both players mana by (turn number+1)
		String nextTurnMessage = "It's now turn " + gameState.getTurnNumber() + "! Make your moves.";
		BasicCommands.addPlayer1Notification(out, nextTurnMessage, 2 );
		gameState.setLastMessage(GameState.noEvent);






	}

}
