package allCards;

import akka.actor.ActorRef;
import structures.GameState;
import structures.basic.BigCard;
import structures.basic.Creature;
import structures.basic.Deathwatch;
import structures.basic.MiniCard;
import structures.basic.Unit;

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
		// need summon command
		
	}
}
