package structures.basic;

import utils.BasicObjectBuilders;

public class Board {
    Tile [][] array;

    public Board(){
        this.array = new Tile [9][5];
        for (int i = 0; i<9;i++){
            for (int j = 5; j<5;j++){
                array[i][j] = BasicObjectBuilders.loadTile(i+1,j+1);
            }
        }
    }
}
