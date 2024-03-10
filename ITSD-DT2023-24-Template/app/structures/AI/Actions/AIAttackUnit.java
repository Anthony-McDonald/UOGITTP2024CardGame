package structures.AI.Actions;

import akka.actor.ActorRef;
import allCards.YoungFlamewing;
import structures.GameState;
import structures.basic.MoveableUnit;
import utils.UnitCommands;

/**
 * This class is used for assessing the action of attacking the nearest enemy unit.
 */
public class AIAttackUnit extends UnitAction{
    private final MoveableUnit enemyUnit;


    public AIAttackUnit(MoveableUnit actionTaker, GameState gameState, MoveableUnit enemyUnit){
        this.actionTaker = actionTaker;
        this.gameState = gameState;
        this.enemyUnit = enemyUnit;
        this.actionScore = 7; //adjust this to adjust starting weighting
        this.assessScore();
        this.actionName = "Attack human unit";


    }

    /**
     * This method is what's called if the UnitActionChecker chooses this action. It attacked the nearest enemy unit.
     * @param out
     */
    @Override
    public void makeAction(ActorRef out){
        UnitCommands.actionableTiles(actionTaker,out, gameState);
        try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
        actionTaker.attackUnit(out, enemyUnit.getTile(), gameState);
    }
    /**
     * Calculates the score for the attack action based on the potential damage to the enemy and the AI unit.
     * It considers factors such as whether the attack will damage the threat, whether it will kill the enemy,
     * and whether the attack is dangerous to the AI unit.
     */
    public void assessScore(){
        if (!isAttackDangerous(enemyUnit)){
            //attack will damage enemy unit so keep actionscore as 7 (high weighting)
            if (willAttackKillEnemy(enemyUnit)){
                this.actionScore = 40; //attack will kill enemy so make weight very high
            }else{
                actionScore = actionScore+actionTaker.getAttack();
            }
        }else{
            //attack will kill attacker so now assess value
            //assess change to enemy health to adjust impact of health
            this.setActionScore(1+actionTaker.getAttack()); //higher score based on damage done
        }
        if(actionTaker.getLastTurnAttacked()==gameState.getTurnNumber()){
            this.actionScore = 0; //no chance of attack
        }
        System.out.println("Action score is now " + this.actionScore);
    }


}
