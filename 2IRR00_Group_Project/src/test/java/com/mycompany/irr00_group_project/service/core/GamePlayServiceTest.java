package com.mycompany.irr00_group_project.service.core;

import com.mycompany.irr00_group_project.model.core.LevelData;
import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import com.mycompany.irr00_group_project.service.core.impl.GamePlayServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
* Junit test class for GamePlayService.
* This class tests the functionality of the GamePlayService,
* including star calculation based on player steps,
* recording level results, retrieving best steps for levels,
* and handling level completion logic.
*/
public class GamePlayServiceTest {

    private GamePlayService gamePlayService;
    private LevelData levelData;

    /**
     * Sets up the GamePlayService instance and LevelData before each test.
     * This method initializes the service and mocks the LevelData with an optimal step count.
     */
    @BeforeEach
    void setUp() {
        gamePlayService = new GamePlayServiceImpl();
        // Mock LevelData with optimal steps = 10
        levelData = new LevelData();
        levelData.setOptimalSteps(10);
    }

    @Test
    void testCalculateStars_OptimalSteps() {
        int stars = gamePlayService.calculateStars(levelData, 10);
        assertEquals(3, stars);
    }

    @Test
    void testCalculateStars_JustAboveOptimal() {
        int stars = gamePlayService.calculateStars(levelData, 12); // within 20%
        assertEquals(2, stars);
    }

    @Test
    void testCalculateStars_Above120Percent() {
        int stars = gamePlayService.calculateStars(levelData, 15); // within 50%
        assertEquals(1, stars);
    }

    @Test
    void testCalculateStars_ZeroSteps() {
        int stars = gamePlayService.calculateStars(levelData, 0);
        assertEquals(0, stars);
    }

    @Test
    void testCalculateStars_NegativeSteps() {
        assertThrows(IllegalArgumentException.class,
            () -> gamePlayService.calculateStars(levelData, -1)
        );
    }

    @Test
    void testCalculateStars_At120Percent() {
        int stars = gamePlayService.calculateStars(levelData, 12); // 120% of 10
        assertEquals(2, stars);
    }

    @Test
    void testCalculateStars_At150Percent() {
        int stars = gamePlayService.calculateStars(levelData, 15); // 150% of 10
        assertEquals(1, stars);
    }

    @Test
    void testCalculateStars_Above150Percent() {
        int stars = gamePlayService.calculateStars(levelData, 16); // above 150%
        assertEquals(1, stars);
    }

    @Test
    void testCalculateStars_NullLevelData() {
        assertThrows(IllegalArgumentException.class, () -> {
            gamePlayService.calculateStars(null, 10);
        });
    }

    @Test
    void testRecordLevelResultAndGetBestSteps() {
        int levelNumber = 1;
        LevelDTO levelDTO = new LevelDTO(levelNumber, levelNumber, false);
        gamePlayService.recordLevelResult(levelDTO, 15, levelData);
        assertEquals(15, gamePlayService.getBestStepsForLevel(levelDTO));

        // Record better result
        gamePlayService.recordLevelResult(levelDTO, 12, levelData);
        assertEquals(12, gamePlayService.getBestStepsForLevel(levelDTO));

        // Record worse result, should not update
        gamePlayService.recordLevelResult(levelDTO, 20, levelData);
        assertEquals(12, gamePlayService.getBestStepsForLevel(levelDTO));
    }

    @Test
    void testGetBestStepsForLevel_NoResult() {
        LevelDTO nonExistentLevel = new LevelDTO(99, 99, false);
        assertThrows(IllegalArgumentException.class, () -> {
            gamePlayService.getBestStepsForLevel(nonExistentLevel);
        });
    }

    @Test
    void testRecordLevelResult_InvalidLevelNumber() {
        LevelDTO invalidLevel = new LevelDTO(-1, -1, false);
        assertThrows(IllegalArgumentException.class, () -> {
            gamePlayService.getBestStepsForLevel(invalidLevel);
        });
    }

    @Test
    void testHandleLevelCompletion() {
        int levelNumber = 2;
        int playerSteps = 10;
        LevelDTO levelDTO = new LevelDTO(levelNumber, levelNumber, false);
        LevelDTO dto = gamePlayService.handleLevelCompletion(levelDTO, playerSteps, levelData);
        assertEquals(levelNumber, dto.getLevelNumber());
        assertEquals(3, dto.getStars());
        assertFalse(dto.isUnlocked());
    }

    @Test
    void testHandleLevelCompletion_AboveOptimalSteps() {
        int levelNumber = 3;
        int playerSteps = 100; // way above optimal
        LevelDTO levelDTO = new LevelDTO(levelNumber, levelNumber, false);
        LevelDTO dto = gamePlayService.handleLevelCompletion(levelDTO, playerSteps, levelData);
        assertEquals(1, dto.getStars());
        assertEquals(1, dto.getStars());
    }

    @Test
    void testHandleLevelCompletion_NullLevelData() {
        assertThrows(IllegalArgumentException.class, () -> {
            LevelDTO levelDTO = new LevelDTO(1, 1, false);
            gamePlayService.handleLevelCompletion(levelDTO, 10, null);
        });
    }
}
