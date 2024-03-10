// This class tests the Unit class to make sure it works correctly.

import structures.basic.Tile;
import structures.basic.Unit;
import structures.basic.UnitAnimationType;
import structures.basic.ImageCorrection;
import structures.basic.Position;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import java.io.File;

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
    void testSetAndGetId() {
        int id = 1;
        unit.setId(id);
        assertEquals(id, unit.getId());
    }

    @Test
    void testSetAndGetAnimation() {
        UnitAnimationType animation = UnitAnimationType.MOVE; // or any other animation
        unit.setAnimation(animation);
        assertEquals(animation, unit.getAnimation());
    }

    @Test
    void testSetAndGetCorrection() {
        ImageCorrection correction = new ImageCorrection();
        unit.setCorrection(correction);
        assertEquals(correction, unit.getCorrection());
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

    @AfterEach
    void tearDown() {
        File tempFile = new File("temp.txt");
        if (tempFile.exists()) {
        tempFile.delete();
    }
        // Method for Cleaning up the test data just in case
    }
    
    



    // Add more test methods maybe.
}