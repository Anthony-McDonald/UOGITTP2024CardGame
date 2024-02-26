package allCards;

import structures.basic.BigCard;
import structures.basic.MiniCard;
import structures.basic.MoveableUnit;
import structures.basic.Spell;

public class Beamshock extends Spell{

    public Beamshock(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
    }

    public void spellEffect(MoveableUnit unit) {

        unit.setStunned(true);
    }
}
