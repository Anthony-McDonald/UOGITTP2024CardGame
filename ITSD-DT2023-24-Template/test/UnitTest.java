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
import org.junit.Test;

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

    @Test
    public void testUnitId() {
        unit.setId(2);
        assertEquals(2, unit.getId());
    }

    @Test
    void testIsStunned() {
        unit.setStunned(true);
        assertTrue(unit.isStunned());
    }

    @Test
    void testSetAndGetId() {
        int id = 1;
        unit.setId(id);
        assertEquals(id, unit.getId());
    }
    
    @Test
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
        Position position = unit.getPosition();
        assertEquals(tile.getXpos(), position.getX());
        assertEquals(tile.getYpos(), position.getY());
        assertEquals(tile.getTilex(), position.getTileX());
        assertEquals(tile.getTiley(), position.getTileY());

        unit.setStunned(true);
        assertTrue(unit.isStunned());
    }
    
    



    // Add more test methods maybe.
}