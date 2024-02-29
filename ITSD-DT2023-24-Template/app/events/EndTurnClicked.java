package events;

import com.fasterxml.jackson.databind.JsonNode;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.AI.AI;
import structures.GameState;
import structures.basic.Card;
import structures.basic.Spell;
import structures.basic.Tile;

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
		gameState.getPlayer1().setMana(0, out);
		//insert AI method where AI makes moves
		AI player2 = (AI) gameState.getPlayer2();
		System.out.println(player2.getHand() + "-----------------------------------------------------");;
		player2.makeActions();
		//AI artificial = (AI) gameState.getPlayer2(); 
		//artificial.aiMoved(out,gameState.getPlayer2().);
//		System.out.println("end turn clicked");
		//remove player 2's unspent mana
		gameState.getPlayer2().setMana(0, out);
		//insert draw card for each player
		gameState.getPlayer1().drawCard(out);
		gameState.getPlayer2().drawCard(out);
		gameState.setTurnNumber(gameState.getTurnNumber()+1); //increase turnNumber
		//increase both players mana by (turn number+1)
		gameState.getPlayer1().setMana(gameState.getTurnNumber()+1, out);
		gameState.getPlayer2().setMana(gameState.getTurnNumber()+1, out);

		if (gameState.getBeamShockCounter() > 0) {
			System.out.println("BEAM SHOCK TRIGGERED AND NOTED, INCREMENTING");
			gameState.setBeamShockCounter(gameState.getBeamShockCounter() + 1);
		} if (gameState.getBeamShockCounter() == 3) {
			gameState.setBeamShockCounter(gameState.getBeamShockCounter() + 1);
		}
		if (gameState.getBeamShockCounter() > 3) {
			System.out.println(gameState.getBeamShockCounter());
			System.out.println("TURN WITH STUN OVER, BEAMSHOCK IS NOW FULLY FINISHED, RESETTING");
			gameState.setBeamShockCounter(0);

			Tile[][] tiles = gameState.getBoard().getAllTiles();
			for (int i = 0; i < tiles.length; i++) {
				for (int j = 0; j < tiles[i].length; j++) {
					Tile tile = tiles[i][j];
					if (tile.getUnit() != null) {
						tile.getUnit().setStunned(false);
					}
				}
			}
		}
		// logic to put AI spells into player hand for testing, comment out when you don't want
//		for (Card card : gameState.getPlayer2().getPlayerDeck()) {
//			System.out.println(card.getCardname());
//			if (card instanceof Spell) {
//				System.out.println("SPELL: " + card);
//				gameState.getPlayer1().drawSpecificCard(out, card);
//			}
//		}




		String nextTurnMessage = "It's now turn " + gameState.getTurnNumber() + "! Make your moves.";
		BasicCommands.addPlayer1Notification(out, nextTurnMessage, 2 );
		gameState.setLastMessage(GameState.noEvent);






	}

}
