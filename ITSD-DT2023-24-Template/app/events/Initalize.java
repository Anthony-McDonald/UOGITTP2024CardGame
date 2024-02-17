package events;

import com.fasterxml.jackson.databind.JsonNode;

import akka.actor.ActorRef;
import commands.BasicCommands;
import demo.CommandDemo;
import demo.Loaders_2024_Check;
import structures.GameState;
import structures.basic.*;

/**
 * Indicates that both the core game loop in the browser is starting, meaning
 * that it is ready to recieve commands from the back-end.
 * 
 * { 
 *   messageType = “initalize”
 * }
 * 
 * @author Dr. Richard McCreadie
 *
 */
public class Initalize implements EventProcessor{

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {
		// hello this is a change
		
		gameState.gameInitalised = true;
		
		gameState.something = true;
		// User 1 makes a change
		//CommandDemo.executeDemo(out); // this executes the command demo, comment out this when implementing your solution
		//Loaders_2024_Check.test(out);
		//board rendering below
		Board board = gameState.getBoard();
		board.renderBoard(out);
		//insert code for creating and rendering hand, maybe contain it within player?
		//code for setting mana and health
		int manaGained = gameState.getTurnNumber()+1;
		Player player1 = gameState.getPlayer1();
		player1.setMana(player1.getMana()+manaGained, out);
		Player player2 = gameState.getPlayer2();
		player2.setMana(player2.getMana()+manaGained, out);








	}

}


