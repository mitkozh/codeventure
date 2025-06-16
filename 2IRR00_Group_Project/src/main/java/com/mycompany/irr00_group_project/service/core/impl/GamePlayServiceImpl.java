package com.mycompany.irr00_group_project.service.core.impl;

import java.util.HashMap;
import java.util.Map;

import com.mycompany.irr00_group_project.model.core.LevelData;
import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;
import com.mycompany.irr00_group_project.service.core.GamePlayService;
import com.mycompany.irr00_group_project.utils.Constants;

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
    public void recordLevelResult(LevelDTO levelDTO, int playerSteps, LevelData levelData) {
        checkValidArgumentsAll(levelDTO, playerSteps, levelData);
        int prevBest = bestStepsPerLevel.getOrDefault(levelDTO.getLevelNumber(), Integer.MAX_VALUE);
        if (playerSteps > 0 && playerSteps < prevBest) {
            bestStepsPerLevel.put(levelDTO.getLevelNumber(), playerSteps);
        }
    }

    private void checkValidArgumentsAll(LevelDTO levelDTO, int playerSteps, LevelData levelData) {
        checkValidLevel(levelDTO);
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

    private static void checkValidLevel(LevelDTO levelDTO) {
        if (levelDTO == null || levelDTO.getLevelNumber() <= 0
                || levelDTO.getLevelNumber() > Constants.MAX_LEVEL) {
            throw new IllegalArgumentException("Invalid LevelDTO provided: " + levelDTO);
        }
    }

    @Override
    public int getBestStepsForLevel(LevelDTO levelDTO) {
        checkValidLevel(levelDTO);
        return bestStepsPerLevel.getOrDefault(levelDTO.getLevelNumber(), -1);
    }

    @Override
    public LevelDTO handleLevelCompletion(LevelDTO levelDTO, int playerSteps, LevelData levelData) {
        checkValidArgumentsAll(levelDTO, playerSteps, levelData);
        recordLevelResult(levelDTO, playerSteps, levelData);
        int stars = calculateStars(levelData, playerSteps);
        levelDTO.setStars(stars);
        return levelDTO;
    }
}