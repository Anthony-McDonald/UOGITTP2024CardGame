package structures.basic;

import akka.actor.ActorRef;
import structures.GameState;

public interface Deathwatch {
    void deathWatch(ActorRef out, GameState gameState);
    /*
     Units that have deathwatch are
     Bad Omen
     Shadow Watcher
     Bloodmoon Priestess
     Shadowdancer
     */
}
