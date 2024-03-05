package structures.AI.Actions;

import akka.actor.ActorRef;
import structures.GameState;
import structures.basic.MoveableUnit;
import utils.UnitCommands;

public class AIAttackAvatar extends UnitAction{

    private MoveableUnit avatar;
    private GameState gameState;

    public AIAttackAvatar(MoveableUnit attacker, MoveableUnit threat, GameState gameState){
        this.actionTaker = attacker;
        this.avatar = threat;
        this.gameState = gameState;
        this.actionScore = 40; //adjust this to modify weight of action
        this.assessScore();
        this.actionName = "Attack human avatar";


    }

    @Override
    public void makeAction(ActorRef out){
        UnitCommands.actionableTiles(actionTaker,out, gameState);
        try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
        actionTaker.attackUnit(out,avatar.getTile(),gameState);
    }

    public void assessScore(){
        if (!isAttackDangerous(avatar)){
            //attack will damage enemy unit so keep actionscore high(high weighting)
            if (willAttackKillEnemy(avatar)){
                this.actionScore = 100; //attack will kill enemy so make weight very high
            }else{
                actionScore = actionScore+actionTaker.getAttack();

            }
        }else{
            //attack will kill attacker so now assess value
            //assess change to enemy health to adjust impact of health
            this.setActionScore(10+actionTaker.getAttack()); //higher score based on damage done
        }
        if(actionTaker.getLastTurnAttacked()==gameState.getTurnNumber()){
            this.actionScore = 0; //no chance of attack
        }
        if (gameState.getTurnNumber()>15){ //higher weight at late game since you want to end game
            this.actionScore= actionScore*3;
        } else if (gameState.getTurnNumber()>10) {
            this.actionScore = actionScore*2;
        }
        System.out.println("Action score for avatar attack is now " + this.actionScore);
    }
}
