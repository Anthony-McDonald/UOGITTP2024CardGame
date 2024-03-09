package allCards;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.*;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;
/**
 * This is the class for BeamShock implementation
 */
public class Beamshock extends Spell{

    public Beamshock(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
    }
    /**
     * The spellEffect method stun the unit targeted and set the counter to 1
     * @param tile
     * @param out
     * @param gameState
     */ 
    public void spellEffect(Tile tile, ActorRef out, GameState gameState) {
        // Play the animation
        EffectAnimation effect = BasicObjectBuilders.loadEffect(StaticConfFiles.f1_martyrdom);
        try {Thread.sleep(BasicCommands.playEffectAnimation(out, effect, tile));} catch (InterruptedException e) {e.printStackTrace();}
        // stun the unit targeted and set the counter to 1
        tile.getUnit().setStunned(true);
        gameState.setBeamShockCounter(1);
    }
}
