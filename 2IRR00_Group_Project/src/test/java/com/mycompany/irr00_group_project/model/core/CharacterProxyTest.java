package com.mycompany.irr00_group_project.model.core;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 * Test class for the CharacterProxy Class .
 * Verifies that character commands are properly forwarded to the output stream.
 */
class CharacterProxyTest {

    /**
     * Tests that moveForward() sends the correct command to the output.
     */
    @Test
    void testMoveForward() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        CharacterProxy proxy = new CharacterProxy(printWriter);
        
        proxy.moveForward();
        printWriter.flush();
        
        assertEquals("CMD:MOVE_FORWARD" + System.lineSeparator(), stringWriter.toString(),
            "moveForward() should write 'CMD:MOVE_FORWARD' to output");
    }

    /**
     * Tests that turnLeft() sends the correct command to the output.
     */
    @Test
    void testTurnLeft() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        CharacterProxy proxy = new CharacterProxy(printWriter);
        
        proxy.turnLeft();
        printWriter.flush();
        
        assertEquals("CMD:TURN_LEFT" + System.lineSeparator(), stringWriter.toString(),
            "turnLeft() should write 'CMD:TURN_LEFT' to output");
    }

    /**
     * Tests that turnRight() sends the correct command to the output.
     */
    @Test
    void testTurnRight() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        CharacterProxy proxy = new CharacterProxy(printWriter);
        
        proxy.turnRight();
        printWriter.flush();
        
        assertEquals("CMD:TURN_RIGHT" + System.lineSeparator(), stringWriter.toString(),
            "turnRight() should write 'CMD:TURN_RIGHT' to output");
    }

    /**
     * Tests that constructor throws NullPointerException when given null PrintWriter.
     */
    @Test
    void testConstructorWithNull() {
        assertThrows(NullPointerException.class, () -> new CharacterProxy(null),
            "Constructor should throw NullPointerException for null PrintWriter");
    }
}