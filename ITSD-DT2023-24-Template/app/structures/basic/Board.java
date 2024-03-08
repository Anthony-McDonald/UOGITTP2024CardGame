package structures.basic;

import java.lang.reflect.Array;
import java.util.ArrayList;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import utils.BasicObjectBuilders;
import structures.basic.Deathwatch;
/**
*This class is for storing all the references to the Tile objects.
 * It contains a 9 by 5 array of Tiles.
 *It also contains the for rendering the base representation of the board, allowing for resets between different gamestates.
*
 */
public class Board {
    private Tile [][] tiles;
    private ArrayList <MoveableUnit> allUnits;

    public Board(){
        this.tiles = new Tile [9][5];
        for (int i = 0; i<9;i++){
            for (int j = 0; j<5;j++){
                tiles[i][j] = BasicObjectBuilders.loadTile(i,j);
            }
        }
    }
    /**
     * This method renders the board, it is used for initially loading the board during initialisation.
     * @param out
     */
    public void renderBoard (ActorRef out){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                    BasicCommands.drawTile(out, this.tiles[i][j], 0);
            }
        }
    }

    /**
     * This method returns a tile at a specified x and y coordinate.
     * @param x
     * @param y
     * @return Tile
     */
    public Tile getTile (int x, int y){
        if(0<=x && x<9 && 0<= y &&y<5) {
            Tile tile = this.tiles[x][y];
            return tile;
        }else{
            return null;
        }
    }

    /**
     * This method returns all the tiles on the board.
     * @return Tile[][]
     */

    public Tile [][] getAllTiles (){
        return this.tiles;
    }

    /**
     * This method is what is used to facilitate the deathwatch ability. It is called from Creature and Wraithling.
     * It notifies the board when this unit dies. The board then iterates through all units present on the board,
     * notifying any that implement the Deathwatch interface to use their deathWatch ability.
     * @param out
     * @param gameState
     */
    public void unitDeath(ActorRef out, GameState gameState) {
        ArrayList<MoveableUnit> allUnits = friendlyUnits(true);//returns human units
        allUnits.addAll(friendlyUnits(false)); //adds AI units
        for (MoveableUnit unit : allUnits){
            if (unit instanceof Deathwatch){
                ((Deathwatch) unit).deathWatch(out, gameState);
            }
        }
    }

    /**
     * This method returns all the units that possess the given boolean. (True = human units, False = AI units)
     * @param userOwned
     * @return
     */
    public ArrayList <MoveableUnit> friendlyUnits (boolean userOwned){
        ArrayList<MoveableUnit> friendlyUnits = new ArrayList<MoveableUnit>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                Tile tile = this.getTile(i,j);
                if (tile.getUnit()!= null){
                    MoveableUnit unit = tile.getUnit();
                    if (unit.isUserOwned()==userOwned){
                        friendlyUnits.add(unit);
                    }
                }
            }
        }
        return friendlyUnits;
    }

}
