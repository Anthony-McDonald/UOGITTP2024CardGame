package utils;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.Board;
import structures.basic.MoveableUnit;
import structures.basic.Tile;
import structures.basic.UnitAnimationType;

public class UnitCommands {
    public static void attackUnit(MoveableUnit attacker, ActorRef out, Tile tile, GameState gameState) {
        MoveableUnit m = tile.getUnit();
        //insert logic about if attack is possible.
        if (attacker.getLastTurnAttacked() != gameState.getTurnNumber()) {
            if (canAttack(attacker, tile, gameState.getBoard())) {
                attacker.setLastTurnAttacked(gameState.getTurnNumber());
                int enemyHealth = m.getCurrentHealth();
                BasicCommands.playUnitAnimation(out, attacker.getUnit(), UnitAnimationType.attack); //attack animation
                enemyHealth = enemyHealth - attacker.getAttack();
                m.setCurrentHealth(enemyHealth, out);
                gameState.getBoard().renderBoard(out); //resets board
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (enemyHealth > 0) { //if enemy is alive, counterattack
                    BasicCommands.playUnitAnimation(out, m.getUnit(), UnitAnimationType.attack);//unit attack animation
                    attacker.setCurrentHealth((attacker.getCurrentHealth() - m.getAttack()), out);
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                gameState.setLastMessage(GameState.noEvent);
            } else {
                //attack not possible on this unit, inform user.
                //
            }
        }else{
            //already attacked this turn
            gameState.setLastMessage(GameState.noEvent);
        }
    }

    public static boolean canAttack (MoveableUnit attacker, Tile targetTile, Board board){
        Tile currentTile = attacker.getTile();
        int xPos = currentTile.getTilex();
        int yPos = currentTile.getTiley();
        for (int i = xPos - 1; i<=xPos+1;i++){ // i is x
            for (int j = yPos -1 ; j<=yPos+1;j++){ // j is y
                if ( 0<=i && i<=8 && 0<=j && j<=4 ){ //if coord in board range
                    Tile highlightTile = board.getTile(i,j);
                    if (highlightTile.getUnit()!=null && highlightTile.getUnit().isUserOwned()!=attacker.isUserOwned()){
                        //if tile has unit and unit is enemy
                        if (targetTile.equals(highlightTile) ){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void moveUnit(MoveableUnit mover, ActorRef out, Tile tile, GameState gameState) {
        //insert logic about if move can occur
        if (gameState.getTurnNumber()!= mover.getLastTurnMoved()) { //hasn't moved this turn
            if (canMove(mover, tile, gameState.getBoard())) {
                mover.setLastTurnMoved(gameState.getTurnNumber());
                gameState.setLastMessage(GameState.noEvent);
                mover.getTile().setUnit(null);
                tile.setUnit(mover); //sets unit on tile in backend
                gameState.getBoard().renderBoard(out);
                BasicCommands.addPlayer1Notification(out, "Moving unit", 3);
                BasicCommands.moveUnitToTile(out, mover.getUnit(), tile);
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                BasicCommands.addPlayer1Notification(out, "Unit can't move here.", 3);
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }else{ //has moved this turn
            gameState.setLastMessage(GameState.noEvent);
            BasicCommands.addPlayer1Notification(out, "Unit has already moved this turn.", 3);
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



    }
    public static boolean canMove (MoveableUnit mover, Tile targetTile, Board board){ //method for determining if a unit can move to a tile
        Tile currentTile = mover.getTile();
        int xPos = currentTile.getTilex();
        int yPos = currentTile.getTiley();
        for (int i = xPos - 1; i<=xPos+1;i++){ // i is x
            for (int j = yPos -1 ; j<=yPos+1;j++){ // j is y
                if ( 0<=i && i<=8 && 0<=j && j<=4 ){ //if coord in board range
                    Tile highlightTile = board.getTile(i,j);
                    if (highlightTile.getUnit()==null){//tile has no unit, safe for highlighting
                        if (targetTile.equals(highlightTile) ){
                            return true;
                        }
                    }
                }
            }
        }
        //the below conditions are for highlighting directions +2 in cardinal directions for movement
        if(xPos-2>=0){
            if (board.getTile(xPos-1,yPos).getUnit() == null) { //if space - 1 is empty
                Tile highlightTile = board.getTile(xPos-2, yPos);
                if (targetTile.equals(highlightTile)&& highlightTile.getUnit() ==null){
                    return true;
                }


            }
        }
        if(xPos+2<=8){
            if (board.getTile(xPos+1,yPos).getUnit() == null) { //if space + 1 is empty
                Tile highlightTile = board.getTile(xPos+2, yPos);
                if (targetTile.equals(highlightTile)&& highlightTile.getUnit() ==null){
                    return true;
                }

            }
        }
        if(yPos-2>=0){
            if (board.getTile(xPos,yPos-1).getUnit() == null) { //if space - 1 is empty
                Tile highlightTile = board.getTile(xPos, yPos-2);
                if (targetTile.equals(highlightTile)&& highlightTile.getUnit() ==null){
                    return true;
                }
            }
        }
        if(yPos+2<=4){
            if (board.getTile(xPos,yPos+1).getUnit() == null) { //if space + 1 is empty
                Tile highlightTile = board.getTile(xPos, yPos+2);
                if (targetTile.equals(highlightTile)&& highlightTile.getUnit() ==null){
                    return true;
                }
            }
        }
        return false;
    }
}
