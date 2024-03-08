package allCards;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.*;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;
import utils.UnitCommands;

public class DarkTerminus extends Spell{
    public DarkTerminus(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
    }

    public void spellEffect(Tile tile, ActorRef out, GameState gameState){
        MoveableUnit unit = tile.getUnit();
        int enemyHealth = unit.getCurrentHealth();
        enemyHealth = 0;
        // Play the animation
        EffectAnimation effect = BasicObjectBuilders.loadEffect(StaticConfFiles.f1_soulshatter);
        try {Thread.sleep(BasicCommands.playEffectAnimation(out, effect, tile));} catch (InterruptedException e) {e.printStackTrace();}
        // Kill the unit
        unit.setCurrentHealth(enemyHealth, out, gameState);

        gameState.setLastMessage(GameState.darkTerminusOngoing);

        // spawn a wraithling
        Wraithling wraithling = new Wraithling();
        wraithling.summon(out, tile, gameState);
        
        gameState.getBoard().renderBoard(out); //resets board
        gameState.setLastMessage(GameState.noEvent); //ONLY DO THIS IF SPELL GOES CORRECTLY
//        gameState.setLastMessage(GameState.noEvent); //ONLY DO THIS IF SPELL GOES CORRECTLY
//        try {Thread.sleep(2000);} catch (InterruptedException e) {e.printStackTrace();}
    }

}
