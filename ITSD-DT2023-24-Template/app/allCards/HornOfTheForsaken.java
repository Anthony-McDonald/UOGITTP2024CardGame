package allCards;

import structures.GameState;
import structures.basic.BigCard;
import structures.basic.MiniCard;
import structures.basic.Spell;

public class HornOfTheForsaken extends Spell{

    public HornOfTheForsaken(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
    }

    public void spellEffect(GameState gameState) {
        // set to 4 to facilitate 3 hits, avoids 1 off error, could edit later for clarity
        gameState.getPlayer1().setHornOfTheForsakenHealth(4);
    }
}
