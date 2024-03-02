package structures.AI.Actions;

import akka.actor.ActorRef;
import structures.AI.AI;
import structures.GameState;
import structures.basic.MoveableUnit;
import structures.basic.Tile;
import utils.UnitCommands;

public class AIMoveUnit extends UnitAction{
    private Tile nearestTileToEnemy;
    private MoveableUnit nearestEnemy;

    public AIMoveUnit(MoveableUnit actionTaker, GameState gameState, Tile nearestTileToEnemy, MoveableUnit enemy){
        this.actionTaker = actionTaker;
        this.gameState = gameState;
        this.nearestTileToEnemy = nearestTileToEnemy;
        this.actionScore = 7; //adjust this to adjust starting weighting
        this.nearestEnemy = enemy;
        this.assessScore();

    }

    @Override
    public void makeAction(ActorRef out){
        UnitCommands.actionableTiles(actionTaker,out, gameState);
        try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
        actionTaker.moveUnit(out, nearestTileToEnemy,gameState);
    }

    public void assessScore(){
        if (this.actionTaker.getLastTurnAttacked()==gameState.getTurnNumber()||this.actionTaker.getLastTurnMoved() == gameState.getTurnNumber()){
            this.actionScore = 0; //unit can't move so it can't perform action so weighting of 0 given
            return;
        }
        //distance to enemy from this nearestTile
        double distanceToEnemy = AI.calculateDistance(actionTaker.getTile(), nearestTileToEnemy);
        this.actionScore = this.actionScore - (int) distanceToEnemy;
        if (this.actionScore<1){
            this.actionScore =1; //ensures that its always a chance even if distance is further than 7.
        }




    }
}
