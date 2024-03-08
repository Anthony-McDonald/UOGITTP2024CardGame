package structures.AI.Actions;

import akka.actor.ActorRef;
import structures.AI.AI;
import structures.GameState;
import structures.basic.MoveableUnit;
import structures.basic.Tile;
import utils.UnitCommands;

/**
 * This class is used to assess the weight of moving towards the enemy avatar.
 */
public class AIMoveToAvatar extends UnitAction{
    private final Tile nearestTileToAvatar;
    private final MoveableUnit avatar;

    public AIMoveToAvatar(MoveableUnit actionTaker, GameState gameState, Tile nearestTileToAvatar, MoveableUnit avatar){
        this.actionTaker = actionTaker;
        this.gameState = gameState;
        this.nearestTileToAvatar = nearestTileToAvatar;
        this.actionScore = 20; //adjust this to adjust starting weighting
        this.avatar = avatar;
        this.assessScore();
        this.actionName = "Move towards AI Avatar";

    }

    /**
     * This method is what's called if the UnitActionChecker chooses this action. It moves the unit to the closest tile
     * possible to the enemy avatar.
     * @param out
     */
    @Override
    public void makeAction(ActorRef out){
        UnitCommands.actionableTiles(actionTaker,out, gameState);
        try {Thread.sleep(500);} catch (InterruptedException e) {e.printStackTrace();}
        actionTaker.moveUnit(out, nearestTileToAvatar,gameState);
    }

    /**
     * This method assesses the weight of this action. It increases the weight based on the distance it has to move to
     * reach the unit.
     */
    public void assessScore(){
        if (this.actionTaker.getLastTurnAttacked()==gameState.getTurnNumber()||this.actionTaker.getLastTurnMoved() == gameState.getTurnNumber()){
            this.actionScore = 0; //unit can't move so it can't perform action so weighting of 0 given
            return;
        }
        //distance to enemy from this nearestTile
        double distanceToEnemy = AI.calculateDistance(actionTaker.getTile(), nearestTileToAvatar);
        this.actionScore = this.actionScore - (int) distanceToEnemy;
        if (this.actionScore<1){
            this.actionScore =1; //ensures that its always a chance even if distance is further than 7.

        }
        if (gameState.getTurnNumber()>15){ //higher weighting in late game
            actionScore = actionScore*3;
        }else if(gameState.getTurnNumber()>10){
            actionScore = actionScore*2;
        }




    }
}
