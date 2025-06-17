package com.mycompany.irr00_group_project.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.mycompany.irr00_group_project.model.core.SpriteCharacter;
import com.mycompany.irr00_group_project.model.enums.Direction;

/**
 * Tests for SpriteCharacter.
 * Verifies sprite positioning, direction, and movement.
 */
public class SpriteCharacterTest {

    /**
     * Tests constructor initialization.
     * Verifies correct position and direction setup.
     */
    @Test
    void testConstructor() {
        SpriteCharacter sprite = new SpriteCharacter(2, 3, Direction.EAST);
        assertEquals(2, sprite.getCurrentRow());
        assertEquals(3, sprite.getCurrentCol());
        assertEquals(Direction.EAST, sprite.getCurrentDirection());
    }

    /**
     * Tests position updates.
     * Verifies row and column can be changed individually.
     */
    @Test
    void testPositionUpdates() {
        SpriteCharacter sprite = new SpriteCharacter(0, 0, Direction.NORTH);
        sprite.setCurrentRow(1);
        sprite.setCurrentCol(1);
        assertEquals(1, sprite.getCurrentRow());
        assertEquals(1, sprite.getCurrentCol());
    }

    /**
     * Tests direction changes.
     * Verifies all direction values can be set.
     */
    @Test
    void testDirectionChanges() {
        SpriteCharacter sprite = new SpriteCharacter(0, 0, Direction.NORTH);
        
        sprite.setCurrentDirection(Direction.EAST);
        assertEquals(Direction.EAST, sprite.getCurrentDirection());
        
        sprite.setCurrentDirection(Direction.SOUTH);
        assertEquals(Direction.SOUTH, sprite.getCurrentDirection());
        
        sprite.setCurrentDirection(Direction.WEST);
        assertEquals(Direction.WEST, sprite.getCurrentDirection());
    }

    /**
     * Tests movement method.
     * Verifies position updates correctly.
     */
    @Test
    void testMoveTo() {
        SpriteCharacter sprite = new SpriteCharacter(0, 0, Direction.NORTH);
        sprite.moveTo(2, 3);
        assertEquals(2, sprite.getCurrentRow());
        assertEquals(3, sprite.getCurrentCol());
    }

    /**
     * Tests negative position values.
     * Verifies sprite can handle negative coordinates.
     */
    @Test
    void testNegativePositions() {
        SpriteCharacter sprite = new SpriteCharacter(-1, -1, Direction.SOUTH);
        assertEquals(-1, sprite.getCurrentRow());
        assertEquals(-1, sprite.getCurrentCol());
        
        sprite.moveTo(-2, -3);
        assertEquals(-2, sprite.getCurrentRow());
        assertEquals(-3, sprite.getCurrentCol());
    }
}