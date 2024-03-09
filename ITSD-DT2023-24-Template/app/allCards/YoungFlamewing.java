package allCards;

import structures.basic.BigCard;
import structures.basic.Creature;
import structures.basic.MiniCard;
import structures.basic.Unit;
/**
 * This is the class for Young Flamewing's implementation
 */
public class YoungFlamewing extends Creature{
    public YoungFlamewing(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
        this.userOwned = false;
        this.attack = 5;
        this.currentHealth = 4;
        this.maxHealth = currentHealth;
    }
    /**
     * This card has fly ability, 
     * which allows Young Flamewing to move to any unoccupied space on the board
     * The canMove() method handles fly logic from UnitCommands 
     */

}
