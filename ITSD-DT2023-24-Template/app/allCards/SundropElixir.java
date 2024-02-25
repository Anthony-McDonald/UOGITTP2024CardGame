package allCards;

import akka.actor.ActorRef;
import structures.GameState;
import structures.basic.BigCard;
import structures.basic.MiniCard;
import structures.basic.MoveableUnit;
import structures.basic.Spell;

public class SundropElixir extends Spell{

    public SundropElixir(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
    }

    public void spellEffect(MoveableUnit unit, ActorRef out){
        int amountToHeal = 4;
        while (amountToHeal > 0) {
            int unitMaxHealth = unit.getMaxHealth();
            if (unit.getCurrentHealth() < unit.getMaxHealth()) {
                unit.setCurrentHealth(unit.getCurrentHealth() + 1, out);
            }
            amountToHeal--;
        }
    }
}
