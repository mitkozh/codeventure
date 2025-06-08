package com.mycompany.irr00_group_project.service.core;

import com.mycompany.irr00_group_project.model.core.LevelData;
import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;

/**
 * Interface for managing gameplay-related services.
 * This service handles the calculation of stars based on player performance,
 * records level results, retrieves best steps for levels,
 * and manages level completion logic.
 */
public interface GamePlayService {
    /**
     * Calculates the number of stars awarded based on the player's steps taken
     * to complete a level compared to the optimal steps defined in LevelData.
     *
     * @param levelData   The level data, including optimal steps and other metrics.
     * @param playerSteps The number of steps taken by the player to complete the
     *                    level.
     * @return The number of stars awarded (0, 1, 2, or 3).
     * @pre {@code playerSteps} must be non-negative and {@code levelData} not null.
     * @throws IllegalArgumentException if {@code playerSteps} is negative.
     */
    int calculateStars(LevelData levelData, int playerSteps) throws IllegalArgumentException;

    /**
     * Records the result of a level completion.
     *
     * @param levelDTO The number of the level completed.
     * @param playerSteps The number of steps taken by the player to complete the
     *                    level.
     * @param levelData   The data associated with the level,
     *                    including optimal steps and other metrics.
     * @pre {@code levelDTO} must be a non-null LevelDTO object
     * with a valid level number (1-50),
     *      {@code playerSteps} must be non-negative, and {@code levelData} must not be null.
     * @throws IllegalArgumentException if the arguments are invalid.
     */
    void recordLevelResult(LevelDTO levelDTO, int playerSteps, LevelData levelData)
            throws IllegalArgumentException;

    /**
     * Retrieves the best steps taken by the player for a specific level.
     *
     * @param levelDTO The LevelDTO object containing the level number.
     * @return The best number of steps recorded for the specified level.
     * @pre {@code levelDTO} must not be null and must have a valid level number (1-50).
     * @throws IllegalArgumentException if the level number is invalid.
     */
    int getBestStepsForLevel(LevelDTO levelDTO) throws IllegalArgumentException;

    /**
     * Handles the completion of a level, updating the level's progress and
     * unlocking the next level (if possible).
     *
     * @param levelDTO The number of the level being completed.
     * @param playerSteps The number of steps taken by the player to complete the
     *                    level.
     * @param levelData   The level data, including optimal steps and other metrics.
     * @return A {@code LevelDTO} object with information about the completed level.
     * @pre {@code levelDTO} must be not null and have a valid level number,
     *      (1-50),{@code playerSteps} not negative, and {@code levelData} not null.
     * @throws IllegalArgumentException if the arguments are invalid,
     */
    LevelDTO handleLevelCompletion(LevelDTO levelDTO, int playerSteps, LevelData levelData)
            throws IllegalArgumentException;
}
