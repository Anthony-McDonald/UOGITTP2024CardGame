package structures.AI.Actions;

import akka.actor.ActorRef;
import structures.AI.AI;
import structures.GameState;
import structures.basic.MoveableUnit;
import structures.basic.Tile;

public class AIMoveToAvatarThreat extends UnitAction{
    private Tile nearestTileToEnemy;
    private MoveableUnit threat;

    public AIMoveToAvatarThreat(MoveableUnit actionTaker, GameState gameState, Tile nearestTileToEnemy, MoveableUnit threat){
        this.actionTaker = actionTaker;
        this.gameState = gameState;
        this.nearestTileToEnemy = nearestTileToEnemy;
        this.actionScore = 15; //adjust this to adjust starting weighting
        this.threat = threat;
        this.assessScore();

    }

    @Override
    public void makeAction(ActorRef out){
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
