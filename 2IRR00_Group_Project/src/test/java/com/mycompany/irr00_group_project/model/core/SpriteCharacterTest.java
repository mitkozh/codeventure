package com.mycompany.irr00_group_project.model.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

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
}