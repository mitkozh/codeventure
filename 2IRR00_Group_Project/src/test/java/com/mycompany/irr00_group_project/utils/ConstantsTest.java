package com.mycompany.irr00_group_project.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Constants class.
 */
public class ConstantsTest {

    @Test
    void testLevelsPerPage() {
        assertEquals(20, Constants.LEVELS_PER_PAGE, "LEVELS_PER_PAGE should be 20");
    }

    @Test
    void testSharedJarPath() {
        String expected = "/com/mycompany/irr00_group_project/assets/jars/shared.jar";
        assertEquals(expected, Constants.SHARED_JAR_PATH,
            "SHARED_JAR_PATH should match expected path");
    }

    @Test
    void testUserCodeFqn() {
        String expected = "player.PlayerCode";
        assertEquals(expected, Constants.USER_CODE_FQN,
            "USER_CODE_FQN should be player.PlayerCode");
    }

    @Test
    void testInitialImportsCode() {
        String expected = """
                package player;
                
                import com.mycompany.irr00_group_project.service.sandbox.UserCode;
                import com.mycompany.irr00_group_project.model.core.Character;
               
                """;
        assertEquals(expected, Constants.INITIAL_IMPORTS_CODE,
            "INITIAL_IMPORTS_CODE should match expected imports");
    }

    @Test
    void testDefaultCode() {
        String expected = """
                public class PlayerCode implements UserCode {
                    @Override
                    public void execute(Character character) {
                        character.turnRight();
                        character.moveForward();
                    }
                }
                """;
        assertEquals(expected, Constants.DEFAULT_CODE,
            "DEFAULT_CODE should match expected default code");
    }

    @Test
    void testMaxLevel() {
        assertEquals(50, Constants.MAX_LEVEL, "MAX_LEVEL should be 50");
    }
}