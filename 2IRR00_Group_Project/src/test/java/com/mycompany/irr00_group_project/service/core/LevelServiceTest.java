package com.mycompany.irr00_group_project.service.core;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import com.mycompany.irr00_group_project.service.core.impl.LevelServiceImpl;
import com.mycompany.irr00_group_project.utils.Constants;

/**
 * JUnit test class for LevelService.
 * This class tests the functionality of the LevelService,
 * including retrieving all levels, checking level progress,
 * completing levels, and unlocking next levels.
 */
public class LevelServiceTest {

    private LevelService levelService;

    @BeforeEach
    void setUp() {
        levelService = LevelServiceImpl.getInstance();
    }

    @Test
    void testGetAllLevelsDTO() {
        List<LevelDTO> levels = levelService.getAllLevelsDTO();
        assertEquals(Constants.MAX_LEVEL, levels.size());
        for (int i = 0; i < levels.size(); i++) {
            LevelDTO level = levels.get(i);
            assertEquals(i + 1, level.getLevelNumber());
        }
    }

    @Test
    void testGetLevelProgress_FirstLevelUnlockedByDefault() {
        LevelDTO progress = levelService.getLevelProgress(1);
        assertEquals(1, progress.getLevelNumber());
        assertTrue(progress.isUnlocked());
    }

    @Test
    void testCompleteLevelAndSave_UnlocksNextLevel() {
        LevelDTO newData = new LevelDTO(1, 3, true);
        levelService.completeLevelAndSave(newData);
        LevelDTO nextLevel = levelService.getLevelProgress(2);
        assertTrue(nextLevel.isUnlocked());
        assertEquals(3, nextLevel.getStars());
    }

    @Test
    void testCompleteLevelAndSave_StarsNotDowngraded() {
        LevelDTO newData = new LevelDTO(1, 3, true);
        levelService.completeLevelAndSave(newData);
        LevelDTO worseData = new LevelDTO(1, 1, true);
        levelService.completeLevelAndSave(worseData);
        LevelDTO progress = levelService.getLevelProgress(1);
        assertEquals(3, progress.getStars());
    }

    @Test
    void testUnlockNextLevel() {
        levelService.unlockNextLevel(1);
        assertTrue(levelService.isLevelUnlocked(2));
    }

    @Test
    void testIsLevelUnlocked() {
        assertTrue(levelService.isLevelUnlocked(1));
        assertFalse(levelService.isLevelUnlocked(Constants.MAX_LEVEL));
    }
}
