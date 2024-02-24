package allCards;

import structures.basic.BigCard;
import structures.basic.Creature;
import structures.basic.Deathwatch;
import structures.basic.MiniCard;
import structures.basic.Unit;

public class ShadowWatcher extends Creature implements Deathwatch{

    public ShadowWatcher(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
		super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
		this.userOwned = true;
		this.attack = 3;
		this.currentHealth = 2;
		this.maxHealth = currentHealth;
    }

    
    //gains +1 attack +1 hp permanently (max health)
	@Override
	public void deathWatch() {
		this.setAttack(getAttack()+1, null);
		this.setMaxHealth(getMaxHealth()+1);
	}
}
