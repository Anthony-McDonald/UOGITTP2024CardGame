package utils;

import akka.actor.ActorRef;
import allCards.SaberspineTiger;
import allCards.SilverguardKnight;
import allCards.YoungFlamewing;
import commands.BasicCommands;
import structures.GameState;
import structures.basic.*;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class UnitCommands {
	
	/**
	 * 
	 * This method allows everything that inherints Moveable unit i.e. Creatures, Wraithling & Avatar
	 * and provides logic on the attacking of the attacker, the counter attacking of the target and updating stats of the units appropriately.
	 * This also checks if the attacker is within an enemy unit with provoke & does the appropriate action.
	 * The method also updates the player with player notifications, indicating events and also manages appropriate animations.
	 */
	
    public static void attackUnit(MoveableUnit attacker, ActorRef out, Tile tile, GameState gameState) {
        MoveableUnit m = tile.getUnit();
        //insert logic about if attack is possible.

        if (isProvokeAdjacent(attacker,gameState)){
            // if provoke is adjacent
            if (! (m instanceof Provoke)){
                BasicCommands.addPlayer1Notification(out, "Unit can only attack Provokers", 3);
                return; //ends attack logic
            }

        }

        if (attacker.getLastTurnAttacked() != gameState.getTurnNumber()) {
            if (canAttack(attacker, tile, gameState)) {
                if (adjacentTiles(attacker.getTile(),gameState).contains(tile)) {
                    //unit is adjacent so no move required
                    attacker.setLastTurnAttacked(gameState.getTurnNumber());
                    int enemyHealth = m.getCurrentHealth();
                    BasicCommands.playUnitAnimation(out, attacker.getUnit(), UnitAnimationType.attack); //attack animation
                    enemyHealth = enemyHealth - attacker.getAttack();
                    m.setCurrentHealth(enemyHealth, out, gameState);
                    gameState.getBoard().renderBoard(out); //resets board
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }
                    BasicCommands.playUnitAnimation(out, attacker.getUnit(), UnitAnimationType.idle);
                    if (enemyHealth > 0) { //if enemy is alive, counterattack
                        BasicCommands.playUnitAnimation(out, m.getUnit(), UnitAnimationType.attack);//unit attack animation
                        attacker.setCurrentHealth((attacker.getCurrentHealth() - m.getAttack()), out, gameState);
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        BasicCommands.playUnitAnimation(out, m.getUnit(), UnitAnimationType.idle);

                    }
                    gameState.setLastMessage(GameState.noEvent);
                }else{
                    //unit is not adjacent so move is required.
                    ArrayList<Tile>targetAdjacentTiles = adjacentTiles(tile, gameState);
                    ArrayList<Tile>attackerMoveableTiles = moveableTiles(attacker,gameState);
                    Tile moveTile = null;
                    for (Tile adjacentTile : targetAdjacentTiles){
                        if (attackerMoveableTiles.contains(adjacentTile)){
                            moveTile = adjacentTile;
                            break;
                        }
                    }
                    moveUnit(attacker, out, moveTile, gameState); //moves unit to appropriate tile
                    attacker.setLastTurnAttacked(gameState.getTurnNumber());
                    int enemyHealth = m.getCurrentHealth();
                    BasicCommands.playUnitAnimation(out, attacker.getUnit(), UnitAnimationType.attack); //attack animation
                    enemyHealth = enemyHealth - attacker.getAttack();
                    m.setCurrentHealth(enemyHealth, out, gameState);
                    gameState.getBoard().renderBoard(out); //resets board
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }
                    BasicCommands.playUnitAnimation(out, attacker.getUnit(), UnitAnimationType.idle);
                    if (enemyHealth > 0) { //if enemy is alive, counterattack
                        BasicCommands.playUnitAnimation(out, m.getUnit(), UnitAnimationType.attack);//unit attack animation
                        attacker.setCurrentHealth((attacker.getCurrentHealth() - m.getAttack()), out, gameState);
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        BasicCommands.playUnitAnimation(out, m.getUnit(), UnitAnimationType.idle);

                    }
                    gameState.setLastMessage(GameState.noEvent);
                }
            } else {
                //attack not possible on this unit, inform user.
                //
                BasicCommands.addPlayer1Notification(out, "Unit can't attack there!", 3);
            }
        }else{
            //already attacked this turn
            gameState.setLastMessage(GameState.noEvent);
        }
    }
    
    /**
     * This method will return a boolean to indicate whether there
     * is a valid target for the attacker i.e. checks if the target tile
     * has a unit & also if it is an enemy unit. This also checks for
     * if there is an enemy unit with provoke within range & does the logic
     * for not allowing it to attack anything else besides the provoker.
     */

    public static boolean canAttack (MoveableUnit attacker, Tile targetTile, GameState gameState){
        Tile currentTile = attacker.getTile();
        Board board = gameState.getBoard();
        if (isProvokeAdjacent(attacker, gameState)){
            if (targetTile.getUnit()!= null){
                MoveableUnit unit = targetTile.getUnit();
                if (!(unit instanceof Provoke) && unit.isUserOwned()!= attacker.isUserOwned()){
                    System.out.println("can't attack, being provoked");
                    return false;
                }
            }
        }
        if (targetTile.getUnit()==null){ //no unit on tile
            System.out.println("Failing because of no unit on tile");
            return false;
        }
        if (targetTile.getUnit().isUserOwned()==attacker.isUserOwned()){
            //unit is friendly can't attack
            System.out.println("Target is friendly");
            return false;
        }

        ArrayList<Tile>attackableTiles = attackableTiles(attacker,gameState);

        if (attackableTiles.contains(targetTile)){
            return true;
        }
        System.out.println("failed cause targetTile not contained within attackable tiles");
        return false;
    }

    
    /**
     * This method handles the moving of a MoveableUnit when they are clicked.
     * The logic that is being handled contains a provoke check to ensure that
     * if provoked they are not able to move. he method also updates the player with player
     * notifications,indicating events and also manages appropriate animations.
     */
    public static void moveUnit(MoveableUnit mover, ActorRef out, Tile tile, GameState gameState) {
        //insert logic about if move can occur
        if (gameState.getTurnNumber()!= mover.getLastTurnMoved()) { //hasn't moved this turn
            if (isProvokeAdjacent(mover,gameState)){ //is provoked
                BasicCommands.addPlayer1Notification(out, "Unit can't move, it's provoked.", 3);
                return;
            }
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
                if(mover.isUserOwned()) {
                    BasicCommands.addPlayer1Notification(out, "Unit can't move here.", 3);
                    try {
                        Thread.sleep(250);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
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
    
    /**
     * This method returns a boolean for canMove, which indicates to the player
     * which tiles are able to move to when the MoveableUnit is clicked.
     * The tiles that are able to be moved to must be within range & does not already have a unit on it.
     * This method also handles the ability of "Fly" which is unique to YoungFlamewing
     */
    
    public static boolean canMove (MoveableUnit mover, Tile targetTile, Board board){ //method for determining if a unit can move to a tile
        if (mover instanceof YoungFlamewing) {		//Fly ability, can move anywhere on the board
        	return targetTile.getUnit() == null;
        	/*for (int i =0; i< 9; i++) {
        		for (int j = 0; j <5; j++) {
        			Tile currentTile = board.getTile(i, j);
        			if (currentTile != targetTile && currentTile.getUnit() != null) {
        			//if (currentTile.getUnit() == null) {
        				return false;
        		}
        	}
        }
        return true;*/
    }
    	
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
                return targetTile.equals(highlightTile) && highlightTile.getUnit() == null;
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
        
        if (mover instanceof YoungFlamewing) {
        	 for (int i = 0; i < 9; i++) {
                 for (int j = 0; j < 5; j++) {
                     Tile currentTile = board.getTile(i, j);
                     // Check if the tile is empty
                     if (currentTile.getUnit() == null) {
                         // Highlight the empty tile
                         BasicCommands.drawTile(out, currentTile, 1);
                     }
                 }
        	 }
        	 
        }

        if (isProvokeAdjacent(mover,gameState)){
            //separate logic for provoked units
            if (mover.getTurnSummoned()!= gameState.getTurnNumber()){
                //not summoned this turn
                if (mover.getLastTurnAttacked()!=gameState.getTurnNumber()){
                //not attacked this turn
                    System.out.println("Uh oh its mr provoke");
                    System.out.println("Initiating provoked highlighting");
                    BasicCommands.addPlayer1Notification(out, "Unit is provoked!", 2);
                    gameState.setLastUnitClicked(mover);
                    gameState.setLastMessage(GameState.friendlyUnitClicked);
                    for (int i = xPos - 1; i<=xPos+1;i++){ // i is x
                        for (int j = yPos -1 ; j<=yPos+1;j++){ // j is y
                            if ( 0<=i && i<=8 && 0<=j && j<=4 ){ //if coord in board range
                                Tile adjacentTile = board.getTile(i,j);
                                if (adjacentTile.getUnit()!= null){
                                    MoveableUnit adjacentUnit = adjacentTile.getUnit();
                                    if (adjacentUnit instanceof Provoke && adjacentUnit.isUserOwned()!= mover.isUserOwned()){
                                        BasicCommands.drawTile(out, adjacentTile, 2);
                                    }
                                }
                            }
                        }
                    }
                    return;
                }else{
                    gameState.setLastMessage(GameState.noEvent);
                    BasicCommands.addPlayer1Notification(out, "This unit has already attacked, it can't perform another action", 2);
                }

            }else{ //summoned this turn, no action
                //insert code notifying user
                System.out.println("Summoned this turn");
                try {Thread.sleep(250);} catch (InterruptedException e) {e.printStackTrace();}
                gameState.setLastMessage(GameState.noEvent);
                BasicCommands.addPlayer1Notification(out, "This unit was summoned this turn, it can't perform an action.", 2);
                return;
            }
        }

        if (mover.getTurnSummoned()!=gameState.getTurnNumber()){//hasn't been summoned this turn, allow action
            System.out.println("Unit hasn't been summoned this turn");
            if (mover.getLastTurnAttacked() != gameState.getTurnNumber()){//hasn't attacked this turn can still move and attack
                System.out.println("Unit hasn't attacked this turn so it can still move and attack");
                if (mover.getLastTurnMoved()!= gameState.getTurnNumber()){//hasn't moved this turn
                    gameState.setLastUnitClicked(mover);
                    gameState.setLastMessage(GameState.friendlyUnitClicked);
                    System.out.println("Highlighting tiles white");
                    ArrayList<Tile>moveableTiles = moveableTiles(mover,gameState);
                    for (Tile highlightTile : moveableTiles){
                        BasicCommands.drawTile(out, highlightTile, 1);
                    }
                    ArrayList<Tile>attackableTiles = attackableTiles(mover,gameState);
                    for (Tile highlightTile : attackableTiles){
                        BasicCommands.drawTile(out, highlightTile, 2);
                    }
                }else { //has moved, can only attack
                    try {Thread.sleep(250);} catch (InterruptedException e) {e.printStackTrace();}
                    BasicCommands.addPlayer1Notification(out, "This unit has already moved, it can only attack", 2);
                    System.out.println("Has moved, can only attack");
                    gameState.setLastUnitClicked(mover);
                    gameState.setLastMessage(GameState.friendlyUnitClicked);
                    ArrayList<Tile>attackableTiles = attackableTiles(mover,gameState);
                    for (Tile highlightTile : attackableTiles){
                        BasicCommands.drawTile(out, highlightTile, 2);
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
        if (canSummon(gameState,userOwned,tile) || gameState.getLastMessage().equals(GameState.darkTerminusOngoing)) {
            tile.setUnit(summon);
            if (summon instanceof Creature){
                Creature creature = (Creature) summon;
                creature.setUnit(BasicObjectBuilders.loadUnit(creature.getUnitConfig(), gameState.getFrontEndUnitID(), Unit.class));
            }
            System.out.println("Unit is " + summon.getUnit());
            summon.getUnit().setPositionByTile(tile);//sets player avatar on tile in front end
            summon.setTurnSummoned(gameState.getTurnNumber());
            summon.setLastTurnAttacked(gameState.getTurnNumber());
            BasicCommands.drawUnit(out, summon.getUnit(), tile); //sets player avatar on tile in front end
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            BasicCommands.setUnitHealth(out, summon.getUnit(), summon.getCurrentHealth());
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            BasicCommands.setUnitAttack(out, summon.getUnit(), summon.getAttack());
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            if (summon instanceof SaberspineTiger) {
            	summon.setTurnSummoned(summon.getTurnSummoned()-1);
            }

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

    public static boolean isProvokeAdjacent (MoveableUnit actionTaker, GameState gameState){
        Board board = gameState.getBoard();
        boolean userOwned = actionTaker.isUserOwned();
        Tile unitTile = actionTaker.getTile();
        int xPos = unitTile.getTilex();
        int yPos = unitTile.getTiley();

        //Loop through adjacent squares
        for (int i = xPos - 1; i<=xPos+1;i++){ // i is x
            for (int j = yPos -1 ; j<=yPos+1;j++){ // j is y
                if ( 0<=i && i<=8 && 0<=j && j<=4 ){ //if coord in board range
                    Tile adjacentTile = board.getTile(i,j);
                    if (adjacentTile.getUnit()!= null){
                        MoveableUnit adjacentTileUnit = adjacentTile.getUnit();
                        if (adjacentTileUnit instanceof Provoke && adjacentTileUnit.isUserOwned()!= userOwned){
                            //if adjacent unit has provoke and is enemy
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public static ArrayList<Tile> adjacentTiles(Tile selectedTile, GameState gameState){
        //method for returning all adjacent tiles
        ArrayList<Tile>adjacentTiles = new ArrayList<>();
        Board board = gameState.getBoard();
        int xPos = selectedTile.getTilex();
        int yPos = selectedTile.getTiley();
        for (int i = xPos - 1; i<=xPos+1;i++){ // i is x
            for (int j = yPos -1 ; j<=yPos+1;j++){ // j is y
                if ( 0<=i && i<=8 && 0<=j && j<=4 ){ //if coord in board range
                    Tile adjacentTile = board.getTile(i,j);
                    adjacentTiles.add(adjacentTile);

                }
            }
        }
        adjacentTiles.remove(selectedTile);
        return adjacentTiles;
    }

    public static ArrayList<Tile> moveableTiles(MoveableUnit selectedUnit, GameState gameState){
        Tile selectedTile = selectedUnit.getTile();
        //method for returning arraylist of all moveable tiles (to be used in AI methods)
        Board board = gameState.getBoard();
        int xPos = selectedTile.getTilex();
        int yPos = selectedTile.getTiley();
        ArrayList<Tile>moveableTiles = new ArrayList<>();
        ArrayList<Tile>adjacentTiles = adjacentTiles(selectedTile, gameState);
        for (Tile tile : adjacentTiles){
            if (tile.getUnit()==null){
                moveableTiles.add(tile);
            }
        }
        //the below conditions are for highlighting directions +2 in cardinal directions for movement
        if(xPos-2>=0){
            if (board.getTile(xPos-1,yPos).getUnit() == null) { //if space - 1 is empty
                Tile highlightTile = board.getTile(xPos - 2, yPos);
                if (!isTileProvokeAdjacent(board.getTile(xPos-1,yPos), gameState, selectedUnit.isUserOwned())){
                    //ensures tile before isn't provoke adjacent, preventing move along.
                    if (highlightTile.getUnit() == null) {
                        moveableTiles.add(highlightTile);
                    }
                }

            }
        }
        if(xPos+2<=8){
            if (board.getTile(xPos+1,yPos).getUnit() == null) { //if space + 1 is empty
                Tile highlightTile = board.getTile(xPos+2, yPos);
                if (!isTileProvokeAdjacent(board.getTile(xPos+1,yPos), gameState, selectedUnit.isUserOwned())) {
                    if (highlightTile.getUnit() == null) {
                        moveableTiles.add(highlightTile);
                    }
                }

            }
        }
        if(yPos-2>=0){
            if (board.getTile(xPos,yPos-1).getUnit() == null) { //if space - 1 is empty
                Tile highlightTile = board.getTile(xPos, yPos-2);
                if (!isTileProvokeAdjacent(board.getTile(xPos,yPos-1), gameState, selectedUnit.isUserOwned())) {
                    //ensures tile before isn't provoke adjacent, preventing move along.
                    if (highlightTile.getUnit() == null) {
                        moveableTiles.add(highlightTile);
                    }
                }

            }
        }
        if(yPos+2<=4){
            if (board.getTile(xPos,yPos+1).getUnit() == null) { //if space + 1 is empty
                if (!isTileProvokeAdjacent(board.getTile(xPos,yPos+1), gameState, selectedUnit.isUserOwned())) {
                    //ensures tile before isn't provoke adjacent, preventing move along.
                    Tile highlightTile = board.getTile(xPos, yPos + 2);
                    if (highlightTile.getUnit() == null) {
                        moveableTiles.add(highlightTile);
                    }
                }

            }
        }
        return moveableTiles;
    }

    public static ArrayList<Tile>attackableTiles(MoveableUnit attacker, GameState gameState){
        //returns all possible attackableTiles, for use within AI
        boolean userOwned = attacker.isUserOwned();
        Board board = gameState.getBoard();
        Tile selectedTile = attacker.getTile();
        ArrayList<Tile>moveableTiles = moveableTiles(attacker,gameState);
        moveableTiles.add(selectedTile);
        ArrayList<Tile>attackableTiles = new ArrayList<>();
        for (Tile tile: moveableTiles){
            //gets adjacent tiles for each moveable tile


            ArrayList<Tile>adjacentToMoveableTiles = adjacentTiles(tile, gameState);

            boolean provokeAdjacent = isTileProvokeAdjacent(tile, gameState ,attacker.isUserOwned());
            if (!provokeAdjacent) {
                for (Tile adjacentTile : adjacentToMoveableTiles) {
                    if (adjacentTile.getUnit() != null && adjacentTile.getUnit().isUserOwned() != userOwned) {
                        //if adjacent tile has unit and unit is enemy
                        attackableTiles.add(adjacentTile);
                    }
                }
            }else{ //moveable tile is adjacent to provoked unit which impacts what it can attack
                for(Tile adjacentTile : adjacentToMoveableTiles){
                    if (adjacentTile.getUnit()!=null && adjacentTile.getUnit().isUserOwned()!= userOwned){
                        //if adjacent tile has unit and unit is enemy
                        if (adjacentTile.getUnit() instanceof Provoke){
                            //only provokers within range can be attacked
                            attackableTiles.add(adjacentTile);
                        }
                    }
                }
            }

        }
        return attackableTiles;
    }

    public static List<Tile> getAllSummonableTiles (GameState gameState, boolean userOwned){
        ArrayList<MoveableUnit>friendlyUnits = gameState.getBoard().friendlyUnits(userOwned);
        List <Tile> allSummonableTiles = new ArrayList<>();
        for(MoveableUnit unit : friendlyUnits){
            ArrayList<Tile>adjacentTiles = UnitCommands.adjacentTiles(unit.getTile(),gameState);
            for (Tile tile: adjacentTiles){
                if (tile.getUnit()==null){
                    allSummonableTiles.add(tile);
                }
            }
        }
        return allSummonableTiles;
    }

    public static boolean isTileProvokeAdjacent(Tile tile, GameState gameState, boolean userOwned){
        ArrayList<Tile>adjacentTiles = UnitCommands.adjacentTiles(tile, gameState);
        for (Tile adjacentTile : adjacentTiles){
            if (adjacentTile.getUnit()!=null && adjacentTile.getUnit().isUserOwned()!= userOwned){
                //if adjacent tile has unit and unit is enemy
                if (adjacentTile.getUnit() instanceof Provoke){
                    return true;
                }
            }
        }
        return false;
    }
}
