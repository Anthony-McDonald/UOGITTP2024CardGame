package allCards;

import structures.basic.BigCard;
import structures.basic.Creature;
import structures.basic.MiniCard;
import structures.basic.Unit;

public class SaberspineTiger extends Creature{
    public SaberspineTiger(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
        this.userOwned = false;
        this.attack = 3;
        this.currentHealth = 2;
        this.maxHealth = currentHealth;
    }
}
