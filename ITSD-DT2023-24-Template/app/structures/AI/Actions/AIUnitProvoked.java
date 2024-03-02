package structures.AI.Actions;

import akka.actor.ActorRef;
import structures.GameState;
import structures.basic.MoveableUnit;

public class AIUnitProvoked extends UnitAction {

    private int actionScore = 1;
    private MoveableUnit provoker;
    public AIUnitProvoked(MoveableUnit actionTaker, GameState gameState, MoveableUnit provoker){
        this.actionTaker = actionTaker;
        this.gameState = gameState;
        this.provoker = provoker;
        if (this.isAttackDangerous(provoker)){
            actionScore = 0;

        }

    }

    @Override
    public void makeAction(ActorRef out){
        if (!this.isAttackDangerous(provoker)){
            actionTaker.attackUnit(out, provoker.getTile(),gameState);
        }else{
            actionTaker.setLastTurnAttacked(gameState.getTurnNumber());
            // unit doesn't attack to stay safe also prevents it from showing up on action checking.
        }
    }


}
