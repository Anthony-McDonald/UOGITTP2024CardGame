package allCards;

import akka.actor.ActorRef;
import structures.GameState;
import structures.basic.BigCard;
import structures.basic.Creature;
import structures.basic.MiniCard;
import structures.basic.Tile;
import structures.basic.Unit;

public class SaberspineTiger extends Creature{
    public SaberspineTiger(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
        this.userOwned = false;
        this.attack = 3;
        this.currentHealth = 2;
        this.maxHealth = currentHealth;
    }
    
    //This card has rush which can move and attack on the turn it is summoned

    //easiest way to do this is to override summon method to set turnsummoned to turn number -1.
    
    public void summon(ActorRef out, Tile tile, GameState gameState) {
    	super.summon(out, tile, gameState);
    	this.setTurnSummoned((turnSummoned)-1);
    	this.setLastTurnAttacked(gameState.getTurnNumber()-1);
    }
}
