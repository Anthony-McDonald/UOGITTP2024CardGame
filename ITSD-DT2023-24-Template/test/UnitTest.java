// This class tests the Unit class to make sure it works correctly.

import structures.basic.Tile;
import structures.basic.Unit;
import structures.basic.UnitAnimationType;
import structures.basic.ImageCorrection;
import structures.basic.Position;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class UnitTest {

    private Unit unit;
    private Tile tile;

    @Before 
    public void setUp() {
        unit = new Unit();
        tile = new Tile();
        
        // Set the position of the unit and the tile
        Position position = new Position(1, 2); // or any other position
        unit.setPosition(position);
        tile.setPosition(position);
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
    void testSetAndGetId() {
        int id = 1;
        unit.setId(id);
        assertEquals(id, unit.getId());

        // Test with a null id
        assertThrows(IllegalArgumentException.class, () -> unit.setId(-1));

        // Test with a large id
        int largeId = Integer.MAX_VALUE;
        unit.setId(largeId);
        assertEquals(largeId, unit.getId());
    }

    @Test
    void testSetAndGetAnimation() {
        UnitAnimationType animation = UnitAnimationType.MOVE; // or any other animation
        unit.setAnimation(animation);
        assertEquals(animation, unit.getAnimation());

        // Test with a null animation
        assertThrows(NullPointerException.class, () -> unit.setAnimation(null));
    }

    @Test
    void testSetAndGetCorrection() {
        ImageCorrection correction = new ImageCorrection();
        unit.setCorrection(correction);
        assertEquals(correction, unit.getCorrection());

        // Test with a null correction
         assertThrows(NullPointerException.class, () -> unit.setCorrection(null));
    }

    @Test
    public void testProperties() {
        Position newPosition = new Position(); 
        unit.setPosition(newPosition);
        assertEquals(newPosition, unit.getPosition());

        // Test for getting the position
        Position fetchedPosition = unit.getPosition();
        assertEquals(newPosition, fetchedPosition); 
    }

    @Test
    void testUnitBehavior() {

        //testIsStunned
        unit.setStunned(true);
        assertTrue(unit.isStunned());
        unit.setStunned(false);
        assertFalse(unit.isStunned());

        unit.setAnimation(UnitAnimationType.attack);
        assertEquals(UnitAnimationType.attack, unit.getAnimation());

        //test for positions
        Tile tile = new Tile(1, 2, 3, 4);
        unit.setPositionByTile(tile);
        Position position = unit.getPosition();
        assertEquals(tile.getXpos(), position.getX());
        assertEquals(tile.getYpos(), position.getY());
        assertEquals(tile.getTilex(), position.getTileX());
        assertEquals(tile.getTiley(), position.getTileY());

    }

    @Test
    public void testUnitConstructor() {
        Unit newUnit = new Unit(1, UnitAnimationType.attack, new Position(), new ImageCorrection(), true);
        assertNotNull(newUnit);
        assertEquals(1, newUnit.getId());
        assertEquals(UnitAnimationType.attack, newUnit.getAnimation());
        assertNotNull(newUnit.getPosition());
        assertNotNull(newUnit.getCorrection());
        assertTrue(newUnit.isStunned());
} // this method seems redundant, but it is for testing the constructor in the Unit class, rather than any other method or attributes.

}