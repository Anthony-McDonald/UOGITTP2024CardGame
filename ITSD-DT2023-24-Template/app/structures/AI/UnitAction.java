package structures.AI;

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

