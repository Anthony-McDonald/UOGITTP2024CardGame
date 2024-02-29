package allCards;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.*;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;

public class SundropElixir extends Spell{

    public SundropElixir(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
    }

    public void spellEffect(Tile tileClicked, ActorRef out, GameState gameState){
        System.out.println("TRYING TO PLAY SUNDROP");
        MoveableUnit unit = tileClicked.getUnit();
        int amountToHeal = 4;
        EffectAnimation effect = BasicObjectBuilders.loadEffect(StaticConfFiles.f1_buff);
        try {Thread.sleep(BasicCommands.playEffectAnimation(out, effect, tileClicked));} catch (InterruptedException e) {e.printStackTrace();}
        while (amountToHeal > 0) {
            if (unit.getCurrentHealth() < unit.getMaxHealth()) {
                unit.setCurrentHealth(unit.getCurrentHealth() + 1, out, gameState);
            }
            amountToHeal--;
        }
    }
}
