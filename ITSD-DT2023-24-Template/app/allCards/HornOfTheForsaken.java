package allCards;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.BigCard;
import structures.basic.EffectAnimation;
import structures.basic.MiniCard;
import structures.basic.Spell;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;


/**
 * This is the class for the Horn of the Forsaken implementation.
 */

public class HornOfTheForsaken extends Spell{

    public HornOfTheForsaken(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
    }

    /**
     * The spellEffect method sets hornOfTheForsaken's health to 4 to facilitate the effect until the health is 0
     * @param out
     * @param gameState
     */
    public void spellEffect(ActorRef out, GameState gameState) {
        // set to 4 to facilitate 3 hits, avoids 1 off error, could edit later for clarity
        // Plays the animation
        EffectAnimation effect2 = BasicObjectBuilders.loadEffect(StaticConfFiles.f1_buff);
        try {Thread.sleep(BasicCommands.playEffectAnimation(out, effect2,gameState.getPlayer1().getAvatar().getTile()));} catch (InterruptedException e) {e.printStackTrace();}
        // Set horn of the forsaken's health to 4, to start the functionality
        gameState.getPlayer1().setHornOfTheForsakenHealth(4);
        gameState.setLastMessage(GameState.noEvent);
    }
}
