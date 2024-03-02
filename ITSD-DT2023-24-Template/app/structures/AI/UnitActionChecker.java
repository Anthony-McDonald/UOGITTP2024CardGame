package structures.AI;

import akka.actor.ActorRef;
import structures.GameState;
import structures.basic.*;
import utils.UnitCommands;

import java.util.ArrayList;

import static utils.UnitCommands.attackableTiles;

/*
class for checking all actions available to a unit
this class is created and assigned to each AI unit prior to a move
 */
public class UnitActionChecker {
    private MoveableUnit actionTaker;
    private GameState gameState;
    private ActorRef actorRef;
    public UnitActionChecker(MoveableUnit actionTaker, GameState gameState, ActorRef out){
        this.actionTaker=actionTaker;
        this.gameState =gameState;
        this.actorRef = out;
    }

    public void makeAction(){
        ArrayList<UnitAction> weightedActions = new ArrayList<>();


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

    public Tile findNearestTileToUnit(MoveableUnit enemyUnit){
        ArrayList<Tile> moveableTiles = UnitCommands.moveableTiles(actionTaker,gameState);
        MoveableUnit enemy = enemyUnit;
        Tile nearestEnemyTile = enemy.getTile();
        double closestDistance = AI.calculateDistance(actionTaker.getTile(),nearestEnemyTile); //distance between unit and enemy unit
        Tile closestTile = actionTaker.getTile();
        for (Tile tile : moveableTiles){
            double distance = AI.calculateDistance(tile,nearestEnemyTile);
            if (distance<closestDistance){
                closestDistance = distance;
                closestTile = tile;
            }
        }
        return closestTile;
    }

    public boolean isEnemyAvatarAttackable(){
        ArrayList<Tile> attackableTiles = UnitCommands.attackableTiles(actionTaker, gameState);
        ArrayList<MoveableUnit> humanUnits = gameState.getBoard().friendlyUnits(true);
        MoveableUnit humanAvatar = null;
        for (MoveableUnit unit : humanUnits){
            if (unit instanceof Avatar){
                humanAvatar = unit;
            }
        }
        if (attackableTiles.contains(humanAvatar.getTile())){
            return true;
        }else{
            return false;
        }
    }

    public Tile findNearestTiletoNearestEnemy(){
        return findNearestTileToUnit(findNearestEnemy());
    }

    public Tile findNearestTileToEnemyAvatar(){
        ArrayList<MoveableUnit> humanUnits = gameState.getBoard().friendlyUnits(true);
        MoveableUnit humanAvatar = null;
        for (MoveableUnit unit : humanUnits){
            if (unit instanceof Avatar){
                humanAvatar = unit;
            }
        }
        return findNearestTileToUnit(humanAvatar);
    }

    public MoveableUnit isAIUnitUnderThreat(){
        MoveableUnit threatUnit = null; //if this method returns null, no immediate threat to AI avatar
        MoveableUnit aiAvatar = null;
        ArrayList<MoveableUnit> AIUnits = gameState.getBoard().friendlyUnits(false);
        for (MoveableUnit unit : AIUnits){
            if (unit instanceof Avatar){
                aiAvatar = unit;
            }
        }

        ArrayList <Tile> avatarThreatTiles = attackableTiles(actionTaker, gameState);
        for (Tile tile : avatarThreatTiles){
            if (tile.getUnit()!= null && tile.getUnit().isUserOwned()){
                //if tile has unit and unit is human
                threatUnit = tile.getUnit();
            }
        }
        return threatUnit;
    }

    public MoveableUnit findProvoker (){
        MoveableUnit provoker = null;
        //if return is null then no provoker
        if(UnitCommands.isProvokeAdjacent(actionTaker,gameState)){
            for (Tile tile : UnitCommands.adjacentTiles(actionTaker.getTile(),gameState)){ //loop through units adjacent tiles
                if (tile.getUnit()!=null && tile.getUnit() instanceof Provoke && tile.getUnit().isUserOwned()!= actionTaker.isUserOwned()){
                    provoker = tile.getUnit();
                }
            }
        }
        return provoker;
    }




}
