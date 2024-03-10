// This class tests the Unit class to make sure it works correctly.

import structures.basic.Tile;
import structures.basic.Unit;
import structures.basic.UnitAnimationType;
import structures.basic.Position;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;

import java.beans.Transient;

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

    @Test
    void testUnitBehavior() {
   
    unit.setAnimation(UnitAnimationType.attack);
    assertEquals(UnitAnimationType.attack, unit.getAnimation());

    Tile tile = new Tile(1, 2, 3, 4);
    unit.setPositionByTile(tile);
    assertEquals(tile.getXpos(), unit.getPosition().getX());
    assertEquals(tile.getYpos(), unit.getPosition().getY());
    assertEquals(tile.getTilex(), unit.getPosition().getTileX());
    assertEquals(tile.getTiley(), unit.getPosition().getTileY());

    unit.setStunned(true);
    assertTrue(unit.isStunned());
}
    


    // Add more test methods maybe.
}