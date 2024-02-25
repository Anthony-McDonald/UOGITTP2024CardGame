package allCards;

import akka.actor.ActorRef;
import structures.GameState;
import structures.basic.*;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;
import utils.UnitCommands;

public class WraithlingSwarm extends Spell{

    public WraithlingSwarm(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
    }

    public void spellEffect(ActorRef out, GameState gameState, int tileX, int tileY){
        System.out.println("attempting to play wraith swarm");

//        for (int i = 0; i < 3; i++) {
//
//            summonWraithling(out, gameState, tileX, tileY);
//
//
//        }
    }
    public static void summonWraithling(ActorRef out, GameState gameState, int tileX, int tileY) {
        gameState.setLastMessage(GameState.creatureCardClicked);
        UnitCommands.summonableTiles(out,gameState);
        Wraithling wraithling = new Wraithling();
        Tile currentTile = gameState.getBoard().getTile(0, 0);
        if (currentTile.getUnit() != null) {

        }
        wraithling.summon(out,currentTile, gameState);
    }

}
