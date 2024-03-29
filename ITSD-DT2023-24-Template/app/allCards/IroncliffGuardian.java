package allCards;

import structures.basic.BigCard;
import structures.basic.Creature;
import structures.basic.MiniCard;
import structures.basic.Provoke;
import structures.basic.Unit;
/**
 * This is the class for Ironcliff Guardian's implementation
 */
public class IroncliffGuardian extends Creature implements Provoke{
    public IroncliffGuardian(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
        this.userOwned = false;
        this.attack = 3;
        this.currentHealth = 10;
        this.maxHealth = currentHealth;
    }
    /**
     * The provoke set enemy units only be able to attack Ironcliff Guardian & also not be able to move
     */
	@Override
	public void provoke() {
		// TODO Auto-generated method stub
		
	}
}
