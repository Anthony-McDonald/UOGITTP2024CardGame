package structures.AI;

import akka.actor.ActorRef;
import structures.GameState;
import structures.basic.MoveableUnit;
import structures.basic.Tile;

public class AIAttackUnit extends UnitAction{
    private MoveableUnit enemyUnit;


    public AIAttackUnit(MoveableUnit actionTaker, GameState gameState, MoveableUnit enemyUnit){
        this.actionTaker = actionTaker;
        this.gameState = gameState;
        this.enemyUnit = enemyUnit;
        this.actionScore = 7; //adjust this to adjust starting weighting
        this.assessScore();


    }

    @Override
    public void makeAction(ActorRef out){
        actionTaker.attackUnit(out, enemyUnit.getTile(), gameState);
    }

    public void assessScore(){
        if (!isAttackDangerous(enemyUnit)){
            //attack will kill enemy unit so keep actionscore as 7 (high weighting)
        }else{
            //attack will kill attacker so now assess value
            //assess change to enemy health to adjust impact of health
            this.setActionScore(1+actionTaker.getAttack()); //higher score based on damage done
        }
    }


}
