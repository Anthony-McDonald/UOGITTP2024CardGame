package allCards;

import akka.actor.ActorRef;
import structures.GameState;
import structures.basic.*;
import utils.UnitCommands;

import java.util.ArrayList;
import java.util.Random;

public class BloodmoonPriestess extends Creature implements Deathwatch{


    public BloodmoonPriestess(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
        this.userOwned = true;
        this.attack = 3;
        this.currentHealth = 3;
        this.maxHealth = currentHealth;
    }

    //Summon a Wraithling on a randomly selected unoccupied adjacent tile
    @Override
    public void deathWatch(ActorRef out, GameState gameState) {
        Tile currentTile = this.getTile();
        ArrayList<Tile>adjacentTiles = UnitCommands.adjacentTiles(currentTile, gameState);
        ArrayList<Tile>emptyAdjacentTiles = new ArrayList<>();
        for (Tile adjacentTile: adjacentTiles){
            if (adjacentTile.getUnit()==null){
                emptyAdjacentTiles.add(adjacentTile);
            }
        }
        if (emptyAdjacentTiles.size() == 0){
            return; //no empty adjacent tiles so no effect
        }else{
            Random rand = new Random();
            int size = emptyAdjacentTiles.size();
            Tile summonTile = emptyAdjacentTiles.get(rand.nextInt(size));
            Wraithling wraithling = new Wraithling();
            wraithling.summon(out,summonTile,gameState);
        }


    }
}
