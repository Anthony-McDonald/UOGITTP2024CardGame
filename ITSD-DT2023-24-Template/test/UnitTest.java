// This class tests the Unit class to make sure it works correctly.

import structures.basic.Tile;
import structures.basic.Unit;
import structures.basic.UnitAnimationType;
import structures.basic.Position;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

public class UnitTest {

    private Unit unit;
    private Tile tile;

    @Before 
    public void setUp() {
        unit = new Unit();
        tile = new Tile();
    }

    @Test
    public void testUnitInitialization() {
        assertNotNull(unit);
        assertEquals(1, unit.getId());
        assertNotNull(unit.getPosition());
        assertEquals(tile.getXpos(), unit.getPosition().getX());
        assertEquals(tile.getYpos(), unit.getPosition().getY());
       
    }

    public void testProperties() {
        Position newPosition = new Position(); 
        unit.setPosition(newPosition);
        assertEquals(newPosition, unit.getPosition());
    }



    // Add more test methods maybe.
}