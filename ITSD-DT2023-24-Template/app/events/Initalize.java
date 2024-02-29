package events;

import com.fasterxml.jackson.databind.JsonNode;

import akka.actor.ActorRef;
import commands.BasicCommands;
import demo.CommandDemo;
import structures.AI.AI;
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
	public static void main(String[] args) {
		Player testPlayer = new Player (true);
		Avatar avatar = new Avatar(testPlayer);

		System.out.println(avatar.getAttack());
		System.out.println(avatar.getCurrentHealth());
	}

	@Override
	public void processEvent(ActorRef out, GameState gameState, JsonNode message) {
		// hello this is a change

		gameState.gameInitalised = true;


		gameState.something = true;
		AI ai = (AI) gameState.getPlayer2();
		ai.setActorRef(out);
		// User 1 makes a change
//		CommandDemo.executeDemo(out); // this executes the command demo, comment out this when implementing your solution
//		Loaders_2024_Check.test(out);
		//board rendering below
		Board board = gameState.getBoard();
		board.renderBoard(out);
//		//insert code for creating and rendering hand, maybe contain it within player?
		//code for setting mana and health
		int manaGained = gameState.getTurnNumber()+1;
		Player player1 = gameState.getPlayer1();
		//draw player1's initial 3 cards
		player1.drawCard(out);
		player1.drawCard(out);
		player1.drawCard(out);
		ai.drawCard(out);
		ai.drawCard(out);
		ai.drawCard(out);
		player1.setMana(player1.getMana()+manaGained, out);
		BasicCommands.setPlayer1Health(out, player1); //only needed here, after this Player.setHealth will handle this

		Player player2 = gameState.getPlayer2();
		player2.setMana(player2.getMana()+manaGained, out);
		BasicCommands.setPlayer2Health(out, player2); //only needed here, after this Player.setHealth will handle this
		//create Avatar objects
		Avatar playerAvatar = new Avatar(player1);
		Avatar aiAvatar = new Avatar(player2);
		player1.setAvatar(playerAvatar);
		player2.setAvatar(aiAvatar);

		Tile playerStartTile = gameState.getBoard().getTile(1,2);
		//we could make this neater later
		playerStartTile.setUnit(playerAvatar); //sets player avatar in back end
		//need the following two commands Unit.setPositionByTile and BasicCommands.drawUnit for initial summon
		playerAvatar.getUnit().setPositionByTile(playerStartTile);//sets player avatar on tile in front end
		BasicCommands.drawUnit(out, playerAvatar.getUnit(), playerStartTile); //sets player avatar on tile in front end
		try {Thread.sleep(250);} catch (InterruptedException e) {e.printStackTrace();}
		BasicCommands.setUnitHealth(out, playerAvatar.getUnit(), playerAvatar.getCurrentHealth());
		try {Thread.sleep(250);} catch (InterruptedException e) {e.printStackTrace();}
		BasicCommands.setUnitAttack(out, playerAvatar.getUnit(), playerAvatar.getAttack());
		try {Thread.sleep(250);} catch (InterruptedException e) {e.printStackTrace();}

		Tile aiStartTile = gameState.getBoard().getTile(7,2);
		player2.getAvatar().setCurrentHealth(15, out, gameState);
		aiStartTile.setUnit(aiAvatar); //sets ai avatar on tile in back end
		aiAvatar.getUnit().setPositionByTile(aiStartTile);//sets ai avatar on tile in front end
		BasicCommands.drawUnit(out,aiAvatar.getUnit(),aiStartTile); //sets ai avatar on tile in front end
		try {Thread.sleep(250);} catch (InterruptedException e) {e.printStackTrace();}
		BasicCommands.setUnitHealth(out, aiAvatar.getUnit(), aiAvatar.getCurrentHealth());
		try {Thread.sleep(250);} catch (InterruptedException e) {e.printStackTrace();}
		BasicCommands.setUnitAttack(out, aiAvatar.getUnit(), aiAvatar.getAttack());
		try {Thread.sleep(250);} catch (InterruptedException e) {e.printStackTrace();}













	}

}


