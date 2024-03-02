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


    }

    @Override
    public void makeAction(ActorRef out){
        actionTaker.attackUnit(out,threat.getTile(),gameState);
    }

    public void assessScore(){
        if (this.isAttackDangerous(threat)){
            actionScore = actionScore - (7 - actionTaker.getAttack()); //reduces weight of action based on damage done
        }
    }
}
