package structures.AI;

import akka.actor.ActorRef;
import structures.AI.Actions.*;
import structures.GameState;
import structures.basic.*;
import utils.UnitCommands;

import java.util.ArrayList;
import java.util.Random;

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

    /*
    the logic of this class is that it assesses the following actions:
    Attacking nearest unit
    Moving towards nearest unit (if attack isn't possible)
    Moving towards enemy avatar
    Attacking enemy avatar
    Moving towards enemy unit that is threatening AI avatar
    Attacking enemy unit that is threatening AI avatar
     */

    public void makeAction(){
        if (actionTaker.getLastTurnAttacked() == gameState.getTurnNumber()){
            return; //no actions available so don't make action
        }
        ArrayList<UnitAction> weightedActions = new ArrayList<>();
        if(UnitCommands.isProvokeAdjacent(actionTaker,gameState)){
            System.out.println("unit is provoked, attacking provoker");
            AIUnitProvoked provokeAction = new AIUnitProvoked(actionTaker,gameState,this.findProvoker());
            provokeAction.makeAction(actorRef);
            return; //ends logic here since unit is provoked. we want no other logic to occur
        }
        MoveableUnit nearestEnemy = findNearestEnemy();
        int attackUnitWeight = 0;
        int moveToUnitWeight = 0;
        if(this.isNearestEnemyAttackable()){
            AIAttackUnit attackUnitAction = new AIAttackUnit(actionTaker,gameState,nearestEnemy);
            int weight = attackUnitAction.getActionScore();
            attackUnitWeight = weight;
            for (int i = 0; i<weight;i++){
                weightedActions.add(attackUnitAction); //adds unit according to action score
                //higher score equals higher weighting
            }
        }else{
            //nearest enemy isn't immmediately attackable
            Tile nearestTileToEnemy = findNearestTileToUnit(nearestEnemy);
            AIMoveUnit moveUnitAction = new AIMoveUnit(actionTaker,gameState,nearestTileToEnemy,nearestEnemy);
            int weight = moveUnitAction.getActionScore();
            moveToUnitWeight = weight;
            for (int i = 0; i<weight;i++){
                weightedActions.add(moveUnitAction);
            }
        }
        int attackThreatWeight = 0;
        int moveToThreatWeight = 0;
        if (this.isAIAvatarUnderThreat()!=null){ //there is an enemy unit that can attack the enemy avatar
            MoveableUnit threatUnit = isAIAvatarUnderThreat();
            Tile nearestTileToThreat = findNearestTileToUnit(threatUnit);
            if(UnitCommands.canAttack(actionTaker,threatUnit.getTile(),gameState)){
                //can attack threat

                AIAttackAvatarThreat attackAvatarThreat = new AIAttackAvatarThreat(actionTaker, threatUnit, gameState);
                int weight = attackAvatarThreat.getActionScore();
                attackThreatWeight = weight;
                for (int i = 0; i<weight;i++){
                    weightedActions.add(attackAvatarThreat);
                }
            }else{
                //can't attack so instead move to Avatar Threat
                AIMoveToAvatarThreat moveToAvatarThreat = new AIMoveToAvatarThreat(actionTaker,gameState, nearestTileToThreat, threatUnit);
                int weight = moveToAvatarThreat.getActionScore();
                moveToThreatWeight = weight;
                for (int i = 0; i<weight;i++){
                    weightedActions.add(moveToAvatarThreat);
                }
            }

        }
        int attackAvatarWeight = 0;
        int moveToAvatarWeight = 0;
        ArrayList<MoveableUnit> humanUnits = gameState.getBoard().friendlyUnits(true);
        MoveableUnit humanAvatar = null;
        for (MoveableUnit unit : humanUnits){
            if (unit instanceof Avatar){
                humanAvatar = unit;
            }
        }
        Tile nearestTileToHumanAvatar = findNearestTileToEnemyAvatar();
        if (this.isEnemyAvatarAttackable()){
            AIAttackAvatar aiAttackAvatar = new AIAttackAvatar(actionTaker,humanAvatar,gameState);
            int weight = aiAttackAvatar.getActionScore();
            attackAvatarWeight = weight;
            for (int i = 0; i<weight;i++){
                weightedActions.add(aiAttackAvatar);
            }
        }else{
            AIMoveToAvatar aiMoveToAvatar = new AIMoveToAvatar(actionTaker,gameState, nearestTileToHumanAvatar, humanAvatar);
            int weight = aiMoveToAvatar.getActionScore();
            moveToAvatarWeight = weight;
            for (int i = 0; i<weight;i++){
                weightedActions.add(aiMoveToAvatar);
            }
        }
        int totalWeight = moveToAvatarWeight+ moveToThreatWeight + moveToUnitWeight + attackAvatarWeight + attackThreatWeight + attackUnitWeight;
        double moveToAvatarChance = 0;
        if (moveToAvatarWeight>0) {
            moveToAvatarChance = ((double) moveToAvatarWeight / (double) totalWeight) * 100;
        }
        System.out.println("Chance of moving to the avatar is " + moveToAvatarChance + "%");
        double moveToThreatChance = 0;
        if (moveToThreatWeight > 0) {
            moveToThreatChance = ((double) moveToThreatWeight / (double) totalWeight) * 100;
        }
        System.out.println("Chance of moving to the threat is " + moveToThreatChance + "%");

        double moveToUnitChance = 0;
        if (moveToUnitWeight > 0) {
            moveToUnitChance = ((double) moveToUnitWeight / (double) totalWeight) * 100;
        }
        System.out.println("Chance of moving to the unit is " + moveToUnitChance + "%");

        double attackAvatarChance = 0;
        if (attackAvatarWeight > 0) {
            attackAvatarChance = ((double) attackAvatarWeight / (double) totalWeight) * 100;
        }
        System.out.println("Chance of attacking the avatar is " + attackAvatarChance + "%");

        double attackThreatChance = 0;
        if (attackThreatWeight > 0) {
            attackThreatChance = ((double) attackThreatWeight / (double) totalWeight) * 100;
        }
        System.out.println("Chance of attacking the threat is " + attackThreatChance + "%");

        double attackUnitChance = 0;
        if (attackUnitWeight > 0) {
            attackUnitChance = ((double) attackUnitWeight / (double) totalWeight) * 100;
        }
        System.out.println("Chance of attacking the unit is " + attackUnitChance + "%");


        if (weightedActions.size()>0){
            //there is an action available
            Random rand = new Random();
            int randomActionIndex = rand.nextInt(weightedActions.size()); //chooses random action from weighted list
            UnitAction randomAction = weightedActions.get(randomActionIndex);
            randomAction.makeAction(actorRef);
        }
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

    public MoveableUnit isAIAvatarUnderThreat(){
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
