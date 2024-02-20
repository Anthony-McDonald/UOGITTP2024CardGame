package allCards;

import structures.basic.BigCard;
import structures.basic.MiniCard;
import structures.basic.Spell;

public class Truestrike extends Spell{

    public Truestrike(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature, boolean isUserOwned) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, isUserOwned);
    }
}
