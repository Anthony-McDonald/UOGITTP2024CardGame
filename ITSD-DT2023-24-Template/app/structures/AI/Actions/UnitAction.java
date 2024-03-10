package structures.AI.Actions;

import akka.actor.ActorRef;
import structures.GameState;
import structures.basic.MoveableUnit;
import structures.basic.Tile;

/**
 * This is an abstract class for all the actions available to the units to extend off.
 */
public abstract class UnitAction {
    protected Tile targetTile;
    protected boolean isActionPossible;
    protected MoveableUnit actionTaker;
    protected GameState gameState;

    protected int actionScore;
    protected String actionName;

    public void makeAction(ActorRef out) {
    	
    }

    /**
     * This method determines if an attack on this unit will kill the attacker.
     * @param enemyUnit
     * @return
     */
    public boolean isAttackDangerous(MoveableUnit enemyUnit){
        //method for assessing if attack on unit will kill attacker
        int attackedHealth = enemyUnit.getCurrentHealth();
        int actionTakerHealth = actionTaker.getCurrentHealth();
        int enemyUnitAttack = enemyUnit.getAttack();
        int actionTakerAttack = actionTaker.getAttack();
        if((attackedHealth-actionTakerAttack)<=0){
            //attack will kill provoker
            return false;
        }
        //counter attack will kill attacker
        return (actionTakerHealth - enemyUnitAttack) <= 0;
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

    /**
     * This method determines if the attack will kill the enemy.
     * @param enemy
     * @return
     */
    public boolean willAttackKillEnemy (MoveableUnit enemy){
        return enemy.getCurrentHealth() <= actionTaker.getAttack();
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }
}

