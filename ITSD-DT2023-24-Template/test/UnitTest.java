/**
 * Tests for the Unit class.
 * 
 * Each test method checks a specific feature of the Unit class.
 * 
 */

import structures.basic.Tile;
import structures.basic.Unit;
import structures.basic.UnitAnimationType;
import structures.basic.ImageCorrection;
import structures.basic.Position;

import static org.junit.Assert.*;
import org.junit.Test;

public class UnitTest {

    private Unit unit;
    private Tile tile;

    public void setUp() {
        unit = new Unit();
        tile = new Tile();
        unit.setId(1);
        // Set the position of the unit and the tile
        Position position = new Position(1, 2, 3, 4); // or any other position
        unit.setPosition(position);
        tile = new Tile("", position.getXpos(), position.getYpos(), 0, 0, position.getTilex(), position.getTiley());
        unit.setTile(tile);
    }

    @Test
    public void testUnitInitialization() {
    	setUp();
        assertNotNull(unit);
        assertEquals(1, unit.getId());
        assertNotNull(unit.getPosition());
        assertEquals(tile.getXpos(), unit.getPosition().getXpos());
        assertEquals(tile.getYpos(), unit.getPosition().getYpos());
       
    }

    @Test
    public void testSetAndGetId() {
    	setUp();
        int id = 1;
        unit.setId(id);
        assertEquals(id, unit.getId());

        // Test with a null id
        //assertThrows(IllegalArgumentException.class, () -> {unit.setId(-1);});
        assertThrows(NullPointerException.class, () -> {unit.setId((Integer) null);});

        // Test with a large id
        int largeId = Integer.MAX_VALUE;
        unit.setId(largeId);
        assertEquals(largeId, unit.getId());
    }

    @Test
    public void testSetAndGetAnimation() {
    	setUp();
        UnitAnimationType animation = UnitAnimationType.move; // or any other animation
        unit.setAnimation(animation);
        assertEquals(animation, unit.getAnimation());

        // Test with a null animation
        assertThrows(NullPointerException.class, () -> unit.setAnimation(null));
    }

    @Test
    public void testSetAndGetCorrection() {
    	setUp();
        ImageCorrection correction = new ImageCorrection();
        unit.setCorrection(correction);
        assertEquals(correction, unit.getCorrection());

        // Test with a null correction
         assertThrows(NullPointerException.class, () -> unit.setCorrection(null));
    }

    @Test
    public void testProperties() {
    	setUp();
        Position newPosition = new Position(5, 6, 7, 8); // or any other position 
        unit.setPosition(newPosition);
        assertEquals(newPosition, unit.getPosition());
    }

    @Test
    public void testUnitBehavior() {
    	setUp();
        //testIsStunned
        unit.setStunned(true);
        assertTrue(unit.isStunned());
        unit.setStunned(false);
        assertFalse(unit.isStunned());

        //test for positions
        this.tile = new Tile("", 1, 2, 0, 0, 3, 4);
        unit.setPositionByTile(tile);
        Position position = unit.getPosition();
        assertEquals(tile.getXpos(), position.getXpos());
        assertEquals(tile.getYpos(), position.getYpos());
        assertEquals(tile.getTilex(), position.getTilex());
        assertEquals(tile.getTiley(), position.getTiley());

    }


}