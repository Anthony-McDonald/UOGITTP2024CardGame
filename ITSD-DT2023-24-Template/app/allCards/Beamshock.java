package allCards;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.*;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;

public class Beamshock extends Spell{

    public Beamshock(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
    }

    public void spellEffect(Tile tile, ActorRef out, GameState gameState) {

        EffectAnimation effect = BasicObjectBuilders.loadEffect(StaticConfFiles.f1_martyrdom);
        try {Thread.sleep(BasicCommands.playEffectAnimation(out, effect, tile));} catch (InterruptedException e) {e.printStackTrace();}
        tile.getUnit().setStunned(true);
        gameState.setBeamShockCounter(1);
    }
}
