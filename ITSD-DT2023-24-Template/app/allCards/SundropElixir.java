package allCards;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.*;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;

/**
 * This is the class for Sundrop Elixir's implementation
 */

public class SundropElixir extends Spell{

    public SundropElixir(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
    }

    /**
     * The spellEffect method handles healing the spell's target unit by 4
     * @param tileClicked
     * @param out
     * @param gameState
     */
    public void spellEffect(Tile tileClicked, ActorRef out, GameState gameState){
        System.out.println("TRYING TO PLAY SUNDROP");
        MoveableUnit unit = tileClicked.getUnit();
        int amountToHeal = 4;
        // Play the animation
        EffectAnimation effect = BasicObjectBuilders.loadEffect(StaticConfFiles.f1_buff);
        try {Thread.sleep(BasicCommands.playEffectAnimation(out, effect, tileClicked));} catch (InterruptedException e) {e.printStackTrace();}
        // Heal the unit targeted
        while (amountToHeal > 0) {
            if (unit.getCurrentHealth() < unit.getMaxHealth()) {
                unit.setCurrentHealth(unit.getCurrentHealth() + 1, out, gameState);
            }
            amountToHeal--;
        }
    }
}
