package allCards;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.*;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;
import utils.UnitCommands;

import java.io.File;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Collections;

public class WraithlingSwarm extends Spell{

    public WraithlingSwarm(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
    }


//    public void checkSatisfied(ActorRef out, GameState gameState) {
//        if (gameState.getxCoords().size() == 3) {
//            System.out.println("WRAITHLING SWARM SATISFIED");
//            System.out.println(gameState.getxCoords());
//            System.out.println(gameState.getyCoords());
//            summonWraithling(out, gameState);
//        }
//    }


    public void spellEffect(ActorRef out, GameState gameState, int tileX, int tileY){
        System.out.println("attempting to play wraith swarm");
        BasicCommands.addPlayer1Notification(out, "Choose 3 tiles", 5);
        gameState.setWraithlingSwarmSatisfied(false);
        gameState.setWraithlingSwarmCounter(0);
    }
//    private void summonWraithling(ActorRef out, GameState gameState) {
//        gameState.setLastMessage(GameState.creatureCardClicked);
//        UnitCommands.summonableTiles(out,gameState);
//
////        for (int i = 0; i )
//
//        for (int i  = 0; i < 3; i++) {
//            Wraithling wraithling = new Wraithling();
//            Tile currentTile = gameState.getBoard().getTile(gameState.getxCoords().get(i), gameState.getyCoords().get(i));
//            wraithling.summon(out,currentTile, gameState);
//        }
//        gameState.getyCoords().clear();
//        gameState.getxCoords().clear();
//        gameState.setWraithlingSwarmSatisfied(true);
//        gameState.setLastMessage(GameState.wraithlingSwarmCompleted);
//
//    }

}
