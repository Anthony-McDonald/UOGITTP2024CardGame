package allCards;

import structures.basic.BigCard;
import structures.basic.Creature;
import structures.basic.MiniCard;
import structures.basic.Provoke;
import structures.basic.Unit;

public class SilverguardKnight extends Creature implements Provoke{

    public SilverguardKnight(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
        this.userOwned = false;
        this.attack = 1;
        this.currentHealth = 5;
        this.maxHealth = currentHealth;
    }

	@Override
	public void provoke() {
		// TODO Auto-generated method stub
		
	}
}
