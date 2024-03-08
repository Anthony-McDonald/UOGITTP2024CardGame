import org.junit.Test;
import structures.basic.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class BoardTest {
    @Test //tests board reference isn't null with constructor
    public void testBoardInstantiation() {
        Board board = new Board();
        assertNotNull(board);
    }

    @Test
    public void testBoardInitializedWithCorrectSize() {
        Board board = new Board();
        Tile[][] tiles = board.getAllTiles();
        assertEquals(9, tiles.length);
        assertEquals(5, tiles[0].length);
    }

    @Test
    public void testTilesLoadedWithCorrectXAndYPositions() {
        Board board = new Board();
        Tile[][] tiles = board.getAllTiles();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 5; j++) {
                assertEquals(i, tiles[i][j].getTilex());
                assertEquals(j, tiles[i][j].getTiley());
            }
        }
    }
    @Test
    public void testReturnsTileAtGivenCoordinates() {
        Board board = new Board();
        Tile expectedTile = board.getAllTiles()[2][3];
        Tile actualTile = board.getTile(2, 3);
        assertEquals(expectedTile, actualTile);
    }
    @Test
    public void test_returns_null_with_out_of_bounds_coordinates() {
        Board board = new Board();
        int x = 10;
        int y = 6;
        Tile tile = board.getTile(x, y);
        assertNull(tile);
    }

    @Test
    public void testEmptyListNoFriendlyUnits() {
        Board board = new Board();
        Wraithling wraithling = new Wraithling();
        wraithling.setUserOwned(false);
        Wraithling wraithling2 = new Wraithling();
        wraithling2.setUserOwned(false);
        board.getTile(0, 0).setUnit(wraithling2);
        board.getTile(1, 0).setUnit(wraithling);
        ArrayList <MoveableUnit> result = board.friendlyUnits(true);
        assertEquals(0, result.size());
    }


    @Test
    public void testOneFriendlyUnitMatchUserOwned() {
        Board board = new Board();
        Wraithling wraithling = new Wraithling();
        wraithling.setUserOwned(true);
        Wraithling wraithling2 = new Wraithling();
        wraithling2.setUserOwned(false);
        board.getTile(0, 0).setUnit(wraithling2);
        board.getTile(1, 0).setUnit(wraithling);
        ArrayList <MoveableUnit> result = board.friendlyUnits(true);
        assertEquals(1, result.size());
    }
}
