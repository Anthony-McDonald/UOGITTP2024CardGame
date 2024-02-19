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
}
