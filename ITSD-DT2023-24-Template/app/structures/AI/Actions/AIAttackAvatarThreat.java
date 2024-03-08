package structures.AI.Actions;

import akka.actor.ActorRef;
import structures.GameState;
import structures.basic.MoveableUnit;
import utils.UnitCommands;

/**
 * This class is used for assessing the action of attacking a threat to the AI avatar
 */
public class AIAttackAvatarThreat extends UnitAction{

    private final MoveableUnit threat;
    private final GameState gameState;

    public AIAttackAvatarThreat(MoveableUnit attacker, MoveableUnit threat, GameState gameState){
        this.actionTaker = attacker;
        this.threat = threat;
        this.gameState = gameState;
        this.actionScore = 15; //adjust this to modify weight of action
        this.assessScore();
        this.actionName = "Attack threat to AI avatar";


    }

    /**
     * If this action is chosen by UnitActionChecker, it will call this method. It will attack the threat to the AI
     * avatar.
     * @param out
     */
    @Override
    public void makeAction(ActorRef out){
        UnitCommands.actionableTiles(actionTaker,out, gameState);
        try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
        actionTaker.attackUnit(out,threat.getTile(),gameState);
    }

    /**
     * Calculates the score for the attack action based on the potential damage to the threat and the AI unit.
     * It considers factors such as whether the attack will damage the threat, whether it will kill the threat,
     * and whether the attack is dangerous to the AI unit.
     */
    public void assessScore(){
        if (!isAttackDangerous(threat)){
            //attack will damage enemy unit so keep actionscore as 7 (high weighting)
            if (willAttackKillEnemy(threat)){
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
