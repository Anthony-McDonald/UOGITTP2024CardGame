package structures.basic;

import java.lang.reflect.Array;
import java.util.ArrayList;

import akka.actor.ActorRef;
import commands.BasicCommands;
import structures.GameState;
import utils.BasicObjectBuilders;
import structures.basic.Deathwatch;

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
    public void renderBoard (ActorRef out){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                    BasicCommands.drawTile(out, this.tiles[i][j], 0);
            }
        }
    }

    public Tile getTile (int x, int y){
        if(0<=x && x<9 && 0<= y &&y<5) {
            Tile tile = this.tiles[x][y];
            return tile;
        }else{
            return null;
        }
    }

    public Tile [][] getAllTiles (){
        return this.tiles;
    }

    public void unitDeath(ActorRef out, GameState gameState) {
        ArrayList<MoveableUnit> allUnits = friendlyUnits(true);//returns human units
        allUnits.addAll(friendlyUnits(false)); //adds AI units
        for (MoveableUnit unit : allUnits){
            if (unit instanceof Deathwatch){
                ((Deathwatch) unit).deathWatch(out, gameState);
            }
        }
    }

    public void openingGambit(ActorRef out, GameState gameState){
        ArrayList<MoveableUnit> allUnits = friendlyUnits(true);//returns human units
        allUnits.addAll(friendlyUnits(false)); //adds AI units
        for (MoveableUnit unit : allUnits){
            if (unit instanceof OpeningGambit){
                ((OpeningGambit) unit).openingGambit(out, gameState);
            }
        }




    }

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
