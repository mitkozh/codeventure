package com.mycompany.irr00_group_project.model.core.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Data Transfer Object (DTO) for game progress.
 * This class holds the progress of different levels in the game.
 */
public class GameProgressDTO {
    private final Map<Integer, LevelDTO> levelProgress = new HashMap<>();

    /**
     * Gets the progress of {@code levelNumber} level.
     *
     * @param levelNumber the level number to retrieve progress for.
     * @pre {@code levelNumber} must be greater than 0.
     * @return the LevelDTO containing the progress of the specified level.
     * @throws IllegalArgumentException if {@code levelNumber} is less than 1.
     */
    public LevelDTO getLevel(int levelNumber) {
        if (levelNumber < 1) {
            throw new IllegalArgumentException("Level number must be greater than 0");
        }
        return levelProgress.get(levelNumber);
    }

    /**
     * Puts the progress of a level into the map.
     *
     * @param levelNumber  the level number to put progress for.
     * @pre {@code levelNumber} must be greater than 0, and
     * {@code levelNewData} must not be null.
     * @param levelNewData the LevelDTO containing the new data for the level.
     */
    public void putLevel(int levelNumber, LevelDTO levelNewData) {
        if (levelNewData == null) {
            throw new IllegalArgumentException("Level data cannot be null");
        }
        if (levelNumber < 1) {
            throw new IllegalArgumentException("Level number must be greater than 0");
        }
        levelProgress.put(levelNumber, levelNewData);
    }

    /**
     * Gets the progress of a level or returns a default
     * LevelDTO if the level is not found.
     * @param levelNumber the level number to retrieve progress for.
     * @pre {@code levelNumber} must be greater than 0.
     * @return the LevelDTO containing the progress of the specified level,
     */
    public LevelDTO getLevelOrDefault(int levelNumber) {
        if (levelNumber < 1) {
            throw new IllegalArgumentException("Level number must be greater than 0");
        }
        boolean firstUnlockedByDefault = levelNumber == 1;
        LevelDTO levelDTO = new LevelDTO(levelNumber, 0, firstUnlockedByDefault);
        return levelProgress.getOrDefault(levelNumber, levelDTO);
    }

    /**
     * Checks if the game progress contains a specific level.
     * @param nextLevel the level number to check for.
     * @pre {@code nextLevel} must be greater than 0.
     * @return {@code true} if the level is present in the progress.
     * @throws IllegalArgumentException if {@code nextLevel} is less than 1.
     */
    public boolean containsLevel(int nextLevel) {
        if (nextLevel < 1) {
            throw new IllegalArgumentException("Level number must be greater than 0");
        }
        return levelProgress.containsKey(nextLevel);
    }
    
    /**
     * Gets the entry set of the level progress map.
     * @return a Set of Map.Entry containing level numbers
     * and their corresponding LevelDTOs.
     */
    public Set<Map.Entry<Integer, LevelDTO>> getEntrySet() {
        return levelProgress.entrySet();
    }
}
