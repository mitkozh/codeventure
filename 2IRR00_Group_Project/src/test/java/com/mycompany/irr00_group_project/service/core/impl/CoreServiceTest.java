package com.mycompany.irr00_group_project.service.core.impl;

import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import com.mycompany.irr00_group_project.model.core.GameState;
import com.mycompany.irr00_group_project.model.core.MovementResult;
import com.mycompany.irr00_group_project.model.core.Point;
import com.mycompany.irr00_group_project.model.core.SpriteCharacter;
import com.mycompany.irr00_group_project.model.core.LevelData;
import com.mycompany.irr00_group_project.model.enums.Direction;
import com.mycompany.irr00_group_project.model.enums.TileType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CoreServiceTest {

    private static class TestLevelServiceUtil {
        private final Map<Integer, LevelDTO> testLevelProgress = new HashMap<>();

        public TestLevelServiceUtil() {
            testLevelProgress.put(1, new LevelDTO(1, 0, true));
        }

        protected void loadProgress() {
            if (!testLevelProgress.containsKey(1)) {
                testLevelProgress.put(1, new LevelDTO(1, 0, true));
            }
        }

        protected void saveProgress() {
        }

        public List<LevelDTO> getAllLevelsDTO() {
            List<LevelDTO> levels = new ArrayList<>();
            for (int i = 1; i <= 50; i++) {
                LevelDTO levelProgress = getLevelProgress(i);
                levels.add(levelProgress);
            }
            return levels;
        }

        public LevelDTO getLevelProgress(int levelNumber) {
            boolean firstUnlockedByDefault = levelNumber == 1;
            return testLevelProgress.getOrDefault(levelNumber,
                    new LevelDTO(levelNumber, 0, firstUnlockedByDefault));
        }

        public void unlockNextLevel(int levelNumber) {
            int nextLevel = levelNumber + 1;
            if (!testLevelProgress.containsKey(nextLevel)) {
                testLevelProgress.put(nextLevel, new LevelDTO(nextLevel, 0, true));
            }
        }

        public void completeLevelAndSave(LevelDTO levelNewData) {
            int levelNumber = levelNewData.getLevelNumber();
            int levelNewDataStars = levelNewData.getStars();
            LevelDTO currentProgress = testLevelProgress.get(levelNumber);
            if (currentProgress == null || levelNewDataStars > currentProgress.getStars()) {
                testLevelProgress.put(levelNumber, levelNewData);
            }
            unlockNextLevel(levelNumber);
            saveProgress();
        }
    }

    @Test
    void testGetAllLevelsDTO() {
        TestLevelServiceUtil levelService = new TestLevelServiceUtil();
        List<LevelDTO> levels = levelService.getAllLevelsDTO();
        assertEquals(50, levels.size());
        assertEquals(1, levels.get(0).getLevelNumber());
        assertTrue(levels.get(0).isUnlocked());
        assertEquals(0, levels.get(0).getStars());
    }

    @Test
    void testGetLevelProgress_FirstLevelUnlockedByDefault() {
        TestLevelServiceUtil levelService = new TestLevelServiceUtil();
        LevelDTO progress = levelService.getLevelProgress(1);
        assertEquals(1, progress.getLevelNumber());
        assertTrue(progress.isUnlocked());
        assertEquals(0, progress.getStars());
    }

    @Test
    void testCompleteLevelAndSave_UnlocksNextLevel() {
        TestLevelServiceUtil levelService = new TestLevelServiceUtil();
        LevelDTO newData = new LevelDTO(1, 3, true);
        levelService.completeLevelAndSave(newData);
        LevelDTO nextLevel = levelService.getLevelProgress(2);
        assertTrue(nextLevel.isUnlocked());
        assertEquals(0, nextLevel.getStars());
    }

    @Test
    void testTryMoveForward_NoSprite() {
        MovementServiceImpl movementService = new MovementServiceImpl();
        GameState gameState = new GameState() {
            
            @Override
            public SpriteCharacter getSprite() {
                return null;
            }

            @Override
            public TileType[][] getGrid() {
                return (TileType[][]) new Object[3][3];
            }

            @Override
            public TileType getTileAt(int row, int col) {
                return null;
            }

            @Override
            public void setTileAt(int row, int col, TileType type) {
            }

            @Override
            public List<Point> getCollectedKeys() {
                return new ArrayList<>();
            }

            @Override
            public Map<Point, Point> getDoorKeyPair() {
                return new HashMap<>();
            }
        };
        MovementResult result = movementService.tryMoveForward(gameState);
        assertFalse(result.isSuccessful());
        assertEquals("No sprite found", result.getMessage());
    }

    @Test
    void testTurnRight() {
        MovementServiceImpl movementService = new MovementServiceImpl();
        SpriteCharacter sprite = new SpriteCharacter(0, 0, null) {
            private Direction direction = Direction.NORTH;

            @Override
            public int getCurrentRow() { 
                return 1; 
            }

            @Override
            public int getCurrentCol() { 
                return 1; 
            }

            @Override
            public Direction getCurrentDirection() { 
                return direction; 
            }

            @Override
            public void moveTo(int newRow, int newCol) {
            }

            @Override
            public void setCurrentDirection(Direction direction) { 
                this.direction = direction; 
            }
        };
        GameState gameState = new GameState() {

            @Override
            public SpriteCharacter getSprite() { 
                return sprite; 
            }

            @Override
            public TileType[][] getGrid() { 
                return (TileType[][]) new Object[3][3]; 
            }

            @Override
            public TileType getTileAt(int row, int col) { 
                return null; 
            }

            @Override
            public void setTileAt(int row, int col, TileType type) {
            }

            @Override
            public List<Point> getCollectedKeys() { 
                return new ArrayList<>(); 
            }

            @Override
            public Map<Point, Point> getDoorKeyPair() { 
                return new HashMap<>(); 
            }
        };
        movementService.turnRight(gameState);
        assertEquals(Direction.EAST, sprite.getCurrentDirection());
    }

    @Test
    void testTurnLeft() {
        MovementServiceImpl movementService = new MovementServiceImpl();
        SpriteCharacter sprite = new SpriteCharacter(0, 0, null) {
            private Direction direction = Direction.EAST;

            @Override
            public int getCurrentRow() { 
                return 1; 
            }

            @Override
            public int getCurrentCol() { 
                return 1; 
            }

            @Override
            public Direction getCurrentDirection() { 
                return direction; 
            }

            @Override
            public void moveTo(int newRow, int newCol) {
            }

            @Override
            public void setCurrentDirection(Direction direction) { 
                this.direction = direction; 
            }
        };
        GameState gameState = new GameState() {

            @Override
            public SpriteCharacter getSprite() { 
                return sprite; 
            }

            @Override
            public TileType[][] getGrid() { 
                return (TileType[][]) new Object[3][3]; 
            }

            @Override
            public TileType getTileAt(int row, int col) { 
                return null; 
            }

            @Override
            public void setTileAt(int row, int col, TileType type) {
            }

            @Override
            public List<Point> getCollectedKeys() { 
                return new ArrayList<>(); 
            }

            @Override
            public Map<Point, Point> getDoorKeyPair() { 
                return new HashMap<>(); 
            }
        };
        movementService.turnLeft(gameState);
        assertEquals(Direction.NORTH, sprite.getCurrentDirection());
    }

    @Test
    void testCalculateStars() {
        GamePlayServiceImpl gamePlayService = new GamePlayServiceImpl();
        LevelData levelData = new LevelData() {
            @Override
            public int getOptimalSteps() { 
                return 10; 
            }
        };
        assertEquals(3, gamePlayService.calculateStars(levelData, 8));
        assertEquals(2, gamePlayService.calculateStars(levelData, 11));
        assertEquals(1, gamePlayService.calculateStars(levelData, 14));
        assertEquals(1, gamePlayService.calculateStars(levelData, 16));
        assertEquals(0, gamePlayService.calculateStars(levelData, 0));
    }

    @Test
    void testHandleLevelCompletion() {
        GamePlayServiceImpl gamePlayService = new GamePlayServiceImpl();
        LevelData levelData = new LevelData() {
            @Override
            public int getOptimalSteps() { 
                return 10; 
            }
        };
        LevelDTO result = gamePlayService.handleLevelCompletion(1, 8, levelData);
        assertEquals(1, result.getLevelNumber());
        assertEquals(3, result.getStars());
        assertTrue(result.isUnlocked());
        assertEquals(8, gamePlayService.getBestStepsForLevel(1));
    }
}