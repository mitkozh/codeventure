package com.mycompany.irr00_group_project.service.core.impl;

import java.util.HashMap;
import java.util.Map;

import com.mycompany.irr00_group_project.model.core.LevelData;
import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import com.mycompany.irr00_group_project.service.core.GamePlayService;

/**
 * Implementation of the GamePlayService interface.
 * This class provides methods to calculate stars based on player performance,
 * record level results, retrieve best steps for levels,
 * and handle level completion logic.
 */
public class GamePlayServiceImpl implements GamePlayService {
    private final Map<Integer, Integer> bestStepsPerLevel = new HashMap<>();

    @Override
    public int calculateStars(LevelData levelData, int playerSteps) {
        checkValidSteps(playerSteps);
        checkValidLevelData(levelData);
        int optimal = levelData.getOptimalSteps();
        if (playerSteps == 0) {
            return 0;
        } // Not solved
        if (playerSteps <= optimal) {
            return 3;
        }
        if (playerSteps <= optimal * 1.2) {
            return 2;
        }
        if (playerSteps <= optimal * 1.5) {
            return 1;
        }
        return 1;
    }

    @Override
    public void recordLevelResult(int levelNumber, int playerSteps, LevelData levelData) {
        checkValidArgumentsAll(levelNumber, playerSteps, levelData);
        int prevBest = bestStepsPerLevel.getOrDefault(levelNumber, Integer.MAX_VALUE);
        if (playerSteps > 0 && playerSteps < prevBest) {
            bestStepsPerLevel.put(levelNumber, playerSteps);
        }
    }

    private void checkValidArgumentsAll(int levelNumber, int playerSteps, LevelData levelData) {
        checkValidLevel(levelNumber);
        checkValidSteps(playerSteps);
        checkValidLevelData(levelData);
    }

    private static void checkValidLevelData(LevelData levelData) {
        if (levelData == null) {
            throw new IllegalArgumentException("Level data cannot be null");
        }
    }

    private static void checkValidSteps(int playerSteps) {
        if (playerSteps < 0) {
            throw new IllegalArgumentException("Player steps cannot be negative: " + playerSteps);
        }
    }

    private static void checkValidLevel(int levelNumber) {
        if (levelNumber < 1 || levelNumber > 50) {
            throw new IllegalArgumentException("Invalid level number: " + levelNumber);
        }
    }

    @Override
    public int getBestStepsForLevel(int levelNumber) {
        checkValidLevel(levelNumber);
        return bestStepsPerLevel.getOrDefault(levelNumber, -1);
    }

    @Override
    public LevelDTO handleLevelCompletion(int levelNumber, int playerSteps, LevelData levelData) {
        checkValidArgumentsAll(levelNumber, playerSteps, levelData);
        recordLevelResult(levelNumber, playerSteps, levelData);
        int stars = calculateStars(levelData, playerSteps);
        return new LevelDTO(levelNumber, stars, true);
    }
}