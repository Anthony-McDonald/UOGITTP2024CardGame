import org.junit.Test;
import structures.basic.Board;
import structures.basic.Tile;

import static org.junit.Assert.*;

public class BoardTest {
    @Test //tests board reference isn't null with constructor
    public void test_board_instantiation() {
        Board board = new Board();
        assertNotNull(board);
    }

    @Test
    public void test_board_initialized_with_correct_size() {
        Board board = new Board();
        Tile[][] tiles = board.getAllTiles();
        assertEquals(9, tiles.length);
        assertEquals(5, tiles[0].length);
    }

    @Test
    public void test_tiles_loaded_with_correct_x_and_y_positions() {
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
    public void test_returns_tile_at_given_coordinates() {
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
}
