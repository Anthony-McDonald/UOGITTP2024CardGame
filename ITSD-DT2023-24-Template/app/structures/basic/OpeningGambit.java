package structures.basic;

import akka.actor.ActorRef;
import structures.GameState;

public interface OpeningGambit {

/*
 Units that have openingGambit are
 Gloom Chaser
 Nightsorrow Assassin
 Silverguard Squire
 */
void openingGambit(ActorRef out, GameState gameState);
}
