package structures.AI.Actions;

import akka.actor.ActorRef;
import structures.GameState;
import structures.basic.MoveableUnit;
import structures.basic.Tile;

public abstract class UnitAction {
    protected Tile targetTile;
    protected boolean isActionPossible;
    protected MoveableUnit actionTaker;
    protected GameState gameState;

    protected int actionScore;

    public void makeAction(ActorRef out) {

    }

    public boolean isAttackDangerous(MoveableUnit enemyUnit){
        //method for assessing if attack on provoker will kill attacker
        int attackedHealth = enemyUnit.getCurrentHealth();
        int actionTakerHealth = actionTaker.getCurrentHealth();
        int enemyUnitAttack = enemyUnit.getAttack();
        int actionTakerAttack = actionTaker.getAttack();
        if((attackedHealth-actionTakerAttack)<=0){
            //attack will kill provoker
            return false;
        }
        if ((actionTakerHealth - enemyUnitAttack)<=0){
            return true;

            //counter attack will kill attacker
        }
        return false;
    }

    public Tile getTargetTile() {
        return targetTile;
    }

    public void setTargetTile(Tile targetTile) {
        this.targetTile = targetTile;
    }

    public boolean isActionPossible() {
        return isActionPossible;
    }

    public void setActionPossible(boolean actionPossible) {
        isActionPossible = actionPossible;
    }

    public int getActionScore() {
        return actionScore;
    }

    public void setActionScore(int actionScore) {
        this.actionScore = actionScore;
    }

    public MoveableUnit getActionTaker() {
        return actionTaker;
    }

    public void setActionTaker(MoveableUnit actionTaker) {
        this.actionTaker = actionTaker;
    }
}

