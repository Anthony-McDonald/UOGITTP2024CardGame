package structures.AI;

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
        if (this.IsAttackDangerous()){
            actionScore = 0;

        }

    }

    @Override
    public void makeAction(ActorRef out){
        if (!this.IsAttackDangerous()){
            actionTaker.attackUnit(out, provoker.getTile(),gameState);
        }else{
            actionTaker.setLastTurnAttacked(gameState.getTurnNumber());
            // unit doesn't attack to stay safe also prevents it from showing up on action checking.
        }
    }

    public boolean IsAttackDangerous(){
        //method for assessing if attack on provoker will kill attacker
        int provokerHealth = provoker.getCurrentHealth();
        int actionTakerHealth = actionTaker.getCurrentHealth();
        int provokerAttack = provoker.getAttack();
        int actionTakerAttack = actionTaker.getAttack();
        if((provokerHealth-actionTakerAttack)<=0){
            //attack will kill provoker
            return false;
        }
        if ((actionTakerHealth - provokerAttack)<=0){
            return true;

            //counter attack will kill attacker
        }
        return false;
    }
}
