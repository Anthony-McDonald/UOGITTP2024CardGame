package structures.AI;

import structures.GameState;
import structures.basic.MoveableUnit;
import structures.basic.Tile;

public class AIAttackUnit extends UnitAction{

    public AIAttackUnit(MoveableUnit actionTaker, GameState gameState, Tile targetTile){
        this.actionTaker = actionTaker;
        this.gameState = gameState;
        this.targetTile = targetTile;
    }

//    public boolean canAttackUnit (){
//        if (this.targetTile.getUnit()!=null){ //if tile has unit, for null handling
//
//        }else {
//            return false;
//        }
//    }
}
