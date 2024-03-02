package structures.AI;

import structures.GameState;
import structures.basic.Board;
import structures.basic.MoveableUnit;
import structures.basic.Tile;
import utils.UnitCommands;

import java.util.ArrayList;

import static utils.UnitCommands.attackableTiles;

/*
class for checking all actions available to a unit
 */
public class UnitActionChecker {
    private MoveableUnit actionTaker;
    private GameState gameState;
    public UnitActionChecker(MoveableUnit actionTaker, GameState gameState){
        this.actionTaker=actionTaker;
        this.gameState =gameState;
    }

    public MoveableUnit findNearestEnemy(){
        Tile startTile = actionTaker.getTile();
        Board board = gameState.getBoard();
        ArrayList<MoveableUnit>humanUnits = board.friendlyUnits(true);
        MoveableUnit closestUnit = null;
        double closestDistance = Double.MAX_VALUE;
        for (MoveableUnit unit : humanUnits){
            Tile unitTile = unit.getTile();
            double distanceToUnit = AI.calculateDistance(startTile, unitTile);
            if (distanceToUnit<closestDistance){
                closestUnit = unit;
                closestDistance =distanceToUnit;
                System.out.println("Closest unit is now " + unit);

            }
        }
        return closestUnit;



    }

    public boolean isNearestEnemyAttackable(){
        ArrayList<Tile> attackableTiles = UnitCommands.attackableTiles(actionTaker, gameState);
        if (attackableTiles.contains(this.findNearestEnemy().getTile())){
            return true; //unit can directly attack
        }else{
            return false; //unit can't directly attack
        }
    }


}
