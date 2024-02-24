package allCards;

import actors.GameActor;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.BigCard;
import structures.basic.MiniCard;
import structures.basic.Spell;
import structures.basic.Tile;

public class Truestrike extends Spell{

    public Truestrike(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
    }

/*
    public void spellEffect(Tile tile){
        if (tile.getUnit() != null) {
            tile.getUnit().setCurrentHealth(tile.getUnit().getCurrentHealth() - 2, GameActor.out);
        } else {
            BasicCommands.addPlayer1Notification(GameActor.out, "Not a valid target", 2);
            this.spellEffect();
        }

    }*/
}
