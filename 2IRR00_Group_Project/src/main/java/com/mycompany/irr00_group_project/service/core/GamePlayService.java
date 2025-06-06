package com.mycompany.irr00_group_project.service.core;

import com.mycompany.irr00_group_project.model.core.LevelData;
import com.mycompany.irr00_group_project.model.core.dto.LevelDTO;

public interface GamePlayService {
    int calculateStars(LevelData levelData, int playerSteps);

    void recordLevelResult(int levelNumber, int playerSteps, LevelData levelData);

    int getBestStepsForLevel(int levelNumber);

    LevelDTO handleLevelCompletion(int levelNumber, int playerSteps, LevelData levelData);
}
