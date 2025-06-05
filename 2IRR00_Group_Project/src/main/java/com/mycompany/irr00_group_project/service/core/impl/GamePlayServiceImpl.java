package com.mycompany.irr00_group_project.service.core.impl;

import com.mycompany.irr00_group_project.model.core.LevelData;
import com.mycompany.irr00_group_project.service.core.GamePlayService;

import java.util.HashMap;
import java.util.Map;

public class GamePlayServiceImpl implements GamePlayService {
    private final Map<Integer, Integer> bestStepsPerLevel = new HashMap<>();

    @Override
    public int calculateStars(LevelData levelData, int playerSteps) {
        int optimal = levelData.getOptimalSteps();
        if (playerSteps <= 0) return 0; // Not solved
        if (playerSteps <= optimal) return 3;
        if (playerSteps <= optimal * 1.2) return 2;
        if (playerSteps <= optimal * 1.5) return 1;
        return 1;
    }

    @Override
    public void recordLevelResult(int levelNumber, int playerSteps, LevelData levelData) {
        int prevBest = bestStepsPerLevel.getOrDefault(levelNumber, Integer.MAX_VALUE);
        if (playerSteps > 0 && playerSteps < prevBest) {
            bestStepsPerLevel.put(levelNumber, playerSteps);
        }
    }

    @Override
    public int getBestStepsForLevel(int levelNumber) {
        return bestStepsPerLevel.getOrDefault(levelNumber, -1);
    }
}