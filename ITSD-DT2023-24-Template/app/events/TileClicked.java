package events;


import com.fasterxml.jackson.databind.JsonNode;

import akka.actor.ActorRef;
import structures.GameState;
import structures.basic.Tile;

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

		int tilex = message.get("tilex").asInt();
		int tiley = message.get("tiley").asInt();
		
		if (gameState.something == true) {
			// do some logic
			Tile currentTile = gameState.getBoard().getTile(tilex, tiley);

			if (currentTile.getUnit() != null){
				//logic for if current tile has unit

				if(currentTile.getUnit().isUserOwned()){
					//if unit clicked was friendly.

				}else{
					//if unit clicked was enemy
				}

			}else{
				//logic for if current tile has no unit


			}
		}




		//set to appropriate message after all logic
	}

}
