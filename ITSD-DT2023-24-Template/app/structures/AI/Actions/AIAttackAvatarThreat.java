package structures.AI.Actions;

import akka.actor.ActorRef;
import structures.GameState;
import structures.basic.MoveableUnit;

public class AIAttackAvatarThreat extends UnitAction{

    private MoveableUnit threat;
    private GameState gameState;

    public AIAttackAvatarThreat(MoveableUnit attacker, MoveableUnit threat, GameState gameState){
        this.actionTaker = attacker;
        this.threat = threat;
        this.gameState = gameState;
        this.actionScore = 15; //adjust this to modify weight of action
        this.assessScore();


    }

    @Override
    public void makeAction(ActorRef out){
        actionTaker.attackUnit(out,threat.getTile(),gameState);
    }

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
