package structures.AI;

import structures.GameState;
import structures.basic.MoveableUnit;
import structures.basic.Tile;

public class AIAttackUnit extends UnitAction{
    private MoveableUnit enemyUnit;

    public AIAttackUnit(MoveableUnit actionTaker, GameState gameState, MoveableUnit enemyUnit){
        this.actionTaker = actionTaker;
        this.gameState = gameState;
        this.enemyUnit = enemyUnit;

    }

//    public boolean canAttackUnit (){
//        if (targetTile.getUnit()!= null){ //if tile has unit, for null handling
//            if (targetTile.getUnit().isUserOwned()!=actionTaker.isUserOwned()){
//
//            }
//        }else {
//            return false;
//        }
//    }
}
