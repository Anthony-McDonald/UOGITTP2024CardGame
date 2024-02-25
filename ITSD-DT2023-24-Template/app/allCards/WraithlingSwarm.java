package allCards;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.*;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;
import utils.UnitCommands;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class WraithlingSwarm extends Spell{
    private static ArrayList<Integer> xCoords = new ArrayList<>();
    private static ArrayList<Integer> yCoords = new ArrayList<>();

    public static boolean isSatisfied = true;

    public WraithlingSwarm(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
    }

    public static ArrayList<Integer> getxCoords() {
        return xCoords;
    }

    public boolean isSatisfied() {
        return isSatisfied;
    }

    public static void checkSatisfied(ActorRef out, GameState gameState) {
        if (xCoords.size() == 3) {
            System.out.println("WRAITHLING SWARM SATISFIED");
            System.out.println(getxCoords());
            System.out.println(getyCoords());
            summonWraithling(out, gameState);
        }
    }

    public static void setSatisfied(boolean satisfied) {
        isSatisfied = satisfied;
    }

    public void setxCoords(ArrayList<Integer> xCoords) {
        xCoords = xCoords;
    }

    public static ArrayList<Integer> getyCoords() {
        return yCoords;
    }

    public void setyCoords(ArrayList<Integer> yCoords) {
        yCoords = yCoords;
    }

    public void spellEffect(ActorRef out, GameState gameState, int tileX, int tileY){
        System.out.println("attempting to play wraith swarm");
        BasicCommands.addPlayer1Notification(out, "Choose 3 tiles", 5);
    }
    public static void summonWraithling(ActorRef out, GameState gameState) {
        gameState.setLastMessage(GameState.creatureCardClicked);
        UnitCommands.summonableTiles(out,gameState);

//        for (int i = 0; i )

        for (int i  = 0; i < 3; i++) {
            Wraithling wraithling = new Wraithling();
            Tile currentTile = gameState.getBoard().getTile(getxCoords().get(i), getyCoords().get(i));
            wraithling.summon(out,currentTile, gameState);
        }
        getyCoords().clear();
        getxCoords().clear();
        setSatisfied(true);

    }

}
