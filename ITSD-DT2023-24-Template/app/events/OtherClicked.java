package events;

import actors.GameActor;
import allCards.WraithlingSwarm;
import com.fasterxml.jackson.databind.JsonNode;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.Player;

/**
 * Indicates that the user has clicked an object on the game canvas, in this case
 * somewhere that is not on a card tile or the end-turn button.
 * 
 * { 
 *   messageType = “otherClicked”
 * }
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class OtherClicked implements EventProcessor{

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {

		// If wraithling swarm is still being clicked, inform player to click elsewhere and don't go to the try block below
		if (!gameState.isWraithlingSwarmSatisfied()) {
			BasicCommands.addPlayer1Notification(out, "No action available. Select a card, unit or tile.", 2);
			return;
		}
		try {Thread.sleep(250);} catch (InterruptedException e) {e.printStackTrace();}
		gameState.setLastMessage(GameState.noEvent);
		BasicCommands.addPlayer1Notification(out, "No action available. Select a card, unit or tile.", 2);
		gameState.getBoard().renderBoard(out); //resets board to base highlighting
		gameState.getPlayer1().unhighlightAllCards(out);
//		Player player = new Player(true);
//		player.printDeck();
//		System.out.println("===handlogic===");
//		player.printHand();
//		player.drawCard();
//		player.printHand();



//		player.printDeck();
//		System.out.println("----");
		Player player2 = new Player(false);
//		player2.printDeck();
	}

}


