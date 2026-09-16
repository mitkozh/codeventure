package com.codeventure.service.core;

import com.codeventure.model.core.LevelData;
import com.codeventure.model.core.dto.LevelDTO;
import java.util.List;

/**
 * Service interface for managing levels.
 */
public interface LevelService {
    List<LevelDTO> getAllLevelsDTO();

    LevelData getLevelDataByLevelDTO(LevelDTO levelDTO);

    LevelDTO getCurrentLevel();

    void completeLevelAndSave(LevelDTO levelNewData);

    LevelDTO getLevelProgress(int levelNumber);

    void unlockNextLevel(int levelNumber);

    boolean isLevelUnlocked(int levelNumber);

    LevelDTO getFirstLevel();

    void selectLevel(LevelDTO selectedLevel);

    LevelDTO selectNextLevel();
}
