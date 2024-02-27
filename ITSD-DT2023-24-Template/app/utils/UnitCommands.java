package utils;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.Board;
import structures.basic.MoveableUnit;
import structures.basic.Provoke;
import structures.basic.Tile;
import structures.basic.UnitAnimationType;
import structures.basic.Unit;

import java.util.ArrayList;

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
        BasicCommands.playUnitAnimation(out, m.getUnit(), UnitAnimationType.idle);
        BasicCommands.playUnitAnimation(out, attacker.getUnit(), UnitAnimationType.idle);

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
        if (mover.isProvoke()) {
        	return false;
        }
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

    
    public static void actionableTiles(MoveableUnit mover, ActorRef out, GameState gameState){
        //need to add logic about last turnMoved and lastTurn attacked and turnSummoned
        System.out.println("Actionable tiles is running.");
        int xPos = mover.getTile().getTilex();
        int yPos = mover.getTile().getTiley();
        Board board = gameState.getBoard();

        if (mover.getTurnSummoned()!=gameState.getTurnNumber()){//hasn't been summoned this turn, allow action
            System.out.println("Unit hasn't been summoned this turn");
            if (mover.getLastTurnAttacked() != gameState.getTurnNumber()){//hasn't attacked this turn can still move and attack
                System.out.println("Unit hasn't attacked this turn so it can still move and attack");
                if (mover.getLastTurnMoved()!= gameState.getTurnNumber()){//hasn't moved this turn
                    System.out.println("Highlighting tiles white");
                    //can still move and attack
                    //highlighting for moving (white)
                    gameState.setLastUnitClicked(mover);
                    gameState.setLastMessage(GameState.friendlyUnitClicked);
                    
                    boolean hasProvokeAdjacent = false;
                    
                    //Loop through adjacent squares
                    for (int i = xPos - 1; i<=xPos+1;i++){ // i is x
                        for (int j = yPos -1 ; j<=yPos+1;j++){ // j is y
                            if ( 0<=i && i<=8 && 0<=j && j<=4 ){ //if coord in board range
                                Tile highlightTile = board.getTile(i,j);
                                if (highlightTile.getUnit() instanceof Provoke && mover.isUserOwned() != highlightTile.getUnit().isUserOwned()) {
                                	//BasicCommands.drawTile(out,highlightTile, 2);
                                    hasProvokeAdjacent = true;
                                    BasicCommands.addPlayer1Notification(out, "This unit is within range of a Provoke ability. Cannot move & can only attack provokers.", 2);
                                    mover.setProvoke(true);
                                    return;
                                } else if (highlightTile.getUnit()==null){//tile has no unit, safe for highlighting
                                    BasicCommands.drawTile(out,highlightTile, 1);
                                
                               /* if (highlightTile.getUnit()==null){//tile has no unit, safe for highlighting
                                    BasicCommands.drawTile(out,highlightTile, 1);
//									System.out.println(i + " " + j);
                                } else if (highlightTile.getUnit() instanceof Provoke && mover.isUserOwned() != highlightTile.getUnit().isUserOwned()) {
                                    BasicCommands.drawTile(out,highlightTile, 2);
                                    hasProvokeAdjacent = true;
                                    BasicCommands.addPlayer1Notification(out, "This unit is within range of a Provoke ability. Cannot move & can only attack provokers.", 2);

                                }*/
                            }
                        }
                    }
                  } 
                    //Provoke logic
                    if (hasProvokeAdjacent) {
                    	mover.setLastTurnMoved(gameState.getTurnNumber()+ 1); //Unit cannot move
                    	return;
                    }
                    
                    
                    //the below conditions are for highlighting directions +2 in cardinal directions for movement
                    if(xPos-2>=0){
                        if (board.getTile(xPos-1,yPos).getUnit() == null) { //if space - 1 is empty
                            Tile highlightTile = board.getTile(xPos - 2, yPos);
                            if (highlightTile.getUnit() == null){
                                BasicCommands.drawTile(out, highlightTile, 1);
                            }

                        }
                    }
                    if(xPos+2<=8){
                        if (board.getTile(xPos+1,yPos).getUnit() == null) { //if space + 1 is empty
                            Tile highlightTile = board.getTile(xPos+2, yPos);
                            if (highlightTile.getUnit() == null) {
                                BasicCommands.drawTile(out, highlightTile, 1);
                            }

                        }
                    }
                    if(yPos-2>=0){
                        if (board.getTile(xPos,yPos-1).getUnit() == null) { //if space - 1 is empty
                            Tile highlightTile = board.getTile(xPos, yPos-2);
                            if (highlightTile.getUnit() == null) {
                                BasicCommands.drawTile(out, highlightTile, 1);
                            }

                        }
                    }
                    if(yPos+2<=4){
                        if (board.getTile(xPos,yPos+1).getUnit() == null) { //if space + 1 is empty
                            Tile highlightTile = board.getTile(xPos, yPos+2);
                            if (highlightTile.getUnit() == null) {
                                BasicCommands.drawTile(out, highlightTile, 1);
                            }

                        }
                    }
                    //code for then highlighting tiles red
                    for (int i = xPos - 1; i<=xPos+1;i++){ // i is x
                        for (int j = yPos -1 ; j<=yPos+1;j++){ // j is y
                            if ( 0<=i && i<=8 && 0<=j && j<=4 ){ //if coord in board range
                                Tile highlightTile = board.getTile(i,j);
                                if (highlightTile.getUnit()!= null && mover.isUserOwned() != highlightTile.getUnit().isUserOwned()){//tile has unit and if enemy unit
                                    BasicCommands.drawTile(out,highlightTile, 2);

                                }
                            }
                        }
                    }

                }else { //has moved, can only attack
                    try {Thread.sleep(250);} catch (InterruptedException e) {e.printStackTrace();}
                    BasicCommands.addPlayer1Notification(out, "This unit has already moved, it can only attack", 2);
                    System.out.println("Has moved, can only attack");
                    gameState.setLastUnitClicked(mover);
                    gameState.setLastMessage(GameState.friendlyUnitClicked);
                    for (int i = xPos - 1; i<=xPos+1;i++){ // i is x
                        for (int j = yPos -1 ; j<=yPos+1;j++){ // j is y
                            if ( 0<=i && i<=8 && 0<=j && j<=4 ){ //if coord in board range
                                Tile highlightTile = board.getTile(i,j);
                                if (highlightTile.getUnit()!= null && mover.isUserOwned() != highlightTile.getUnit().isUserOwned()){//tile has unit and if enemy unit
                                    BasicCommands.drawTile(out,highlightTile, 2);

                                }
                            }
                        }
                    }
                }
            }else{
                //has attacked this turn, can't move or attack again
                //inform user
                try {Thread.sleep(250);} catch (InterruptedException e) {e.printStackTrace();}
                gameState.setLastMessage(GameState.noEvent);
                BasicCommands.addPlayer1Notification(out, "This unit has already attacked, it can't perform another action", 2);



            }
        }else{ //summoned this turn, no action
            //insert code notifying user
            System.out.println("Summoned this turn");
            try {Thread.sleep(250);} catch (InterruptedException e) {e.printStackTrace();}
            gameState.setLastMessage(GameState.noEvent);
            BasicCommands.addPlayer1Notification(out, "This unit was summoned this turn, it can't perform an action.", 2);

        }
    }

    public static void summon (MoveableUnit summon, ActorRef out, Tile tile, GameState gameState){
        boolean userOwned = summon.isUserOwned();
        if (canSummon(gameState,userOwned,tile)) {
            tile.setUnit(summon);
            summon.getUnit().setPositionByTile(tile);//sets player avatar on tile in front end
            summon.setTurnSummoned(gameState.getTurnNumber());
            summon.setLastTurnAttacked(gameState.getTurnNumber());
            BasicCommands.drawUnit(out, summon.getUnit(), tile); //sets player avatar on tile in front end
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            BasicCommands.setUnitHealth(out, summon.getUnit(), summon.getCurrentHealth());
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            BasicCommands.setUnitAttack(out, summon.getUnit(), summon.getAttack());
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            gameState.getBoard().openingGambit(out, gameState);//for opening gambit
            gameState.getBoard().renderBoard(out);
            gameState.setLastMessage(GameState.noEvent);
        } else{ //inform player unsuitable location
            BasicCommands.addPlayer1Notification(out,"Can't summon here", 2);
        }
    }

    public static void summonableTiles(ActorRef out, GameState gameState){
        Board board = gameState.getBoard();
        ArrayList<MoveableUnit> friendlyUnits = board.friendlyUnits(true); //returns all player owned units
        for (MoveableUnit unit : friendlyUnits){
            Tile friendlyTile = unit.getTile();
            int xPos = friendlyTile.getTilex();
            int yPos = friendlyTile.getTiley();
            //Loop through adjacent squares
            for (int i = xPos - 1; i<=xPos+1;i++){ // i is x
                for (int j = yPos -1 ; j<=yPos+1;j++){ // j is y
                    if ( 0<=i && i<=8 && 0<=j && j<=4 ){ //if coord in board range
                        Tile highlightTile = board.getTile(i,j);
                        if (highlightTile.getUnit()==null){//tile has no unit, safe for highlighting
                            BasicCommands.drawTile(out,highlightTile, 1);
                        }
                    }
                }
            }
        }
    }

    public static boolean canSummon (GameState gameState, boolean userOwned, Tile possibleTile){
        Board board = gameState.getBoard();
        ArrayList<MoveableUnit> friendlyUnits = board.friendlyUnits(userOwned); //returns all player owned units
        for (MoveableUnit unit : friendlyUnits){
            Tile friendlyTile = unit.getTile();
            int xPos = friendlyTile.getTilex();
            int yPos = friendlyTile.getTiley();
            //Loop through adjacent squares
            for (int i = xPos - 1; i<=xPos+1;i++){ // i is x
                for (int j = yPos -1 ; j<=yPos+1;j++){ // j is y
                    if ( 0<=i && i<=8 && 0<=j && j<=4 ){ //if coord in board range
                        Tile highlightTile = board.getTile(i,j);
                        if (highlightTile.getUnit()==null&& highlightTile.equals(possibleTile)){//tile has no unit, safe for summon
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
