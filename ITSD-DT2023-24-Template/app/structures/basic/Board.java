package structures.basic;

import akka.actor.ActorRef;
import commands.BasicCommands;
import utils.BasicObjectBuilders;

public class Board {
    Tile [][] tiles;

    public Board(){
        this.tiles = new Tile [9][5];
        for (int i = 0; i<9;i++){
            for (int j = 0; j<5;j++){
                tiles[i][j] = BasicObjectBuilders.loadTile(i+1,j+1);
            }
        }
    }
    public void renderBoard (ActorRef out){
        for (int i = 0; i<9;i++){
            for (int j = 0; j<5;j++){
                BasicCommands.drawTile(out,this.tiles[i][j],0);
            }
        }
    }

    public Tile getTile (int x, int y){
        Tile tile = this.tiles[x-1][y-1];
        return tile;
    }

    public Tile [][] getAllTiles (){
        return this.tiles;
    }
}
