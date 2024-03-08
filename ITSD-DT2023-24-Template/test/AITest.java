import akka.actor.ActorPath;
import akka.actor.ActorRef;
import org.junit.Test;
import structures.*;
import structures.AI.AI;
import structures.basic.*;

import java.util.ArrayList;

import static org.junit.Assert.*;
public class AITest {
//    @Test
//    public void test_ai_can_summon_creature() {
//        GameState gameState1 = new GameState();
//        Board board = gameState1.getBoard();
//        Tile tile1 = board.getTile(2, 3);
//        Tile tile2 = board.getTile(7, 3);
//        AI newAI = new AI(false, gameState1);
//        Player human = new Player(true);
//        Avatar humanAvatar = new Avatar(human);
//        tile1.setUnit(humanAvatar);
//        Avatar aiAvatar = new Avatar(newAI);
//        tile2.setUnit(aiAvatar);
//
//        ArrayList<Tile> tiles = newAI.tilesForAIUnitSummons();
//        Tile summonTile = tiles.get(0);
//        Creature creature = new Creature(0, "Test Creature", 2, null, null, false, null);
//        creature.setManacost(2);
//        newAI.getHand().add(creature);
//
//        newAI.setMana(2,null);
//        newAI.summonUnit();
//        assertEquals(creature, summonTile.getUnit());
//    }

    @Test
    public void testAiCanCalculateDistance() {
        GameState gameState1 = new GameState();
        Board board = gameState1.getBoard();
        Tile tile1 = board.getTile(2, 3);
        Tile tile2 = board.getTile(7, 3);
        AI newAI = new AI(false, gameState1);

        double distance = newAI.calculateDistance(tile1, tile2);

        assertEquals(5.0, distance, 0.001);
    }

    @Test
    public void testAiCanReturnBestCreature() {
        GameState gameState1 = new GameState();
        AI newAI = new AI(false, gameState1);
        newAI.setMana(5, null);

        Creature creature1 = new Creature(0, "Creature 1", 2, null, null, false, null);
        creature1.setManacost(2);
        Creature creature2 = new Creature(0, "Creature 2", 3, null, null, false, null);
        creature2.setManacost(3);
        Creature creature3 = new Creature(0, "Creature 3", 4, null, null, false, null);
        creature3.setManacost(4);

        newAI.getHand().add(creature1);
        newAI.getHand().add(creature2);
        newAI.getHand().add(creature3);

        Creature bestCreature = newAI.returnBestCreature();

        assertEquals(creature3, bestCreature);
    }
}
