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
    int calculateStars(LevelData levelData, int playerSteps);

    void recordLevelResult(int levelNumber, int playerSteps, LevelData levelData);

    int getBestStepsForLevel(int levelNumber);

    LevelDTO handleLevelCompletion(int levelNumber, int playerSteps, LevelData levelData);
}
