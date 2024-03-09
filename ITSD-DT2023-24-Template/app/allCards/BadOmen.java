package allCards;

import akka.actor.ActorRef;

import structures.GameState;
import structures.basic.BigCard;
import structures.basic.Creature;
import structures.basic.Deathwatch;
import structures.basic.MiniCard;
import structures.basic.Unit;
/**
 * This is the class for Bad Omen's implementation
 */
public class BadOmen extends Creature implements Deathwatch{

    public BadOmen(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
        this.userOwned = true;
        this.attack = 0;
        this.currentHealth = 1;
        this.maxHealth = currentHealth;
    }

    /**
     * The deathWatch method handles +1 attack permanently
     * @param out
     * @param gameState
     */ 
    @Override
    public void deathWatch(ActorRef out, GameState gameState) {
		this.setAttack(getAttack()+1, out);
	}
}
