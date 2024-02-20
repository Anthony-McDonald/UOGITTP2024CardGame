package allCards;

import structures.basic.BigCard;
import structures.basic.Creature;
import structures.basic.MiniCard;
import structures.basic.Provoke;
import structures.basic.Unit;

public class IroncliffGuardian extends Creature implements Provoke{
    public IroncliffGuardian(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature, String unitConfig, int maxHealth, int currentHealth, int attack, int turnSummoned, int lastTurnMoved, Unit unit, boolean userOwned) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig, maxHealth, currentHealth, attack, turnSummoned, lastTurnMoved,unit, userOwned);
    }

	@Override
	public void provoke() {
		// TODO Auto-generated method stub
		
	}
}
