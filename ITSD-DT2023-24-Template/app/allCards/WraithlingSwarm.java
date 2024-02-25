package allCards;

import akka.actor.ActorRef;
import structures.GameState;
import structures.basic.*;
import utils.BasicObjectBuilders;
import utils.StaticConfFiles;
import utils.UnitCommands;

public class WraithlingSwarm extends Spell{

    public WraithlingSwarm(int id, String cardname, int manacost, MiniCard miniCard, BigCard bigCard, boolean isCreature,  String unitConfig) {
        super(id, cardname, manacost, miniCard, bigCard, isCreature, unitConfig);
    }

    public void spellEffect(ActorRef out, GameState gameState){
        summonWraithling(out, gameState);
        System.out.println("attempting to play wraith swarm");
//    Wraithling wraithling = new Wraithling();
        Card cardToPlay = gameState.getPlayer1().getPlayerDeck().get(0);
        for (Card card : gameState.getPlayer1().getPlayerDeck()) {
            if (card.getIsCreature()) {
                cardToPlay = card;
            }
        }

        for (int i = 0; i < 3; i++) {


            gameState.setLastMessage(GameState.creatureCardClicked);
            UnitCommands.summonableTiles(out,gameState);

            Tile currentTile = gameState.getBoard().getTile(2, i + 1);

            MoveableUnit m = (Creature) cardToPlay;

            m.summon(out,currentTile, gameState);
        }
    }
    private void summonWraithling(ActorRef out, GameState gameState) {

    }

}
