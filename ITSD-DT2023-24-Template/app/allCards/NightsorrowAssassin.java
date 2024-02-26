package allCards;

import akka.actor.ActorRef;
import structures.GameState;
import structures.basic.BigCard;
import structures.basic.Creature;
import structures.basic.MiniCard;
import structures.basic.OpeningGambit;
import structures.basic.Unit;

public class NightsorrowAssassin extends Creature implements OpeningGambit{

    public NightsorrowAssassin(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
        this.userOwned = true;
        this.attack = 4;
        this.currentHealth = 2;
        this.maxHealth = currentHealth;
    }

	@Override
	public void openingGambit(ActorRef out, GameState gameState) {
		// TODO Auto-generated method stub
		
	}
}
