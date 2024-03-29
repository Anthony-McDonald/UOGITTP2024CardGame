package allCards;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.*;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;

/**
 * This is the class for Truestrike's implementation
 */

public class Truestrike extends Spell{

    public Truestrike(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
    }


    /**
     * The spellEffect method handles dealing 2 damage to the spell's targeted unit
     * @param tile
     * @param out
     * @param gameState
     */
    public void spellEffect(Tile tile, ActorRef out, GameState gameState){
        // If there is a unit on the selected tile
        if (tile.getUnit() != null) {
            // Play the animation
            EffectAnimation effect = BasicObjectBuilders.loadEffect(StaticConfFiles.f1_inmolation);
            try {Thread.sleep(BasicCommands.playEffectAnimation(out, effect, tile));} catch (InterruptedException e) {e.printStackTrace();}
            // Damage the unit on the tile
            tile.getUnit().setCurrentHealth(tile.getUnit().getCurrentHealth() - 2,out, gameState);
        } else {
            BasicCommands.addPlayer1Notification(out, "Not a valid target", 2);
            this.spellEffect();
        }

    }
}
