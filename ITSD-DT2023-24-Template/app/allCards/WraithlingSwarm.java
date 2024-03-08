package allCards;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.*;

/**
 * This is the class for Wraithling Swarm's implementation
 */

public class WraithlingSwarm extends Spell{

    public WraithlingSwarm(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
    }

    /**
     * The spellEffect method sets wraithlingSwarmSatisfied to false and the wraithlingSwarmCounter to 0 in gamestate,
     * starting the logic that handles the placement of wraithlings.
     * @param out
     * @param gameState
     * @param tileX
     * @param tileY
     */
    public void spellEffect(ActorRef out, GameState gameState, int tileX, int tileY){
        System.out.println("attempting to play wraith swarm");
        BasicCommands.addPlayer1Notification(out, "Choose 3 tiles", 5);
        // Change the variables in the gamestate that are tested for to see if wraithling swarm still needs to be checked for
        gameState.setWraithlingSwarmSatisfied(false);
        gameState.setWraithlingSwarmCounter(0);
    }

}
