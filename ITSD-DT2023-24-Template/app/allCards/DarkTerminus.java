package allCards;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.*;

public class DarkTerminus extends Spell{
    public DarkTerminus(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
    }

    public void spellEffect(Tile tile, ActorRef out, GameState gameState){
        MoveableUnit unit = tile.getUnit();
        int enemyHealth = unit.getCurrentHealth();
        enemyHealth = 0;
        unit.setCurrentHealth(enemyHealth, out, gameState);
        gameState.getBoard().renderBoard(out); //resets board
        Wraithling wraithling = new Wraithling();
        wraithling.summon(out,tile, gameState);
        gameState.setLastMessage(GameState.noEvent); //ONLY DO THIS IF SPELL GOES CORRECTLY
    }

}
